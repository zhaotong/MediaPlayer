package com.media.player;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.media.player.listener.OnScreenChangeListener;
import com.media.player.view.MediaTextureView;

public class MediaPlayerView extends FrameLayout implements OnScreenChangeListener {

    private Context context;

    private MediaPlayerController mediaPlayerController;
    private MediaTextureView textureView;
    private FrameLayout mContainer;


    public MediaPlayerView(@NonNull Context context) {
        super(context);
        this.context = context;
        init();
    }

    public MediaPlayerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public MediaPlayerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public MediaPlayerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        init();
    }


    private void init() {

        mContainer = new FrameLayout(context);
        mContainer.setBackgroundColor(Color.BLACK);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mContainer, params);

        textureView = new MediaTextureView(context);
        mContainer.addView(textureView, params);
        mediaPlayerController = new MediaPlayerController(context);
        mContainer.addView(mediaPlayerController, params);
        mediaPlayerController.bringToFront();


    }



    @Override
    public void enterFullScreen() {

    }

    @Override
    public void exitFullScreen() {

    }
}
