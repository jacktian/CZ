package com.android.user;

import com.android.util.AniImageView;
import com.android.util.MarketDialog;
import com.android.view.BottomBar;
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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

public class YygloActivity extends Activity {

	// private final static String consumer_secret
	// ="p40b6649377ebccf213e45cd827af6693";
	private AniImageView mAniImageView;
	private AnimationDrawable anim;
	private MarketDialog mDialog;
	private ImageView shareImg;
	private View view;
	private PopupWindow popupWindow;
	private ImageView sinaImg;
	private ImageView weixinImg;
	private ImageView friendImg;
	private Button cancel;
	// private Weibo mWeibo;
	// public static Oauth2AccessToken accessToken;
	private Context tag = YygloActivity.this;
	private String content = "share content";
	private String lat = "90.00", lon = "90.00";

	String path = "1 室内项目路线 \n海底精灵城 ⇒ 小摩尔大冒险 ⇒ 游戏要塞 ⇒ 冰剑国度 ⇒ 大话嬉戏 ⇒ 天幕幻想 ⇒ 天际骇客 ⇒  梦幻擎天 \n2 亲子游项目专用路线 \n海底精灵城 ⇒ 小摩尔大冒险 ⇒ 空中大巡逻 ⇒ 魔法精灵 ⇒ 飞旋骑士 ⇒ 游戏要塞 ⇒ 冰剑国度 ⇒ 天堂之舵 ⇒ 龙行天下 ";
	UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share", RequestType.SOCIAL);
	String appID = "wxf68fd078c1d9f53a";
	String contentUrl = "http://www.ccjoy.cn/joylandweb/main.html";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yyglo_activity);
		BottomBar bottomBar = new BottomBar(this);

		// 实例化
		// wxApi = WXAPIFactory.createWXAPI(this, WX_APP_ID);
		initView();
		initListener();
	}
	/*
	 * private void wechatShare( int flag){
	 * 
	 * final EditText editor = new EditText(YygloActivity.this);
	 * editor.setLayoutParams(new
	 * LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
	 * LinearLayout.LayoutParams.WRAP_CONTENT)); editor.setText(R.string.jtlx);
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

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		/** 使用SSO授权必须添加如下代码 */
		UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode);
		if (ssoHandler != null) {
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	private void initListener() {
		// TODO Auto-generated method stub
		// final Intent intent;
		shareImg.setOnClickListener(new OnClickListener() {

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
				UMWXHandler wxHandler = mController.getConfig().supportWXPlatform(YygloActivity.this, appID,
						contentUrl);
				wxHandler.setWXTitle("嬉戏谷欢迎你!");
				UMWXHandler circleHandler = mController.getConfig().supportWXCirclePlatform(YygloActivity.this, appID,
						contentUrl);
				circleHandler.setCircleTitle("嬉戏谷欢迎你!");

			}
		});
		sinaImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// mController.getConfig().setSsoHandler(new SinaSsoHandler());
				try {
					mController.setShareMedia(new UMImage(YygloActivity.this, ""));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				mController.setShareContent(path);

				mController.postShare(YygloActivity.this, SHARE_MEDIA.SINA, new SnsPostListener() {
					public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
						Toast.makeText(YygloActivity.this, "分享完成", Toast.LENGTH_SHORT).show();
					}

					public void onStart() {
						Toast.makeText(YygloActivity.this, "开始分享", Toast.LENGTH_SHORT).show();
					}
				});
				// intent = new Intent(YygloActivity.this, Sinaweibo.class);
				// startActivity(intent);
				// mWeibo.authorize(tag, new AuthDialogListener());
			}
		});
		weixinImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();
				try {
					mController.setShareMedia(new UMImage(YygloActivity.this, ""));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// 设置分享内容
				mController.setShareContent(path);
				mController.directShare(YygloActivity.this, SHARE_MEDIA.WEIXIN, new SnsPostListener() {

					@Override
					public void onStart() {
						Toast.makeText(YygloActivity.this, "分享开始", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
						if (eCode == StatusCode.ST_CODE_SUCCESSED) {
							Toast.makeText(YygloActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(YygloActivity.this, "分享失败", Toast.LENGTH_SHORT).show();
						}
					}
				});

				// wxApi.registerApp(WX_APP_ID);
				// wechatShare(1);//分享到微信好友
			}
		});
		friendImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();
				try {
					mController.setShareMedia(new UMImage(YygloActivity.this, ""));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// 设置分享内容
				mController.setShareContent(path);
				mController.directShare(YygloActivity.this, SHARE_MEDIA.WEIXIN_CIRCLE, new SnsPostListener() {

					@Override
					public void onStart() {
						Toast.makeText(YygloActivity.this, "分享开始", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
						if (eCode == StatusCode.ST_CODE_SUCCESSED) {
							Toast.makeText(YygloActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(YygloActivity.this, "分享失败", Toast.LENGTH_SHORT).show();
						}
					}
				});
				// wxApi.registerApp(WX_APP_ID);
				// wechatShare(0);//分享到微信朋友圈
			}
		});
		cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				popupWindow.dismiss();
			}
		});
	}

	private void initView() {
		// TODO Auto-generated method stub
		shareImg = (ImageView) findViewById(R.id.shareicon);
		view = LayoutInflater.from(this).inflate(R.layout.popup_share, null);
		sinaImg = (ImageView) view.findViewById(R.id.img1);
		weixinImg = (ImageView) view.findViewById(R.id.img2);
		friendImg = (ImageView) view.findViewById(R.id.img3);
		cancel = (Button) view.findViewById(R.id.cancel);
		// mWeibo = Weibo.getInstance(APP_KEY, REDIRECT_URL);
	}

}
