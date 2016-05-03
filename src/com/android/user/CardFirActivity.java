package com.android.user;

import java.util.ArrayList;
import java.util.List;

import com.android.common.CardBean;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CardFirActivity extends Activity {
	private ListView cardList;
	private List<CardBean> listCard;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.carddetail);
		initView();
		initListener();
	}

	private void initView() {
		// TODO Auto-generated method stub
		cardList = (ListView) findViewById(R.id.cardlist);
		Intent intent = getIntent();

		int stringValue = intent.getIntExtra("whichcard", 0);
		// int stringValue=intent.getIntExtra("card",0);
		switch (stringValue) {
		// case R.id.experiencebtn1:
		case 0:
			listCard = new ArrayList<CardBean>();
			listCard.add(new CardBean("宝贝地盘", R.drawable.m1_proj_image, ""));
			listCard.add(new CardBean("飞旋骑士", R.drawable.m2_proj_image, ""));
			listCard.add(new CardBean("咕噜咕噜", R.drawable.m3_proj_image, ""));
			listCard.add(new CardBean("海底精灵城", R.drawable.m4_proj_image, ""));
			listCard.add(new CardBean("空中大巡逻", R.drawable.m5_proj_image, ""));
			listCard.add(new CardBean("么么公主的家", R.drawable.m6_proj_image, ""));
			listCard.add(new CardBean("摩尔桥", R.drawable.m7_proj_image, ""));
			listCard.add(new CardBean("魔法精灵", R.drawable.m8_proj_image, ""));
			cardList.setAdapter(new CardListAdapter(listCard));
			break;
		// case R.id.experiencebtn2:
		case 1:
			listCard = new ArrayList<CardBean>();
			listCard.add(new CardBean("精灵半岛", R.drawable.j1_proj_image, ""));
			listCard.add(new CardBean("神秘之岛", R.drawable.j2_proj_image, ""));
			listCard.add(new CardBean("月银王国", R.drawable.j3_proj_image, ""));
			cardList.setAdapter(new CardListAdapter(listCard));
			break;
		// case R.id.experiencebtn3:
		case 2:
			listCard = new ArrayList<CardBean>();
			listCard.add(new CardBean("龙行天下", R.drawable.c1_proj_image, ""));
			listCard.add(new CardBean("云之秘境", R.drawable.c2_proj_image, ""));
			listCard.add(new CardBean("烈魂", R.drawable.c3_proj_image, ""));
			listCard.add(new CardBean("天堂之舵", R.drawable.c4_proj_image, ""));
			listCard.add(new CardBean("冰剑国度", R.drawable.c5_proj_image, ""));
			listCard.add(new CardBean("传奇天下", R.drawable.recommend_proj_32x, ""));
			listCard.add(new CardBean("海盗王号", R.drawable.c7_proj_image, ""));
			listCard.add(new CardBean("游戏要塞", R.drawable.c8_proj_image, ""));
			cardList.setAdapter(new CardListAdapter(listCard));
			break;
		// case R.id.experiencebtn5:
		case 3:
			listCard = new ArrayList<CardBean>();
			listCard.add(new CardBean("撕裂星空", R.drawable.x1_proj_image, ""));
			listCard.add(new CardBean("雷神之怒", R.drawable.x2_proj_image, ""));
			listCard.add(new CardBean("天幕幻想", R.drawable.x3_proj_image, ""));
			listCard.add(new CardBean("大话嬉戏", R.drawable.x4_proj_image, ""));
			listCard.add(new CardBean("天际骇客", R.drawable.x5_proj_image, ""));
			cardList.setAdapter(new CardListAdapter(listCard));
			break;
		// case R.id.experiencebtn4:
		case 4:
			listCard = new ArrayList<CardBean>();
			listCard.add(new CardBean("中华龙塔", R.drawable.s1_proj_image, ""));
			listCard.add(new CardBean("嬉戏天路", R.drawable.s2_proj_image, ""));
			cardList.setAdapter(new CardListAdapter(listCard));
			break;
		// case R.id.experiencebtn6:
		case 5:
			listCard = new ArrayList<CardBean>();
			listCard.add(new CardBean("兽血征程", R.drawable.l1_proj_image, ""));
			listCard.add(new CardBean("热浪湾", R.drawable.l2_proj_image, ""));
			listCard.add(new CardBean("魔神天途", R.drawable.l3_proj_image, ""));
			listCard.add(new CardBean("梦幻擎天", R.drawable.l4_proj_image, ""));
			listCard.add(new CardBean("迷兽门", R.drawable.l5_proj_image, ""));
			listCard.add(new CardBean("迷兽桥", R.drawable.l6_proj_image, ""));
			listCard.add(new CardBean("欢乐巷", R.drawable.l1_proj_image, ""));
			cardList.setAdapter(new CardListAdapter(listCard));
			break;
		// case R.id.experiencebtn7:
		case 6:
			listCard = new ArrayList<CardBean>();
			listCard.add(new CardBean("历险封神榜", R.drawable.hxsl_lxfsb_01, ""));
			listCard.add(new CardBean("西游降魔", R.drawable.hxsl_xyxm_02, ""));
			listCard.add(new CardBean("绿野迷踪", R.drawable.hxsl_lymz_03, ""));
			listCard.add(new CardBean("幸福运转", R.drawable.hxsl_xfyz_04, ""));
			listCard.add(new CardBean("蓝宇飞艇", R.drawable.hxsl_lyft_05, ""));
			listCard.add(new CardBean("旋转激流", R.drawable.hxsl_xzjl_06, ""));
			listCard.add(new CardBean("地海惊流", R.drawable.hxsl_dhjl_07, ""));
			cardList.setAdapter(new CardListAdapter(listCard));
			break;
		// case R.id.experiencebtn8:
		case 7:
			listCard = new ArrayList<CardBean>();
			listCard.add(new CardBean("鲸鱼船", R.drawable.lkwg_jyc_01, ""));
			listCard.add(new CardBean("神奇飞毯", R.drawable.lkwg_sqft_02, ""));
			listCard.add(new CardBean("彩虹泡泡", R.drawable.lkwg_chpp_03, ""));
			listCard.add(new CardBean("洛克历险", R.drawable.lkwg_lklx_04, ""));

			cardList.setAdapter(new CardListAdapter(listCard));
			break;
		// case R.id.experiencebtn9:
		case 8:
			listCard = new ArrayList<CardBean>();
			listCard.add(new CardBean("奥丁龙旋风", R.drawable.wmssj_adlxf_01, ""));
			listCard.add(new CardBean("漩涡试炼", R.drawable.wmssj_xwsl_02, ""));
			listCard.add(new CardBean("巨龙深渊", R.drawable.wmssj_jlsy_03, ""));
			listCard.add(new CardBean("飓风碗", R.drawable.wmssj_jfw_04, ""));
			listCard.add(new CardBean("麦哲伦之路", R.drawable.wmssj_mzlzl_05, ""));
			listCard.add(new CardBean("极速训练港", R.drawable.wmssj_jsxlg_06, ""));
			listCard.add(new CardBean("热浪湾", R.drawable.wmssj_rlw_07, ""));
			listCard.add(new CardBean("无风带", R.drawable.wmssj_wfd_08, ""));
			listCard.add(new CardBean("英雄寨", R.drawable.wmssj_yxz_09, ""));
			cardList.setAdapter(new CardListAdapter(listCard));
			break;
		default:
			break;
		}

	}

	private void initListener() {
		// TODO Auto-generated method stub

	}

	public class CardListAdapter extends BaseAdapter {
		LayoutInflater mInflater = null;
		List<CardBean> list;

		public CardListAdapter(List<CardBean> list) {
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
		public View getView(int arg0, View convertView, ViewGroup arg2) {
			// TODO Auto-generated method stub
			ViewHolder viewHolder;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.card_item, null);
				viewHolder.title = (TextView) convertView.findViewById(R.id.titletxt);
				viewHolder.img = (ImageView) convertView.findViewById(R.id.carditemimg);
				viewHolder.txt = (TextView) convertView.findViewById(R.id.cardtxt);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			// viewHolder.img.setImageResource(R.drawable.bg_22x);
			Log.e("20131129", " list.get(arg0).getCard()=" + list.get(arg0).getCard());
			viewHolder.title.setText(list.get(arg0).getTitle());
			viewHolder.img.setImageResource(list.get(arg0).getCard());
			Log.e("20131129", " img=" + viewHolder.img);
			// viewHolder.img.setImageResource(R.drawable.m1_proj_image);
			viewHolder.txt.setText(list.get(arg0).getTxt());

			return convertView;
		}

	}

	public class ViewHolder {
		public TextView title;
		public ImageView img;
		public TextView txt;

	}

}
