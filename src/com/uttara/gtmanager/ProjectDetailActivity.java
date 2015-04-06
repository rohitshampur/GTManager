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
		Log.d(Config.TAG, "ProjectBean)))))))))))))) "+pb);
		projName.setText(pb.getProjectName().toString());
		dateOfCreation.setText(pb.getProj_CreatedDate().toString());
		projectName = pb.getProjectName();
		lv = (ListView) findViewById(R.id.memberNameListView);
		pd = new ProgressDialog(this);
		pd.setTitle("Loading Project details");
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.setMessage("Please wait");
		pd.setIndeterminate(true);
		pd.show();
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
	
	private class FetchMemberList extends AsyncTask<String, Void, List<RegBean>>{
		String projName ;
		List<RegBean> listOfNames = new ArrayList<RegBean>();
		public FetchMemberList(String projectName) {
			this.projName = projectName;
		}

		@Override
		protected List<RegBean> doInBackground(String... params) {
			
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
				JSONObject jObj = (JSONObject) parser.parse(response);
				Log.d("gtmanager","inside do in background after getting json object. json object = "+jObj.get("listOfNames"));
				String str =  jObj.get("listOfNames").toString();
				JSONObject obj1 = (JSONObject) parser.parse(str);
				Log.d(Config.TAG, "000000000000000000"+obj1.get("beanList"));
				String str1 = (String)obj1.get("beanList").toString();
				JSONArray jArray = (JSONArray) parser.parse(str1);
				List<RegBean> rbList = new ArrayList<RegBean>();
				for (int i = 0; i < jArray.size(); i++) {
					RegBean rb = new RegBean();
					JSONObject obj = (JSONObject) jArray.get(i);
					rb.setFname((String) obj.get("firstName"));
					rb.setEmail((String) obj.get("email"));
					rbList.add(rb);
				}
				Log.d(Config.TAG, "rblist........."+rbList);
				/*List<TaskBean> taskBeans= new ArrayList<TaskBean>();
				TaskBean taskBean;
				JSONArray arrayTasks=(JSONArray) parser.parse((String) obj.get("listOfNames"));*/
				//listOfNames = (List<RegBean>) obj.get("listOfNames");
				return rbList;
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
		protected void onPostExecute(List<RegBean> result) {
			Log.d(Config.TAG, "result = "+result);
			List<RegBean> nameList = new ArrayList<RegBean>();
			
			List<String> sList = new ArrayList<String>();
			for(RegBean rb : result){
				sList.add(rb.getFname().toString());
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
			Log.d(Config.TAG, "name list = "+sList);
			for(String str1 :sList){
			adapter.add(str1);
			lv.setAdapter(adapter);
			pd.dismiss();
			}
			super.onPostExecute(result);
		}
	}
	

	
}
