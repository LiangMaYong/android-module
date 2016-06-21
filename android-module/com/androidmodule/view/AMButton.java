package com.androidmodule.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * AMButton
 * 
 * @author LiangMaYong
 * @version 1.0
 */
public class AMButton extends Button {

	public static final int SHAPE_TYPE_ROUND = 0;
	public static final int SHAPE_TYPE_RECTANGLE = 1;

	protected int mWidth;
	protected int mHeight;
	protected Paint mBackgroundPaint;
	protected int mShapeType;
	protected int mRadius;

	private int COVER_ALPHA = 48;
	private Paint mPressedPaint;
	private int mPressedColor;

	public AMButton(Context context) {
		this(context, null);
	}

	public AMButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public AMButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public AMButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init(context);
	}

	private int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	protected void init(final Context context) {
		if (isInEditMode())
			return;
		mShapeType = SHAPE_TYPE_RECTANGLE;
		mRadius = dip2px(getContext(), 2);
		int unpressedColor = Color.TRANSPARENT;

		mBackgroundPaint = new Paint();
		mBackgroundPaint.setStyle(Paint.Style.FILL);
		mBackgroundPaint.setAlpha(Color.alpha(unpressedColor));
		mBackgroundPaint.setColor(unpressedColor);
		mBackgroundPaint.setAntiAlias(true);

		this.setWillNotDraw(false);
		this.setDrawingCacheEnabled(true);
		this.setClickable(true);
		this.eraseOriginalBackgroundColor(unpressedColor);

		mPressedColor = 0x9c000000;

		mPressedPaint = new Paint();
		mPressedPaint.setStyle(Paint.Style.FILL);
		mPressedPaint.setColor(mPressedColor);
		mPressedPaint.setAlpha(0);
		mPressedPaint.setAntiAlias(true);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mWidth = w;
		mHeight = h;
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		if (mBackgroundPaint == null) {
			super.onDraw(canvas);
			return;
		}
		if (mShapeType == SHAPE_TYPE_ROUND) {
			canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2, mBackgroundPaint);
		} else {
			RectF rectF = new RectF();
			rectF.set(0, 0, mWidth, mHeight);
			canvas.drawRoundRect(rectF, mRadius, mRadius, mBackgroundPaint);
		}
		super.onDraw(canvas);
		if (mShapeType == SHAPE_TYPE_ROUND) {
			canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2.1038f, mPressedPaint);
		} else {
			RectF rectF = new RectF();
			rectF.set(0, 0, mWidth, mHeight);
			canvas.drawRoundRect(rectF, mRadius, mRadius, mPressedPaint);
		}
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mPressedPaint.setAlpha(COVER_ALPHA);
			invalidate();
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			mPressedPaint.setAlpha(0);
			invalidate();
			break;
		}
		return super.onTouchEvent(event);
	}

	protected void eraseOriginalBackgroundColor(int color) {
		if (color != Color.TRANSPARENT) {
			this.setBackgroundColor(Color.TRANSPARENT);
		}
	}

	/**
	 * Set the unpressed color.
	 *
	 * @param color
	 *            the color of the background
	 */
	public void setUnpressedColor(int color) {
		mBackgroundPaint.setAlpha(Color.alpha(color));
		mBackgroundPaint.setColor(color);
		eraseOriginalBackgroundColor(color);
		invalidate();
	}

	public int getShapeType() {
		return mShapeType;
	}

	/**
	 * Set the shape type.
	 *
	 * @param shapeType
	 *            SHAPE_TYPE_ROUND or SHAPE_TYPE_RECTANGLE
	 */
	public void setShapeType(int shapeType) {
		if (shapeType != SHAPE_TYPE_ROUND) {
			shapeType = SHAPE_TYPE_RECTANGLE;
		}
		mShapeType = shapeType;
		invalidate();
	}

	public int getRadius() {
		return mRadius;
	}

	/**
	 * Set the radius if the shape type is SHAPE_TYPE_ROUND.
	 *
	 * @param radius
	 *            by px.
	 */
	public void setRadius(int radius) {
		mRadius = radius;
		invalidate();
	}

	public int getPressedColor() {
		return mPressedColor;
	}

	/**
	 * Set the pressed color.
	 *
	 * @param pressedColor
	 *            pressed color
	 */
	public void setPressedColor(int pressedColor) {
		mPressedPaint.setColor(mPressedColor);
		invalidate();
	}
}
