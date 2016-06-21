package com.androidmodule.actionbar.configs;

import com.androidmodule.actionbar.AMActionBar;

public class AMActionGreen extends AMActionConfig {

	@Override
	public void attachActionBar(AMActionBar actionBar) {
		super.attachActionBar(actionBar);
		setBackgroundColor(0xff3399ff);
		setTitleColor(0xffffffff);
		setSubTitleColor(0xffffffff);
		setActionItemPadding(10);
		setProgressColor(0x99ffffff);
		setTitleSize(18);
		setActionItemSize(18);
		setLineHeight(1);
		setLineColor(0x05333333);
		setProgressHeight(1);
		setActionItemColor(0xffffffff, 0x90dddddd);
	}

}
