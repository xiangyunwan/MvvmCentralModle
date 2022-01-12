package com.mobile.centaur.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

/**
 * 原点
 * */
public class SimpleCircle extends View {
	
	private int mWidth;
	private int mHeigth;
	private int mRadius;
	private Point mPointCenter = new Point();
	private Paint mPaint;
	
	private int mColorRes = Color.parseColor("#999999");


	public SimpleCircle(Context context) {
		this(context, null);
	}

	public SimpleCircle(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SimpleCircle(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		 init(attrs);
	}



	public void setmColorRes(String mColorRes,float alph) {
		if(TextUtils.isEmpty(mColorRes))
			return ;
		mPaint.setColor(Color.parseColor(mColorRes))  ;
		mPaint.setAlpha((int)(alph * 255));
		invalidate();
	}
	

	public void init( AttributeSet attrs )
	{
		mPaint = new Paint();
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setAntiAlias(true);
		mPaint.setColor(mColorRes);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		mWidth = MeasureSpec.getSize(widthMeasureSpec);
		mHeigth = MeasureSpec.getSize(heightMeasureSpec);

		mRadius = mWidth / 2;
		mPointCenter.x = mWidth / 2;
		mPointCenter.y = mHeigth / 2;

		final float left = 0;
		final float top = 0;
		final float right = left + mWidth;
		final float bottom = top + mHeigth;
	}
	
	
	/*
	 * 
	 * */
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		 canvas.drawCircle(mPointCenter.x, mPointCenter.y, mRadius ,mPaint);
	}

	
	
}
