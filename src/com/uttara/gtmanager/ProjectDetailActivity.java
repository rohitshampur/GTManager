package com.uttara.gtmanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ProjectDetailActivity extends Activity {
	
	private TextView projName;
	private TextView dateOfCreation;
	private ProjectBean pb;
	private String projectName;
	private ListView lv;
	private ProgressDialog pd;
	private List<String> listOfNames;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project_detail);
		projName = (TextView) findViewById(R.id.projname);
		dateOfCreation = (TextView) findViewById(R.id.projdate);
		Intent intent = getIntent();
		pb = new ProjectBean();
		pb = (ProjectBean) intent.getSerializableExtra("currentMem");
		Log.d(Config.TAG, "ProjectBean "+pb);
		projName.setText(pb.getProjectName().toString());
		dateOfCreation.setText(pb.getProj_CreatedDate().toString());
		projectName = pb.getProjectName();
		lv = (ListView) findViewById(R.id.memberNameListView);
		
		new FetchMemberList(projectName).execute();
		
		
	}
	public void addTaskOnline(View v){
		Intent intent = new Intent(this, AddTaskOnline.class);
		intent.putExtra("projectName", projectName);
		intent.putExtra("forward", true);
		startActivity(intent);
	}
	public void viewTasks(View v){
		Intent intent = new Intent(this,TaskListView.class);
		intent.putExtra("projectName", projectName);
		intent.putStringArrayListExtra("listofnames", (ArrayList<String>) listOfNames);
		startActivity(intent);
		
	}
	private class FetchMemberList extends AsyncTask<String, Void, List<String>>{
		String projName ;
		List<String> listOfNames = new ArrayList<String>();
		public FetchMemberList(String projectName) {
			this.projName = projectName;
		}

		@Override
		protected List<String> doInBackground(String... params) {
			
			HttpURLConnection con = null;
			BufferedReader br = null;
			
			try
			{
				String urlStr = new String(Config.CONFIG+"/listMembersOfProject?projName="+projName);
				URL url = new URL(urlStr); 
				con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("POST");
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String response = br.readLine().toString();
				Log.d("gtmanager","inside do in background after getting response. response = "+response.toString());
				JSONParser parser = new JSONParser();
				JSONObject obj = (JSONObject) parser.parse(response);
				Log.d("gtmanager","inside do in background after getting json object. json object = "+obj.get("listOfNames").toString());
				listOfNames = (List<String>) obj.get("listOfNames");
				return listOfNames;
			}catch(Exception e){
				e.printStackTrace();
			}
			finally{
				if(br!=null){
					try {
						br.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					con.disconnect();
				}
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(List<String> result) {
			Log.d(Config.TAG, "result = "+result);
			List<String> nameList = new ArrayList<String>();
			
			
			for(String str : result){
				nameList.add(str);
			}
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1){
			    @Override
			    public View getView(int position, View convertView, ViewGroup parent) {
			        View view = super.getView(position, convertView, parent);
			        TextView text = (TextView) view.findViewById(android.R.id.text1);
			        text.setTextColor(Color.BLACK);
			        return view;
			    }
			};
			Log.d(Config.TAG, "name list = "+nameList);
			for(String str1 :nameList){
			adapter.add(str1);
			lv.setAdapter(adapter);
			}
			super.onPostExecute(result);
		}
	}
	

	
}
