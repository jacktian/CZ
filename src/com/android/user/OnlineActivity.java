package com.android.user;

import com.android.view.BottomBar;

import android.app.Activity;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.Window;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class OnlineActivity extends Activity {
	final Activity activity = this;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().requestFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.online_activity);
		BottomBar bottomBar = new BottomBar(this);
		WebView webView = (WebView) findViewById(R.id.webView);
		webView.getSettings().setSupportZoom(true);
		webView.loadUrl("http://twap.ccjoy.cn");

		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				if (newProgress == 100) {
				} else {

				}
			}
		});

		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			/*
			 * @Override public void onReceivedSslError(WebView view,
			 * SslErrorHandler handler, android.net.http.SslError error) {
			 * handler.proceed(); }
			 */
			@Override
			public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
				// TODO Auto-generated method stub
				handler.proceed();
				super.onReceivedSslError(view, handler, error);
			}
		});
	}
}
