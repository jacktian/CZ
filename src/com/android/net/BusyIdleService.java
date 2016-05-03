package com.android.net;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.android.common.BusyIdleStatusBean;

import android.util.Log;
import android.util.Xml;

public class BusyIdleService {

	public List<BusyIdleStatusBean> getBusyIdleStatus()
			throws MalformedURLException, IOException, XmlPullParserException {
		String path = "http://218.93.39.237:8082/TravelWebApi/api/DevicesStatus/?status=128";
		HttpURLConnection con = (HttpURLConnection) new URL(path).openConnection();
		con.setConnectTimeout(1500);
		con.setRequestMethod("GET");
		con.setUseCaches(true);
		con.addRequestProperty("Content-Type", "text/xml; charset=utf-8");
		int i = con.getResponseCode();
		if (i == 200) {
			InputStream in = con.getInputStream();
			String str = inputStreamString(in);

			if (str != null && str.startsWith("\ufeff")) {
				str = str.substring(1);
			}
			InputStream inin = new ByteArrayInputStream(str.getBytes());
			Log.e("20140118", "lost in  = " + inin);
			return parseXML(inin);
		}
		return null;
	}

	public String inputStreamString(InputStream in) throws IOException {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[409600];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n));
		}
		return out.toString();
	}

	public List<BusyIdleStatusBean> parseXML(InputStream in) throws XmlPullParserException, IOException {
		List<BusyIdleStatusBean> statues = null;
		BusyIdleStatusBean statue = null;
		XmlPullParser pullParser = Xml.newPullParser();
		pullParser.setInput(in, "UTF-8");
		int event = pullParser.getEventType();
		Log.e("20140118", "event = " + event);
		statues = new ArrayList<BusyIdleStatusBean>();
		while (event != XmlPullParser.END_DOCUMENT) {
			switch (event) {
			/*
			 * case XmlPullParser.START_DOCUMENT: Log.e("20131212",
			 * "START_DOCUMENTSTART_DOCUMENT");
			 * 
			 * break;
			 */
			case XmlPullParser.START_TAG:
				if ("P_DevicesStatus".equals(pullParser.getName())) {
					statue = new BusyIdleStatusBean();
				}
				if ("subdistrictname".equals(pullParser.getName())) {
					statue.setSubdistrictname(pullParser.nextText().trim());
					// statue.setTelName(pullParser.nextText());
				}
				if ("busyidlestatus".equals(pullParser.getName())) {
					statue.setBusyidlestatus(pullParser.nextText());
					// statue.setTelNums(pullParser.nextText());
				}
				break;
			case XmlPullParser.END_TAG:
				if ("P_DevicesStatus".equals(pullParser.getName())) {
					statues.add(statue);
					statue = null;
				}
				break;
			}
			event = pullParser.next();
		}
		return statues;
	}

}
