package com.android.user;

import java.io.InputStream;

//import net.sourceforge.simcpux.Util;

import com.android.common.IntentAction;
import com.android.util.AniImageView;
import com.android.view.BottomBar;
import com.tencent.mm.sdk.openapi.IWXAPI;
//import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.RequestType;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.sso.UMWXHandler;
//import com.tencent.mm.sdk.openapi.WXImageObject;
//import com.tencent.mm.sdk.openapi.WXMediaMessage;
//import com.tencent.mm.sdk.openapi.WXTextObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class TstjActivity extends Activity implements OnGestureListener {
	private AniImageView mAniImageView;
	private AnimationDrawable anim;
	private ViewFlipper flipper;
	private GestureDetector detector;
	private ImageView shareicon;
	private View view;
	private PopupWindow popupWindow;
	private ImageView ly1, ly2, ly3, ly35, ly4, ly5, ly6, ly7, ly8, ly9, ly10, ly11, ly12;
	private ImageView recommend_02x, recommend_12x, recommend_22x, recommend_32x, recommend_42x, recommend_52x,
			recommend_62x, recommend_72x, recommend_82x, recommend_92x, recommend_102x, recommend_112x, recommend_122x;
	private Bitmap bitmap, bit0, bit1, bit2, bit3, bit4, bit5, bit6, bit7, bit8, bit9, bit10, bit11, bit12;
	private IWXAPI wxApi;
	public static final String WX_APP_ID = "wxf68fd078c1d9f53a";
	private ImageView sinaImg;
	private ImageView weixinImg;
	private ImageView friendImg;
	private Button cancel;

	private final static String APP_KEY = "111182051";
	private final static String REDIRECT_URL = "http://zhushou.360.cn/detail/index/soft_id/1764113?recrefer=SE_D_%E6%8E%8C%E4%B8%8A%E5%AC%89%E6%88%8F";
	private Context tag = TstjActivity.this;

	private String content = "share content";
	private String lat = "90.00", lon = "90.00";
	private static final int THUMB_SIZE = 150;
	UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share", RequestType.SOCIAL);
	String appID = "wxf68fd078c1d9f53a";
	String contentUrl = "http://www.ccjoy.cn/joylandweb/main.html";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tstj_activity);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		initView();
		initListener();
		// 实例化
		wxApi = WXAPIFactory.createWXAPI(this, WX_APP_ID);
		super.onResume();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/** 使用SSO授权必须添加如下代码 */
		UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode);
		if (ssoHandler != null) {
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	private Bitmap wechat_Share() {
		int id = flipper.getCurrentView().getId();
		Bitmap bmp = null;
		switch (id) {
		case R.id.framelayout1:
			bmp = BitmapFactory.decodeResource(getResources(), R.drawable.recommend_02x);
			break;
		case R.id.framelayout2:
			bmp = BitmapFactory.decodeResource(getResources(), R.drawable.recommend_12x);
			break;
		case R.id.framelayout35:
			bmp = BitmapFactory.decodeResource(getResources(), R.drawable.recommend_22x);
			break;
		case R.id.framelayout3:
			bmp = BitmapFactory.decodeResource(getResources(), R.drawable.recommend_32x);
			break;
		case R.id.framelayout4:
			bmp = BitmapFactory.decodeResource(getResources(), R.drawable.recommend_42x);
			break;
		case R.id.framelayout5:
			bmp = BitmapFactory.decodeResource(getResources(), R.drawable.recommend_52x);
			break;
		default:
			break;
		}
		return bmp;
	}

	private int getBitmap_id() {
		int id = flipper.getCurrentView().getId();
		int bitmap_id = 0;
		switch (id) {
		case R.id.framelayout1:
			bitmap_id = R.drawable.recommend_02x;
			break;
		case R.id.framelayout2:
			bitmap_id = R.drawable.recommend_12x;
			break;
		case R.id.framelayout35:
			bitmap_id = R.drawable.recommend_22x;
			break;
		case R.id.framelayout3:
			bitmap_id = R.drawable.recommend_32x;
			break;
		case R.id.framelayout4:
			bitmap_id = R.drawable.recommend_42x;
			break;
		case R.id.framelayout5:
			bitmap_id = R.drawable.recommend_52x;
			break;

		case R.id.framelayout6:
			bitmap_id = R.drawable.recommend_62x;
			break;
		case R.id.framelayout7:
			bitmap_id = R.drawable.recommend_72x;
			break;
		case R.id.framelayout8:
			bitmap_id = R.drawable.recommend_82x;
			break;
		case R.id.framelayout9:
			bitmap_id = R.drawable.recommend_92x;
			break;
		case R.id.framelayout10:
			bitmap_id = R.drawable.recommend_102x;
			break;
		case R.id.framelayout11:
			bitmap_id = R.drawable.recommend_112x;
			break;
		case R.id.framelayout12:
			bitmap_id = R.drawable.recommend_122x;
			break;
		default:
			break;
		}
		return bitmap_id;
	}

	private void wechatShare(int flag) {
		int id = flipper.getCurrentView().getId();
		Bitmap bmp = null;
		switch (id) {
		case R.id.framelayout1:
			bmp = BitmapFactory.decodeResource(getResources(), R.drawable.recommend_02x);
			break;
		case R.id.framelayout2:
			bmp = BitmapFactory.decodeResource(getResources(), R.drawable.recommend_12x);
			break;
		case R.id.framelayout35:
			bmp = BitmapFactory.decodeResource(getResources(), R.drawable.recommend_22x);
			break;
		case R.id.framelayout3:
			bmp = BitmapFactory.decodeResource(getResources(), R.drawable.recommend_32x);
			break;
		case R.id.framelayout4:
			bmp = BitmapFactory.decodeResource(getResources(), R.drawable.recommend_42x);
			break;
		case R.id.framelayout5:
			bmp = BitmapFactory.decodeResource(getResources(), R.drawable.recommend_52x);
			break;
		default:
			break;
		}

		/*
		 * WXImageObject imgObj = new WXImageObject(bmp);
		 * 
		 * WXMediaMessage msg = new WXMediaMessage(); msg.mediaObject = imgObj;
		 * 
		 * Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE,
		 * THUMB_SIZE, true); bmp.recycle(); //msg.thumbData =
		 * Util.bmpToByteArray(thumbBmp, true); // 设置缩略图 // 构造一个Req
		 * SendMessageToWX.Req req = new SendMessageToWX.Req(); req.transaction
		 * = String.valueOf(System.currentTimeMillis()); //
		 * transaction字段用于唯一标识一个请求 req.message = msg; req.scene =(flag==0)?
		 * SendMessageToWX.Req.WXSceneTimeline :
		 * SendMessageToWX.Req.WXSceneSession;
		 * 
		 * // 调用api接口发送数据到微信 wxApi.sendReq(req);
		 */

	}

	private void initListener() {
		shareicon.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popupWindow = new PopupWindow(view, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
				popupWindow.setBackgroundDrawable(new BitmapDrawable());
				popupWindow.setFocusable(true);
				popupWindow.setTouchable(true);
				popupWindow.setOutsideTouchable(true);
				popupWindow.setAnimationStyle(R.style.PopupAnimation);
				popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
				UMWXHandler wxHandler = mController.getConfig().supportWXPlatform(TstjActivity.this, appID, contentUrl);
				wxHandler.setWXTitle("嬉戏谷欢迎你!");
				UMWXHandler circleHandler = mController.getConfig().supportWXCirclePlatform(TstjActivity.this, appID,
						contentUrl);
				circleHandler.setCircleTitle("嬉戏谷欢迎你!");

			}
		});
		sinaImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();
				mController.setShareContent(null);
				// mController.getConfig().setSsoHandler(new SinaSsoHandler());

				mController.setShareMedia(new UMImage(TstjActivity.this, getBitmap_id()));

				mController.postShare(TstjActivity.this, SHARE_MEDIA.SINA, new SnsPostListener() {
					public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
						Toast.makeText(TstjActivity.this, "分享完成", Toast.LENGTH_SHORT).show();
					}

					public void onStart() {
						Toast.makeText(TstjActivity.this, "开始分享", Toast.LENGTH_SHORT).show();
					}
				});

			}
		});
		weixinImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();
				mController.setShareContent(null);
				// 设置分享内容
				mController.setShareMedia(new UMImage(TstjActivity.this, getBitmap_id()));

				mController.directShare(TstjActivity.this, SHARE_MEDIA.WEIXIN, new SnsPostListener() {

					@Override
					public void onStart() {
						Toast.makeText(TstjActivity.this, "分享开始", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
						if (eCode == StatusCode.ST_CODE_SUCCESSED) {
							Toast.makeText(TstjActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(TstjActivity.this, "分享失败", Toast.LENGTH_SHORT).show();
						}
					}
				});
				mController.initEntity(TstjActivity.this, null);
			}
		});
		friendImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();
				mController.setShareContent(null);
				// 设置分享内容
				mController.setShareMedia(new UMImage(TstjActivity.this, getBitmap_id()));
				mController.directShare(TstjActivity.this, SHARE_MEDIA.WEIXIN_CIRCLE, new SnsPostListener() {

					@Override
					public void onStart() {
						Toast.makeText(TstjActivity.this, "分享开始", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
						if (eCode == StatusCode.ST_CODE_SUCCESSED) {
							Toast.makeText(TstjActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(TstjActivity.this, "分享失败", Toast.LENGTH_SHORT).show();
						}
					}
				});

			}
		});
		cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				popupWindow.dismiss();
			}
		});
		flipper.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int id = flipper.getCurrentView().getId();
				if (id == R.id.framelayout1) {
					Intent intent = new Intent(IntentAction.DETAIL_ACTIVITY);
					startActivity(intent);
				}
				if (id == R.id.framelayout2) {
					Intent intent = new Intent(IntentAction.DETAILSEC_ACTIVITY);
					startActivity(intent);
				}
				if (id == R.id.framelayout35) {
					Intent intent = new Intent(IntentAction.DETAILOTH_ACTIVITY);
					startActivity(intent);
				}
				if (id == R.id.framelayout3) {
					Intent intent = new Intent(IntentAction.DETAILTHI_ACTIVITY);
					startActivity(intent);
				}
				if (id == R.id.framelayout4) {
					Intent intent = new Intent(IntentAction.DETAILFOR_ACTIVITY);
					startActivity(intent);
				}
				if (id == R.id.framelayout5) {
					Intent intent = new Intent(IntentAction.DETAILFIV_ACTIVITY);
					startActivity(intent);
				}
				Intent intent1;
				switch (id) {
				case R.id.framelayout6:
					intent1 = new Intent(TstjActivity.this, TstjDetailsActivity.class);
					intent1.putExtra("which", 0);
					startActivity(intent1);
					break;
				case R.id.framelayout7:
					intent1 = new Intent(TstjActivity.this, TstjDetailsActivity.class);
					intent1.putExtra("which", 1);
					startActivity(intent1);
					break;
				case R.id.framelayout8:
					intent1 = new Intent(TstjActivity.this, TstjDetailsActivity.class);
					intent1.putExtra("which", 2);
					startActivity(intent1);
					break;
				case R.id.framelayout9:
					intent1 = new Intent(TstjActivity.this, TstjDetailsActivity.class);
					intent1.putExtra("which", 3);
					startActivity(intent1);
					break;
				case R.id.framelayout10:
					intent1 = new Intent(TstjActivity.this, TstjDetailsActivity.class);
					intent1.putExtra("which", 4);
					startActivity(intent1);
					break;
				case R.id.framelayout11:
					intent1 = new Intent(TstjActivity.this, TstjDetailsActivity.class);
					intent1.putExtra("which", 5);
					startActivity(intent1);
					break;
				case R.id.framelayout12:
					intent1 = new Intent(TstjActivity.this, TstjDetailsActivity.class);
					intent1.putExtra("which", 6);
					startActivity(intent1);
					break;

				default:
					break;
				}
			}
		});

	}

	private void initView() {
		// TODO Auto-generated method stub
		detector = new GestureDetector(this);
		ly1 = (ImageView) findViewById(R.id.ly1);
		ly2 = (ImageView) findViewById(R.id.ly2);
		ly3 = (ImageView) findViewById(R.id.ly3);
		ly35 = (ImageView) findViewById(R.id.ly35);
		ly4 = (ImageView) findViewById(R.id.ly4);
		ly5 = (ImageView) findViewById(R.id.ly5);

		ly6 = (ImageView) findViewById(R.id.ly6);
		ly7 = (ImageView) findViewById(R.id.ly7);
		ly8 = (ImageView) findViewById(R.id.ly8);
		ly9 = (ImageView) findViewById(R.id.ly9);
		ly10 = (ImageView) findViewById(R.id.ly10);
		ly11 = (ImageView) findViewById(R.id.ly11);
		ly12 = (ImageView) findViewById(R.id.ly12);

		recommend_02x = (ImageView) findViewById(R.id.recommend_02x);
		recommend_12x = (ImageView) findViewById(R.id.recommend_12x);
		recommend_22x = (ImageView) findViewById(R.id.recommend_22x);
		recommend_32x = (ImageView) findViewById(R.id.recommend_32x);
		recommend_42x = (ImageView) findViewById(R.id.recommend_42x);
		recommend_52x = (ImageView) findViewById(R.id.recommend_52x);

		recommend_62x = (ImageView) findViewById(R.id.recommend_62x);
		recommend_72x = (ImageView) findViewById(R.id.recommend_72x);
		recommend_82x = (ImageView) findViewById(R.id.recommend_82x);
		recommend_92x = (ImageView) findViewById(R.id.recommend_92x);
		recommend_102x = (ImageView) findViewById(R.id.recommend_102x);
		recommend_112x = (ImageView) findViewById(R.id.recommend_112x);
		recommend_122x = (ImageView) findViewById(R.id.recommend_122x);

		bitmap = readBitMap(getBaseContext(), R.drawable.recommend_bg2x);
		bit0 = readBitMap(getBaseContext(), R.drawable.recommend_02x);
		bit1 = readBitMap(getBaseContext(), R.drawable.recommend_12x);
		bit2 = readBitMap(getBaseContext(), R.drawable.recommend_22x);
		bit3 = readBitMap(getBaseContext(), R.drawable.recommend_32x);
		bit4 = readBitMap(getBaseContext(), R.drawable.recommend_42x);
		bit5 = readBitMap(getBaseContext(), R.drawable.recommend_52x);

		bit6 = readBitMap(getBaseContext(), R.drawable.recommend_62x);
		bit7 = readBitMap(getBaseContext(), R.drawable.recommend_72x);
		bit8 = readBitMap(getBaseContext(), R.drawable.recommend_82x);
		bit9 = readBitMap(getBaseContext(), R.drawable.recommend_92x);
		bit10 = readBitMap(getBaseContext(), R.drawable.recommend_102x);
		bit11 = readBitMap(getBaseContext(), R.drawable.recommend_112x);
		bit12 = readBitMap(getBaseContext(), R.drawable.recommend_122x);

		flipper = (ViewFlipper) findViewById(R.id.ViewFlipper1);

		recommend_02x.setImageBitmap(bit0);
		recommend_12x.setImageBitmap(bit1);
		recommend_22x.setImageBitmap(bit2);
		recommend_32x.setImageBitmap(bit3);
		recommend_42x.setImageBitmap(bit4);
		recommend_52x.setImageBitmap(bit5);

		recommend_62x.setImageBitmap(bit6);
		recommend_72x.setImageBitmap(bit7);
		recommend_82x.setImageBitmap(bit8);
		recommend_92x.setImageBitmap(bit9);
		recommend_102x.setImageBitmap(bit10);
		recommend_112x.setImageBitmap(bit11);
		recommend_122x.setImageBitmap(bit12);

		ly1.setImageBitmap(bitmap);
		ly2.setImageBitmap(bitmap);
		ly3.setImageBitmap(bitmap);
		ly35.setImageBitmap(bitmap);
		ly4.setImageBitmap(bitmap);
		ly5.setImageBitmap(bitmap);

		ly6.setImageBitmap(bitmap);
		ly7.setImageBitmap(bitmap);
		ly8.setImageBitmap(bitmap);
		ly9.setImageBitmap(bitmap);
		ly10.setImageBitmap(bitmap);
		ly11.setImageBitmap(bitmap);
		ly12.setImageBitmap(bitmap);

		shareicon = (ImageView) findViewById(R.id.shareicon);
		view = LayoutInflater.from(this).inflate(R.layout.popup_share, null);
		BottomBar bottomBar = new BottomBar(this);
		sinaImg = (ImageView) view.findViewById(R.id.img1);
		weixinImg = (ImageView) view.findViewById(R.id.img2);
		friendImg = (ImageView) view.findViewById(R.id.img3);
		cancel = (Button) view.findViewById(R.id.cancel);
		// mWeibo = Weibo.getInstance(APP_KEY, REDIRECT_URL);
	}

	public static Bitmap readBitMap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// 获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}

	public boolean dispatchTouchEvent(MotionEvent ev) {
		detector.onTouchEvent(ev);
		super.dispatchTouchEvent(ev);
		return true;
	};

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		super.onTouchEvent(event);
		return this.detector.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		this.flipper.setClickable(true);
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		// TODO Auto-generated method stub
		this.flipper.setClickable(false);

		if (e1.getX() - e2.getX() > 120) {
			this.flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
			this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
			this.flipper.showNext();
			return true;
		} else if (e1.getX() - e2.getX() < -120) {
			this.flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_in));
			this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_out));
			this.flipper.showPrevious();
			return true;
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		bitmap.recycle();
		bit0.recycle();
		bit1.recycle();
		bit2.recycle();
		bit3.recycle();
		bit4.recycle();
		bit5.recycle();

		bit6.recycle();
		bit7.recycle();
		bit8.recycle();
		bit9.recycle();
		bit10.recycle();
		bit11.recycle();
		bit12.recycle();
		super.onDestroy();
	}

}
