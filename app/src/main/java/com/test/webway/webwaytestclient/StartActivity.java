package com.test.webway.webwaytestclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class StartActivity extends Activity {
private Button connect;
private EditText ipAddress;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.start);
	ipAddress =(EditText) findViewById(R.id.editText1);
	connect=(Button)findViewById(R.id.button1);
	connect.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String ip= ipAddress.getText().toString();
			Client.SERVERIP=ip;
			Intent intent = new Intent(StartActivity.this, MainActivity.class);
			startActivity(intent);
		}
	});
}


}
