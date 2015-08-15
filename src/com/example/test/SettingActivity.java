package com.example.test;

import com.cyx.lib.ShaPreOpe;
import com.example.test.R.string;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingActivity extends Activity {
	
	String modName = "设置"; // 模块名
	Button btnSave;
	Button btnCancel;
	EditText edIp;
	EditText edPort;
	
	ShaPreOpe shaPreOpe;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		btnSave = (Button)findViewById(R.id.btnSave);
		btnCancel = (Button)findViewById(R.id.btnCancel);
		edIp = (EditText)findViewById(R.id.edIp);
		edPort = (EditText) findViewById(R.id.edPort);

		shaPreOpe = new ShaPreOpe(this);

		edIp.setText(shaPreOpe.read("ip", ""));
		edPort.setText(shaPreOpe.read("port", ""));

		btnSave.setOnClickListener(new ClickEvent());
		btnCancel.setOnClickListener(new ClickEvent());
	}

	class ClickEvent implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			switch (v.getId()) {
			case R.id.btnSave:
				saveSetting();
				break;
			case R.id.btnCancel:
				finish();
				break;
			default:
				break;
			}
		}
	}
	
	private void saveSetting() {
		
		String ip = edIp.getText().toString();
		ip.trim();
		if (ip.equals("")) {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					SettingActivity.this);
			builder.setTitle(modName);
			builder.setIcon(R.drawable.home);
			builder.setMessage("请输入IP");
			builder.setPositiveButton("确定", null);
			builder.create().show();

			edIp.setFocusable(true);
			edIp.requestFocus();
			return;
		}
		String port = edPort.getText().toString();
		port.trim();
		if (port.equals("")) {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					SettingActivity.this);
			builder.setTitle(modName);
			builder.setIcon(R.drawable.home);
			builder.setMessage("请输入端口");
			builder.setPositiveButton("确定", null);
			builder.create().show();

			edPort.setFocusable(true);
			edPort.requestFocus();
			return;
		}
		final int intPort = Integer.parseInt(port);
		final int minPort = 1;
		final int maxPort = 65535;
		if (intPort<minPort || intPort>maxPort){
			String msg = "端口只能是" + String.valueOf(minPort) + "~" + String.valueOf(maxPort);
			AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
			builder.setTitle(modName);
			builder.setIcon(R.drawable.home);
			builder.setMessage(msg);
			builder.setPositiveButton("确定", null);
			builder.create().show();
			
			edPort.setFocusable(true);
			edPort.requestFocus();
			return;
		}
		
		shaPreOpe.write("ip", ip);
		shaPreOpe.write("port", port);
		
		finish();
	}
}
