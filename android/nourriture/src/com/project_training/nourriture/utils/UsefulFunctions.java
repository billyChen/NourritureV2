package com.project_training.nourriture.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class UsefulFunctions {

	public static boolean	checkConnection(Context context) {
		ConnectivityManager	connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo	networkInfo = connectivityManager.getActiveNetworkInfo();
		
		if ( networkInfo != null && networkInfo.isConnected() )
			return true;
		return false;
	}
}
