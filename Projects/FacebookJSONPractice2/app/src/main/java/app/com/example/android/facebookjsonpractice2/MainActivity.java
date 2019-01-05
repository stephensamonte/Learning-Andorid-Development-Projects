package app.com.example.android.facebookjsonpractice2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FacebookSdk.sdkInitialize(getApplicationContext());

//        public static ArrayList<FacebookPost> extractFacebookPosts() {

        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/{request-id}",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
            /* handle the result */
                    }
                }
        ).executeAsync();


        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/{post-id}",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
            /* handle the result */
                    }
                }
        ).executeAsync();


            final GraphRequest request = GraphRequest.newGraphPathRequest(
                    AccessToken,
                    "/340304326534",
                    new GraphRequest.Callback() {
                        @Override
                        public void onCompleted(GraphResponse response) {
                            // Insert your code here
                            GraphResponse dataArray = response;
                            String title = dataArray.getString("name");

                        }
                    });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,posts.limit(10)");
            request.setParameters(parameters);
            request.executeAsync();

    }
}
