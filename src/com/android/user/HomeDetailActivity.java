package com.android.user;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;
import net.tsz.afinal.FinalBitmap;

public class HomeDetailActivity extends Activity {
	private ViewPager viewPager;
	private int[] imageResId;
	private List<ImageView> imageViews;
	private List<View> dots;
	private int currentItem = 0;
	private TextView detail;
	private FinalBitmap fb;
	String string = "", imageurl = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_activity);
		fb = FinalBitmap.create(this);
		imageResId = new int[] { R.drawable.img_img_fun1 };
		imageViews = new ArrayList<ImageView>();
		detail = (TextView) findViewById(R.id.detail);
		try {
			Intent intent = getIntent();
			string = intent.getStringExtra("homedetail") + "\n\n\n\n\n\n";
			imageurl = intent.getStringExtra("imageurl");
			// Bitmap bitmap = intent.getParcelableExtra("bitmap");
			detail.setText(string);
		} catch (Exception e) {
			// TODO: handle exception
		}

		// detail.setText(getResources().getString(R.string.homenew1));
		// for(int i = 0; i <imageResId.length;i++)
		// {
		ImageView imageView = new ImageView(this);
		// imageView.setImageResource(imageResId[i]);
		// imageView.setImageBitmap(MainActivity.bitmap);

		imageView.setScaleType(ScaleType.FIT_XY);
		imageViews.add(imageView);
		fb.display(imageView, imageurl);
		// }
		dots = new ArrayList<View>();
		dots.add(findViewById(R.id.v_dot0));
		dots.add(findViewById(R.id.v_dot1));
		dots.add(findViewById(R.id.v_dot2));
		dots.add(findViewById(R.id.v_dot3));
		dots.add(findViewById(R.id.v_dot4));

		viewPager = (ViewPager) findViewById(R.id.vp);
		viewPager.setAdapter(new MyAdapter());
		viewPager.setOnPageChangeListener(new MyPageChangeListener());
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			viewPager.setCurrentItem(currentItem);
		};
	};

	private class ScrollTask implements Runnable {

		public void run() {
			synchronized (viewPager) {
				System.out.println("currentItem: " + currentItem);
				currentItem = (currentItem + 1) % imageViews.size();
				handler.obtainMessage().sendToTarget();
			}
		}

	}

	private class MyPageChangeListener implements OnPageChangeListener {
		private int oldPosition = 0;

		/**
		 * This method will be invoked when a new page becomes selected.
		 * position: Position index of the new selected page.
		 */
		public void onPageSelected(int position) {
			currentItem = position;
			dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
			dots.get(position).setBackgroundResource(R.drawable.dot_focused);
			oldPosition = position;
		}

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}
	}

	private class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return imageResId.length;
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(imageViews.get(arg1));
			return imageViews.get(arg1);
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView((View) arg2);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {

		}

		@Override
		public void finishUpdate(View arg0) {

		}
	}

}
