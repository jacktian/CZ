package com.android.user;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

public class MessageBox {

	public static Dialog CreateAlertDialog1(String title, String msg, Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(msg).setTitle(title);
		// .setCancelable(false)
		// .setPositiveButton(btnname, new DialogInterface.OnClickListener() {
		// public void onClick(DialogInterface dialog, int id) {
		// dialog.dismiss();
		// }
		// });
		return builder.create();

	}

	public static AlertDialog.Builder createAlertDialogBuilder(String title, String leftBuff, String msg,
			Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(msg).setTitle(title)
				// .setCancelable(false)
				.setPositiveButton(leftBuff, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
					}
				});
		return builder;

	}

	public static Dialog createAlertDialog(String title, String msg, String btnname, Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(msg).setTitle(title).setCancelable(false).setPositiveButton(btnname,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
					}
				});
		return builder.create();

	}

	public static AlertDialog.Builder createAlertDialogBuilder(String title, String leftBuff, Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title)
				// .setCancelable(false)
				.setPositiveButton(leftBuff, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
					}
				});
		return builder;

	}

	public static void CreateAlertDialog(String title, String msg, Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(msg).setTitle(title).setCancelable(false).setPositiveButton("ȷ��",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	public static void CreateAlertDialog1(String title, SpannableString ss, Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(ss).setTitle(title).setCancelable(false).setPositiveButton("ȷ��",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	public static ProgressDialog createProgressDialog(boolean canceable, String msg, Context context) {

		ProgressDialog dialog = new ProgressDialog(context);
		dialog.setMessage(msg);
		dialog.setIndeterminate(true);
		dialog.setCancelable(canceable);

		return dialog;
	}

	public static ProgressDialog createProgressDialog(boolean canceable, String title, String msg, Context context) {
		ProgressDialog dialog = new ProgressDialog(context);
		dialog.setTitle(title);
		dialog.setMessage(msg);
		dialog.setIndeterminate(true);
		dialog.setCancelable(canceable);
		return dialog;
	}

	public static AlertDialog CreateAlertDialog2(String title, String msg, Context context) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle(title);
		dialog.setMessage(msg);
		AlertDialog alert = ((Builder) dialog).create();

		return alert;
	}

	public static AlertDialog CreateAlertDialog2(String title, String msg, String leftButton, String rightButton,
			Context context) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle(title);
		dialog.setMessage(msg);
		dialog.setPositiveButton(leftButton, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

				/* User clicked OK so do some stuff */
			}
		});

		dialog.setNegativeButton(rightButton, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {

				/* User clicked Cancel so do some stuff */
			}
		});

		AlertDialog alert = ((Builder) dialog).create();

		return alert;
	}

	// ---------------------------------------------------------------------------------
	public static Dialog CreateAlertDialog3(String title, View views, Context context) {
		LayoutInflater factory = LayoutInflater.from(context);
		// final View textEntryView =
		// factory.inflate(R.layout.alert_dialog_text_entry, null);
		return new AlertDialog.Builder(context).setTitle(title).setView(views).create();

	}

	public static Dialog CreateAlertDialog4(String title, String items[], boolean states[], Context context) {
		return new AlertDialog.Builder(context).setTitle(title)
				.setMultiChoiceItems(items, states, new DialogInterface.OnMultiChoiceClickListener() {
					public void onClick(DialogInterface dialog, int whichButton, boolean isChecked) {

						/* User clicked on a check box do some stuff */
					}
				})
				// .setPositiveButton(R.string.alert_dialog_ok, new
				// DialogInterface.OnClickListener() {
				// public void onClick(DialogInterface dialog, int whichButton)
				// {
				//
				// /* User clicked Yes so do some stuff */
				// }
				// })
				// .setNegativeButton(R.string.alert_dialog_cancel, new
				// DialogInterface.OnClickListener() {
				// public void onClick(DialogInterface dialog, int whichButton)
				// {
				//
				// /* User clicked No so do some stuff */
				// }
				// })
				.create();

	}

	public static Toast CreateToast(Context context, String msg) {
		Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		return toast;
	}
}
