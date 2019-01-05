package app.com.example.android.twitterfabric;

import android.app.ListActivity;
import android.os.Bundle;

import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

public class EmbeddedTweetActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName("wakeathletics")
                .build();
        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(this)
                .setTimeline(userTimeline)
                .build();

        setListAdapter(adapter);

    }
}
