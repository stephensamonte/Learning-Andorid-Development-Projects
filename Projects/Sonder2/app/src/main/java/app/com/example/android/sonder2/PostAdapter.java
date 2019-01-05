package app.com.example.android.sonder2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * An {@link PostAdapter} knows how to create a list item layout for each post
 * in the data source (a list of {@link Post} objects).
 *
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */
public class PostAdapter extends ArrayAdapter<Post>{
    /**
     * Constructs a new {@link PostAdapter}.
     * @param context of the app
     * @param posts is the list of posts, which is the data source of the adapter
     */
    public PostAdapter(Context context, ArrayList<Post> posts) {
        super(context, 0, posts);
    }

    /**
     * Returns a list item view that displays information about the post at the given position
     * in the list of posts.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_post, parent, false);
        }

        // Find the post at the given position in the list of posts
        Post currentPost = getItem(position);

        // Find the TextView with view ID name
        TextView nameView = (TextView) listItemView.findViewById(R.id.list_item_name);
        // Display the sender name of the current post in that TextView
        nameView.setText(currentPost.getName());


        // Find the TextView with view ID message
        TextView messageView = (TextView) listItemView.findViewById(R.id.list_item_message);
        // Display the message of the current post in that TextView
        messageView.setText(currentPost.getMessage());

        // Find the TextView with view ID time
        TextView timeView = (TextView) listItemView.findViewById(R.id.list_item_time);
        // Display the time of the current post in that TextView
        timeView.setText(currentPost.getTime());

        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }
}