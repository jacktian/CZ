package com.android.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.android.common.HomeNews;

import android.util.Log;
import android.util.Xml;

public class HomeNewsService {
	public List<HomeNews> getHomeNews() throws MalformedURLException, IOException, XmlPullParserException {
		String path = "http://218.93.39.237:8082/TravelWebApi/api/News/?pagenum=10";
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
			Log.e("20131210", "inininininin = " + inin);
			return parseXML(inin);
		}
		return null;
	}

	public String inputStreamString(InputStream in) throws IOException {
		// StringBuffer out = new StringBuffer();
		// byte[] b = new byte[409600];
		// for (int n; (n = in.read(b)) != -1;) {
		// out.append(new String(b, 0, n));
		// }
		// return out.toString();
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int count = -1;
		while ((count = in.read(data, 0, 1024)) != -1)
			outStream.write(data, 0, count);
		data = null;
		return new String(outStream.toByteArray(), "UTF-8");
	}

	public List<HomeNews> parseXML(InputStream in) throws XmlPullParserException, IOException {
		List<HomeNews> news = null;
		HomeNews homeNews = null;
		XmlPullParser pullParser = Xml.newPullParser();
		pullParser.setInput(in, "UTF-8");
		int event = pullParser.getEventType();
		Log.e("20140114", "event = " + event);
		String tag = pullParser.getName();
		news = new ArrayList<HomeNews>();
		while (event != XmlPullParser.END_DOCUMENT) {
			switch (event) {
			/*
			 * case XmlPullParser.START_DOCUMENT: Log.e("20131212",
			 * "START_DOCUMENTSTART_DOCUMENT");
			 * 
			 * break;
			 */
			case XmlPullParser.START_TAG:
				if ("P_News".equals(pullParser.getName())) {
					// int id = new Integer(pullParser.getAttributeValue(0));
					// Log.e("20131210", "pullParser.getAttributeValue(0) = " +
					// pullParser.getAttributeValue(0));
					homeNews = new HomeNews();
					// homeNews.setId(id);
				}

				if ("description".equals(pullParser.getName())) {
					homeNews.setDescription(pullParser.nextText());
					// Log.e("20131210", "pullParser.getAttributeValue(0) = " +
					// pullParser.nextText());
				}
				if ("id".equals(pullParser.getName())) {
					homeNews.setNum(pullParser.nextText());
				}
				if ("imagefile".equals(pullParser.getName())) {
					homeNews.setImagefile(pullParser.nextText());
				}
				break;
			case XmlPullParser.END_TAG:
				if ("P_News".equals(pullParser.getName())) {
					news.add(homeNews);
					homeNews = null;
				}
				break;
			}
			event = pullParser.next();
		}
		return news;
	}
}
