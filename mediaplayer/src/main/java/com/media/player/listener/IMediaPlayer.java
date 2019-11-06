package com.media.player.listener;

import android.view.Surface;
import android.view.SurfaceHolder;

public interface IMediaPlayer {

//    void setOnPlayerStateChangeListener(OnPlayerStateChangeListener onMediaPlayerListener);

    void setOnStateChangeListener(OnStateChangeListener onStateChangeListener);

    void initPlayer();

    boolean isPlaying();

    void setDisplay(SurfaceHolder holder);

    void setSurface(Surface var1);

    void setDataSource(String var1);

    void prepareAsync();

    void start();


    void stop();

    /**
     * 暂停
     */
    void pause();


    /**
     * seek到制定的位置继续播放
     *
     * @param pos 播放位置
     */
    void seekTo(long pos);


    /**
     * 设置播放速度
     *
     * @param speed 播放速度
     */
    void setSpeed(float speed);


    /**
     * 获取办法给总时长，毫秒
     *
     * @return 视频总时长ms
     */
    long getDuration();

    /**
     * 获取当前播放的位置，毫秒
     *
     * @return 当前播放位置，ms
     */
    long getCurrentPosition();

    /**
     * 获取播放速度
     *
     * @param speed 播放速度
     * @return 播放速度
     */
    float getSpeed(float speed);

    /**
     * 获取网络加载速度
     *
     * @return 网络加载速度
     */
    long getTcpSpeed();


    /**
     * 释放Player
     */
    void release();
}
