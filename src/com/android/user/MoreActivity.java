package com.android.user;

import com.android.util.WigetUtil;
import com.android.view.BottomBar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MoreActivity extends Activity {
	private ImageView back;
	private ImageView more1;
	private ImageView more2;
	private ImageView more3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more_activity);
		BottomBar bottomBar = new BottomBar(this);
		initView();
		initListener();
	}

	private void initView() {
		// TODO Auto-generated method stub
		// back = (ImageView)findViewById(R.id.back);
		more1 = (ImageView) findViewById(R.id.more1);
		more2 = (ImageView) findViewById(R.id.more2);
		more3 = (ImageView) findViewById(R.id.more3);
	}

	private void initListener() {
		// TODO Auto-generated method stub
		/*
		 * back.setOnTouchListener(OntouchUtil.onTouchListener);
		 * back.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub MoreActivity.this.finish(); } });
		 */
		more1.setOnClickListener(onClickListener);
		more2.setOnClickListener(onClickListener);
		more3.setOnClickListener(onClickListener);

	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			WigetUtil.alertDialogWithOkBtn(MoreActivity.this, "", getResources().getString(R.string.expect));
		}
	};

}
