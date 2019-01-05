package app.com.example.android.alarmclocktest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import static android.R.attr.id;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    // to make our alarm manager
    AlarmManager alarm_manager;
    TimePicker alarm_timepicker;
    TextView update_text;
    Context context;
    PendingIntent pending_intent;
    long choose_music_sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.context = this;

        // initialize our alarm manager
        alarm_manager = (AlarmManager) getSystemService(ALARM_SERVICE) ;

        // initialize our timepicker
        alarm_timepicker = (TimePicker) findViewById(R.id.timePicker);

        // initialize our text update box
        update_text = (TextView) findViewById(R.id.update_text);

        // create an instance of a calendar
        final Calendar calendar = Calendar.getInstance();

        // create an intent to the Alarm Reciever class
        final Intent my_intent = new Intent(this.context, Alarm_Reciever.class);


        //create the spinner in the main UI
        Spinner spinner = (Spinner) findViewById(R.id.ringtone_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.music_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        // Set an onClick listener to the onItemSelected method
        spinner.setOnItemSelectedListener(this);



        // initialize start button
        Button alarm_on = (Button) findViewById(R.id.alarm_on);
        // create an onClick listener to start the alarm

        alarm_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /**
                 * Setting calendar instance with the hour and minute that we picked on the time picker
                 */
                calendar.set(Calendar.HOUR_OF_DAY, alarm_timepicker.getHour());
                calendar.set(Calendar.MINUTE, alarm_timepicker.getMinute());

                // get the string values of the hour and minutes
                int hour = alarm_timepicker.getHour();
                int minute = alarm_timepicker.getMinute();

                // convert eh int values to string
                String hour_string = String.valueOf(hour);
                String minute_string = String.valueOf(minute);

                // convert 24 hour time to 12 hour time
                if (hour > 12) {
                    hour_string = String.valueOf(hour - 1);
                }

                if (minute < 10 ){
                    // 10:0 --> 10:07
                    minute_string = "0" + String.valueOf(minute);
                }

                // method that changes the update text Textbox
                set_alarm_text("Alarm set to: " + hour_string + minute_string);

                // put in extra string into my_intent
                // tells the clock that you paused the "alarm_on" button
                my_intent.putExtra("extra", "alarm on");

                // put in an extra logn into my_intent
                // tells the clock that you want a certain value form the spinner
                my_intent.putExtra("music_choice", choose_music_sound);

                // create a pending intent that delays the intent untial the specified calendar time
                pending_intent = PendingIntent.getBroadcast(MainActivity.this, 0,
                        my_intent, PendingIntent.FLAG_UPDATE_CURRENT);

                // set the alarm manager
                alarm_manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        pending_intent);
            }
        });

        // initialize the stop button
        Button alarm_off = (Button) findViewById(R.id.alarm_off);
        // create an onClick listener to unset the alarm or undo an alarm set
        alarm_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // method that changes the update textTextbox
                set_alarm_text("Alarm off!");

                alarm_manager.cancel(pending_intent);

                // put in extra string into my_intent
                // tells the clock that you paused the "alarm_off" button
                my_intent.putExtra("extra", "alarm off");

                // also put an extra long into the alarm off section to
                // prevent crashes in a null pointer exception
                // tells the clock that you want a certain value form the spinner
                my_intent.putExtra("music_choice", choose_music_sound);

                // stop the ringtone
                sendBroadcast(my_intent);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void set_alarm_text(String output ) {
        update_text.setText("Alarm on!");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

        // outputting what ever id the user has selected
        choose_music_sound = id;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // Another interface callback
    }
}
