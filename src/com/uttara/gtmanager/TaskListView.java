package com.uttara.gtmanager;

import java.io.BufferedReader;
import java.io.IOException;
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


public class TaskListView extends ListActivity {
	private String projectName;
	private ArrayAdapter<?> listTaskAdapter;
	List<TaskBean> taskBeans;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_list_view);
		Intent intent = getIntent();
		projectName = intent.getStringExtra("projectName");
		
		taskBeans= new ArrayList<TaskBean>();
		new FetchTaskOnline().execute();
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		TaskBean currTask = (TaskBean) listTaskAdapter.getItem(position);
		Log.d("gtmanager", "current task = "+currTask);
		Intent intent = new Intent(this, TaskDetail.class);
		intent.putExtra("currentTask", currTask);
		intent.putExtra("projectName",projectName);
		intent.putExtra("fromtTaskList", true);
		startActivity(intent);
		super.onListItemClick(l, v, position, id);
		
	}
	private class FetchTaskOnline extends AsyncTask<Void, Void, List<TaskBean>>{

		@Override
		protected List<TaskBean> doInBackground(Void... params) {
			HttpURLConnection con = null;
			BufferedReader br = null;
			try{
				String urlStr = new String(Config.CONFIG+"/getJsonlistTask?projectName="+projectName);
				URL url = new URL(urlStr); 
				con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("POST");
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String response = br.readLine().toString();
				Log.d("gtmanager","inside do in background after getting response. response = "+response.toString());
				/*JSONParser parser = new JSONParser();
				JSONObject obj = (JSONObject) parser.parse(response);
				List<TaskBean> taskBeans= new ArrayList<TaskBean>();
				String list = (String) obj.get("data");
				JSONArray jArray = new JSONArray()*/
				JSONParser parser= new JSONParser();
				JSONObject jsonObject=(JSONObject) parser.parse(response);
				String data=(String) jsonObject.get("data");
				JSONArray jArray=(JSONArray) parser.parse(data);
				TaskBean taskbean ;
				for(int i=0;i<jArray.size();i++)
				{
					taskbean= new TaskBean();
					JSONObject object=(JSONObject) jArray.get(i);
				
					
					taskbean.setTask_sl_no(Integer.parseInt(""+(Long) object.get("task_sl_no")));
					taskbean.setTaskName((String) object.get("taskName"));
					taskbean.setTaskDesc((String) object.get("taskDesc"));
					taskbean.setTaskCreatedDate((String) object.get("taskCreatedDate"));
					taskbean.setPriority(""+(Long) object.get("priority"));
					taskbean.setTaskCompletionDate((String) object.get("taskCompletionDate"));
					taskbean.setStatus((String) object.get("taskStatus"));
					Log.d(Config.TAG, "task bean "+taskbean+" i value "+i);
					//taskbean= new TaskBean();
					System.out.println("Adding bean to array list "+taskBeans.add(taskbean)+" bean is "+taskbean);;
					
				}
				Log.d(Config.TAG, "taskBeans`````````````````````````    "+taskBeans);
				
				Log.d("gtmanager","inside do in background after getting json object. json object = "+jArray);
				return taskBeans;
				
			}catch(Exception e){
				e.printStackTrace();
			}finally{
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
	protected void onPostExecute(List<TaskBean> result) {
		Log.d(Config.TAG, "result = "+result);
		super.onPostExecute(result);
		Log.d(Config.TAG, "List of Original beans are @@@@@@@@@@@@@ "+taskBeans);
		Activity activity = TaskListView.this;
		if(result.size()>0){
			listTaskAdapter = new TaskListAdapter(activity, result);
			setListAdapter(listTaskAdapter);
			Toast.makeText(getApplicationContext(), "Loaded Task list ", Toast.LENGTH_LONG).show();
			
			
		}else{
			Toast.makeText(getApplicationContext(), "List empty", Toast.LENGTH_SHORT).show();
		}
		
	}	
	}
}
