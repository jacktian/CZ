package com.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;
import android.webkit.WebView;
import android.widget.ProgressBar;

public class Mywebview extends WebView {

	// public Mywebview(Context context) {
	// super(context);
	// }
	// public Mywebview(Context context,AttributeSet attrs)
	// {
	// super(context,attrs);
	// }
	private float x;
	private float y;

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		ViewParent mViewParent = this.getParent();
		final int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			x = ev.getX();
			y = ev.getY();
			mViewParent.requestDisallowInterceptTouchEvent(true);
			break;
		case MotionEvent.ACTION_MOVE:
			if (Math.abs(ev.getX() - x) > 10)
				mViewParent.requestDisallowInterceptTouchEvent(false);
			else if (Math.abs(ev.getY() - y) > 10)
				mViewParent.requestDisallowInterceptTouchEvent(true);
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			mViewParent.requestDisallowInterceptTouchEvent(true);
			break;
		}
		return super.dispatchTouchEvent(ev);
	}

	public ProgressBar progressbar;

	public Mywebview(Context context, AttributeSet attrs) {
		super(context, attrs);
		progressbar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
		progressbar.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, 5, 0, 0));
		addView(progressbar);
		setWebChromeClient(new WebChromeClient());
	}

	public class WebChromeClient extends android.webkit.WebChromeClient {
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			if (newProgress == 100) {
				progressbar.setVisibility(GONE);
			} else {
				if (progressbar.getVisibility() == GONE)
					progressbar.setVisibility(VISIBLE);
				progressbar.setProgress(newProgress);
			}
			super.onProgressChanged(view, newProgress);
		}

	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
		lp.x = l;
		lp.y = t;
		progressbar.setLayoutParams(lp);
		super.onScrollChanged(l, t, oldl, oldt);
	}
}
