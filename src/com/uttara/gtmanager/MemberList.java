package com.uttara.gtmanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.example.android.swipedismiss.SwipeDismissListViewTouchListener;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MemberList extends ListActivity {
	private ArrayAdapter listMemberAdapter;
	private ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		pd = new ProgressDialog(MemberList.this);
		pd.setTitle("Listing");
		pd.setMessage("Please wait");
		pd.setCancelable(true);
		pd.show();

		new FetchMemberList().execute();
		super.onResume();

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		MemberBean currMember = (MemberBean) listMemberAdapter
				.getItem(position);
		Log.d("gtmanager", "current member = " + currMember);
		Intent intent = new Intent(this, MemberDetailActivity.class);
		intent.putExtra("currentMem", currMember);
		startActivity(intent);
		super.onListItemClick(l, v, position, id);
	}

	private class FetchMemberList extends
			AsyncTask<Void, Void, List<MemberBean>> {

		@Override
		protected List<MemberBean> doInBackground(Void... params) {
			HttpURLConnection con = null;
			BufferedReader br = null;
			List<MemberBean> mbl = new ArrayList<MemberBean>();
			try {

				String urlStr = new String(Config.CONFIG
						+ "/getJsonListOfEmployees");
				URL url = new URL(urlStr);
				con = (HttpURLConnection) url.openConnection();
				br = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				String response = br.readLine().toString();
				Log.d(Config.TAG, "data " + response);

				// JSONParser parser=new JSONParser();
				org.json.JSONObject object = new org.json.JSONObject(response);
				String string = (String) object.get("data");
				org.json.JSONArray arr = new org.json.JSONArray(string);
				Log.d(Config.TAG, "Data after parsing " + arr);

				/*
				 * JSONParser parser2= new JSONParser(); JSONArray
				 * array=(JSONArray) parser2.parse(string);
				 */
				// JSONArray array=(JSONArray) jsonObject.get("data");
				// Log.d(Config.TAG, "Array Data is "+array.toString());
				// JSONArray array=(JSONArray) parser.parse(string);

				for (int i = 0; i < arr.length(); i++) {
					MemberBean mb = new MemberBean();
					org.json.JSONObject object2 = (org.json.JSONObject) arr
							.get(i);
					mb.setEmail(object2.get("email").toString());
					mb.setName(object2.get("firstName").toString());
					mb.setLname(object2.get("lastName").toString());
					mb.setPhno(object2.get("phoneNumber").toString());

					mbl.add(mb);
					System.out.println(object2.get("firstName"));
					System.out.println(object2.get("email"));

					Log.d(Config.TAG, "ProjBean " + mb);
				}
				return mbl;
				// Log.d(Config.TAG, "ProjBean ");

			} catch (Exception e) {
				e.printStackTrace();
				Log.d("gtmanager",
						"errormsg of catch block of bckgrnd fetchMemberlist "
								+ e.getMessage());
			} finally {
				if (br != null) {
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
		protected void onPostExecute(List<MemberBean> mbl) {
			Activity activity = MemberList.this;
			if(mbl.size()>0){
				listMemberAdapter = new MemberListAdapter(activity, mbl);
				setListAdapter(listMemberAdapter);
				Toast.makeText(getApplicationContext(), "Loaded Member list ", Toast.LENGTH_LONG).show();
				pd.dismiss();
				ListView lv = getListView();
				SwipeDismissListViewTouchListener touchListner = new SwipeDismissListViewTouchListener(lv, new SwipeDismissListViewTouchListener.DismissCallbacks() {
					
					@Override
					public void onDismiss(ListView listView, int[] reverseSortedPositions) {
						for (int position : reverseSortedPositions) {
                            listMemberAdapter.remove(listMemberAdapter.getItem(position));
                            
                        }
						listMemberAdapter.notifyDataSetChanged();
                    
						
					}
					
					@Override
					public boolean canDismiss(int position) {
						// TODO Auto-generated method stub
						return true;
					}
				});
				lv.setOnTouchListener(touchListner);
				
			
			}else
				Toast.makeText(getApplicationContext(), "List empty", Toast.LENGTH_SHORT).show();;
			super.onPostExecute(mbl);
		
			
		}
	}

}
