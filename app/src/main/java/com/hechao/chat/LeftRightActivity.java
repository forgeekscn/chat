package com.hechao.chat;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.text.AttributedCharacterIterator;

/**
 * Created by Administrator on 2016/3/30.
 */
public class LeftRightActivity extends RelativeLayout {

    Context context;
    FrameLayout leftLayout;
    FrameLayout midLayout;
    FrameLayout rightLayout;

    public LeftRightActivity(Context c){
        super(c);
        initView(context);
    }


    public LeftRightActivity(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }


    public void initView(Context context){
        this.context=context;
        leftLayout=new FrameLayout(context);
        midLayout= new FrameLayout(context);
        rightLayout= new FrameLayout(context);

        leftLayout.setBackgroundColor(Color.BLUE);
        midLayout.setBackgroundColor(Color.GRAY);
        rightLayout.setBackgroundColor(Color.GREEN);
        addView(leftLayout);
        addView(midLayout);
        addView(rightLayout);



    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        midLayout.measure(widthMeasureSpec,heightMeasureSpec);
        int realWidth= MeasureSpec.getSize(widthMeasureSpec);
        int tempWidth=MeasureSpec.makeMeasureSpec((int) (realWidth*0.8f),MeasureSpec.EXACTLY);
        leftLayout.measure(tempWidth,heightMeasureSpec);
        rightLayout.measure(tempWidth,heightMeasureSpec);



    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        midLayout.layout(l, t, r, b);
        leftLayout.layout(l-leftLayout.getWidth(),t,r,b);
        rightLayout.layout(l+midLayout.getWidth(),t,l+midLayout.getWidth()+rightLayout.getWidth(),b);



    }
}
