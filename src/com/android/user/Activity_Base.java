package com.android.user;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public abstract class Activity_Base extends Activity {

	protected ImageLoader imageLoader = ImageLoader.getInstance();

	protected LinearLayout titlebar;
	protected Button titlebar_back, titlebar_left, titlebar_right, titlebar_menu;
	protected TextView titlebar_name;
	protected ImageButton webnav_left, webnav_right;

	String tmp = "";

	private boolean instanceStateSaved;

	ProgressDialog pdialog;
	String Skinname;

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// getMenuInflater().inflate(R.menu.main_menu, menu);
	// return true;
	// }
	//
	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// switch (item.getItemId()) {
	// case R.id.item_clear_memory_cache:
	// imageLoader.clearMemoryCache();
	// return true;
	// case R.id.item_clear_disc_cache:
	// imageLoader.clearDiscCache();
	// return true;
	// default:
	// return false;
	// }
	// }

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MapApplication.getInstance().addActivity(this);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		instanceStateSaved = true;
	}

	protected void onResume() {
		super.onResume();
		/**
		 * 页面起始（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
		 * 不能与StatService.onPageStart一级onPageEnd函数交叉使用
		 */
		// StatService.onResume(this);
	}

	@Override
	protected void onDestroy() {
		if (!instanceStateSaved) {
			imageLoader.stop();
		}
		super.onDestroy();
	}

	protected void onPause() {
		super.onPause();

		/**
		 * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
		 * 不能与StatService.onPageStart一级onPageEnd函数交叉使用
		 */
		// StatService.onPause(this);
	}
	// /**
	// * 封装的asynctask方法，此方法没有进度框.
	// *
	// * @param pCallEarliest 运行于主线程，最先执行此方法.
	// * @param mCallable 运行于异步线程,第二执行此方法.
	// * @param mCallback 运行于主线程,最后执行此方法.
	// */
	// public <T> void doAsync(final CallEarliest<T> pCallEarliest,
	// final Callable<T> mCallable, final Callback<T> mCallback) {
	// AsyncTaskUtils.doAsync(pCallEarliest, mCallable, mCallback);
	// }
	//
	// /**
	// * 封装的asynctask方法，此方法拥有进度对话框，并支持定义样式.
	// * @param pContext 上下文
	// * @param styleID 对话框样式
	// ProgressDialog.STYLE_HORIZONTAL|ProgressDialog.STYLE_SPINNER
	// * @param pTitle 标题
	// * @param pMessage 内容
	// * @param pCallEarliest 运行于主线程，最先执行此方法.
	// * @param progressCallable 运行于异步线程,用于传递对话框进度.
	// * @param pCallback 运行于主线程,最后执行此方法.
	// */
	// public <T> void doProgressAsync(final Context pContext, final int
	// styleID,
	// final String pTitleResID, final String pMessageResID,
	// final CallEarliest<T> pCallEarliest, final ProgressCallable<T> pCallable,
	// final Callback<T> pCallback) {
	//
	// AsyncTaskUtils.doProgressAsync(pContext, styleID, pTitleResID,
	// pMessageResID, pCallEarliest, pCallable, pCallback);
	// }
	//
	//
	// /**
	// * 封装的asynctask方法，此方法拥有进度对话框，并支持定义样式.
	// * @param pContext 上下文
	// * @param styleID 对话框样式
	// ProgressDialog.STYLE_HORIZONTAL|ProgressDialog.STYLE_SPINNER
	// * @param pTitle 标题,资源id
	// * @param pMessage 内容,资源id
	// * @param pCallEarliest 运行于主线程，最先执行此方法.
	// * @param progressCallable 运行于异步线程,用于传递对话框进度.
	// * @param pCallback 运行于主线程,最后执行此方法.
	// */
	// public <T> void doProgressAsync(final Context pContext, final int
	// styleID,
	// final int pTitleResID, final int pMessageResID,
	// final CallEarliest<T> pCallEarliest, final ProgressCallable<T> pCallable,
	// final Callback<T> pCallback) {
	//
	// AsyncTaskUtils.doProgressAsync(pContext, styleID, pTitleResID,
	// pMessageResID, pCallEarliest, pCallable, pCallback);
	// }
}
