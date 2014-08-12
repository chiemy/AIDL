package com.chiemy.aidl;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;


public class AddService extends Service {
	private IBinder binder = new AddOpBinder();
	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}
	
	private class AddOpBinder extends AddOp.Stub{
		@Override
		public int addService(int a, int b) throws RemoteException {
			return add(a, b);
		}
	}
	
	public int add(int a,int b){
		return a + b;
	}

}
