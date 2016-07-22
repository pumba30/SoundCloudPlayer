package com.pumba30.soundcloudplayer.player;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import com.pumba30.soundcloudplayer.R;
import com.pumba30.soundcloudplayer.api.models.Track;
import com.pumba30.soundcloudplayer.player.playerEventBus.PlayerEvent;
import com.pumba30.soundcloudplayer.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

/**
 * Created by pumba30 on 06.07.2016.
 */
public class Player {
    public static final String LOG_TAG = Player.class.getSimpleName();
    public static Player sPlayer = null;
    private Context mContext;
    private MediaPlayer mPlayer;

    public static synchronized Player getInstance(Context context) {
        if (sPlayer == null) {
            sPlayer = new Player(context);
        }
        return sPlayer;
    }

    private Player(Context context) {
        mContext = context;
        mPlayer = new MediaPlayer();
        setErrorListener();
    }

    private void setErrorListener() {
        mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                Log.d(LOG_TAG, String.format("Error player(%s%s)", i, i1));

                if (i == MediaPlayer.MEDIA_ERROR_SERVER_DIED)
                    mediaPlayer.reset();
                else if (i == MediaPlayer.MEDIA_ERROR_UNKNOWN)
                    mPlayer.reset();

                return true;
            }
        });
    }

    public void playPlayer(Track track) {
        try {
            mPlayer.reset();
            mPlayer.setDataSource(track.getStreamUrl() +
                    mContext.getString(R.string.prefix_query_client_id) +
                    mContext.getString(R.string.client_id));
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.prepareAsync();
            mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                    EventBus.getDefault().post(new PlayerEvent(true));
                    Utils.toast(mContext, R.string.play);
                }
            });
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    Log.d(LOG_TAG, "Player onComplete");
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
            Log.d(LOG_TAG, "Failed set data source");
        }
    }

    public void stopPlayer() {
        Utils.toast(mContext, R.string.player_stop);
        EventBus.getDefault().post(new PlayerEvent(false));
        mPlayer.stop();
        mPlayer.reset();
    }

    public void pausePlayer() {
        mPlayer.pause();
    }

    public void playAfterPause() {
        mPlayer.start();
    }

    public boolean isPlayingPlayer() {
        return mPlayer.isPlaying();
    }


}
