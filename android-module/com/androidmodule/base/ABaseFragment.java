package com.androidmodule.base;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.androidmodule.base.interfaces.IAMFragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

/**
 * AMBaseFragment
 * 
 * @author LiangMaYong
 * @version 1.0
 */
abstract class ABaseFragment extends Fragment implements IAMFragment {

	private Object mBase = null;

	public ABaseFragment() {
	}

	public ABaseFragment(Object base) {
		this.mBase = base;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		try {
			methoder("onActivityCreated", Bundle.class).invoke(savedInstanceState);
		} catch (Exception e) {
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try {
			methoder("onActivityResult", int.class, int.class, Intent.class).invoke(requestCode, resultCode, data);
		} catch (Exception e) {
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		try {
			methoder("onConfigurationChanged", Configuration.class).invoke(newConfig);
		} catch (Exception e) {
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		try {
			return (Boolean) methoder("onContextItemSelected", MenuItem.class).invoke(item);
		} catch (Exception e) {
		}
		return super.onContextItemSelected(item);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		cloneTo(this, mBase);
		setField(mBase, "mBase", this);
		try {
			methoder("onCreate", Bundle.class).invoke(savedInstanceState);
		} catch (Exception e) {
		}
	}

	@Override
	public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
		try {
			return (Animation) methoder("onCreateAnimation", int.class, boolean.class, int.class).invoke(transit, enter,
					nextAnim);
		} catch (Exception e) {
		}
		return super.onCreateAnimation(transit, enter, nextAnim);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		try {
			methoder("onCreateContextMenu", ContextMenu.class, View.class, ContextMenuInfo.class).invoke(menu, v,
					menuInfo);
		} catch (Exception e) {
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		try {
			methoder("onCreateOptionsMenu", Menu.class, MenuInflater.class).invoke(menu, inflater);
		} catch (Exception e) {
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		try {
			return (View) methoder("onCreateView", LayoutInflater.class, ViewGroup.class, Bundle.class).invoke(inflater,
					container, savedInstanceState);
		} catch (Exception e) {
		}
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		try {
			methoder("onDestroy").invoke();
		} catch (Exception e) {
		}
	}

	@Override
	public void onDestroyOptionsMenu() {
		super.onDestroyOptionsMenu();
		try {
			methoder("onDestroyOptionsMenu").invoke();
		} catch (Exception e) {
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		try {
			methoder("onDestroyView").invoke();
		} catch (Exception e) {
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		try {
			methoder("onDetach").invoke();
		} catch (Exception e) {
		}
	}

	@Override
	public final void onHiddenChanged(boolean hidden) {
		try {
			methoder("onHiddenChanged", boolean.class).invoke(hidden);
		} catch (Exception e) {
		}
		if (hidden) {
			onNowHidden();
		} else {
			onNextShow();
		}
	}

	@Override
	public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
		super.onInflate(activity, attrs, savedInstanceState);
		try {
			methoder("onInflate", Activity.class, AttributeSet.class, Bundle.class).invoke(activity, attrs,
					savedInstanceState);
		} catch (Exception e) {
		}
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		try {
			methoder("onLowMemory").invoke();
		} catch (Exception e) {
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		try {
			return (Boolean) methoder("onOptionsItemSelected", MenuItem.class).invoke(item);
		} catch (Exception e) {
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onOptionsMenuClosed(Menu menu) {
		super.onOptionsMenuClosed(menu);
		try {
			methoder("onOptionsMenuClosed", Menu.class).invoke(menu);
		} catch (Exception e) {
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		try {
			methoder("onPause").invoke();
		} catch (Exception e) {
		}
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		try {
			methoder("onPrepareOptionsMenu", Menu.class).invoke(menu);
		} catch (Exception e) {
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		try {
			methoder("onResume").invoke();
		} catch (Exception e) {
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		try {
			methoder("onSaveInstanceState", Bundle.class).invoke(outState);
		} catch (Exception e) {
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		try {
			methoder("onStart").invoke();
		} catch (Exception e) {
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		try {
			methoder("onStop").invoke();
		} catch (Exception e) {
		}
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		try {
			methoder("onViewCreated", View.class, Bundle.class).invoke(view, savedInstanceState);
		} catch (Exception e) {
		}
	}

	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		super.onViewStateRestored(savedInstanceState);
		try {
			methoder("onViewStateRestored", Bundle.class).invoke(savedInstanceState);
		} catch (Exception e) {
		}
	}

	@Override
	public void onNewIntent() {
		try {
			methoder("onNewIntent").invoke();
		} catch (Exception e) {
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		try {
			return (Boolean) methoder("onKeyDown", int.class, KeyEvent.class).invoke(keyCode, event);
		} catch (Exception e) {
		}
		return false;
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		try {
			return (Boolean) methoder("onKeyUp", int.class, KeyEvent.class).invoke(keyCode, event);
		} catch (Exception e) {
		}
		return false;
	}

	@Override
	public boolean onBackPressed() {
		try {
			return (Boolean) methoder("onBackPressed").invoke();
		} catch (Exception e) {
		}
		return false;
	}

	public void onClosed() {
		try {
			methoder("onClosed").invoke();
		} catch (Exception e) {
		}
	}

	/**
	 * Override this method to facilitate access to the current page page Pause
	 * callback
	 */
	public void onNowHidden() {
		try {
			methoder("onNowHidden").invoke();
		} catch (Exception e) {
		}
	}

	/**
	 * Override this method to facilitate access to the current page page Resume
	 * callback
	 */
	public void onNextShow() {
		try {
			methoder("onNextShow").invoke();
		} catch (Exception e) {
		}
	}

	private Methoder methoder(String method, Class<?>... parameterTypes) {
		return new Methoder(mBase.getClass(), mBase, method, parameterTypes);
	}

	private static final class Methoder {

		private Method method;
		private Object object;

		private Method getDeclaredMethod(Class<?> clazz, String name, Class<?>... parameterTypes) {
			Method method = null;
			for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
				try {
					method = clazz.getDeclaredMethod(name, parameterTypes);
					return method;
				} catch (Exception e) {
				}
			}
			return null;
		}

		private Methoder(Class<?> clazz, Object object, String method, Class<?>... parameterTypes) {
			try {
				this.object = object;
				this.method = getDeclaredMethod(clazz, method, parameterTypes);
			} catch (Exception e) {
			}
		}

		public Object invoke(Object... args) throws Exception {
			if (method != null) {
				try {
					method.setAccessible(true);
					Object object = method.invoke(this.object, args);
					method = null;
					this.object = null;
					return object;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return null;
		}
	}

	private void cloneTo(Object object, Object targetObject) {
		if (object == null || targetObject == null) {
			return;
		}
		Map<String, Object> fields = getFields(object);
		if (fields != null && !fields.isEmpty()) {
			for (Entry<String, Object> entry : fields.entrySet()) {
				setField(targetObject, entry.getKey(), entry.getValue());
			}
		}
	}

	private Map<String, Object> getFields(Object object) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (object == null) {
			return map;
		}
		Class<?> clazz = object.getClass();
		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			Field[] fields = clazz.getDeclaredFields();
			if (fields != null && fields.length > 0) {
				for (int i = 0; i < fields.length; i++) {
					Object value = null;
					try {
						fields[i].setAccessible(true);
						value = fields[i].get(object);
					} catch (IllegalArgumentException e1) {
					} catch (IllegalAccessException e1) {
					}
					map.put(fields[i].getName(), value);
				}
			}
		}
		return map;
	}

	private boolean setField(Object object, String fieldName, Object value) {
		if (object == null) {
			return false;
		}
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
}
