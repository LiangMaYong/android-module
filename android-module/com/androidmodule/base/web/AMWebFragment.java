package com.androidmodule.base.web;

import com.androidmodule.actionbar.AMActionController;
import com.androidmodule.actionbar.iconfont.AMIcon;
import com.androidmodule.base.AMFragment;
import com.androidmodule.dialog.AMDialogView;
import com.androidmodule.view.AMWebLayout;
import com.androidmodule.view.AMWebLayout.OnWebVidaoFullScreenListener;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class AMWebFragment extends AMFragment implements OnWebVidaoFullScreenListener {

	private String url, title, close;
	private boolean isSetTitle = false;
	private boolean showActionBar = false;
	private AMWebLayout weblayout;
	private View errorView = null;
	private View loadView = null;

	public AMWebLayout getWeblayout() {
		return weblayout;
	}

	private boolean webError = false;
	private boolean showError = false;
	private boolean isReload = false;

	protected boolean isShowError() {
		return showError;
	}

	protected void showErrorPage() {
		if (errorView != null) {
			showError = true;
			webError = true;
			getWeblayout().setVisibility(View.GONE);
			errorView.setVisibility(View.VISIBLE);
		}
	}

	protected void hideErrorPage() {
		if (errorView != null) {
			showError = false;
			getWeblayout().setVisibility(View.VISIBLE);
			errorView.setVisibility(View.GONE);
		}
	}

	protected boolean shouldOverrideUrlLoading(WebView view, String weburl) {
		return false;
	}

	protected View onCreateErrorView(Context context) {
		return null;
	}

	protected boolean useLoadView() {
		return false;
	}

	protected View onCreateLoadingView(Context context) {
		return new AMDialogView(context);
	}

	@Override
	protected void initViews(View containerView, RelativeLayout rootView) {
		getActionController().leftThree().text(title);
		initTitle();
		loadUrl();
		weblayout.setWebChromeClient(new WebChromeClient() {

			@SuppressWarnings("deprecation")
			@Override
			public void onProgressChanged(WebView view, int progress) {
				if (progress == 100) {
					getActionController().progressBar().setProgress(0);
					initTitle();
					if (isShowError()) {
						if (isReload && !webError) {
							hideErrorPage();
						} else {
							view.clearView();
							webError = false;
						}
					}
					if (loadView != null) {
						loadView.setVisibility(View.GONE);
					}
					isReload = false;
				} else {
					if (loadView != null) {
						loadView.setVisibility(View.VISIBLE);
					}
					getActionController().progressBar().setProgress(progress);
				}
			}
		});
		weblayout.setWebViewClient(new WebViewClient() {

			@SuppressWarnings("deprecation")
			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				view.clearView();
				showErrorPage();
			}

			@SuppressLint("DefaultLocale")
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String weburl) {
				if (AMWebFragment.this.shouldOverrideUrlLoading(view, weburl)) {
					return true;
				}
				final String url = weburl.toLowerCase();
				if (url.startsWith("geo:")) {
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
					getContext().startActivity(intent);
				} else if (url.startsWith("mailto:")) {
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
					getContext().startActivity(intent);
				} else if (url.startsWith("tel:")) {
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
					getContext().startActivity(intent);
				} else if (url.startsWith("http://") || url.startsWith("https://")) {
					view.stopLoading();
					view.loadUrl(url);
				} else if (url.startsWith("file://") || url.startsWith("file://")) {
					view.stopLoading();
					view.loadUrl(url);
				}
				return true;
			}
		});
	}

	private void loadUrl() {
		if (url != null && !"".equals(url) && !"null".equals(title)) {
			weblayout.getWebView().loadUrl(url);
		} else {
			weblayout.getWebView().loadUrl("about:blank");
		}
	}

	private void initTitle() {
		if (!isSetTitle) {
			if (!isFragmentClosed()) {
				getActionController().leftThree().text(weblayout.getWebView().getTitle());
			}
		}
	}

	@Override
	protected void onSuperCreate() {
		url = getArguments().getString("URL");
		title = getArguments().getString("TITLE");
		if (title != null && !"".equals(title) && !"null".equals(title)) {
			isSetTitle = true;
		} else {
			isSetTitle = false;
		}
		showActionBar = getArguments().getBoolean("ACTION_BAR", true);
	}

	@Override
	public boolean useAMActionBar() {
		return showActionBar;
	}

	@Override
	protected View getContaierView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		weblayout = new AMWebLayout(getContext());
		errorView = onCreateErrorView(getContext());
		if (errorView != null) {
			errorView.setVisibility(View.GONE);
			weblayout.addView(errorView);
		}
		if (useLoadView()) {
			loadView = onCreateLoadingView(getContext());
			if (loadView != null) {
				LinearLayout loading = new LinearLayout(getContext());
				loading.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
						RelativeLayout.LayoutParams.MATCH_PARENT));
				loading.setGravity(Gravity.CENTER);
				loading.addView(loadView);
				loadView.setVisibility(View.GONE);
				weblayout.addView(loading);
			}
		}
		weblayout.setOnWebVidaoFullScreenListener(this);
		return weblayout;
	}

	@Override
	protected int getContaierViewId() {
		return 0;
	}

	/**
	 * reload
	 */
	private void reload() {
		isReload = true;
		weblayout.getWebView().stopLoading();
		weblayout.getWebView().reload();
	}

	/**
	 * onBackPressed
	 */
	@Override
	public boolean onBackPressed() {
		if (weblayout.getWebView().canGoBack()) {
			weblayout.getWebView().stopLoading();
			weblayout.getWebView().goBack();
			isReload = true;
			if (close != null && !"".equals(close)) {
				getActionController().rightTwo().text(close).clicked(new OnClickListener() {
					@Override
					public void onClick(View v) {
						weblayout.getWebView().loadUrl("about:blank");
						close();
					}
				}).textSize(14).visible();
			} else {
				getActionController().rightTwo().text("").iconToLeft(AMIcon.icon_close).clicked(new OnClickListener() {
					@Override
					public void onClick(View v) {
						weblayout.getWebView().loadUrl("about:blank");
						close();
					}
				}).textSize(14).visible();
			}
			return true;
		}
		return super.onBackPressed();
	}

	/**
	 * goback
	 */
	private void goBack() {
		if (weblayout.getWebView().canGoBack()) {
			weblayout.getWebView().stopLoading();
			weblayout.getWebView().goBack();
			isReload = true;
			if (close != null && !"".equals(close)) {
				getActionController().rightTwo().text(close).clicked(new OnClickListener() {
					@Override
					public void onClick(View v) {
						weblayout.getWebView().loadUrl("about:blank");
						close();
					}
				}).textSize(14).visible();
			} else {
				getActionController().rightTwo().text("").iconToLeft(AMIcon.icon_close).clicked(new OnClickListener() {
					@Override
					public void onClick(View v) {
						weblayout.getWebView().loadUrl("about:blank");
						close();
					}
				}).textSize(14).visible();
			}
		} else {
			close();
		}
	}

	/**
	 * initActions
	 */
	@Override
	protected void initActions(AMActionController actionController) {

		if (!showActionBar) {
			actionController.hide();
		} else {
			actionController.show();
		}
		actionController.right().iconToLeft(AMIcon.icon_refresh).text("").clicked(new OnClickListener() {
			@Override
			public void onClick(View v) {
				reload();
			}
		});
		actionController.left().iconToLeft(AMIcon.icon_back).text("").clicked(new OnClickListener() {
			@Override
			public void onClick(View v) {
				goBack();
			}
		});
	}

	@Override
	public void quitFullScreen() {

	}

	@Override
	public void enterFullScreen() {

	}

}
