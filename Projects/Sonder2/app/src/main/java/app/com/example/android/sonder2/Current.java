package app.com.example.android.sonder2;

import android.app.LoaderManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

public class Current extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {

    // url = https://graph.facebook.com/v2.7/340304326534?fields=id%2Cname%2Cposts%7Bpermalink_url%2Cmessage%2Ccreated_time%2Cpicture%7D&access_token=1055216731231195%7CF_mKyDrDllbCbZ9imb1vooQzBQU

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current);
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
