package com.example.android.miwok;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;

    /*
    This listener gets triggered when the (@link MediaPlayer) has completed playing the audio file.
     */
    private MediaPlayer.OnCompletionListener mCompleteListener = new MediaPlayer.OnCompletionListener(){
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        // Create an array of words
        final ArrayList<Word> words = new ArrayList<Word>();

        //words.add("one");
        words.add(new Word("one", "lutti", R.mipmap.number_one, R.raw.number_one));
        words.add(new Word("two", "etiiko", R.mipmap.number_two, R.raw.number_two));
        words.add(new Word("three", "tolookosu", R.mipmap.number_three, R.raw.number_three));
        words.add(new Word("four", "oyyisa", R.mipmap.number_four, R.raw.number_four));
        words.add(new Word("five", "massokka", R.mipmap.number_five, R.raw.number_five));
        words.add(new Word("six", "temmokka", R.mipmap.number_six, R.raw.number_six));
        words.add(new Word("seven", "kenokaku", R.mipmap.number_seven, R.raw.number_seven));
        words.add(new Word("eight", "kawinta", R.mipmap.number_eight, R.raw.number_eight));
        words.add(new Word("nine", "wo'e", R.mipmap.number_nine, R.raw.number_nine));
        words.add(new Word("ten", "na'aache", R.mipmap.number_ten, R.raw.number_ten));

        // Create an {@link ArrayAdapter}, whose data source is a list of Strings. The
        // adapter knows how to create layouts for each item in the list, using the
        // simple_list_item_1.xml layout resource defined in the Android framework.
        // This list item layout contains a single {@link TextView}, which the adapter will set to
        // display a single word.
        WordAdapter adapter= new WordAdapter(this, words, R.color.category_numbers);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_list.xml file.
        ListView listView = (ListView) findViewById(R.id.list);

        // Make the {@link ListView} use the {@link ArrayAdapter} we created above, so that the
        // {@link ListView} will display list items for each word in the list of words.
        // Do this by calling the setAdapter method on the {@link ListView} object and pass in
        // 1 argument, which is the {@link ArrayAdapter} with the variable name itemsAdapter.
        assert listView != null;
        listView.setAdapter(adapter);

        // Set a click listener to play the audio when the list item is clicked on
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Get the {@link Word} object at the given position the user clicked on
                Word word = words.get(position);

                // Release the media player if it currently exists because we are about to
                // play a different sound file.
                releaseMediaPlayer();

                // Create and setup the {@link MediaPlayer} for the audio resource associated
                // with the current word
                mMediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getAudioResourceId());

                // Start the audio file
                mMediaPlayer.start();

                /* Setup a listener on the media player, so that we can stop and release the
                media player once the sound has finished playing. */
                mMediaPlayer.setOnCompletionListener(mCompleteListener);
            }
        });

//   Printing via a linear layout
//     for (int index = 0; index < words.size(); index++) {
//
//            // Create a new TextView
//            TextView wordView = new TextView(this);
//
//            // Set the text to be the word at the currnwt index
//            wordView.setText(words.get(index));
//
//            // Add this text view as another child to the root view of this layout
//            assert rootView != null;
//            rootView.addView(wordView);
//        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // When the activity is stopped, release the media player resources because we won't
        //be playing any more sounds.
        mMediaPlayer.release();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
        }
    }

}
