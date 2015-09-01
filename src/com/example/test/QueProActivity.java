package com.example.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cyx.lib.ConverUtil;
import com.example.test.model.ProInfo;
import com.example.test.webservice.WebService;
import com.example.test.webservice.WsErr;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class QueProActivity extends Activity {

	private static final String MOD_NAME = "��ѯ��Ʒ";
	EditText etProNum;
	Button btnScan;
	Button btnQue;
	Button btnClr;
	ListView lvProInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_que_pro);

		etProNum = (EditText) findViewById(R.id.etProNum);
		btnScan = (Button) findViewById(R.id.btnScan);
		btnQue = (Button) findViewById(R.id.btnQuery);
		btnClr = (Button) findViewById(R.id.btnClear);
		lvProInfo = (ListView) findViewById(R.id.lvProInfo);

		setProInfo(null);

		btnScan.setOnClickListener(new ClickEvent());
		btnQue.setOnClickListener(new ClickEvent());
		btnClr.setOnClickListener(new ClickEvent());
	}

	class ClickEvent implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btnScan:
				onClick_Scan();
				break;
			case R.id.btnQuery:
				onClick_Query();
				break;
			case R.id.btnClear:
				onClick_Clear();
				break;
			default:
				break;
			}
		}
	}

	// ���ò�Ʒ��Ϣ
	void setProInfo(ProInfo proInfo) {

		if (null == proInfo) {
			proInfo = new ProInfo();
		}

		List<Map<String, String>> data = new ArrayList<Map<String, String>>();

		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("title", "��Ʒ���");
		map1.put("context", ConverUtil.StringRdNull(proInfo.proNum));
		data.add(map1);

		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("title", "��Ʒ����");
		map2.put("context", ConverUtil.StringRdNull(proInfo.proName));
		data.add(map2);

		Map<String, String> map3 = new HashMap<String, String>();
		map3.put("title", "��Ʒ�ͺ�");
		map3.put("context", ConverUtil.StringRdNull(proInfo.proModel));
		data.add(map3);

		SimpleAdapter adapter = new SimpleAdapter(this, data,
				android.R.layout.simple_list_item_2, new String[] { "title",
						"context" }, new int[] { android.R.id.text1,
						android.R.id.text2 });
		lvProInfo.setAdapter(adapter);
	}

	void onClick_Scan() {
		// ��ɨ�����ɨ����������ά��
		Intent openCameraIntent = new Intent(QueProActivity.this,
				CaptureActivity.class);
		startActivityForResult(openCameraIntent, 0);
	}

	void onClick_Query() {
		String proNum = etProNum.getText().toString();
		proNum.trim();
		if (proNum.equals("")) {

			new AlertDialog.Builder(this).setTitle(MOD_NAME)
					.setMessage("������Ҫ��ѯ�Ĳ�Ʒ���").setPositiveButton("ȷ��", null)
					.show();
			etProNum.requestFocus();
			return;
		}

		queryProInfo(proNum);
	}

	void onClick_Clear() {
		etProNum.setText("");
		setProInfo(null);
	}

	void queryProInfo(String proNum) {
		ProInfo proInfo = new ProInfo();
		WsErr err = new WsErr();
		WebService.queryProduct(proNum, proInfo, err);
		if (err.errCode.equals(WsErr.ERR_NO)) {
			if (null != proInfo) {
				setProInfo(proInfo);
			} else {
				onClick_Clear();

				final String msg = "û�в�Ʒ���:" + proNum + "����Ϣ";
				new AlertDialog.Builder(this).setTitle(MOD_NAME)
						.setMessage(msg).setPositiveButton("ȷ��", null).show();
			}

		} else {
			new AlertDialog.Builder(this).setTitle(MOD_NAME)
					.setMessage(err.errMsg).setPositiveButton("ȷ��", null)
					.show();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// ����ɨ�������ڽ�������ʾ��
		if (resultCode == RESULT_OK) {
			Bundle bundle = data.getExtras();
			String scanResult = bundle.getString("result");
			etProNum.setText(scanResult);
			onClick_Query();
		}
	}
}
