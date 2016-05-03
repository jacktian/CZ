package com.android.user;

import com.android.common.IntentAction;
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
import com.baidu.mapapi.map.RouteOverlay;
import com.baidu.mapapi.map.TransitOverlay;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPlanNode;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class JtlxActivity extends Activity implements LocationListener {
	private MapView mMapView = null;
	private double lat;
	private double lng;
	private MKSearch mMKSearch = null;
	private MKPlanNode start;
	private MKPlanNode end;
	private MapController mMapController;
	private locationOverlay myLocationOverlay = null;
	private ImageButton topword, topimage;
	private LocationClient mLocClient;
	private LocationData locData = null;
	boolean isRequest = false;// 是否手动触发请求定位
	boolean isFirstLoc = true;// 是否首次定位
	MapApplication app;
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
		topword = (ImageButton) findViewById(R.id.topword);
		topword.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(IntentAction.YYGL_ACTIVITY);
				startActivity(intent);
			}
		});
		topimage = (ImageButton) findViewById(R.id.topimage);
		topimage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

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
			start = new MKPlanNode();
			start.pt = new GeoPoint((int) (location.getLatitude() * 1E6), (int) (location.getLongitude() * 1E6));
			Log.e("20131207", "start lat = " + location.getLatitude());
			Log.e("20131207", "start lng = " + location.getLongitude());
			end = new MKPlanNode();
			end.pt = new GeoPoint((int) (31.4999 * 1E6), (int) (120.0551 * 1E6));
			mMKSearch.drivingSearch(null, start, null, end);
			mLocClient.stop();
		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
	}

	private void initLoc() {
		// TODO Auto-generated method stub
		ImageButton loc = (ImageButton) findViewById(R.id.loc);
		ImageButton bus = (ImageButton) findViewById(R.id.bus);
		ImageButton car = (ImageButton) findViewById(R.id.car);
		ImageButton foot = (ImageButton) findViewById(R.id.foot);

		/*
		 * final MKPlanNode stNode = new MKPlanNode(); // stNode.name =
		 * editSt.getText().toString(); 113.729845,34.767642 GeoPoint point1 =
		 * new GeoPoint((int) (30.767642 * 1e6), (int) (120.729845 * 1e6));
		 * stNode.pt = point1; // 113.688235,34.76948 final MKPlanNode enNode =
		 * new MKPlanNode(); GeoPoint point2 = new GeoPoint((int) (31.243319 *
		 * 1E6), (int) (121.509075 * 1E6)); enNode.pt = point2;
		 */
		loc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				requestLocClick();
				// mMKSearch.drivingSearch(null, start, null, end);
			}
		});

		bus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// mMKSearch.drivingSearch(null, start, null, end);
				mMKSearch.transitSearch("常州", start, end);
			}
		});
		car.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.e("20131208", "startstartstart =" + start);
				Log.e("20131208", "endendendendend =" + end);
				mMKSearch.drivingSearch(null, start, null, end);
			}
		});
		foot.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.e("20131208", "startstartstart =" + start);
				Log.e("20131208", "endendendendend =" + end);
				mMKSearch.walkingSearch(null, start, null, end);
			}
		});
	}

	private void myLoc() {
		MyLocationOverlay myloc = new MyLocationOverlay(mMapView);
		myloc.getMyLocation();
		myloc.enableCompass();
		mMapView.getOverlays().add(myloc);
	}

	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (String.valueOf(lat) != null) {
				GeoPoint point = new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6));
				mMapController.setCenter(point);
				mMapController.setZoom(12);
			}

		}
	};

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

	public void modifyLocationOverlayIcon(Drawable marker) {
		// 当传入marker为null时，使用默认图标绘制
		myLocationOverlay.setMarker(marker);
		// 修改图层，需要刷新MapView生效
		mMapView.refresh();
	}

	private void initMap() {
		// TODO Auto-generated method stub
		/*
		 * mBMapMan = new BMapManager(getApplication());
		 * mBMapMan.init("19e0f7732bf80cef1ba1ad6b4e75e10c", null);
		 */
		setContentView(R.layout.jtlx_activity);
		mMapView = (MapView) findViewById(R.id.bmapsView);
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
		rute();

	}

	public void requestLocClick() {
		isRequest = true;
		mLocClient.requestLocation();
		Toast.makeText(JtlxActivity.this, "正在定位……", Toast.LENGTH_SHORT).show();
		modifyLocationOverlayIcon(null);

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mMapView.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mMapView.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onResume() {
		BottomBar bottomBar = new BottomBar(this);
		mMapView.onResume();
		if (app.mBMapMan != null) {
			app.mBMapMan.start();
		}
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		mMapView.destroy();
		if (mLocClient != null)
			mLocClient.stop();
		if (app.mBMapMan != null) {
			mMKSearch.destory();
			app.mBMapMan.destroy();
			app.mBMapMan = null;
		}
		super.onDestroy();
	}

	private void rute() {
		mMKSearch = new MKSearch();
		mMKSearch.init(app.mBMapMan, new MKSearchListener() {

			@Override
			public void onGetWalkingRouteResult(MKWalkingRouteResult result, int arg1) {
				// TODO Auto-generated method stub
				mMapView.getOverlays().clear();
				if (result == null) {
					Toast.makeText(app, "没有找到步行路线", Toast.LENGTH_SHORT).show();
					return;
				}

				RouteOverlay routeOverlay = new RouteOverlay(JtlxActivity.this, mMapView);

				routeOverlay.setData(result.getPlan(0).getRoute(0));
				mMapView.getOverlays().add(routeOverlay);
				mMapView.invalidate();
				mMapView.getController().animateTo(result.getStart().pt);

				mMapView.refresh();// 刷新地图

			}

			@Override
			public void onGetTransitRouteResult(MKTransitRouteResult result, int arg1) { // TODO
				mMapView.getOverlays().clear(); // Auto-generated
				// method
				if (result == null) {
					return;
				}

				TransitOverlay transitOverlay = new TransitOverlay(JtlxActivity.this, mMapView);
				// 此处仅展示一个方案作为示例
				transitOverlay.setData(result.getPlan(0));
				mMapView.getOverlays().add(transitOverlay);
				mMapView.invalidate();
				mMapView.refresh();// 刷新地图
			}

			@Override
			public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) { // TODO
																					// Auto-generated
																					// method
																					// stub

			}

			@Override
			public void onGetShareUrlResult(MKShareUrlResult arg0, int arg1, int arg2) { // TODO
																							// Auto-generated
																							// method
																							// stub

			}

			@Override
			public void onGetPoiResult(MKPoiResult arg0, int arg1, int arg2) { // TODO
																				// Auto-generated
																				// method
																				// stub

			}

			@Override
			public void onGetPoiDetailSearchResult(int arg0, int arg1) { // TODO
																			// Auto-generated
																			// method
																			// stub

			}

			@Override
			public void onGetDrivingRouteResult(MKDrivingRouteResult result, int arg1) {
				// TODO Auto-generated method stub
				mMapView.getOverlays().clear();
				if (result == null)
					return;

				RouteOverlay routeOverlay = new RouteOverlay(JtlxActivity.this, mMapView);
				routeOverlay.setData(result.getPlan(0).getRoute(0));
				mMapView.getOverlays().add(routeOverlay);
				mMapView.refresh();// 刷新地图

			}

			@Override
			public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) { // TODO
																				// Auto-generated
																				// method
																				// stub

			}

			@Override
			public void onGetAddrResult(MKAddrInfo arg0, int arg1) {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		if (app.mBMapMan != null) {
			app.mBMapMan.stop();
		}
		super.onPause();
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		/*
		 * if (location != null) { lat = location.getLatitude(); lng =
		 * location.getLongitude(); }
		 */
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

}
