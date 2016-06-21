package com.androidmodule.utils;

import android.app.Application;

/**
 * LContext
 * 
 * @author LiangMaYong
 * @version 1.0
 */
public final class AMContext {

	private static Application APP_INSTANCE;

	/**
	 * @return
	 */
	public static Application getApplication() {
		if (APP_INSTANCE == null) {
			synchronized (AMContext.class) {
				if (APP_INSTANCE == null) {
					APP_INSTANCE = AMReflect.on("android.app.ActivityThread").call("currentActivityThread")
							.call("getApplication").get();
				}
			}
		}
		return APP_INSTANCE;
	}

}
