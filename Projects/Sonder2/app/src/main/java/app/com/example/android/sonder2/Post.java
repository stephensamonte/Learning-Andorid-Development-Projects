package app.com.example.android.sonder2;

/**
 * An {@link Post} object contains information related to a single post.
 */
public class Post {
    /** Magnitude of the earthquake */
    private String mName;

    /** Location of the earthquake */
    private String mMessage;

    /** Time of the post */
    private String mTime;

    /** Website URL of the post */
    private String mUrl;

    /**
     * Constructs a new {@link Post} object.
     *
     * @param name is the name of the post sender
     * @param message is the message of the post
     * @param time is the time in milliseconds (from the Epoch) when the
     *                           earthquake happened
     * @param url is the website URL to find more details about the earthquake
     */
    public Post(String name, String message, String time, String url) {
        mName = name;
        mMessage = message;
        mTime = time;
        mUrl = url;
    }


    // Below are public getter methods so that other classes can access the values
    // Returns the name of the post sender.
    public String getName() {
        return mName;
    }

     // Returns the message of the post.
    public String getMessage() {
        return mMessage;
    }

    //Returns the time of the post.
    public String getTime() {
        return mTime;
    }

    // Returns the website URL to find more information about the post.
    public String getUrl() {
        return mUrl;
    }

}
