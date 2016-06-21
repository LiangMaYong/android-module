package com.androidmodule.base.stack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.androidmodule.AMConstant;
import com.androidmodule.base.AMFragment;
import com.androidmodule.base.interfaces.ICloseFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;

/**
 * StackManager
 * 
 * @author LiangMaYong
 * @version 1.0
 */
public class AMStackManager implements ICloseFragment {

	private AMFragmentStack stack;
	private final FragmentActivity context;
	private long CLICK_SPACE = 200;
	private long currentTime;
	private int currentMode;
	private int nextIn;
	private int nextOut;
	private Animation quit_In, quit_Out;
	private Animation next_In, next_Out;
	private int dialog_in;
	private int dialog_out;
	public static final int STANDARD = 0x11;
	public static final int SINGLE_TOP = 0x12;
	public static final int SINGLE_TASK = 0x13;
	public static final int SINGLE_INSTANCE = 0x14;
	public static final int KEEP_CURRENT = 0x15;
	private int dialogCount = 0;
	private List<Fragment> dialogFragments = new ArrayList<Fragment>();
	@SuppressLint("UseSparseArrays")
	private Map<Integer, Bundle> resultMap = new HashMap<Integer, Bundle>();

	public void setResult(int resultCode, Bundle data) {
		synchronized (resultMap) {
			resultMap.put(resultCode, data);
		}
	}

	public Bundle getResult(int resultCode) {
		synchronized (resultMap) {
			if (!resultMap.isEmpty() && resultMap.containsKey(resultCode)) {
				return resultMap.get(resultCode);
			}
		}
		return null;
	}

	public boolean isShowDialog() {
		return dialogCount > 0;
	}

	/**
	 * Set the time to click to Prevent repeated clicks,default 500ms
	 *
	 * @param CLICK_SPACE
	 *            Repeat click time
	 */
	public void setClickSpace(long CLICK_SPACE) {
		this.CLICK_SPACE = CLICK_SPACE;
	}

	public AMStackManager(FragmentActivity context) {
		stack = new AMFragmentStack();
		stack.setCloseFragmentListener(this);
		this.context = context;
		quit_In = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		quit_In.setDuration(500);
		quit_Out = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		quit_Out.setDuration(500);

		next_In = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		next_In.setDuration(500);
		next_Out = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		next_Out.setDuration(500);
	}

	/**
	 * Set the bottom of the fragment
	 * 
	 * @param mTargetFragment
	 *            bottom of the fragment
	 */
	public void setFragment(@NonNull AMFragment mTargetFragment) {
		FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
		transaction.setTransition(FragmentTransaction.TRANSIT_NONE)
				.replace(AMConstant.FRAGMENT_ID, mTargetFragment, mTargetFragment.getClass().getName()).commit();
		stack.putStandard(mTargetFragment);
	}

