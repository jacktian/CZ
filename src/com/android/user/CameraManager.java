package com.android.user;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.widget.Toast;

final class CameraManager {
	private static CameraManager cameraManager;
	private Camera camera;
	private final Context context;
	private boolean af;
	private boolean foucs;
	private boolean previewing;
	private Handler autoFocusHandler;
	private Handler previewHandler;
	private int previewMessage;
	private CameraPreview instanceCameraPreview;

	public static void init(Context context) {
		if (cameraManager == null) {
			cameraManager = new CameraManager(context);
		}
	}

	public static CameraManager get() {
		return cameraManager;
	}

	private CameraManager(Context context) {
		this.context = context;
		camera = null;
		previewing = false;
	}

	@SuppressLint("NewApi")
	public String openDriver(SurfaceHolder holder) throws IOException {
		String result = null;
		if (camera == null) {
			camera = Camera.open();
			camera.setPreviewDisplay(holder);
			camera.setDisplayOrientation(90);
			// Camera.Parameters p =camera.getParameters();
			// p.setPreviewFormat(PixelFormat.JPEG);
			// p.setPreviewSize(CameraPreview.ScrrenWidth,
			// CameraPreview.ScrrenHeight);
			// p.setPictureSize(CameraPreview.ScrrenWidth,
			// CameraPreview.ScrrenHeight);
			// camera.setParameters(p);
		}
		return result;
	}

	public void closeDriver() {
		if (camera != null) {
			camera.release();
			camera = null;
		}
	}

	public void startPreview() {
		if (camera != null && !previewing) {
			camera.startPreview();
			previewing = true;
		}
	}

	public void stopPreview() {
		if (camera != null && previewing) {
			// if (!useOneShotPreviewCallback) {
			// camera.setPreviewCallback(null);
			// }
			camera.stopPreview();
			previewHandler = null;
			autoFocusHandler = null;
			previewing = false;
		}
	}

	public void requestPreviewFrame(Handler handler, int message, final CameraPreview instanceCameraPreview) {
		this.instanceCameraPreview = instanceCameraPreview;
		if (camera != null && previewing) {
			previewHandler = handler;
			previewMessage = message;

			camera.autoFocus(new AutoFocusCallback() {
				public void onAutoFocus(boolean success, Camera camera1) {
					// TODO Auto-generated method stub
					// success为true表示对焦成功
					if (success) {
						if (camera1 != null) {
							camera1.takePicture(null, null, jpegCallback);
						} else {
							Toast.makeText(context, "拍照错误,请重新拍摄", Toast.LENGTH_SHORT).show();
							instanceCameraPreview.btn_back.setClickable(true);
							instanceCameraPreview.btn_takephoto.setClickable(true);
							instanceCameraPreview.layout_takephoto.setClickable(true);
						}
					} else {
						Toast.makeText(context, "拍照错误,请重新拍摄", Toast.LENGTH_SHORT).show();
						instanceCameraPreview.btn_back.setClickable(true);
						instanceCameraPreview.btn_takephoto.setClickable(true);
						instanceCameraPreview.layout_takephoto.setClickable(true);
					}
				}
			});
		} else {
			Toast.makeText(context, "拍照错误,请重新拍摄", Toast.LENGTH_SHORT).show();
			instanceCameraPreview.btn_back.setClickable(true);
			instanceCameraPreview.btn_takephoto.setClickable(true);
			instanceCameraPreview.layout_takephoto.setClickable(true);
		}
	}

	public void requestAutoFocus(Handler handler, int message) {
	}

	private ShutterCallback shuuterCallback = new ShutterCallback() {
		public void onShutter() {
		}
	};
	ProgressDialog alertDialog;
	private PictureCallback jpegCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			if (previewHandler != null) {
				try {
					// int bmW = bt.getWidth();
					// int bmH = bt.getHeight();
					// //设置图片比例
					// double scale = 0.8;
					// //计算缩小比例
					// scaleW = (float)(scaleW*scale);
					// scaleH = (float)(scaleH*scale);
					// //按照比例缩小后生成一个Bitmap
					// Matrix matrix = new Matrix();
					// matrix.postScale(scaleW, scaleH);
					//
					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inJustDecodeBounds = true;
					// 获取这个图片的宽和高
					Bitmap $bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
					options.inJustDecodeBounds = false;
					// 计算缩放比
					int be = (int) (options.outHeight / (float) 500);
					if (be <= 0)
						be = 1;
					options.inSampleSize = be;
					// 重新读入图片，注意这次要把options.inJustDecodeBounds设为false哦
					$bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
					// Bitmap $bitmap = BitmapFactory.decodeByteArray(data, 0,
					// data.length);
					// int sizew= ScreenUtils.getInstance().getWidth();
					// int sizeh= ScreenUtils.getInstance().getHeight();
					// float scaleWidth =(float)($bitmap.getWidth()*0.5);
					// float scaleHeight = (float)($bitmap.getHeight()*0.5);
					Matrix matrix = new Matrix();
					// matrix.postScale(scaleWidth, scaleHeight);
					matrix.setRotate(90);
					Bitmap resizedBitmap = Bitmap.createBitmap($bitmap, 0, 0, $bitmap.getWidth(), $bitmap.getHeight(),
							matrix, true);
					if ($bitmap != null && !$bitmap.isRecycled()) {
						$bitmap.recycle();
					}
					ByteArrayOutputStream out = new ByteArrayOutputStream(data.length);
					resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);
					byte[] array = out.toByteArray();
					out.flush();
					out.close();
					Message message = previewHandler.obtainMessage(previewMessage, array);
					message.sendToTarget();
					previewHandler = null;
					System.gc();
				} catch (Exception ex) {
					Toast.makeText(context, "拍照错误,请重新拍摄", Toast.LENGTH_SHORT).show();
					instanceCameraPreview.btn_back.setClickable(true);
					instanceCameraPreview.btn_takephoto.setClickable(true);
					instanceCameraPreview.layout_takephoto.setClickable(true);
				}
			} else {
				Toast.makeText(context, "拍照错误,请重新拍摄", Toast.LENGTH_SHORT).show();
				instanceCameraPreview.btn_back.setClickable(true);
				instanceCameraPreview.btn_takephoto.setClickable(true);
				instanceCameraPreview.layout_takephoto.setClickable(true);
			}
		}
	};
	private PictureCallback rawCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
		}
	};
}
