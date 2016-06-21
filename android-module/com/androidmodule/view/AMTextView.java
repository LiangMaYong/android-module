package com.androidmodule.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * AMTextView
 * 
 * @author LiangMaYong
 * @version 1.0
 */
public class AMTextView extends TextView {

	public AMTextView(Context context) {
		super(context);
	}

	public AMTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AMTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/**
	 * set textColor
	 * 
	 * @param color
	 *            color
	 * @param pressed
	 *            pressed
	 */
	public void setTextColor(int color, int pressed) {
		int statePressed = android.R.attr.state_pressed;
		int stateFocesed = android.R.attr.state_focused;
		int[][] state = { { statePressed }, { -statePressed }, { stateFocesed }, { -stateFocesed } };
		ColorStateList colors = new ColorStateList(state, new int[] { pressed, color, pressed, color });
		setTextColor(colors);
	}

}
