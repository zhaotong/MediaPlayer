package com.media.player.player;

import android.media.AudioManager;
import android.view.Surface;
import android.view.SurfaceHolder;

import com.media.player.entity.MediaState;
import com.media.player.listener.IMediaPlayer;
import com.media.player.listener.OnStateChangeListener;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class IjkPlayer implements IMediaPlayer {


    private IjkMediaPlayer ijkMediaPlayer;

    private OnStateChangeListener onStateChangeListener;


    @Override
    public void setOnStateChangeListener(OnStateChangeListener onStateChangeListener) {
        this.onStateChangeListener = onStateChangeListener;
    }

    @Override
    public void initPlayer() {
        ijkMediaPlayer = new tv.danmaku.ijk.media.player.IjkMediaPlayer();
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "start-on-prepared", 0);
        ijkMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "opensles", 1);

        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "overlay-format", IjkMediaPlayer.SDL_FCC_RV32);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "framedrop", 1);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "start-on-prepared", 0);

        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "http-detect-range-support", 1);

        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_CODEC, "skip_loop_filter", 48);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_CODEC, "min-frames", 100);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "enable-accurate-seek", 1);
        ijkMediaPlayer.setVolume(1.0f, 1.0f);

        //设置播放前的探测时间 ,达到首屏秒开效果
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "analyzemaxduration", 100L);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "probesize", 10240L);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "flush_packets", 1L);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "packet-buffering", 0L);

        ijkMediaPlayer.setOnCompletionListener(new tv.danmaku.ijk.media.player.IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(tv.danmaku.ijk.media.player.IMediaPlayer iMediaPlayer) {

                if (onStateChangeListener != null) {
                    onStateChangeListener.onStateChange(MediaState.STATE_COMPLETED, null);
                }
            }
        });

        ijkMediaPlayer.setOnBufferingUpdateListener(new tv.danmaku.ijk.media.player.IMediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(tv.danmaku.ijk.media.player.IMediaPlayer iMediaPlayer, int i) {
                if (onStateChangeListener != null) {
                    onStateChangeListener.onStateChange(MediaState.STATE_BUFFERING, i);
                }
            }
        });

        ijkMediaPlayer.setOnPreparedListener(new tv.danmaku.ijk.media.player.IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(tv.danmaku.ijk.media.player.IMediaPlayer iMediaPlayer) {
                if (onStateChangeListener != null) {
                    onStateChangeListener.onStateChange(MediaState.STATE_PAUSED, null);
                }
            }
        });

        ijkMediaPlayer.setOnVideoSizeChangedListener(new tv.danmaku.ijk.media.player.IMediaPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(tv.danmaku.ijk.media.player.IMediaPlayer iMediaPlayer, int i, int i1, int i2, int i3) {
                if (onStateChangeListener != null) {
                    onStateChangeListener.onStateChange(MediaState.STATE_SIZE, i1);
                }
            }
        });

        ijkMediaPlayer.setOnErrorListener(new tv.danmaku.ijk.media.player.IMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(tv.danmaku.ijk.media.player.IMediaPlayer iMediaPlayer, int i, int i1) {
                if (onStateChangeListener != null) {
                    onStateChangeListener.onStateChange(MediaState.STATE_ERROR, "");
                }
                return false;
            }
        });

        ijkMediaPlayer.setOnInfoListener(new tv.danmaku.ijk.media.player.IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(tv.danmaku.ijk.media.player.IMediaPlayer iMediaPlayer, int i, int i1) {

                switch (i){

                }
                return false;
            }
        });

    }

    @Override
    public boolean isPlaying() {
        return ijkMediaPlayer.isPlaying();
    }

    @Override
    public void setDisplay(SurfaceHolder holder) {
        ijkMediaPlayer.setDisplay(holder);
    }

    @Override
    public void setSurface(Surface surface) {
        ijkMediaPlayer.setSurface(surface);

    }

    @Override
    public void setDataSource(String path) {
        try {
            if (onStateChangeListener != null) {
                onStateChangeListener.onStateChange(MediaState.STATE_IDLE, null);
            }
            ijkMediaPlayer.setDataSource(path);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void prepareAsync() {
        if (onStateChangeListener != null) {
            onStateChangeListener.onStateChange(MediaState.STATE_PREPARING, null);
        }
        ijkMediaPlayer.prepareAsync();
    }

    @Override
    public void start() {
        ijkMediaPlayer.start();
    }

    @Override
    public void stop() {
        ijkMediaPlayer.stop();

    }

    @Override
    public void pause() {
        ijkMediaPlayer.pause();

    }

    @Override
    public void seekTo(long pos) {
        ijkMediaPlayer.seekTo(pos);

    }


    @Override
    public void setSpeed(float speed) {
        ijkMediaPlayer.setSpeed(speed);

    }


    @Override
    public long getDuration() {
        return ijkMediaPlayer.getDuration();
    }

    @Override
    public long getCurrentPosition() {
        return ijkMediaPlayer.getCurrentPosition();
    }


    @Override
    public float getSpeed(float speed) {
        return ijkMediaPlayer.getSpeed(speed);
    }

    @Override
    public long getTcpSpeed() {
        return ijkMediaPlayer.getTcpSpeed();
    }

    @Override
    public void release() {
        ijkMediaPlayer.reset();
        ijkMediaPlayer.release();
        ijkMediaPlayer = null;

        if (onStateChangeListener != null) {
            onStateChangeListener.onStateChange(MediaState.STATE_RELEASE, null);
        }
    }
}