	/**
	 * Jump to the specified fragment
	 * 
	 * @param from
	 *            current fragment
	 * @param to
	 *            next fragment
	 */
	public void addFragment(@NonNull final Fragment from, @NonNull final Fragment to) {
		if (System.currentTimeMillis() - currentTime > CLICK_SPACE) {
			currentTime = System.currentTimeMillis();
			FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
			if (nextIn != 0 && nextOut != 0) {
				transaction.setCustomAnimations(nextIn, nextOut);
				transaction.add(AMConstant.FRAGMENT_ID, to, to.getClass().getName()).hide(from).commit();
			} else {
				transaction.add(AMConstant.FRAGMENT_ID, to, to.getClass().getName()).hide(from).commit();
			}
		}
	}

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
		this.nextIn = nextIn;
		this.nextOut = nextOut;
		quit_In = AnimationUtils.loadAnimation(context, quitIn);
		quit_Out = AnimationUtils.loadAnimation(context, quitOut);
	}

	/**
	 * Jump to the specified fragment
	 * 
	 * @param from
	 *            current fragment
	 * @param to
	 *            next fragment
	 * @param bundle
	 *            Parameter carrier
	 * @param stackMode
	 *            fragment stack Mode
	 */
	public void addFragment(AMFragment from, AMFragment to, Bundle bundle, @StackMode int stackMode) {
		if (stackMode != KEEP_CURRENT) {
			currentMode = stackMode;
		}
		if (bundle != null) {
			to.setArguments(bundle);
		}
		switch (currentMode) {
		case SINGLE_TOP:
			if (!stack.putSingleTop(to)) {
				addFragment(from, to);
			}
			break;
		case SINGLE_TASK:
			if (!stack.putSingleTask(to)) {
				addFragment(from, to);
			}
			break;
		case SINGLE_INSTANCE:
			stack.putSingleInstance(to);
			addFragment(from, to);
			break;
		default:
			stack.putStandard(to);
			addFragment(from, to);
			break;
		}

	}

	/**
	 * open this fragment
	 * 
	 * @param from
	 *            current fragment
	 * @param to
	 *            next fragment
	 */
	public void openFragment(AMFragment from, AMFragment to) {
		addFragment(from, to, null, KEEP_CURRENT);
	}

	/**
	 * Jump to the specified fragment with a parameter form
	 * 
	 * @param from
	 *            current fragment
	 * @param to
	 *            next fragment
	 * @param bundle
	 *            Parameter carrier
	 */
	public void addFragment(AMFragment from, AMFragment to, Bundle bundle) {
		addFragment(from, to, bundle, KEEP_CURRENT);
	}

	/**
	 * Jump to the specified fragment and do not hide the current page.
	 *
	 * @param to
	 *            To jump to the page
	 */
	public void dialogFragment(Fragment to) {
		dialogCount++;
		dialogFragments.add(to);
		FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
		if (!to.isAdded()) {
			if (dialog_in != 0 && dialog_out != 0) {
				transaction.setCustomAnimations(dialog_in, dialog_out)
						.add(AMConstant.FRAGMENT_ID, to, to.getClass().getName()).commit();
			} else {
				transaction.add(AMConstant.FRAGMENT_ID, to, to.getClass().getName()).commit();
			}
		}
	}

	/**
	 * Set the animation to add fragment in dialog mode
	 *
	 * @param dialog_in
	 *            The next page to enter the animation
	 * @param dialog_out
	 *            The next page out of the animation
	 */
	public void setDialogAnim(@AnimRes int dialog_in, @AnimRes int dialog_out) {
		this.dialog_in = dialog_in;
		this.dialog_out = dialog_out;
	}

	/**
	 * Closes the specified fragment
	 *
	 * @param mTargetFragment
	 *            fragment
	 */
	public void closeFragment(Fragment mTargetFragment) {
		FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
		transaction.remove(mTargetFragment).commit();
	}

	/**
	 * Close the specified fragment by tag
	 *
	 * @param tag
	 *            fragment tag
	 */
	public void closeFragment(String tag) {
		AMFragment fragmentByTag = (AMFragment) context.getSupportFragmentManager().findFragmentByTag(tag);
		if (fragmentByTag != null) {
			closeFragment(fragmentByTag);
			context.getSupportFragmentManager().popBackStackImmediate(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		}
	}

	public void close() {
		context.getSupportFragmentManager().popBackStack();
	}

	/**
	 * Close all fragment
	 */
	public void closeAllFragment() {
		int backStackCount = context.getSupportFragmentManager().getBackStackEntryCount();
		for (int i = 0; i < backStackCount; i++) {
			int backStackId = context.getSupportFragmentManager().getBackStackEntryAt(i).getId();
			context.getSupportFragmentManager().popBackStack(backStackId, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		}
	}

	public void onBackPressed() {
		if (!dialogFragments.isEmpty()) {
			dialogCount--;
			closeFragment(dialogFragments.get(dialogCount));
			dialogFragments.remove(dialogCount);
			return;
		}
		Fragment[] last = stack.getLast();
		final AMFragment from = (AMFragment) last[0];
		AMFragment to = (AMFragment) last[1];

		if (to == null) {
			closeAllFragment();
			context.finish();
			return;
		}
		if (from != null) {
			from.onClosed();
			if (to != null) {
				FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
				transaction.show(to).commit();
			}
			View fromVie = from.getView();
			if (fromVie != null && quit_Out != null) {
				fromVie.startAnimation(quit_Out);
				quit_Out.setAnimationListener(new Animation.AnimationListener() {
					@Override
					public void onAnimationStart(Animation animation) {

					}

					@Override
					public void onAnimationEnd(Animation animation) {
						stack.onBackPressed();
						closeFragment(from);
					}

					@Override
					public void onAnimationRepeat(Animation animation) {

					}
				});
			} else {
				stack.onBackPressed();
				closeFragment(from);
			}
		}
		if (to != null) {
			View toView = to.getView();
			if (toView != null && quit_In != null) {
				toView.startAnimation(quit_In);
			}
		}
	}

	public static boolean isFirstClose = false;

	@Override
	public void close(final AMFragment fragment, boolean back) {
		if (back && fragment.isFragmentVisible() && !dialogFragments.contains(fragment)) {
			onBackPressed();
			return;
		}
		if (isFirstClose) {
			View view = fragment.getView();
			if (view != null) {
				if (quit_Out != null) {
					view.startAnimation(quit_Out);
					quit_Out.setAnimationListener(new Animation.AnimationListener() {
						@Override
						public void onAnimationStart(Animation animation) {

						}

						@Override
						public void onAnimationEnd(Animation animation) {
							fragment.onClosed();
							stack.closeFragment(fragment);
							closeFragment(fragment);
						}

						@Override
						public void onAnimationRepeat(Animation animation) {

						}
					});
				} else {
					fragment.onClosed();
					stack.closeFragment(fragment);
					closeFragment(fragment);
				}
			}
			isFirstClose = false;
		} else {
			fragment.onClosed();
			stack.closeFragment(fragment);
			closeFragment(fragment);
		}
	}

	@Override
	public void show(AMFragment fragment) {
		FragmentTransaction transaction = context.getSupportFragmentManager().beginTransaction();
		transaction.show(fragment).commit();
		View view = fragment.getView();
		if (view != null && quit_In != null) {
			view.startAnimation(quit_In);
		}
	}

	@IntDef({ STANDARD, SINGLE_TOP, SINGLE_TASK, SINGLE_INSTANCE, KEEP_CURRENT })
	public @interface StackMode {

	}

	/**
	 * get visible fragment
	 * 
	 * @return visible fragment
	 */
	public final AMFragment getVisibleFragment() {
		if (!isShowDialog()) {
			Fragment[] last = stack.getLast();
			final AMFragment from = (AMFragment) last[0];
			return from;
		}
		return null;
	}
}
