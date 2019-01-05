package app.com.klexos.android.urbanevolution;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;

import java.util.ArrayList;

import app.com.klexos.android.urbanevolution.youtube.YouTubeRecyclerViewFragment;

public class YouTubeFragment extends Fragment {
    private final GsonFactory mJsonFactory = new GsonFactory();
    private final HttpTransport mTransport = AndroidHttp.newCompatibleTransport();
    private String[] YOUTUBE_PLAYLISTS = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This line allows fragment to handle menu events
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.youtube, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_youtube_refresh) {
//            getFragmentManager().beginTransaction()
//                    .replace(R.id.container, YouTubeRecyclerViewFragment.newInstance(mYoutubeDataApi, YOUTUBE_PLAYLISTS))
//                    .commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String title = getArguments().getString("title");
        ArrayList<String> playlistsIds = getArguments().getStringArrayList("playlists");

        // Set title bar
        ((MainActivity) getActivity()).setActionBarTitle(title);

        if (playlistsIds != null) {
            YOUTUBE_PLAYLISTS = playlistsIds.toArray(new String[0]);
        }

        View rootView = inflater.inflate(R.layout.activity_youtube, container, false);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (savedInstanceState == null) {
            YouTube mYoutubeDataApi = new YouTube.Builder(mTransport, mJsonFactory, null)
                    .setApplicationName(getResources().getString(R.string.app_name))
                    .build();

            getFragmentManager().beginTransaction()
                    .add(R.id.container, YouTubeRecyclerViewFragment.newInstance(mYoutubeDataApi, YOUTUBE_PLAYLISTS))
                    .commit();

        }
        return rootView;
    }
}
