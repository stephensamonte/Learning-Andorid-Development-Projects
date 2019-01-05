package app.com.klexos.android.urbanevolution;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class YoutubePlayerActivity extends YouTubeBaseActivity
        implements YouTubePlayer.OnInitializedListener, View.OnClickListener,
        CompoundButton.OnCheckedChangeListener {

    public static final String KEY_VIDEO_ID = "KEY_VIDEO_ID";
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    protected RadioGroup styleRadioGroup; // this is accessed with an override
    // This is the seek time the user can set
    private int startTime = 0;
    private YouTubePlayer mPlayer;
    private String mVideoId;
    private Button rewindButton;
    private Button playButton;
    private Button pauseButton;
    private Button forewardButton;
    private Button audioMuteButton;
    private Button setLoopVideoButton;
    private Button seekToButton;
    private TextView seekToText;
    private LinearLayout otherViews;

    private YouTubePlayerView playerView;

    private int seekToTimeMillis = 0;

    //this is the end time in milliseconds
    private int endTime;

    // These are the event listeners
    private MyPlayerStateChangeListener playerStateChangeListener;
    private MyPlaybackEventListener playbackEventListener;

    private boolean goLoop = false;
    private RadioButton loopSwitchOff;

    private Handler handler;
    private int loopDurationMillis;
    private Toast toastObject; // This is for debug purposes

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.activity_youtube_player);

        final Bundle arguments = getIntent().getExtras();
        if (arguments != null && arguments.containsKey(KEY_VIDEO_ID)) {
            mVideoId = arguments.getString(KEY_VIDEO_ID);
        } else if (arguments != null && arguments.containsKey(Intent.EXTRA_TEXT)) {
            // This is to get intent string from youtube
            String videoLink = arguments.getString(Intent.EXTRA_TEXT);
            showMessage(videoLink);
            // This finds the video given a link from Youtube
            if (videoLink != null) {
                int idLinkPosition = videoLink.lastIndexOf('/');
                mVideoId = videoLink.substring(idLinkPosition + 1);
            }
        }
        playerView = (YouTubePlayerView) findViewById(R.id.youTubePlayerView);
        playerView.initialize(getString(R.string.DEVELOPER_KEY), this);

        seekToText = (TextView) findViewById(R.id.seek_to_text);

        rewindButton = (Button) findViewById(R.id.rewind_button);
        rewindButton.setOnClickListener(this);
        playButton = (Button) findViewById(R.id.play_button);
        playButton.setOnClickListener(this);
        pauseButton = (Button) findViewById(R.id.pause_button);
        pauseButton.setOnClickListener(this);
        forewardButton = (Button) findViewById(R.id.foreward_button);
        forewardButton.setOnClickListener(this);
        setLoopVideoButton = (Button) findViewById(R.id.set_loop_video_start_button);
        setLoopVideoButton.setOnClickListener(this);
        // This is the seek controller
        seekToButton = (Button) findViewById(R.id.seek_to_button);
        seekToButton.setOnClickListener(this);
        // This is the mute button
        audioMuteButton = (Button) findViewById(R.id.audio_mute_button);
        audioMuteButton.setOnClickListener(this);

        styleRadioGroup = (RadioGroup) findViewById(R.id.loop_video_radio_group);
        ((RadioButton) findViewById(R.id.loop_video_off)).setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.loop_video_short)).setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.loop_video_medium)).setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.loop_video_long)).setOnCheckedChangeListener(this);
        ((RadioButton) findViewById(R.id.loop_video_full)).setOnCheckedChangeListener(this);

        loopSwitchOff = (RadioButton) findViewById(R.id.loop_video_off);

        setControlsEnabled(false);

        playerStateChangeListener = new MyPlayerStateChangeListener();
        playbackEventListener = new MyPlaybackEventListener();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer YoutubePlayer, boolean restored) {

        mPlayer = YoutubePlayer;
        setControlsEnabled(true);

        // I don't believe I need this
        mPlayer.setPlayerStateChangeListener(playerStateChangeListener);
        mPlayer.setPlaybackEventListener(playbackEventListener);

        if (mVideoId != null) {
            if (restored) {
                mPlayer.play();
            } else {
                mPlayer.loadVideo(mVideoId);

            }
        }
        handler = new Handler();
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
            showMessage("On Initialization Failure line 184");
        } else {
            //Handle the failure
            //Toast.makeText(this, "Unable to load video", Toast.LENGTH_LONG).show();
            String errorMessage = String.format(getString(R.string.error_player), youTubeInitializationResult.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        //Null check the player
        if (mPlayer != null) {
            if (v == playButton) {
                mPlayer.play();
            } else if (v == pauseButton) {
                mPlayer.pause();
            } else if (v == rewindButton) {
                mPlayer.seekToMillis(mPlayer.getCurrentTimeMillis() - 5000);
            } else if (v == forewardButton) {
                mPlayer.seekToMillis(mPlayer.getCurrentTimeMillis() + 5000);
            } else if (v == setLoopVideoButton) {
                // Set loop video start time button
                int currentTime = mPlayer.getCurrentTimeMillis();
                seekToTimeMillis = currentTime; // this sets the seek time
                int currentTimeSeconds = currentTime / 1000; // This converts the current time from milliseconds to seconds
                showMessage("Current Time: " + currentTimeSeconds);
                callHandler(); // calls handler
                // This sets the value of the seek
                seekToText.setText(currentTimeSeconds + "", TextView.BufferType.EDITABLE);
            } else if (v == seekToButton) {
                mPlayer.seekToMillis(seekToTimeMillis);
            } else if (v == audioMuteButton) {
                AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                audio.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
            }
        }
    }

    // This sets the video end time to loop
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked && mPlayer != null) {
            switch (buttonView.getId()) {
                case R.id.loop_video_off:
                    goLoop = false;
                    loopDurationMillis = mPlayer.getDurationMillis() + 5000;
                    callHandler(); // calls handler
                    break;
                case R.id.loop_video_short:
                    goLoop = true;
                    loopDurationMillis = 5000;
                    callHandler(); // calls handler
                    break;
                case R.id.loop_video_medium:
                    goLoop = true;
                    loopDurationMillis = 10000;
                    callHandler(); // calls handler
                    break;
                case R.id.loop_video_long:
                    goLoop = true;
                    loopDurationMillis = 15000;
                    callHandler(); // calls handler
                    break;
                case R.id.loop_video_full:
                    goLoop = true;
                    loopDurationMillis = mPlayer.getDurationMillis() + 5000;
                    callHandler(); // calls handler
                    break;
            }
        }
    }

    private void setControlsEnabled(boolean enabled) {
        playButton.setEnabled(enabled);
        pauseButton.setEnabled(enabled);
        styleRadioGroup.setEnabled(enabled);
        forewardButton.setEnabled(enabled);
        rewindButton.setEnabled(enabled);
        setLoopVideoButton.setEnabled(enabled);
        seekToButton.setEnabled(enabled);
        audioMuteButton.setEnabled(enabled);
    }

    private void callHandler() {
        // This releases whatever handlers already exists
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        if (goLoop) {
            // This checks to see if the current time is equal to the end video time
            endTime = seekToTimeMillis + loopDurationMillis;
            if (endTime < mPlayer.getDurationMillis()) {
                runnable();
            }
        }
    }

    private void runnable() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //For every 1 second, check the current time and endTime
                if (mPlayer != null) {
                    try {
                        if (mPlayer.getCurrentTimeMillis() <= endTime) {
                            handler.postDelayed(this, 1000);
                        } else {
                            handler.removeCallbacks(this); //no longer required
                            mPlayer.seekToMillis(seekToTimeMillis); // Loop the video when end reached
                            callHandler();
                            showMessage("Loop time hit");
                        }
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 1000);
    }

    // This shows the toast messages for debug purposes
    private void showMessage(String message) {
        if (toastObject != null) {
            toastObject.cancel();
        }
        toastObject = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toastObject.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        if (mPlayer != null) {
            mPlayer.release();
        }
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        doLayout(); // change the layout when the device is rotated
    }

    private void doLayout() {
        int orientation = getResources().getConfiguration().orientation;
        final int LANDSCAPE = 2;
        final int PORTRAIT = 1;

        otherViews = (LinearLayout) findViewById(R.id.other_views);
        View decorView = getWindow().getDecorView(); // this is the status bar
        // ActionBar actionBar = getActionBar(); // this is the action bar

        LinearLayout.LayoutParams playerParams =
                (LinearLayout.LayoutParams) playerView.getLayoutParams();
        ViewGroup.LayoutParams otherViewsParams = otherViews.getLayoutParams();

        if (orientation == PORTRAIT) {
            otherViews.setVisibility(View.VISIBLE);
            // Show the status bar
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            // show the action bar
            //actionBar.show();

            playerParams.width = otherViewsParams.width = MATCH_PARENT;
            playerParams.height = WRAP_CONTENT;
            otherViewsParams.height = MATCH_PARENT;
        } else if (orientation == LANDSCAPE) {
            //otherViews.setVisibility(View.GONE);
            // Hide the status bar.
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
            //actionBar.hide();

            playerParams.width = MATCH_PARENT;
            playerParams.height = MATCH_PARENT;
            playerParams.weight = 0;
            otherViewsParams.height = 0;
        }
    }

    // I don't think I need this
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            playerView.initialize(getString(R.string.DEVELOPER_KEY), this);
        }
    }

    private final class MyPlaybackEventListener implements YouTubePlayer.PlaybackEventListener {

        @Override
        public void onPlaying() {
            // Called when playback starts, either due to user action or call to play().
        }

        @Override
        public void onPaused() {
            // Called when playback is paused, either due to user action or call to pause().
        }

        @Override
        public void onStopped() {
            // Called when playback stops for a reason other than being paused.
            if (goLoop) {
                mPlayer.seekToMillis(seekToTimeMillis); // Loop the video when end reached
            }
        }

        @Override
        public void onBuffering(boolean b) {
            // Called when buffering starts or ends.
        }

        @Override
        public void onSeekTo(int i) {
            // Called when a jump in playback position occurs, either
            // due to user scrubbing or call to seekRelativeMillis() or seekToMillis()
        }
    }

    private final class MyPlayerStateChangeListener implements YouTubePlayer.PlayerStateChangeListener {

        @Override
        public void onLoading() {
            // Called when the player is loading a video
            // At this point, it's not ready to accept commands affecting playback such as play() or pause()
        }

        @Override
        public void onLoaded(String s) {
            // Called when a video is done loading.
            // Playback methods such as play(), pause() or seekToMillis(int) may be called after this callback.
        }

        @Override
        public void onAdStarted() {
            // Called when playback of an advertisement starts.
        }

        @Override
        public void onVideoStarted() {
            // Called when playback of the video starts.
        }

        @Override
        public void onVideoEnded() {
            // Called when the video reaches its end.
            //showMessage("Ended");
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {
            //set the switch to the Off position
            loopSwitchOff.setChecked(true);
            if (errorReason == YouTubePlayer.ErrorReason.UNEXPECTED_SERVICE_DISCONNECTION) {
                // When this error occurs the player is released and can no longer be used.
                mPlayer = null;
                setControlsEnabled(false);
            }
        }
    }
}