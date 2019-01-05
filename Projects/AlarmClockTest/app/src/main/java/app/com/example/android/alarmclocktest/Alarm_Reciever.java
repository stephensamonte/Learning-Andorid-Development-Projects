package app.com.example.android.alarmclocktest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Alarm_Reciever extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("We are in the reciever.", "yey.");

        // fetch extra strings from the intent
        // tells the app whether the user pressed the alarm on button or the alarm off button
        String get_your_string = intent.getExtras().getString("extra");
        Log.e("what is the key?", get_your_string);

        // getch the extra longs form the intent
        // tells us which value the user picked form the spinner
        Long get_your_music_choice = intent.getExtras().getLong("music_choice");

        Log.e("The music choice is ", get_your_music_choice.toString());

        // create an intent to the ringtone service
        Intent service_intent = new Intent(context, RingtonePlayingService.class);

        // pass the extra string from the MainActivity to the Ringtone Playing Service
        service_intent.putExtra("extra", get_your_string);
        // pass the extra integer from the Reciever to the Ringtone Playing Service
        service_intent.putExtra("music_choice", get_your_music_choice);

        // start the ringtone service
        context.startService(service_intent);


    }
}
