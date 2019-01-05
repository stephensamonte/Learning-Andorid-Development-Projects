package app.com.klexos.wakefield.news;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Logs "Help Activity" event to Fabric Answers
        Answers.getInstance().logCustom(new CustomEvent("Help Activity"));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendAdditionalHelpEmail();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void openSettingsActivity(View view) {
        // This intent launches the settings activity where users can customize "My Feed"
        Intent intent = new Intent(getBaseContext(), SettingsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void openUsernamesWebsite(View view) {
        // This intent opens a website where users can find a list of their school usernames
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(getString(R.string.school_website_usernames)));
        startActivity(intent);
    }

    public void sendAdditionalHelpEmail() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + // only email apps should handle this
                getResources().getString(R.string.app_help_emailTo)));
        intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_help_subject));
        intent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.app_email_message));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}