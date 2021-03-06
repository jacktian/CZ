package com.android.user;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

final class SaveThread extends Thread {

	private static final String TAG = "SaveThread";

	public Handler mHandler;

	private final CameraPreview mActivity;

	SaveThread(CameraPreview activity) {
		mActivity = activity;
	}

	@Override
	public void run() {
		Looper.prepare();
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message message) {
				switch (message.what) {
				case R.id.save:
					save((byte[]) message.obj, message.arg1, message.arg2);
					break;
				case R.id.quit:
					Looper.myLooper().quit();
					break;
				}
			}
		};
		Looper.loop();
	}

	// Save the center rectangle of the Y channel as a greyscale PNG to the SD
	// card.
	private void save(byte[] data, int width, int height) {
		// Rect framingRect = CameraManager.get().getFramingRect();
		// int framingWidth = framingRect.width();
		// int framingHeight = framingRect.height();
		// if (framingWidth > width || framingHeight > height) {
		// throw new IllegalArgumentException();
		// }
		//
		// int leftOffset = framingRect.left;
		// int topOffset = framingRect.top;
		// int[] colors = new int[framingWidth * framingHeight];
		//
		// for (int y = 0; y < framingHeight; y++) {
		// int rowOffset = (y + topOffset) * width + leftOffset;
		// for (int x = 0; x < framingWidth; x++) {
		// int pixel = (int) data[rowOffset + x];
		// pixel = 0xff000000 + (pixel << 16) + (pixel << 8) + pixel;
		// colors[y * framingWidth + x] = pixel;
		// }
		// }
		//
		// Bitmap bitmap = Bitmap.createBitmap(colors, framingWidth,
		// framingHeight,
		// Bitmap.Config.ARGB_8888);
		// OutputStream outStream = getNewPhotoOutputStream();
		// if (outStream != null) {
		// bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
		// try {
		// outStream.close();
		// Message message = Message.obtain(mActivity.mHandler,
		// R.id.save_succeeded);
		// message.sendToTarget();
		// return;
		// } catch (IOException e) {
		// Log.e(TAG, "Exception closing stream: " + e.toString());
		// }
		// }
		//
		Message message = Message.obtain(mActivity.mHandler, R.id.save_failed);
		message.sendToTarget();
	}
	//
	// private static OutputStream getNewPhotoOutputStream() {
	// File sdcard = new File("/sdcard");
	// if (sdcard.exists()) {
	// File barcodes = new File(sdcard, "barcodes");
	// if (barcodes.exists()) {
	// if (!barcodes.isDirectory()) {
	// Log.e(TAG, "/sdcard/barcodes exists but is not a directory");
	// return null;
	// }
	// } else {
	// if (!barcodes.mkdir()) {
	// Log.e(TAG, "Could not create /sdcard/barcodes directory");
	// return null;
	// }
	// }
	// Date now = new Date();
	// String fileName = now.getTime() + ".png";
	// try {
	// return new FileOutputStream(new File(barcodes, fileName));
	// } catch (FileNotFoundException e) {
	// Log.e(TAG, "Could not create FileOutputStream");
	// }
	// } else {
	// Log.e(TAG, "/sdcard does not exist");
	// }
	// return null;
	// }

}
