package app.com.klexos.wakefield.news;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

/**
 * This fragment shows the timeline of a specific username defined in the settings
 */
public class MyNewsActivityFragment4 extends ListFragment {

    // Username String
    private String username;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This line allows fragment to handle menu events
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_my_news_feed, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_information) {
            // Toast or some other action
            MyNewsActivity.toastInformation(getContext(), username);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.twitter_post_list, container, false);

        // This gets the value of the username from MyNewsActivity
        username = MyNewsActivity.getUsername4();

        // Lookup the swipe container view
        final SwipeRefreshLayout swipeLayout =
                (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);

        // This sets the color of the SwipeRefreshLayout
        swipeLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorBlack);

        // This code gets a person's timeline
        final UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName(username)
                .build();
        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(getActivity())
                .setTimeline(userTimeline)
                .build();
        setListAdapter(adapter);

        // This removes the progress bar once the list view is populated
        final ProgressBar loading = (ProgressBar) rootView.findViewById(R.id.loading_circle);
        final Callback<TimelineResult<Tweet>> callback = new Callback<TimelineResult<Tweet>>() {
            @Override
            public void success(Result<TimelineResult<Tweet>> result) {
                // Success
                loading.setVisibility(View.GONE);
            }

            @Override
            public void failure(TwitterException e) {
                // Failure
                if (isAdded()) {
                    NavigationDrawerActivity.displayToastMessage(getContext(),
                            getString(R.string.loading_failure), Toast.LENGTH_LONG);

                    // This removes the loading progress bar
                    loading.setVisibility(View.GONE);
                }
            }
        };
        userTimeline.next(null, callback);

        // This is the swipe refresh layout
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeLayout.setRefreshing(true);
                adapter.refresh(new Callback<TimelineResult<Tweet>>() {
                    @Override
                    public void success(Result<TimelineResult<Tweet>> result) {
                        swipeLayout.setRefreshing(false);
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        // Toast or some other action
                        if (isAdded()) {
                            NavigationDrawerActivity.displayToastMessage(getContext(),
                                    getString(R.string.swipe_refresh_failure), Toast.LENGTH_LONG);
                            swipeLayout.setRefreshing(false);
                        }
                    }
                });
            }
        });
        return rootView;
    }
}