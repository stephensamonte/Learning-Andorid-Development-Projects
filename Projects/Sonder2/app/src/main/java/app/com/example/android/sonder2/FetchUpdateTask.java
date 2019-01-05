package app.com.example.android.sonder2;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FetchUpdateTask extends AsyncTask<String, Void, ArrayList<Post>> {

    private final String LOG_TAG = FetchUpdateTask.class.getSimpleName();

    @Override
    protected ArrayList<Post> doInBackground(String... params) {
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String postJsonStr = null;

        // This is for the url builder that im not using
//            String search = "?fields=id%2Cname%2Cposts%7Bpermalink_url%2Cmessage%2Ccreated_time%2Cpicture";
//            String apiKey = "%7D&access_token=1055216731231195%7CF_mKyDrDllbCbZ9imb1vooQzBQU";

        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are avaiable at facebook's api explorer


            String baseUrl = "https://graph.facebook.com/v2.7/" + params[0] + "?fields=id%2Cname%2Cposts%7Bpermalink_url%2Cmessage%2Ccreated_time%2Cpicture";
            String apiKey = "%7D&access_token=1055216731231195%7CF_mKyDrDllbCbZ9imb1vooQzBQU"; //+ BuildConfig.OPEN_WEATHER_MAP_API_KEY; (use this instead of having api token there
            URL url = new URL(baseUrl.concat(apiKey));

            // This is the url builder but im not using it
//                final String FACEBOOK_BASE_URL =
//                        "https://graph.facebook.com/v2.7/";
//                final String WEBSITE_ID = null;
//                final String QUERY_PARAM = null;
//                final String Key = null;
//                Uri builtUri = Uri.parse(FACEBOOK_BASE_URL).buildUpon()
//                        .appendQueryParameter(WEBSITE_ID, params[0])
//                        .appendQueryParameter(QUERY_PARAM, search)
//                        .appendQueryParameter(Key, apiKey)
//                        .build()
//                URL url = new URL(builtUri.toString()); // this is for making the url

            Log.v(LOG_TAG, "Built URI " + url); // this si for checking the url

            // Create the request to Facebook, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            postJsonStr = buffer.toString();
            Log.v(LOG_TAG, "Post JSON String " + postJsonStr);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        // this is to parse the data from the server
        try {
            return getPostDataFromJson(postJsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // this will only happen if there was an error getting or parsing the data
        return null;
    }

        /* Take the String representing the complete forecast in JSON Format and
        pull out the data we need to construct the Strings needed for the wireframes.
        The constructor takes teh JSON string and converts it into an Object hierarchy for us.
         */

    private ArrayList<Post> getPostDataFromJson(String postJsonStr)
            throws JSONException {

        // Create an empty ArrayList that we can start adding posts to
        ArrayList<Post> posts = new ArrayList<>();

        // These are the names of the JSON objects that need to be extracted.
        final String FACEBOOK_NAME = "name";
        final String FACEBOOK_POSTS = "posts";
        final String FACEBOOK_DATA = "data";
        final String FACEBOOK_MESSAGE = "message";
        final String FACEBOOK_TIME = "created_time";

        JSONObject postJson = new JSONObject(postJsonStr);
        JSONObject postsJson = postJson.getJSONObject(FACEBOOK_POSTS);
        JSONArray postArray = postsJson.getJSONArray(FACEBOOK_DATA);
        Log.v("hello", postArray.toString());
        Log.v("nice", "" + postArray.length());

        // FACEBOOK returns current posts based upon the facebook page being asked for.

        // Get the content of each post
        for (int i = 0; i < postArray.length(); i++){
            // Get a single post at position i within the list of posts
            JSONObject onePost = postArray.getJSONObject(i);

            // Extract the value for the key called "message"
            // message is inside an if statement because sometimes return doesn't
            // give a key called message
            String message = "";
            if (onePost.has(FACEBOOK_MESSAGE)){
                message = onePost.getString(FACEBOOK_MESSAGE);
            }

            // Extract the value for the key called "created_time"
            String time = onePost.getString(FACEBOOK_TIME);


            // Create a new {@link Post} object with the name, message, time,
            // and url from the JSON response.
            Post post = new Post (FACEBOOK_NAME, message, time, "url");

            // Add the new {@link Earthquake} to the list of earthquakes.
            posts.add(post);
        }
        // this prints to the log the string data that was extracted above
            Log.v("nope", posts.toString());
        return posts;
    }

    // At the end of FetchUpdateTask onPostExecute is called to update the adapter to update the
    // UserInterface. String[] result here is from the do in background class above its "resultStrs"
//    @Override
//    protected void onPostExecute(ArrayList<Post> result) {
//        return posts
//    }
}