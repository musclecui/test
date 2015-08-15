package com.example.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class UserLoginActivity extends Activity {

	Button btnLogin;
	Menu menu;
	private int menuItemId = Menu.FIRST;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_login);

		btnLogin = (Button) findViewById(R.id.btnLogin);

		btnLogin.setOnClickListener(new OnClickListener() {

//			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				SimpleDateFormat df = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");

				LayoutInflater inflater = (LayoutInflater) UserLoginActivity.this
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View vPopupWin = inflater.inflate(R.layout.dialog_popup,
						null, false);
				final PopupWindow pw = new PopupWindow(vPopupWin, 400, 200,
						true);

				Button btnOk = (Button) vPopupWin.findViewById(R.id.btnOk);
				Button btnCancel = (Button) vPopupWin
						.findViewById(R.id.btnCancel);
				TextView tvTip = (TextView) vPopupWin.findViewById(R.id.tvTip);
				tvTip.setText("test");

				btnOk.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						Date dt = new Date();
						SimpleDateFormat df = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						TextView tvTip = (TextView) vPopupWin
								.findViewById(R.id.tvTip);
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

				// SimpleDateFormat df = new
				// SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 设置日期格式
				// Date dt = new Date();

				// AlertDialog.Builder builder = new
				// AlertDialog.Builder(UserLoginActivity.this);
				// builder.setTitle(R.string.app_name);
				// builder.setIcon(R.drawable.home);
				// builder.setMessage("你正在登录");
				// builder.setPositiveButton("确定", new
				// DialogInterface.OnClickListener() {
				//
				// @Override
				// public void onClick(DialogInterface dialog, int which) {
				// // TODO Auto-generated method stub
				// Toast.makeText(UserLoginActivity.this, "你按了确定",
				// Toast.LENGTH_LONG).show();
				// }
				// });
				// builder.setNegativeButton("取消", new
				// DialogInterface.OnClickListener() {
				//
				// @Override
				// public void onClick(DialogInterface dialog, int which) {
				// // TODO Auto-generated method stub
				// Toast.makeText(UserLoginActivity.this, "你按了取消",
				// Toast.LENGTH_LONG).show();
				// }
				// });
				// builder.create().show();

				// EditText edUserName =
				// (EditText)findViewById(R.id.edUserName);
				// EditText edPassword =
				// (EditText)findViewById(R.id.edPassword);
				// String userName = edUserName.getText().toString();
				// String password = edPassword.getText().toString();
				// String s = userName + password;
				// Intent intent = new Intent(UserLoginActivity.this,
				// ToDetVehActivity.class);
				// Bundle bundle = new Bundle();
				// bundle.putString("1", "One");
				// bundle.putString("2", "Two");
				// bundle.putString("3", "Three");
				// bundle.putInt("4", 4);
				// intent.putExtras(bundle);
				// startActivity(intent);
				// finish();

				// int i = 1;
				// float f = 2.2f;
				// String s = "hello -";
				// s += df.format(dt);
				// s += "-";
				// s += String.valueOf(true);
				// s += "-";
				// s += String.valueOf(i);
				// s += "-";
				// s += String.valueOf(f);
				// Toast tst = Toast.makeText(UserLoginActivity.this, s,
				// Toast.LENGTH_LONG);
				// tst.show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		this.menu = menu;
		addMenu(menu);
//		addSubMenu(menu);

		return super.onCreateOptionsMenu(menu);
	}

	private void addMenu(Menu menu) {

		MenuItem addMenuItem = menu.add(1, menuItemId++, 1, "设置");
		addMenuItem.setIcon(R.drawable.delete);
//		// addMenuItem.setOnMenuItemClickListener(UserLoginActivity.this);
//		MenuItem deleteMenuItem = menu.add(1, menuItemId++, 2, "删除");
//		deleteMenuItem.setIcon(R.drawable.close_delete);
//		// deleteMenuItem.setOnMenuItemClickListener(this);
//		MenuItem menuItem1 = menu.add(1, menuItemId++, 3, "菜单1");
//		// menuItem1.setOnMenuItemClickListener(this);
//		MenuItem menuItem2 = menu.add(1, menuItemId++, 4, "菜单2");
	}

	private void addSubMenu(Menu menu) {

		// 添加子菜单
		SubMenu fileSubMenu = menu.addSubMenu(1, menuItemId++, 5, "文件");

		fileSubMenu.setHeaderIcon(R.drawable.check);
		// 子菜单不支持图像
		MenuItem newMenuItem = fileSubMenu.add(1, menuItemId++, 1, "新建");
		newMenuItem.setCheckable(true);
		newMenuItem.setChecked(true);
		MenuItem openMenuItem = fileSubMenu.add(2, menuItemId++, 2, "打开");
		MenuItem exitMenuItem = fileSubMenu.add(2, menuItemId++, 3, "退出");
		exitMenuItem.setChecked(true);
		fileSubMenu.setGroupCheckable(2, true, true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {


		Log.d("你点击了", String.valueOf(item.getItemId()));
//		new AlertDialog.Builder(UserLoginActivity.this).setMessage("你点击了“"+item.getTitle()+"”").show();
		Toast.makeText(this, "你点击了“"+item.getTitle()+"”", Toast.LENGTH_SHORT).show();
		
		switch (item.getItemId()) {
		case 1:
			settingMenuItemSelected();
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		case 5:
			break;	
		default:
			return super.onOptionsItemSelected(item);
		}

		return true;
	}
	
	private void settingMenuItemSelected() {

		 startActivity(new Intent(UserLoginActivity.this, SettingActivity.class));
	}

}
