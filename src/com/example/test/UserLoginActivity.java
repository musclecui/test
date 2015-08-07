package com.example.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class UserLoginActivity extends Activity {

	Button btnLogin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_login);
		
		btnLogin = (Button)findViewById(R.id.btnLogin);
		
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 设置日期格式
				Date dt = new Date();
				
				EditText edUserName = (EditText)findViewById(R.id.edUserName);
				EditText edPassword = (EditText)findViewById(R.id.edPassword);
				String userName = edUserName.getText().toString();
				String password = edPassword.getText().toString();
				
				String s = userName + password;
				Intent intent = new Intent(UserLoginActivity.this, ToDetVehActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("1", "One");
				bundle.putString("2", "Two");
				bundle.putString("3", "Three");
				bundle.putInt("4", 4);
				intent.putExtras(bundle);
				startActivity(intent);
				
//				finish();
				
//				int i = 1;
//				float f = 2.2f;
//				String s = "hello -";
//				s += df.format(dt);
//				s += "-";
//				s += String.valueOf(true);
//				s += "-";
//				s += String.valueOf(i);
//				s += "-";
//				s += String.valueOf(f);
//				Toast tst = Toast.makeText(UserLoginActivity.this, s, Toast.LENGTH_LONG);
//				tst.show();
			}
		});
	}
}
