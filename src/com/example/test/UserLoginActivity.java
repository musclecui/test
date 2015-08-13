package com.example.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

public class UserLoginActivity extends Activity {

	Menu menu;
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

				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				LayoutInflater inflater = (LayoutInflater)UserLoginActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View vPopupWin = inflater.inflate(R.layout.dialog_popup, null, false);
				final PopupWindow pw = new PopupWindow(vPopupWin, 400, 200, true);
				
				Button btnOk = (Button)vPopupWin.findViewById(R.id.btnOk);
				Button btnCancel = (Button)vPopupWin.findViewById(R.id.btnCancel);
				TextView tvTip = (TextView)vPopupWin.findViewById(R.id.tvTip);
				tvTip.setText("test");
				
				btnOk.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						Date dt = new Date();
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						TextView tvTip = (TextView)vPopupWin.findViewById(R.id.tvTip);
						tvTip.setText(df.format(dt));
						
					}
				});
				btnCancel.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						pw.dismiss();
					}
				});
				pw.showAtLocation(v, Gravity.CENTER, 0, 0);
				
				
//				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 设置日期格式
//				Date dt = new Date();
				
				
//				AlertDialog.Builder builder = new AlertDialog.Builder(UserLoginActivity.this);
//				builder.setTitle(R.string.app_name);
//				builder.setIcon(R.drawable.home);
//				builder.setMessage("你正在登录");
//				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//					
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						// TODO Auto-generated method stub
//						Toast.makeText(UserLoginActivity.this, "你按了确定", Toast.LENGTH_LONG).show();
//					}
//				});
//				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//					
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						// TODO Auto-generated method stub
//						Toast.makeText(UserLoginActivity.this, "你按了取消", Toast.LENGTH_LONG).show();
//					}
//				});
//				builder.create().show();
				
				
//				EditText edUserName = (EditText)findViewById(R.id.edUserName);
//				EditText edPassword = (EditText)findViewById(R.id.edPassword);
//				String userName = edUserName.getText().toString();
//				String password = edPassword.getText().toString();
//				String s = userName + password;
//				Intent intent = new Intent(UserLoginActivity.this, ToDetVehActivity.class);
//				Bundle bundle = new Bundle();
//				bundle.putString("1", "One");
//				bundle.putString("2", "Two");
//				bundle.putString("3", "Three");
//				bundle.putInt("4", 4);
//				intent.putExtras(bundle);
//				startActivity(intent);
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
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		this.menu = menu;
		addMenu(menu);
		addSubMenu(menu);

		return super.onCreateOptionsMenu(menu);	
	}
	
	private void addMenu(Menu menu) {

		menu.add("菜单1");
		menu.add("菜单2");
		menu.add("菜单3");
		menu.add("菜单4");
		menu.add("菜单5");
		menu.add("菜单6");
		menu.add("菜单7");
	}
	
	private void addSubMenu(Menu menu) {
		
	}
	
}
