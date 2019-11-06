package com.mediaplayer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.customview.widget.ViewDragHelper;

public class DragView extends ConstraintLayout {

    ViewDragHelper dragHelper;
    Context context;

    public DragView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public DragView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public DragView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    void init() {

        dragHelper = ViewDragHelper.create(this, 1f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(@NonNull View child, int pointerId) {
                return child.getId() == R.id.dragView;
            }

            @Override
            public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
                if (left > getWidth() - child.getMeasuredWidth()) {// 右侧边界
                    left = getWidth() - child.getMeasuredWidth();
                } else if (left < 0) {// 左侧边界
                    left = 0;
                }
                return left;
            }

            @Override
            public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
                if (top > getHeight() - child.getMeasuredHeight()) {
                    top = getHeight() - child.getMeasuredHeight();
                } else if (top < 0) {
                    top = 0;
                }
                return top;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }
}
