package com.androidmodule.actionbar;

import com.androidmodule.actionbar.configs.AMActionConfig;
import com.androidmodule.actionbar.configs.AMActionGray;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * AMActionBar
 * 
 * @author LiangMaYong
 * @version 1.0
 */
public class AMActionBar extends RelativeLayout {

	public static AMActionConfig ACTION_CONFIG = new AMActionGray();

	private Animation mShowAction, mHiddenAction;
	private int anim_time = 300;
	private AMActionController controller;
	private AMActionConfig actionConfig;

	private Handler handler = new Handler();

	public AMActionConfig getConfig() {
		return actionConfig;
	}

	private void show(final Runnable runnable) {
		if (getVisibility() == View.GONE) {
			long duration = anim_time;
			if (getConfig().getActionBarShowAnim() > 0) {
				Animation animation = AnimationUtils.loadAnimation(getContext(), getConfig().getActionBarShowAnim());
				startAnimation(animation);
				duration = animation.getDuration();
			} else if (mShowAction != null) {
				startAnimation(mShowAction);
			}
			handler.postDelayed(new Runnable() {
				public void run() {
					setVisibility(View.VISIBLE);
					if (runnable != null) {
						runnable.run();
					}
				}
			}, duration);
		} else {
			if (runnable != null) {
				runnable.run();
			}
		}
	}

	/**
	 * hide
	 * 
	 * @param runnable
	 */
	private void hide(final Runnable runnable) {
		if (getVisibility() == View.VISIBLE) {
			long duration = anim_time;
			if (getConfig().getActionBarHideAnim() > 0) {
				Animation animation = AnimationUtils.loadAnimation(getContext(), getConfig().getActionBarHideAnim());
				startAnimation(animation);
				duration = animation.getDuration();
			} else if (mHiddenAction != null) {
				startAnimation(mHiddenAction);
			}
			handler.postDelayed(new Runnable() {
				public void run() {
					setVisibility(View.GONE);
					if (runnable != null) {
						runnable.run();
					}
				}
			}, duration);
		} else {
			if (runnable != null) {
				runnable.run();
			}
		}
	}

	/**
	 * show view
	 */
	public void show() {
		show(null);
	}

	/**
	 * hide view
	 */
	public void hide() {
		hide(null);
	}

	public AMActionBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	public AMActionBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public AMActionBar(Context context) {
		super(context);
		initView();
	}

