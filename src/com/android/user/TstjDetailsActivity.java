package com.android.user;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class TstjDetailsActivity extends Activity {
	private int imgids[] = { R.drawable.details_adlxf, R.drawable.details_chpp, R.drawable.details_jfw,
			R.drawable.details_lklx, R.drawable.details_rlw, R.drawable.details_xfyz, R.drawable.details_yxz };
	private int contents[] = { R.string.details_adlxf, R.string.details_chpp, R.string.details_jfw,
			R.string.details_lklx, R.string.details_rlw, R.string.details_xfyz, R.string.details_yxz };
	private ImageView img;
	private int which;
	private TextView detail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tstj_detail_activity);
		img = (ImageView) findViewById(R.id.vp);
		detail = (TextView) findViewById(R.id.detail);
		which = getIntent().getIntExtra("which", 0);

		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		img.setImageResource(imgids[which]);
		detail.setText(contents[which]);
	}

}
