package com.android.user;

import com.android.view.BottomBar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class AboutActivity extends Activity {
	private ImageView back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_activity);
		BottomBar bottomBar = new BottomBar(this);
		initView();
		initListener();
	}

	private void initView() {
		// TODO Auto-generated method stub
		// back = (ImageView)findViewById(R.id.back);

	}

	private void initListener() {
		// TODO Auto-generated method stub
		/*
		 * back.setOnTouchListener(OntouchUtil.onTouchListener);
		 * back.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub AboutActivity.this.finish(); } });
		 */

	}

}
