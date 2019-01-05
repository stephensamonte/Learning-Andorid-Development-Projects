package app.com.klexos.android.urbanevolution.stopwatch;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import app.com.klexos.android.urbanevolution.R;

/**
 * A fragment with a Google +1 button.
 */
public class TimerFragment extends Fragment {

    Button startButton, resetButton;
    TextView time;
    long starttime = 0L;
    long endTime = 0L;
    long ellapsedTimeInMilliseconds = 0L;
    long updatedtime = 0L;
    boolean timerIsOff = true;
    int secs = 0;
    int mins = 0;
    int milliseconds = 0;
    Handler handler = new Handler();
    EditText setTimerEditText;
    long setTimer;
    public Runnable updateTimer = new Runnable() {

        public void run() {
            ellapsedTimeInMilliseconds = System.currentTimeMillis() - starttime;
            updatedtime = setTimer - (ellapsedTimeInMilliseconds);

            secs = (int) (updatedtime / 1000);
            mins = secs / 60;
            secs = secs % 60;
            milliseconds = (int) (updatedtime % 1000);
            time.setText(mins + ":" + secs + ":" + milliseconds);
            time.setTextColor(Color.RED);
            handler.postDelayed(this, 0);
        }
    };


    public TimerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timer, container, false);

        setTimerEditText = (EditText) view.findViewById(R.id.set_timer_edit_text);
        startButton = (Button) view.findViewById(R.id.start_button);
        resetButton = (Button) view.findViewById(R.id.reset_button);
        time = (TextView) view.findViewById(R.id.timer_text);
        time.setText(endTime + "");

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timerIsOff) {
                    //timer will start
                    setTimer = Long.valueOf(setTimerEditText.getText().toString());
                    endTime = System.currentTimeMillis() + setTimer;
                    startButton.setText("Pause");
                    starttime = System.currentTimeMillis();
                    handler.postDelayed(updateTimer, 0);
                    timerIsOff = false;
                } else {
                    //timer will pause
                    startButton.setText("Start");
                    time.setTextColor(Color.BLUE);
                    handler.removeCallbacks(updateTimer);
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starttime = 0L;
                endTime = 300000L;
                ellapsedTimeInMilliseconds = 0L;
                updatedtime = 0L;
                timerIsOff = true;
                secs = 0;
                mins = 0;
                milliseconds = 0;
                startButton.setText("Start");
                handler.removeCallbacks(updateTimer);
                time.setText("00:00:00");
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
