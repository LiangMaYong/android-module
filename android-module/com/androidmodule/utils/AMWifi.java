package com.androidmodule.utils;

import java.util.List;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

/**
 * LWifi
 * 
 * @author LiangMaYong
 * @version 1.0
 */
public class AMWifi {

	/**
	 * 
	 * open wifi.
	 * 
	 * @param context
	 *            context
	 * @param enabled
	 *            enabled
	 */
	public static void setWifiEnabled(Context context, boolean enabled) {
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		if (enabled) {
			wifiManager.setWifiEnabled(true);
		} else {
			wifiManager.setWifiEnabled(false);
		}
	}

	/**
	 * isConnectivity.
	 * 
	 * @param context
	 *            context
	 * @return isConnectivity flag
	 */
	public static boolean isConnectivity(Context context) {

		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return ((connectivityManager.getActiveNetworkInfo() != null
				&& connectivityManager.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED)
				|| telephonyManager.getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
	}

	/**
	 * isWifiConnectivity
	 *
	 * @param context
	 *            the context
	 * @return boolean isWifiConnectivity flag
	 */
	public static boolean isWifiConnectivity(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
			return true;
		}
		return false;
	}

	/**
	 * get wifi list
	 * 
	 * @param context
	 *            context
	 * @return Wifi list
	 */
	public static List<ScanResult> getScanResults(Context context) {
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		List<ScanResult> list = null;
		// start scan WiFi
		boolean f = wifiManager.startScan();
		if (!f) {
			getScanResults(context);
		} else {
			// get scan results
			list = wifiManager.getScanResults();
		}
		return list;
	}

	/**
	 * According to the SSID filter scan results
	 * 
	 * 
	 * @param context
	 *            context
	 * @param bssid
	 *            ssid
	 * @return ScanResult
	 */
	public static ScanResult getScanResultsByBSSID(Context context, String bssid) {
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		ScanResult scanResult = null;
		// start scan WiFi
		boolean f = wifiManager.startScan();
		if (!f) {
			getScanResultsByBSSID(context, bssid);
		}
		// get scan results
		List<ScanResult> list = wifiManager.getScanResults();
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				// get scan results
				scanResult = list.get(i);
				if (scanResult.BSSID.equals(bssid)) {
					break;
				}
			}
		}
		return scanResult;
	}

	/**
	 * 
	 * get wifi info
	 * 
	 * @param context
	 *            context
	 * @return WifiInfo
	 */
	public static WifiInfo getConnectionInfo(Context context) {
		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		return wifiInfo;
	}

}
