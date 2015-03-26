package com.uttara.gtmanager;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;

import android.util.Log;

public class ServiceHandler {
	
	
	public void postData(String url,JSONObject obj) {
	    // Create a new HttpClient and Post Header

	    HttpParams myParams = new BasicHttpParams();
	    HttpConnectionParams.setConnectionTimeout(myParams, 10000);
	    HttpConnectionParams.setSoTimeout(myParams, 10000);
	    HttpClient httpclient = new DefaultHttpClient(myParams );
	    String json=obj.toString();

	    try {

	        HttpPost httppost = new HttpPost(url.toString());
	        httppost.setHeader("Content-type", "application/json");

	        StringEntity se = new StringEntity(obj.toString()); 
	        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
	        httppost.setEntity(se); 

	        HttpResponse response = httpclient.execute(httppost);
	        String temp = EntityUtils.toString(response.getEntity());
	        Log.i("tag", temp);


	    } catch (ClientProtocolException e) {

	    } catch (IOException e) {
	    }
	}
}
