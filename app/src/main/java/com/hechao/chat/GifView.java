package com.hechao.chat;

import java.io.InputStream;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;


public class GifView extends View {

    private Resources resources;
    private Movie mMovie;
    private long mMovieStart;
    private float ratioWidth;
    private float ratioHeight;

    public GifView(Context context) {
        this(context, null);
    }

    public GifView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GifView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        resources = context.getResources();

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.GifView);
        int resourceId = ta.getResourceId(R.styleable.GifView_src, -1);
        setGifResource(resourceId);
        ta.recycle();
    }

    public void setGifResource(int resourceId) {
        if (resourceId == -1) {
            return;
        }
        InputStream is = resources.openRawResource(resourceId);
        mMovie = Movie.decodeStream(is);
        requestLayout();
    }

    public void setGifStream(InputStream is) {
        mMovie = Movie.decodeStream(is);
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (mMovie != null) {
            int w = mMovie.width();
            int h = mMovie.height();
            if (w <= 0)
                w = 1;
            if (h <= 0)
                h = 1;

            int pleft = getPaddingLeft();
            int pright = getPaddingRight();
            int ptop = getPaddingTop();
            int pbottom = getPaddingBottom();

            int widthSize;
            int heightSize;

            w += pleft + pright;
            h += ptop + pbottom;

            w = Math.max(w, getSuggestedMinimumWidth());
            h = Math.max(h, getSuggestedMinimumHeight());

            widthSize = resolveSizeAndState(w, widthMeasureSpec, 0);
            heightSize = resolveSizeAndState(h, heightMeasureSpec, 0);

            ratioWidth = (float) widthSize / w;
            ratioHeight = (float) heightSize / h;

            setMeasuredDimension(widthSize, heightSize);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        long now = android.os.SystemClock.uptimeMillis();
        if (mMovieStart == 0) {   // first time
            mMovieStart = now;
        }
        if (mMovie != null) {
            int dur = mMovie.duration();
            if (dur == 0) {
                dur = 1000;
            }
            int relTime = (int) ((now - mMovieStart) % dur);
            mMovie.setTime(relTime);
//            mMovie.draw(canvas, getWidth() - mMovie.width(),
//                        getHeight() - mMovie.height());

            float scale = Math.min(ratioWidth, ratioHeight);
            canvas.scale(scale, scale);
            mMovie.draw(canvas, 0, 0);

            invalidate();
        }
    }

}
