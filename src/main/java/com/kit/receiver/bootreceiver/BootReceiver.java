package com.kit.receiver.bootreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.kit.app.core.task.DoSomeThing;
import com.kit.utils.AppUtils;

public class BootReceiver extends BroadcastReceiver implements IBootService {

	public Context mContext;

	@Override
	public void onReceive(Context context, Intent intent) {
		mContext = context;
		startBootService();
	}

	@Override
	public boolean startBootService() {
		return true;
	}

}