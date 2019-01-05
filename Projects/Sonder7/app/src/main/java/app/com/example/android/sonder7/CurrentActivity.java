package app.com.example.android.sonder7;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class CurrentActivity extends Fragment {

    public CurrentActivity(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);


        // dummy data for the ListView.
        String[] postArray = {"Wakefield SGA - Back to school night is tomorrow! - 2:55pm Tuesday",
                "sender, message, time",
                "sender, message, time",
                "sender, message, time",
                "sender, message, time",
                "sender, message, time",
                "sender, message, time",
        };

        List<String> posts = new ArrayList<String>(
                Arrays.asList(postArray));

        // Creating an ArrayAdapter which will take data form a source and use it to
        // populate the ListView it's attached to.
        ArrayAdapter<String> mPostAdapter = new ArrayAdapter<String>(
                // The current context (this fragment's parent activity
                getActivity(),
                // ID of list item layout
                R.layout.list_item_post,
                // ID of the textview to populate
                R.id.list_item_post_textview,
                // the Post data
                posts);

        // Get a reference to the ListView, and attach this adapter to the list view
        // The adapter will supply list item layouts to the list view based on the revieved data
        ListView listView = (ListView) rootView.findViewById(
                R.id.listview_post);
        listView.setAdapter(mPostAdapter);

        return rootView;
    }

}
