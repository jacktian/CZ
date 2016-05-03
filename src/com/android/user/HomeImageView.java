package com.android.user;

import java.lang.reflect.Field;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class HomeImageView extends ImageView {
	public AnimationDrawable animationDrawable;
	public Field field;

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub

		try {
			java.lang.reflect.Field field = AnimationDrawable.class.getDeclaredField("mCurFrame");
			field.setAccessible(true);
			int curFrame = field.getInt(animationDrawable);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		super.onDraw(canvas);
	}

	public HomeImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

}
