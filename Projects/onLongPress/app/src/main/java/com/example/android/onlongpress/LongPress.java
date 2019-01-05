package com.example.android.onlongpress;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class LongPress extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_press);
        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "You have pressed it long :)", Toast.LENGTH_LONG).show();
                return true;
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "Not Long Enough :(", Toast.LENGTH_SHORT).show();
            }
        });
    }
}





