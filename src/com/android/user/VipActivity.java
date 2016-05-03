package com.android.user;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.android.common.IntentAction;
import com.android.view.BottomBar;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
//import com.tencent.mm.sdk.openapi.WXImageObject;
//import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.sso.UMWXHandler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import net.tsz.afinal.FinalBitmap;

public class VipActivity extends Activity {
	private ImageView iView, vipbottom;
	private ImageView imageView;
	private ListView imglist;
	private Bitmap bitmap, listbitmap;
	private ImageView cameraicon;
	private Bitmap mWater, img;
	private List<Bitmap> list;
	private ListAdapter adapter;
	private View view;
	private PopupWindow popupWindow;
	private Button delete;
	private Button cancel;
	private Button share;
	private int ii;
	private int argitem = 0;
	View view_main;
	private View view_share;
	private ImageView shareImg;
	private PopupWindow popupWindow_share;
	private ImageView sinaImg;
	private ImageView weixinImg;
	private ImageView friendImg;
	private Button cancel_share;
	// private Weibo mWeibo;
	// public static Oauth2AccessToken accessToken;
	private Context tag = VipActivity.this;
	private String content = "share content";
	private String lat = "90.00", lon = "90.00";
	private IWXAPI wxApi;
	public static final String WX_APP_ID = "wxf68fd078c1d9f53a";
	Intent intent;
	private static final int THUMB_SIZE = 150;
	UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share", RequestType.SOCIAL);
	String appID = "wxf68fd078c1d9f53a";
	String contentUrl = "http://www.ccjoy.cn/joylandweb/main.html";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vip_activity);
		BottomBar bottomBar = new BottomBar(this);
		initView();
		// initList();
		initListener();
		// 实例化

		// view_main=findViewById(R.layout.vip_activity);
		// mWeibo = Weibo.getInstance(APP_KEY, REDIRECT_URL);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/** 使用SSO授权必须添加如下代码 */
		UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode);
		if (ssoHandler != null) {
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	/*
	 * public void wechatShare(int flag,Bitmap bmp) { WXImageObject imgObj=new
	 * WXImageObject(bmp); WXMediaMessage msg=new WXMediaMessage();
	 * msg.title="嬉戏谷"; msg.description="一起来嬉戏谷玩吧！！"; msg.mediaObject=imgObj;
	 * Bitmap
	 * thumbBmp=Bitmap.createScaledBitmap(bmp,THUMB_SIZE,THUMB_SIZE,true);
	 * bmp.recycle(); //msg.thumbData = Util.bmpToByteArray(thumbBmp, true); //
	 * 设置缩略图 // 构造一个Req SendMessageToWX.Req req=new SendMessageToWX.Req();
	 * req.transaction=String.valueOf(System.currentTimeMillis()); //
	 * transaction字段用于唯一标识一个请求 req.message=msg; req.scene=(flag==0) ?
	 * SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
	 * // 调用api接口发送数据到微信 wxApi.sendReq(req); }
	 */

	private void initList() {
		iView.setVisibility(View.VISIBLE);
		vipbottom.setVisibility(View.VISIBLE);
		imglist.setVisibility(View.VISIBLE);
		List<String> listStr = getPictures("/sdcard/xxg_image" + "");
		if (listStr != null && listStr.size() >= 1) {
			adapter = new ListAdapter(listStr);
			imglist.setAdapter(adapter);
			iView.setVisibility(View.GONE);
			vipbottom.setVisibility(View.GONE);
		} else {
			imglist.setVisibility(View.GONE);
		}

		// }
	}

	protected void onResume() {
		super.onResume();
		// /new Thread(new lsiviewpagerThread()).start();
		initList();
	}

	protected void onStop() {
		super.onStop();
		// listbitmap.recycle();
	}

	private void initListener() {
		// TODO Auto-generated method stub
		cameraicon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				// startActivityForResult(intent, 1);
				Intent intent = new Intent(IntentAction.CAMERRA_PREVIVEW_ACTIVITY);
				startActivity(intent);
			}
		});
		// delete.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// File file = new File("/sdcard/xxg_image"+"");
		// File[] files = file.listFiles();
		// if(ii<file.length())
		// {
		// final File f = files[ii];
		// if(f.exists())
		// {
		// f.delete();
		//// Intent intent = new Intent(VipActivity.this, VipActivity.class);
		//// startActivity(intent);
		//// finish();
		// //adapter.argitem
		// adapter.notifyDataSetChanged();
		// }
		// }
		// popupWindow.dismiss();
		// }
		// });
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();
			}
		});
	}

	private void initView() {
		// TODO Auto-generated method stub
		iView = (ImageView) findViewById(R.id.vip);
		vipbottom = (ImageView) findViewById(R.id.vipbottom);
		imglist = (ListView) findViewById(R.id.imglist);
		cameraicon = (ImageView) findViewById(R.id.cameraicon);
		view = LayoutInflater.from(this).inflate(R.layout.popup_delete, null);
		list = new ArrayList<Bitmap>();
		delete = (Button) view.findViewById(R.id.delete);
		cancel = (Button) view.findViewById(R.id.cancel);
		share = (Button) view.findViewById(R.id.share);
		view_share = LayoutInflater.from(this).inflate(R.layout.popup_share, null);
		sinaImg = (ImageView) view_share.findViewById(R.id.img1);
		weixinImg = (ImageView) view_share.findViewById(R.id.img2);
		friendImg = (ImageView) view_share.findViewById(R.id.img3);
		cancel_share = (Button) view_share.findViewById(R.id.cancel);
		iView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IntentAction.CAMERRA_PREVIVEW_ACTIVITY);
				startActivity(intent);
			}
		});
	}

	/*
	 * private void initView_shear() { //shareImg = (ImageView)
	 * findViewById(R.id.shareicon); //mWeibo = Weibo.getInstance(APP_KEY,
	 * REDIRECT_URL); }
	 */
	// @Override
	// protected void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	// super.onActivityResult(requestCode, resultCode, data);
	//
	// if (resultCode == Activity.RESULT_OK) {
	// if (MomeryUtils.externalMemoryAvailable()) {
	// Bundle bundle = data.getExtras();
	// bitmap = (Bitmap) bundle.get("data");
	// if (null != bitmap) {
	// //iView.setVisibility(4);
	// SimpleDateFormat sdf = new SimpleDateFormat(
	// "yyyy-MM-dd-hh-mm-ss");
	// FileUtils.saveFile(bitmap,
	// sdf.format(new Date(System.currentTimeMillis()))
	// + "1.jpg");
	// //mWater = BitmapFactory.decodeResource(getResources(),
	// R.drawable.water_0);
	// //img = WaterUtils.createBitmap(bitmap, mWater, "");
	//
	// list.add(bitmap);
	//
	// adapter = new ListAdapter(list);
	// Log.e("201311212013112120131121", "list = " + list);
	// imglist.setAdapter(adapter);
	// iView.setVisibility(4);
	// if (img != null) {
	// FileUtils
	// .saveFile(
	// img,
	// sdf.format(new Date(System
	// .currentTimeMillis()))
	// + "2.jpg");
	// }
	// } else {
	// Log.i("athas", " .");
	// }
	// } else {
	// Log.i("athas", "sdcard");
	// }
	// }
	// }
	public class ListAdapter extends BaseAdapter {
		LayoutInflater mInflater = null;
		List<String> list;
		private FinalBitmap fb;

		public ListAdapter(List<String> list) {
			// TODO Auto-generated constructor stub
			this.list = list;
			fb = FinalBitmap.create(VipActivity.this);
			mInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(final int arg0, View convertView, ViewGroup arg2) {
			// TODO Auto-generated method stub
			ViewHolder viewHolder;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.list_item, null);
				viewHolder.img = (ImageView) convertView.findViewById(R.id.itemimg);
				viewHolder.phototimeyear = (TextView) convertView.findViewById(R.id.phototimeyear);
				viewHolder.phototimeday = (TextView) convertView.findViewById(R.id.phototimeday);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			try {
				// viewHolder.img.setImageResource(R.drawable.bg_22x);
				// File f=new File(list.get(arg0).toString());
				// long time=f.lastModified();
				// Calendar cal=Calendar.getInstance();
				// cal.setTimeInMillis(time);
				// String datastring=new String(cal.getTime().toLocaleString());
				// String dataitem[]=datastring.split("-");
				// viewHolder.phototimeyear.setText(dataitem[0]+"年");
				// viewHolder.phototimeday.setText(dataitem[1]+"月"+dataitem[2].substring(0,2)+"日");
				String imagepath = new String(list.get(arg0).toString());
				String imagepathitem[] = imagepath.split("/");
				int imagelength = imagepathitem.length;
				String stringdata = imagepathitem[imagelength - 1];

				String datastring = new String(stringdata);
				String dataitem[] = datastring.split("-");
				viewHolder.phototimeyear.setText(dataitem[0] + "年");
				viewHolder.phototimeday.setText(dataitem[1] + "月" + dataitem[2] + "日");
			} catch (Exception e) {
			}
			final String aag = list.get(arg0).toString();
			// viewHolder.img.setImageBitmap(BitmapFactory.decodeFile((aag)));

			fb.display(viewHolder.img, aag);

			viewHolder.img.setScaleType(ScaleType.FIT_XY);

			viewHolder.img.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ii = arg0;
					popupWindow = new PopupWindow(view, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
					popupWindow.setBackgroundDrawable(new BitmapDrawable());
					popupWindow.setFocusable(true);
					popupWindow.setTouchable(true);
					popupWindow.setOutsideTouchable(true);
					popupWindow.setAnimationStyle(R.style.PopupAnimation);
					popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);

				}
			});
			delete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					File file = new File("/sdcard/xxg_image" + "");
					File[] files = file.listFiles();
					if (arg0 < file.length()) {
						final File f = files[arg0];
						if (f.exists()) {
							f.delete();
							list.remove(arg0);
							adapter.notifyDataSetChanged();
							if (list.size() == 0) {
								iView.setVisibility(View.VISIBLE);
								vipbottom.setVisibility(View.VISIBLE);
							}
						}
					}
					popupWindow.dismiss();
				}
			});
			share.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					popupWindow.dismiss();
					// TODO Auto-generated method stub
					popupWindow_share = new PopupWindow(view_share, LayoutParams.FILL_PARENT,
							LayoutParams.WRAP_CONTENT);
					popupWindow_share.setBackgroundDrawable(new BitmapDrawable());
					popupWindow_share.setFocusable(true);
					popupWindow_share.setTouchable(true);
					popupWindow_share.setOutsideTouchable(true);
					popupWindow_share.setAnimationStyle(R.style.PopupAnimation);
					popupWindow_share.showAtLocation(view_share, Gravity.BOTTOM, 0, 0);
					UMWXHandler wxHandler = mController.getConfig().supportWXPlatform(VipActivity.this, appID,
							contentUrl);
					wxHandler.setWXTitle("嬉戏谷欢迎你!");
					UMWXHandler circleHandler = mController.getConfig().supportWXCirclePlatform(VipActivity.this, appID,
							contentUrl);
					circleHandler.setCircleTitle("嬉戏谷欢迎你!");
				}
			});
			sinaImg.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					popupWindow_share.dismiss();
					// mController.getConfig().setSsoHandler(new
					// SinaSsoHandler());

					mController.setShareContent(null);

					mController.setShareMedia(new UMImage(VipActivity.this, BitmapFactory.decodeFile(aag)));

					mController.postShare(VipActivity.this, SHARE_MEDIA.SINA, new SnsPostListener() {
						public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
							Toast.makeText(VipActivity.this, "分享完成", Toast.LENGTH_SHORT).show();
						}

						public void onStart() {
							Toast.makeText(VipActivity.this, "开始分享", Toast.LENGTH_SHORT).show();
						}
					});
					// intent=new Intent(VipActivity.this,Sinaweibo.class);
					// startActivity(intent);
					// mWeibo.authorize(tag, new AuthDialogListener());
				}
			});
			weixinImg.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					popupWindow_share.dismiss();
					mController.setShareContent(null);
					// 设置分享内容
					mController.setShareMedia(new UMImage(VipActivity.this, BitmapFactory.decodeFile(aag)));
					mController.directShare(VipActivity.this, SHARE_MEDIA.WEIXIN, new SnsPostListener() {
						@Override
						public void onStart() {
							Toast.makeText(VipActivity.this, "分享开始", Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
							if (eCode == StatusCode.ST_CODE_SUCCESSED) {
								Toast.makeText(VipActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(VipActivity.this, "分享失败", Toast.LENGTH_SHORT).show();
							}
						}
					});
					// VipActivity.this.wechatShare(1,BitmapFactory.decodeFile((aag)));//分享到微信好友
					// view_main.setFocusable(true);
					// view_main.requestFocus();
				}
			});
			friendImg.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					popupWindow_share.dismiss();
					mController.setShareContent(null);
					// 设置分享内容
					mController.setShareMedia(new UMImage(VipActivity.this, BitmapFactory.decodeFile(aag)));
					mController.directShare(VipActivity.this, SHARE_MEDIA.WEIXIN_CIRCLE, new SnsPostListener() {

						@Override
						public void onStart() {
							Toast.makeText(VipActivity.this, "分享开始", Toast.LENGTH_SHORT).show();
						}

						@Override
						public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
							if (eCode == StatusCode.ST_CODE_SUCCESSED) {
								Toast.makeText(VipActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(VipActivity.this, "分享失败", Toast.LENGTH_SHORT).show();
							}
						}
					});
					// VipActivity.this.wechatShare(0,BitmapFactory.decodeFile((aag)));//分享到微信朋友圈
				}
			});
			cancel.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0) {
					popupWindow.dismiss();
				}
			});
			cancel_share.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0) {
					popupWindow_share.dismiss();
				}
			});
			Log.e("Aatha 20131122", "list.get(arg0) = " + list.get(arg0));
			return convertView;
		}
	}

	private Bitmap options(String fileimagepath) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// 获取这个图片的宽和高
		listbitmap = BitmapFactory.decodeFile(fileimagepath, options);// 此时返回bm为空
		options.inJustDecodeBounds = false;
		// 计算缩放比
		int be = (int) (options.outHeight / (float) 800);
		if (be <= 0)
			be = 1;
		options.inSampleSize = be;
		// 重新读入图片，注意这次要把options.inJustDecodeBounds设为false哦
		listbitmap = BitmapFactory.decodeFile(fileimagepath, options);
		// WeakReference<Bitmap> reference = new
		// WeakReference<Bitmap>(listbitmap);
		// 这个就放置于内存中
		return listbitmap;
	}

	public class ViewHolder {
		public ImageView img;
		public TextView phototimeyear;
		public TextView phototimeday;
	}

	/**
	 * Get pictures under directory of strPath
	 * 
	 * @param strPath
	 * @return list
	 */
	public List<String> getPictures(final String strPath) {
		List<String> list = new ArrayList<String>();
		File file = new File(strPath);
		File[] files = file.listFiles();
		if (files == null) {
			return null;
		}
		for (int i = 0; i < files.length; i++) {
			final File f = files[i];
			if (f.isFile()) {
				try {
					int idx = f.getPath().lastIndexOf(".");
					if (idx <= 0) {
						continue;
					}
					String suffix = f.getPath().substring(idx);
					if (suffix.toLowerCase().equals(".jpg") || suffix.toLowerCase().equals(".jpeg")
							|| suffix.toLowerCase().equals(".bmp") || suffix.toLowerCase().equals(".png")
							|| suffix.toLowerCase().equals(".gif")) {
						list.add(f.getPath());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
}
