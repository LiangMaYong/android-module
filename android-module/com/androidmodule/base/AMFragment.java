package com.androidmodule.base;

import com.androidmodule.actionbar.AMActionBar;
import com.androidmodule.actionbar.AMActionController;
import com.androidmodule.base.web.AMWebFragment;
import com.androidmodule.utils.AMGesture;
import com.androidmodule.utils.AMGesture.LGestureType;
import com.androidmodule.utils.AMGesture.OnGestureListener;
import com.androidmodule.utils.AMPresenter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SlidingPaneLayout.LayoutParams;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/**
 * AMFragment
 * 
 * @author LiangMaYong
 * @version 1.0
 */
public abstract class AMFragment extends ABaseFragment {

	public AMFragment() {
	}

	private AMFragment(Fragment fragment) {
		super(fragment);
	}

	public boolean isFragmentVisible() {
		return isFragmentVisible;
	}

	private boolean isFrist = false;

	private GestureDetector gestureDetector;
	private OnGestureListener gestureListener;
	private RelativeLayout rootView;
	private AMActivity activity;
	private boolean isFragmentVisible = false;
	private boolean isFragmentClose = false;
	private Context context;

	public boolean isFragmentClosed() {
		return isFragmentClose;
	}

	public Context getContext() {
		return context;
	}

	public boolean isFrist() {
		return isFrist;
	}

	public boolean onTouchEvent(MotionEvent event) {
		if (gestureDetector != null) {
			return gestureDetector.onTouchEvent(event);
		}
		return false;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		gestureDetector = AMGesture.getGestureDetector(getActivity(), new OnGestureListener() {
			@Override
			public void onTouch(LGestureType gestureType, int direction) {
				if (gestureListener != null) {
					gestureListener.onTouch(gestureType, direction);
				}
			}
		});
	}

	public RelativeLayout getRootView() {
		return rootView;
	}

	/**
	 * set IBOnGestureListener
	 * 
	 * @param listener
	 *            IBOnGestureListener
	 */
	public void setOnGestureListener(OnGestureListener listener) {
		this.gestureListener = listener;
	}

	protected void onSuperCreate() {

	}

