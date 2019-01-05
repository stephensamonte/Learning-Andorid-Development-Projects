package com.example.android.arraypractice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int[] myArray = new int[5];
        myArray[0] = 10;
        myArray[1] = 20;
        myArray[2] = 30;
        myArray[3] = 40;
        myArray[4] = 50;

        // could have done: int[] myArray = {10, 20, 30, 40, 50};
        for (int i = 0; i < myArray.length; i++)
            {
                Log.v("LogExample", String.valueOf(myArray[i]));
            }
    }
}
