package com.uttara.gtmanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

public class ChooseMemberList extends ListActivity {
	private ProgressDialog pd;

	private ArrayAdapter<?> listMemberAdapter;
	private int i;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_member_list);
		i = 0;
		
		//lv = (ListView) findViewById(R.id.listView1);
		
	}

	@Override
	protected void onResume() {
		pd = new ProgressDialog(ChooseMemberList.this);
		pd.setTitle("Listing");
		pd.setMessage("Please wait");
		pd.setCancelable(true);
		pd.show();
		i++;
		new FetchMemberList().execute();
		super.onResume();
		
		
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		
		super.onListItemClick(l, v, position, id);
		
		MemberBeanParcable currMember = (MemberBeanParcable) listMemberAdapter.getItem(position);
		Intent intent = new Intent();
		Bundle b = new Bundle();
		b.putParcelable("item1", currMember);
		intent.putExtra("item1", b);
		intent.putExtra("count", i);
		intent.putExtra("item", currMember.getName());
		
		setResult(1, intent);
			finish();
			
         /*else
         {
        	Intent intent = new Intent(getApplicationContext(), AddProject.class);
        	startActivity(intent);
         }*/
		
       
	}
	private class FetchMemberList extends AsyncTask<Void, Void, List<MemberBeanParcable>>{

		@Override
		protected List<MemberBeanParcable> doInBackground(Void... params) {
			HttpURLConnection con = null;
			BufferedReader br = null;
			List<MemberBeanParcable> mbl = new ArrayList<MemberBeanParcable>();
			try{
				
				String urlStr = new String(Config.CONFIG+"/getJsonListOfEmployees");
				URL url = new URL(urlStr);
				con = (HttpURLConnection) url.openConnection();
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String response = br.readLine().toString();
				Log.d(Config.TAG, "data "+response);
				
				//JSONParser parser=new JSONParser();
				org.json.JSONObject object= new org.json.JSONObject(response);
				String string=(String) object.get("data");
				org.json.JSONArray arr = new org.json.JSONArray(string);
				Log.d(Config.TAG, "Data after parsing "+arr);
				
				/*JSONParser parser2= new JSONParser();
				JSONArray array=(JSONArray) parser2.parse(string);*/
				//JSONArray array=(JSONArray) jsonObject.get("data");
				//Log.d(Config.TAG, "Array Data is "+array.toString());
				//JSONArray array=(JSONArray) parser.parse(string);
				
				for(int i=0;i<arr.length();i++)
				{	 
					MemberBeanParcable mb = new MemberBeanParcable();
					org.json.JSONObject object2=(org.json.JSONObject) arr.get(i);
					mb.setEmail(object2.get("email").toString());
					mb.setName(object2.get("firstName").toString());
					mb.setLname(object2.get("lastName").toString());
					mb.setPhno(object2.get("phoneNumber").toString());
					
					mbl.add(mb);
					System.out.println(object2.get("firstName"));
					System.out.println(object2.get("email"));
					
					Log.d(Config.TAG, "ProjBean "+mb);
				}
				return mbl;
			//	 Log.d(Config.TAG, "ProjBean ");
			
				
			}catch(Exception e){
				e.printStackTrace();
				Log.d("gtmanager", "errormsg of catch block of bckgrnd fetchMemberlist "+e.getMessage());
			}
			finally{
				if(br != null){
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
		protected void onPostExecute(List<MemberBeanParcable> mbl) {
			
		
			List<String> str = new ArrayList<>();
			for(MemberBeanParcable b  : mbl){
				str.add(b.getName().toString());
				str.add(b.getEmail().toString());
			}
			
			
					
			
			Log.d(Config.TAG, " str "+str);
			if(mbl.size()>0){
				Activity activity = ChooseMemberList.this; 
				listMemberAdapter = new ChooseMemberListAdapter(activity, mbl);
				/*ArrayAdapter<String> arr = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, str){
				    @Override
				    public View getView(int position, View convertView, ViewGroup parent) {
				        View view = super.getView(position, convertView, parent);
				        TextView text = (TextView) view.findViewById(android.R.id.text1);
				        text.setTextColor(Color.BLACK);
				        return view;
				    }
				};*/ 
				setListAdapter(listMemberAdapter);
				Toast.makeText(getApplicationContext(), "Loaded Member list ", Toast.LENGTH_SHORT).show();
				
			}else
				Toast.makeText(getApplicationContext(), "List empty", Toast.LENGTH_SHORT).show();
			super.onPostExecute(mbl);
			
			/*setOnItemClickListener(new OnItemClickListener() {
				 
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                   int position, long id) {
                  
                 // ListView Clicked item index
                 int itemPosition     = position;
                 
                 // ListView Clicked item value
                 String  itemValue    = (String) lv.getItemAtPosition(position);
                s SharedPreferences pref = getSharedPreferences("gtmanager", MODE_PRIVATE);
                 Editor edit = pref.edit();
                 edit.putString("itemValue", itemValue);
                 edit.commit();
                 selecteditem = itemValue;
                 Log.d("gtmanager","inside on itemclick = "+itemValue );
                 if(itemValue != null){   
                 Intent intent = new Intent();
 				intent.putExtra("item", itemValue);
 				setResult(1, intent);
 				finish();
                 }else
                 {
                	Intent intent = new Intent(getApplicationContext(), AddProject.class);
                	startActivity(intent);
                 }
 				
 				
                  // Show Alert 
                  Toast.makeText(getApplicationContext(),
                    "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                    .show();
                  
                }
  
           });
			
		}*/pd.dismiss();
		
			}
		
		
	}
	
}
