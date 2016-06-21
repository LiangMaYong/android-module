package com.androidmodule.view;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * AMViewPager
 * 
 * @author LiangMaYong
 * @version 1.0
 */
public class AMViewPager extends FrameLayout {
	/** The enabled. */
	private boolean enabled = true;

	public AMViewPager(Context context) {
		super(context);
		initViews();
	}

	public AMViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		initViews();
	}

	public AMViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initViews();
	}

	private ViewPager viewPager;
	private OnLViewPagerSelectListener selectListener;

	private void initViews() {
		viewPager = new ViewPager(getContext()) {
			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouchEvent(MotionEvent event) {
				if (enabled) {
					return super.onTouchEvent(event);
				}
				return false;
			}
		};
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				_selected(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		addView(viewPager);
	}

	public ViewPager getViewPager() {
		return viewPager;
	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public void begin(List<Integer> layouts, int index) {
		ArrayList<View> views = new ArrayList<View>();
		LayoutInflater inflater = LayoutInflater.from(getContext());
		for (int i = 0; i < layouts.size(); i++) {
			try {
				View view = inflater.inflate(layouts.get(i), null);
				views.add(view);
			} catch (Exception e) {
			}
		}
		viewPager.setAdapter(new LPagerAdapter(views));
		_selected(index);
		setCurrentItem(index, false);
		if (Build.VERSION.SDK_INT >= 9) {
			viewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
		}
	}

	@SuppressLint("Recycle")
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public void begin(FragmentActivity activity, List<Fragment> fragments, int index) {
		FragmentManager fm = activity.getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		viewPager.setOffscreenPageLimit(fragments.size());
		viewPager.setAdapter(new FPagerAdapter(fm, fragments));
		_selected(index);
		setCurrentItem(index, false);
		if (Build.VERSION.SDK_INT >= 9) {
			viewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);
		}
	}

	/**
	 * Sets the paging enabled.
	 *
	 * @param enabled
	 *            the new paging enabled
	 */
	public void setPagingEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setCurrentItem(int index, boolean smoothScroll) {
		viewPager.setCurrentItem(index, smoothScroll);
	}

	private void _selected(int index) {
		if (selectListener != null) {
			selectListener.selected(index);
		}
	}

	public void setOnLViewPagerSelectListener(OnLViewPagerSelectListener selectListener) {
		this.selectListener = selectListener;
	}

	public static interface OnLViewPagerSelectListener {
		void selected(int index);
	}

	public class LPagerAdapter extends PagerAdapter {

		private ArrayList<View> views = new ArrayList<View>();

		public LPagerAdapter(ArrayList<View> views) {
			this.views = views;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public int getCount() {
			return views.size();
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView(views.get(position));
		}

		@Override
		public Object instantiateItem(View container, int position) {
			((ViewPager) container).addView(views.get(position));
			return views.get(position);
		}

	}

	public class FPagerAdapter extends FragmentPagerAdapter {

		private List<Fragment> fragments;

		public FPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
			super(fm);
			this.fragments = fragments;
		}

		@Override
		public Fragment getItem(int position) {
			return fragments.get(position);

		}

		@Override
		public int getCount() {
			return fragments.size();
		}

	}
}
