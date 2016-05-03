package com.android.user;

import com.android.util.MarketDialog;
import com.android.view.BottomBar;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.MyLocationOverlay.LocationMode;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPlanNode;
import com.baidu.mapapi.search.MKPoiInfo;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionInfo;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class BmActivity extends Activity {
	private MapView mMapView = null;
	private MarketDialog dialog;
	private Button image1;
	private Button image2;
	private Button image3;
	private Button image4;
	private Button image5;
	private MKSearch mSearch = null;
	private MapApplication app;
	private ArrayAdapter<String> sugAdapter = null;
	private double lat;
	private double lng;
	private MapController mMapController;
	private LocationClient mLocClient;
	private LocationData locData = null;
	boolean isRequest = false;// 是否手动触发请求定位
	boolean isFirstLoc = true;// 是否首次定位
	private MKPlanNode start;
	private MKPlanNode end;
	private locationOverlay myLocationOverlay = null;
	public MyLocationListenner myListener = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		app = (MapApplication) this.getApplication();
		if (app.mBMapMan == null) {
			app.mBMapMan = new BMapManager(this);
			app.mBMapMan.init(MapApplication.strKey, null);
		}
		initMap();
		initLoc();

	}

	private void initLoc() {
		// TODO Auto-generated method stub
		ImageButton loc = (ImageButton) findViewById(R.id.navilocation);
		dialog = new MarketDialog(this, R.style.MarketDialog);

		// Window window = dialog.getWindow();
		LayoutInflater factory = LayoutInflater.from(this);
		final View dialogView = factory.inflate(R.layout.market_dialog, null);
		dialog.setContentView(dialogView);
		image1 = (Button) dialogView.findViewById(R.id.image1);
		image2 = (Button) dialogView.findViewById(R.id.image2);
		image3 = (Button) dialogView.findViewById(R.id.image3);
		image4 = (Button) dialogView.findViewById(R.id.image4);
		image5 = (Button) dialogView.findViewById(R.id.image5);
		image1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				mSearch.poiSearchNearBy("公交", new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6)), 3000);
			}
		});
		image2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				mSearch.poiSearchNearBy("餐厅", new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6)), 3000);
			}
		});
		image3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				mSearch.poiSearchNearBy("景点", new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6)), 3000);
			}
		});
		image4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				mSearch.poiSearchNearBy("酒店", new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6)), 3000);
			}
		});
		image5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				mSearch.poiSearchNearBy("银行", new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6)), 3000);
			}
		});

		loc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.show();
			}
		});

	}

	private void myLoc() {
		MyLocationOverlay myloc = new MyLocationOverlay(mMapView);
		myloc.getMyLocation(); //
		myloc.enableCompass(); //
		mMapView.getOverlays().add(myloc);
	}

	private void initMap() {
		// TODO Auto-generated method stub
		/*
		 * mBMapMan = new BMapManager(getApplication());
		 * mBMapMan.init("19e0f7732bf80cef1ba1ad6b4e75e10c", null);
		 */
		setContentView(R.layout.bm_activity);
		BottomBar bottomBar = new BottomBar(this);
		mMapView = (MapView) findViewById(R.id.bmapsView);
		mMapView.getController().enableClick(true);

		mMapView.setBuiltInZoomControls(true);
		mMapController = mMapView.getController();
		mMapController.setZoom(16);
		/*
		 * GeoPoint point = new GeoPoint((int) (31.485 * 1E6), (int) (119.584 *
		 * 1E6)); mMapController.setCenter(point); mMapController.setZoom(12);
		 */

		mLocClient = new LocationClient(this);
		locData = new LocationData();
		myListener = new MyLocationListenner();
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(10000);
		mLocClient.setLocOption(option);
		mLocClient.start();

		myLocationOverlay = new locationOverlay(mMapView);
		// 设置定位数据
		myLocationOverlay.setData(locData);
		// 添加定位图层
		mMapView.getOverlays().add(myLocationOverlay);
		myLocationOverlay.enableCompass();
		// 修改定位数据后刷新图层生效
		mMapView.refresh();

		sugAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line);
		// keyWorldsView.setAdapter(sugAdapter);

		mSearch = new MKSearch();
		mSearch.init(app.mBMapMan, new MKSearchListener() {
			// 在此处理详情页结果
			@Override
			public void onGetPoiDetailSearchResult(int type, int error) {
				if (error != 0) {
					Toast.makeText(BmActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(BmActivity.this, "成功，查看详情页面", Toast.LENGTH_SHORT).show();
				}
			}

			/**
			 * 在此处理poi搜索结果
			 */
			public void onGetPoiResult(MKPoiResult res, int type, int error) {
				// 错误号可参考MKEvent中的定义
				if (error != 0 || res == null) {
					Toast.makeText(BmActivity.this, "抱歉，未找到结果", Toast.LENGTH_LONG).show();
					return;
				}
				// 将地图移动到第一个POI中心点
				if (res.getCurrentNumPois() > 0) {
					// 将poi结果显示到地图上
					MyPoiOverlay poiOverlay = new MyPoiOverlay(BmActivity.this, mMapView, mSearch);
					poiOverlay.setData(res.getAllPoi());
					mMapView.getOverlays().clear();
					mMapView.getOverlays().add(poiOverlay);
					mMapView.refresh();
					// 当ePoiType为2（公交线路）或4（地铁线路）时， poi坐标为空
					for (MKPoiInfo info : res.getAllPoi()) {
						if (info.pt != null) {
							mMapView.getController().animateTo(info.pt);
							break;
						}
					}
				} else if (res.getCityListNum() > 0) {
					// 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
					String strInfo = "在";
					for (int i = 0; i < res.getCityListNum(); i++) {
						strInfo += res.getCityListInfo(i).city;
						strInfo += ",";
					}
					strInfo += "找到结果";
					Toast.makeText(BmActivity.this, strInfo, Toast.LENGTH_LONG).show();
				}
			}

			public void onGetDrivingRouteResult(MKDrivingRouteResult res, int error) {
			}

			public void onGetTransitRouteResult(MKTransitRouteResult res, int error) {
			}

			public void onGetWalkingRouteResult(MKWalkingRouteResult res, int error) {
			}

			public void onGetAddrResult(MKAddrInfo res, int error) {
			}

			public void onGetBusDetailResult(MKBusLineResult result, int iError) {
			}

			/**
			 * 更新建议列表
			 */
			@Override
			public void onGetSuggestionResult(MKSuggestionResult res, int arg1) {
				if (res == null || res.getAllSuggestions() == null) {
					return;
				}
				sugAdapter.clear();
				for (MKSuggestionInfo info : res.getAllSuggestions()) {
					if (info.key != null)
						sugAdapter.add(info.key);
				}
				sugAdapter.notifyDataSetChanged();

			}

			@Override
			public void onGetShareUrlResult(MKShareUrlResult result, int type, int error) {
				// TODO Auto-generated method stub

			}
		});
	}

	public class locationOverlay extends MyLocationOverlay {

		public locationOverlay(MapView mapView) {
			super(mapView);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected boolean dispatchTap() {
			// TODO Auto-generated method stub
			return true;
		}

	}

	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			lat = location.getLatitude();
			lng = location.getLongitude();

			locData.latitude = location.getLatitude();
			locData.longitude = location.getLongitude();
			// 如果不显示定位精度圈，将accuracy赋值为0即可
			locData.accuracy = location.getRadius();
			// 此处可以设置 locData的方向信息, 如果定位 SDK 未返回方向信息，用户可以自己实现罗盘功能添加方向信息。
			locData.direction = location.getDerect();
			// 更新定位数据
			GeoPoint point = new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6));
			mMapController.setCenter(point);
			mMapView.refresh();
			// 是手动触发请求或首次定位时，移动到定位点
			if (isRequest || isFirstLoc) {
				// 移动地图到定位点
				Log.d("LocationOverlay", "receive location, animate to it");
				mMapController.animateTo(
						new GeoPoint((int) (location.getLatitude() * 1e6), (int) (location.getLongitude() * 1e6)));
				isRequest = false;
				myLocationOverlay.setLocationMode(LocationMode.FOLLOWING);
				// mLocClient.requestLocation();
				modifyLocationOverlayIcon(null);
			}
			// 首次定位完成
			isFirstLoc = false;

		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
	}

	public void modifyLocationOverlayIcon(Drawable marker) {
		// 当传入marker为null时，使用默认图标绘制
		myLocationOverlay.setMarker(marker);
		// 修改图层，需要刷新MapView生效
		mMapView.refresh();
	}

	@Override
	protected void onDestroy() {
		if (mMapView != null) {

			mMapView.destroy();
		}
		if (app.mBMapMan != null) {
			mSearch.destory();
			app.mBMapMan.destroy();
			app.mBMapMan = null;
		}
		if (mLocClient != null) {

			mLocClient.stop();
		}
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		if (mMapView != null) {
			mMapView.onPause();
		}
		if (app.mBMapMan != null) {
			app.mBMapMan.stop();
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		if (app.mBMapMan != null) {
			app.mBMapMan.start();
		}
		super.onResume();
	}

}
