package com.android.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

@SuppressLint("ViewConstructor")
public class BasePop extends PopupWindow {
	public BasePop(Context context, final View view) {
		super(context);
		setContentView(view);
		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(LayoutParams.MATCH_PARENT);
		setFocusable(true);

		ColorDrawable colordrawable = new ColorDrawable(0xb0000000);
		this.setBackgroundDrawable(colordrawable);
		view.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View view, MotionEvent ev) {
				int[] location = new int[2];
				view.getLocationOnScreen(location);
				int x = location[0];
				int y = location[1];
				if (ev.getX() < x || ev.getX() > (x + view.getWidth()) || ev.getY() < y
						|| ev.getY() > (y + view.getHeight())) {
				} else {
					BasePop.this.dismiss();
				}
				return true;
			}
		});
	}
}
