package com.uttara.gtmanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputFilter.LengthFilter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private EditText ed_email;
	private EditText ed_pass;
	private String email;
	private String pass;
	LogBean lb;
	boolean isCon;
	private ProgressDialog pDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
public void login(View v){
	ed_email = (EditText) findViewById(R.id.email_fld);
	ed_pass = (EditText) findViewById(R.id.password_fld);
	
	email = ed_email.getText().toString();
	pass = ed_pass.getText().toString();
	Log.d("gtmanager", "email = "+email+ " Pass = "+pass);
	lb = new LogBean();
	lb.setEmail(email);
	lb.setPass(pass);
	String msg = lb.validate();
	if (msg != ""){
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}
	else
		isCon = checkInternet();
	System.out.println("is connected to internt "+isCon);
	if(isCon){
	if(msg==""){
		SharedPreferences pref = getSharedPreferences("gtmanager", MODE_PRIVATE);
		Editor edit = pref.edit();
		edit.putString("email", email);
		edit.commit();
		ed_email.setText(email);
		new OnlineLogin().execute(lb);
	}
	}else{
		Toast.makeText(getApplicationContext(), "Please connect to the internet",Toast.LENGTH_LONG).show();
	}
}

public void register(View v){
	
	Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
	startActivity(intent);

}

private boolean checkInternet() {
    boolean haveConnectedWifi = false;
    boolean haveConnectedMobile = false;

    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
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

private class OnlineLogin extends AsyncTask<LogBean, Void, JSONObject>{
	
	@Override
	protected void onPreExecute() {
		// show progress dialog
		pDialog = new ProgressDialog(MainActivity.this);
		pDialog.setTitle("Logging in");
		pDialog.setMessage("wait...");
		pDialog.setCancelable(true);
		pDialog.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				OnlineLogin.this.cancel(true);
				
			}
		});
		pDialog.show();
		
	
	}
	
	@Override
	protected JSONObject doInBackground(LogBean... params) {
		HttpURLConnection con = null;
		BufferedReader br = null;
		try{
			String email = lb.getEmail();
			String pass = lb.getPass();
			Log.d("gtmanager", "Email = "+email+" Password = "+pass);
			String urlstr = new String(Config.CONFIG+"/jsonLogin?email="+email+"&pass="+pass);
			URL url = new URL(urlstr);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String response = br.readLine();
			Log.d("gtmanager","inside do in background after getting response. response = "+response.toString());
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(response);
			Log.d("gtmanager","inside do in background after getting json object. json object = "+obj.get("status").toString());
			return obj;
		}catch(Exception e){
			Log.d("gtmanager", "errormsg of catch block of bckgrnd Login "+e.getMessage());
			e.printStackTrace();
		}finally{
			try {
				if(br !=null)
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			con.disconnect();
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(JSONObject result) {
		if(result !=null){
			Log.d("gtmanager", "result from Login"+result.toString());
			String res = (String) result.get("status").toString();
			System.out.println("result of get"+res);
			Log.d("gtmanager", "result from Login "+result.get("status").toString());
			// check if the role is manager or employee and show the menu respectively
			
				
				if(res.equals(Config.ROLE_MANAGER)){
				Toast.makeText(getApplicationContext(),"Login Successfull!",Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(getApplicationContext(), ManagerMenuActivity.class);
				startActivity(intent);
				finish();
				}else 
					if(res.equals(Config.ROLE_EMPL)){
						Toast.makeText(getApplicationContext(),"Login Successfull!",Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(getApplicationContext(), EmplMenuActivity.class);
						startActivity(intent);
						finish();
				}else
					Toast.makeText(getApplicationContext(), res, Toast.LENGTH_SHORT).show();
				
			}
		
		super.onPostExecute(result);
		pDialog.dismiss();
	}
	
}
}
