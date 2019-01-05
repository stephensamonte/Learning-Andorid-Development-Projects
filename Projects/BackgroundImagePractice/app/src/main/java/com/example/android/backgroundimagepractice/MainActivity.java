package com.example.android.backgroundimagepractice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        video = (TextView) findViewById(R.id.second);
//        video.setBackgroundResource(R.drawable.videos_list);
    }


}
