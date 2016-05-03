package com.android.user;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.net.URLEncoder;

import com.android.view.BottomBar;
import com.android.view.ZoomImageView;
import com.google.gson.stream.JsonReader;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

public class YysjActivity extends Activity {
	private Bitmap bitmap;
	private ZoomImageView imageView;
	// private LargeImageView imageView;
	LayoutInflater inflater;
	Dialog dialog1;
	String aaString;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yysj_activity);

		dialog1 = showDialogrefresh(this);
		BottomBar bottomBar = new BottomBar(this);
		getWithAndHeight();
		initImage();
	}

	public String citylist(String jsonData) throws Exception {
		String imagefile = "";
		JsonReader reader = new JsonReader(new StringReader(jsonData));
		try {
			reader.beginArray();
			while (reader.hasNext()) {
				reader.beginObject();
				while (reader.hasNext()) {
					String tagName3 = reader.nextName();
					if (tagName3.equals("imagefile")) {
						imagefile = reader.nextString();
					} else {
						reader.skipValue();
					}
				}
				reader.endObject();
			}
			reader.endArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return imagefile;
	}

	private void initImage() {
		imageView = (ZoomImageView) findViewById(R.id.imagebig);
		ViewGroup.LayoutParams lp = imageView.getLayoutParams();
		lp.width = (int) winwidth;
		lp.height = LayoutParams.WRAP_CONTENT;
		imageView.setLayoutParams(lp);
		// imageView.setScaleType(ScaleType.CENTER_INSIDE);
		FinalHttp FinalHttp1 = new FinalHttp();
		FinalHttp1.get("http://218.93.39.237:8082//TravelWebApi/api/ShowTime/?pagenum=1", new AjaxCallBack<Object>() {
			public void onSuccess(Object t) {
				super.onSuccess(t);
				try {
					aaString = citylist(t.toString()).replace("~", URLEncoder.encode("~"))
							.replace("时间表", URLEncoder.encode("时间表")).replace("副本", URLEncoder.encode("副本"));
					new DownloadImageTask().execute(aaString);
					// bitmap=((BitmapDrawable)loadImageFromNetwork(aaString)).getBitmap();
					// int bitwidth=bitmap.getWidth();
					// int bitheight=bitmap.getHeight();
					// int size=(int) (winwidth/bitwidth);
					// Bitmap bitmap1=Bitmap.createBitmap(bitmap, 0, 0, (int)
					// winwidth, bitheight*size);
					// Matrix matrix=new Matrix();
					// matrix.setScale(2.0f,2.0f);
					// imageView.setImageMatrix(matrix);
					// FinalBitmap
					// finalBitmap=FinalBitmap.create(YysjActivity.this);
					// finalBitmap.display(imageView, aaString);
				} catch (Exception e) {
				}
			}

			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				try {
					if (dialog1 != null) {
						dialog1.dismiss();
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
	}

	private Dialog showDialogrefresh(Context context) {
		if (inflater == null) {
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		View v = inflater.inflate(R.layout.dialogrefresh, null);
		dialog1 = new Dialog(context, R.style.translucent_notitle);
		dialog1.setContentView(v);
		dialog1.setCanceledOnTouchOutside(false);
		dialog1.show();
		return dialog1;
	}

	private class DownloadImageTask extends AsyncTask<String, Void, Drawable> {
		protected Drawable doInBackground(String... urls) {
			return loadImageFromNetwork(urls[0]);
		}

		protected void onPostExecute(Drawable result) {
			try {
				if (result == null) {
					bitmap = readBitMap(getBaseContext(), R.drawable.timetable2x);
//					imageZoom();

					imageView.setImageBitmap(bitmap);

				} else {
					BitmapDrawable bd = (BitmapDrawable) result;
					bitmap = bd.getBitmap();

					// imageView.setImageDrawable(bd);

					// getWithAndHeight();
//					imageZoom();
					// bitmap=changeBitmap(bitmap);
					// bitmap=zoomImage(bitmap, winwidth, winheight);
					// ByteArrayOutputStream mOutputStream=new
					// ByteArrayOutputStream();
					// bitmap.compress(Bitmap.CompressFormat.JPEG, 100,
					// mOutputStream);
					// byte [] bytearray=mOutputStream.toByteArray();
					// InputStream inputStream=new
					// ByteArrayInputStream(bytearray);
					imageView.setImageBitmap(bitmap);
					// getResources().openRawResource(R.drawable.timetable2x);
					// imageView.setInputStream(
					// getResources().openRawResource(R.drawable.timetable2x));
				}
			} catch (Exception e) {
			} finally {
				try {
					if (dialog1 != null) {
						dialog1.dismiss();
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
	}

	float winwidth;
	float winheight;

	private void getWithAndHeight() {

		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		winwidth = displayMetrics.widthPixels;
		winheight = displayMetrics.heightPixels;

	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	private void imageZoom() {
		// 图片允许最大空间 单位：KB
		double maxSize = 800.00;
		// 将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] b = baos.toByteArray();
		// 将字节换成KB
		double mid = b.length / 1024;
		// 判断bitmap占用空间是否大于允许最大空间 如果大于则压缩 小于则不压缩
		if (mid > maxSize) {
			// 获取bitmap大小 是允许最大大小的多少倍
			double i = mid / maxSize;
			// 开始压缩 此处用到平方根 将宽带和高度压缩掉对应的平方根倍
			// （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
			bitmap = zoomImage(bitmap, bitmap.getWidth() / Math.sqrt(i), bitmap.getHeight() / Math.sqrt(i));
		}

	}

	public Bitmap zoomImage(Bitmap bgimage, double newWidth, double newHeight) {
		// 获取这个图片的宽和高
		float width = bgimage.getWidth();
		float height = bgimage.getHeight();

		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 计算宽高缩放率
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleWidth);
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width, (int) height, matrix, true);
		return bitmap;
	}

	private Bitmap changeBitmap(Bitmap bgimage) {
		// 获取这个图片的宽和高

		float width = bgimage.getWidth();
		float height = bgimage.getHeight();
		float simplesize = 1;
		simplesize = winwidth / width;

		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 计算宽高缩放率
		float scaleWidth = simplesize;
		float scaleHeight = simplesize;
		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width, (int) height, matrix, true);

		return bitmap;
	}

	private Drawable loadImageFromNetwork(String imageUrl) {
		Drawable drawable = null;
		try {
			drawable = Drawable.createFromStream(new URL(imageUrl).openStream(), "image.jpg");
		} catch (IOException e) {
		}
		return drawable;
	}

	public static Bitmap readBitMap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}

	public class MulitPointTouchListener implements OnTouchListener {
		Matrix matrix = new Matrix();
		Matrix savedMatrix = new Matrix();
		public ImageView image;
		static final int NONE = 0;
		static final int DRAG = 1;
		static final int ZOOM = 2;
		int mode = NONE;
		PointF start = new PointF();
		PointF mid = new PointF();
		float oldDist = 1f;

		public MulitPointTouchListener(ImageView image) {
			super();
			this.image = image;
		}

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			this.image.setScaleType(ScaleType.MATRIX);
			ImageView view = (ImageView) v;
			// dumpEvent(event);
			switch (event.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
				savedMatrix.set(matrix);
				start.set(event.getX(), event.getY());
				mode = DRAG;
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				oldDist = spacing(event);
				if (oldDist > 10f) {
					savedMatrix.set(matrix);
					midPoint(mid, event);
					mode = ZOOM;
				}
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_POINTER_UP:
				mode = NONE;
				break;
			case MotionEvent.ACTION_MOVE:
				if (mode == DRAG) {
					matrix.set(savedMatrix);
					matrix.postTranslate(event.getX() - start.x, event.getY() - start.y);
				} else if (mode == ZOOM) {
					float newDist = spacing(event);
					if (newDist > 10f) {
						matrix.set(savedMatrix);
						float scale = newDist / oldDist;
						matrix.postScale(scale, scale, mid.x, mid.y);
					}
				}
				break;
			}
			view.setImageMatrix(matrix);
			return true;
		}

		private float spacing(MotionEvent event) {
			float x = event.getX(0) - event.getX(1);
			float y = event.getY(0) - event.getY(1);
			return FloatMath.sqrt(x * x + y * y);
		}

		private void midPoint(PointF point, MotionEvent event) {
			float x = event.getX(0) + event.getX(1);
			float y = event.getY(0) + event.getY(1);
			point.set(x / 2, y / 2);
		}
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		if (bitmap != null) {
			if (!bitmap.isRecycled()) {
				bitmap.recycle();
				bitmap = null;
				System.gc();
			}
		}
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (bitmap != null) {
			if (!bitmap.isRecycled()) {
				bitmap.recycle();
				bitmap = null;
				System.gc();
			}
		}
	}
}