	@Override
	public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.context = inflater.getContext();
		onSuperCreate();
		RelativeLayout layout = new RelativeLayout(inflater.getContext());
		layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		FrameLayout frameLayout = new FrameLayout(inflater.getContext());
		frameLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		layout.addView(frameLayout);
		View view = getContaierView(inflater, frameLayout, savedInstanceState);
		getActionController().reset(useAMActionBar());
		initActions(getActionController());
		if (useAMActionBar() && contaierViewAlignAMActionBar()) {
			frameLayout.setPadding(0, getModuleActionBar().getConfig().getActionHeightPx(), 0, 0);
		}
		frameLayout.addView(view);
		isFragmentVisible = true;
		isFragmentClose = false;
		getRootActivity().onFragmentCreateView(this, view);
		initViews(view, layout);
		return layout;
	}

	/**
	 * init views
	 * 
	 * @param containerView
	 *            containerView
	 */
	protected abstract void initViews(View containerView, RelativeLayout rootView);

	public boolean useAMActionBar() {
		return true;
	}

	public boolean contaierViewAlignAMActionBar() {
		return false;
	}

	/**
	 * get contaier view Id
	 * 
	 * @return
	 */
	protected abstract int getContaierViewId();

	/**
	 * get contaier View
	 * 
	 * @return
	 */
	protected View getContaierView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(getContaierViewId(), container, false);
	}

	/**
	 * open a new Fragment
	 *
	 * @param fragment
	 *            fragment
	 */
	public void open(@NonNull AMFragment fragment) {
		getRootActivity().getStackManager().addFragment(this, fragment, null);
	}

	/**
	 * setResult
	 * 
	 * @param resultCode
	 *            resultCode
	 * @param data
	 *            data
	 */
	public void setResult(int resultCode, Bundle data) {
		getRootActivity().getStackManager().setResult(resultCode, data);
	}

	/**
	 * getResult
	 * 
	 * @param resultCode
	 *            resultCode
	 * @return
	 */
	public Bundle getResult(int resultCode) {
		return getRootActivity().getStackManager().getResult(resultCode);
	}

	/**
	 * open fragment
	 * 
	 * @param fragment
	 *            fragment
	 * @param bundle
	 *            bundle
	 */
	protected void open(@NonNull AMFragment fragment, Bundle bundle) {
		getRootActivity().getStackManager().addFragment(this, fragment, bundle);
	}

	/**
	 * closeAndOpen fragment
	 * 
	 * @param fragment
	 *            fragment
	 * @param bundle
	 *            bundle
	 */
	protected void closeAndOpen(@NonNull AMFragment fragment, Bundle bundle) {
		getRootActivity().getStackManager().addFragment(this, fragment, bundle);
		finish(this);
	}

	/**
	 * open fragment
	 * 
	 * @param fragment
	 *            fragment
	 * @param bundle
	 *            bundle
	 * @param stackMode
	 *            stackMode
	 */
	protected void open(@NonNull AMFragment fragment, Bundle bundle, int stackMode) {
		getRootActivity().getStackManager().addFragment(this, fragment, bundle, stackMode);
	}

	/**
	 * openWeb
	 * 
	 * @param url
	 *            url
	 * @param title
	 *            title
	 * @param action
	 *            action
	 */
	protected void openWeb(String url, String title, boolean action) {
		Bundle bundle = new Bundle();
		bundle.putString("URL", url);
		bundle.putString("TITLE", title);
		bundle.putBoolean("ACTION_BAR", action);
		open(new AMWebFragment(), bundle);
	}

	/**
	 * closeAndOpenWeb
	 * 
	 * @param url
	 *            url
	 * @param title
	 *            title
	 * @param action
	 *            action
	 */
	protected void closeAndOpenWeb(String url, String title, boolean action) {
		Bundle bundle = new Bundle();
		bundle.putString("URL", url);
		bundle.putString("TITLE", title);
		bundle.putBoolean("ACTION_BAR", action);
		closeAndOpen(new AMWebFragment(), bundle);
	}

	/**
	 * closeAndOpen fragment
	 * 
	 * @param fragment
	 *            fragment
	 * @param bundle
	 *            bundle
	 * @param stackMode
	 *            stackMode
	 */
	protected void closeAndOpen(@NonNull AMFragment fragment, Bundle bundle, int stackMode) {
		getRootActivity().getStackManager().addFragment(this, fragment, bundle, stackMode);
		finish(this);
	}

	/**
	 * Jump to the specified fragment and do not hide the current page.
	 *
	 * @param to
	 *            To jump to the page
	 */
	public void dialogFragment(Fragment to) {
		getRootActivity().getStackManager().dialogFragment(to);
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
		getRootActivity().getStackManager().setDialogAnim(dialog_in, dialog_out);
	}

	/**
	 * close this current Fragment
	 */
	protected void close() {
		getRootActivity().getStackManager().close(this, true);
		isFragmentClose = true;
	}

	/**
	 * Closes the specified fragment
	 *
	 * @param fragment
	 *            the specified fragment
	 */
	protected void finish(AMFragment fragment) {
		getRootActivity().getStackManager().close(fragment, false);
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	/**
	 * Get fragment dependent Activity, many times this is very useful
	 *
	 * @return LFragmentActivity dependent Activity
	 */
	public AMActivity getRootActivity() {
		if (activity == null) {
			if (getActivity() instanceof AMActivity) {
				activity = (AMActivity) getActivity();
			} else {
				throw new ClassCastException("this activity must be extends " + AMActivity.class.getName());
			}
		}
		return activity;
	}

	/**
	 * Override this method in order to facilitate the singleTop mode to be
	 * called in
	 */
	@Override
	public void onNewIntent() {
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			boolean flag = onBackPressed();
			if (flag) {
				return flag;
			}
		}
		return false;
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		return false;
	}

	@Override
	public boolean onBackPressed() {
		return false;
	}

	/**
	 * show toast
	 * 
	 * @param text
	 *            text
	 */
	public void showToast(final String text) {
		getRootActivity().showToast(text);
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
		getRootActivity().showToast(text, duration);
	}

	public AMActionController getActionController() {
		return getRootActivity().getActionController();
	}

	@Override
	public void onNextShow() {
		super.onNextShow();
		isFragmentVisible = true;
		getActionController().reset(useAMActionBar());
		initActions(getActionController());
	}

	@Override
	public void onNowHidden() {
		isFragmentVisible = false;
		super.onNowHidden();
	}

	public AMActionBar getModuleActionBar() {
		return getRootActivity().getAMActionBar();
	}

	public final static AMFragment createByFragment(final Fragment fragment) {
		AMFragment moduleFragment = new AMFragment(fragment) {

			@Override
			protected View getContaierView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
				return fragment.onCreateView(inflater, container, savedInstanceState);
			}

			@Override
			protected int getContaierViewId() {
				return 0;
			}

			@Override
			protected void initActions(AMActionController actionController) {

			}

			@Override
			protected void initViews(View containerView, RelativeLayout rootView) {

			}

		};
		return moduleFragment;
	}

	protected abstract void initActions(AMActionController actionController);

	private AMPresenter<?> presenter;

	@Override
	public void onClosed() {
		super.onClosed();
		if (presenter != null) {
			presenter.dettach();
		}
	}

	public AMPresenter<?> getPresenter() {
		return presenter;
	}

	protected void setPersenter(AMPresenter<?> presenter) {
		if (presenter == null) {
			return;
		}
		if (this.presenter != null && this.presenter.equals(presenter)) {
			return;
		}
		this.presenter = presenter;
	}

}
