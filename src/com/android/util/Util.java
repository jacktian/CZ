package com.android.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class Util {
	private static ProgressDialog dialog = null;

	public static void showDialg(Context context, String message, int stytle) {

		if (dialog == null) {
			dialog = new ProgressDialog(context);
			dialog.setProgressStyle(stytle);
			dialog.setMessage(message);
			Window window = dialog.getWindow();
			WindowManager.LayoutParams lp = window.getAttributes();
			lp.alpha = 0.8f;
			lp.dimAmount = 0.8f;
			window.setAttributes(lp);
			dialog.show();

		}
	}

	public static void dissmissDialog() {

		if (dialog == null)
			return;

		if (dialog.isShowing()) {

			dialog.cancel();
			dialog.dismiss();
			dialog = null;
		}
	}

	/**
	 * �ж�����
	 * 
	 * @return
	 */
	public static boolean checkNet(Context context) {
		if (hasConnectedNetwork(context)) {
			return true;
		}
		return false;
	}

	/**
	 * �Ƿ�����l��
	 * 
	 * @param context
	 * @return
	 */
	public static boolean hasConnectedNetwork(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		}
		return connectivity.getActiveNetworkInfo() != null;
	}

	/**
	 * ��ʾtoast����ʱ�䣩
	 * 
	 * @param context
	 * @param s
	 */
	public static void showToastLong(Context context, String s) {
		Toast.makeText(context, s, Toast.LENGTH_LONG).show();
	}

}
