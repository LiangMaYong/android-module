package com.androidmodule.utils;

import android.os.Bundle;

/**
 * AMPresenter
 * 
 * @author LiangMaYong
 * @version 1.0
 */
public abstract class AMPresenter<V> {

	private V view;

	public V getView() {
		return view;
	}

	public void dettach() {
		this.view = null;
	}

	public AMPresenter(V view) {
		this.view = view;
	}

	public abstract void handleMessage(int what, Bundle bundle);

}