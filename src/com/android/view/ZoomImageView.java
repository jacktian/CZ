package com.android.view;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

public class ZoomImageView extends ImageView
		implements OnScaleGestureListener, OnTouchListener, ViewTreeObserver.OnGlobalLayoutListener {

	public ZoomImageView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public ZoomImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		super.setScaleType(ScaleType.MATRIX);
		mScaleGestureDetector = new ScaleGestureDetector(context, this);
		this.setOnTouchListener(this);

	}

	public static final float SCALE_MAX = 4.0f;

	private float initScale = 1.0f;

	private final float[] matrixValues = new float[9];

	private boolean once = true;

	private ScaleGestureDetector mScaleGestureDetector = null;

	private final Matrix mScaleMatrix = new Matrix();
	private int lastPointerCount;
	private boolean isCanDrag;
	private float mLastX;
	private float mLastY;
	private boolean isCheckTopAndBottom;
	private boolean isCheckLeftAndRight;
	private double mTouchSlop = 10.0;

	@Override
	public boolean onScale(ScaleGestureDetector detector) {
		// TODO Auto-generated method stub
		float scale = getScale();
		float scaleFactor = detector.getScaleFactor();
		if (getDrawable() == null) {
			return true;
		}
		if ((scale < SCALE_MAX && scaleFactor > 1.0) || (scale > initScale && scaleFactor < 1.0)) {
			if (scaleFactor * scale < initScale) {
				scaleFactor = initScale / scale;
			}
			if (scaleFactor * scale > SCALE_MAX) {
				scaleFactor = SCALE_MAX / scale;
			}
			mScaleMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
			checkBorderAndCenterWhenScale();
			setImageMatrix(mScaleMatrix);
		}
		return true;
	}

	@Override
	public boolean onScaleBegin(ScaleGestureDetector detector) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void onScaleEnd(ScaleGestureDetector detector) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGlobalLayout() {
		// TODO Auto-generated method stub

		if (once) {
			Drawable d = getDrawable();
			if (d == null) {
				return;
			}

			int width = getWidth();
			int height = getHeight();

			int dw = d.getIntrinsicWidth();
			int dh = d.getIntrinsicHeight();
			float scale = 1.0f;

			if (dw > width && dh < height) {
				scale = width * 1.0f / dw;
			}
			if (dw < width && dh > height) {
				scale = height * 1.0f / dh + 2;
			}
			if (dw > width && dh > height) {
				scale = Math.min(width * 1.0f / dw, height * 1.0f / dh);
			}

			initScale = scale;
			mScaleMatrix.postTranslate((width - dw) / 2, (height - dh) / 2);
			mScaleMatrix.postScale(scale, scale, width / 2, height / 2);
			setImageMatrix(mScaleMatrix);
			once = false;
		}

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		mScaleGestureDetector.onTouchEvent(event);
		float x = 0, y = 0;
		final int pointerCount = event.getPointerCount();
		for (int i = 0; i < pointerCount; i++) {
			x += event.getX(i);
			y += event.getY(i);
		}

		x = x / pointerCount;
		y = y / pointerCount;

		if (pointerCount != lastPointerCount) {
			isCanDrag = false;
			mLastX = x;
			mLastY = y;
		}

		lastPointerCount = pointerCount;
		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:

			float dx = x - mLastX;
			float dy = y - mLastY;

			if (!isCanDrag) {
				isCanDrag = isCanDrag(dx, dy);
			}
			if (isCanDrag) {
				RectF rectF = getMatrixRectF();
				if (getDrawable() != null) {
					isCheckLeftAndRight = isCheckTopAndBottom = true;
					if (rectF.width() < getWidth()) {
						dx = 0;
						isCheckLeftAndRight = false;
					}
					if (rectF.height() < getHeight()) {
						dy = 0;
						isCheckTopAndBottom = false;
					}
					mScaleMatrix.postTranslate(dx, dy);
					checkMatrixBounds();
					setImageMatrix(mScaleMatrix);
				}
			}
			mLastX = x;
			mLastY = y;
			break;

		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:

			lastPointerCount = 0;
			break;
		}
		return true;
	}

	private void checkMatrixBounds() {
		RectF rect = getMatrixRectF();

		float deltaX = 0, deltaY = 0;
		final float viewWidth = getWidth();
		final float viewHeight = getHeight();
		if (rect.top > 0 && isCheckTopAndBottom) {
			deltaY = -rect.top;
		}
		if (rect.bottom < viewHeight && isCheckTopAndBottom) {
			deltaY = viewHeight - rect.bottom;
		}
		if (rect.left > 0 && isCheckLeftAndRight) {
			deltaX = -rect.left;
		}
		if (rect.right < viewWidth && isCheckLeftAndRight) {
			deltaX = viewWidth - rect.right;
		}
		mScaleMatrix.postTranslate(deltaX, deltaY);
	}

	private boolean isCanDrag(float dx, float dy) {
		return Math.sqrt((dx * dx) + (dy * dy)) >= mTouchSlop;
	}

	public final float getScale() {
		mScaleMatrix.getValues(matrixValues);
		return matrixValues[Matrix.MSCALE_X];
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		getViewTreeObserver().addOnGlobalLayoutListener(this);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		getViewTreeObserver().removeGlobalOnLayoutListener(this);
	}

	private void checkBorderAndCenterWhenScale() {
		RectF rect = getMatrixRectF();
		float deltaX = 0;
		float deltaY = 0;
		int width = getWidth();
		int height = getHeight();

		if (rect.width() >= width) {
			if (rect.left > 0) {
				deltaX = -rect.left;
			}
			if (rect.right < width) {
				deltaX = width - rect.right;
			}
		}
		if (rect.height() >= height) {
			if (rect.top > 0) {
				deltaY = -rect.top;
			}
			if (rect.bottom < height) {
				deltaY = height - rect.bottom;
			}
		}

		if (rect.width() < width) {
			deltaX = width * 0.5f - rect.right + 0.5f * rect.width();
		}
		if (rect.height() < height) {
			deltaY = height * 0.5f - rect.bottom + 0.5f * rect.height();
		}

		mScaleMatrix.postTranslate(deltaX, deltaY);

	}

	private RectF getMatrixRectF() {
		// TODO Auto-generated method stub
		Matrix matrix = mScaleMatrix;
		RectF rect = new RectF();
		Drawable d = getDrawable();
		if (d != null) {
			rect.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
			matrix.mapRect(rect);
		}

		return rect;
	}
}
