package com.android.user;

/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.android.util.FileUtils;
import com.android.view.MyGallery;
import com.android.view.PageIndicatorView;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

// ----------------------------------------------------------------------
public class CameraPreview extends Activity_Base implements SurfaceHolder.Callback, OnClickListener {
	// private SurfaceView mSurfaceView;
	// private SurfaceHolder mSurfaceHolder;
	// private boolean bifPrieview = false;
	// private Camera mCamera;
	public int ScrrenWidth;
	public int ScrrenHeight;
	Button btn_back;
	private Button btn_pic;
	TextView btn_takephoto;
	LinearLayout layout_takephoto;

	private static int PicPhotoCount = -1;
	ProgressDialog m_pDialog;
	MyGallery gallery;
	PageIndicatorView view_page;
	ArrayList<String> imgLists;
	int isSelectPos = 0;
	WaterImgGalleryAdapter imgGalleryAdapter;
	private CameraPreview instanceCameraPreview;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Window window = getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_camera);
		this.instanceCameraPreview = this;
		CameraManager.init(getApplication());
		// DisplayMetrics dm = new DisplayMetrics();
		// getWindowManager().getDefaultDisplay().getMetrics(dm);
		// ScrrenWidth = dm.widthPixels;
		// ScrrenHeight=dm.heightPixels;
		// mSurfaceView = (SurfaceView) findViewById(R.id.surface_camera);
		btn_back = (Button) findViewById(R.id.btn_back);
		btn_takephoto = (TextView) findViewById(R.id.btn_takephoto);
		btn_pic = (Button) findViewById(R.id.btn_pic);
		layout_takephoto = (LinearLayout) findViewById(R.id.layout_takephoto);
		// mSurfaceHolder = mSurfaceView.getHolder();
		// mSurfaceHolder.addCallback(CameraPreview.this);
		// mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surface_camera);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		btn_back.setOnClickListener(this);
		btn_takephoto.setOnClickListener(this);
		btn_pic.setOnClickListener(this);
		layout_takephoto.setOnClickListener(this);
		PicPhotoCount = 0;
		initData();
		gallery = (MyGallery) findViewById(R.id.gallery);
		view_page = (PageIndicatorView) findViewById(R.id.view_page);
		view_page.setTotalPage(getImgLists().size());
		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
				isSelectPos = position;
				view_page.setCurrentPage(position);
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		imgGalleryAdapter = new WaterImgGalleryAdapter(imgLists, imageLoader);
		gallery.setAdapter(imgGalleryAdapter);
	}

	private void initData() {
		// String imageUri = "drawable://" + R.drawable.image; // from drawables
		// (only images, non-9patch)
		getImgLists().add("drawable://" + R.drawable.cqtx);
		getImgLists().add("drawable://" + R.drawable.jlh);
		getImgLists().add("drawable://" + R.drawable.mezy);
		getImgLists().add("drawable://" + R.drawable.mhgz);
		getImgLists().add("drawable://" + R.drawable.msdl);
		getImgLists().add("drawable://" + R.drawable.sds);
		getImgLists().add("drawable://" + R.drawable.tbdj);
		getImgLists().add("drawable://" + R.drawable.xjcs);
		getImgLists().add("drawable://" + R.drawable.lkwg);
		getImgLists().add("drawable://" + R.drawable.hxsl);
		getImgLists().add("drawable://" + R.drawable.wmssj);
		m_pDialog = new ProgressDialog(CameraPreview.this);
		m_pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		m_pDialog.setMessage("正在保存图片");
		m_pDialog.setIndeterminate(true);
		m_pDialog.setCancelable(true);
	}

	public ArrayList<String> getImgLists() {
		if (imgLists == null) {
			imgLists = new ArrayList<String>(1);
		}
		return imgLists;
	}

	public void setImgLists(ArrayList<String> imgLists) {
		this.imgLists = imgLists;
	}

	public void onClick(View v) {
		if (v == btn_back) {
			btn_back.setClickable(false);
			btn_takephoto.setClickable(false);
			layout_takephoto.setClickable(false);
			finish();
		} else if (v == btn_takephoto || v == layout_takephoto) {
			btn_back.setClickable(false);
			btn_takephoto.setClickable(false);
			layout_takephoto.setClickable(false);
			m_pDialog.show();
			loadImage2();
		} else if (v == btn_pic) {
			if (PicPhotoCount == 0) {
				PicPhotoCount++;
				CameraManager.get().requestPreviewFrame(mHandler, R.id.save, instanceCameraPreview);
			}
		}
	}

	private void loadImage2() {
		Thread thread = new Thread() {
			public void run() {
				try {
					Message message = new Message();
					message.what = 1;
					handler2.sendMessage(message);
				} catch (Exception e) {
				} finally {
					m_pDialog.dismiss();
				}
			}
		};
		thread.start();
		thread = null;
	}

	final Handler handler2 = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				CameraManager.get().requestPreviewFrame(mHandler, R.id.save, instanceCameraPreview);
			}
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onPause() {
		super.onPause();
		CameraManager.get().stopPreview();
		// if (mSaveThread != null) {
		// Message quit = Message.obtain(mSaveThread.mHandler, R.id.quit);
		// quit.sendToTarget();
		// try {
		// mSaveThread.join();
		// } catch (InterruptedException e) {
		// }
		// mSaveThread = null;
		// }
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// if (mSaveThread == null ) {
		// mSaveThread = new SaveThread(this);
		// mSaveThread.start();
		// }
	}

	Bitmap resultbitmap;
	Bitmap bm1;
	Bitmap bm;
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message message) {
			switch (message.what) {
			case R.id.save:
				try {
					byte buf[] = (byte[]) message.obj;
					bm = BitmapFactory.decodeByteArray(buf, 0, buf.length);
					// Bitmap bitmap = ImageUtils.getScaleImage(bm);
					int res = 0;
					switch (isSelectPos) {
					// getImgLists().add("drawable://"+R.drawable.cqtx);
					// getImgLists().add("drawable://"+R.drawable.jlh);
					// getImgLists().add("drawable://"+R.drawable.mezy);
					// getImgLists().add("drawable://"+R.drawable.mhgz);
					// getImgLists().add("drawable://"+R.drawable.msdl);
					// getImgLists().add("drawable://"+R.drawable.sds);
					// getImgLists().add("drawable://"+R.drawable.tbdj);
					// getImgLists().add("drawable://"+R.drawable.xjcs);
					//

					case 0:
						res = R.drawable.cqtx;
						break;
					case 1:
						res = R.drawable.jlh;
						break;
					case 2:
						res = R.drawable.mezy;
						break;
					case 3:
						res = R.drawable.mhgz;
						break;
					case 4:
						res = R.drawable.msdl;
						break;

					case 5:
						res = R.drawable.sds;
						break;
					case 6:
						res = R.drawable.tbdj;
						break;
					case 7:
						res = R.drawable.xjcs;
						break;
					case 8:
						res = R.drawable.lkwg;
						break;
					case 9:
						res = R.drawable.hxsl;
						break;
					case 10:
						res = R.drawable.wmssj;
						break;
					default:
						break;
					}
					BitmapDrawable bd = (BitmapDrawable) MapApplication.getInstance().getResources().getDrawable(res);
					bm1 = bd.getBitmap();
					resultbitmap = watermarkBitmap(bm, bm1, "");

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
					FileUtils.saveFile(resultbitmap, sdf.format(new Date(System.currentTimeMillis())) + "1.jpg");

				} catch (Exception e) {
					Toast.makeText(CameraPreview.this, "拍照错误,请重新拍摄", Toast.LENGTH_SHORT).show();
					btn_back.setClickable(true);
					btn_takephoto.setClickable(true);
					layout_takephoto.setClickable(true);
				} finally {
					if (resultbitmap != null && !resultbitmap.isRecycled()) {
						resultbitmap.recycle();
					}
					if (bm != null && !bm.isRecycled()) {
						bm.recycle();
					}
					if (bm1 != null && !bm1.isRecycled()) {
						bm1.recycle();
					}
					finish();
				}
				break;
			}
		}
	};

	// public Bitmap getBitmapByDrawable(int res) {
	// BitmapDrawable bd = (BitmapDrawable)
	// MapApplication.getInstance().getResources().getDrawable(res);
	// Bitmap bm = bd.getBitmap();
	// return bm;
	// }
	public boolean isHasSDCard() {
		boolean isHasSDCard = false;
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			isHasSDCard = true;
		}
		return isHasSDCard;
	}

	/*
	 * ��ȡ������յ�ͼƬ��ַ
	 */
	public String getCameraFileName(String srcPath) {
		String picPathString = "";
		Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
		String month = String.valueOf(c.get(Calendar.MONTH) + 1);
		String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
		String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
		String minute = String.valueOf(c.get(Calendar.MINUTE));
		String second = String.valueOf(c.get(Calendar.SECOND));
		String name = "IMG_" + year + "_" + month + "_" + day + "_" + hour + "_" + minute + "_" + second;
		picPathString = srcPath; // ��ʱͼƬ�洢��ַ
		if (isHasSDCard()) {
			File destDir = new File(picPathString);
			if (!destDir.exists()) {
				destDir.mkdirs();
			}
		}
		picPathString = picPathString + name + ".jpg";
		return picPathString;
	}

	/**
	 * ��ˮӡ Ҳ���Լ�����
	 * 
	 * @param src
	 * @param watermark
	 * @param title
	 * @return
	 */
	public Bitmap watermarkBitmap(Bitmap src, Bitmap watermark, String title) {
		if (src == null) {
			return null;
		}
		int w = src.getWidth();
		int h = src.getHeight();
		// ��Ҫ����ͼƬ̫����ɵ��ڴ泬�������,�����ҵ�ͼƬ��С���Բ�д��Ӧ������
		Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);// ����һ���µĺ�SRC���ȿ��һ���λͼ
		Canvas cv = new Canvas(newb);
		cv.drawBitmap(src, 0, 0, null);// �� 0��0��꿪ʼ����src
		Paint paint = new Paint();
		// ����ͼƬ
		if (watermark != null) {
			int ww = watermark.getWidth();
			int wh = watermark.getHeight();
			// paint.setAlpha(50);
			// paint.setAlpha(100);
			// cv.drawBitmap(watermark, w - ww + 5, h - wh + 5, paint);//
			// ��src�����½ǻ���ˮӡ
			// cv.drawBitmap(watermark, 0, 0, paint);// ��src�����Ͻǻ���ˮӡ
			cv.drawBitmap(watermark, (w - ww + 5) / 2, h - wh + 5, paint);
		} else {
			Log.i("i", "water mark failed");
		}
		// ��������
		if (title != null) {
			String familyName = "����";
			Typeface font = Typeface.create(familyName, Typeface.NORMAL);
			TextPaint textPaint = new TextPaint();
			textPaint.setColor(Color.RED);
			textPaint.setTypeface(font);
			textPaint.setTextSize(40);

			cv.drawText(title, w - 400, h - 40, textPaint);
		}
		cv.save(Canvas.ALL_SAVE_FLAG);
		cv.restore();
		if (src != null && !src.isRecycled()) {
			src.recycle();
		}
		if (watermark != null && !watermark.isRecycled()) {
			watermark.recycle();
		}
		return newb;
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// CameraManager.get().DriverSurfaceChanged(holder,format, width,
		// height);
		// Log.d("digilinx-Camera surfaceChanged",
		// "width="+width+"height="+height+"Format="+format);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		// try {
		// initCamera(holder);
		// } catch (IOException e) {
		// throw new RuntimeException(e);
		// }
		try {
			CameraManager.get().openDriver(holder);
			CameraManager.get().startPreview();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
	}
	// void initCamera(SurfaceHolder holder)throws IOException {
	//
	// if (!bifPrieview) {
	// if(mCamera==null){
	// mCamera = Camera.open();
	// }
	// if(mCamera==null)
	// {
	// throw new IOException();
	// }
	// }
	// if (mCamera != null && !bifPrieview) {
	// Camera.Parameters p = mCamera.getParameters();
	// p.setPictureFormat(PixelFormat.JPEG);
	// p.setPictureSize(ScrrenWidth, ScrrenHeight);
	// mCamera.setParameters(p);
	// try {
	// mCamera.setPreviewDisplay(holder);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// mCamera.startPreview();
	// bifPrieview = true;
	// }
	//
	// }
	// private void takePicture() {
	// mCamera.takePicture(shuuterCallback, rawCallback, jpegCallback);
	// }
	// private void resetCamera() {
	// if (mCamera != null && bifPrieview) {
	// mCamera.stopPreview();
	// mCamera = null;
	// bifPrieview = false;
	// }
	// }
	// private ShutterCallback shuuterCallback = new ShutterCallback() {
	//
	// public void onShutter() {
	//
	// }
	//
	// };
	// private PictureCallback jpegCallback = new PictureCallback() {
	//
	// public void onPictureTaken(byte[] data, Camera camera) {
	// getIntent().putExtra("pic", data);
	// setResult(20, getIntent());
	// finish();
	// }
	//
	// };
	// private PictureCallback rawCallback = new PictureCallback() {
	//
	// public void onPictureTaken(byte[] data, Camera camera) {
	//
	// }
	//
	// };
	// public void stopPreview() {
	// if(mCamera!=null&&bifPrieview){
	// mCamera.stopPreview();
	// bifPrieview=false;
	// }
	// }
	//
	// public void closeDriver() {
	// if (mCamera != null) {
	// mCamera.release();
	// mCamera = null;
	// }
	//
	// }
	// private void displayFrameworkBugMessageAndExit() {
	// AlertDialog.Builder builder = new AlertDialog.Builder(this);
	// builder.setTitle(getString(R.string.app_name));
	// builder.setMessage(getString(R.string.msg_camera_framework_bug));
	// builder.setPositiveButton("ȷ��", new FinishListener(this));
	// builder.setOnCancelListener(new FinishListener(this));
	// builder.show();
	//
	// public void onError(int error, Camera camera) {
	// if(error==android.hardware.Camera.CAMERA_ERROR_SERVER_DIED)
	// {
	// Log.d(TAG, "CAMERA_ERROR_SERVER_DIED");
	// camera.release();
	// camera = null;
	//
	//
	// AlertDialog.Builder builder = new AlertDialog.Builder(this);
	// builder.setTitle(getString(R.string.app_name));
	// builder.setMessage("CAMERA_ERROR_SERVER_DIED");
	// builder.setPositiveButton("ȷ��", new FinishListener(this));
	// builder.setOnCancelListener(new FinishListener(this));
	// builder.show();
	// }
	// else if(error==android.hardware.Camera.CAMERA_ERROR_UNKNOWN)
	// {
	// Log.d(TAG, "CAMERA_ERROR_UNKNOWN");
	// camera.release();
	// camera = null;
	//
	// AlertDialog.Builder builder = new AlertDialog.Builder(this);
	// builder.setTitle(getString(R.string.app_name));
	// builder.setMessage("CAMERA_ERROR_UNKNOWN");
	// builder.setPositiveButton("ȷ��", new FinishListener(this));
	// builder.setOnCancelListener(new FinishListener(this));
	// builder.show();
	// }
	//
	// }
}
