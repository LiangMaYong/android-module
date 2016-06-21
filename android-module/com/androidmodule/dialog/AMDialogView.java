package com.androidmodule.dialog;

import com.androidmodule.view.AMProgressWheel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AMDialogView extends LinearLayout {

	public AMDialogView(Context context) {
		super(context);
		initView(context);
	}

	@SuppressWarnings("deprecation")
	private void initView(Context context) {
		setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		setMinimumWidth(dip2px(context, 80));
		setMinimumHeight(dip2px(context, 80));
		setGravity(Gravity.CENTER);
		setOrientation(LinearLayout.VERTICAL);
		setPadding(dip2px(context, 20), dip2px(context, 10), dip2px(context, 20), dip2px(context, 10));
		setBackgroundDrawable(new RoundColorDrawable(20, 0x99333333));

		AMProgressWheel progressBar = new AMProgressWheel(context);
		progressBar.setTag("P");
		progressBar.setBarColor(0xddffffff);
		progressBar.setRimColor(0x05eeeeee);
		progressBar.setRimWidth(5);
		progressBar.setBarWidth(5);
		int width = dip2px(context, 30);
		progressBar.setCircleRadius(width);
		progressBar.setPadding(0, dip2px(context, 10), 0, dip2px(context, 10));
		if (!progressBar.isSpinning()) {
			progressBar.spin();
		}
		addView(progressBar);

		TextView textView = new TextView(getContext());
		textView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		textView.setSingleLine();
		textView.setGravity(Gravity.CENTER);
		textView.setTextSize(15);
		textView.setTextColor(0xffffffff);
		textView.setText("Loading");
		textView.setTag("T");
		addView(textView);
	}

	private int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	private static class RoundColorDrawable extends ColorDrawable {

		private int round;

		public RoundColorDrawable(int round, int color) {
			super(color);
			this.round = round;
		}

		@Override
		public void draw(Canvas canvas) {
			super.draw(new RoundCanvas(canvas, round));
		}

		private class RoundCanvas extends Canvas {

			private int round;
			private Canvas canvas;

			public RoundCanvas(Canvas canvas, int round) {
				this.round = round;
				this.canvas = canvas;
			}

			@Override
			public void drawRect(Rect r, Paint paint) {
				RectF rectF = new RectF(r);
				canvas.drawRoundRect(rectF, round, round, paint);
			}
		}
	}
}
