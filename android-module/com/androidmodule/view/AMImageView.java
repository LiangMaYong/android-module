package com.androidmodule.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * AMImageView
 * 
 * @author LiangMaYong
 * @version 1.0
 */
public class AMImageView extends ImageView {

	public static final int SHAPE_TYPE_ROUND = 0;
	public static final int SHAPE_TYPE_RECTANGLE = 1;

	private int mWidth;
	private int mHeight;
	private int mPaintAlpha = 48;

	private int mPressedColor;
	private Paint mPaint;
	private int mShapeType;
	private int mRadius;

	public AMImageView(Context context) {
		super(context);
	}

	public AMImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public AMImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	private void init(final Context context, final AttributeSet attrs) {
		if (isInEditMode())
			return;
		mPressedColor = 0x9c000000;
		mShapeType = SHAPE_TYPE_RECTANGLE;
		mRadius = dip2px(context, 2);
		mPaint = new Paint();
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setColor(mPressedColor);
		this.setWillNotDraw(false);
		mPaint.setAlpha(0);
		mPaint.setAntiAlias(true);
		this.setDrawingCacheEnabled(true);
		this.setClickable(true);
	}

	private int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
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
		super.onDraw(canvas);
		if (mPaint == null)
			return;
		if (mShapeType == SHAPE_TYPE_ROUND) {
			canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2.1038f, mPaint);
		} else {
			RectF rectF = new RectF();
			rectF.set(0, 0, mWidth, mHeight);
			canvas.drawRoundRect(rectF, mRadius, mRadius, mPaint);
		}
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mPaint.setAlpha(mPaintAlpha);
			invalidate();
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			mPaint.setAlpha(0);
			invalidate();
			break;
		}
		return super.onTouchEvent(event);
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
		if (pressedColor != this.mPressedColor) {
			mPaint.setColor(mPressedColor);
			invalidate();
		}
	}

	public int getPressedAlpha() {
		return mPaintAlpha;
	}

	public void setPressedAlpha(int pressedAlpha) {
		if (pressedAlpha != this.mPaintAlpha) {
			this.mPaintAlpha = pressedAlpha;
			invalidate();
		}
	}

	public void setRadius(int mRadius) {
		if (mRadius != this.mRadius) {
			this.mRadius = mRadius;
			invalidate();
		}
	}

	public int getShapeType() {
		return mShapeType;
	}

	public void setShapeType(int shapeType) {
		if (shapeType != SHAPE_TYPE_ROUND) {
			shapeType = SHAPE_TYPE_RECTANGLE;
		}
		if (shapeType != this.mShapeType) {
			this.mShapeType = shapeType;
			invalidate();
		}
	}
}
