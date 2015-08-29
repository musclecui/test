package com.example.test;

import com.example.test.model.ProInfo;
import com.example.test.webservice.WebService;
import com.example.test.webservice.WsErr;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class QueProActivity extends Activity {

	private static final String MOD_NAME = "查询产品";
	EditText edProNum;
	Button btnQue;
	Button btnClr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_que_pro);
		
		edProNum = (EditText)findViewById(R.id.edProNum);
		btnQue = (Button)findViewById(R.id.btnQuery);
		btnClr = (Button)findViewById(R.id.btnClear);
		
		btnQue.setOnClickListener(new ClickEvent());
		btnClr.setOnClickListener(new ClickEvent());
	}
	
	class ClickEvent implements OnClickListener {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btnQuery:
				Query();
				break;
			case R.id.btnClear:
				Clear();
				break;
			default:

			}
		}
	}
	
	void Query() {
		String proNum = edProNum.getText().toString();
		proNum.trim();
		if (proNum.equals("")) {
			
			new AlertDialog.Builder(this)
			.setTitle(MOD_NAME).setMessage("请输入要查询的产品编号")
			.setPositiveButton("确定", null).show();
			edProNum.requestFocus();
			return;
		}
		
		ProInfo proInfo = new ProInfo();
		WsErr err = new WsErr();
		WebService.queryProduct(proNum, proInfo, err);
		if (err.errCode.equals(WsErr.ERR_NO)) {
			Log.d("haha", proInfo.proName);
		}
	}
	
	void Clear() {
		
	}
}
