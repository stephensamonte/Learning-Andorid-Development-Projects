package app.com.example.android.twitterfabric;

import android.app.ListActivity;
import android.os.Bundle;

import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.TwitterListTimeline;

public class TimelineActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeline);

        // Collection "Fabric Picks"
        final TwitterListTimeline timeline = new TwitterListTimeline.Builder()
                .slugWithOwnerScreenName("wakefield-sports", "silentwork1")
                .build();
        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(this)
                .setTimeline(timeline)
                .build();
        setListAdapter(adapter);
    }
}
