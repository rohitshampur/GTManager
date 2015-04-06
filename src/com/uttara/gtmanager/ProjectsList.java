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
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ProjectsList extends ListActivity {
	private ArrayAdapter<?> listProjAdapter;
	private ProgressDialog pd;
	ListView lv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_projects_list);
		lv = (ListView) findViewById(android.R.id.list);
	
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				/*ProjectBean currProj = (ProjectBean) listProjAdapter.getItem(position);
				Log.d("gtmanager", "current project = "+currProj);
				Intent intent = new Intent(getApplicationContext(), ProjectsList.class);
				intent.putExtra("currentMem", currProj);
				startActivity(intent);*/
				return false;
			}
		});
	}
	@Override
	protected void onResume() {
		pd = new ProgressDialog(ProjectsList.this);
		pd.setTitle("Listing");
		pd.setMessage("Please wait");
		pd.setCancelable(true);
		pd.show();
	
		new FetchProjectsList().execute();
		super.onResume();
		
		
	}
	
	
	public void addProject(View v){
		Intent intent = new Intent(getApplicationContext(), AddProject.class);
		startActivity(intent);
	}
	
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		ProjectBean currProj = (ProjectBean) listProjAdapter.getItem(position);
		Log.d("gtmanager", "current project = "+currProj);
		Intent intent = new Intent(this, ProjectDetailActivity.class);
		intent.putExtra("currentMem", currProj);
		startActivity(intent);
		super.onListItemClick(l, v, position, id);
		
	}
	
	private class FetchProjectsList extends AsyncTask<Void, Void, List<ProjectBean>>{
	
		
		
		@Override
		protected List<ProjectBean> doInBackground(Void... params) {
			HttpURLConnection con = null;
			BufferedReader br = null;
			List<ProjectBean> pbl = new ArrayList<ProjectBean>();
			try{
				SharedPreferences pref = getSharedPreferences("gtmanager", MODE_PRIVATE);
				String email = pref.getString("email", "blank");
				Log.d(Config.TAG, "pref email "+email);
				String urlStr = new String(Config.CONFIG+"/getJsonListOfProjects?managerEmailId="+email);
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
					ProjectBean pb = new ProjectBean();
					org.json.JSONObject object2=(org.json.JSONObject) arr.get(i);
					pb.setProjectName(object2.get("projectName").toString());
					pb.setProj_CreatedDate(object2.get("proj_CreatedDate").toString());
					pb.setTypeOfProject(object2.getString("typeOfProject").toString());
					pb.setDescription((String) object2.get("description"));
					pb.setProjCompletionDate((String) object2.get("proj_CompletionDate"));
					pbl.add(pb);
					System.out.println(object2.get("project_sl_no"));
					System.out.println(object2.get("projectName"));
					System.out.println(object2.get("proj_CreatedDate"));
					System.out.println(object2.get("description"));
					Log.d(Config.TAG, "ProjBean!!!!!!!!!!!!!! "+pb);
				}
				return pbl;
			//	 Log.d(Config.TAG, "ProjBean ");
			
				
			}catch(Exception e){
				e.printStackTrace();
				Log.d("gtmanager", "errormsg of catch block of bckgrnd fetchprojectlist "+e.getMessage());
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
		protected void onPostExecute(List<ProjectBean> pbl) {
			Activity activity = ProjectsList.this;
			if(pbl.size()>0){
				listProjAdapter = new ProjectListAdapter(activity, pbl);
				setListAdapter(listProjAdapter);
				Toast.makeText(getApplicationContext(), "Loaded Project list ", Toast.LENGTH_SHORT).show();
				pd.dismiss();
			}else
				Toast.makeText(getApplicationContext(), "List empty", Toast.LENGTH_LONG).show();
			pd.dismiss();
			super.onPostExecute(pbl);
			
		}
		
	}
	
}
