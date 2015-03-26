package com.uttara.gtmanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnCancelListener;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	
	private EditText ed_fname;
	private EditText ed_lname;
	private EditText ed_email;
	private EditText ed_phnum;
	private EditText ed_pass;
	private EditText ed_rpass;
	
	private String fname,lname,email,phnum,pass,rpass;
	private RegBean rb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		ed_fname = (EditText) findViewById(R.id.fname_fld);
		ed_lname = (EditText) findViewById(R.id.lname_fld);
		ed_email = (EditText) findViewById(R.id.email_fld);
		ed_phnum = (EditText) findViewById(R.id.phnum_fld);
		ed_pass = (EditText) findViewById(R.id.password_fld);
		ed_rpass = (EditText) findViewById(R.id.rpassword_fld);
		
		
	}
	public void register(View v){
	
		
		fname = ed_fname.getText().toString();
		lname = ed_lname.getText().toString();
		email = ed_email.getText().toString();
		phnum = ed_phnum.getText().toString();
		pass = ed_pass.getText().toString();
		rpass = ed_rpass.getText().toString();
		
		Log.d(Config.TAG, "data"+fname+" "+lname+" "+email+" "+pass+" "+rpass);
		rb = new RegBean();
		rb.setFname(fname);
		rb.setLname(lname);
		rb.setEmail(email);
		rb.setPhnum(phnum);
		rb.setPass(pass);
		rb.setRpass(rpass);
		
		String msg = rb.validate();
		if(msg == ""){
			boolean b = checkInternet();
			
			if(b == true){
				SharedPreferences pref = getSharedPreferences("gtmanager", MODE_PRIVATE);
				Editor edit = pref.edit();
				edit.putString("email", email);
				edit.commit();
			new OnlineRegister().execute(rb);
			}else{
				Toast.makeText(getApplicationContext(), "Please connect to the internet", Toast.LENGTH_LONG).show();
			}
		}else{
			Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
		}
		}
	private class OnlineRegister extends AsyncTask<RegBean, Void, JSONObject>{
		private ProgressDialog pDialog;
		@Override
		protected void onPreExecute() {
			// show progress dialog
			pDialog = new ProgressDialog(RegisterActivity.this);
			pDialog.setTitle("Registering");
			pDialog.setMessage("wait...");
			pDialog.setCancelable(true);
			pDialog.setOnCancelListener(new OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					OnlineRegister.this.cancel(true);
					
				}
			});
			pDialog.show();
		
		}
		@Override
		protected JSONObject doInBackground(RegBean... params) {
			HttpURLConnection con = null;
			BufferedReader br = null;
			try{
				fname = rb.getFname();
				lname = rb.getLname();
				email = rb.getEmail();
				phnum = rb.getPhnum();
				pass = rb.getPass();
				Log.d(Config.TAG, "data"+fname+" "+lname+" "+email+" "+phnum+" "+pass);
				String urlStr = new String(Config.CONFIG+"/registerEmployee?firstName="+fname+"&lastName="+lname+"&email="+email+"&phoneNumber="+phnum+"&password="+pass+"&repeatPassword="+pass);
				URL url = new URL(urlStr); 
				con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("POST");
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String response = br.readLine().toString();
				Log.d("gtmanager","inside do in background after getting response. response = "+response.toString());
				JSONParser parser = new JSONParser();
				JSONObject obj = (JSONObject) parser.parse(response);
				Log.d("gtmanager","inside do in background after getting json object. json object = "+obj.get("status").toString());
				return obj;
			}catch(Exception e ){
				e.printStackTrace();
				Log.d("gtmanager", "errormsg of catch block of bckgrnd Register "+e.getMessage());
			}finally{
				if(br !=null){
					try {
						br.close();
						con.disconnect();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			return null;
		}
		@Override
		protected void onPostExecute(JSONObject result) {
			if(result != null){
				String status = result.get("status").toString();
				
			}
			 
			super.onPostExecute(result);
		}
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
}
