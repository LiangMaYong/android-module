package com.androidmodule.actionbar.iconfont;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Icon font
 * 
 * @author LiangMaYong
 * @version 1.0
 */
public class AMIconFont {
	private static Map<String, Typeface> typefaceMap = new HashMap<String, Typeface>();

	/**
	 * Get font
	 * 
	 * @return
	 */
	public static Typeface getFont(Context context, String fontPath) {
		loadFont(context, fontPath);
		if (typefaceMap.containsKey(fontPath)) {
			return typefaceMap.get(fontPath);
		}
		return null;
	}

	/**
	 * load font
	 * 
	 * @param context
	 */
	public static void loadFont(Context context, String fontPath) {
		if (typefaceMap.containsKey(fontPath)) {
			return;
		}
		try {
			Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
			typefaceMap.put(fontPath, tf);
		} catch (Exception e) {
		}
	}
}
