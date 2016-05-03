package com.android.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.android.common.HomeNews;
import com.android.common.IntentAction;
import com.android.net.HomeNewsService;
import com.android.util.Util;
import com.android.util.WigetUtil;
import com.android.view.BottomBar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.JsonReader;
import android.util.JsonToken;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.ImageView;
import android.widget.TextView;
import net.tsz.afinal.FinalBitmap;

public class MainActivity extends Activity {
	private ImageView mYygl;
	private ImageView mTstj;
	private ImageView mJtlx;
	private ImageView mYysj;
	private ImageView mJjzl;
	private ImageView mZxgp;
	private ImageView weather;
	private TextView temp;
	private AnimationDrawable anim;
	private Context context;
	private ImageView homeImageView;

	private List<HomeNews> news;
	private FinalBitmap fb;
	int curpage = 0;
	private String tempfirst;
	private String tempsecond;
	private String weatherString;
	private boolean istoday = true;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		fb = FinalBitmap.create(this);
		news = new ArrayList<HomeNews>();
		initView();
		initListener();
	}

	Timer timer = new Timer();
	Handler handler3 = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 3:
				if (news.size() == 1) {
					fb.display(homeImageView, news.get(0).getImagefile());
				} else {
					if (curpage < news.size()) {
						fb.display(homeImageView, news.get(curpage).getImagefile());
						Handler m_Handler = new Handler();
						m_Handler.postDelayed(new Runnable() {
							public void run() {
								curpage = curpage + 1;
								if (curpage == news.size()) {
									curpage = 0;
								}
								Message message = new Message();
								message.what = 3;
								handler3.sendMessage(message);// 发送消息
							}
						}, 5000);

					} else {
						Handler m_Handler = new Handler();
						m_Handler.postDelayed(new Runnable() {
							public void run() {
								curpage = 0;
								Message message = new Message();
								message.what = 3;
								handler3.sendMessage(message);// 发送消息
							}
						}, 5000);
					}
				}
				break;
			case 4:
				fb.display(homeImageView, news.get(0).getImagefile());
				break;
			}
			super.handleMessage(msg);
		};
	};

	// URL中文修改
	private static String zhPattern = "[\\u4e00-\\u9fa5]+";

	public static String encode(String str, String charset) throws UnsupportedEncodingException {
		str = str.replaceAll(" ", "+");// 对空字符串进行处理
		Pattern p = Pattern.compile(zhPattern);
		Matcher m = p.matcher(str);
		StringBuffer b = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(b, URLEncoder.encode(m.group(0), charset));
		}
		m.appendTail(b);
		return b.toString();
	}

	private void initNew() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					news = new HomeNewsService().getHomeNews();

					// HomeNews homeNews=new HomeNews();
					// homeNews.setNum("22");
					// homeNews.setImagefile("http://218.93.39.237:8082/TravelMg/UpFile/News/img_201461.jpg");
					// homeNews.setDescription("11111111111111111动型、巡回展览的科普平台，共分数字气象站、数字保健站、数字小影院、迷你科技馆（台式与挂壁）、智造小工坊、脑筋急转弯、科普广角镜、虚拟实验室、科普新视窗、科普小剧场等十大板块168项展品，基本涵盖了大型科技馆主要类别和形式。至今已开展“科普新干线进五区（进校区、进社区、进街区、进园区、进营区）”巡展活动600余场次，深受各年龄群体的欢迎。此次“科普新干线”抵达嬉戏谷，将为大家呈现生动有趣的科普知识，带领大家领略科技的无穷魅力，既可大大增强孩子们探究科学奥秘的兴趣，拓展课余生活，还能激发孩子们的创新意识与能力。\r\n惊喜不只如此，5月31日—6月2日期间，家长凡在窗口购票，同行一名1.5米以下孩子即可享免费入园。需要提醒的是，检票时，家长和孩子需同时佩戴红领带才可享此优惠。这个儿童节，想给孩子一次与众不同的经历么？那就快来常州嬉戏谷吧");
					// news.add(homeNews);
					// HomeNews homeNews1=new HomeNews();
					// homeNews1.setNum("23");
					// homeNews1.setImagefile("http://www.dswf.cn/file/upload/201301/17/22-07-24-85-1.jpg");
					// homeNews1.setDescription("2222222222222222动型、巡回展览的科普平台，共分数字气象站、数字保健站、数字小影院、迷你科技馆（台式与挂壁）、智造小工坊、脑筋急转弯、科普广角镜、虚拟实验室、科普新视窗、科普小剧场等十大板块168项展品，基本涵盖了大型科技馆主要类别和形式。至今已开展“科普新干线进五区（进校区、进社区、进街区、进园区、进营区）”巡展活动600余场次，深受各年龄群体的欢迎。此次“科普新干线”抵达嬉戏谷，将为大家呈现生动有趣的科普知识，带领大家领略科技的无穷魅力，既可大大增强孩子们探究科学奥秘的兴趣，拓展课余生活，还能激发孩子们的创新意识与能力。\r\n惊喜不只如此，5月31日—6月2日期间，家长凡在窗口购票，同行一名1.5米以下孩子即可享免费入园。需要提醒的是，检票时，家长和孩子需同时佩戴红领带才可享此优惠。这个儿童节，想给孩子一次与众不同的经历么？那就快来常州嬉戏谷吧");
					// news.add(homeNews1);
					// HomeNews homeNews2=new HomeNews();
					// homeNews2.setNum("24");
					// homeNews2.setImagefile("http://i1.sinaimg.cn/qc/2013/0518/U6053P33DT20130518175442.png");
					// homeNews2.setDescription("3333333333333333动型、巡回展览的科普平台，共分数字气象站、数字保健站、数字小影院、迷你科技馆（台式与挂壁）、智造小工坊、脑筋急转弯、科普广角镜、虚拟实验室、科普新视窗、科普小剧场等十大板块168项展品，基本涵盖了大型科技馆主要类别和形式。至今已开展“科普新干线进五区（进校区、进社区、进街区、进园区、进营区）”巡展活动600余场次，深受各年龄群体的欢迎。此次“科普新干线”抵达嬉戏谷，将为大家呈现生动有趣的科普知识，带领大家领略科技的无穷魅力，既可大大增强孩子们探究科学奥秘的兴趣，拓展课余生活，还能激发孩子们的创新意识与能力。\r\n惊喜不只如此，5月31日—6月2日期间，家长凡在窗口购票，同行一名1.5米以下孩子即可享免费入园。需要提醒的是，检票时，家长和孩子需同时佩戴红领带才可享此优惠。这个儿童节，想给孩子一次与众不同的经历么？那就快来常州嬉戏谷吧");
					// news.add(homeNews2);

				} catch (Exception e) {
					e.printStackTrace();
				}
				if (news.size() >= 1) { // 处理URL中文
					for (int i = 0; i < news.size(); i++) {
						try {
							news.get(i).setImagefile(encode(news.get(i).getImagefile(), "utf-8"));
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					Message message = new Message();
					message.what = 3;
					handler3.sendMessage(message);
				}
			}
		}).start();
	}

	private void initView() {
		mYygl = (ImageView) findViewById(R.id.yygl);
		mTstj = (ImageView) findViewById(R.id.tstj);
		mJtlx = (ImageView) findViewById(R.id.jtlx);
		mYysj = (ImageView) findViewById(R.id.yysj);
		mJjzl = (ImageView) findViewById(R.id.jjzl);
		mZxgp = (ImageView) findViewById(R.id.zxgp);
		weather = (ImageView) findViewById(R.id.weather);
		temp = (TextView) findViewById(R.id.temp);
		homeImageView = (ImageView) findViewById(R.id.homeImageView);
		int sysVersion = Integer.parseInt(VERSION.SDK);
		if (sysVersion >= 11)
			homeImageView.setRotation(5);
		BottomBar bottomBar = new BottomBar(this);
		thread.start();
	}

	private void initWeather() {
		// FinalHttp http=new FinalHttp();
		// AjaxParams params=new AjaxParams();
		// params.put("apikey", "6ea3c94475761a73e5c6d1c4a605a4ce");
		// params.put("city", "changzhou");
		// http.get("http://apis.baidu.com/heweather/weather/free", params, new
		// AjaxCallBack<Object>() {
		// @Override
		// public void onSuccess(Object t) {
		// // TODO Auto-generated method stub
		// super.onSuccess(t);
		// t=t.toString();
		// }
		// public void onFailure(Throwable t, int errorNo, String strMsg) {
		//
		// };
		// });

		// System.out.println(jsonResult);

		new Thread(new Runnable() {
			public void run() {
				// String
				// weatherUrl="http://www.weather.com.cn/data/cityinfo/101191101.html";
				// String weatherJson=queryStringForGet(weatherUrl);

				String httpUrl = "http://apis.baidu.com/heweather/weather/free";
				String httpArg = "city=changzhou";
				String weatherJson = request(httpUrl, httpArg);
				JsonReader reader = new JsonReader(new StringReader(weatherJson));
				try {
					reader.beginObject();
					while (reader.hasNext()) {
						String name = reader.nextName();
						if (name.equals("HeWeather data service 3.0") && reader.peek() == JsonToken.BEGIN_ARRAY) {
							reader.beginArray();
							while (reader.hasNext()) {
								reader.beginObject();
								while (reader.hasNext()) {

									String name1 = reader.nextName();
									if (name1.equals("daily_forecast") && reader.peek() == JsonToken.BEGIN_ARRAY) {
										reader.beginArray();
										while (reader.hasNext()) {
											reader.beginObject();
											while (reader.hasNext()) {
												String name2 = reader.nextName();
												if (name2.equals("cond") && reader.peek() == JsonToken.BEGIN_OBJECT) {
													reader.beginObject();
													while (reader.hasNext()) {
														String name3 = reader.nextName();
														if (name3.equals("txt_d")) {

															if (istoday) {

																weatherString = reader.nextString();
															} else {
																reader.nextString();
															}

														} else {
															reader.skipValue();
														}
													}
													reader.endObject();
												} else if (name2.equals("tmp")
														&& reader.peek() == JsonToken.BEGIN_OBJECT) {
													reader.beginObject();
													while (reader.hasNext()) {
														String name4 = reader.nextName();
														if (name4.equals("max")) {

															if (istoday) {

																tempsecond = reader.nextString();
															} else {
																reader.nextString();
															}

														} else if (name4.equals("min")) {

															if (istoday) {

																tempfirst = reader.nextString();
															} else {
																reader.nextString();
															}

														} else {
															reader.skipValue();
														}

													}

													reader.endObject();
												} else {
													reader.skipValue();
												}

											}

											reader.endObject();
											istoday = false;

										}
										reader.endArray();
									} else {
										reader.skipValue();
									}
								}
								reader.endObject();
							}
							reader.endArray();
						} else {
							reader.skipValue();
						}

					}
					reader.endObject();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						reader.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				Message message = new Message();
				message.what = 1;
				thandler.sendMessage(message);

			}
		}).start();
	}

	/**
	 * @param urlAll
	 *            :请求接口
	 * @param httpArg
	 *            :参数
	 * @return 返回结果
	 */
	public static String request(String httpUrl, String httpArg) {
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		httpUrl = httpUrl + "?" + httpArg;

		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			// 填入apikey到HTTP header
			connection.setRequestProperty("apikey", "6ea3c94475761a73e5c6d1c4a605a4ce");
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			result = sbf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	Handler thandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.what == 1) {

				try {

					String str = tempfirst + "°" + "~" + tempsecond + "°";
					temp.setText(str);
					String wea = weatherString;
					if (wea.equals("小雨")) {
						weather.setBackgroundResource(R.drawable.weather_rainy);
					} else if (wea.equals("晴")) {
						weather.setBackgroundResource(R.drawable.weather_sunny);
					} else if (wea.equals("雪")) {
						weather.setBackgroundResource(R.drawable.weather_snow);
					} else if (wea.equals("阴")) {
						weather.setBackgroundResource(R.drawable.weather_overcast);
					} else {
						weather.setBackgroundResource(R.drawable.weather_cloudy);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	};

	private String queryStringForGet(String url) {
		HttpGet request = new HttpGet(url);
		String result = null;
		try {
			HttpResponse response = new DefaultHttpClient().execute(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (org.apache.http.ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	private void initListener() {
		homeImageView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (news.size() >= 1) {
					try {
						Intent intent = new Intent(IntentAction.HOMEDETAIL_ACTIVITY);
						intent.putExtra("homedetail", String.valueOf(news.get(curpage).getDescription()));
						intent.putExtra("imageurl", String.valueOf(news.get(curpage).getImagefile()));
						startActivity(intent);
					} catch (Exception e) {
					}
				}
			}
		});
		mYygl.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mYygl.setImageResource(R.drawable.frame_animation);
				AnimationDrawable animationDrawable = (AnimationDrawable) mYygl.getDrawable();
				animationDrawable.start();
				Intent intent = new Intent(IntentAction.YYGLO_ACTIVITY);
				startActivity(intent);
			}
		});
		mTstj.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mTstj.setImageResource(R.drawable.frame_animation);
				AnimationDrawable animationDrawable = (AnimationDrawable) mTstj.getDrawable();
				animationDrawable.start();
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, TstjActivity.class);
				startActivity(intent);
			}
		});
		mJtlx.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mJtlx.setImageResource(R.drawable.frame_animation);
				AnimationDrawable animationDrawable = (AnimationDrawable) mJtlx.getDrawable();
				animationDrawable.start();
				Intent intent = new Intent(IntentAction.JTLX_ACTIVITY);
				startActivity(intent);
			}
		});
		mYysj.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mYysj.setImageResource(R.drawable.frame_animation);
				AnimationDrawable animationDrawable = (AnimationDrawable) mYysj.getDrawable();
				animationDrawable.start();
				Intent intent = new Intent(IntentAction.YYSJ_ACTIVITY);
				startActivity(intent);
			}
		});
		mJjzl.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mJjzl.setImageResource(R.drawable.frame_animation);
				AnimationDrawable animationDrawable = (AnimationDrawable) mJjzl.getDrawable();
				animationDrawable.start();
				Intent intent = new Intent(IntentAction.JJZL_ACTIVITY);
				startActivity(intent);
			}
		});
		mZxgp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mZxgp.setImageResource(R.drawable.frame_animation);
				AnimationDrawable animationDrawable = (AnimationDrawable) mZxgp.getDrawable();
				animationDrawable.start();
				// Intent intent = new Intent(IntentAction.ONLINE_ACTIVITY);
				// startActivity(intent);
				Intent intent = new Intent();
				intent.setAction("android.intent.action.VIEW");
				Uri content_url = Uri.parse("http://twap.ccgogogo.com/");
				intent.setData(content_url);
				startActivity(intent);
			}
		});
	}

	OnPreDrawListener opdl = new OnPreDrawListener() {
		public boolean onPreDraw() {
			// TODO Auto-generated method stub
			anim.start();
			return true;
		}
	};

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mYygl.setImageResource(R.drawable.home_yyxl2x);
		mTstj.setImageResource(R.drawable.home_tese2x);
		mJtlx.setImageResource(R.drawable.home_jiaotong2x);
		mYysj.setImageResource(R.drawable.home_yysj2x);
		mJjzl.setImageResource(R.drawable.home_showway2x);
		mZxgp.setImageResource(R.drawable.home_xxgnk2x);
	}

	protected void onDestroy() {
		super.onDestroy();
	};

	Thread thread = new Thread() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (!isOpenNetWork()) {
				Message message = new Message();
				message.what = 1;
				handler.sendMessage(message);
				homeImageView.setClickable(false);
			} else {
				homeImageView.setClickable(true);
				initNew();
				initWeather();
				// timer.schedule(task,1000,2000);
			}
			super.run();
		}
	};

	private boolean isOpenNetWork() {
		ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connManager.getActiveNetworkInfo() != null) {
			return connManager.getActiveNetworkInfo().isAvailable();
		}
		return false;
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				WigetUtil.alertDialogWithnet(MainActivity.this, "", getResources().getString(R.string.remind));
				break;
			default:
				break;
			}
		};
	};

	private void showDialog() {
		Util.showDialg(context, context.getResources().getString(R.string.plswait).toString(),
				ProgressDialog.STYLE_SPINNER);
	}
}
