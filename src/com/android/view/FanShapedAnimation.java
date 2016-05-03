package com.android.view;

//Download by http://www.codefans.net
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;

public class FanShapedAnimation {
	// �붯��
	public static void startAnimationIN(ViewGroup viewGroup, int duration) {
		for (int i = 0; i < viewGroup.getChildCount(); i++) {
			viewGroup.getChildAt(i).setVisibility(View.VISIBLE);// ������ʾ
			viewGroup.getChildAt(i).setFocusable(true);// ��ý���
			viewGroup.getChildAt(i).setClickable(true);// ���Ե��
		}

		Animation animation;
		/**
		 * ��ת���� RotateAnimation(fromDegrees, toDegrees, pivotXType,
		 * pivotXValue, pivotYType, pivotYValue) fromDegrees ��ʼ��ת�Ƕ� toDegrees
		 * ��ת���ĽǶ� pivotXType X�� ������ pivotXValue x�� ��ת�Ĳο��� pivotYType
		 * Y�� ������ pivotYValue Y�� ��ת�Ĳο���
		 */
		animation = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1.0f);
		animation.setFillAfter(true);// ͣ���ڶ�������λ��
		animation.setDuration(duration);

		viewGroup.startAnimation(animation);

	}

	// ������
	public static void startAnimationOUT(final ViewGroup viewGroup, int duration, int startOffSet) {
		Animation animation;
		animation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1.0f);
		animation.setFillAfter(true);// ͣ���ڶ�������λ��
		animation.setDuration(duration);
		animation.setStartOffset(startOffSet);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				for (int i = 0; i < viewGroup.getChildCount(); i++) {
					viewGroup.getChildAt(i).setVisibility(View.GONE);// ������ʾ
					viewGroup.getChildAt(i).setFocusable(false);// ��ý���
					viewGroup.getChildAt(i).setClickable(false);// ���Ե��
				}

			}
		});

		viewGroup.startAnimation(animation);
	}
}
