package com.androidmodule.actionbar.configs;

import com.androidmodule.actionbar.AMActionBar;

import android.content.Context;

public abstract class AMActionConfig {

	public AMActionConfig() {
	}

	public void attachActionBar(AMActionBar actionBar) {
		action_bar_height = dip2px(actionBar.getContext(), 50);
	}

	private int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	private boolean show = true;
	private int action_bar_show_anim = -1;
	private int action_bar_hide_anim = -1;
	private int action_bar_height = -1;
	private int action_item_padding = 10;
	private int action_item_text_size = 16;
	private int title_text_size = 18;
	private int sub_title_text_size = 12;
	private int action_bg_color = 0xff333333;

	private int title_color = 0xffffffff;

	private int line_color = 0x00ffffff;
	private int line_height = 1;
	private int sub_title_color = 0xdddddddd;
	private int progress_color = 0xff3399ff;
	private int progress_height = 2;

	private int action_item_color = 0xffffffff;
	private int action_item_pressed_color = 0xdddddddd;

	public final int getBackgroundColor() {
		return action_bg_color;
	}

	public final void setBackgroundColor(int action_bg_color) {
		this.action_bg_color = action_bg_color;
	}

	public final int getActionItemColor(boolean pressed) {
		if (pressed) {
			return action_item_pressed_color;
		}
		return action_item_color;
	}

	public final void setActionItemColor(int color, int pressed) {
		this.action_item_color = color;
		this.action_item_pressed_color = pressed;
	}

	public final int getSubTitleColor() {
		return sub_title_color;
	}

	public final void setSubTitleColor(int color) {
		this.sub_title_color = color;
	}

	public final int getTitleColor() {
		return title_color;
	}

	public void setTitleColor(int color) {
		this.title_color = color;
	}

	public final int getSubTitleSize() {
		return sub_title_text_size;
	}

	public final void setSubTitleSize(int size) {
		this.sub_title_text_size = size;
	}

	public final int getTitleSize() {
		return title_text_size;
	}

	public final void setTitleSize(int size) {
		this.title_text_size = size;
	}

	public final int getActionItemPadding() {
		return action_item_padding;
	}

	public final void setActionItemPadding(int padding) {
		this.action_item_padding = padding;
	}

	public final int getActionItemSize() {
		return action_item_text_size;
	}

	public final void setActionItemSize(int size) {
		this.action_item_text_size = size;
	}

	public final int getProgressColor() {
		return progress_color;
	}

	public final void setProgressColor(int color) {
		this.progress_color = color;
	}

	public int getProgressHeight() {
		return progress_height;
	}

	public void setProgressHeight(int height) {
		this.progress_height = height;
	}

	public int getLineColor() {
		return line_color;
	}

	public void setLineColor(int color) {
		this.line_color = color;
	}

	public int getLineHeight() {
		return line_height;
	}

	public void setLineHeight(int height) {
		this.line_height = height;
	}

	public int getActionHeightPx() {
		return action_bar_height;
	}

	public void setActionHeightPx(int heightPx) {
		this.action_bar_height = heightPx;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public int getActionBarHideAnim() {
		return action_bar_hide_anim;
	}

	public int getActionBarShowAnim() {
		return action_bar_show_anim;
	}

}
