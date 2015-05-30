package com.uttara.gtmanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddTaskOnline extends Activity {

	private List<String> namelist = new ArrayList<String>();
	private List<String> emaillist = new ArrayList<String>();
	Calendar c = Calendar.getInstance();
	int cYear = c.get(Calendar.YEAR);
	int cMonth = c.get(Calendar.MONTH);
	
	int cDay = c.get(Calendar.DAY_OF_MONTH);
	static final int DATE_DIALOG_ID = 999;
	private TextView dateView;
	private String selectedDate = null;
	private Spinner mySpinner;
	private EditText nameView;
	private TextView descView;
	private String priority;
	private String memberEmail;
	private Spinner prioritySpinner;
	private TaskBean tb ;
	private String projName;
	Intent intent;
	
	List<MemberBeanParcable> mList = new ArrayList<MemberBeanParcable>();
	private TextView tv;
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_task);
		
		
		tb=new TaskBean();
		int currYear = c.get(Calendar.YEAR);
		Log.d(Config.TAG,"current year = "+currYear);
		dateView = (TextView) findViewById(R.id.dateView);
		nameView = (EditText) findViewById(R.id.taskName_fld);
		descView = (TextView) findViewById(R.id.description_fld);
		Log.d(Config.TAG, "List----->"+namelist+"email list---->"+emaillist);
		Intent intent = getIntent();
		projName = intent.getStringExtra("projectName").toString();
		
		prioritySpinner = (Spinner) findViewById(R.id.prioritySpinner1);
		prioritySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
					priority = (String) prioritySpinner.getItemAtPosition(position);
					Log.d(Config.TAG, "priority = "+priority);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		/*	ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, str){ 
			@Override 
		    public View getView(int position, View convertView, ViewGroup parent) {
	        View view = super.getView(position, convertView, parent);
	        TextView text = (TextView) view.findViewById(android.R.id.text1);
	        text.setTextColor(Color.BLACK);
	        return view;
	    }
	};
		//adapter.add(str);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				
			}
		});*/
		 tv = (TextView) findViewById(R.id.selectedMemberForTask);
		mySpinner = (Spinner) findViewById(R.id.spinner1);
		mySpinner.setAdapter(new CustomSpinnerAdapter(AddTaskOnline.this, R.id.row_list,mList));
		mySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				MemberBeanParcable mb = (MemberBeanParcable) mySpinner.getItemAtPosition(position);
				tv.setText(mb.getName().toString());
				memberEmail = mb.getEmail().toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				tv.setText("");
			}
		});
		new FetchMemberList(projName).execute();
		}
	@SuppressWarnings("deprecation")
	public void pickDate(View v){
		showDialog(DATE_DIALOG_ID);
		
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
		   // set date picker as current date
			 
			
		   return new DatePickerDialog(this, datePickerListener, 
                         cYear, cMonth,cDay);
		}
		return null;
	}
	private DatePickerDialog.OnDateSetListener datePickerListener 
    = new DatePickerDialog.OnDateSetListener() {

// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
			int selectedMonth, int selectedDay) {
		cYear = selectedYear;
		cMonth = selectedMonth;
		cDay = selectedDay;
		cMonth++;
		if(selectedYear<cYear){
			Toast.makeText(getApplicationContext(), "Please select the future date", Toast.LENGTH_LONG).show();;
		}else{
		//Log.d(Config.TAG , "date "+cMonth+" "+cDay+" "+cYear);	
		
		dateView.setText(cDay+"-"+cMonth+"-"+cYear);
		selectedDate = ""+cDay+"-"+cMonth+"-"+cYear;
		// set selected date into textview
		}
		
			
		
		
		}
		};
		public void submitTask(View v){
			
			tb.setProjectName(projName);
			tb.setPriority(priority);
			tb.setTaskName(nameView.getText().toString());
			tb.setTaskDesc(descView.getText().toString());
			tb.setEmployeeEmail(memberEmail);
			tb.setTaskCompletionDate(selectedDate);
			Log.d(Config.TAG, "taskBean "+tb);
			new AddTaskUpload().execute(tb);
			
			
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
				Log.d(Config.TAG, "result>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>. = "+result);
				List<RegBean> nameList = new ArrayList<RegBean>();
				
				tv = (TextView) findViewById(R.id.selectedMemberForTask);
				mySpinner = (Spinner) findViewById(R.id.spinner1);
				mySpinner.setAdapter(new CustomSpinnerAdapter1(AddTaskOnline.this, R.id.row_list,result));
				mySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						RegBean rb = (RegBean) mySpinner.getItemAtPosition(position);
						tv.setText(rb.getFname().toString());
						memberEmail = rb.getEmail().toString();
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub
						
					}
				});
				
				
				super.onPostExecute(result);
			}
		}
		private class AddTaskUpload extends AsyncTask<TaskBean, Void, JSONObject>{
			private HttpURLConnection con = null;
			private BufferedReader br = null;
			
			@Override
			protected JSONObject doInBackground(TaskBean... params) {
				try {
				String urlStr = new String(Config.CONFIG+"/getJsonaddTaskForProjects?projectName="+tb.getProjectName()+"&taskName="+tb.getTaskName()+"&taskDesc="+tb.getTaskDesc()+"&priority="+tb.getPriority()+"&completionDate="+tb.getTaskCompletionDate()+"&employeeEmail="+memberEmail);
				
					URL url = new URL(urlStr);
					con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("POST");
					br = new BufferedReader(new InputStreamReader(con.getInputStream()));
					String response = br.readLine();
					Log.d("gtmanager","inside do in background after getting response. response = "+response.toString());
					JSONParser parser = new JSONParser();
					JSONObject obj = (JSONObject) parser.parse(response);
					Log.d("gtmanager","inside do in background after getting json object. json object = "+obj.get("Status").toString());
					return obj;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					if(br != null){
						try {
							br.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(con!=null){
							con.disconnect();
						}
					}
				}
				return null;
			}
			
			@Override
			protected void onPostExecute(JSONObject result) {
				if(result != null){
					String status = result.get("Status").toString();
					System.out.println(status);
					if(status.equals(Config.SUCCESS)){
						Toast.makeText(getApplicationContext(),"Addded Task Successfully", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(getApplicationContext(), ProjectsList.class);
						intent.putExtra("taskName", tb.getTaskName());
						startActivity(intent);
						
					}
					else{
						Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
					}
					super.onPostExecute(result);
				}
			}
		}
			
}
		


	

