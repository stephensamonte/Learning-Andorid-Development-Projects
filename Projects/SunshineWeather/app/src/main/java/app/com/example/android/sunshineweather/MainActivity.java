package app.com.example.android.sunshineweather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        if (id == R.id.action_map) {
            openPreferredLocationInMap(); // run helper method
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //implicit intent to open a maps applicaiton
    private void openPreferredLocationInMap(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String location = sharedPref.getString(getApplicationContext().getString(R.string.pref_location_key),
                getApplicationContext().getString(R.string.pref_location_default));

        /*
        Using the URI scheme for showing a location on a map
         */

        Uri geoLocation = Uri.parse("geo: 0,0?").buildUpon()
                .appendQueryParameter("q", location)
                .build(); // q = quarry parameter (searches this on the maps application

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);

        // Starts activity only if it resolves successfully
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        } else {
            Log.d(LOG_TAG, "Couldn't call " + location + "no maps app");
        }

    }
}
