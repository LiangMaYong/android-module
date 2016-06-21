package com.androidmodule.view;

import java.io.File;

import com.androidmodule.dialog.AMDialogView;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebStorage.QuotaUpdater;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/**
 * AMWebLayout
 * 
 * @author LiangMaYong
 * @version 1.0
 */
@SuppressWarnings("deprecation")
public class AMWebLayout extends RelativeLayout {

	private AMWebView web;
	private FrameLayout videoFullView;
	private View customView;
	private CustomViewCallback customViewCallback;
	private View progressVideoView;
	private WebChromeClient webChromeClient;
	private OnWebVidaoFullScreenListener screenListener;

	public void setOnWebVidaoFullScreenListener(OnWebVidaoFullScreenListener screenListener) {
		this.screenListener = screenListener;
	}

	public AMWebView getWebView() {
		return web;
	}

	public static interface OnWebVidaoFullScreenListener {
		void quitFullScreen();

		void enterFullScreen();
	}

	public void setWebChromeClient(WebChromeClient webChromeClient) {
		this.webChromeClient = webChromeClient;
	}

	public void setWebViewClient(WebViewClient client) {
		web.setWebViewClient(client);
	}

	public AMWebLayout(Context context) {
		super(context);
		initViews(context);
	}

	public AMWebLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initViews(context);
	}

	/**
	 * init views
	 * 
	 * @param context
	 */
	private void initViews(Context context) {
		setGravity(Gravity.CENTER);
		setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		web = new AMWebView(context);
		web.setBackgroundColor(0xffffffff);
		web.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		web.setVisibility(View.VISIBLE);
		addView(web);
		videoFullView = new FrameLayout(getContext());
		videoFullView.setBackgroundColor(0xff333333);
		videoFullView
				.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		videoFullView.setVisibility(View.GONE);
		addView(videoFullView);
		//
		web.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				if (webChromeClient != null) {
					webChromeClient.onProgressChanged(view, newProgress);
				}
			}

			@Override
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
				if (webChromeClient != null) {
					webChromeClient.onReceivedTitle(view, title);
				}
			}

			@Override
			public void onReceivedIcon(WebView view, Bitmap icon) {
				super.onReceivedIcon(view, icon);
				if (webChromeClient != null) {
					webChromeClient.onReceivedIcon(view, icon);
				}
			}

			@Override
			public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed) {
				super.onReceivedTouchIconUrl(view, url, precomposed);
				if (webChromeClient != null) {
					webChromeClient.onReceivedTouchIconUrl(view, url, precomposed);
				}
			}

			@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
			@Override
			public void onShowCustomView(View view, int requestedOrientation, CustomViewCallback callback) {
				super.onShowCustomView(view, requestedOrientation, callback);
				if (webChromeClient != null) {
					webChromeClient.onShowCustomView(view, requestedOrientation, callback);
				}
			}

			@Override
			public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
				if (webChromeClient != null) {
					return webChromeClient.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
				}
				return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
			}

			@Override
			public void onRequestFocus(WebView view) {
				super.onRequestFocus(view);
				if (webChromeClient != null) {
					webChromeClient.onRequestFocus(view);
				}
			}

			@Override
			public void onCloseWindow(WebView window) {
				super.onCloseWindow(window);
				if (webChromeClient != null) {
					webChromeClient.onCloseWindow(window);
				}
			}

			@Override
			public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
				if (webChromeClient != null) {
					return webChromeClient.onJsAlert(view, url, message, result);
				}
				return super.onJsAlert(view, url, message, result);
			}

			@Override
			public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
				if (webChromeClient != null) {
					return webChromeClient.onJsConfirm(view, url, message, result);
				}
				return super.onJsConfirm(view, url, message, result);
			}

			@Override
			public boolean onJsPrompt(WebView view, String url, String message, String defaultValue,
					JsPromptResult result) {
				if (webChromeClient != null) {
					return webChromeClient.onJsPrompt(view, url, message, defaultValue, result);
				}
				return super.onJsPrompt(view, url, message, defaultValue, result);
			}

			@Override
			public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
				if (webChromeClient != null) {
					return webChromeClient.onJsBeforeUnload(view, url, message, result);
				}
				return super.onJsBeforeUnload(view, url, message, result);
			}

			@Override
			public void onGeolocationPermissionsShowPrompt(String origin, Callback callback) {
				super.onGeolocationPermissionsShowPrompt(origin, callback);
				if (webChromeClient != null) {
					webChromeClient.onGeolocationPermissionsShowPrompt(origin, callback);
				}
			}

			@Override
			public void onGeolocationPermissionsHidePrompt() {
				super.onGeolocationPermissionsHidePrompt();
				if (webChromeClient != null) {
					webChromeClient.onGeolocationPermissionsHidePrompt();
				}
			}

			@TargetApi(21)
			@Override
			public void onPermissionRequest(PermissionRequest request) {
				super.onPermissionRequest(request);
				if (webChromeClient != null) {
					webChromeClient.onPermissionRequest(request);
				}
			}

			@TargetApi(21)
			@Override
			public void onPermissionRequestCanceled(PermissionRequest request) {
				super.onPermissionRequestCanceled(request);
				if (webChromeClient != null) {
					webChromeClient.onPermissionRequestCanceled(request);
				}
			}

			@Deprecated
			@Override
			public boolean onJsTimeout() {
				if (webChromeClient != null) {
					return webChromeClient.onJsTimeout();
				}
				return super.onJsTimeout();
			}

			@Deprecated
			@Override
			public void onConsoleMessage(String message, int lineNumber, String sourceID) {
				super.onConsoleMessage(message, lineNumber, sourceID);
				if (webChromeClient != null) {
					webChromeClient.onConsoleMessage(message, lineNumber, sourceID);
				}
			}

			@Override
			public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
				if (webChromeClient != null) {
					return webChromeClient.onConsoleMessage(consoleMessage);
				}
				return super.onConsoleMessage(consoleMessage);
			}

			@Override
			public Bitmap getDefaultVideoPoster() {
				if (webChromeClient != null) {
					return webChromeClient.getDefaultVideoPoster();
				}
				return super.getDefaultVideoPoster();
			}

			@Override
			public void getVisitedHistory(ValueCallback<String[]> callback) {
				super.getVisitedHistory(callback);
				if (webChromeClient != null) {
					webChromeClient.getVisitedHistory(callback);
				}
			}

			@Deprecated
			@Override
			public void onExceededDatabaseQuota(String url, String databaseIdentifier, long quota,
					long estimatedDatabaseSize, long totalQuota, QuotaUpdater quotaUpdater) {
				super.onExceededDatabaseQuota(url, databaseIdentifier, quota, estimatedDatabaseSize, totalQuota,
						quotaUpdater);
				if (webChromeClient != null) {
					webChromeClient.onExceededDatabaseQuota(url, databaseIdentifier, quota, estimatedDatabaseSize,
							totalQuota, quotaUpdater);
				}
			}

			@Deprecated
			@Override
			public void onReachedMaxAppCacheSize(long requiredStorage, long quota, QuotaUpdater quotaUpdater) {
				super.onReachedMaxAppCacheSize(requiredStorage, quota, quotaUpdater);
				if (webChromeClient != null) {
					webChromeClient.onReachedMaxAppCacheSize(requiredStorage, quota, quotaUpdater);
				}
			}

			@TargetApi(21)
			@Override
			public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback,
					FileChooserParams fileChooserParams) {
				if (webChromeClient != null) {
					return webChromeClient.onShowFileChooser(webView, filePathCallback, fileChooserParams);
				}
				return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
			}

			@Override
			public void onShowCustomView(View view, CustomViewCallback callback) {
				web.setVisibility(View.INVISIBLE);
				if (customView != null) {
					callback.onCustomViewHidden();
					return;
				}
				screenListener.enterFullScreen();
				videoFullView.addView(view);
				customView = view;
				customViewCallback = callback;
				videoFullView.setVisibility(View.VISIBLE);
				if (webChromeClient != null) {
					webChromeClient.onShowCustomView(view, callback);
				}
			}

			@Override
			public void onHideCustomView() {
				if (customView == null)
					return;
				screenListener.quitFullScreen();
				customView.setVisibility(View.GONE);
				videoFullView.removeView(customView);
				customView = null;
				videoFullView.setVisibility(View.GONE);
				customViewCallback.onCustomViewHidden();
				web.setVisibility(View.VISIBLE);
				if (webChromeClient != null) {
					webChromeClient.onHideCustomView();
				}
			}

			@Override
			public View getVideoLoadingProgressView() {
				View view = null;
				if (webChromeClient != null) {
					view = webChromeClient.getVideoLoadingProgressView();
				}
				if (view != null) {
					return view;
				}
				if (progressVideoView != null) {
					progressVideoView = new AMDialogView(getContext());
					return progressVideoView;
				}
				return super.getVideoLoadingProgressView();
			}
		});

	}

	public static class AMWebView extends WebView {

		public AMWebView(Context context) {
			super(context);
			init(context);
		}

		private void init(Context context) {
			initConfig();

		}

		@SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled", "SdCardPath" })
		private void initConfig() {
			setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
			setHorizontalScrollBarEnabled(false);
			setHorizontalScrollbarOverlay(true);
			setVerticalScrollBarEnabled(false);
			setScrollbarFadingEnabled(false);
			WebSettings ws = getSettings();
			ws.setSavePassword(false);
			ws.setSaveFormData(true);
			ws.setJavaScriptEnabled(true);
			ws.setGeolocationEnabled(true);
			ws.setGeolocationDatabasePath("/data/data/" + getContext().getPackageName() + "/databases/");
			ws.setSupportZoom(false);
			ws.setBuiltInZoomControls(false);
			ws.setDatabaseEnabled(true);
			ws.setLoadWithOverviewMode(true);
			ws.setUseWideViewPort(true);
			ws.setDomStorageEnabled(true);

			ws.getLayoutAlgorithm();
			ws.setLayoutAlgorithm(LayoutAlgorithm.NORMAL);
			ws.setCacheMode(WebSettings.LOAD_NO_CACHE);
			String cacheDirPath = getWebDiskCacheDir(getContext(), "AM_WEB_CACHE").getPath();
			ws.setDatabasePath(cacheDirPath);
			ws.setAppCachePath(cacheDirPath);
			ws.setAppCacheEnabled(true);
		}

	}

	/**
	 * getWebDiskCacheDir
	 * 
	 * @param context
	 *            context
	 * @param uniqueName
	 *            uniqueName
	 * @return File
	 */
	@SuppressLint("NewApi")
	private static File getWebDiskCacheDir(Context context, String uniqueName) {
		String cachePath;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
				|| !Environment.isExternalStorageRemovable()) {
			File cacheDir = context.getExternalCacheDir();
			if (cacheDir != null) {
				cachePath = cacheDir.getPath();
			} else {
				cachePath = context.getCacheDir().getPath();
			}

		} else {
			cachePath = context.getCacheDir().getPath();
		}
		return new File(cachePath + File.separator + uniqueName);
	}
}
