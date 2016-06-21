package com.androidmodule.base.interfaces;

import android.view.KeyEvent;

/**
 * IAMFragment
 * 
 * @author LiangMaYong
 * @version 1.0
 */
public interface IAMFragment {

	boolean onKeyDown(int keyCode, KeyEvent event);

	boolean onKeyUp(int keyCode, KeyEvent event);

	boolean onBackPressed();

	void onClosed();

	void onNewIntent();

	void onNowHidden();

	void onNextShow();
}
