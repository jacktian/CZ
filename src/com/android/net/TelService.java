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

import com.android.common.TelNum;

import android.util.Log;
import android.util.Xml;

public class TelService {
	public List<TelNum> getTelNum() throws MalformedURLException, IOException, XmlPullParserException {
		String path = "http://218.93.39.237:8082/TravelWebApi/api/Telphone/";
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

	public List<TelNum> parseXML(InputStream in) throws XmlPullParserException, IOException {
		List<TelNum> tels = null;
		TelNum telNums = null;
		XmlPullParser pullParser = Xml.newPullParser();
		pullParser.setInput(in, "UTF-8");
		int event = pullParser.getEventType();
		Log.e("20140118", "event = " + event);
		tels = new ArrayList<TelNum>();
		while (event != XmlPullParser.END_DOCUMENT) {
			switch (event) {
			/*
			 * case XmlPullParser.START_DOCUMENT: Log.e("20131212",
			 * "START_DOCUMENTSTART_DOCUMENT");
			 * 
			 * break;
			 */
			case XmlPullParser.START_TAG:
				if ("P_Telphone".equals(pullParser.getName())) {
					// int id = new Integer(pullParser.getAttributeValue(0));
					// Log.e("20131210", "pullParser.getAttributeValue(0) = " +
					// pullParser.getAttributeValue(0));
					telNums = new TelNum();
					// homeNews.setId(id);
				}
				if ("telName".equals(pullParser.getName())) {
					telNums.setTelName(pullParser.nextText());
					// Log.e("20131210", "pullParser.getAttributeValue(0) = " +
					// pullParser.nextText());
				}
				if ("telNums".equals(pullParser.getName())) {
					telNums.setTelNums(pullParser.nextText());
					// Log.e("20131210", "pullParser.getAttributeValue(0) = " +
					// pullParser.nextText());
				}
				break;
			case XmlPullParser.END_TAG:
				if ("P_Telphone".equals(pullParser.getName())) {
					tels.add(telNums);
					telNums = null;
				}
				break;
			}
			event = pullParser.next();
		}
		return tels;
	}

}
