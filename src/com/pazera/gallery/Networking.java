package com.pazera.gallery;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;


public class Networking {
	public static boolean checkInternet(Context context) {
		// TODO Auto-generated constructor stub
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);        
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		   
		if (networkInfo == null || !networkInfo.isConnected()) {
		    return false;
		} else {
		    return true;
		}
	}

}
