package com.chiemy.aidlclient;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.chiemy.aidl.AddOp;

public class MainActivity extends Activity {
	private AddOp binder;
	private ServiceConnection conn;
	private EditText a,b;
	private TextView result;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		conn = new AddOpServiceConnection();
		Intent intent = new Intent("add");
		bindService(intent, conn, BIND_AUTO_CREATE);
		
		a = (EditText) findViewById(R.id.a);
		b = (EditText) findViewById(R.id.b);
		result = (TextView) findViewById(R.id.result);
		
		findViewById(R.id.start).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(binder == null){
					result.setText("没有AIDL服务");
					return;
				}
				String s1 = a.getText().toString();
				String s2 = b.getText().toString();
				int a1 = Integer.parseInt(s1);
				int a2 = Integer.parseInt(s2);
				try {
					int r = binder.addService(a1, a2);
					result.setText("a+b=" + r);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private class AddOpServiceConnection implements ServiceConnection{
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			binder = AddOp.Stub.asInterface(service);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			binder = null;
		}
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbindService(conn);
	}

}
