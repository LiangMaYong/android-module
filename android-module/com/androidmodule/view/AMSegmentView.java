package com.androidmodule.view;

import java.util.ArrayList;
import java.util.List;

import com.androidmodule.view.drawable.AMRadiusDrawable;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * AMSegmentView
 * 
 * @author LiangMaYong
 * @version 1.0
 */
public class AMSegmentView extends LinearLayout {
	private int mSelectedBackgroundColors = 0xff3399ff;
	private int mCornerRadius = 5;
	private LinearLayout contentView;
	private AMRadiusDrawable mBackgroundDrawable;
	private List<SegmentItem> items = new ArrayList<SegmentItem>();
	private OnSegmentSelectedListener segmentSelectedListener;
	private int selectedIndex = -1;

	public int getSelectedIndex() {
		return selectedIndex;
	}

	public static interface OnSegmentSelectedListener {
		void onSelected(SegmentItem item, int index);
	}

	/**
	 * setOnSegmentSelectedListener
	 * 
	 * @param segmentSelectedListener
	 */
	public void setOnSegmentSelectedListener(OnSegmentSelectedListener segmentSelectedListener) {
		this.segmentSelectedListener = segmentSelectedListener;
	}

	public AMSegmentView(Context context) {
		super(context);
		initViews();
	}

