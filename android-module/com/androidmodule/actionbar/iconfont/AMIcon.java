package com.androidmodule.actionbar.iconfont;

import android.content.Context;

/**
 * AMIcon
 *
 * @author LiangMaYong
 * @version 1.0
 **/
public final class AMIcon {

	private AMIcon() {
	}

	private static final String FONT_PATH = "fonts/android_module.ttf";

	public static void loadFont(Context context) {
		AMIconFont.loadFont(context, FONT_PATH);
	}

	public static final AMIconValue icon_collect = new AMIconValue(FONT_PATH, 0xe617);
	public static final AMIconValue icon_back = new AMIconValue(FONT_PATH, 0xe614);
	public static final AMIconValue icon_scan = new AMIconValue(FONT_PATH, 0xe609);
	public static final AMIconValue icon_cart = new AMIconValue(FONT_PATH, 0xe60b);
	public static final AMIconValue icon_ontup = new AMIconValue(FONT_PATH, 0xe603);
	public static final AMIconValue icon_close = new AMIconValue(FONT_PATH, 0xe616);
	public static final AMIconValue icon_checkmark = new AMIconValue(FONT_PATH, 0xe615);
	public static final AMIconValue icon_top = new AMIconValue(FONT_PATH, 0xe624);
	public static final AMIconValue icon_address = new AMIconValue(FONT_PATH, 0xe613);
	public static final AMIconValue icon_history = new AMIconValue(FONT_PATH, 0xe61c);
	public static final AMIconValue icon_coupon = new AMIconValue(FONT_PATH, 0xe62a);
	public static final AMIconValue icon_uparrow = new AMIconValue(FONT_PATH, 0xe626);
	public static final AMIconValue icon_setting = new AMIconValue(FONT_PATH, 0xe61d);
	public static final AMIconValue icon_ontaddcart = new AMIconValue(FONT_PATH, 0xe612);
	public static final AMIconValue icon_unfoldfill = new AMIconValue(FONT_PATH, 0xe62e);
	public static final AMIconValue icon_unfold = new AMIconValue(FONT_PATH, 0xe611);
	public static final AMIconValue icon_share = new AMIconValue(FONT_PATH, 0xe623);
	public static final AMIconValue icon_fold = new AMIconValue(FONT_PATH, 0xe60c);
	public static final AMIconValue icon_order = new AMIconValue(FONT_PATH, 0xe60e);
	public static final AMIconValue icon_red = new AMIconValue(FONT_PATH, 0xe629);
	public static final AMIconValue icon_addressfill = new AMIconValue(FONT_PATH, 0xe62d);
	public static final AMIconValue icon_new = new AMIconValue(FONT_PATH, 0xe628);
	public static final AMIconValue icon_personalcenter = new AMIconValue(FONT_PATH, 0xe60f);
	public static final AMIconValue icon_ontdown = new AMIconValue(FONT_PATH, 0xe604);
	public static final AMIconValue icon_droparrow = new AMIconValue(FONT_PATH, 0xe618);
	public static final AMIconValue icon_edit = new AMIconValue(FONT_PATH, 0xe619);
	public static final AMIconValue icon_refresh = new AMIconValue(FONT_PATH, 0xe620);
	public static final AMIconValue icon_fire = new AMIconValue(FONT_PATH, 0xe62b);
	public static final AMIconValue icon_right = new AMIconValue(FONT_PATH, 0xe622);
	public static final AMIconValue icon_home = new AMIconValue(FONT_PATH, 0xe60d);
	public static final AMIconValue icon_remind = new AMIconValue(FONT_PATH, 0xe621);
	public static final AMIconValue icon_collectselected = new AMIconValue(FONT_PATH, 0xe627);
	public static final AMIconValue icon_gift = new AMIconValue(FONT_PATH, 0xe600);
	public static final AMIconValue icon_news = new AMIconValue(FONT_PATH, 0xe61e);
	public static final AMIconValue icon_gift1 = new AMIconValue(FONT_PATH, 0xe61b);
	public static final AMIconValue icon_notice = new AMIconValue(FONT_PATH, 0xe62c);
	public static final AMIconValue icon_search = new AMIconValue(FONT_PATH, 0xe610);

}