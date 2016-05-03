package com.android.user;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

public class AppUtil {
	/**
	 * 根据路径得到bitmap
	 * 
	 * @param path
	 * @param newWidth
	 * @param newHeight
	 * @return
	 */
	public static Bitmap getBitmap(String path, int newWidth, int newHeight) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inJustDecodeBounds = true;
		Bitmap bitmap = null;
		try {
			BitmapFactory.decodeStream(new FileInputStream(path), null, opt);
			int o_height = opt.outHeight;
			int o_width = opt.outWidth;

			int s = 1;
			// if (w > 0 && h > 0) {
			while ((o_width / s > newWidth) || (o_height / s > newHeight)) {
				s += 1;
			}
			// }
			opt = new BitmapFactory.Options();
			opt.inSampleSize = s;
			opt.inPurgeable = true;
			opt.inInputShareable = true;
			InputStream is = new FileInputStream(path);
			bitmap = BitmapFactory.decodeStream(is, null, opt);

			is.close();
			return bitmap;
		} catch (Exception e) {
			return null;
		}
	}

	public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		bmp.compress(CompressFormat.PNG, 100, output);
		if (needRecycle) {
			bmp.recycle();
		}

		byte[] result = output.toByteArray();
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
}