	private View actionItem(String tag) {
		AMActionView textView = new AMActionView(getContext());
		int padding = dip2px(getContext(), actionConfig.getActionItemPadding());
		textView.setPadding(padding, 0, padding, 0);
		textView.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));

		textView.setBackgroundColor(0x00ffffff);
		textView.setEnabled(false);
		textView.setSingleLine();
		textView.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
		textView.setText("");
		textView.setTag(tag);
		textView.setTextSize(actionConfig.getActionItemSize());
		textView.setTextColor(actionConfig.getActionItemColor(false), actionConfig.getActionItemColor(true));
		textView.setGravity(Gravity.CENTER);
		textView.setMinWidth(actionConfig.getActionHeightPx());
		textView.setMaxWidth(getScreenWidth(getContext()) - 3 * actionConfig.getActionHeightPx());
		return textView;
	}

	private View titleItem(String tag) {
		TextView textView = new TextView(getContext());
		textView.setSingleLine();
		textView.setText("AndroidModule");
		textView.setTag(tag);
		textView.setTextSize(actionConfig.getTitleSize());
		textView.setTextColor(actionConfig.getTitleColor());
		textView.setGravity(Gravity.CENTER);
		MarginLayoutParams marginLayoutParams = new MarginLayoutParams(
				getScreenWidth(getContext()) - 2 * actionConfig.getActionHeightPx(), LayoutParams.WRAP_CONTENT);
		textView.setLayoutParams(marginLayoutParams);
		textView.setPadding(dip2px(getContext(), actionConfig.getActionItemPadding()), 0,
				dip2px(getContext(), actionConfig.getActionItemPadding()), 0);
		return textView;
	}

	private View subTitleItem(String tag) {
		TextView textView = new TextView(getContext());
		textView.setText("sub title");
		textView.setSingleLine();
		textView.setTag(tag);
		textView.setTextSize(actionConfig.getSubTitleSize());
		textView.setTextColor(actionConfig.getSubTitleColor());
		textView.setGravity(Gravity.CENTER);
		textView.setPadding(dip2px(getContext(), actionConfig.getActionItemPadding()), 0,
				dip2px(getContext(), actionConfig.getActionItemPadding()), 0);
		MarginLayoutParams marginLayoutParams = new MarginLayoutParams(
				getScreenWidth(getContext()) - 2 * actionConfig.getActionHeightPx(), LayoutParams.WRAP_CONTENT);
		marginLayoutParams.topMargin = 0;
		marginLayoutParams.bottomMargin = 0;
		textView.setLayoutParams(marginLayoutParams);
		return textView;
	}

	private void initView() {
		if (ACTION_CONFIG != null) {
			actionConfig = ACTION_CONFIG;
			actionConfig.attachActionBar(this);
		} else {
			actionConfig = new AMActionGray();
			actionConfig.attachActionBar(this);
		}
		mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
		mHiddenAction.setDuration(anim_time);
		mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		mShowAction.setDuration(anim_time);

		setBackgroundColor(actionConfig.getBackgroundColor());
		// left
		LinearLayout layoutLeft = new LinearLayout(getContext());
		LayoutParams leftParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		leftParams.addRule(RelativeLayout.CENTER_VERTICAL);
		layoutLeft.setLayoutParams(leftParams);
		layoutLeft.setOrientation(LinearLayout.HORIZONTAL);
		for (int i = 0; i < 4; i++) {
			layoutLeft.addView(actionItem("L" + i));
		}
		addView(layoutLeft);

		// right
		LinearLayout layoutRight = new LinearLayout(getContext());
		LayoutParams rightParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		rightParams.addRule(RelativeLayout.CENTER_VERTICAL);
		layoutRight.setLayoutParams(rightParams);
		layoutRight.setOrientation(LinearLayout.HORIZONTAL);
		for (int i = 0; i < 4; i++) {
			layoutRight.addView(actionItem("R" + (3 - i)));
		}
		addView(layoutRight);
		// center
		LinearLayout layoutCenter = new LinearLayout(getContext());
		layoutCenter.setGravity(Gravity.CENTER);
		LayoutParams centerParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		layoutCenter.setOrientation(LinearLayout.VERTICAL);
		layoutCenter.setLayoutParams(centerParams);
		layoutCenter.addView(titleItem("C0"));
		layoutCenter.addView(subTitleItem("C1"));
		addView(layoutCenter);

		View lineView = new View(getContext());
		lineView.setTag("LINE");
		lineView.setBackgroundColor(actionConfig.getLineColor());
		LayoutParams lineParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				dip2px(getContext(), actionConfig.getLineHeight()));
		lineParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		lineView.setLayoutParams(lineParams);
		addView(lineView);

		AMActionProgressBar progressBar = new AMActionProgressBar(getContext(), actionConfig.getProgressColor());
		progressBar.setTag("P");
		LayoutParams progressParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				dip2px(getContext(), actionConfig.getProgressHeight()));
		progressParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		progressBar.setLayoutParams(progressParams);
		progressBar.setProgress(0);
		addView(progressBar);

		// head
		RelativeLayout headLayout = new RelativeLayout(getContext());
		headLayout.setTag("HEAD");
		LayoutParams headParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		headLayout.setLayoutParams(headParams);
		addView(headLayout);

		goneAllView();
		controller = new AMActionController(this);
		setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, actionConfig.getActionHeightPx()));
	}

	public void goneByTag(String tag) {
		if (findViewWithTag(tag) != null) {
			findViewWithTag(tag).setVisibility(GONE);
		}
	}

	public void visibleByTag(String tag) {
		if (findViewWithTag(tag) != null) {
			findViewWithTag(tag).setVisibility(VISIBLE);
		}
	}

	public void goneAllView() {
		goneByTag("C0");
		goneByTag("C1");
		goneByTag("L0");
		goneByTag("L1");
		goneByTag("L2");
		goneByTag("L3");
		goneByTag("R0");
		goneByTag("R1");
		goneByTag("R2");
		goneByTag("R3");
	}

	public void setActionConfig(AMActionConfig actionConfig) {
		if (actionConfig == null) {
			return;
		}
		this.actionConfig = actionConfig;
		actionConfig.attachActionBar(this);
		setBackgroundColor(actionConfig.getBackgroundColor());
		for (int i = 0; i < 4; i++) {
			AMActionView textView = (AMActionView) findViewWithTag("R" + i);
			textView.setTextSize(actionConfig.getActionItemSize());
			textView.setTextColor(actionConfig.getActionItemColor(false), actionConfig.getActionItemColor(true));
			textView.setGravity(Gravity.CENTER);
			textView.setBackgroundColor(0x00ffffff);
			textView.setPadding(dip2px(getContext(), actionConfig.getActionItemPadding()), 0,
					dip2px(getContext(), actionConfig.getActionItemPadding()), 0);
		}
		for (int i = 0; i < 4; i++) {
			AMActionView textView = (AMActionView) findViewWithTag("L" + i);
			textView.setTextSize(actionConfig.getActionItemSize());
			textView.setTextColor(actionConfig.getActionItemColor(false), actionConfig.getActionItemColor(true));
			textView.setGravity(Gravity.CENTER);
			textView.setBackgroundColor(0x00ffffff);
			textView.setPadding(dip2px(getContext(), actionConfig.getActionItemPadding()), 0,
					dip2px(getContext(), actionConfig.getActionItemPadding()), 0);
		}

		TextView title = (TextView) findViewWithTag("C0");
		title.setTextSize(actionConfig.getTitleSize());
		title.setTextColor(actionConfig.getTitleColor());
		title.setPadding(dip2px(getContext(), actionConfig.getActionItemPadding()), 0,
				dip2px(getContext(), actionConfig.getActionItemPadding()), 0);

		TextView subTitle = (TextView) findViewWithTag("C1");
		subTitle.setTextSize(actionConfig.getSubTitleSize());
		subTitle.setTextColor(actionConfig.getSubTitleColor());
		subTitle.setPadding(dip2px(getContext(), actionConfig.getActionItemPadding()), 0,
				dip2px(getContext(), actionConfig.getActionItemPadding()), 0);

		AMActionProgressBar progressBar = (AMActionProgressBar) findViewWithTag("P");
		progressBar.color(actionConfig.getProgressColor());

		setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, actionConfig.getActionHeightPx()));
	}

	private int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	private int getScreenWidth(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}

	/**
	 * get controller
	 * 
	 * @return LActionController
	 */
	public AMActionController getController() {
		return controller;
	}

	public class AMActionProgressBar extends ProgressBar {

		private int color = 0xffff881a;

		public AMActionProgressBar(Context context) {
			super(context, null, android.R.attr.progressBarStyleHorizontal);
			initViews();
		}

		public AMActionProgressBar(Context context, int color) {
			super(context, null, android.R.attr.progressBarStyleHorizontal);
			this.color = color;
			initViews();
		}

		@SuppressLint("RtlHardcoded")
		private void initViews() {
			ShapeDrawable shapeDrawable = new ShapeDrawable(new RectShape());
			shapeDrawable.getPaint().setColor(color);
			ClipDrawable progressDrawable = new ClipDrawable(shapeDrawable, Gravity.LEFT, ClipDrawable.HORIZONTAL);
			setIndeterminate(false);
			setMax(100);
			setProgressDrawable(progressDrawable);
			setBackgroundColor(0x00000000);
		}

		@Override
		public synchronized void setProgress(int progress) {
			super.setProgress(progress);
		}

		@SuppressLint("RtlHardcoded")
		public AMActionProgressBar color(int color) {
			if (this.color == color) {
				return this;
			}
			this.color = color;
			ShapeDrawable shapeDrawable = new ShapeDrawable(new RectShape());
			shapeDrawable.getPaint().setColor(color);
			ClipDrawable progressDrawable = new ClipDrawable(shapeDrawable, Gravity.LEFT, ClipDrawable.HORIZONTAL);
			setProgressDrawable(progressDrawable);
			int progress = getProgress();
			setProgress(0);
			setProgress(progress);
			return this;
		}

		public synchronized AMActionProgressBar seekTo(int progress) {
			super.setProgress(progress);
			return this;
		}

		public synchronized AMActionProgressBar add(int progress) {
			super.setProgress(getProgress() + progress);
			return this;
		}

		public void hide() {
			setVisibility(View.GONE);
		}

		public void show() {
			setVisibility(View.VISIBLE);
		}
	}

}