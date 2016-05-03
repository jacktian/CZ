package com.android.user;

import java.util.ArrayList;
import java.util.List;

import com.android.common.IntentAction;
import com.android.util.Util;
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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class GlCardActivitynew extends Activity {
	// private String APP_KEY = "114640118";
	// private String REDIRECT_URL = "cd693f8ebae3fdd9a23e89e8d1e8e729";

	private AnimationDrawable anim;
	// private ViewFlipper flipper;
	// private GestureDetector detector;
	// private ImageView shareicon;
	private View view, dialogView;
	private PopupWindow popupWindow;
	// private Dialog dialog;
	private TextView cardid;
	private ImageView sinaImg;
	private ImageView weixinImg;
	private ImageView friendImg;
	private int current = 1;
	// private Weibo mWeibo;
	// public Oauth2AccessToken accessToken;
	// private String content = "share content";
	// private String lat = "90.00", lon = "90.00";
	// private Button introbtn1, sininbtn1, navibtn1, experiencebtn1;
	// private Button introbtn2, sininbtn2, navibtn2, experiencebtn2;
	// private Button introbtn3, sininbtn3, navibtn3, experiencebtn3;
	// private IWXAPI wxApi;
	public String WX_APP_ID = "wxf68fd078c1d9f53a";
	private Button cancel;
	String appID = "wxf68fd078c1d9f53a";
	String contentUrl = "http://www.ccjoy.cn/joylandweb/main.html";
	UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share", RequestType.SOCIAL);
	String welcome = "欢迎来到嬉戏谷！";
	private ViewPager viewPager;
	private int[] contentids = { R.string.mrzy, R.string.jlh, R.string.cqtx, R.string.sds, R.string.xjcs, R.string.msdl,
			R.string.hxsl, R.string.lkwg, R.string.wmssj };
	private List<View> views = new ArrayList<View>();
	private int imageids[] = { R.drawable.recommend_proj_52x, R.drawable.recommend_proj_42x,
			R.drawable.recommend_proj_32x, R.drawable.recommend_proj_22x, R.drawable.recommend_proj_12x,
			R.drawable.recommend_proj_02x, R.drawable.recommend_proj_add1, R.drawable.recommend_proj_add2,
			R.drawable.recommend_proj_add3 };
	private int titleids[] = { R.drawable.recommend_title_52x, R.drawable.recommend_title_42x,
			R.drawable.recommend_title_32x, R.drawable.recommend_title_22x, R.drawable.recommend_title_12x,
			R.drawable.recommend_title_02x, R.drawable.recommend_title_add1, R.drawable.recommend_title_add2,
			R.drawable.recommend_title_add3 };
	private BasePop basePop;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.glcard_activitynew);
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
		UMWXHandler wxHandler = mController.getConfig().supportWXPlatform(GlCardActivitynew.this, appID, contentUrl);
		wxHandler.setWXTitle("嬉戏谷欢迎你!");
		UMWXHandler circleHandler = mController.getConfig().supportWXCirclePlatform(GlCardActivitynew.this, appID,
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
				mController.setShareMedia(new UMImage(GlCardActivitynew.this, curpic(current)));

				mController.postShare(GlCardActivitynew.this, SHARE_MEDIA.SINA, new SnsPostListener() {
					public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
						Toast.makeText(GlCardActivitynew.this, "分享完成", Toast.LENGTH_SHORT).show();
					}

					public void onStart() {
						Toast.makeText(GlCardActivitynew.this, "开始分享", Toast.LENGTH_SHORT).show();
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
				mController.setShareMedia(new UMImage(GlCardActivitynew.this, curpic(current)));
				mController.directShare(GlCardActivitynew.this, SHARE_MEDIA.WEIXIN, new SnsPostListener() {

					@Override
					public void onStart() {
						Toast.makeText(GlCardActivitynew.this, "分享开始", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
						if (eCode == StatusCode.ST_CODE_SUCCESSED) {
							Toast.makeText(GlCardActivitynew.this, "分享成功", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(GlCardActivitynew.this, "分享失败", Toast.LENGTH_SHORT).show();
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
				mController.setShareMedia(new UMImage(GlCardActivitynew.this, curpic(current)));
				mController.directShare(GlCardActivitynew.this, SHARE_MEDIA.WEIXIN_CIRCLE, new SnsPostListener() {

					@Override
					public void onStart() {
						Toast.makeText(GlCardActivitynew.this, "分享开始", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onComplete(SHARE_MEDIA platform, int eCode, SocializeEntity entity) {
						if (eCode == StatusCode.ST_CODE_SUCCESSED) {
							Toast.makeText(GlCardActivitynew.this, "分享成功", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(GlCardActivitynew.this, "分享失败", Toast.LENGTH_SHORT).show();
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
			bitmap_id = R.drawable.sreshota7;
			break;
		case 8:
			bitmap_id = R.drawable.sreshota8;
			break;
		case 9:
			bitmap_id = R.drawable.sreshota9;
			break;
		default:
			break;
		}
		return bitmap_id;
	}

	@SuppressLint("InflateParams")
	private void initView() {

		viewPager = (ViewPager) findViewById(R.id.glcardviewpage);
		LayoutInflater m_inflater;
		m_inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		views.clear();
		for (int i = 0; i < imageids.length; i++) {
			View view = m_inflater.inflate(R.layout.glcarditem, null);
			ImageView imageView = (ImageView) view.findViewById(R.id.glcarditemimg);

			imageView.setImageResource(imageids[i]);
			ImageView titleimg = (ImageView) view.findViewById(R.id.glcarditemtitle);
			titleimg.setImageResource(titleids[i]);

			views.add(view);
		}
		viewPager.setAdapter(new MyViewPagerAdapter(views));
		dialogView = m_inflater.inflate(R.layout.card_dialog, null);
		basePop = new BasePop(GlCardActivitynew.this, dialogView);

		cardid = (TextView) dialogView.findViewById(R.id.cardid);
		view = m_inflater.inflate(R.layout.popup_share, null);
		sinaImg = (ImageView) view.findViewById(R.id.img1);
		weixinImg = (ImageView) view.findViewById(R.id.img2);
		friendImg = (ImageView) view.findViewById(R.id.img3);
		// mWeibo = Weibo.getInstance(APP_KEY, REDIRECT_URL);
		cancel = (Button) view.findViewById(R.id.cancel);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.gc();
	}

	public class MyViewPagerAdapter extends PagerAdapter {
		private List<View> mListViews;

		public MyViewPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;// 构造方法，参数是我们的页卡，这样比较方便。
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(mListViews.get(position));// 删除页卡
		}

		@Override
		public Object instantiateItem(ViewGroup container, final int position) { // 这个方法用来实例化页卡
			container.addView(mListViews.get(position), 0);// 添加页卡
			Button introbutton = (Button) mListViews.get(position).findViewById(R.id.introbtn);
			introbutton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					basePop.showAtLocation(findViewById(R.id.layoutlu), Gravity.CENTER, 0, 0);
					cardid.setText(getResources().getString(contentids[position]).toString());
				}
			});
			Button sininbutton = (Button) mListViews.get(position).findViewById(R.id.sininbtn);
			sininbutton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Util.showDialg(GlCardActivitynew.this, getResources().getString(R.string.sign), 0);
					new Handler().postDelayed(new Runnable() {

						public void run() {
							current = position + 1;
							Util.dissmissDialog();
							initPopup();

						}

					}, 1000);
				}
			});
			Button navibtnbutton = (Button) mListViews.get(position).findViewById(R.id.navibtn);
			navibtnbutton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
			});

			Button experiencebtn = (Button) mListViews.get(position).findViewById(R.id.experiencebtn);
			experiencebtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// finish();
					Intent intent = new Intent();
					intent.setAction(IntentAction.CARDFIR_ACTIVITY);
					intent.putExtra("whichcard", position);
					startActivity(intent);
				}
			});

			return mListViews.get(position);
		}

		@Override
		public int getCount() {
			return mListViews.size();// 返回页卡的数量
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;// 官方提示这样写
		}
	}
}
