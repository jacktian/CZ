package com.android.user;

import java.util.Timer;
import java.util.TimerTask;

import com.android.common.IntentAction;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class IntInit extends Activity {
	private SharedPreferences pref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intinit_activity);
		timer.schedule(task, 1000);
	}

	Timer timer = new Timer();
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				starthelper();
				break;
			}
			super.handleMessage(msg);
		};
	};
	TimerTask task = new TimerTask() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Message message = new Message();
			message.what = 1;
			handler.sendMessage(message);
		}

	};

	public void starthelper() {
		pref = getSharedPreferences("hostname", Context.MODE_WORLD_READABLE | Context.MODE_WORLD_WRITEABLE);
		String fg = pref.getString("start", "0");
		Intent intent = new Intent();
		if (fg.equals("0")) {
			Intent intent0 = new Intent(IntentAction.SYSTEMINIT);
			startActivity(intent0);
			try {
				startActivity(intent);
			} catch (ActivityNotFoundException e) {
			}
			pref.edit().putString("start", "1").commit();
		} else {
			Intent intent1 = new Intent(IntentAction.MAIN_ACTIVITY);
			startActivity(intent1);

		}
		IntInit.this.finish();
	}

}
