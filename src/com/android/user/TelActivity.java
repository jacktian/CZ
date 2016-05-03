package com.android.user;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.xmlpull.v1.XmlPullParserException;

import com.android.common.TelNum;
import com.android.net.TelService;
import com.android.view.BottomBar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class TelActivity extends Activity {
	private ImageView back;
	private Button fjtel;
	private Button hjtel;
	private Button jjtel;
	private PopupWindow popupWindow;
	private View view;
	private Button btn1;
	private Button btn2;
	private ListView tellist;
	private List<TelNum> tels;
	private ListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tel_activity);
		BottomBar bottomBar = new BottomBar(this);
		initView();
		initListener();
	}

	private void initView() {
		// TODO Auto-generated method stub
		view = LayoutInflater.from(this).inflate(R.layout.popup_window, null);
		tellist = (ListView) findViewById(R.id.tellist);

		btn1 = (Button) view.findViewById(R.id.btn1);
		btn2 = (Button) view.findViewById(R.id.btn2);
		// fjtel.setOnClickListener(onClickListener);
		// hjtel.setOnClickListener(onClickListener);
		// jjtel.setOnClickListener(onClickListener);
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					tels = new TelService().getTelNum();
					Log.e("20140118", "tels = " + tels);
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

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 3:
				if (tels == null) {
				} else {
					adapter = new ListAdapter(tels);
					tellist.setAdapter(adapter);
				}

				break;
			}
			super.handleMessage(msg);
		};
	};

	private void initListener() {
		// TODO Auto-generated method stub
		btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				popupWindow.dismiss();
			}
		});
	}

	OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == fjtel) {
				getPopup();
				btn1.setText(getResources().getString(R.string.fj));
				btn1.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(Intent.ACTION_CALL,
								Uri.parse("tel:" + getResources().getString(R.string.fj)));
						startActivity(intent);
					}
				});
			}
			if (v == hjtel) {
				getPopup();
				Log.e("20131119", "hjtelhjtelhjtelhjtelhjtelhjtel");
				btn1.setText(getResources().getString(R.string.hj));
				btn1.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(Intent.ACTION_CALL,
								Uri.parse("tel:" + getResources().getString(R.string.hj)));
						startActivity(intent);
					}
				});
			}
			if (v == jjtel) {
				Log.e("20131119", "jjteljjteljjteljjteljjteljjtel");
				getPopup();
				btn1.setText(getResources().getString(R.string.jj));
				btn1.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(Intent.ACTION_CALL,
								Uri.parse("tel:" + getResources().getString(R.string.jj)));
						startActivity(intent);
					}
				});
			}

		}
	};

	public class ListAdapter extends BaseAdapter {
		LayoutInflater mInflater = null;
		List<TelNum> list;

		public ListAdapter(List<TelNum> list) {
			// TODO Auto-generated constructor stub
			this.list = list;
			mInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(final int arg0, View convertView, ViewGroup arg2) {
			// TODO Auto-generated method stub
			ViewHolder viewHolder;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.tel_item, null);
				viewHolder.teltxt = (TextView) convertView.findViewById(R.id.teltxt);
				viewHolder.telnum = (TextView) convertView.findViewById(R.id.telnum);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			// viewHolder.img.setImageResource(R.drawable.bg_22x);
			// viewHolder.teltxt.(list.get(arg0));
			// viewHolder.img.setScaleType(ScaleType.CENTER_CROP);

			viewHolder.teltxt.setTextColor(Color.GRAY);
			viewHolder.telnum.setTextColor(Color.GRAY);

			viewHolder.teltxt.setText(tels.get(arg0).getTelName());
			viewHolder.telnum.setText(tels.get(arg0).getTelNums());
			Log.e("Aatha 20140118", "tels.get(arg0).getTelName() = " + tels.get(arg0).getTelName());
			Log.e("Aatha 20140118", "tels.get(arg0).getTelNums() = " + tels.get(arg0).getTelNums());
			viewHolder.telnum.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tels.get(arg0).getTelNums()));
					startActivity(intent);
				}
			});
			return convertView;
		}

	}

	public class ViewHolder {
		public TextView teltxt;
		public TextView telnum;
	}

	private void getPopup() {
		popupWindow = new PopupWindow(view, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setFocusable(true);
		popupWindow.setTouchable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setAnimationStyle(R.style.PopupAnimation);
		popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
	}
}
