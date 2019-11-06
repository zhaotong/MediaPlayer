package com.media.player;

import android.content.Context;

import com.media.player.listener.IMediaPlayer;
import com.media.player.listener.OnStateChangeListener;
import com.media.player.player.IjkPlayer;

public class MediaPlayerManager {


    private static MediaPlayerManager instance;

    private Context context;

    public static final int PLAYER_IJK = 2;
    public static final int PLAYER_ANDROID = 1;
    public static final int PLAYER_EXO = 3;


    private OnStateChangeListener onStateChangeListener;
    private IMediaPlayer mediaPlayer;

    public static MediaPlayerManager getInstance() {
        if (instance == null) {
            synchronized (MediaPlayerManager.class) {
                if (instance == null) {
                    instance = new MediaPlayerManager();
                }
            }
        }
        return instance;
    }


    private MediaPlayerManager() {
        changePlayer(PLAYER_IJK);
    }

    public void changePlayer(int type) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        if (type == PLAYER_IJK) {
            mediaPlayer = new IjkPlayer();
            mediaPlayer.initPlayer();
        } else if (type == PLAYER_ANDROID) {
            mediaPlayer = new IjkPlayer();
            mediaPlayer.initPlayer();
        } else if (type == PLAYER_EXO) {
            mediaPlayer = new IjkPlayer();
            mediaPlayer.initPlayer();
        }

        mediaPlayer.setOnStateChangeListener(onStateChangeListener);

    }

    public void setOnStateChangeListener(OnStateChangeListener onStateChangeListener) {
        this.onStateChangeListener = onStateChangeListener;
    }

    public IMediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }


}
