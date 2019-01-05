package app.com.klexos.android.urbanevolution;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import java.util.ArrayList;

import app.com.klexos.android.urbanevolution.urbanfit.UrbanFitHome;
import io.fabric.sdk.android.Fabric;

import static app.com.klexos.android.urbanevolution.youtube.YoutubePlaylists.breakdancePlaylist;
import static app.com.klexos.android.urbanevolution.youtube.YoutubePlaylists.freerunningPlaylists;
import static app.com.klexos.android.urbanevolution.youtube.YoutubePlaylists.hiphopPlaylist;
import static app.com.klexos.android.urbanevolution.youtube.YoutubePlaylists.parkourPlaylists;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "xrc1QurgW9erMdGKHvp55ZYCr";
    private static final String TWITTER_SECRET = "7VRQgwUcRu8Y8LPknHpw9DJuMqPMyJaONXHOaG7oWQen8ezdrR";
    // This is for toast messages
    public static Toast toast;
    // This is declared so I have access to these globally
    NavigationView navigationView = null;
    Toolbar toolbar = null;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Make sure this is before calling super.onCreate
        setTheme(R.style.AppTheme_NoActionBar);

        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);

        //Set the fragment initially
        HomeFragment fragment = new HomeFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment); // Replace with fragment I just created
        fragmentTransaction.commit();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        if (id == R.id.nav_stopwatch) {
            Intent intent = new Intent(getBaseContext(), StopwatchAcivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        } else if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            //Set the fragment initially
            HomeFragment fragment = new HomeFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment); // Replace with fragment I just created
            fragmentTransaction.commit();

        } else if (id == R.id.nav_urbanfit) {
            //Set the fragment initially
            UrbanFitHome fragment = new UrbanFitHome();
            android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment); // Replace with fragment I just created
            fragmentTransaction.commit();

        } else if (id == R.id.nav_parkour) {
            OpenYoutubeActivityFragment("Parkour", packagePlaylists(parkourPlaylists));

        } else if (id == R.id.nav_freerunning) {
            OpenYoutubeActivityFragment("Freerunning", packagePlaylists(freerunningPlaylists));

        } else if (id == R.id.nav_hiphop_dance) {
            OpenYoutubeActivityFragment("Hip Hop Dancing", packagePlaylists(hiphopPlaylist));

        } else if (id == R.id.nav_break_dance) {
            OpenYoutubeActivityFragment("Breakdancing", packagePlaylists(breakdancePlaylist));

        } else if (id == R.id.nav_partner_information) {
            Intent intent = new Intent(getBaseContext(), PartnerInformationActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);

        } else if (id == R.id.nav_website) {
            openWebsite("https://urbanevo.com/");

        } else if (id == R.id.nav_share_application) {
            // this is to get the package name
            String packageName = getApplicationContext().getPackageName();

            // Share application intent
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Look at this: https://wakefield.apsva.us/");
            sendIntent.setType("text/plain");
            if (sendIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(sendIntent);
            }

        } else if (id == R.id.nav_manage) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // This opens a website in an Internet Browser
    private void openWebsite(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private void OpenYoutubeActivityFragment(String title, ArrayList<String> playlistsStringsArray) {

        //Bundle strings to send to fragment
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putStringArrayList("playlists", playlistsStringsArray);

        //Set the fragment initially
        YouTubeFragment fragment = new YouTubeFragment();
        fragment.setArguments(bundle); // set Fragmentclass Arguments
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private ArrayList<String> packagePlaylists(String[] playlistIdStrings) {
        ArrayList<String> playlistsStringsArray = new ArrayList<String>();
        for (int i = 0; i <= playlistIdStrings.length - 1; i++) {
            playlistsStringsArray.add(playlistIdStrings[i]);
        }
        return playlistsStringsArray;
    }

    // This sets the Title on the App Bar
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
