package com.media.player;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.media.player.entity.MediaState;
import com.media.player.listener.OnScreenChangeListener;
import com.media.player.listener.OnStateChangeListener;

import java.lang.ref.WeakReference;


public class MediaPlayerController extends FrameLayout implements OnStateChangeListener {

    private TextView player_title;
    private ImageView player_back;
    private ImageView player_image;
    private ProgressBar player_loading;
    private TextView player_state;
    private ImageView player_start_pause;
    private ProgressBar player_progress;
    private TextView player_duration;
    private SeekBar player_seek;
    private ImageView player_full_screen;
    private LinearLayout player_time_ll;

    private int orientation = ORIENTATION_PORTRAIT;

    public static final int ORIENTATION_PORTRAIT = 1;
    public static final int ORIENTATION_LANDSCAPE = 2;


    private UIHandler handler;

    private static final int MSG_WHAT_SHOW_CONTROLLER = 1;
    private static final int MSG_WHAT_HINT_CONTROLLER = 2;
    private static final int MSG_WHAT_PROGRESS = 3;

    protected OnScreenChangeListener onScreenChangeListener;
    protected Context context;

    public MediaPlayerController(@NonNull Context context) {
        super(context);
        this.context = context;
        init();
    }

    public MediaPlayerController(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public MediaPlayerController(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public MediaPlayerController(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        init();
    }

    private static class UIHandler extends Handler {
        WeakReference<MediaPlayerController> weakReference;

        public UIHandler(MediaPlayerController context) {
            super(Looper.getMainLooper());
            weakReference = new WeakReference(context);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            if (weakReference.get() == null)
                return;
            switch (msg.what) {
                case MSG_WHAT_SHOW_CONTROLLER:
                    weakReference.get().handler.removeMessages(MSG_WHAT_HINT_CONTROLLER);
                    weakReference.get().player_time_ll.setVisibility(VISIBLE);
                    weakReference.get().player_start_pause.setVisibility(VISIBLE);
                    weakReference.get().player_progress.setVisibility(GONE);
                    if (weakReference.get().orientation == ORIENTATION_LANDSCAPE) {
                        weakReference.get().player_back.setVisibility(VISIBLE);
                        weakReference.get().player_title.setVisibility(VISIBLE);
                    }

                    weakReference.get().handler.sendEmptyMessageDelayed(MSG_WHAT_HINT_CONTROLLER, 3 * 1000);
                    break;

                case MSG_WHAT_HINT_CONTROLLER:
                    weakReference.get().handler.removeMessages(MSG_WHAT_HINT_CONTROLLER);
                    weakReference.get().player_time_ll.setVisibility(GONE);
                    weakReference.get().player_start_pause.setVisibility(GONE);
                    weakReference.get().player_progress.setVisibility(VISIBLE);
                    weakReference.get().player_back.setVisibility(GONE);
                    weakReference.get().player_title.setVisibility(GONE);
                    break;


                case MSG_WHAT_PROGRESS:
                    weakReference.get().player_progress.setProgress(msg.arg1);
                    weakReference.get().player_seek.setProgress(msg.arg1);
                    break;
            }
        }
    }

    private void init() {
        handler = new UIHandler(this);

        LayoutInflater.from(context).inflate(R.layout.player_controller, this, true);

        player_title = findViewById(R.id.player_title);
        player_back = findViewById(R.id.player_back);
        player_image = findViewById(R.id.player_image);
        player_loading = findViewById(R.id.player_loading);
        player_state = findViewById(R.id.player_state);
        player_start_pause = findViewById(R.id.player_start_pause);
        player_progress = findViewById(R.id.player_progress);
        player_duration = findViewById(R.id.player_duration);
        player_seek = findViewById(R.id.player_seek);
        player_full_screen = findViewById(R.id.player_full_screen);
        player_time_ll = findViewById(R.id.player_time_ll);

        player_back.setOnClickListener(onClickListener);
        player_start_pause.setOnClickListener(onClickListener);
        player_full_screen.setOnClickListener(onClickListener);
        setOnClickListener(onClickListener);


        player_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    MediaPlayerManager.getInstance().getMediaPlayer().seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                MediaPlayerManager.getInstance().getMediaPlayer().pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                MediaPlayerManager.getInstance().getMediaPlayer().start();

            }
        });
    }


    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.player_back) {
                if (orientation == ORIENTATION_LANDSCAPE) {
                    exitFullScreen();
                    return;
                }

            } else if (v.getId() == R.id.player_controller) {
                handler.sendEmptyMessage(MSG_WHAT_SHOW_CONTROLLER);
            } else if (v.getId() == R.id.player_start_pause) {


            } else if (v.getId() == R.id.player_full_screen) {

            }
        }
    };

    public void setOnScreenChangeListener(OnScreenChangeListener onScreenChangeListener) {
        this.onScreenChangeListener = onScreenChangeListener;
    }

    protected void exitFullScreen() {
        if (onScreenChangeListener != null)
            onScreenChangeListener.exitFullScreen();
    }

    protected void enterFullScreen() {
        if (onScreenChangeListener != null)
            onScreenChangeListener.enterFullScreen();
    }

    @Override
    public void onStateChange(int state, Object data) {
        switch (state) {
            case MediaState.STATE_IDLE:
                if (orientation == ORIENTATION_LANDSCAPE) {
                    player_back.setVisibility(VISIBLE);
                    player_title.setVisibility(VISIBLE);
                }
                player_image.setVisibility(VISIBLE);
                player_time_ll.setVisibility(GONE);
                player_progress.setVisibility(GONE);
                player_state.setVisibility(GONE);
                player_loading.setVisibility(GONE);
                player_start_pause.setVisibility(VISIBLE);
                break;

            case MediaState.STATE_PREPARING:
                if (orientation == ORIENTATION_LANDSCAPE) {
                    player_back.setVisibility(VISIBLE);
                    player_title.setVisibility(VISIBLE);
                }
                player_image.setVisibility(VISIBLE);
                player_time_ll.setVisibility(GONE);
                player_progress.setVisibility(GONE);
                player_loading.setVisibility(VISIBLE);
                player_state.setVisibility(VISIBLE);
                player_start_pause.setVisibility(GONE);
                player_state.setText("加载中...");
                break;

            case MediaState.STATE_PREPARED:

                if (orientation == ORIENTATION_LANDSCAPE) {
                    player_back.setVisibility(VISIBLE);
                    player_title.setVisibility(VISIBLE);
                }
                player_image.setVisibility(VISIBLE);
                player_time_ll.setVisibility(GONE);
                player_progress.setVisibility(GONE);
                player_state.setVisibility(GONE);
                player_state.setText("");
                player_start_pause.setVisibility(GONE);

                break;
            case MediaState.STATE_PLAYING:

                player_back.setVisibility(GONE);
                player_title.setVisibility(GONE);
                player_image.setVisibility(GONE);
                player_time_ll.setVisibility(GONE);
                player_state.setVisibility(GONE);
                player_state.setText("");
                player_progress.setVisibility(VISIBLE);
                player_start_pause.setVisibility(GONE);
                player_start_pause.setImageResource(R.drawable.ic_player_pause);

                break;
            case MediaState.STATE_PAUSED:

                player_back.setVisibility(GONE);
                player_title.setVisibility(GONE);
                player_image.setVisibility(GONE);
                player_time_ll.setVisibility(GONE);
                player_state.setVisibility(GONE);
                player_state.setText("");
                player_progress.setVisibility(VISIBLE);
                player_start_pause.setVisibility(VISIBLE);
                player_start_pause.setImageResource(R.drawable.ic_player_start);

                break;
            case MediaState.STATE_COMPLETED:

                if (orientation == ORIENTATION_LANDSCAPE) {
                    player_back.setVisibility(VISIBLE);
                    player_title.setVisibility(VISIBLE);
                }
                player_image.setVisibility(VISIBLE);
                player_time_ll.setVisibility(GONE);
                player_state.setVisibility(GONE);
                player_state.setText("");
                player_progress.setProgress(0);
                player_seek.setProgress(0);
                player_progress.setVisibility(VISIBLE);
                player_start_pause.setVisibility(VISIBLE);
                player_start_pause.setImageResource(R.drawable.ic_player_start);
                break;

            case MediaState.STATE_BUFFERING:
                int secondaryProgress = (int) data;
                player_progress.setSecondaryProgress(secondaryProgress);
                player_seek.setSecondaryProgress(secondaryProgress);
                break;

            case MediaState.STATE_ERROR:
                if (orientation == ORIENTATION_LANDSCAPE) {
                    player_back.setVisibility(VISIBLE);
                    player_title.setVisibility(VISIBLE);
                }
                player_image.setVisibility(VISIBLE);
                player_time_ll.setVisibility(GONE);
                player_state.setVisibility(VISIBLE);
                player_state.setText("出错了...");
                player_progress.setVisibility(GONE);
                player_start_pause.setVisibility(VISIBLE);
                player_start_pause.setImageResource(R.drawable.ic_player_start);

                break;
        }

    }

}
