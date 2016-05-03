package com.android.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class WaterUtils {
	public static Bitmap createBitmap(Bitmap src, Bitmap waterMak, String title) {
		if (src == null) {
			return src;
		}
		int w = src.getWidth();
		int h = src.getHeight();
		int ww = waterMak.getWidth();
		int wh = waterMak.getHeight();
		Log.i("jiangqq", "w = " + w + ",h = " + h + ",ww = " + ww + ",wh = " + wh);
		Bitmap newBitmap = Bitmap.createBitmap(400, 300, Config.ARGB_8888);
		Canvas mCanvas = new Canvas(newBitmap);
		mCanvas.drawBitmap(src, w, h, null);
		Paint paint = new Paint();
		// paint.setAlpha(100);
		mCanvas.drawBitmap(waterMak, 50, 50, paint);
		/*
		 * if (null != title) { Paint textPaint = new Paint();
		 * textPaint.setColor(Color.RED); textPaint.setTextSize(16); String
		 * familyName = "宋体"; Typeface typeface = Typeface.create(familyName,
		 * Typeface.BOLD_ITALIC); textPaint.setTypeface(typeface);
		 * textPaint.setTextAlign(Align.CENTER); mCanvas.drawText(title, w / 2,
		 * 25, textPaint);
		 * 
		 * }
		 */
		mCanvas.save(Canvas.ALL_SAVE_FLAG);
		mCanvas.restore();
		return newBitmap;
	}

}
