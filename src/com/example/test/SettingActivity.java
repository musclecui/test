package com.example.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SettingActivity extends Activity {
	
	Button btnSave;
	Button btnCancel;
	EditText edIp;
	EditText edPort;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		btnSave = (Button)findViewById(R.id.btnSave);
		btnCancel = (Button)findViewById(R.id.btnCancel);
		edIp = (EditText)findViewById(R.id.edIp);
		edPort = (EditText)findViewById(R.id.edPort);

		btnSave.setOnClickListener(new ClickEvent());
		btnCancel.setOnClickListener(new ClickEvent());
	}

	class ClickEvent implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			switch (v.getId()) {
			case R.id.btnSave:
				finish();
				break;
			case R.id.btnCancel:
				finish();
				break;
			default:
				break;
			}
		}
	}
}
