package app.com.klexos.wakefield.news;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Base64;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import java.io.UnsupportedEncodingException;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends NavigationDrawerActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "UDhPNFc1bEZ3MmV6ZUowQk9Pd1pmNEMyZw=="; // base64
    private static final String TWITTER_SECRET =
            "OFR0UktrS0NOSUIxSkFmZDV5RXNJZXBCSlQ0S2R6MUFoS203SzRIZTR4N2dISUdwUTc="; // base64

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Make sure this is before calling super.onCreate
        setTheme(R.style.AppTheme_NoActionBar);

        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(decode(TWITTER_KEY),
                decode(TWITTER_SECRET));

        /* This gets authentication for the app to make twitter requests and enables Crashlytics
          which also includes answers (for monitoring application metrics) */
        Fabric.with(this, new Twitter(authConfig), new Crashlytics());

        // This highlights the "Home" tab in the navigation drawer
        mNavigationView.setCheckedItem(R.id.nav_main_activity);

        // This is the content Adapter
        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        CategoryAdapter adapter = new CategoryAdapter(this, getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Find the tab layout that shows the tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        // Connect the tab layout with the view pager. This will
        //   1. Update the tab layout when the view pager is swiped
        //   2. Update the view pager when a tab is selected
        //   3. Set the tab layout's tab names with the view pager's adapter's titles
        //      by calling onPageTitle()
        tabLayout.setupWithViewPager(viewPager);

        // Logs "Home Activity" event to Fabric Answers
        Answers.getInstance().logCustom(new CustomEvent("Home Activity"));

        // This is for rating the application
        AppRater.app_launched(this);
    }

    // This is to decode the TWITTER_KEY and TWITTER_SECRET
    // This decodes the base64 strings to the actual key
    private String decode(String base64) {
        // Receiving side
        byte[] data = Base64.decode(base64, Base64.DEFAULT);
        String decodedValue = null;
        try {
            decodedValue = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decodedValue;
    }

    // This ends the activity when no longer visible
    @Override
    protected void onPause() {
        super.onPause();
        if (myNewsActivityClicked) {
            finish(); // ends the activity
        }
    }
}