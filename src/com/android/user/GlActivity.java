package com.android.user;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import com.android.common.BusyIdleStatusBean;
import com.android.model.MapObjectContainer;
import com.android.model.MapObjectModel;
import com.android.net.BusyIdleService;
import com.android.popup.TextPopup;
import com.android.util.MarketDialog;
import com.android.util.Util;
import com.android.util.WigetUtil;
import com.android.view.BottomBar;
import com.ls.widgets.map.MapWidget;
import com.ls.widgets.map.config.GPSConfig;
import com.ls.widgets.map.config.MapGraphicsConfig;
import com.ls.widgets.map.config.OfflineMapConfig;
import com.ls.widgets.map.events.MapScrolledEvent;
import com.ls.widgets.map.events.MapTouchedEvent;
import com.ls.widgets.map.events.ObjectTouchEvent;
import com.ls.widgets.map.interfaces.Layer;
import com.ls.widgets.map.interfaces.MapEventsListener;
import com.ls.widgets.map.interfaces.OnLocationChangedListener;
import com.ls.widgets.map.interfaces.OnMapScrollListener;
import com.ls.widgets.map.interfaces.OnMapTouchListener;
import com.ls.widgets.map.model.MapObject;
import com.ls.widgets.map.utils.PivotFactory;
import com.ls.widgets.map.utils.PivotFactory.PivotPosition;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GlActivity extends Activity implements MapEventsListener, OnMapTouchListener {
	private static final String TAG = "BrowseMapActivity";
	private static final Integer LAYER1_ID = 0;
	private static final Integer LAYER2_ID = 1;
	private static final Integer LAYER3_ID = 2;
	private static final Integer LAYER4_ID = 3;
	private static final Integer LAYER5_ID = 4;
	private static final int MAP_ID = 23;
	private int nextObjectId;
	private int pinHeight;
	private MapObjectContainer model;
	private MapWidget map;
	private TextPopup mapObjectInfoPopup;
	private Location points[];
	private int currentPoint;
	private MarketDialog dialog;
	private ImageView collectIcon;
	private ImageView cardicon;
	private MarketDialog collectdialog;
	private Button cateGory1;
	private Button cateGory3;
	private Button cateGory4;
	private Button cateGory5;
	private ImageButton stateRefreshBtn;
	private Bundle savedInstanceState;

	private Layer layer1;
	private Layer layer2;
	private Layer layer3;
	private Layer layer4;
	private Layer layer5;
	private TextView txt;
	private ImageView img;
	private List<BusyIdleStatusBean> status;
	private int mresh = 0;
	OfflineMapConfig config;
	ProgressDialog m_pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gl_activity);
		BottomBar bottomBar = new BottomBar(this);
		initView();
		model = new MapObjectContainer();
		// initTestLocationPoints();
		initMap(savedInstanceState);
		nextObjectId = 0;
		initMapObjects();
		initMapListeners();
		layer3.setVisible(false);
		layer4.setVisible(false);
		layer5.setVisible(false);
		layer2.setVisible(true);
		map.centerMap();
		m_pDialog = new ProgressDialog(GlActivity.this);
		m_pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		m_pDialog.setMessage("正在加载。。。");
		m_pDialog.setIndeterminate(true);
		m_pDialog.setCancelable(true);
		initListener();
		if (!isOpenNetWork()) {
			WigetUtil.alertDialogWithnet(GlActivity.this, "", getResources().getString(R.string.remindgl));
		} else {
			refresh();
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		map = null;

		layer1.clearAll();
		layer2.clearAll();
		layer3.clearAll();
		layer4.clearAll();
		layer5.clearAll();
		layer1 = null;
		layer2 = null;
		layer3 = null;
		layer4 = null;
		layer5 = null;

		super.onDestroy();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// if (map==null) {

		// }
	}

	private boolean isOpenNetWork() {
		ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connManager.getActiveNetworkInfo() != null) {
			return connManager.getActiveNetworkInfo().isAvailable();
		}
		return false;
	}

	private void initView() {
		// TODO Auto-generated method stub
		collectIcon = (ImageView) findViewById(R.id.collecticon);
		cardicon = (ImageView) findViewById(R.id.cardicon);
		stateRefreshBtn = (ImageButton) findViewById(R.id.staterefreshbtn);
	}

	private void initListener() {
		// TODO Auto-generated method stub
		collectIcon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				initCateDialog();
			}
		});
		cardicon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// if (map!=null) {
				//
				// map.clearLayers();
				// map=null;
				// }
				// finish();
				Intent intent = new Intent(GlActivity.this, GlCardActivitynew.class);
				startActivity(intent);
			}
		});
		stateRefreshBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (isOpenNetWork()) {
					if (mresh == 0)
						refresh();
					else
						refresh1();
				} else {
					Toast.makeText(GlActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	private void refresh() {
		m_pDialog.show();
		// Util.showDialg(GlActivity.this,getResources().getString(R.string.staterefreshbtn),0);
		// new Thread( new Runnable() {
		// public void run() {
		// while (true) {
		// try {
		// Thread.sleep(10000);
		//
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		// }
		// }).start();

		new Handler().postDelayed(new Runnable() {

			public void run() {

				new Thread(new runnable()).start();
			}

		}, 1000);

	}

	private void refresh1() {
		// Util.showDialg(GlActivity.this,getResources().getString(R.string.staterefreshbtn),0);
		m_pDialog.show();
		new Handler().postDelayed(new Runnable() {

			public void run() {
				new Thread(new runnable1()).start();

			}

		}, 1000);

	}

	class runnable implements Runnable {
		public void run() {
			try {
				getStatus();
				Message message = new Message();
				message.what = 1;
				handler.sendMessage(message);// 发送消息
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				m_pDialog.dismiss();
			}
		}
	}

	class runnable1 implements Runnable {
		public void run() {
			try {
				getStatus();
				Message message = new Message();
				message.what = 1;
				handler1.sendMessage(message);// 发送消息
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				m_pDialog.dismiss();
			}
		}
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if (status == null) {
					Toast.makeText(GlActivity.this, "数据错误", Toast.LENGTH_SHORT).show();
				} else {
					updateStatus();

				}
				// Util.dissmissDialog();

				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};
	Handler handler1 = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if (status == null) {
					Toast.makeText(GlActivity.this, "数据错误", Toast.LENGTH_SHORT).show();
				} else {
					updateStatus1();
				}
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};

	private void getStatus() throws MalformedURLException, IOException, XmlPullParserException {
		BusyIdleService service = new BusyIdleService();
		status = service.getBusyIdleStatus();

	}

	private BusyIdleStatusBean getBusyIdleStatusBeanByName(String name) {
		for (BusyIdleStatusBean bean : status) {
			if (name.equals(bean.getSubdistrictname())) {
				return bean;
			}
		}
		return null;
	}

	private void updateStatus() {
		for (Integer id = 0; id < nextObjectId; id++) {
			MapObject o = layer2.getMapObject(id);
			if (o == null)
				continue;
			String name = getMapObjectName(id);
			if (name == null)
				continue;
			BusyIdleStatusBean bean = getBusyIdleStatusBeanByName(name);
			if (bean != null) {
				addScalableMapObjectSceneryByStatue(o, layer2, Integer.valueOf(bean.getBusyidlestatus()));
			}

		}
		Util.dissmissDialog();
		map.centerMap();
		// this.initMapListeners();
	}

	private void updateStatus1() {
		for (Integer id = 0; id < nextObjectId; id++) {
			MapObject o = layer3.getMapObject(id);
			if (o == null)
				continue;
			String name = getMapObjectName(id);
			if (name == null)
				continue;
			BusyIdleStatusBean bean = getBusyIdleStatusBeanByName(name);
			if (bean != null) {
				addScalableMapObjectSceneryByStatue1(o, layer3, Integer.valueOf(bean.getBusyidlestatus()));
			}
		}
		Util.dissmissDialog();
		map.centerMap();
		// this.initMapListeners();
	}

	private void initCateDialog() {
		// TODO Auto-generated method stub
		collectdialog = new MarketDialog(this, R.style.MarketDialog);
		// Window window = dialog.getWindow();
		LayoutInflater factory = LayoutInflater.from(this);
		final View dialogView = factory.inflate(R.layout.category_dialog, null);
		collectdialog.setContentView(dialogView);
		collectdialog.show();
		cateGory1 = (Button) dialogView.findViewById(R.id.category1);
		cateGory3 = (Button) dialogView.findViewById(R.id.category3);
		cateGory4 = (Button) dialogView.findViewById(R.id.category4);
		cateGory5 = (Button) dialogView.findViewById(R.id.category5);
		cateGory1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mresh = 0;
				collectdialog.dismiss();
				layer3.setVisible(false);
				layer4.setVisible(false);
				layer5.setVisible(false);
				layer2.setVisible(true);
				map.centerMap();

			}
		});
		cateGory3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub]
				mresh = 1;
				collectdialog.dismiss();

				if (isOpenNetWork()) {
					refresh1();
				} else {
					Toast.makeText(GlActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
				}

				layer2.setVisible(false);
				layer4.setVisible(false);
				layer5.setVisible(false);
				layer3.setVisible(true);
				map.centerMap();

			}
		});
		cateGory4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				collectdialog.dismiss();
				layer2.setVisible(false);
				layer4.setVisible(true);
				layer5.setVisible(false);
				layer3.setVisible(false);
				map.centerMap();
			}
		});
		cateGory5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				collectdialog.dismiss();
				layer2.setVisible(false);
				layer4.setVisible(false);
				layer5.setVisible(true);
				layer3.setVisible(false);
				map.centerMap();
			}
		});
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (map != null) {

			map.saveState(outState);
		}
	}

	private void initTestLocationPoints() {
		points = new Location[5];
		for (int i = 0; i < points.length; ++i) {
			points[i] = new Location("test");
		}
		points[0].setLatitude(3.2117012756213316);
		points[0].setLongitude(73.03506774997711);
		points[1].setLatitude(3.2122245926560167);
		points[1].setLongitude(73.03744733333588);
		points[2].setLatitude(3.2112819380469135);
		points[2].setLongitude(73.03983449935913);
		points[3].setLatitude(3.2130494147249915);
		points[3].setLongitude(73.03946435451508);
		points[4].setLatitude(3.2148276002942713);
		points[4].setLongitude(73.03796768188477);
		currentPoint = 0;
	}

	private Location getNextLocationPoint() {
		if (currentPoint < points.length - 1) {
			currentPoint += 1;
		} else {
			currentPoint = 0;
		}
		return points[currentPoint];
	}

	private void initMap(Bundle savedInstanceState) {
		map = new MapWidget(savedInstanceState, this, "map", 12); // initial
																	// zoom
																	// level
		map.setId(MAP_ID);
		config = map.getConfig();
		config.setZoomBtnsVisible(false); // Sets embedded zoom buttons visible
		config.setPinchZoomEnabled(true); // Sets pinch gesture to zoom
		config.setFlingEnabled(true); // Sets inertial scrolling of the map
		config.setMapCenteringEnabled(false);
		config.setMinZoomLevelLimit(3);
		// Configuration of GPS receiver
		GPSConfig gpsConfig = config.getGpsConfig();
		gpsConfig.setPassiveMode(false);
		gpsConfig.setGPSUpdateInterval(500, 5);
		// Configuration of position marker
		MapGraphicsConfig graphicsConfig = config.getGraphicsConfig();
		graphicsConfig.setAccuracyAreaColor(0x550000FF); // Blue with
															// transparency
		graphicsConfig.setAccuracyAreaBorderColor(Color.BLUE); // Blue without
																// transparency
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.rootLayout);
		// Adding the map to the layout
		layout.addView(map, 0);
		layout.setBackgroundColor(Color.parseColor("#0049FF"));
		// Adding layers in order to put there some map objects
		map.createLayer(LAYER1_ID); // you will need layer id's in order to
									// access particular layer
		map.createLayer(LAYER2_ID);
		map.createLayer(LAYER3_ID);
		map.createLayer(LAYER4_ID);
		map.createLayer(LAYER5_ID);
		layer1 = map.getLayerById(LAYER1_ID);
		layer2 = map.getLayerById(LAYER2_ID);
		layer3 = map.getLayerById(LAYER3_ID);
		layer4 = map.getLayerById(LAYER4_ID);
		layer5 = map.getLayerById(LAYER5_ID);
	}

	private void initModel() {
		// Adding objects to the model
		// You may want to implement your own model
		MapObjectModel objectModel = new MapObjectModel(0, 500, 300, "Shows above the image 1");
		model.addObject(objectModel);
		objectModel = new MapObjectModel(1, 600, 350, "Shows above the image 2");
		model.addObject(objectModel);
		int id = 2;
		for (Location point : points) {
			objectModel = new MapObjectModel(id, point, "Point " + id);
			model.addObject(objectModel);
			id += 1;
		}
	}

	private void initMapObjects() {
		mapObjectInfoPopup = new TextPopup(this, (RelativeLayout) findViewById(R.id.rootLayout));
		// Layer layer1 = map.getLayerById(LAYER1_ID);
		// layer2 = map.getLayerById(LAYER2_ID);
		for (int i = 0; i < model.size(); ++i) {
			addNotScalableMapObject(model.getObject(i), layer1);
		}
		// Adding two map objects to the second layer
		addScalableMapObjectScenery(420, 100, layer2);
		addScalableMapObjectScenery(420, 168, layer2);
		addScalableMapObjectScenery(460, 340, layer2);
		addScalableMapObjectScenery(460, 380, layer2);
		addScalableMapObjectScenery(540, 371, layer2);// 4
		addScalableMapObjectScenery(497, 462, layer2);//
		addScalableMapObjectScenery(583, 434, layer2);// yzmj
		addScalableMapObjectScenery(298, 377, layer2);// slxk
		addScalableMapObjectScenery(359, 425, layer2);
		addScalableMapObjectScenery(316, 432, layer2);// 9
		addScalableMapObjectScenery(400, 445, layer2);// tmhx
		addScalableMapObjectScenery(260, 460, layer2);// lszn
		addScalableMapObjectScenery(325, 513, layer2);// yueyin
		addScalableMapObjectScenery(416, 541, layer2);// smzd
		addScalableMapObjectScenery(400, 699, layer2);// jinglingbandao
		addScalableMapObjectScenery(566, 529, layer2);// bjgd15
		addScalableMapObjectScenery(571, 563, layer2);// yxys
		addScalableMapObjectScenery(544, 572, layer2);// cqm
		addScalableMapObjectScenery(516, 605, layer2);// fxqs18
		addScalableMapObjectScenery(484, 663, layer2);// mfjl
		addScalableMapObjectScenery(487, 712, layer2);// bbdp
		addScalableMapObjectScenery(567, 648, layer2);// 21 mmgzdj
		addScalableMapObjectScenery(582, 683, layer2);
		addScalableMapObjectScenery(526, 682, layer2);// gulugulu
		addScalableMapObjectScenery(506, 739, layer2);// 24
		addScalableMapObjectScenery(564, 749, layer2);// meq
		addScalableMapObjectScenery(250, 600, layer2);// msq
		addScalableMapObjectScenery(143, 679, layer2);
		addScalableMapObjectScenery(191, 682, layer2);// sxzc
		addScalableMapObjectScenery(225, 706, layer2);// mstt
		addScalableMapObjectScenery(254, 701, layer2);// msm
		addScalableMapObjectScenery(308, 730, layer2);// mhqt
		addScalableMapObjectScenery(250, 757, layer2);

		addScalableMapObjectFood(412, 358, layer3);// 33
		addScalableMapObjectFood(442, 383, layer3);
		addScalableMapObjectFood(453, 433, layer3);// shqz
		addScalableMapObjectFood(533, 431, layer3);// ttw
		addScalableMapObjectFood(524, 476, layer3);// cqmsj
		addScalableMapObjectFood(400, 456, layer3);
		addScalableMapObjectFood(510, 532, layer3);// ldcf
		addScalableMapObjectFood(433, 552, layer3);
		addScalableMapObjectFood(557, 614, layer3);
		addScalableMapObjectFood(513, 645, layer3);
		addScalableMapObjectFood(570, 711, layer3);// mekxdc
		addScalableMapObjectFood(440, 746, layer3);// drsz
		addScalableMapObjectFood(371, 788, layer3);
		addScalableMapObjectFood(442, 820, layer3);
		addScalableMapObjectFood(295, 472, layer3);
		addScalableMapObjectFood(234, 560, layer3);
		addScalableMapObjectFood(234, 682, layer3);
		addScalableMapObjectFood(233, 701, layer3);
		addScalableMapObjectFood(279, 689, layer3);// 51
		addScalableMapObjectService(440, 115, layer4);
		addScalableMapObjectService(382, 363, layer4);
		addScalableMapObjectService2(470, 363, layer4, 54);// 54
		addScalableMapObjectService(235, 451, layer4);
		addScalableMapObjectService2(553, 400, layer4, 56);// 56
		addScalableMapObjectService(214, 549, layer4);
		addScalableMapObjectService(195, 659, layer4);
		addScalableMapObjectService(244, 728, layer4);
		addScalableMapObjectService(220, 753, layer4);
		addScalableMapObjectService(333, 729, layer4);
		addScalableMapObjectService(345, 823, layer4);
		addScalableMapObjectService2(454, 807, layer4, 63);// 63
		addScalableMapObjectService2(450, 892, layer4, 64);// 64
		addScalableMapObjectService(451, 892, layer4);
		addScalableMapObjectService2(463, 828, layer4, 66);// 66
		addScalableMapObjectService(445, 837, layer4);
		addScalableMapObjectService(470, 822, layer4);
		addScalableMapObjectService(463, 785, layer4);
		addScalableMapObjectService2(233, 453, layer4, 70);// 70
		addScalableMapObjectService(454, 739, layer4);
		addScalableMapObjectService(498, 761, layer4);
		addScalableMapObjectService2(195, 680, layer4, 73);// 73
		addScalableMapObjectService(596, 721, layer4);
		addScalableMapObjectService(565, 618, layer4);
		addScalableMapObjectService(605, 489, layer4);
		addScalableMapObjectService(531, 421, layer4);
		addScalableMapObjectService(447, 330, layer4);
		addScalableMapObjectService(363, 755, layer4);
		addScalableMapObjectService(358, 801, layer4);
		addScalableMapObjectService(356, 816, layer4);
		addScalableMapObjectService(339, 889, layer4);
		addScalableMapObjectService(452, 809, layer4);
		addScalableMapObjectShop(479, 303, layer5);// 84
		addScalableMapObjectShop(494, 357, layer5);
		addScalableMapObjectShop(448, 394, layer5);
		addScalableMapObjectShop(467, 428, layer5);// 87
		addScalableMapObjectShop(504, 434, layer5);
		addScalableMapObjectShop(519, 443, layer5);
		addScalableMapObjectShop(517, 430, layer5);
		addScalableMapObjectShop(543, 516, layer5);
		addScalableMapObjectShop(513, 566, layer5);
		addScalableMapObjectShop(566, 676, layer5);
		addScalableMapObjectShop(529, 728, layer5);
		addScalableMapObjectShop(353, 777, layer5);
		addScalableMapObjectShop(366, 753, layer5);
		addScalableMapObjectShop(373, 772, layer5);
		addScalableMapObjectShop(357, 788, layer5);
		addScalableMapObjectShop(366, 812, layer5);
		addScalableMapObjectShop(345, 814, layer5);
		addScalableMapObjectShop(365, 823, layer5);
		addScalableMapObjectShop(357, 838, layer5);
		addScalableMapObjectShop(371, 839, layer5);
		addScalableMapObjectShop(436, 777, layer5);
		addScalableMapObjectShop(446, 793, layer5);
		addScalableMapObjectShop(458, 814, layer5);
		addScalableMapObjectShop(255, 502, layer5);
		addScalableMapObjectShop(276, 531, layer5);
		addScalableMapObjectShop(241, 533, layer5);
		addScalableMapObjectShop(220, 543, layer5);
		addScalableMapObjectShop(274, 554, layer5);
		addScalableMapObjectShop(262, 631, layer5);
		addScalableMapObjectShop(193, 689, layer5);
		addScalableMapObjectShop(258, 724, layer5);

		// 幻想森林
		addScalableMapObjectScenery(924, 794, layer2);
		addScalableMapObjectScenery(764, 750, layer2);
		addScalableMapObjectScenery(1057, 735, layer2);
		addScalableMapObjectScenery(995, 659, layer2);
		addScalableMapObjectScenery(912, 677, layer2);
		addScalableMapObjectScenery(1164, 678, layer2);
		addScalableMapObjectScenery(1245, 639, layer2);
		// 洛克王国
		addScalableMapObjectScenery(658, 685, layer2);
		addScalableMapObjectScenery(674, 714, layer2);
		addScalableMapObjectScenery(725, 675, layer2);
		addScalableMapObjectScenery(837, 628, layer2);
		// 完美水世界
		addScalableMapObjectScenery(1187, 596, layer2);
		addScalableMapObjectScenery(1316, 559, layer2);
		addScalableMapObjectScenery(1266, 508, layer2);
		addScalableMapObjectScenery(1170, 481, layer2);
		addScalableMapObjectScenery(1129, 505, layer2);
		addScalableMapObjectScenery(1065, 533, layer2);
		addScalableMapObjectScenery(908, 585, layer2);
		addScalableMapObjectScenery(958, 637, layer2);
		addScalableMapObjectScenery(1129, 569, layer2);

		// 二期美食
		addScalableMapObjectFood(836, 728, layer3);
		addScalableMapObjectFood(1091, 670, layer3);
		addScalableMapObjectFood(1033, 613, layer3);
		addScalableMapObjectFood(713, 666, layer3);
		addScalableMapObjectFood(845, 678, layer3);
		addScalableMapObjectFood(1074, 607, layer3);
		addScalableMapObjectFood(1233, 560, layer3);
		addScalableMapObjectFood(1205, 521, layer3);
		addScalableMapObjectFood(1032, 599, layer3);
		addScalableMapObjectFood(913, 640, layer3);

		// 二期购物
		addScalableMapObjectShop(780, 821, layer5);
		addScalableMapObjectShop(1091, 688, layer5);
		addScalableMapObjectShop(778, 662, layer5);
		addScalableMapObjectShop(840, 664, layer5);
		addScalableMapObjectShop(1093, 642, layer5);
		addScalableMapObjectShop(1193, 519, layer5);
		addScalableMapObjectShop(1016, 619, layer5);
		addScalableMapObjectShop(882, 637, layer5);

		// 二期服务
		// 厕所
		addScalableMapObjectService(687, 844, layer4);
		addScalableMapObjectService(824, 884, layer4);
		addScalableMapObjectService(789, 914, layer4);
		addScalableMapObjectService(792, 658, layer4);
		addScalableMapObjectService(1081, 705, layer4);
		addScalableMapObjectService(979, 595, layer4);
		addScalableMapObjectService(1010, 581, layer4);
		addScalableMapObjectService(1090, 624, layer4);
		addScalableMapObjectService(1296, 606, layer4);
		addScalableMapObjectService(1300, 512, layer4);
		// 医疗
		addScalableMapObjectService2(998, 601, layer4, 64);
		addScalableMapObjectService2(1117, 629, layer4, 64);
		// 电话
		addScalableMapObjectService2(728, 846, layer4, 54);

		addScalableMapObjectBig(layer2);
		addScalableMapObjectBig(layer3);
		addScalableMapObjectBig(layer4);
		addScalableMapObjectBig(layer5);
		// addNotScalableMapObject(900, 350, layer2);
	}

	private void initMapObjectsFood() {
		mapObjectInfoPopup = new TextPopup(this, (RelativeLayout) findViewById(R.id.rootLayout));
		// Layer layer1 = map.getLayerById(LAYER1_ID);
		layer2.setVisible(false);
		layer4.setVisible(false);
		layer5.setVisible(false);
		layer3.setVisible(true);
		// layer3 = map.getLayerById(LAYER3_ID);
		for (int i = 0; i < model.size(); ++i) {
			addNotScalableMapObject(model.getObject(i), layer1);
		}
		// Adding two map objects to the second layer
		// addScalableMapObject(900, 120, layer3);
		// addScalableMapObject(900, 500, layer3);
		// addScalableMapObject(900, 800, layer3);
		// addNotScalableMapObject(900, 350, layer2);
	}

	private void addNotScalableMapObject(int x, int y, Layer layer) {
		// Getting the drawable of the map object
		Drawable drawable = getResources().getDrawable(R.drawable.map_object);
		pinHeight = drawable.getIntrinsicHeight();
		// Creating the map object
		MapObject object1 = new MapObject(Integer.valueOf(nextObjectId), // id,
																			// will
																			// be
																			// passed
																			// to
																			// the
																			// listener
																			// when
																			// user
																			// clicks
																			// on
																			// it
				drawable, new Point(x, y), // coordinates in original map
											// coordinate system.
				// Pivot point of center of the drawable in the drawable's
				// coordinate system.
				PivotFactory.createPivotPoint(drawable, PivotPosition.PIVOT_CENTER), true, // This
																							// object
																							// will
																							// be
																							// passed
																							// to
																							// the
																							// listener
				false); // is not scalable. It will have the same size on each
						// zoom level
		// Adding object to layer
		layer.addMapObject(object1);
		nextObjectId += 1;
	}

	private void addNotScalableMapObject(MapObjectModel objectModel, Layer layer) {
		if (objectModel.getLocation() != null) {
			addNotScalableMapObject(objectModel.getLocation(), layer);
		} else {
			addNotScalableMapObject(objectModel.getX(), objectModel.getY(), layer);
		}
	}

	private void addNotScalableMapObject(Location location, Layer layer) {
		if (location == null)
			return;
		// Getting the drawable of the map object
		Drawable drawable = getResources().getDrawable(R.drawable.map_scenery);
		// Creating the map object
		MapObject object1 = new MapObject(Integer.valueOf(nextObjectId), // id,
																			// will
																			// be
																			// passed
																			// to
																			// the
																			// listener
																			// when
																			// user
																			// clicks
																			// on
																			// it
				drawable, new Point(0, 0), // coordinates in original map
											// coordinate system.
				// Pivot point of center of the drawable in the drawable's
				// coordinate system.
				PivotFactory.createPivotPoint(drawable, PivotPosition.PIVOT_CENTER), true, // This
																							// object
																							// will
																							// be
																							// passed
																							// to
																							// the
																							// listener
				true); // is not scalable. It will have the same size on each
						// zoom level
		layer.addMapObject(object1);
		// Will crash if you try to move before adding to the layer.
		object1.moveTo(location);
		nextObjectId += 1;
	}

	private void addScalableMapObjectSceneryByStatue(MapObject newModel, Layer layer, Integer statue) {
		Drawable drawable = getResources().getDrawable(R.drawable.map_scenery);
		if (statue == 0) {
			drawable = getResources().getDrawable(R.drawable.map_scenery0);
		} else if (statue == 1) {
			drawable = getResources().getDrawable(R.drawable.map_scenery);
		} else if (statue == 2) {
			drawable = getResources().getDrawable(R.drawable.map_scenery2);
		}
		newModel.setDrawable(drawable);
		layer.getMapObject(newModel.getId()).setDrawable(newModel.getDrawable());
	}

	private void addScalableMapObjectSceneryByStatue1(MapObject newModel, Layer layer, Integer statue) {
		Drawable drawable = getResources().getDrawable(R.drawable.map_food);
		if (statue == 0) {
			drawable = getResources().getDrawable(R.drawable.map_food_0);
		} else if (statue == 1) {
			drawable = getResources().getDrawable(R.drawable.map_food);
		} else if (statue == 2) {
			drawable = getResources().getDrawable(R.drawable.map_food_2);
		}
		newModel.setDrawable(drawable);
		layer.getMapObject(newModel.getId()).setDrawable(newModel.getDrawable());
	}

	private void addScalableMapObjectScenery(int x, int y, Layer layer) {
		Drawable drawable = getResources().getDrawable(R.drawable.map_scenery);

		MapObject object1 = new MapObject(Integer.valueOf(nextObjectId), drawable, x, y, true, true);
		WeakReference<MapObject> object2 = new WeakReference<MapObject>(object1);
		layer.addMapObject(object2.get());
		object1 = null;
		nextObjectId += 1;

	}

	private String getMapObjectName(Integer objectId) {
		String name = null;
		if (objectId == 0) {
			name = getResources().getString(R.string.zhlt);
		} else if (objectId == 1) {
			name = getResources().getString(R.string.xxtl);
		} else if (objectId == 2) {
			name = getResources().getString(R.string.lh);
		} else if (objectId == 3) {
			name = getResources().getString(R.string.hdwh);
		} else if (objectId == 4) {
			name = getResources().getString(R.string.lxtx);
		} else if (objectId == 5) {
			name = getResources().getString(R.string.ttzd);
		} else if (objectId == 6) {
			name = getResources().getString(R.string.yzmj2);
		} else if (objectId == 7) {
			name = getResources().getString(R.string.slxk2);
		} else if (objectId == 8) {
			name = getResources().getString(R.string.dhxx2);
		} else if (objectId == 9) {
			name = getResources().getString(R.string.tjhk2);
		} else if (objectId == 10) {
			name = getResources().getString(R.string.tmhx2);
		} else if (objectId == 11) {
			name = getResources().getString(R.string.lszn2);
		} else if (objectId == 12) {
			name = getResources().getString(R.string.yywg2);
		} else if (objectId == 13) {
			name = getResources().getString(R.string.smzd2);
		} else if (objectId == 14) {
			name = getResources().getString(R.string.jlbd2);
		} else if (objectId == 15) {
			name = getResources().getString(R.string.bjgd2);
		} else if (objectId == 16) {
			name = getResources().getString(R.string.yxys2);
		} else if (objectId == 17) {
			name = getResources().getString(R.string.cqm2);
		} else if (objectId == 18) {
			name = getResources().getString(R.string.fxqs2);
		} else if (objectId == 19) {
			name = getResources().getString(R.string.mfjl2);
		} else if (objectId == 20) {
			name = getResources().getString(R.string.bbdp2);
		} else if (objectId == 21) {
			name = getResources().getString(R.string.mmgzdj2);
		} else if (objectId == 22) {
			name = getResources().getString(R.string.kzdxl2);
		} else if (objectId == 23) {
			name = getResources().getString(R.string.glgl2);
		} else if (objectId == 24) {
			name = getResources().getString(R.string.hdjlc2);
		} else if (objectId == 25) {
			name = getResources().getString(R.string.meq2);
		} else if (objectId == 26) {
			name = getResources().getString(R.string.msq2);
		} else if (objectId == 27) {
			name = getResources().getString(R.string.rlw2);
		} else if (objectId == 28) {
			name = getResources().getString(R.string.sxzc2);
		} else if (objectId == 29) {
			name = getResources().getString(R.string.mstt2);
		} else if (objectId == 30) {
			name = getResources().getString(R.string.msm2);
		} else if (objectId == 31) {
			name = getResources().getString(R.string.mhqt2);
		} else if (objectId == 32) {
			name = getResources().getString(R.string.xxzhlx2);
		} else if (objectId == 33) {
			name = getResources().getString(R.string.yhnlzyct);
		} else if (objectId == 34) {
			name = getResources().getString(R.string.mmjd);
		} else if (objectId == 35) {
			name = getResources().getString(R.string.shqz);
		} else if (objectId == 36) {
			name = getResources().getString(R.string.ttw);
		} else if (objectId == 37) {
			name = getResources().getString(R.string.cqmsj);
		} else if (objectId == 38) {
			name = getResources().getString(R.string.zcly);
		} else if (objectId == 39) {
			name = getResources().getString(R.string.ldcf);
		} else if (objectId == 40) {
			name = getResources().getString(R.string.yhkf);
		} else if (objectId == 41) {
			name = getResources().getString(R.string.kkklklgf);
		} else if (objectId == 42) {
			name = getResources().getString(R.string.lmtx);
		} else if (objectId == 43) {
			name = getResources().getString(R.string.mekxdc);
		} else if (objectId == 44) {
			name = getResources().getString(R.string.drszztct);
		} else if (objectId == 45) {
			name = getResources().getString(R.string.ksxw);
		} else if (objectId == 46) {
			name = getResources().getString(R.string.jjs);
		} else if (objectId == 47) {
			name = getResources().getString(R.string.twbx);
		} else if (objectId == 48) {
			name = getResources().getString(R.string.hxsk);
		} else if (objectId == 49) {
			name = getResources().getString(R.string.dnsms);
		} else if (objectId == 50) {
			name = getResources().getString(R.string.emgs);
		} else if (objectId > 51 && objectId < 84) {
			name = getResources().getString(R.string.toliet);
		} else if (objectId == 51) {
			name = getResources().getString(R.string.wohztct);
		} else if (objectId == 84) {
			name = getResources().getString(R.string.xxfc);
		} else if (objectId == 85) {
			name = getResources().getString(R.string.fst);
		} else if (objectId == 86) {
			name = getResources().getString(R.string.hzmb);
		} else if (objectId == 87) {
			name = getResources().getString(R.string.wjzdy);
		} else if (objectId == 88) {
			name = getResources().getString(R.string.mfsk);
		} else if (objectId == 89) {
			name = getResources().getString(R.string.cqmgc);
		} else if (objectId == 90) {
			name = getResources().getString(R.string.cqsh);
		} else if (objectId == 91) {
			name = getResources().getString(R.string.mhlb);
		} else if (objectId == 92) {
			name = getResources().getString(R.string.oy);
		} else if (objectId == 93) {
			name = getResources().getString(R.string.yldbbx);
		} else if (objectId == 94) {
			name = getResources().getString(R.string.mfhzg);
		} else if (objectId == 95) {
			name = getResources().getString(R.string.keppd);
		} else if (objectId == 96) {
			name = getResources().getString(R.string.gzzdy);
		} else if (objectId == 97) {
			name = getResources().getString(R.string.jmztd);
		} else if (objectId == 98) {
			name = getResources().getString(R.string.atm);
		} else if (objectId == 99) {
			name = getResources().getString(R.string.mtjt);
		} else if (objectId == 100) {
			name = getResources().getString(R.string.xcm);
		} else if (objectId == 101) {
			name = getResources().getString(R.string.yxh);
		} else if (objectId == 102) {
			name = getResources().getString(R.string.xyyhtl);
		} else if (objectId == 103) {
			name = getResources().getString(R.string.hmbb);
		} else if (objectId == 104) {
			name = getResources().getString(R.string.tghz);
		} else if (objectId == 105) {
			name = getResources().getString(R.string.hellok);
		} else if (objectId == 106) {
			name = getResources().getString(R.string.dlam);
		} else if (objectId == 107) {
			name = getResources().getString(R.string.xxmz);
		} else if (objectId == 108) {
			name = getResources().getString(R.string.yzqp);
		} else if (objectId == 109) {
			name = getResources().getString(R.string.qqsxj);
		} else if (objectId == 110) {
			name = getResources().getString(R.string.xcmnd);
		} else if (objectId == 111) {
			name = getResources().getString(R.string.yhs);
		} else if (objectId == 112) {
			name = getResources().getString(R.string.fsyjs);
		} else if (objectId == 113) {
			name = getResources().getString(R.string.qcj);
		} else if (objectId == 114) {
			name = getResources().getString(R.string.slbz);
		}
		switch (objectId) {
		case 115:
			name = getResources().getString(R.string.lxfsb);
			break;
		case 116:
			name = getResources().getString(R.string.xyxm);
			break;
		case 117:
			name = getResources().getString(R.string.lymz);
			break;
		case 118:
			name = getResources().getString(R.string.xfyz);
			break;
		case 119:
			name = getResources().getString(R.string.lyft);
			break;
		case 120:
			name = getResources().getString(R.string.xzjl);
			break;
		case 121:
			name = getResources().getString(R.string.dhjl);
			break;
		case 122:
			name = getResources().getString(R.string.jyc);
			break;
		case 123:
			name = getResources().getString(R.string.sqft);
			break;
		case 124:
			name = getResources().getString(R.string.chpp);
			break;
		case 125:
			name = getResources().getString(R.string.lklx);
			break;
		case 126:
			name = getResources().getString(R.string.adlxf);
			break;
		case 127:
			name = getResources().getString(R.string.xwsl);
			break;
		case 128:
			name = getResources().getString(R.string.jlsy);
			break;
		case 129:
			name = getResources().getString(R.string.jfw);
			break;
		case 130:
			name = getResources().getString(R.string.mzlzl);
			break;
		case 131:
			name = getResources().getString(R.string.jsxly);
			break;
		case 132:
			name = getResources().getString(R.string.rlw);
			break;
		case 133:
			name = getResources().getString(R.string.wfd);
			break;
		case 134:
			name = getResources().getString(R.string.yxz);
			break;

		case 135:
			name = getResources().getString(R.string.hnzj);
			break;
		case 136:
			name = getResources().getString(R.string.lyxz);
			break;
		case 137:
			name = getResources().getString(R.string.ssmsgc);
			break;
		case 138:
			name = getResources().getString(R.string.lyct);
			break;

		case 139:
			name = getResources().getString(R.string.slsq);
			break;
		case 140:
			name = getResources().getString(R.string.hsdcct);
			break;
		case 141:
			name = getResources().getString(R.string.ksh);
			break;
		case 142:
			name = getResources().getString(R.string.klyb);
			break;
		case 143:
			name = getResources().getString(R.string.rhkbjz);
			break;

		case 144:
			name = getResources().getString(R.string.rdmsgc);
			break;

		case 145:
			name = getResources().getString(R.string.wmw);
			break;
		case 146:
			name = getResources().getString(R.string.slyz);
			break;
		case 147:
			name = getResources().getString(R.string.bdclw);
			break;

		case 148:
			name = getResources().getString(R.string.lkxz);
			break;
		case 149:
			name = getResources().getString(R.string.yzzby);
			break;
		case 150:
			name = getResources().getString(R.string.szbl);
			break;
		case 151:
			name = getResources().getString(R.string.spsj);
			break;
		case 152:
			name = getResources().getString(R.string.ncbl);
			break;

		default:
			break;
		}
		return name;
	}

	private void addScalableMapObjectFood(int x, int y, Layer layer) {
		Drawable drawable = getResources().getDrawable(R.drawable.food_12x);
		MapObject object1 = new MapObject(Integer.valueOf(nextObjectId), drawable, x, y, true, true);
		layer.addMapObject(object1);
		nextObjectId += 1;
	}

	private void addScalableMapObjectService(int x, int y, Layer layer) {
		Drawable drawable = getResources().getDrawable(R.drawable.tolieticon);
		MapObject object1 = new MapObject(Integer.valueOf(nextObjectId), drawable, x, y, true, true);
		layer.addMapObject(object1);
		nextObjectId += 1;
	}

	private void addScalableMapObjectService2(int x, int y, Layer layer, int i) {
		Drawable drawable = null;
		if (i == 66) {
			drawable = getResources().getDrawable(R.drawable.telicon);
		}
		if (i == 64) {
			drawable = getResources().getDrawable(R.drawable.yiyuan);
		}
		if (i == 63) {
			drawable = getResources().getDrawable(R.drawable.collicon);
		}
		if (i == 56) {
			drawable = getResources().getDrawable(R.drawable.takephotoicon);
		}
		if (i == 54) {
			drawable = getResources().getDrawable(R.drawable.telicon);
		}
		if (i == 70) {
			drawable = getResources().getDrawable(R.drawable.telicon);
		}
		if (i == 73) {
			drawable = getResources().getDrawable(R.drawable.takephotoicon);
		}

		MapObject object1 = new MapObject(Integer.valueOf(nextObjectId), drawable, x, y, true, true);
		layer.addMapObject(object1);
		nextObjectId += 1;
	}

	private void addScalableMapObjectShop(int x, int y, Layer layer) {
		Drawable drawable = getResources().getDrawable(R.drawable.shop_1);
		MapObject object1 = new MapObject(Integer.valueOf(nextObjectId), drawable, x, y, true, true);
		layer.addMapObject(object1);
		nextObjectId += 1;
	}

	private void addScalableMapObjectBig(Layer layer) {
		int x;
		int y;
		Drawable drawable;
		MapObject object1;
		for (int i = 0; i < 11; i++) {
			if (i == 0) {
				drawable = getResources().getDrawable(R.drawable.sds1);
				object1 = new MapObject(Integer.valueOf(nextObjectId), drawable, 510, 120, true, true);
				layer.addMapObject(object1);
				nextObjectId += 1;
			} else if (i == 1) {
				drawable = getResources().getDrawable(R.drawable.xjcs1);
				object1 = new MapObject(Integer.valueOf(nextObjectId), drawable, 180, 170, true, true);
				layer.addMapObject(object1);
				nextObjectId += 1;
			} else if (i == 2) {
				drawable = getResources().getDrawable(R.drawable.cqtx1);
				object1 = new MapObject(Integer.valueOf(nextObjectId), drawable, 645, 268, true, true);
				layer.addMapObject(object1);
				nextObjectId += 1;
			} else if (i == 3) {
				drawable = getResources().getDrawable(R.drawable.msdl1);
				object1 = new MapObject(Integer.valueOf(nextObjectId), drawable, 20, 662, true, true);
				layer.addMapObject(object1);
				nextObjectId += 1;
			} else if (i == 4) {
				drawable = getResources().getDrawable(R.drawable.tbdj1);
				object1 = new MapObject(Integer.valueOf(nextObjectId), drawable, 442, 870, true, true);
				layer.addMapObject(object1);
				nextObjectId += 1;
			} else if (i == 5) {
				drawable = getResources().getDrawable(R.drawable.mezy1);
				object1 = new MapObject(Integer.valueOf(nextObjectId), drawable, 561, 540, true, true);
				layer.addMapObject(object1);
				nextObjectId += 1;
			} else if (i == 6) {
				drawable = getResources().getDrawable(R.drawable.mhgz1);
				object1 = new MapObject(Integer.valueOf(nextObjectId), drawable, 165, 865, true, true);
				layer.addMapObject(object1);
				nextObjectId += 1;
			} else if (i == 7) {
				drawable = getResources().getDrawable(R.drawable.jlh1);
				object1 = new MapObject(Integer.valueOf(nextObjectId), drawable, 270, 536, true, true);
				layer.addMapObject(object1);
				nextObjectId += 1;
			} else if (i == 8) {
				drawable = getResources().getDrawable(R.drawable.lkwg1);
				object1 = new MapObject(Integer.valueOf(nextObjectId), drawable, 660, 510, true, true);
				layer.addMapObject(object1);
				nextObjectId += 1;
			} else if (i == 9) {
				drawable = getResources().getDrawable(R.drawable.hxsl1);
				object1 = new MapObject(Integer.valueOf(nextObjectId), drawable, 1065, 730, true, true);
				layer.addMapObject(object1);
				nextObjectId += 1;
			} else if (i == 10) {
				drawable = getResources().getDrawable(R.drawable.wmssj1);
				object1 = new MapObject(Integer.valueOf(nextObjectId), drawable, 1046, 340, true, true);
				layer.addMapObject(object1);
				nextObjectId += 1;
			}
		}
	}

	private void initMapListeners() {
		// In order to receive MapObject touch events we need to set listener
		map.setOnMapTouchListener(this);
		// In order to receive pre and post zoom events we need to set
		// MapEventsListener
		map.addMapEventsListener(this);
		// In order to receive map scroll events we set OnMapScrollListener
		map.setOnMapScrolledListener(new OnMapScrollListener() {
			public void onScrolledEvent(MapWidget v, MapScrolledEvent event) {
				handleOnMapScroll(v, event);
			}
		});
		map.setOnLocationChangedListener(new OnLocationChangedListener() {
			@Override
			public void onLocationChanged(MapWidget v, Location location) {
				// You can handle location change here.
				// For example you can scroll to new location by using
				// v.scrollMapTo(location)
			}
		});
	}

	private void handleOnMapScroll(MapWidget v, MapScrolledEvent event) {
		// When user scrolls the map we receive scroll events
		// This is useful when need to move some object together with the map
		int dx = event.getDX(); // Number of pixels that user has scrolled
								// horizontally
		int dy = event.getDY(); // Number of pixels that user has scrolled
								// vertically
		if (mapObjectInfoPopup.isVisible()) {
			mapObjectInfoPopup.moveBy(dx, dy);
		}
	}

	@Override
	public void onPostZoomIn() {
		Log.i(TAG, "onPostZoomIn()");
	}

	@Override
	public void onPostZoomOut() {
		Log.i(TAG, "onPostZoomOut()");
	}

	@Override
	public void onPreZoomIn() {
		Log.i(TAG, "onPreZoomIn()");
		if (mapObjectInfoPopup != null) {
			mapObjectInfoPopup.hide();
		}
	}

	@Override
	public void onPreZoomOut() {
		Log.i(TAG, "onPreZoomOut()");
		if (mapObjectInfoPopup != null) {
			mapObjectInfoPopup.hide();
		}
	}

	// * On map touch listener implemetnation *//
	@Override
	public void onTouch(MapWidget v, MapTouchedEvent event) {
		// Get touched object events from the MapTouchEvent
		ArrayList<ObjectTouchEvent> touchedObjs = event.getTouchedObjectEvents();
		if (touchedObjs.size() > 0) {
			int xInMapCoords = event.getMapX();
			int yInMapCoords = event.getMapY();
			int xInScreenCoords = event.getScreenX();
			int yInScreenCoords = event.getScreenY();
			ObjectTouchEvent objectTouchEvent = touchedObjs.get(0);
			// Due to a bug this is not actually the layer id, but index of the
			// layer in layers array.
			// Will be fixed in the next release.
			long layerId = objectTouchEvent.getLayerId();
			Integer objectId = (Integer) objectTouchEvent.getObjectId();
			// User has touched one or more map object
			// We will take the first one to show in the toast message.
			String message = "You touched the object with id: " + objectId + " on layer: " + layerId + " mapX: "
					+ xInMapCoords + " mapY: " + yInMapCoords + " screenX: " + xInScreenCoords + " screenY: "
					+ yInScreenCoords;
			Log.d(TAG, message);
			MapObjectModel objectModel = model.getObjectById(objectId.intValue());
			if (objectModel != null) {
				// This is a case when we want to show popup info exactly above
				// the pin image
				float density = getResources().getDisplayMetrics().density;
				int imgHeight = (int) (pinHeight / density / 2);
				// Calculating position of popup on the screen
				int x = xToScreenCoords(objectModel.getX());
				int y = yToScreenCoords(objectModel.getY()) - imgHeight;
				// Show it
				showLocationsPopup(x, y, objectModel.getCaption());
			} else {
				// This is a case when we want to show popup where the user has
				// touched.
				if (objectId == 0) {
					initDialog();
					txt.setText(getResources().getString(R.string.zhlt));
					img.setImageResource(R.drawable.s1_proj_image);
					dialog.show();
				} else if (objectId == 1) {
					initDialog();
					txt.setText(getResources().getString(R.string.xxtl));
					img.setImageResource(R.drawable.s2_proj_image);
					dialog.show();
				} else if (objectId == 2) {
					initDialog();
					txt.setText(getResources().getString(R.string.lh));
					img.setImageResource(R.drawable.c3_proj_image);
					dialog.show();
				} else if (objectId == 3) {
					initDialog();
					txt.setText(getResources().getString(R.string.hdwh));
					img.setImageResource(R.drawable.c7_proj_image);
					dialog.show();
				} else if (objectId == 4) {
					initDialog();
					txt.setText(getResources().getString(R.string.lxtx));
					img.setImageResource(R.drawable.c1_proj_image);
					dialog.show();
				} else if (objectId == 5) {
					initDialog();
					txt.setText(getResources().getString(R.string.ttzd));
					img.setImageResource(R.drawable.c4_proj_image);
					dialog.show();
				} else if (objectId == 6) {
					initDialog();
					txt.setText(getResources().getString(R.string.yzmj2));
					img.setImageResource(R.drawable.c2_proj_image);
					dialog.show();
				} else if (objectId == 7) {
					initDialog();
					txt.setText(getResources().getString(R.string.slxk2));
					img.setImageResource(R.drawable.x1_proj_image);
					dialog.show();
				} else if (objectId == 8) {
					initDialog();
					txt.setText(getResources().getString(R.string.dhxx2));
					img.setImageResource(R.drawable.x4_proj_image);
					dialog.show();
				} else if (objectId == 9) {
					initDialog();
					txt.setText(getResources().getString(R.string.tjhk2));
					img.setImageResource(R.drawable.x5_proj_image);
					dialog.show();
				} else if (objectId == 10) {
					initDialog();
					txt.setText(getResources().getString(R.string.tmhx2));
					img.setImageResource(R.drawable.x3_proj_image);
					dialog.show();
				} else if (objectId == 11) {
					initDialog();
					txt.setText(getResources().getString(R.string.lszn2));
					img.setImageResource(R.drawable.x2_proj_image);
					dialog.show();
				} else if (objectId == 12) {
					initDialog();
					txt.setText(getResources().getString(R.string.yywg2));
					img.setImageResource(R.drawable.j3_proj_image);
					dialog.show();
				} else if (objectId == 13) {
					initDialog();
					txt.setText(getResources().getString(R.string.smzd2));
					img.setImageResource(R.drawable.j2_proj_image);
					dialog.show();
				} else if (objectId == 14) {
					initDialog();
					txt.setText(getResources().getString(R.string.jlbd2));
					img.setImageResource(R.drawable.j1_proj_image);
					dialog.show();
				} else if (objectId == 15) {
					initDialog();
					txt.setText(getResources().getString(R.string.bjgd2));
					img.setImageResource(R.drawable.c5_proj_image);
					dialog.show();
				} else if (objectId == 16) {
					initDialog();
					txt.setText(getResources().getString(R.string.yxys2));
					img.setImageResource(R.drawable.c8_proj_image);
					dialog.show();
				} else if (objectId == 17) {
					initDialog();
					txt.setText(getResources().getString(R.string.cqm2));
					img.setImageResource(R.drawable.xx1_proj_image);
					dialog.show();
				} else if (objectId == 18) {
					initDialog();
					txt.setText(getResources().getString(R.string.fxqs2));
					img.setImageResource(R.drawable.m2_proj_image);
					dialog.show();
				} else if (objectId == 19) {
					initDialog();
					txt.setText(getResources().getString(R.string.mfjl2));
					img.setImageResource(R.drawable.m8_proj_image);
					dialog.show();
				} else if (objectId == 20) {
					initDialog();
					txt.setText(getResources().getString(R.string.bbdp2));
					img.setImageResource(R.drawable.m1_proj_image);
					dialog.show();
				} else if (objectId == 21) {
					initDialog();
					txt.setText(getResources().getString(R.string.mmgzdj2));
					img.setImageResource(R.drawable.m6_proj_image);
					dialog.show();
				} else if (objectId == 22) {
					initDialog();
					txt.setText(getResources().getString(R.string.kzdxl2));
					img.setImageResource(R.drawable.m5_proj_image);
					dialog.show();
				} else if (objectId == 23) {
					initDialog();
					txt.setText(getResources().getString(R.string.glgl2));
					img.setImageResource(R.drawable.m3_proj_image);
					dialog.show();
				} else if (objectId == 24) {
					initDialog();
					txt.setText(getResources().getString(R.string.hdjlc2));
					img.setImageResource(R.drawable.m4_proj_image);
					dialog.show();
				} else if (objectId == 25) {
					initDialog();
					txt.setText(getResources().getString(R.string.meq2));
					img.setImageResource(R.drawable.m7_proj_image);
					dialog.show();
				} else if (objectId == 26) {
					initDialog();
					txt.setText(getResources().getString(R.string.msq2));
					img.setImageResource(R.drawable.l6_proj_image);
					dialog.show();
				} else if (objectId == 27) {
					initDialog();
					txt.setText(getResources().getString(R.string.rlw2));
					img.setImageResource(R.drawable.l2_proj_image);
					dialog.show();
				} else if (objectId == 28) {
					initDialog();
					txt.setText(getResources().getString(R.string.sxzc2));
					img.setImageResource(R.drawable.l1_proj_image);
					dialog.show();
				} else if (objectId == 29) {
					initDialog();
					txt.setText(getResources().getString(R.string.mstt2));
					img.setImageResource(R.drawable.l3_proj_image);
					dialog.show();
				} else if (objectId == 30) {
					initDialog();
					txt.setText(getResources().getString(R.string.msm2));
					img.setImageResource(R.drawable.l5_proj_image);
					dialog.show();
				} else if (objectId == 31) {
					initDialog();
					txt.setText(getResources().getString(R.string.mhqt2));
					img.setImageResource(R.drawable.xx2_proj_image);
					dialog.show();
				} else if (objectId == 32) {
					initDialog();
					txt.setText(getResources().getString(R.string.xxzhlx2));
					img.setImageResource(R.drawable.l4_proj_image);
					dialog.show();
				} else if (objectId == 33) {
					initDialog();
					txt.setText(getResources().getString(R.string.yhnlzyct));
					img.setImageResource(R.drawable.food1_proj_image);
					dialog.show();
				} else if (objectId == 34) {
					initDialog();
					txt.setText(getResources().getString(R.string.mmjd));
					img.setImageResource(R.drawable.food2_proj_image);
					dialog.show();
				} else if (objectId == 35) {
					initDialog();
					txt.setText(getResources().getString(R.string.shqz));
					img.setImageResource(R.drawable.food3_proj_image);
					dialog.show();
				} else if (objectId == 36) {
					initDialog();
					txt.setText(getResources().getString(R.string.ttw));
					img.setImageResource(R.drawable.food4_proj_image);
					dialog.show();
				} else if (objectId == 37) {
					initDialog();
					txt.setText(getResources().getString(R.string.cqmsj));
					img.setImageResource(R.drawable.food5_proj_image);
					dialog.show();
				} else if (objectId == 38) {
					initDialog();
					txt.setText(getResources().getString(R.string.zcly));
					img.setImageResource(R.drawable.food6_proj_image);
					dialog.show();
				} else if (objectId == 39) {
					initDialog();
					txt.setText(getResources().getString(R.string.ldcf));
					img.setImageResource(R.drawable.food7_proj_image);
					dialog.show();
				} else if (objectId == 40) {
					initDialog();
					txt.setText(getResources().getString(R.string.yhkf));
					img.setImageResource(R.drawable.food8_proj_image);
					dialog.show();
				} else if (objectId == 41) {
					initDialog();
					txt.setText(getResources().getString(R.string.kkklklgf));
					img.setImageResource(R.drawable.food9_proj_image);
					dialog.show();
				} else if (objectId == 42) {
					initDialog();
					txt.setText(getResources().getString(R.string.lmtx));
					img.setImageResource(R.drawable.food10_proj_image);
					dialog.show();
				} else if (objectId == 43) {
					initDialog();
					txt.setText(getResources().getString(R.string.mekxdc));
					img.setImageResource(R.drawable.food11_proj_image);
					dialog.show();
				} else if (objectId == 44) {
					initDialog();
					txt.setText(getResources().getString(R.string.drszztct));
					img.setImageResource(R.drawable.food12_proj_image);
					dialog.show();
				} else if (objectId == 45) {
					initDialog();
					txt.setText(getResources().getString(R.string.ksxw));
					img.setImageResource(R.drawable.food13_proj_image);
					dialog.show();
				} else if (objectId == 46) {
					initDialog();
					txt.setText(getResources().getString(R.string.jjs));
					img.setImageResource(R.drawable.food14_proj_image);
					dialog.show();
				} else if (objectId == 47) {
					initDialog();
					txt.setText(getResources().getString(R.string.twbx));
					img.setImageResource(R.drawable.food15_proj_image);
					dialog.show();
				} else if (objectId == 48) {
					initDialog();
					txt.setText(getResources().getString(R.string.hxsk));
					img.setImageResource(R.drawable.food16_proj_image);
					dialog.show();
				} else if (objectId == 49) {
					initDialog();
					txt.setText(getResources().getString(R.string.dnsms));
					img.setImageResource(R.drawable.food17_proj_image);
					dialog.show();
				} else if (objectId == 50) {
					initDialog();
					txt.setText(getResources().getString(R.string.emgs));
					img.setImageResource(R.drawable.food18_proj_image);
					dialog.show();
				} else if (objectId == 51) {
					initDialog();
					txt.setText(getResources().getString(R.string.wohztct));
					img.setImageResource(R.drawable.food19_proj_image);
					dialog.show();
				} else if (objectId > 51 && objectId < 84) {
					// if(objectId==66||objectId==64||objectId==63||objectId==56||objectId==54||objectId==70||objectId==73)
					// {
					// }
					//
					// else {
					// dialog=new MarketDialog(this,R.style.MarketDialog);
					// LayoutInflater factory=LayoutInflater.from(this);
					// final View
					// dialogView=factory.inflate(R.layout.poptitle,null);
					// dialog.setContentView(dialogView);
					//
					// Window dialogWindow=dialog.getWindow();
					// // dialogWindow.setLayout(120, 160);
					// WindowManager.LayoutParams
					// lp=dialogWindow.getAttributes();
					// lp.width=300;
					// lp.height=200;
					// lp.alpha=1.0f;
					// dialogWindow.setAttributes(lp);
					// txt=(TextView) dialogView.findViewById(R.id.dialogtitle);
					//
					// txt.setGravity(Gravity.CENTER);
					// txt.setText(getResources().getString(R.string.toliet));
					// dialog.show();
					// }
					//
					//
				} else if (objectId == 84) {
					initDialog();
					txt.setText(getResources().getString(R.string.xxfc));
					img.setImageResource(R.drawable.shop1_proj_image);
					dialog.show();
				} else if (objectId == 85) {
					initDialog();
					txt.setText(getResources().getString(R.string.fst));
					img.setImageResource(R.drawable.shop2_proj_image);
					dialog.show();
				} else if (objectId == 86) {
					initDialog();
					txt.setText(getResources().getString(R.string.hzmb));
					img.setImageResource(R.drawable.shop3_proj_image);
					dialog.show();
				} else if (objectId == 87) {
					initDialog();
					txt.setText(getResources().getString(R.string.wjzdy));
					img.setImageResource(R.drawable.shop4_proj_image);
					dialog.show();
				} else if (objectId == 88) {
					initDialog();
					txt.setText(getResources().getString(R.string.mfsk));
					img.setImageResource(R.drawable.shop5_proj_image);
					dialog.show();
				} else if (objectId == 89) {
					initDialog();
					txt.setText(getResources().getString(R.string.cqmgc));
					img.setImageResource(R.drawable.shop6_proj_image);
					dialog.show();
				} else if (objectId == 90) {
					initDialog();
					txt.setText(getResources().getString(R.string.cqsh));
					img.setImageResource(R.drawable.shop7_proj_image);
					dialog.show();
				} else if (objectId == 91) {
					initDialog();
					txt.setText(getResources().getString(R.string.mhlb));
					img.setImageResource(R.drawable.shop8_proj_image);
					dialog.show();
				} else if (objectId == 92) {
					initDialog();
					txt.setText(getResources().getString(R.string.oy));
					img.setImageResource(R.drawable.shop9_proj_image);
					dialog.show();
				} else if (objectId == 93) {
					initDialog();
					txt.setText(getResources().getString(R.string.yldbbx));
					img.setImageResource(R.drawable.shop10_proj_image);
					dialog.show();
				} else if (objectId == 94) {
					initDialog();
					txt.setText(getResources().getString(R.string.mfhzg));
					img.setImageResource(R.drawable.shop11_proj_image);
					dialog.show();
				} else if (objectId == 95) {
					initDialog();
					txt.setText(getResources().getString(R.string.keppd));
					img.setImageResource(R.drawable.shop12_proj_image);
					dialog.show();
				} else if (objectId == 96) {
					initDialog();
					txt.setText(getResources().getString(R.string.gzzdy));
					img.setImageResource(R.drawable.shop13_proj_image);
					dialog.show();
				} else if (objectId == 97) {
					initDialog();
					txt.setText(getResources().getString(R.string.jmztd));
					img.setImageResource(R.drawable.shop14_proj_image);
					dialog.show();
				} else if (objectId == 98) {
					initDialog();
					txt.setText(getResources().getString(R.string.atm));
					img.setImageResource(R.drawable.shop15_proj_image);
					dialog.show();
				} else if (objectId == 99) {
					initDialog();
					txt.setText(getResources().getString(R.string.mtjt));
					img.setImageResource(R.drawable.shop17_proj_image);
					dialog.show();
				} else if (objectId == 100) {
					initDialog();
					txt.setText(getResources().getString(R.string.xcm));
					img.setImageResource(R.drawable.shop18_proj_image);
					dialog.show();
				} else if (objectId == 101) {
					initDialog();
					txt.setText(getResources().getString(R.string.yxh));
					img.setImageResource(R.drawable.shop19_proj_image);
					dialog.show();
				} else if (objectId == 102) {
					initDialog();
					txt.setText(getResources().getString(R.string.xyyhtl));
					img.setImageResource(R.drawable.shop20_proj_image);
					dialog.show();
				} else if (objectId == 103) {
					initDialog();
					txt.setText(getResources().getString(R.string.hmbb));
					img.setImageResource(R.drawable.shop20_proj_image);
					dialog.show();
				} else if (objectId == 104) {
					initDialog();
					txt.setText(getResources().getString(R.string.tghz));
					img.setImageResource(R.drawable.shop21_proj_image);
					dialog.show();
				} else if (objectId == 105) {
					initDialog();
					txt.setText(getResources().getString(R.string.hellok));
					img.setImageResource(R.drawable.shop22_proj_image);
					dialog.show();
				} else if (objectId == 106) {
					initDialog();
					txt.setText(getResources().getString(R.string.dlam));
					img.setImageResource(R.drawable.shop23_proj_image);
					dialog.show();
				} else if (objectId == 107) {
					initDialog();
					txt.setText(getResources().getString(R.string.xxmz));
					img.setImageResource(R.drawable.shop24_proj_image);
					dialog.show();
				} else if (objectId == 108) {
					initDialog();
					txt.setText(getResources().getString(R.string.yzqp));
					img.setImageResource(R.drawable.shop25_proj_image);
					dialog.show();
				} else if (objectId == 109) {
					initDialog();
					txt.setText(getResources().getString(R.string.qqsxj));
					img.setImageResource(R.drawable.shop26_proj_image);
					dialog.show();
				} else if (objectId == 110) {
					initDialog();
					txt.setText(getResources().getString(R.string.xcmnd));
					img.setImageResource(R.drawable.shop27_proj_image);
					dialog.show();
				} else if (objectId == 111) {
					initDialog();
					txt.setText(getResources().getString(R.string.yhs));
					img.setImageResource(R.drawable.shop28_proj_image);
					dialog.show();
				} else if (objectId == 112) {
					initDialog();
					txt.setText(getResources().getString(R.string.fsyjs));
					img.setImageResource(R.drawable.shop29_proj_image);
					dialog.show();
				} else if (objectId == 113) {
					initDialog();
					txt.setText(getResources().getString(R.string.qcj));
					img.setImageResource(R.drawable.shop30_proj_image);
					dialog.show();
				} else if (objectId == 114) {
					initDialog();
					txt.setText(getResources().getString(R.string.slbz));
					img.setImageResource(R.drawable.shop31_proj_image);
					dialog.show();
				}

				switch (objectId) {
				case 115:
					initDialog();
					txt.setText(getResources().getString(R.string.lxfsb));
					img.setImageResource(R.drawable.hxsl_lxfsb_01);
					dialog.show();
					break;
				case 116:
					initDialog();
					txt.setText(getResources().getString(R.string.xyxm));
					img.setImageResource(R.drawable.hxsl_xyxm_02);
					dialog.show();
					break;
				case 117:
					initDialog();
					txt.setText(getResources().getString(R.string.lymz));
					img.setImageResource(R.drawable.hxsl_lymz_03);
					dialog.show();
					break;
				case 118:
					initDialog();
					txt.setText(getResources().getString(R.string.xfyz));
					img.setImageResource(R.drawable.hxsl_xfyz_04);
					dialog.show();
					break;
				case 119:
					initDialog();
					txt.setText(getResources().getString(R.string.lyft));
					img.setImageResource(R.drawable.hxsl_lyft_05);
					dialog.show();
					break;
				case 120:
					initDialog();
					txt.setText(getResources().getString(R.string.xzjl));
					img.setImageResource(R.drawable.hxsl_xzjl_06);
					dialog.show();
					break;
				case 121:
					initDialog();
					txt.setText(getResources().getString(R.string.dhjl));
					img.setImageResource(R.drawable.hxsl_dhjl_07);
					dialog.show();
					break;
				case 122:
					initDialog();
					txt.setText(getResources().getString(R.string.jyc));
					img.setImageResource(R.drawable.lkwg_jyc_01);
					dialog.show();
					break;
				case 123:
					initDialog();
					txt.setText(getResources().getString(R.string.sqft));
					img.setImageResource(R.drawable.lkwg_sqft_02);
					dialog.show();
					break;
				case 124:
					initDialog();
					txt.setText(getResources().getString(R.string.chpp));
					img.setImageResource(R.drawable.lkwg_chpp_03);
					dialog.show();
					break;
				case 125:
					initDialog();
					txt.setText(getResources().getString(R.string.lklx));
					img.setImageResource(R.drawable.lkwg_lklx_04);
					dialog.show();
					break;
				case 126:
					initDialog();
					txt.setText(getResources().getString(R.string.adlxf));
					img.setImageResource(R.drawable.wmssj_adlxf_01);
					dialog.show();
					break;
				case 127:
					initDialog();
					txt.setText(getResources().getString(R.string.xwsl));
					img.setImageResource(R.drawable.wmssj_xwsl_02);
					dialog.show();
					break;
				case 128:
					initDialog();
					txt.setText(getResources().getString(R.string.jlsy));
					img.setImageResource(R.drawable.wmssj_jlsy_03);
					dialog.show();
					break;
				case 129:
					initDialog();
					txt.setText(getResources().getString(R.string.jfw));
					img.setImageResource(R.drawable.wmssj_jfw_04);
					dialog.show();
					break;
				case 130:
					initDialog();
					txt.setText(getResources().getString(R.string.mzlzl));
					img.setImageResource(R.drawable.wmssj_mzlzl_05);
					dialog.show();
					break;
				case 131:
					initDialog();
					txt.setText(getResources().getString(R.string.jsxly));
					img.setImageResource(R.drawable.wmssj_jsxlg_06);
					dialog.show();
					break;
				case 132:
					initDialog();
					txt.setText(getResources().getString(R.string.rlw));
					img.setImageResource(R.drawable.wmssj_rlw_07);
					dialog.show();
					break;
				case 133:
					initDialog();
					txt.setText(getResources().getString(R.string.wfd));
					img.setImageResource(R.drawable.wmssj_wfd_08);
					dialog.show();
					break;
				case 134:
					initDialog();
					txt.setText(getResources().getString(R.string.yxz));
					img.setImageResource(R.drawable.wmssj_yxz_09);
					dialog.show();
					break;

				case 135:
					initDialog();
					txt.setText(getResources().getString(R.string.hnzj));
					img.setImageResource(R.drawable.hnzj);
					dialog.show();
					break;
				case 136:
					initDialog();
					txt.setText(getResources().getString(R.string.lyxz));
					img.setImageResource(R.drawable.lyxz);
					dialog.show();
					break;
				case 137:
					initDialog();
					txt.setText(getResources().getString(R.string.ssmsgc));
					img.setImageResource(R.drawable.ssmsgc);
					dialog.show();
					break;
				case 138:
					initDialog();
					txt.setText(getResources().getString(R.string.lyct));
					img.setImageResource(R.drawable.luct);
					dialog.show();
					break;

				case 139:
					initDialog();
					txt.setText(getResources().getString(R.string.slsq));
					img.setImageResource(R.drawable.slsq);
					dialog.show();
					break;
				case 140:
					initDialog();
					txt.setText(getResources().getString(R.string.hsdcct));
					img.setImageResource(R.drawable.hsdcct);
					dialog.show();
					break;
				case 141:
					initDialog();
					txt.setText(getResources().getString(R.string.ksh));
					img.setImageResource(R.drawable.ksh);
					dialog.show();
					break;
				case 142:
					initDialog();
					txt.setText(getResources().getString(R.string.klyb));
					img.setImageResource(R.drawable.klyb);
					dialog.show();
					break;
				case 143:
					initDialog();
					txt.setText(getResources().getString(R.string.rhkbjz));
					img.setImageResource(R.drawable.lhkbjz);
					dialog.show();
					break;

				case 144:
					initDialog();
					txt.setText(getResources().getString(R.string.rdmsgc));
					img.setImageResource(R.drawable.rdmsgc);
					dialog.show();
					break;

				case 145:
					initDialog();
					txt.setText(getResources().getString(R.string.wmw));
					img.setImageResource(R.drawable.wmw);
					dialog.show();
					break;
				case 146:
					initDialog();
					txt.setText(getResources().getString(R.string.slyz));
					img.setImageResource(R.drawable.slyz);
					dialog.show();
					break;
				case 147:
					initDialog();
					txt.setText(getResources().getString(R.string.bdclw));
					img.setImageResource(R.drawable.bdclw);
					dialog.show();
					break;

				case 148:
					initDialog();
					txt.setText(getResources().getString(R.string.lkxz));
					img.setImageResource(R.drawable.lkxz);
					dialog.show();
					break;
				case 149:
					initDialog();
					txt.setText(getResources().getString(R.string.yzzby));
					img.setImageResource(R.drawable.yzzby);
					dialog.show();
					break;
				case 150:
					initDialog();
					txt.setText(getResources().getString(R.string.szbl));
					img.setImageResource(R.drawable.szbl);
					dialog.show();
					break;
				case 151:
					initDialog();
					txt.setText(getResources().getString(R.string.spsj));
					img.setImageResource(R.drawable.spsj);
					dialog.show();
					break;
				case 152:
					initDialog();
					txt.setText(getResources().getString(R.string.ncbl));
					img.setImageResource(R.drawable.ncbl);
					dialog.show();
					break;

				default:
					break;
				}

			}
			// Hint: If user touched more than one object you can show the
			// dialog in which ask
			// the user to select concrete object
		} else {
			if (mapObjectInfoPopup != null) {
				mapObjectInfoPopup.hide();
			}
		}
	}

	private void initDialog() {
		dialog = new MarketDialog(this, R.style.MarketDialog);
		LayoutInflater factory = LayoutInflater.from(this);
		final View dialogView = factory.inflate(R.layout.scenery_dialog, null);
		dialog.setContentView(dialogView);
		Window dialogWindow = dialog.getWindow();
		// dialogWindow.setLayout(120, 160);
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		lp.width = 600;
		lp.height = 800;
		lp.alpha = 1.0f;
		dialogWindow.setAttributes(lp);
		txt = (TextView) dialogView.findViewById(R.id.dialogid);
		img = (ImageView) dialogView.findViewById(R.id.dialogimg);
	}

	private void showLocationsPopup(int x, int y, String text) {
		RelativeLayout mapLayout = (RelativeLayout) findViewById(R.id.rootLayout);
		if (mapObjectInfoPopup != null) {
			mapObjectInfoPopup.hide();
		}
		((TextPopup) mapObjectInfoPopup)
				.setIcon((BitmapDrawable) getResources().getDrawable(R.drawable.map_popup_arrow));
		((TextPopup) mapObjectInfoPopup).setText(text);
		mapObjectInfoPopup.setOnClickListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (mapObjectInfoPopup != null) {
						mapObjectInfoPopup.hide();
					}
				}
				return false;
			}
		});
		((TextPopup) mapObjectInfoPopup).show(mapLayout, x, y);
	}

	/***
	 * Transforms coordinate in map coordinate system to screen coordinate
	 * system
	 * 
	 * @param mapCoord
	 *            - X in map coordinate in pixels.
	 * @return X coordinate in screen coordinates. You can use this value to
	 *         display any object on the screen.
	 */
	private int xToScreenCoords(int mapCoord) {
		return (int) (mapCoord * map.getScale() - map.getScrollX());
	}

	private int yToScreenCoords(int mapCoord) {
		return (int) (mapCoord * map.getScale() - map.getScrollY());
	}

}
