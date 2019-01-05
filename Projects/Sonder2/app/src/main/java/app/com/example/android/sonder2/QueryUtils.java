package app.com.example.android.sonder2;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving post data from Facebook.
 */
public final class QueryUtils {

    /** Tag for the log messages */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Query the Facebook dataset and return a list of {@link Post} objects.
     */
    public static List<Post> fetchUpdateData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        // Extract relevant fields from the JSON response and create a list of {@link Post}s
        List<Post> posts = extractDataFromJson(jsonResponse);

        // Return the list of {@link Earthquake}s
        return posts;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        // Will contain the raw JSON response as a string.
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        //BufferedReader reader = null;

//        // Will contain the raw JSON response as a string.
//        String postJsonStr = null;

        // This is for the url builder that im not using
//            String search = "?fields=id%2Cname%2Cposts%7Bpermalink_url%2Cmessage%2Ccreated_time%2Cpicture";
//            String apiKey = "%7D&access_token=1055216731231195%7CF_mKyDrDllbCbZ9imb1vooQzBQU";

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }


    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


    /**
     * Return a list of {@link Post} objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<Post> extractDataFromJson(String postJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(postJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding posts to
        List<Post> posts = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(postJSON);

            JSONArray postArray = baseJsonResponse.getJSONArray("data");

            // FACEBOOK returns current posts based upon the facebook page being asked for.

            // Get the content of each post
            for (int i = 0; i < postArray.length(); i++) {
                // Get a single post at position i within the list of posts
                JSONObject onePost = postArray.getJSONObject(i);

                // Extract the value for the key called "message"
                // message is inside an if statement because sometimes return doesn't
                // give a key called message
                String message = "";
                if (onePost.has("message")) {
                    message = onePost.getString("message");
                }

                // Extract the value for the key called "created_time"
                String time = onePost.getString("time");

                // Extract the url of the post
                String url = "www.google.com"; // This is a place holder

                // Create a new {@link Post} object with the name, message, time,
                // and url from the JSON response.
                Post post = new Post("name", message, time, url);

                // Add the new {@link Earthquake} to the list of earthquakes.
                posts.add(post);
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        // Return the list of posts
        return posts;
    }

}