package com.example.android.miwok;

/**
 * (@link Word) represents a vocabulary word that the user wants to learn.
 * It contains a default translation and a Miwok translation wor that word.
 */
public class Word {

    //Default translation for the word
    private String mDefaultTranslation;

    // Miwok translation for the word
    private String mMiwokTranslation;

    /** Audio resource ID for the word */
    private int mAudioResourceId;

    // Image resource ID for the words
    private int mImageResourceId = NO_IMAGE_PROVIDED;

    private static final int NO_IMAGE_PROVIDED = -1;

    /*
    Create a new Word object.
    @param defaultTranslation is the word in a language that the user understands
    @param miwokTranslation is the word in the Miwok language
    @param audioResourceId is the resource ID for the audio file associated with this word
     */
    public Word(String defaultTranslation, String miwokTranslation, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResourceId = audioResourceId;
    }

    /*
    param miwokTranslation is the word in the Miwok language
    @param imageResourceId is the drawable resource ID for the image associated with the word
    @param audioResourceId is the resource ID for the audio file associated with this word */
    public Word(String defaultTranslation, String miwokTranslation, int imageResourceId, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceId = imageResourceId;
        mAudioResourceId = audioResourceId;
    }

    // Get the default translation of the word.
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    // Get teh Miwok translation of the word.
    public String getMiworkTranslation() {
        return mMiwokTranslation;
    }

    // Return teh image resource ID of the word
    public int getImageResourceId(){
        return mImageResourceId;
    }

    // Return whether or not there is an image for the word
    public boolean hasImage() {
        return (mImageResourceId != NO_IMAGE_PROVIDED);
    }

    /*Return the audio resource ID of the word.*/
    public int getAudioResourceId() {
        return mAudioResourceId;
    }
}
