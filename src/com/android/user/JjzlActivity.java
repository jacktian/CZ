package com.android.user;

import com.android.view.BottomBar;

import android.app.Activity;
import android.os.Bundle;

public class JjzlActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yygl_activity);
		BottomBar bottomBar = new BottomBar(this);

	}
}
