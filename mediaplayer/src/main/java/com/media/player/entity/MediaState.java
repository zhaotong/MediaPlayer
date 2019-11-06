package com.media.player.entity;

public class MediaState {

    /**
     * 播放错误
     **/
    public static final int STATE_ERROR = -1;
    /**
     * 播放未开始
     **/
    public static final int STATE_IDLE = 0;
    /**
     * 播放准备中
     **/
    public static final int STATE_PREPARING = 1;
    /**
     * 播放准备就绪
     **/
    public static final int STATE_PREPARED = 2;
    /**
     * 正在播放
     **/
    public static final int STATE_PLAYING = 3;
    /**
     * 暂停播放
     **/
    public static final int STATE_PAUSED = 4;
    /**
     * 正在缓冲
     **/
    public static final int STATE_BUFFERING = 5;

    /**
     * 播放完成
     **/
    public static final int STATE_COMPLETED = 6;

    /**
     * 视频size
     */
    public static final int STATE_SIZE = 7;


    /**
     * release
     */
    public static final int STATE_RELEASE = 8;

}
