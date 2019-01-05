package com.example.android.dynamicautoconpletetextviewpractice;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class MainActivity extends Activity {

    RelativeLayout rl;
    AutoCompleteTextView auto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String str[]={"Arun","Mathev","Vishnu","Vishal","Arjun",
                "Arul","Balaji","Babu","Boopathy","Godwin","Nagaraj"};

        rl = (RelativeLayout) findViewById(R.id.rl);

        auto = new AutoCompleteTextView (MainActivity.this);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                ((int) LayoutParams.WRAP_CONTENT, (int) LayoutParams.WRAP_CONTENT);

        params.leftMargin = 80;
        params.topMargin = 130;

        auto.setLayoutParams(params);
        auto.setEms(10);

        ArrayAdapter<String> adp=new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,str);

        auto.setThreshold(1);
        auto.setAdapter(adp);

        rl.addView(auto);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_main, menu);
//        return true;
//    }
}
