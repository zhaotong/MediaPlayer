package com.media.player.player;

import android.content.Context;
import android.view.Surface;
import android.view.SurfaceHolder;

import com.media.player.listener.IMediaPlayer;
import com.media.player.listener.OnPlayerStateChangeListener;

public class ExoMediaPlayer implements IMediaPlayer {


    private Context context;



    @Override
    public void setOnPlayerStateChangeListener(OnPlayerStateChangeListener onMediaPlayerListener) {

    }

    @Override
    public void initPlayer() {
        this.context = context.getApplicationContext();


    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public void setDisplay(SurfaceHolder holder) {

    }

    @Override
    public void setSurface(Surface var1) {

    }

    @Override
    public void setDataSource(String var1) {

    }

    @Override
    public void prepareAsync() {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void seekTo(long pos) {

    }

    @Override
    public void setSpeed(float speed) {

    }

    @Override
    public long getDuration() {
        return 0;
    }

    @Override
    public long getCurrentPosition() {
        return 0;
    }

    @Override
    public float getSpeed(float speed) {
        return 0;
    }

    @Override
    public long getTcpSpeed() {
        return 0;
    }

    @Override
    public void release() {

    }

}
