package com.androidmodule.base.interfaces;

import com.androidmodule.base.AMFragment;

/**
 * ICloseFragment
 * 
 * @author LiangMaYong
 * @version 1.0
 */
public interface ICloseFragment {
	void close(AMFragment fragment, boolean back);

	void show(AMFragment fragment);
}
