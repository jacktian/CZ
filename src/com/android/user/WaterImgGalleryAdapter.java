package com.android.user;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class WaterImgGalleryAdapter extends Adapter_Base {

	ArrayList<String> imgList;

	public WaterImgGalleryAdapter(ArrayList<String> imgList, ImageLoader imageLoader) {
		this.imgList = imgList;
		this.imageLoader = imageLoader;
		options = new DisplayImageOptions.Builder().showStubImage(R.drawable.cqtx).showImageForEmptyUri(R.drawable.cqtx)
				.imageScaleType(ImageScaleType.NONE).cacheInMemory().cacheOnDisc().bitmapConfig(Bitmap.Config.RGB_565)
				.build();
	}

	public int getCount() {
		return imgList == null ? 0 : imgList.size();
	}

	public String getItem(int position) {
		return imgList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewClass view;
		if (convertView == null) {
			convertView = LayoutInflater.from(_context).inflate(R.layout.gallery_row_water, null);
			view = new ViewClass();
			view.item_img = (ImageView) convertView.findViewById(R.id.home_img_big);
			convertView.setTag(view);
		} else {
			view = (ViewClass) convertView.getTag();
		}

		// ����ͼƬ
		if (imgList.get(position) != null && !imgList.get(position).equals("")) {
			imageLoader.init(ImageLoaderConfiguration.createDefault(_context));
			imageLoader.displayImage(imgList.get(position), view.item_img, options);
			// Bitmap bm = ImageUtils.getBitmapByDrawable(R.drawable.water_0);
			// view.item_img.setImageBitmap(bm);
		}

		return convertView;
	}

	// ����������ࣺ
	private class ViewClass {
		ImageView item_img;
	}
}
