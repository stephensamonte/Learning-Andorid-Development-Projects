package app.com.example.android.sunshineweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DetailActivityFragment extends Fragment {

    private static final String LOG_TAG = DetailActivityFragment.class.getSimpleName();

    // to create share text
    private static final String FORECAST_SHARE_HASHTAG = " #SunshineWeatherApp";

    // makes ForecastStr a member variable (this is necessary to package the string before sending to other applications
    private String mForecastStr;

    public DetailActivityFragment() {
        setHasOptionsMenu(true);
    }

    // creates the detail text to send display
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // The detail Activity called via intent.
        Intent intent = getActivity().getIntent();
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            mForecastStr = intent.getStringExtra(Intent.EXTRA_TEXT);
            ((TextView) rootView.findViewById(R.id.detail_text))
                    .setText(mForecastStr);
        }
        return rootView;
    }

    // creates the share forecast string to send to other applications
    private Intent createShareForecastIntent() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, mForecastStr + FORECAST_SHARE_HASHTAG);
        shareIntent.setType("text/plain");
        startActivity(shareIntent);
        return shareIntent;
    }

    //inflates the detail fragment xml which has the share acition bar
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present
        inflater.inflate(R.menu.detailfragment, menu);

        // Retrieve the share menu item
        MenuItem item = menu.findItem(R.id.action_share);

        // Get the provider and hold onto it to set/change the share intent.
        ShareActionProvider mShareActionProvider =
                (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        //Attach an intent to this ShareActionProvider. you can update this at any time,
        // like when the user selects a new piece of data they might like to share.
        if ((mShareActionProvider)!= null) {
            mShareActionProvider.setShareIntent(createShareForecastIntent());
        } else {
            Log.d(LOG_TAG, "Share Action Provider is null");
        }
    }

}