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
//import com.tencent.mm.sdk.openapi.WXMediaMessage;
//import com.tencent.mm.sdk.openapi.WXTextObject;

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

public class YyglActivity extends Activity {
	private final static String APP_KEY = "111182051";
	private final static String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";

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
	private Context tag = YyglActivity.this;
	private String content = "share content";
	private String lat = "90.00", lon = "90.00";

	UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share", RequestType.SOCIAL);
	String appID = "wxf68fd078c1d9f53a";
	String contentUrl = "http://www.ccjoy.cn/joylandweb/main.html";
	String aaa = "文字描述：\n公交：\n常州市区常州市中心：乘坐BRT1号到武进公交中心站，转73到环球动漫嬉戏谷站下\n火车站：坐68路或70路在潘家桥下\n\n【直达大巴】：\n常州火车站常州客运站38.3公里：乘坐常州客运嬉戏谷专线车直达园区。\n无锡火车站无锡客运站38公里：乘坐无锡客运嬉戏谷专线车直达园区。";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yygl_activity);
		BottomBar bottomBar = new BottomBar(this);
		initView();
		initListener();

	}

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
				UMWXHandler wxHandler = mController.getConfig().supportWXPlatform(YyglActivity.this, appID, contentUrl);
				wxHandler.setWXTitle("嬉戏谷欢迎你!");
				UMWXHandler circleHandler = mController.getConfig().supportWXCirclePlatform(YyglActivity.this, appID,
						contentUrl);
				circleHandler.setCircleTitle("嬉戏谷欢迎你!");
			}
		});
		sinaImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();
				// mController.getConfig().setSsoHandler(new SinaSsoHandler());

				try {
					mController.setShareMedia(new UMImage(YyglActivity.this, ""));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				mController.setShareContent(aaa);

				mController.postShare(YyglActivity.this, SHARE_MEDIA.SINA, new SnsPostListener() {
					public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
						Toast.makeText(YyglActivity.this, "分享完成", Toast.LENGTH_SHORT).show();
					}

					public void onStart() {
						Toast.makeText(YyglActivity.this, "开始分享", Toast.LENGTH_SHORT).show();
					}
				});
				// mWeibo.authorize(tag, new AuthDialogListener());

			}
		});

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();
			}
		});
		weixinImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();

				try {
					mController.setShareMedia(new UMImage(YyglActivity.this, ""));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// 设置分享内容
				mController.setShareContent(aaa);
				mController.directShare(YyglActivity.this, SHARE_MEDIA.WEIXIN, new SnsPostListener() {

					@Override
					public void onStart() {
						Toast.makeText(YyglActivity.this, "分享开始", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
						if (eCode == StatusCode.ST_CODE_SUCCESSED) {
							Toast.makeText(YyglActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(YyglActivity.this, "分享失败", Toast.LENGTH_SHORT).show();
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
					mController.setShareMedia(new UMImage(YyglActivity.this, ""));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// 设置分享内容
				mController.setShareContent(aaa);
				mController.directShare(YyglActivity.this, SHARE_MEDIA.WEIXIN_CIRCLE, new SnsPostListener() {

					@Override
					public void onStart() {
						Toast.makeText(YyglActivity.this, "分享开始", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
						if (eCode == StatusCode.ST_CODE_SUCCESSED) {
							Toast.makeText(YyglActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(YyglActivity.this, "分享失败", Toast.LENGTH_SHORT).show();
						}
					}
				});
				// wxApi.registerApp(WX_APP_ID);
				// wechatShare(0);//分享到微信朋友圈
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
