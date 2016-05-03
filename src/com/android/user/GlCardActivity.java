package com.android.user;

import com.android.common.IntentAction;
import com.android.util.Util;
import com.android.view.BottomBar;
import com.tencent.mm.sdk.openapi.IWXAPI;
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
//import com.tencent.mm.sdk.openapi.WXMediaMessage;
//import com.tencent.mm.sdk.openapi.WXTextObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class GlCardActivity extends Activity implements OnGestureListener {
	private String APP_KEY = "114640118";
	private String REDIRECT_URL = "cd693f8ebae3fdd9a23e89e8d1e8e729";
	// private AniImageView mAniImageView;
	private AnimationDrawable anim;
	private ViewFlipper flipper;
	private GestureDetector detector;
	private ImageView shareicon;
	private View view, dialogView;
	private PopupWindow popupWindow;
	private Dialog dialog;
	private TextView cardid;
	private ImageView sinaImg;
	private ImageView weixinImg;
	private ImageView friendImg;
	private int current = 1;
	// private Weibo mWeibo;
	// public Oauth2AccessToken accessToken;
	private String content = "share content";
	private String lat = "90.00", lon = "90.00";
	private Button introbtn1, sininbtn1, navibtn1, experiencebtn1;
	private Button introbtn2, sininbtn2, navibtn2, experiencebtn2;
	private Button introbtn3, sininbtn3, navibtn3, experiencebtn3;
	private IWXAPI wxApi;
	public String WX_APP_ID = "wxf68fd078c1d9f53a";
	private Button cancel;
	String appID = "wxf68fd078c1d9f53a";
	String contentUrl = "http://www.ccjoy.cn/joylandweb/main.html";
	UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share", RequestType.SOCIAL);
	String welcome = "欢迎来到嬉戏谷！";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.glcard_activity);
		BottomBar bottomBar = new BottomBar(this);
		initView();
		initListener();

	}
	/*
	 * Handler handler = new Handler() { public void
	 * handleMessage(android.os.Message msg) { switch (msg.what) { case 1:
	 * Util.dissmissDialog(); initPopup(); break; } super.handleMessage(msg); };
	 * };
	 */

	private void initPopup() {

		popupWindow = new PopupWindow(view, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setFocusable(true);
		popupWindow.setTouchable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setAnimationStyle(R.style.PopupAnimation);
		popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/** 使用SSO授权必须添加如下代码 */
		UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode);
		if (ssoHandler != null) {
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	public void btnclick(View v) {
		BasePop popupWindow = new BasePop(GlCardActivity.this, dialogView);
		switch (v.getId()) {
		case R.id.introbtn1:

			popupWindow.showAtLocation(findViewById(R.id.layoutlu), Gravity.CENTER, 0, 0);
			cardid.setText(getResources().getString(R.string.mrzy).toString());
			break;
		case R.id.introbtn2:

			popupWindow.showAtLocation(findViewById(R.id.layoutlu), Gravity.CENTER, 0, 0);
			cardid.setText(getResources().getString(R.string.jlh).toString());
			break;
		case R.id.introbtn3:
			popupWindow.showAtLocation(findViewById(R.id.layoutlu), Gravity.CENTER, 0, 0);
			cardid.setText(getResources().getString(R.string.cqtx).toString());
			break;
		case R.id.introbtn4:
			popupWindow.showAtLocation(findViewById(R.id.layoutlu), Gravity.CENTER, 0, 0);
			cardid.setText(getResources().getString(R.string.sds).toString());
			break;
		case R.id.introbtn5:
			popupWindow.showAtLocation(findViewById(R.id.layoutlu), Gravity.CENTER, 0, 0);
			cardid.setText(getResources().getString(R.string.xjcs).toString());
			break;
		case R.id.introbtn6:
			popupWindow.showAtLocation(findViewById(R.id.layoutlu), Gravity.CENTER, 0, 0);
			cardid.setText(getResources().getString(R.string.msdl).toString());
			break;
		case R.id.introbtn7:
			popupWindow.showAtLocation(findViewById(R.id.layoutlu), Gravity.CENTER, 0, 0);
			cardid.setText(getResources().getString(R.string.msdl).toString());
			break;
		case R.id.introbtn8:
			popupWindow.showAtLocation(findViewById(R.id.layoutlu), Gravity.CENTER, 0, 0);
			cardid.setText(getResources().getString(R.string.msdl).toString());
			break;
		case R.id.introbtn9:
			popupWindow.showAtLocation(findViewById(R.id.layoutlu), Gravity.CENTER, 0, 0);
			cardid.setText(getResources().getString(R.string.msdl).toString());
			break;
		case R.id.navibtn1:
			this.finish();
			break;
		case R.id.navibtn2:
			this.finish();
			break;
		case R.id.navibtn3:
			this.finish();
			break;
		case R.id.navibtn4:
			this.finish();
			break;
		case R.id.navibtn5:
			this.finish();
			break;
		case R.id.navibtn6:
			this.finish();
			break;
		case R.id.navibtn7:
			this.finish();
			break;
		case R.id.navibtn8:
			this.finish();
			break;
		case R.id.navibtn9:
			this.finish();
			break;
		case R.id.sininbtn1:

			Util.showDialg(this, getResources().getString(R.string.sign), 0);
			new Handler().postDelayed(new Runnable() {

				public void run() {
					current = 1;
					Util.dissmissDialog();
					initPopup();

				}

			}, 1000);
			break;
		case R.id.sininbtn2:

			Util.showDialg(this, getResources().getString(R.string.sign), 0);
			new Handler().postDelayed(new Runnable() {

				public void run() {
					current = 2;
					Util.dissmissDialog();
					initPopup();

				}

			}, 1000);
			break;
		case R.id.sininbtn3:

			Util.showDialg(this, getResources().getString(R.string.sign), 0);
			new Handler().postDelayed(new Runnable() {

				public void run() {
					current = 3;
					Util.dissmissDialog();
					initPopup();

				}

			}, 1000);
			break;
		case R.id.sininbtn4:

			Util.showDialg(this, getResources().getString(R.string.sign), 0);
			new Handler().postDelayed(new Runnable() {

				public void run() {
					current = 4;
					Util.dissmissDialog();
					initPopup();

				}

			}, 1000);
			break;
		case R.id.sininbtn5:

			Util.showDialg(this, getResources().getString(R.string.sign), 0);
			new Handler().postDelayed(new Runnable() {

				public void run() {
					current = 5;
					Util.dissmissDialog();
					initPopup();

				}

			}, 1000);
			break;
		case R.id.sininbtn6:

			Util.showDialg(this, getResources().getString(R.string.sign), 0);
			new Handler().postDelayed(new Runnable() {

				public void run() {
					current = 6;
					Util.dissmissDialog();
					initPopup();

				}

			}, 1000);
			break;

		case R.id.sininbtn7:

			Util.showDialg(this, getResources().getString(R.string.sign), 0);
			new Handler().postDelayed(new Runnable() {

				public void run() {
					current = 7;
					Util.dissmissDialog();
					initPopup();

				}

			}, 1000);
			break;
		case R.id.sininbtn8:

			Util.showDialg(this, getResources().getString(R.string.sign), 0);
			new Handler().postDelayed(new Runnable() {

				public void run() {
					current = 8;
					Util.dissmissDialog();
					initPopup();

				}

			}, 1000);
			break;
		case R.id.sininbtn9:

			Util.showDialg(this, getResources().getString(R.string.sign), 0);
			new Handler().postDelayed(new Runnable() {

				public void run() {
					current = 9;
					Util.dissmissDialog();
					initPopup();

				}

			}, 1000);
			break;
		case R.id.experiencebtn1:
			Intent intentfir = new Intent(IntentAction.CARDFIR_ACTIVITY);
			intentfir.putExtra("card", v.getId());
			startActivity(intentfir);
			break;
		case R.id.experiencebtn2:
			Intent intentsec = new Intent(IntentAction.CARDFIR_ACTIVITY);
			intentsec.putExtra("card", v.getId());
			startActivity(intentsec);
			break;
		case R.id.experiencebtn3:
			Intent intentthi = new Intent(IntentAction.CARDFIR_ACTIVITY);
			intentthi.putExtra("card", v.getId());
			startActivity(intentthi);
			break;
		case R.id.experiencebtn4:
			Intent intentfor = new Intent(IntentAction.CARDFIR_ACTIVITY);
			intentfor.putExtra("card", v.getId());
			startActivity(intentfor);
			break;
		case R.id.experiencebtn5:
			Intent intentfiv = new Intent(IntentAction.CARDFIR_ACTIVITY);
			intentfiv.putExtra("card", v.getId());
			startActivity(intentfiv);
			break;
		case R.id.experiencebtn6:
			Intent intentsix = new Intent(IntentAction.CARDFIR_ACTIVITY);
			intentsix.putExtra("card", v.getId());
			startActivity(intentsix);
			break;
		case R.id.experiencebtn7:
			Intent intentseven = new Intent(IntentAction.CARDFIR_ACTIVITY);
			intentseven.putExtra("card", v.getId());
			startActivity(intentseven);
			break;
		case R.id.experiencebtn8:
			Intent intenteight = new Intent(IntentAction.CARDFIR_ACTIVITY);
			intenteight.putExtra("card", v.getId());
			startActivity(intenteight);
			break;
		case R.id.experiencebtn9:
			Intent intentnine = new Intent(IntentAction.CARDFIR_ACTIVITY);
			intentnine.putExtra("card", v.getId());
			startActivity(intentnine);
			break;
		default:
			break;
		}
	}

	/*
	 * private void wechatShare( int flag){
	 * 
	 * final EditText editor = new EditText(GlCardActivity.this);
	 * editor.setLayoutParams(new
	 * LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
	 * LinearLayout.LayoutParams.WRAP_CONTENT)); editor.setText("我在嬉戏谷！！");
	 * String text = editor.getText().toString(); if (text == null ||
	 * text.length() == 0) { return; } // 初始化一个WXTextObject对象 WXTextObject
	 * textObj = new WXTextObject(); textObj.text = text;
	 * 
	 * // 用WXTextObject对象初始化一个WXMediaMessage对象 WXMediaMessage msg = new
	 * WXMediaMessage(); msg.mediaObject = textObj; // 发送文本类型的消息时，title字段不起作用 //
	 * msg.title = "Will be ignored"; msg.description = text;
	 * 
	 * // 构造一个Req SendMessageToWX.Req req = new SendMessageToWX.Req();
	 * req.transaction = String.valueOf(System.currentTimeMillis()); //
	 * transaction字段用于唯一标识一个请求 req.message = msg; req.scene =(flag==0)?
	 * SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
	 * 
	 * // 调用api接口发送数据到微信 wxApi.sendReq(req);
	 * 
	 * }
	 */
	private void initListener() {
		// TODO Auto-generated method stub
		// mAniImageView.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// }
		// });
		UMWXHandler wxHandler = mController.getConfig().supportWXPlatform(GlCardActivity.this, appID, contentUrl);
		wxHandler.setWXTitle("嬉戏谷欢迎你!");
		UMWXHandler circleHandler = mController.getConfig().supportWXCirclePlatform(GlCardActivity.this, appID,
				contentUrl);
		circleHandler.setCircleTitle("嬉戏谷欢迎你!");
		sinaImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();
				// mController.getConfig().setSsoHandler(new SinaSsoHandler());
				/*
				 * UMWXHandler wxHandler =
				 * mController.getConfig().supportWXPlatform(GlCardActivity.this
				 * ,appID, contentUrl); wxHandler.setWXTitle("嬉戏谷欢迎你!");
				 * UMWXHandler circleHandler =
				 * mController.getConfig().supportWXCirclePlatform(
				 * GlCardActivity.this,appID, contentUrl) ;
				 * circleHandler.setCircleTitle("嬉戏谷欢迎你!");
				 */

				mController.setShareContent(null);
				mController.setShareMedia(new UMImage(GlCardActivity.this, curpic(current)));

				mController.postShare(GlCardActivity.this, SHARE_MEDIA.SINA, new SnsPostListener() {
					public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
						Toast.makeText(GlCardActivity.this, "分享完成", Toast.LENGTH_SHORT).show();
					}

					public void onStart() {
						Toast.makeText(GlCardActivity.this, "开始分享", Toast.LENGTH_SHORT).show();
					}
				});
				// mWeibo.authorize(this, new AuthDialogListener());
				// AuthDialogListener listener = new AuthDialogListener();
				// mWeibo.authorize(this,listener);
			}
		});
		weixinImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();
				// 设置分享内容
				mController.setShareContent(null);
				mController.setShareMedia(new UMImage(GlCardActivity.this, curpic(current)));
				mController.directShare(GlCardActivity.this, SHARE_MEDIA.WEIXIN, new SnsPostListener() {

					@Override
					public void onStart() {
						Toast.makeText(GlCardActivity.this, "分享开始", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
						if (eCode == StatusCode.ST_CODE_SUCCESSED) {
							Toast.makeText(GlCardActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(GlCardActivity.this, "分享失败", Toast.LENGTH_SHORT).show();
						}
					}
				});

			}
		});
		friendImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				popupWindow.dismiss();
				// 设置分享内容
				mController.setShareContent(null);
				mController.setShareMedia(new UMImage(GlCardActivity.this, curpic(current)));
				mController.directShare(GlCardActivity.this, SHARE_MEDIA.WEIXIN_CIRCLE, new SnsPostListener() {

					@Override
					public void onStart() {
						Toast.makeText(GlCardActivity.this, "分享开始", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
						if (eCode == StatusCode.ST_CODE_SUCCESSED) {
							Toast.makeText(GlCardActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(GlCardActivity.this, "分享失败", Toast.LENGTH_SHORT).show();
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
	}

	// private Handler handler = new Handler() {
	// @Override
	// public void handleMessage(Message msg) {
	// switch (msg.what) {
	// case 0:
	// StatusesAPI api = new StatusesAPI(accessToken);
	//
	// api.update(content, lat, lon, new RequestListener() {
	//
	// @Override
	// public void onIOException(IOException arg0) {
	// System.out.println("failure");
	// }
	//
	// @Override
	// public void onError(WeiboException arg0) {
	// System.out.println("failure");
	// }
	//
	// @Override
	// public void onComplete(String arg0) {
	// System.out.println("success");
	// }
	// });
	// break;
	// case 1:
	// Util.dissmissDialog();
	// initPopup();
	//
	// break;
	// default:
	// break;
	// }
	// }
	// };

	private int curpic(int current) {
		int bitmap_id = 0;
		switch (current) {
		case 1:
			bitmap_id = R.drawable.sreshota1;
			break;
		case 2:
			bitmap_id = R.drawable.sreshota2;
			break;
		case 3:
			bitmap_id = R.drawable.sreshota3;
			break;
		case 4:
			bitmap_id = R.drawable.sreshota4;
			break;
		case 5:
			bitmap_id = R.drawable.sreshota5;
			break;
		case 6:
			bitmap_id = R.drawable.sreshota6;
			break;
		case 7:
			bitmap_id = R.drawable.sreshota6;
			break;
		case 8:
			bitmap_id = R.drawable.sreshota6;
			break;
		case 9:
			bitmap_id = R.drawable.sreshota6;
			break;
		default:
			break;
		}
		return bitmap_id;
	}

	private void initView() {
		// TODO Auto-generated method stub
		detector = new GestureDetector(this);
		flipper = (ViewFlipper) findViewById(R.id.CardFlipper1);

		// mAniImageView = (AniImageView) findViewById(R.id.aniimageviewid);
		// mAniImageView.setImageResource(R.drawable.bottom_animation);
		// anim = (AnimationDrawable) mAniImageView.getDrawable();
		// mAniImageView.getViewTreeObserver().addOnPreDrawListener(opdl);

		// dialog = new MarketDialog(this, R.style.MarketDialog);
		// // dialog = new Dialog(this);
		// LayoutInflater factory = LayoutInflater.from(this);
		// View dialogView = factory.inflate(R.layout.card_dialog, null);
		// dialog.setContentView(dialogView);
		// Window dialogWindow = dialog.getWindow();
		// // dialogWindow.setLayout(120, 160);
		// WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		// lp.width = 900;
		// lp.height = 1200;
		// lp.alpha = 0.8f;
		// dialogWindow.setAttributes(lp);

		LayoutInflater m_inflater;
		m_inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		dialogView = m_inflater.inflate(R.layout.card_dialog, null);

		// popupWindow.showAsDropDown(anchor);
		cardid = (TextView) dialogView.findViewById(R.id.cardid);
		view = LayoutInflater.from(this).inflate(R.layout.popup_share, null);
		sinaImg = (ImageView) view.findViewById(R.id.img1);
		weixinImg = (ImageView) view.findViewById(R.id.img2);
		friendImg = (ImageView) view.findViewById(R.id.img3);
		// mWeibo = Weibo.getInstance(APP_KEY, REDIRECT_URL);
		cancel = (Button) view.findViewById(R.id.cancel);
	}

	OnPreDrawListener opdl = new OnPreDrawListener() {
		public boolean onPreDraw() {
			// TODO Auto-generated method stub
			anim.start();
			return true;
		}
	};

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
		super.onDestroy();
		System.gc();
	}
}
