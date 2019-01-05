package app.com.example.android.sonder2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class CurrentFragment extends Fragment {

    // Adapter for the list of posts
    private PostAdapter mPostAdapter;

    public CurrentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_current, container, false);


        // dummy data for the ListView.
        ArrayList<Post> posts = new ArrayList<>();
        posts.add(new Post("Wakefield SGA", "Back to school night is tomorrow!", "2:55pm Tuesday", "url"));
        posts.add(new Post("Wakefield SGA", "Back to school night is tomorrow!", "2:55pm Tuesday", "url"));
        posts.add(new Post("Wakefield SGA", "Back to school night is tomorrow!", "2:55pm Tuesday", "url"));
        posts.add(new Post("Wakefield SGA", "Back to school night is tomorrow!", "2:55pm Tuesday", "url"));
        posts.add(new Post("Wakefield SGA", "Back to school night is tomorrow!", "2:55pm Tuesday", "url"));
        posts.add(new Post("Wakefield SGA", "Back to school night is tomorrow!", "2:55pm Tuesday", "url"));
        posts.add(new Post("Wakefield SGA", "Back to school night is tomorrow!", "2:55pm Tuesday", "url"));
        posts.add(new Post("End Wakefield SGA", "Back to school night is tomorrow!", "2:55pm Tuesday", "url"));

        // Find a reference to the {@link ListView} in the layout
        ListView postListView = (ListView) rootView.findViewById(R.id.listview_post);

        // Create a new adapter that takes an empty list of earthquakes as input
        mPostAdapter = new PostAdapter(getContext(), new ArrayList<Post>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        postListView.setAdapter(mPostAdapter);


//        // Set an item click listener on the ListView, which sends an intent to a web browser
//        // to open a website with more information about the selected post.
//        ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                // Find the current post that was clicked on
//                Post currentPost = adapter.getItem(position);
//
//                // Convert the String URL into a URI object (to pass into the Intent constructor)
//                Uri postUri = Uri.parse(currentPost.getUrl());
//
//                // Create a new intent to view the post URI
//                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, postUri);
//
//                // Send the intent to launch a new activity
//                startActivity(websiteIntent);
//            }
//        });
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // This line allows fragment to handle menu events
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_current, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            FetchUpdateTask postTask = new FetchUpdateTask();
            postTask.execute("340304326534"); // this number is the facebook page's ID
            return true;
        }
        if (id == R.id.action_change) {
            int x = (int) (Math.random() * 3); // this buton gets data from a random choice of facebook pages
            // 0 = wakefield sports, 1 = wakefield wrestling, 2 = Arlington Public Schools
            String[] websites = {"340304326534", "265726902598", "ArlingtonPublicSchools"};

            FetchUpdateTask postTask = new FetchUpdateTask();
            postTask.execute(websites[x]); // this number is the facebook page's ID
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
