package app.com.klexos.wakefield.news;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
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
import com.twitter.sdk.android.tweetui.TwitterListTimeline;

/**
 * The fragment for Wakefield Teachers twitter feeds containing a simple view.
 */
public class HomeTeachersFragment extends ListFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.twitter_post_list, container, false);

        // Lookup the swipe container view
        final SwipeRefreshLayout swipeLayout = (SwipeRefreshLayout)
                rootView.findViewById(R.id.swipeContainer);

        // This sets the color of the SwipeRefreshLayout
        swipeLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorBlack);

        // This gets the wakefield clubs feed from twitter
        final TwitterListTimeline timeline = new TwitterListTimeline.Builder()
                .slugWithOwnerScreenName(getResources().getString(R.string.list_name_teachers),
                        getResources().getString(R.string.list_owner_username))
                .build();
        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(getActivity())
                .setTimeline(timeline)
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
        timeline.next(null, callback);

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