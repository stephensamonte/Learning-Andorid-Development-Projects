package app.com.example.android.alarmclocktest;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class RingtonePlayingService extends Service {

    MediaPlayer media_song;
    int startId;
    boolean isRunning;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        // fetch the extra string from the alarm on/off values
        String state = intent.getExtras().getString("extra");
        // fetch the music choice integer values
        Long music_sound_choice = intent.getExtras().getLong("music_choice");


        Log.e("ringtone extra is ", state);
        Log.e("Music choice is ", music_sound_choice.toString());

        // put the notification here, test it out

        // notification
        // set up the notification service
        NotificationManager notify_manager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        // set up an intent that goes to teh Main Activity
        Intent intent_main_activity = new Intent(this.getApplicationContext(), MainActivity.class);
        // set up a pending intent (this waits until the alarm goes off
        PendingIntent pending_intent_main_activity = PendingIntent.getActivity(this, 0,
                intent_main_activity, 0);

        // make the notificaiton parameters
        Notification notification_popup = new Notification.Builder(this)
                .setContentTitle("An alarm is going off")
                .setContentText("Click me!")
                .setContentIntent(pending_intent_main_activity)
                .setAutoCancel(true) // when we click on it it automatically dissapears
                .build();

        /**
         * This converts the extra string values from the intent to the start IDs, values 0 or 1
         */
        assert state != null;
        switch (state) {
            case "alarm on":
                startId = 1;
                break;
            case "alarm off":
                startId = 0;
                break;
            default:
                startId = 0;
                break;
        }

        // if else statements
        if (!this.isRunning && startId == 1){ // if there is no muisc playing and the user pressed "alarm on"
            // music should start playing
            Log.e("there is no music, ", "and you want start");

            this.isRunning = true;
            this.startId = 0;

            // set up the notification call command
            notify_manager.notify(0, notification_popup);

            // play the music sound depending on the pressed music choice id
            if (music_sound_choice == 0){
                // create an instance of the media player
                media_song = MediaPlayer.create(this, R.raw.Actin_Up);
            } else if (music_sound_choice == 1){
                media_song = MediaPlayer.create(this, R.raw.Break_You_In);
            } else if (music_sound_choice == 2){
                media_song = MediaPlayer.create(this, R.raw.I_m_Fly);
            } else {
                media_song = MediaPlayer.create(this, R.raw.Actin_Up);
            }

            // starts the ringtone
            media_song.start();


        } else if (this.isRunning && startId == 0){ // if there is no muisc playing and the user pressed "alarm on"
            // stop playing music
            Log.e("there is music, ", "and you want end");

            // stops the ringtone
            media_song.stop();
            media_song.reset();

            this.isRunning = false;
            this.startId = 0;

        } else if (!this.isRunning && startId == 0){ // if there is no muisc playing and the user pressed "alarm off"
            // do nothing
            Log.e("there is no music, ", "and you want end");

            this.isRunning = false;
            this.startId = 0;

        } else if (this.isRunning && startId == 1 ){ // if there is muisc playing and the user pressed "alarm on"
            // do nothing
            Log.e("there is music, ", "and you want start");

            this.isRunning = false;
            this.startId = 1;

        } else { // catch any other random events
            Log.e("else, ", "somehow you reached this");

        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Tell the user we stopped.
        Log.e("On destroy called", "ta da");

        this.isRunning = false;  // I don't believe this is required
    }
}
