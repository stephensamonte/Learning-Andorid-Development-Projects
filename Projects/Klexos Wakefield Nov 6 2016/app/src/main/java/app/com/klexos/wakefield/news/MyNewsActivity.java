package app.com.klexos.wakefield.news;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;

public class MyNewsActivity extends NavigationDrawerActivity {

    private static String mUsername1;
    private static String mUsername2;
    private static String mUsername3;
    private static String mUsername4;

    // Returns the username to the fragments and the MyNewsAdapter
    public static String getUsername1() {
        return mUsername1;
    }

    public static String getUsername2() {
        return mUsername2;
    }

    public static String getUsername3() {
        return mUsername3;
    }

    public static String getUsername4() {
        return mUsername4;
    }

    // When "Information" dropdown menu item is selected this toasts words
    public static void toastInformation(Context context, String username) {

        NavigationDrawerActivity.displayToastMessage(context,
                context.getString(R.string.information_toast_beginning) + " \"" + username +
                        "\" " + context.getString(R.string.information_toast_ending), Toast.LENGTH_SHORT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This gets the value of the username from shared preferences in the settings under general
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        mUsername1 = prefs.getString(getString(R.string.pref_username_key_1),
                getString(R.string.pref_username_default1));
        mUsername2 = prefs.getString(getString(R.string.pref_username_key_2),
                getString(R.string.pref_username_default2));
        mUsername3 = prefs.getString(getString(R.string.pref_username_key_3),
                getString(R.string.pref_username_default3));
        mUsername4 = prefs.getString(getString(R.string.pref_username_key_4),
                getString(R.string.pref_username_default4));

        // This highlights the "My News" tab in the navigation drawer
        mNavigationView.setCheckedItem(R.id.nav_my_news);

        // This is the content Adapter
        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        MyNewsAdapter adapter = new MyNewsAdapter(this, getSupportFragmentManager());

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

        // Logs "My News Activity" event to Fabric Answers
        Answers.getInstance().logCustom(new CustomEvent("My News Activity"));
    }

    // This ends the activity when no longer visible
    @Override
    protected void onPause() {
        super.onPause();
        if (mainActivityClicked) {
            finish(); // ends the activity
        }
    }
}