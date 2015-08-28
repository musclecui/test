package com.example.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ToDetVehActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todet_veh);

		// Bundle bundle = getIntent().getExtras();
		// String s1 = bundle.getString("1");
		// String s2 = bundle.getString("2");
		// String s3 = bundle.getString("3");
		// int i4 = bundle.getInt("4");
		// String s = s1 + s2 + s3 + String.valueOf(i4);
		// EditText ed = (EditText)findViewById(R.id.edIp);
		// ed.setText(s);
		// Log.i("接收到", s);

		String s = "当前登录用户：" + GloVar.curUser.userName + ","
				+ GloVar.curUser.realName;
		EditText ed = (EditText) findViewById(R.id.edIp);
		 ed.setText(s);

		Button btnClose = (Button) findViewById(R.id.btnClose);
		btnClose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}
