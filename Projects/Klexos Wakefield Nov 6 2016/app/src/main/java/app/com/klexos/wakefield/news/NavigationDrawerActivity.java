package app.com.klexos.wakefield.news;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;

public class NavigationDrawerActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    /* This is the delay time that allows the navigation drawer to close before
    launching a new activity. */
    private static final int NAVDRAWER_LAUNCH_DELAY = 250;

    // This is for toast messages
    public static Toast toast;

    // This is the Navigation Drawer
    public DrawerLayout mdrawer;

    // This is to check which navigation items were selected to properly finish(end) activities
    public boolean mainActivityClicked = false;
    public boolean myNewsActivityClicked = false;

    // The NavigationView for drawer is made public so that activities can highlight certain tabs
    public NavigationView mNavigationView;
    private ActionBarDrawerToggle mDrawerToggle;
    private int CONNECTION_WIFI = 1;
    private int CONNECTION_MOBILE = 2;
    private int CONNECTION_NONE = 0;

    // Toast message method
    public static void displayToastMessage(Context context, String message, int duration) {

        /**
         * This checks to see if there's a toast message currently being displayed.
         * If so the current toast is canceled.
         */
        if (toast != null) {
            toast.cancel();
        }

        // This makes the new toast messages
        toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);

        // Sets the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mdrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerToggle = new ActionBarDrawerToggle(
                this, // host Activity
                mdrawer, // DrawerLayout object
                toolbar, // nav drawer image to replace 'Up' caret
                R.string.navigation_drawer_open, // "open drawer" description for accessibility
                R.string.navigation_drawer_close); // "close drawer" description for accessibility
        mdrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        // This listens for navigation drawer clicks
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        // This is SharedPreferences used to read user settings
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        // This sets the background theme
        String backgroundColor = sharedPrefs.getString(getString(R.string.pref_background_key),
                getString(R.string.pref_background_default));

        // This switch allows users to pick whether or not they want the background
        LinearLayout background = (LinearLayout) findViewById(R.id.content_background_view);
        switch (backgroundColor) {
            case "0":
                background.setBackgroundColor(ContextCompat.getColor(this,
                        R.color.tw__composer_white));
                break;
            case "1":
                background.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBlack));
                break;
            case "2":
                background.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGrey));
                break;
            case "3":
                background.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGreen));
                break;
            case "4":
                background.setBackground
                        (ContextCompat.getDrawable(this, R.drawable.loading_backdrop_dog));
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
        if (id == R.id.action_refresh) {
            recreate(); // This recreates the current activity
            toastNetworkInfo(checkNetworkConnection()); // This checks internet connection

            // Logs "Menu Refresh" event to Fabric Answers
            Answers.getInstance().logCustom(new CustomEvent("Menu Refresh"));
            return true;
        }

        if (id == R.id.action_usernames) {

            // Logs "Menu Refresh" event to Fabric Answers
            Answers.getInstance().logCustom(new CustomEvent("Menu Usernames"));

            // Open a website on wakefield with the twitter usernames
            openWebsite(getString(R.string.school_website_usernames));
        }
        return super.onOptionsItemSelected(item);
    }

    // Handle navigation view item clicks here.
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        mdrawer.closeDrawer(GravityCompat.START);

        if (id == R.id.nav_main_activity) {
            mainActivityClicked = true;
            /* This handler allows the navigation drawer to close before launching a new
             activity by delaying startActivity. */
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                }
            }, NAVDRAWER_LAUNCH_DELAY);

        } else if (id == R.id.nav_my_news) {
            myNewsActivityClicked = true;
            /* This handler allows the navigation drawer to close before launching a new
             activity by delaying startActivity. */
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getBaseContext(), MyNewsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                }
            }, NAVDRAWER_LAUNCH_DELAY);

        } else if (id == R.id.nav_synergy_login_website) {

            // Logs "Navigation Drawer Synergy" event to Fabric Answers
            Answers.getInstance().logCustom(new CustomEvent("Navigation Drawer Synergy"));

            // This is SharedPreferences used to read user settings
            SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

            // Opens synergy based on the user type
            String user = sharedPrefs.getString(getString(R.string.pref_user_key),
                    getString(R.string.pref_user_default));

            switch (user) {
                case "0": // if the user is a student
                    openWebsite(getString(R.string.student_vue_login_website));
                    break;
                case "1": // if the user is a parent
                    openWebsite(getString(R.string.parent_vue_login_website));
                    break;
            }

        } else if (id == R.id.nav_school_website) {

            // Logs "Navigation Drawer School Website" event to Fabric Answers
            Answers.getInstance().logCustom(new CustomEvent("Navigation Drawer School Website"));

            openWebsite(getString(R.string.school_website));

        } else if (id == R.id.nav_share_application) {

            // Logs "Navigation Drawer Share Application" event to Fabric Answers
            Answers.getInstance().logCustom(new CustomEvent("Navigation Drawer Share Application"));

            // this is to get the package name
            String packageName = getApplicationContext().getPackageName();

            // Share application intent
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    getResources().getString(R.string.app_share_introduction) + " " +
                            getResources().getString(R.string.app_name) + " " +
                            getResources().getString(R.string.app_share_subject) + "\n\n" +
                            getResources().getString(R.string.app_download_website) +
                            packageName);
            sendIntent.setType("text/plain");
            if (sendIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(sendIntent);
            }

        } else if (id == R.id.nav_help) {

            /* This handler allows the navigation drawer to close before launching a new
             activity by delaying startActivity. */
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getBaseContext(), HelpActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                }
            }, NAVDRAWER_LAUNCH_DELAY);

        } else if (id == R.id.nav_settings) {

            /* This handler allows the navigation drawer to close before launching a new
             activity by delaying startActivity. */
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getBaseContext(), SettingsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                }
            }, NAVDRAWER_LAUNCH_DELAY);
        }
        return true;
    }

    // This opens a website in an Internet Browser
    private void openWebsite(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    /**
     * Check whether the device is connected to the internet, and if so, whether the connection
     * is wifi or mobile (it could be something else).
     */
    public int checkNetworkConnection() {
        // BEGIN_INCLUDE(connect)
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();

        if (activeInfo != null && activeInfo.isConnected()) {
            boolean wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            boolean mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            if (wifiConnected) {
                return CONNECTION_WIFI; // wifi connection
            } else if (mobileConnected) {
                return CONNECTION_MOBILE; // mobile connection
            }
        }
        return CONNECTION_NONE; // no internet connection
        // END_INCLUDE(connect)
    }

    // Toasts the network connectivity status
    public void toastNetworkInfo(int connectivity) {
        // This makes the new toast messages just for displaying network status
        Toast networkStatus;
        if (connectivity == CONNECTION_WIFI) {
            networkStatus = Toast.makeText(this, getString(R.string.connected_wifi), Toast.LENGTH_SHORT);
            networkStatus.show();
        } else if (connectivity == CONNECTION_MOBILE) {
            networkStatus = Toast.makeText(this, getString(R.string.connected_mobile), Toast.LENGTH_SHORT);
            networkStatus.show();
        } else if (connectivity == CONNECTION_NONE) {
            networkStatus = Toast.makeText(this, getString(R.string.connected_not), Toast.LENGTH_SHORT);
            networkStatus.show();
        }
    }
}