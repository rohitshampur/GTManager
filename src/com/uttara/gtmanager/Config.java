package com.uttara.gtmanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class Config {
	
	
	public static final String CONFIG = "http://192.168.1.4:9080/GroupBasedTaskManager";
	public static final String SUCCESS = "Success";
	public static final String TAG = "gtmanager";
	public static final String ROLE_MANAGER = "Manager";
	public static final String ROLE_EMPL = "Emp";
	public static int DOWNLOAD_DIALOG_PROGRESS = 0;
	
	private boolean checkInternet(Context c) {
	    boolean haveConnectedWifi = false;
	    boolean haveConnectedMobile = false;

	    ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo[] netInfo = cm.getAllNetworkInfo();
	    for (NetworkInfo ni : netInfo) {
	        if (ni.getTypeName().equalsIgnoreCase("WIFI"))
	            if (ni.isConnected())
	                haveConnectedWifi = true;
	        if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
	            if (ni.isConnected())
	                haveConnectedMobile = true;
	    }
	    return haveConnectedWifi || haveConnectedMobile;
	}
}
