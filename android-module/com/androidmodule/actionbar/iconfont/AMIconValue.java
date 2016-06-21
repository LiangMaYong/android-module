package com.androidmodule.actionbar.iconfont;

/**
 * LIConValue
 * 
 * @author LiangMaYong
 * @version 1.0
 */
public class AMIconValue {

	private int unicode_value;
	private String fontPath;

	public String getFontPath() {
		return fontPath;
	}

	public int getUnicodeValue() {
		return unicode_value;
	}

	public AMIconValue(String fontPath, int unicode_value) {
		this.fontPath = fontPath;
		this.unicode_value = unicode_value;
	}

}
