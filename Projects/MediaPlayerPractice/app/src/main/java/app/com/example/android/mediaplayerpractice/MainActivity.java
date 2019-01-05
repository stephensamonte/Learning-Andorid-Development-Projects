package app.com.example.android.mediaplayerpractice;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // This is the initialization of the MediaPlayer
        mediaPlayer = MediaPlayer.create(this, R.raw.song);

        // This is the play button
        Button playButton = (Button) findViewById(R.id.play_button);

        playButton.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.start();
//                mediaPlayer.setOnSeekCompleteListener(new MediaPlayer().setOnCompletionListener(){
//                    @Override
//                    public void onCompletion(MediaPlayer mediaPlayer){
//                        Toast.makeText(MainActivity.this, "Play", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                //Toast.makeText(MainActivity. this, "Play", Toast.LENGTH_SHORT).show();
            }
        });

        // This is the pause button
        Button pauseButton = (Button) findViewById(R.id.pause_button);

        pauseButton.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
                //Toast.makeText(MainActivity. this, "Pause", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
