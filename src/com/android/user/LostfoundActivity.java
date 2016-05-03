package com.android.user;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import com.android.common.Biaoqingadapter;
import com.android.common.LostFound;
import com.android.net.LostFoundService;
import com.android.view.BottomBar;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;

public class LostfoundActivity extends Activity {
	private ImageView back;
	private ListView list;
	private List<LostFound> losts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lostfound_activity);
		BottomBar bottomBar = new BottomBar(this);
		initView();

		// initListener();
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
		// back = (ImageView)findViewById(R.id.back);
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				list = (ListView) findViewById(R.id.list1);
				try {
					losts = new LostFoundService().getLostFound();
					Log.e("20140118", "losts = " + losts);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (XmlPullParserException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Message message = new Message();
				message.what = 3;
				handler.sendMessage(message);
			}
		}).start();
	}

	private void initListener() {
		// TODO Auto-generated method stub

		Biaoqingadapter biaoqingadapter = new Biaoqingadapter(this, getData());
		list.setAdapter(biaoqingadapter);
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 3:
				if (losts == null) {

				} else {
					initListener();
				}

				break;
			}
			super.handleMessage(msg);
		};
	};

	private List<String> getData() {

		List<String> data = new ArrayList<String>();
		for (int i = 0; i < losts.size(); i++) {
			data.add(losts.get(i).getTimestamp() + "\n" + losts.get(i).getN_event());
		}
		return data;
	}

}
