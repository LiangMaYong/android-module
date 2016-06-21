package com.androidmodule.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;

/**
 * AMTagImageView
 * 
 * @author LiangMaYong
 * @version 1.0
 */
public class AMTagImageView extends AMImageView {

	public static final String TAG = "IBTagImageView";

	public static final byte LEFT_TOP = 0x00;

	public static final byte RIGHT_TOP = 0x01;

	public static final byte LEFT_BOTTOM = 0x02;

	public static final byte RIGHT_BOTTOM = 0x03;

	private static final float THE_SQUARE_ROOT_OF_2 = (float) Math.sqrt(2);

	private static final int DEFAULT_TAG_WIDTH = 20;

	private static final int DEFAULT_CORNER_DISTANCE = 20;

	private static final int DEFAULT_TAG_BACKGROUND_COLOR = 0x9F27CDC0;

	private static final int DEFAULT_TAG_TEXT_SIZE = 15;

	private static final int DEFAULT_TAG_TEXT_COLOR = 0xFFFFFFFF;

	private float mCornerDistance;

	private float mTagWidth;

	private int mTagBackgroundColor;

	private Path mPath;

	private Paint mPaint;

	private String mTagText;

	private int mTagTextSize;

	private Paint mTextPaint;

	private Rect mTagTextBound;

	private int mTagTextColor;

	private float mDensity;

	private int mTagOrientation;

	private MyPoint startPoint;

	private MyPoint endPoint;

	private boolean mTagEnable;

	public AMTagImageView(Context context) {
		this(context, null);
		initViews(context);
	}