	public AMSegmentView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initViews();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public AMSegmentView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initViews();
	}

	private void initViews() {
		setOrientation(VERTICAL);
		setGravity(Gravity.CENTER);
		contentView = new LinearLayout(getContext());
		int padding = dip2px(getContext(), 1);
		contentView.setPadding(padding, padding, padding, padding);
		contentView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		initBackground();
		setItems(0, "Frist", "Two", "Three");
		addView(contentView);
	}

	@SuppressWarnings("deprecation")
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public void initBackground() {
		mBackgroundDrawable = new AMRadiusDrawable(mCornerRadius, true, mSelectedBackgroundColors);
		contentView.setOrientation(HORIZONTAL);
		if (Build.VERSION.SDK_INT < 16) {
			contentView.setBackgroundDrawable(mBackgroundDrawable);
		} else {
			contentView.setBackground(mBackgroundDrawable);
		}
	}

	public void setSegmentSizes(float selected, float unselected) {
		for (int i = 0; i < items.size(); i++) {
			items.get(i).setSelectedSize(selected);
			items.get(i).setUnSelectedSize(unselected);
		}
	}

	public void setSegmentColors(int color, int selected) {
		setSegmentStrokeColor(selected);
		setSegmentSelectedBackgroundColor(selected);
		setSegmentSelectedTextColor(color);
		setSegmentUnSelectedBackgroundColor(color);
		setSegmentUnSelectedTextColor(selected);
	}

	public void setSegmentUnSelectedBackgroundColor(int color) {
		for (int i = 0; i < items.size(); i++) {
			items.get(i).setUnselectedBackgroundColor(color);
		}
	}

	public void setSegmentUnSelectedTextColor(int color) {
		for (int i = 0; i < items.size(); i++) {
			items.get(i).setUnselectedTextColor(color);
		}
	}

	public void setSegmentStrokeColor(int color) {
		this.mSelectedBackgroundColors = color;
		initBackground();
	}

	public void setSegmentSelectedBackgroundColor(int color) {
		for (int i = 0; i < items.size(); i++) {
			items.get(i).setSelectedBackgroundColor(color);
		}
	}

	public void setSegmentSelectedTextColor(int color) {
		for (int i = 0; i < items.size(); i++) {
			items.get(i).setSelectedTextColor(color);
		}
	}

	public void setSegmentRadius(int radius) {
		this.mCornerRadius = radius;
		for (int i = 0; i < items.size(); i++) {
			items.get(i).setRadius(radius);
		}
		initBackground();
	}

	public void setItems(int index, String... text) {
		selectedIndex = -1;
		contentView.removeAllViews();
		items.removeAll(items);
		for (int i = 0; i < text.length; i++) {
			SegmentItem item = item(text[i]);
			items.add(item);
			if (i == 0) {
				item.setFrist(true);
			}
			if (i == text.length - 1) {
				LayoutParams layoutParams = (LayoutParams) item.getLayoutParams();
				layoutParams.rightMargin = 0;
				item.setEnd(true);
			}
			final int selected = i;
			item.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					selected(selected);
				}
			});
			contentView.addView(item);
		}
		selected(index);
	}

	public void selected(int index) {
		if (index >= items.size()) {
			return;
		}
		if (index < 0) {
			return;
		}
		selectedIndex = index;
		for (int i = 0; i < items.size(); i++) {
			items.get(i).selected(false);
			if (i == index) {
				items.get(i).selected(true);
				if (segmentSelectedListener != null) {
					segmentSelectedListener.onSelected(items.get(i), index);
				}
			}
		}
	}

	private SegmentItem item(String msg) {
		SegmentItem item = new SegmentItem(getContext());
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		layoutParams.weight = 1;
		layoutParams.rightMargin = 2;
		item.setLayoutParams(layoutParams);
		item.selected(false);
		item.setText(msg);
		return item;
	}

	private int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public class SegmentItem extends TextView {

		private int mSelectedBackgroundColors = 0xff3399ff;
		private int mUnSelectedBackgroundColors = 0xffffffff;
		private int mSelectedTextColors = 0xffffffff;
		private int mUnSelectedTextColors = 0xff3399ff;
		private int mCornerRadius = 5;
		private float selectSize = -1;
		private float unselectSize = -1;
		private boolean selected = false;
		private boolean isFrist = false;
		private boolean isEnd = false;

		@Override
		public boolean isSelected() {
			return selected;
		}

		public void setSelectedSize(float selectedSize) {
			this.selectSize = selectedSize;
		}

		public void setUnSelectedSize(float unselectedSize) {
			this.unselectSize = unselectedSize;
		}

		public void setFrist(boolean isFrist) {
			this.isFrist = isFrist;
			if (isFrist) {
				isEnd = false;
			}
		}

		public void setEnd(boolean isEnd) {
			this.isEnd = isEnd;
			if (isEnd) {
				isFrist = false;
			}
		}

		public void setRadius(int radius) {
			this.mCornerRadius = radius;
			selected(isSelected());
		}

		public void setSelectedTextColor(int color) {
			this.mSelectedTextColors = color;
			selected(isSelected());
		}

		public void setUnselectedTextColor(int color) {
			this.mUnSelectedTextColors = color;
			selected(isSelected());
		}

		public void setSelectedBackgroundColor(int color) {
			this.mSelectedBackgroundColors = color;
			selected(isSelected());
		}

		public void setUnselectedBackgroundColor(int color) {
			this.mUnSelectedBackgroundColors = color;
			selected(isSelected());
		}

		private AMRadiusDrawable mBackgroundDrawable;

		public SegmentItem(Context context) {
			super(context);
			initViews();
		}

		private void initViews() {
			setPadding(dip2px(getContext(), 10), dip2px(getContext(), 5), dip2px(getContext(), 10),
					dip2px(getContext(), 5));
			setGravity(Gravity.CENTER);
			selected(false);
		}

		/**
		 * set textColor
		 * 
		 * @param color
		 *            color
		 * @param pressed
		 *            pressed
		 */
		public void setTextColor(int color, int pressed) {
			int statePressed = android.R.attr.state_pressed;
			int stateFocesed = android.R.attr.state_focused;
			int[][] state = { { statePressed }, { -statePressed }, { stateFocesed }, { -stateFocesed } };
			ColorStateList colors = new ColorStateList(state, new int[] { pressed, color, pressed, color });
			setTextColor(colors);
		}

		@SuppressWarnings("deprecation")
		@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
		public void selected(boolean select) {
			this.selected = select;
			if (select) {
				if (selectSize >= 0) {
					setTextSize(selectSize);
				}
				setEnabled(false);
				mBackgroundDrawable = new AMRadiusDrawable(0, true, mSelectedBackgroundColors);
				mBackgroundDrawable.setStrokeWidth(1);
				mBackgroundDrawable.setStrokeColor(mSelectedBackgroundColors);
				if (isFrist) {
					mBackgroundDrawable.setRadiuses(mCornerRadius, 0, mCornerRadius, 0);
				} else if (isEnd) {
					mBackgroundDrawable.setRadiuses(0, mCornerRadius, 0, mCornerRadius);
				}
				setTextColor(mSelectedBackgroundColors);
				if (Build.VERSION.SDK_INT < 16) {
					setBackgroundDrawable(mBackgroundDrawable);
				} else {
					setBackground(mBackgroundDrawable);
				}
				setTextColor(mSelectedTextColors, mSelectedTextColors - 0xA1000000);
			} else {
				if (unselectSize >= 0) {
					setTextSize(unselectSize);
				}
				setEnabled(true);
				mBackgroundDrawable = new AMRadiusDrawable(0, true, mUnSelectedBackgroundColors);
				setTextColor(mSelectedBackgroundColors);
				mBackgroundDrawable.setStrokeWidth(1);
				if (isFrist) {
					mBackgroundDrawable.setRadiuses(mCornerRadius, 0, mCornerRadius, 0);
				} else if (isEnd) {
					mBackgroundDrawable.setRadiuses(0, mCornerRadius, 0, mCornerRadius);
				}
				mBackgroundDrawable.setStrokeColor(mSelectedBackgroundColors);
				if (Build.VERSION.SDK_INT < 16) {
					setBackgroundDrawable(mBackgroundDrawable);
				} else {
					setBackground(mBackgroundDrawable);
				}
				setTextColor(mUnSelectedTextColors, mUnSelectedTextColors - 0xA1000000);
			}
		}
	}
}
