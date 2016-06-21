package com.androidmodule.dialog;

import java.util.HashMap;
import java.util.Map;

import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * AMDialogLoading
 * 
 * @author LiangMaYong
 * @version 1.0
 */
public class AMDialogLoading extends Dialog {

	private static Map<ContextWrapper, AMDialogLoading> dialogs = new HashMap<ContextWrapper, AMDialogLoading>();

	/**
	 * initLoading
	 * 
	 * @param wrapper
	 *            wrapper
	 */
	public static void initLoading(ContextWrapper wrapper) {
		if (wrapper != null) {
			dialogs.put(wrapper, new AMDialogLoading(wrapper));
		}
	}

	/**
	 * cancel loading
	 */
	public static void cancelLoading(ContextWrapper wrapper) {
		AMDialogLoading loading = getDialog(wrapper);
		if (loading != null) {
			try {
				loading.cancel();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * destroyLoading
	 * 
	 * @param wrapper
	 *            wrapper
	 */
	public static void destroyLoading(ContextWrapper wrapper) {
		if (wrapper != null) {
			dialogs.remove(wrapper);
		}
	}

	private static AMDialogLoading getDialog(ContextWrapper wrapper) {
		AMDialogLoading loading = null;
		if (wrapper != null) {
			loading = dialogs.get(wrapper);
			if (loading == null) {
				loading = new AMDialogLoading(wrapper);
				dialogs.put(wrapper, loading);
			}
		}
		return loading;
	}

	/**
	 * show loading dialog
	 * 
	 * @param wrapper
	 *            wrapper
	 * @param msg
	 *            loading msg
	 * @param dim
	 *            dim
	 */
	public static void showLoadingDialog(ContextWrapper wrapper, String msg, boolean dim) {
		AMDialogLoading loading = getDialog(wrapper);
		if (loading == null) {
			return;
		}
		if (!dim) {
			Window window = loading.getWindow();
			WindowManager.LayoutParams params = window.getAttributes();
			params.dimAmount = 0f;
			window.setAttributes(params);
		}
		if (loading != null) {
			loading.showLoading(msg);
		}
	}

	/**
	 * show loading dialog delay cancel
	 * 
	 * @param wrapper
	 *            wrapper
	 * @param msg
	 *            msg
	 * @param dim
	 *            dim
	 * @param delayMillis
	 *            delayMillis
	 * @param runnable
	 *            cancel runnable
	 */
	public static void showLoadingDelayCancel(ContextWrapper wrapper, String msg, boolean dim, long delayMillis,
			Runnable runnable) {
		AMDialogLoading loading = getDialog(wrapper);
		if (!dim) {
			Window window = loading.getWindow();
			WindowManager.LayoutParams params = window.getAttributes();
			params.dimAmount = 0f;
			window.setAttributes(params);
		}
		if (loading != null) {
			loading.showLoadingDelay(msg, delayMillis, runnable);
		}
	}

	private LinearLayout contentView;

	private void showLoadingDelay(String msg, long delayMillis, Runnable runnable) {
		setText(msg);
		cancel(delayMillis, runnable);
		if (!isShowing()) {
			show();
		}
	}

	private void showLoading(String msg) {
		setText(msg);
		if (!isShowing()) {
			show();
		}
	}

	private AMDialogLoading(Context context) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
		initViews(context);
	}

	private void initViews(Context context) {
		contentView = new AMDialogView(context);
		setContentView(contentView);
	}

	private void setText(CharSequence text) {
		try {
			((TextView) contentView.findViewWithTag("T")).setText(text);
		} catch (Exception e) {
		}
	}

	private Handler handler = new Handler();

	private void cancel(long delayMillis, final Runnable runnable) {
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				cancel();
				if (runnable != null) {
					runnable.run();
				}
			}
		}, delayMillis);
	}

}
