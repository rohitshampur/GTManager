package com.uttara.gtmanager;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


public class Config {
	
	
	public static final String CONFIG = "http://192.168.153.107:8090/GroupBasedTaskManager";
	public static final String SUCCESS = "Success";
	public static final String TAG = "gtmanager";
	public static final String ROLE_MANAGER = "Manager";
	public static final String ROLE_EMPL = "Emp";
	public static int DOWNLOAD_DIALOG_PROGRESS = 0;
	
	public static boolean checkInternet(Context c) {
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
	
	   public  void showStatusDialog(String title, String message, String posButtonText,Context context)
   	{
   		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
   		
   		alertDialogBuilder
   		.setTitle(title)
   		.setMessage(message)
   		.setCancelable(false)
   		.setPositiveButton(posButtonText, null);
   		AlertDialog alertDialog = alertDialogBuilder.create();
   		alertDialog.show();
   	
   	}
	   
	   public static String DateChecker(String date) {
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			String presentDate = format.format(new Date()).toString();
			Date d1 = null;
			Date d2 = null;
			try {
				d1 = format.parse(date);
				d2 = format.parse(presentDate);
				// in milliseconds

				long diff = d2.getTime() - d1.getTime();

				long diffDays = diff / (24 * 60 * 60 * 1000);
				System.out.print(diffDays + " Days");
				Log.d(TAG, "diff dates"+diffDays);
				// 100 years = 36,467 Days

				int day = (int) diffDays;
				if (day <=0) {
					return "Success";
					
				} else {
					return " Date is accepted upto this date only";
				}

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
				return " Dear user Kindly provide Date in this format : DD-MM-YYYY";
			}
		}
	
	
	
}
