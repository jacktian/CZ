package com.android.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.util.Log;

public class FileUtils {
	
    public static void saveFile(Bitmap pBitmap,String fileName)
    {
    	File file=new File("/sdcard/xxg_image");
    	if(!file.exists())
    	{
    		file.mkdirs();
    	}
    	String filePathName=file.getAbsolutePath()+"/"+fileName;
    	FileOutputStream fos=null;
    	try {
			fos=new FileOutputStream(filePathName);
			pBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			fos.flush();
		} catch (Exception e) {
		   e.printStackTrace();
		}finally
		{
			if(fos!=null)
			{
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
    } 
}