	public AMTagImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		initViews(context);
	}

	public AMTagImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initViews(context);
	}

	private void initViews(Context context) {
		mDensity = context.getResources().getDisplayMetrics().density;
		mTagOrientation = 0;
		mTagWidth = dip2px(DEFAULT_TAG_WIDTH);
		mCornerDistance = dip2px(DEFAULT_CORNER_DISTANCE);
		mTagBackgroundColor = DEFAULT_TAG_BACKGROUND_COLOR;
		mTagText = "";
		mTagTextSize = dip2px(DEFAULT_TAG_TEXT_SIZE);
		mTagTextColor = DEFAULT_TAG_TEXT_COLOR;
		mTagEnable = true;
		if (TextUtils.isEmpty(mTagText))
			mTagText = "";
		mPaint = new Paint();
		mPath = new Path();
		mTextPaint = new Paint();
		mTagTextBound = new Rect();
		startPoint = new MyPoint();
		endPoint = new MyPoint();
	}

	/**
	 *
	 * @param textSize
	 *            unit:dip
	 */
	public void setTagTextSize(int textSize) {
		this.mTagTextSize = dip2px(textSize);
		invalidate();
	}

	public int getTagTextSize() {
		return mTagTextSize;
	}

	/**
	 *
	 * @param cornerDistance
	 *            unit:dip
	 */
	public void setTagCornerDistance(int cornerDistance) {
		if (this.mCornerDistance == cornerDistance)
			return;
		this.mCornerDistance = dip2px(cornerDistance);
		invalidate();
	}

	/**
	 *
	 * @return unit:dip
	 */
	public int getTagCornerDistance() {
		return px2dip(this.mCornerDistance);
	}

	public int getTagTextColor() {
		return this.mTagTextColor;
	}

	public void setTagTextColor(int tagTextColor) {
		if (this.mTagTextColor == tagTextColor)
			return;
		this.mTagTextColor = tagTextColor;
		invalidate();
	}

	public String getTagText() {
		return this.mTagText;
	}

	public void setTagText(String tagText) {
		if (tagText.equals(this.mTagText))
			return;
		this.mTagText = tagText;
		invalidate();
	}

	public void setTagBackgroundColor(int tagBackgroundColor) {
		if (this.mTagBackgroundColor == tagBackgroundColor)
			return;
		this.mTagBackgroundColor = tagBackgroundColor;
		invalidate();
	}

	public int getTagBackgroundColor() {
		return this.mTagBackgroundColor;
	}

	/**
	 * @return unit:dip
	 */
	public int getTagWidth() {
		return px2dip(this.mTagWidth);
	}

	/**
	 *
	 * @param tagWidth
	 *            unit:dip
	 */
	public void setTagWidth(int tagWidth) {
		this.mTagWidth = dip2px(tagWidth);
		invalidate();
	}

	/**
	 * @return 0 : left_top 1 : right_top 2 : left_bottom 3 : right_bottom
	 */
	public int getTagOrientation() {
		return mTagOrientation;
	}

	/**
	 *
	 * @param tagOrientation
	 *            {@link #LEFT_TOP} or {@link #LEFT_BOTTOM} or
	 *            {@link #RIGHT_TOP} or {@link #RIGHT_BOTTOM}
	 */
	public void setTagOrientation(int tagOrientation) {
		if (tagOrientation == this.mTagOrientation)
			return;
		this.mTagOrientation = tagOrientation;
		invalidate();
	}

	public void setTagEnable(boolean tagEnable) {
		if (this.mTagEnable == tagEnable)
			return;
		this.mTagEnable = tagEnable;
		invalidate();
	}

	public boolean getTagEnable() {
		return this.mTagEnable;
	}

	@Override
	protected void onDraw(Canvas mCanvas) {
		super.onDraw(mCanvas);
		if (mTagWidth > 0 && mTagEnable) {
			float rDistance = mCornerDistance + mTagWidth / 2;
			chooseTagOrientation(rDistance);
			mTextPaint.setTextSize(mTagTextSize);
			mTextPaint.getTextBounds(mTagText, 0, mTagText.length(), mTagTextBound);
			mPaint.setDither(true);
			mPaint.setAntiAlias(true);
			mPaint.setColor(mTagBackgroundColor);
			mPaint.setStyle(Paint.Style.STROKE);
			mPaint.setStrokeJoin(Paint.Join.ROUND);
			mPaint.setStrokeCap(Paint.Cap.SQUARE);
			mPaint.setStrokeWidth(mTagWidth);
			mPath.reset();
			mPath.moveTo(startPoint.x, startPoint.y);
			mPath.lineTo(endPoint.x, endPoint.y);
			mCanvas.drawPath(mPath, mPaint);
			mTextPaint.setColor(mTagTextColor);
			mTextPaint.setTextSize(mTagTextSize);
			mTextPaint.setAntiAlias(true);
			// 斜边长度
			float hypotenuse = THE_SQUARE_ROOT_OF_2 * rDistance;
			mCanvas.drawTextOnPath(mTagText, mPath, hypotenuse / 2 - mTagTextBound.width() / 2,
					mTagTextBound.height() / 2, mTextPaint);
		}
	}

	private void chooseTagOrientation(float rDistance) {
		int mWidth = getMeasuredWidth();
		int mHeight = getMeasuredHeight();
		switch (mTagOrientation) {
		case 0:
			startPoint.x = 0;
			startPoint.y = rDistance;
			endPoint.x = rDistance;
			endPoint.y = 0;
			break;
		case 1:
			startPoint.x = mWidth - rDistance;
			startPoint.y = 0;
			endPoint.x = mWidth;
			endPoint.y = rDistance;
			break;
		case 2:
			startPoint.x = 0;
			startPoint.y = mHeight - rDistance;
			endPoint.x = rDistance;
			endPoint.y = mHeight;
			break;
		case 3:
			startPoint.x = mWidth - rDistance;
			startPoint.y = mHeight;
			endPoint.x = mWidth;
			endPoint.y = mHeight - rDistance;
			break;
		}
	}

	private int dip2px(int dip) {
		return (int) (mDensity * dip + 0.5f);
	}

	private int px2dip(float px) {
		return (int) (px / mDensity + 0.5f);
	}

	static class MyPoint {
		float x;
		float y;
	}
}
