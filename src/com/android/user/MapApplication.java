package com.android.user;

import java.util.LinkedList;
import java.util.List;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.widget.Toast;

public class MapApplication extends Application {

	private List<Activity> activityList = new LinkedList<Activity>();
	private static MapApplication mInstance = null;
	public boolean m_bKeyRight = true;
	BMapManager mBMapMan = null;
	// public static final String strKey = "Rz1zCfqKG670eKstkrLsNiEX";
	// public static final String strKey = "Pn5mUxZeEZ5xLkRX7ghgaGLk";
	// public static final String strKey = "BBxcNyjA6v6TAfNi4FoG2k5o";
	public static final String strKey = "Pn5mUxZeEZ5xLkRX7ghgaGLk";

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		// initEngineManager(this);
	}

	public void initEngineManager(Context context) {
		if (mBMapMan == null) {
			mBMapMan = new BMapManager(context);
		}

		if (!mBMapMan.init(strKey, new MyGeneralListener())) {
			Toast.makeText(MapApplication.getInstance().getApplicationContext(), "BMapManager  初始化错误!",
					Toast.LENGTH_LONG).show();
		}

	}

	public static MapApplication getInstance() {
		return mInstance;
	}

	/*
	 * public static BaseApplication getInstance(){ if(null == instance){
	 * instance = new BaseApplication(); } return instance; }
	 */

	// 添加Activity到容器中
	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	// Activity结束finish
	public void exitForLog() {
		for (Activity activity : activityList) {
			activity.finish();
		}
	}

	// 遍历所有Activity并finish
	public void exit() {
		// 退出
		for (Activity activity : activityList) {
			activity.finish();
		}
		// android.os.Process.killProcess(android.os.Process.myPid());
		System.exit(0);
		// new LogoutRequestTask().execute();
	}

	// 常用事件监听，用来处理通常的网络错误，授权验证错误等
	static class MyGeneralListener implements MKGeneralListener {

		@Override
		public void onGetNetworkState(int iError) {
			if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
				Toast.makeText(MapApplication.getInstance().getApplicationContext(), "您的网络出错啦！", Toast.LENGTH_LONG)
						.show();
			} else if (iError == MKEvent.ERROR_NETWORK_DATA) {
				Toast.makeText(MapApplication.getInstance().getApplicationContext(), "输入正确的检索条件！", Toast.LENGTH_LONG)
						.show();
			}
			// ...
		}

		@Override
		public void onGetPermissionState(int iError) {
			if (iError == MKEvent.ERROR_PERMISSION_DENIED) {
				// 授权Key错误：
				Toast.makeText(MapApplication.getInstance().getApplicationContext(),
						"请在 DemoApplication.java文件输入正确的授权Key！", Toast.LENGTH_LONG).show();
				MapApplication.getInstance().m_bKeyRight = false;
			}
		}
	}
}