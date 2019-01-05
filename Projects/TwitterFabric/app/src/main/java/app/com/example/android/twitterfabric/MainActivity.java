package app.com.example.android.twitterfabric;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "P8O4W5lFw2ezeJ0BOOwZf4C2g";
    private static final String TWITTER_SECRET = "8TtRKkKCNIB1JAfd5yEsIepBJT4Kdz1AhKm7K4He4x7gHIGpQ7";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);
    }

    // this opens the EmbededTweetActivity
    public void openSearchActivity(View view){
        Intent open_intent = new Intent(MainActivity.this, EmbeddedTweetActivity.class);
        startActivity(open_intent);
    }

    // this opens the timeline activity
    public void openTimelineActivity(View view){
        Intent open_intent = new Intent(MainActivity.this, TimelineActivity.class);
        startActivity(open_intent);
    }
}
