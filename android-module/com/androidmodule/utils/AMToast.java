package com.androidmodule.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * AMToast
 * 
 * @author LiangMaYong
 * @version 1.0
 */
@SuppressLint("InflateParams")
public class AMToast {

	private static Toast mToast;
	private static Handler mHandler = new Handler();
	private static Runnable run = new Runnable() {
		@Override
		public void run() {
			mToast.cancel();
		}
	};

	/**
	 * show toast
	 * 
	 * @param context
	 *            context
	 * @param text
	 *            text
	 * @param duration
	 *            duration
	 */
	@SuppressWarnings("deprecation")
	public static void showToast(Context context, String text, int duration) {
		mHandler.removeCallbacks(run);
		if (mToast == null) {
			mToast = new Toast(context);
		}
		LinearLayout linearLayout = new LinearLayout(context);
		linearLayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		linearLayout.setGravity(Gravity.CENTER);
		linearLayout.setPadding(20, 10, 20, 10);
		linearLayout.setBackgroundDrawable(new RoundColorDrawable(10, 0x50333333));

		TextView tv = new TextView(context);
		linearLayout.addView(tv);
		tv.setTextColor(0xffffffff);
		tv.setTextSize(14);
		tv.setText(text);
		mToast.setView(linearLayout);
		mHandler.postDelayed(run, duration);
		mToast.show();
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