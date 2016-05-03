package com.android.view;

import com.android.common.IntentAction;
import com.android.user.R;
import com.android.util.AniImageView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class BottomBar {
	private Activity activity;
	private int activityIndex;
	private ImageView mimageView;
	private AniImageView mAniImageView;
	private AnimationDrawable anim;
	private RelativeLayout level2;
	private ImageButton helperBtn;
	private ImageButton navball1;
	private ImageButton c1;
	private ImageButton c2;
	private ImageButton c3;
	private ImageButton c4;

	private boolean isLevel2Show = true;

	public BottomBar(final Activity activity) {
		this.activity = activity;
		navball1 = (ImageButton) activity.findViewById(R.id.navball1);
		c1 = (ImageButton) activity.findViewById(R.id.c1);
		c2 = (ImageButton) activity.findViewById(R.id.c2);
		c3 = (ImageButton) activity.findViewById(R.id.c3);
		c4 = (ImageButton) activity.findViewById(R.id.c4);
		helperBtn = (ImageButton) activity.findViewById(R.id.c5);

		level2 = (RelativeLayout) activity.findViewById(R.id.level2);
		mAniImageView = (AniImageView) activity.findViewById(R.id.aniimageviewid);
		mAniImageView.setImageResource(R.drawable.bottom_animation);
		anim = (AnimationDrawable) mAniImageView.getDrawable();
		mAniImageView.getViewTreeObserver().addOnPreDrawListener(opdl);
		FanShapedAnimation.startAnimationOUT(level2, 500, 0);

		c1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(IntentAction.MAIN_ACTIVITY);
				activity.startActivity(intent);
			}
		});
		c2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(IntentAction.GL_ACTIVITY);
				activity.startActivity(intent);
			}
		});

		helperBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(IntentAction.HELPER_ACTIVITY);
				activity.startActivity(intent);
			}
		});
		c3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(IntentAction.VIP_ACTIVITY);
				activity.startActivity(intent);
			}
		});
		c4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(IntentAction.BM_ACTIVITY);
				activity.startActivity(intent);
			}
		});

		mAniImageView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!isLevel2Show) {
					FanShapedAnimation.startAnimationIN(level2, 500);
				} else {
					FanShapedAnimation.startAnimationIN(level2, 500);

				}
				isLevel2Show = !isLevel2Show;
			}
		});
		navball1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FanShapedAnimation.startAnimationOUT(level2, 500, 0);
			}
		});

	}

	OnPreDrawListener opdl = new OnPreDrawListener() {
		public boolean onPreDraw() {
			// TODO Auto-generated method stub
			anim.start();
			return true;
		}
	};

}
