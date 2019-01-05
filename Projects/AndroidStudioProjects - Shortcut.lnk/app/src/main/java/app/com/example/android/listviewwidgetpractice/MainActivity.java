package app.com.example.android.listviewwidgetpractice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    private ArrayAdapter<String> la;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        setupUI();
    }

    public void setupUI()
    {
        lv = (ListView) findViewById(R.id.lvId);
        String[] planets = getResources().getStringArray(R.array.planets);
        lv.setAdapter(new ArrayAdapter<String>(this, R.layout.row, planets));
    }
}