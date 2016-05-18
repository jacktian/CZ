package com.android.util;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class OntouchUtil {
	public static void changeLight(ImageView imageView, int brightness) {
		ColorMatrix cMatrix = new ColorMatrix();
		cMatrix.set(new float[] { 1, 0, 0, 0, brightness, 0, 1, 0, 0, brightness, 
				0, 0, 1, 0, brightness, 0, 0, 0, 1, 0 });
		imageView.setColorFilter(new ColorMatrixColorFilter(cMatrix));
	}

	public static OnTouchListener onTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View view, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				// GroupHolder holder = (GroupHolder) view.getTag();
				// onclick
				break;
			case MotionEvent.ACTION_DOWN:
				changeLight((ImageView) view, -50);
				break;
			case MotionEvent.ACTION_MOVE:
				changeLight((ImageView) view, 0);
				break;
			case MotionEvent.ACTION_CANCEL:
				changeLight((ImageView) view, 0);
				break;
			default:
				break;
			}
			return false;
		}

	};

}
