package com.example.android.arrayadaptertutorial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        String[] myArray = {"A.M.", "Being There", "Summerteeth", "Yankee Hotel Foxtrot",
                "A Ghost Is Born", "Kicking Television: Live in Chicago", "Sky Blue Sky",
                "Wilco (The Album)", "The Whole Love", "Star Wars"};

        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(this, R.layout.list_item, myArray);

        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(myAdapter);
    }
}
