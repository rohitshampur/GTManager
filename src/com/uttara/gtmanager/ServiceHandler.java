package com.uttara.gtmanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.R.string;
import android.util.Log;

public class ServiceHandler
{

	//public static string TAG="ServiceHandler";
	 String response;

	public ServiceHandler()
	{
	}

	public static String httpPost(String postURL, List<NameValuePair> params) 
	{
		
		try {
			String data;
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(postURL);


			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params,HTTP.UTF_8);
			post.setEntity(ent);
			HttpResponse responsePOST = client.execute(post);
			HttpEntity resEntity = responsePOST.getEntity();
			if (resEntity != null)
			{
				data=EntityUtils.toString(resEntity);
				Log.d(Config.TAG,"RESPONSE is from Entity " + data);
				Log.d(Config.TAG, "data is returning "+data);
				return data;
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		Log.d(Config.TAG, "returning the result"+null);
		return null;
	}
	
	
	public static String httpGet(String url) {
		try {
			String data = "";
			HttpClient client = new DefaultHttpClient();
			// String getURL = "http://www.google.com";
			HttpGet get = new HttpGet(url);
			HttpResponse responseGet = client.execute(get);
			HttpEntity resEntityGet = responseGet.getEntity();

			if (resEntityGet != null) {
				data = EntityUtils.toString(resEntityGet);
				Log.i("GET RESPONSE is from Servre is ", data);
			}
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			Log.d("URL", "Login failed");
			return null;
		}

	}

	public String makeServiceCall(String url) {
		String str = null;
		StringBuilder builder = new StringBuilder();

		try {

			BufferedReader in = null;

			HttpClient httpclient = new DefaultHttpClient();
			HttpGet request = new HttpGet(url);

			HttpResponse response = httpclient.execute(request);

			if (response.getStatusLine().getStatusCode() == 200) {
				in = new BufferedReader(new InputStreamReader(response
						.getEntity().getContent()));
				while ((str = in.readLine()) != null)
					builder.append(str);
			} else {
				Log.e(Config.class.toString(), "Failed to download file");
			}

			// code from
			// http://stackoverflow.com/a/20321876/3090120
			// http://www.vogella.com/tutorials/AndroidJSON/article.html
			// http://www.androidhive.info/2012/01/android-json-parsing-tutorial/
		} catch (ClientProtocolException e) 
		{
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (Exception e) {

			Log.e("log_tag", "Error in http connection" + e.toString());
		}

		return builder.toString();
	}

}