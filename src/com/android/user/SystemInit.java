package com.android.user;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import com.android.common.IntentAction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class SystemInit extends Activity {
	private ViewPager viewPager;
	private List<ImageView> imageViews;
	private ImageView guideImageView;

	private int[] imageResId;
	private List<View> dots;
	private int currentItem = 0;
	private ScheduledExecutorService scheduledExecutorService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.system_init);
		initView();
		initListener();
		imageResId = new int[] { R.drawable.guide_0, R.drawable.guide_1, R.drawable.guide_2 };
		imageViews = new ArrayList<ImageView>();
		for (int i = 0; i < imageResId.length; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setImageResource(imageResId[i]);
			imageView.setScaleType(ScaleType.CENTER_CROP);
			imageViews.add(imageView);
		}

		dots = new ArrayList<View>();
		dots.add(findViewById(R.id.v_dot0));
		dots.add(findViewById(R.id.v_dot1));
		dots.add(findViewById(R.id.v_dot2));
		// dots.add(findViewById(R.id.v_dot3));
		// dots.add(findViewById(R.id.v_dot4));

		viewPager = (ViewPager) findViewById(R.id.vp);
		viewPager.setAdapter(new MyAdapter());// �������ViewPagerҳ���������
		// ����һ������������ViewPager�е�ҳ��ı�ʱ����
		viewPager.setOnPageChangeListener(new MyPageChangeListener());
	}

	private void initListener() {
		// TODO Auto-generated method stub
		guideImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(IntentAction.MAIN_ACTIVITY);
				startActivity(intent);
				SystemInit.this.finish();
			}
		});
	}

	private void initView() {
		// TODO Auto-generated method stub
		guideImageView = (ImageView) findViewById(R.id.guideid);
	}

	@Override
	protected void onStart() {
		// scheduledExecutorService =
		// Executors.newSingleThreadScheduledExecutor();
		// ��Activity��ʾ������ÿ�������л�һ��ͼƬ��ʾ
		// scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 2,
		// TimeUnit.SECONDS);
		super.onStart();
	}

	@Override
	protected void onStop() {
		// ��Activity���ɼ��ʱ��ֹͣ�л�
		// scheduledExecutorService.shutdown();
		super.onStop();
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			viewPager.setCurrentItem(currentItem);// �л���ǰ��ʾ��ͼƬ
		};
	};

	private class ScrollTask implements Runnable {

		public void run() {
			synchronized (viewPager) {
				System.out.println("currentItem: " + currentItem);
				currentItem = (currentItem + 1) % imageViews.size();
				handler.obtainMessage().sendToTarget(); // ͨ��Handler�л�ͼƬ
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
