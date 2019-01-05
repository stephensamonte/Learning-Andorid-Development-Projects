package app.com.example.android.sonder2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        // Dummay data with the new list layout element
        ArrayList<Post> posts = new ArrayList<>();
        posts.add(new Post("Wakefield SGA", "Back to school night is tomorrow!", "2:55pm Tuesday", "url"));
        posts.add(new Post("Wakefield SGA", "Back to school night is tomorrow!", "2:55pm Tuesday", "url"));
        posts.add(new Post("Wakefield SGA", "Back to school night is tomorrow!", "2:55pm Tuesday", "url"));
        posts.add(new Post("Wakefield SGA", "Back to school night is tomorrow!", "2:55pm Tuesday", "url"));
        posts.add(new Post("Wakefield SGA", "Back to school night is tomorrow!", "2:55pm Tuesday", "url"));
        posts.add(new Post("Wakefield SGA", "Back to school night is tomorrow!", "2:55pm Tuesday", "url"));
        posts.add(new Post("Wakefield SGA", "Back to school night is tomorrow!", "2:55pm Tuesday", "url"));
        posts.add(new Post("End Wakefield SGA", "Back to school night is tomorrow!", "2:55pm Tuesday", "url"));


        // Find a reference to the (@link ListView in the Layout)
        ListView postListView = (ListView) findViewById(R.id.list);

        // Create a new adapter that takes the list of posts as input
        //PostAdapter adapter = new PostAdapter(this, posts);

        // Set the adapter to the (@link listview)
        // so the list can be populated in the user interface
        //postListView.setAdapter(adapter);
    }
}


