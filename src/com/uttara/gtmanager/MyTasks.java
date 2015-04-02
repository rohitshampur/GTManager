package com.uttara.gtmanager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MyTasks extends ListActivity {
	private String email;
	private ArrayAdapter<?> listTaskAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_tasks);
		Intent intent = getIntent();
		email = intent.getStringExtra("email");
		new FetchMyTasks().execute();
		
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		TaskBean currTask = (TaskBean) listTaskAdapter.getItem(position);
		Log.d("gtmanager", "current project = "+currTask);
		Intent intent = new Intent(this, TaskDetail.class);
		intent.putExtra("currentTask", currTask);
		startActivity(intent);
		super.onListItemClick(l, v, position, id);
		
	}
	private class FetchMyTasks extends AsyncTask<Void, Void, List<TaskBean>>{

		@Override
		protected List<TaskBean> doInBackground(Void... params) {
			HttpURLConnection con = null;
			BufferedReader br = null;
			try{
				String urlStr = new String(Config.CONFIG+"/taskForEmployee?email="+email);
				URL url = new URL(urlStr); 
				con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("POST");
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String response = br.readLine().toString();
				Log.d("gtmanager","inside do in background after getting response. response = "+response.toString());
				JSONParser parser= new JSONParser();
				/*JSONObject jsonObject=(JSONObject) parser.parse(response);
				String data=(String) jsonObject.get("data");*/
				JSONArray jArray=(JSONArray) parser.parse(response);
				List<TaskBean> taskBeans= new ArrayList<TaskBean>();
				TaskBean tb;
				for(int i=0;i<jArray.size();i++)
				{
					JSONObject object=(JSONObject) jArray.get(i);
					
					tb= new TaskBean();
					tb.setTask_sl_no(Integer.parseInt(""+(Long) object.get("task_sl_no")));
					tb.setTaskName((String) object.get("taskName"));
					tb.setTaskDesc((String) object.get("taskDesc"));
					tb.setTaskCreatedDate((String) object.get("taskCreatedDate"));
					tb.setPriority(""+(Long) object.get("priority"));
					tb.setTaskCompletionDate((String) object.get("taskCompletionDate"));
					tb.setStatus((String) object.get("taskStatus"));
					tb.setProjectName((String) object.get("projectName"));
					taskBeans.add(tb);
					
				}
				Log.d(Config.TAG, "taskBeans  "+taskBeans);
				return taskBeans;
			}catch(Exception e){
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPostExecute(List<TaskBean> result) {
			Log.d(Config.TAG, "result = "+result);
			
			Activity activity = MyTasks.this;
			if(result.size()>0){
				listTaskAdapter = new TaskListAdapter(activity, result);
				setListAdapter(listTaskAdapter);
				Toast.makeText(getApplicationContext(), "Loaded Task list ", Toast.LENGTH_LONG).show();
				
			}else{
				Toast.makeText(getApplicationContext(), "List empty", Toast.LENGTH_SHORT).show();
			}
			super.onPostExecute(result);
		}	
		
	}
}
