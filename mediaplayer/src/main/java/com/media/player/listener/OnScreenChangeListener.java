package com.media.player.listener;

public interface OnScreenChangeListener {

    /**
     * 进入全屏模式
     */
    void enterFullScreen();

    /**
     * 退出全屏模式
     *
     * @return true 退出
     */
    void exitFullScreen();
}
