package com.androidmodule.base;

import java.lang.reflect.Field;

import com.androidmodule.AMConstant;
import com.androidmodule.actionbar.AMActionBar;
import com.androidmodule.actionbar.AMActionController;
import com.androidmodule.actionbar.configs.AMActionConfig;
import com.androidmodule.base.stack.AMStackManager;
import com.androidmodule.utils.AMToast;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * AMActivity
 * 
 * @author LiangMaYong
 * @version 1.0
 */
public abstract class AMActivity extends FragmentActivity {

	private AMStackManager manager;
	private int backgroundColor = 0xffeeeeee;
	private RelativeLayout rootView;
	private FrameLayout frameView;
	private AMActionBar actionBar;

	public AMStackManager getStackManager() {
		return manager;
	}

	public void initViews(View rootView) {

	}

	public boolean showAMActionBar() {
		return true;
	}

	public RelativeLayout getRootView() {
		return rootView;
	}

	protected void onSuperCreate(@Nullable Bundle savedInstanceState) {

	}

	protected void onFragmentCreateView(@Nullable AMFragment fragment, View view) {

	}

	@Override
	protected final void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		onSuperCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		rootView = new RelativeLayout(this);
		frameView = new FrameLayout(this);
		TextView textView = new TextView(this);
		frameView.addView(textView);
		frameView.setLayoutParams(
				new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		frameView.setId(AMConstant.FRAGMENT_ID);
		frameView.setBackgroundColor(backgroundColor);
		rootView.addView(frameView);
		actionBar = new AMActionBar(this);
		rootView.addView(actionBar);
		AMActionConfig config = getAMActionConfig();
		if (config != null) {
			actionBar.setActionConfig(config);
		}
		if (!showAMActionBar()) {
			actionBar.setVisibility(View.GONE);
		}
		setContentView(rootView);
		AMFragment fragment = null;
		fragment = getFristFragment();
		if (fragment != null) {
			manager = new AMStackManager(this);
			setField(fragment, "isFrist", true);
			manager.setFragment(fragment);
		} else {
			actionBar.setVisibility(View.GONE);
			textView.setTextSize(14);
			textView.setGravity(Gravity.CENTER);
			textView.setText("Frist fragment can't is NULL\nAndroidModule 1.0");
			textView.setTextColor(0xff18a28b);
		}
		initViews(rootView);
		onCreateActivity(savedInstanceState);
	}

	public AMActionConfig getAMActionConfig() {
		return null;
	}

	public AMActionController getActionController() {
		if (actionBar == null) {
			return null;
		}
		return actionBar.getController();
	}

	public AMActionBar getAMActionBar() {
		return actionBar;
	}

	/**
	 * Set the bottom of the fragment
	 *
	 * @return fragment
	 */
	protected abstract @NonNull AMFragment getFristFragment();

	/**
	 * Set page switch animation
	 *
	 * @param nextIn
	 *            The next page to enter the animation
	 * @param nextOut
	 *            The next page out of the animation
	 * @param quitIn
	 *            The current page into the animation
	 * @param quitOut
	 *            Exit animation for the current page
	 */
	public void setAnim(@AnimRes int nextIn, @AnimRes int nextOut, @AnimRes int quitIn, @AnimRes int quitOut) {
		if (manager != null) {
			manager.setAnim(nextIn, nextOut, quitIn, quitOut);
		}
	}

	/**
	 * Rewriting onCreate method
	 *
	 * @param savedInstanceState
	 *            savedInstanceState
	 */
	public void onCreateActivity(Bundle savedInstanceState) {
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (getVisibleFragment() != null) {
			boolean flag = getVisibleFragment().onTouchEvent(event);
			if (flag) {
				return true;
			} else {
				return super.onTouchEvent(event);
			}
		}
		return super.onTouchEvent(event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (getVisibleFragment() != null) {
			return getVisibleFragment().onKeyUp(keyCode, event);
		}
		return super.onKeyUp(keyCode, event);
	}

	@Override
	public final boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if (getVisibleFragment() != null) {
				boolean flag = getVisibleFragment().onKeyDown(keyCode, event);
				if (!flag) {
					if (manager != null) {
						manager.onBackPressed();
					} else {
						return super.onKeyDown(keyCode, event);
					}
				}
			} else {
				if (manager != null) {
					manager.onBackPressed();
				} else {
					return super.onKeyDown(keyCode, event);
				}
			}
			return true;
		default:
			if (getVisibleFragment() != null) {
				return getVisibleFragment().onKeyDown(keyCode, event);
			}
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * get visible fragment
	 * 
	 * @return visible fragment
	 */
	public final AMFragment getVisibleFragment() {
		if (manager != null) {
			return manager.getVisibleFragment();
		}
		return null;
	}

	private boolean setField(Object object, String fieldName, Object value) {
		Field field = null;
		Class<?> clazz = object.getClass();
		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				field = clazz.getDeclaredField(fieldName);
			} catch (Exception e) {
			}
		}
		if (field != null) {
			field.setAccessible(true);
			try {
				field.set(object, value);
				return true;
			} catch (Exception e) {
			}
		}
		return false;
	}

	public Object getField(Object object, String fieldName) {
		Class<?> clazz = object.getClass();
		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				Field field = clazz.getDeclaredField(fieldName);
				return field.get(object);
			} catch (Exception e) {
			}
		}
		return null;
	}

	/**
	 * show toast
	 * 
	 * @param text
	 *            text
	 */
	public void showToast(final String text) {
		showToast(text, 1500);
	}

	/**
	 * showToast
	 * 
	 * @param text
	 *            text
	 * @param duration
	 *            duration
	 */
	public void showToast(final String text, int duration) {
		if (duration < 500) {
			duration = 500;
		}
		AMToast.showToast(this, text, duration);
	}

}
