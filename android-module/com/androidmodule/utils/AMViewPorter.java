package com.androidmodule.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

/**
 * AMViewPorter
 * 
 * @author LiangMaYong
 * @version 1.0
 */
public class AMViewPorter {

	/**
	 * view
	 */
	private View view;
	/**
	 * parent
	 */
	private View parent;
	/**
	 * toWidth
	 */
	private int toWidth;
	/**
	 * toHeight
	 */
	private int toHeight;

	private AMViewPorter(View view) {
		this.view = view;
		if (view == null) {
			throw new IllegalArgumentException("LViewPorter init -> View == NULL!");
		}
	}

	/**
	 * from
	 *
	 * @param view
	 * @return
	 */
	public static AMViewPorter from(View view) {
		return new AMViewPorter(view);
	}

	/**
	 * from
	 * 
	 * @param parent
	 *            parent
	 * @param id
	 *            id
	 * @return
	 */
	public static AMViewPorter from(View parent, int id) {
		return from(parent.findViewById(id));
	}

	/**
	 * from
	 * 
	 * @param activity
	 *            activity
	 * @param id
	 *            id
	 * @return
	 */
	public static AMViewPorter from(Activity activity, int id) {
		return from(activity.findViewById(id));
	}

	/**
	 * of
	 *
	 * @param parent
	 *            parent
	 * @return
	 */
	public AMViewPorter of(View parent) {
		this.parent = parent;
		return this;
	}

	/**
	 * ofScreen
	 *
	 * @return
	 */
	public AMViewPorter ofScreen() {
		parent = null;
		return this;
	}

	/**
	 * of
	 *
	 * @param activity
	 * @return
	 */
	public AMViewPorter of(Activity activity) {

		this.parent = activity.getWindow().getDecorView();

		return this;
	}

	/**
	 * ofWidth
	 *
	 * @param divCount
	 * @return
	 */
	public AMViewPorter ofWidth(int divCount) {
		if (parent != null) {
			int width = parent.getWidth();
			toWidth = width / divCount;
		} else {
			toWidth = AMScreen.getScreenWidth(view.getContext()) / 2;
		}

		return this;
	}

	/**
	 * ofHeight
	 *
	 * @param divCount
	 * @return
	 */
	public AMViewPorter ofHeight(int divCount) {
		if (parent != null) {
			int height = parent.getHeight();
			toHeight = height / divCount;
		} else {
			toHeight = AMScreen.getScreenHeight(view.getContext()) / 2;
		}
		return this;
	}

	/**
	 * divWidth
	 *
	 * @param divCount
	 * @return
	 */
	public AMViewPorter divWidth(int divCount) {
		if (toWidth != 0) {
			toWidth /= divCount;
		} else {
			toWidth = AMScreen.getScreenWidth(view.getContext()) / divCount;
		}
		return this;
	}

	/**
	 * divHeight
	 *
	 * @param divCount
	 * @return
	 */
	public AMViewPorter divHeight(int divCount) {
		if (toHeight != 0) {
			toHeight /= divCount;
		} else {
			toHeight = AMScreen.getScreenHeight(view.getContext()) / divCount;
		}
		return this;
	}

	public AMViewPorter div(int divCount) {
		divWidth(divCount);
		divHeight(divCount);
		return this;
	}

	/**
	 * castWidth
	 *
	 * @param toWidth
	 *            toWidth
	 * @return
	 */
	public AMViewPorter castWidth(int toWidth) {
		this.toWidth = toWidth;
		return this;
	}

	/**
	 * castHeight
	 *
	 * @param toHeight
	 *            toHeight
	 * @return
	 */
	public AMViewPorter castHeight(int toHeight) {
		this.toHeight = toHeight;
		return this;
	}

	/**
	 * fillWidth
	 *
	 * @return
	 */
	public AMViewPorter fillWidth() {
		if (parent != null) {
			toWidth = parent.getWidth();
			return this;
		} else {
			View viewParent = (View) view.getParent();
			if (viewParent != null) {
				toWidth = viewParent.getWidth();
			} else {
				toWidth = AMScreen.getScreenWidth(view.getContext());
			}
		}

		return this;
	}

	/**
	 * fillHeight
	 *
	 * @return
	 */
	public AMViewPorter fillHeight() {
		if (parent != null) {
			toWidth = parent.getWidth();
			return this;
		} else {
			View viewParent = (View) view.getParent();
			if (viewParent != null) {
				toWidth = viewParent.getHeight();
			} else {
				toWidth = AMScreen.getScreenHeight(view.getContext());
			}
		}
		return this;
	}

	/**
	 * fillWidthAndHeight
	 * 
	 * @return
	 */
	public AMViewPorter fillWidthAndHeight() {
		fillWidth();
		fillHeight();
		return this;
	}

	/**
	 * alpha
	 *
	 * @param alpha
	 * @return
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public AMViewPorter alpha(float alpha) {
		view.setAlpha(alpha);
		return this;
	}

	/**
	 * commit
	 */
	public void commit() {
		ViewGroup.LayoutParams params = view.getLayoutParams();

		if (toWidth != 0) {
			params.width = toWidth;
		}
		if (toHeight != 0) {
			params.height = toHeight;
		}

		view.setLayoutParams(params);
		view.invalidate();

	}

	/**
	 * sameAs
	 *
	 * @param another
	 */
	public AMViewPorter sameAs(View another) {
		toWidth = another.getWidth();
		toHeight = another.getHeight();
		return this;
	}

}
