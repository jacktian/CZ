package com.android.common;

import java.text.SimpleDateFormat;

import android.graphics.Bitmap;

public class BitmapBean {
	private SimpleDateFormat time;
	private Bitmap bitmap;

	public SimpleDateFormat getTime() {
		if (time != null) {
			return time;
		} else {
			return null;
		}
	}

	public void setTime(SimpleDateFormat sdf) {
		this.time = time;
	}

	public Bitmap getBitmap() {
		if (bitmap != null) {
			return bitmap;
		} else
			return null;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

}
