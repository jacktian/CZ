package com.android.common;

import java.util.List;

import com.android.user.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Biaoqingadapter extends BaseAdapter {
	private Context m_context;
	// private ArrayList<HashMap<String,Object>> listviewitems;
	List<String> data;

	public Biaoqingadapter(Context context, List<String> data) {
		this.m_context = context;
		this.data = data;
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int positon) {
		return positon;
	}

	public long getItemId(int id) {
		return id;
	}

	public View getView(final int position, View convertView, ViewGroup viewgroup) {
		final ViewHolder item;
		if (convertView != null) {
			item = (ViewHolder) convertView.getTag();
		} else {
			item = new ViewHolder();
			convertView = LayoutInflater.from(m_context).inflate(R.layout.lostfounditem, null);

			item.title = (TextView) convertView.findViewById(R.id.title);
			item.title.setTextColor(Color.GRAY);
			// item.txt=(TextView)convertView.findViewById(R.id.content);
			convertView.setTag(item);
		}
		item.title.setText(data.get(position).toString());

		return convertView;
	}

	public class ViewHolder {
		public TextView title;
		public TextView txt;
	}
}
