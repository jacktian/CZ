package com.android.util;

import com.android.user.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;

public class WigetUtil {
	public static void alertDialogWithOkBtn(final Context context, String title, CharSequence message) {
		new AlertDialog.Builder(context).setTitle(title).setMessage(message)
				.setPositiveButton(R.string.glo_ok, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				})

				.show();
	}

	public static void alertDialogWithnet(final Context context, String title, CharSequence message) {
		new AlertDialog.Builder(context).setTitle(title).setMessage(message)
				.setPositiveButton(R.string.global_ok, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				}).setNegativeButton(R.string.setnet, new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = null;
						intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
						context.startActivity(intent);
					}
				}).show();
	}

}
