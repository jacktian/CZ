package com.android.user;

import com.android.common.IntentAction;
import com.android.util.WigetUtil;
import com.android.view.BottomBar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class HelperActivity extends Activity implements OnClickListener {
	private ImageView imageView1;
	private ImageView imageView2;
	private ImageView imageView3;
	private ImageView imageView4;
	private ImageView imageView5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.helper_activity);
		BottomBar bottomBar = new BottomBar(this);
		initView();
		initListener();
	}

	private void initView() {
		// TODO Auto-generated method stub
		imageView1 = (ImageView) findViewById(R.id.helper1);
		imageView2 = (ImageView) findViewById(R.id.helper2);
		imageView3 = (ImageView) findViewById(R.id.helper3);
		imageView4 = (ImageView) findViewById(R.id.helper4);
		imageView5 = (ImageView) findViewById(R.id.helper5);
	}

	private void initListener() {
		// TODO Auto-generated method stub
		/*
		 * imageView1.setOnTouchListener(OntouchUtil.onTouchListener);
		 * imageView2.setOnTouchListener(OntouchUtil.onTouchListener);
		 * imageView3.setOnTouchListener(OntouchUtil.onTouchListener);
		 * imageView4.setOnTouchListener(OntouchUtil.onTouchListener);
		 * imageView5.setOnTouchListener(OntouchUtil.onTouchListener);
		 * imageView6.setOnTouchListener(OntouchUtil.onTouchListener);
		 */
		imageView1.setOnClickListener(this);
		imageView2.setOnClickListener(this);
		imageView3.setOnClickListener(this);
		imageView4.setOnClickListener(this);
		imageView5.setOnClickListener(this);
	}

	private boolean isOpenNetWork() {
		ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connManager.getActiveNetworkInfo() != null) {
			return connManager.getActiveNetworkInfo().isAvailable();
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == imageView1) {
			if (!isOpenNetWork())
				WigetUtil.alertDialogWithnet(HelperActivity.this, "", getResources().getString(R.string.remind));
			else {
				Intent intent = new Intent(IntentAction.LOSTFOUND_ACTIVITY);
				startActivity(intent);
			}
		}
		if (v == imageView2) {
			if (!isOpenNetWork())
				WigetUtil.alertDialogWithnet(HelperActivity.this, "", getResources().getString(R.string.remind));
			else {
				Intent intent = new Intent(IntentAction.TEL_ACTIVITY);
				startActivity(intent);
			}
		}
		if (v == imageView3) {
			WigetUtil.alertDialogWithOkBtn(this, "", getResources().getString(R.string.update));
		}
		if (v == imageView4) {
			Intent intent = new Intent(IntentAction.MORE_ACTIVITY);
			startActivity(intent);
		}
		if (v == imageView5) {
			Intent intent = new Intent(IntentAction.ABOUT_ACTIVITY);
			startActivity(intent);
		}

	}

}
