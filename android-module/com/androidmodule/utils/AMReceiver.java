package com.androidmodule.utils;

import java.util.HashMap;
import java.util.Map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

/**
 * AMReceiver
 * 
 * @author LiangMaYong
 * @version 1.0
 */
public class AMReceiver {

	/**
	 * cache BroadcastReceiver
	 */
	private static Map<Context, Map<String, BroadcastReceiver>> receiverMap = new HashMap<Context, Map<String, BroadcastReceiver>>();;

	private AMReceiver() {
	}

	/**
	 * send Broadcast
	 * 
	 * @param context
	 *            context
	 * @param action
	 *            action
	 * @param bundle
	 *            bundle
	 */
	public static void sendBroadcast(Context context, String action, Bundle bundle) {
		if (context == null) {
			return;
		}
		Intent intent = new Intent();
		intent.setAction(action);
		if (bundle != null && !bundle.isEmpty()) {
			intent.putExtras(bundle);
		}
		context.sendBroadcast(intent);
	}

	/**
	 * OnBroadcastListener
	 * 
	 * @author LiangMaYong
	 * @version 1.0
	 */
	public static interface OnReceiverListener {
		void onReceive(Context context, Bundle bundle);
	}

	/**
	 * registerReceiver
	 * 
	 * @param context
	 *            context
	 * @param action
	 *            action
	 * @param broadcastListener
	 *            broadcastListener
	 */
	public static void registerReceiver(Context context, final String action,
			final OnReceiverListener broadcastListener) {
		Map<String, BroadcastReceiver> map = null;
		if (receiverMap.containsKey(context)) {
			map = receiverMap.get(context);
		} else {
			map = new HashMap<String, BroadcastReceiver>();
		}
		if (map.containsKey(action)) {
			unregisterReceiver(context, action);
		}
		BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				if (intent.getAction().equals(action)) {
					if (broadcastListener != null) {
						broadcastListener.onReceive(context, intent.getExtras());
					}
				}
			}
		};
		IntentFilter filter = new IntentFilter(action);
		context.getApplicationContext().registerReceiver(broadcastReceiver, filter);
		map.put(action, broadcastReceiver);
		receiverMap.put(context, map);
	}

	/**
	 * unregisterReceiver
	 * 
	 * @param context
	 *            context
	 * @param action
	 *            action
	 */
	public static void unregisterReceiver(Context context, final String action) {
		if (receiverMap.containsKey(context)) {
			Map<String, BroadcastReceiver> map = receiverMap.get(context);
			if (map.containsKey(action)) {
				try {
					BroadcastReceiver broadcastReceiver = map.get(action);
					context.getApplicationContext().unregisterReceiver(broadcastReceiver);
				} catch (Exception e) {
				}
				map.remove(action);
			}
			if (map.isEmpty()) {
				receiverMap.remove(context);
			} else {
				receiverMap.put(context, map);
			}
		}
	}

	/**
	 * unregisterReceiverAll
	 * 
	 * @param context
	 *            context
	 */
	public static void unregisterReceiverAll(Context context) {
		if (receiverMap.containsKey(context)) {
			Map<String, BroadcastReceiver> map = receiverMap.get(context);
			for (BroadcastReceiver receiver : map.values()) {
				try {
					context.getApplicationContext().unregisterReceiver(receiver);
				} catch (Exception e) {
				}
			}
			receiverMap.remove(context);
		}
	}
}
