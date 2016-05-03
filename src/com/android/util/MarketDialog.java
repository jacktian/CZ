package com.android.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

public class MarketDialog extends Dialog {
	public MarketDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}

	public MarketDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	protected MarketDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
	}

	Context context;

	// public MarketDialog(Context context, int theme) {
	// super(context, theme);
	// }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.market_dialog);
		// initView();

	}

	private void initView() {
		// TODO Auto-generated method stub

	}

}
