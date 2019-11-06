package com.mediaplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.OrientationEventListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.media.player.MediaPlayerView;

public class MainActivity extends AppCompatActivity {

    private int lastOrientation;
    private TextView textView;

    private ImageView imageView;
    private LinearLayout ll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);
        imageView = findViewById(R.id.image);
        ll = findViewById(R.id.ll);

        OrientationEventListener orientationEventListener = new OrientationEventListener(this, SensorManager.SENSOR_DELAY_NORMAL) {
            @Override
            public void onOrientationChanged(int orientation) {
                int mOrientation;
                textView.setText("orientation = " + orientation);
                Log.d("MainActivity", "onOrientationChanged:   orientation  =  " + orientation);
                if (orientation > 45 && orientation < 135) {
                    mOrientation = 90;
                } else if (orientation >= 135 && orientation < 225) {
                    mOrientation = 180;
                } else if (orientation >= 225 && orientation < 315) {
                    mOrientation = 270;
                } else {
                    mOrientation = 0;
                }
                if (mOrientation != lastOrientation) {
                    lastOrientation = mOrientation;
//                    textView.setRotation(360 - lastOrientation);
//                    imageView.setRotation(360 - lastOrientation);
                    ll.setRotation(360 - lastOrientation);
                }
            }
        };
        orientationEventListener.enable();
        MediaPlayerView mediaPlayerView =new MediaPlayerView(this);
    }


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        imageView.setRotation(90);
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
