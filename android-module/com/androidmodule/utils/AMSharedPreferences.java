package com.androidmodule.utils;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

/**
 * LSharedPreferences
 * 
 * @author LiangMaYong
 * @version 1.0
 */
public class AMSharedPreferences {
	private AMSharedPreferences() {
	}

	private static class StringCacheHolder {
		static final AMSharedPreferences cache = new AMSharedPreferences();
	}

	private String sharedPreferencesName;

	/**
	 * init preferencesUtil
	 * 
	 * @param filename
	 *            preferencesName
	 * @return IBPreferencesUtil
	 */
	@SuppressLint("DefaultLocale")
	public static AMSharedPreferences init(String filename) {
		AMSharedPreferences cache = StringCacheHolder.cache;
		if (filename == null || "".equals(filename)) {
			filename = "L_SYSTEM_COMMON_PREFERENCES";
		}
		cache.sharedPreferencesName = filename.toUpperCase();
		return cache;
	}

	/**
	 * get string
	 * 
	 * @param key
	 *            key
	 * @param defValue
	 *            defValue
	 * @return string
	 */
	public String getString(String key, String defValue) {
		String mString = "";
		try {
			SharedPreferences Host = AMContext.getApplication().getSharedPreferences(sharedPreferencesName, 0);
			mString = new String(AMDes.decrypt(Host.getString(key, defValue), key));
		} catch (Exception e) {
		}
		return mString;
	}

	/**
	 * get int
	 * 
	 * @param key
	 *            key
	 * @param defValue
	 *            defValue
	 * @return int
	 */
	public int getInt(String key, int defValue) {
		int mInt = 0;
		try {
			SharedPreferences Host = AMContext.getApplication().getSharedPreferences(sharedPreferencesName, 0);
			mInt = Host.getInt(key, defValue);
		} catch (Exception e) {
		}
		return mInt;
	}

	/**
	 * get boolean
	 * 
	 * @param key
	 *            key
	 * @param defValue
	 *            defValue
	 * @return boolean
	 */
	public boolean getBoolean(String key, boolean defValue) {
		boolean retu = false;
		try {
			retu = "Yes".equals(getString(key, defValue ? "Yes" : "No"));
		} catch (Exception e) {
		}
		return retu;
	}

	/**
	 * get float
	 * 
	 * @param key
	 *            key
	 * @param defValue
	 *            defValue
	 * @return float
	 */
	public float getFloat(String key, float defValue) {
		float retu = 0;
		try {
			String string = getString(key, defValue + "");
			retu = Float.parseFloat(string);
		} catch (Exception e) {
		}
		return retu;
	}

	/**
	 * get long
	 * 
	 * @param key
	 *            key
	 * @param defValue
	 *            defValue
	 * @return long
	 */
	public long getLong(String key, long defValue) {
		long retu = 0;
		try {
			String string = getString(key, defValue + "");
			retu = Long.parseLong(string);
		} catch (Exception e) {
		}
		return retu;
	}

	/**
	 * set string
	 * 
	 * @param key
	 *            key
	 * @param value
	 *            value
	 */
	public void setString(String key, String value) {
		try {
			SharedPreferences.Editor Home = AMContext.getApplication().getSharedPreferences(sharedPreferencesName, 0)
					.edit();
			Home.putString(key, AMDes.encrypt(value.getBytes(), key));
			Home.commit();
		} catch (Exception e) {
		}
	}

	/**
	 * set long
	 * 
	 * @param key
	 *            key
	 * @param defValue
	 *            defValue
	 */
	public void setLong(String key, long value) {
		setString(key, value + "");
	}

	/**
	 * set float
	 * 
	 * @param key
	 *            key
	 * @param value
	 *            value
	 */
	public void setFloat(String key, float value) {
		setString(key, value + "");
	}

	/**
	 * set boolean
	 * 
	 * @param key
	 *            key
	 * @param defValue
	 *            defValue
	 */
	public void setBoolean(String key, boolean value) {
		setString(key, value ? "Yes" : "No");
	}

	/**
	 * set int
	 * 
	 * @param key
	 *            key
	 * @param value
	 *            value
	 */
	public void setInt(String key, int value) {
		setString(key, value + "");
	}

	/**
	 * remove
	 * 
	 * @param key
	 *            key
	 */
	public void remove(String key) {
		try {
			SharedPreferences.Editor sp = AMContext.getApplication().getSharedPreferences(sharedPreferencesName, 0)
					.edit();
			sp.remove(key);
			sp.commit();
		} catch (Exception e) {
		}
	}
}
