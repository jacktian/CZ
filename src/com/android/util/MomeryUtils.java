package com.android.util;

public class MomeryUtils {

	public static boolean externalMemoryAvailable() {
		return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
	}
}