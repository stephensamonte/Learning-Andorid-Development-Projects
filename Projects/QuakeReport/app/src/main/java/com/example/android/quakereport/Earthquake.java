package com.example.android.quakereport;

public class Earthquake {

    /**
     * Magnitude of the earthquake
     */
    private String mMagnitude;

    /**
     * Location of the earthquake
     */
    private String mLocation;

    /** Time of the earthquake */
    private long mTimeInMilliseconds;


    /**
     * Constructs a new {@link Earthquake} object.
     *
     * @param magnitude is the magnitude (size) of the earthquake
     * @param location  is the location where the earthquake happened
     * @param timeInMilliseconds      is the earthquake happened
     */
    public Earthquake(String magnitude, String location, Long timeInMilliseconds) {
        mMagnitude = magnitude;
        mLocation = location;
        mTimeInMilliseconds = timeInMilliseconds;
    }

    /**
     * Returns the magnitude of the earthquake.
     */
    public String getMagnitude() {
        return mMagnitude;
    }

    /**
     * Returns the location of the earthquake.
     */
    public String getLocation() {
        return mLocation;
    }

    /**
     * Returns the time of the earthquake.
     */
    public Long getTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }
}
