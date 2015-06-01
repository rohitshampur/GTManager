package com.uttara.gtmanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddTask extends Activity {

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
	boolean b;
	Intent intent ;
	private ProgressDialog pdialog;
	List<MemberBeanParcable> mList = new ArrayList<MemberBeanParcable>();
	private TextView tv;
	boolean stat = false;
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_task);
		
		
		
		int currYear = c.get(Calendar.YEAR);
		Log.d(Config.TAG,"current year = "+currYear);
		dateView = (TextView) findViewById(R.id.dateView);
		nameView = (EditText) findViewById(R.id.taskName_fld);
		descView = (TextView) findViewById(R.id.description_fld);
		pdialog = new ProgressDialog(this);
		
		intent = getIntent();
	
		mList = intent.getParcelableArrayListExtra("MemberBeanList");
		Log.d(Config.TAG, "MemberbeanList"+mList);
		
		Log.d(Config.TAG, "List----->"+namelist+"email list---->"+emaillist);
		projName = intent.getStringExtra("projName").toString();
		
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
		mySpinner.setAdapter(new CustomSpinnerAdapter(AddTask.this, R.id.row_list,mList));
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
		
		String date;
		if(cDay<10||cMonth<10){
			date = "0"+cDay + "/0" + cMonth + "/" + cYear;
		}else{
		date = cDay + "/" + cMonth + "/" + cYear;
		}
		Log.d(Config.TAG, "Date = "+date);
		if(Config.DateChecker(date).equals(Config.SUCCESS)){
		dateView.setText("Selected date : "+date);
		dateView.setError(null);
		selectedDate = date;
		}else{
			dateView.setError("Invalid date ");
			Toast.makeText(getApplicationContext(), "Inavlid date", Toast.LENGTH_LONG).show();
		}
		}
		};
		public void submitTask(View v){
			tb = new TaskBean();
			tb.setProjectName(projName);
			tb.setPriority(priority);
			tb.setTaskName(nameView.getText().toString());
			nameView.setOnFocusChangeListener(new OnFocusChangeListener() {
				
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					// TODO Auto-generated method stub
					
				}
			});
			tb.setTaskDesc(descView.getText().toString());
			tb.setEmployeeEmail(memberEmail);
			tb.setTaskCompletionDate(selectedDate);
			Log.d(Config.TAG, "VAlidate bean"+tb.validate());
			if(tb.validate()==""){
			Log.d(Config.TAG, "taskBean "+tb);
			
			new CheckTaskName().execute();
			
			  
			//new AddTaskOnline().execute(tb);
			
			}
		}
			private class CheckTaskName extends AsyncTask<Void, Void, JSONObject>{
			
			@Override
				protected void onPreExecute() {
				pdialog = new ProgressDialog(AddTask.this);
				pdialog.setTitle("Checking project name");
				pdialog.setMessage("Loading...");
				pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				pdialog.show();
					super.onPreExecute();
				}
			@Override
			protected JSONObject doInBackground(Void... params) {
				HttpURLConnection con = null;
				BufferedReader br = null;
				
				
				try {
					
					Log.d(Config.TAG,"Taskbean!!!!!!!!!!------"+tb);
					String taskN = tb.getTaskName();
					taskN = taskN.replaceAll(" ", "%40");
					
				String urlStr = new String(Config.CONFIG+"/checkTaskName?taskName="+taskN);
				
					URL url = new URL(urlStr);
					con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("POST");
					br = new BufferedReader(new InputStreamReader(con.getInputStream()));
					String response = br.readLine();
					Log.d("gtmanager","inside do in background after getting response. response = "+response.toString());
					JSONParser parser = new JSONParser();
					JSONObject obj = (JSONObject) parser.parse(response);
					Log.d("gtmanager","inside do in background after getting json object. json object = "+obj.get("status").toString());
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
					String status = result.get("status").toString();
					System.out.println(status);
					if(status.equals(Config.SUCCESS)){
						/*Toast.makeText(getApplicationContext(),"Addded Task Successfully", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent();
						intent.putExtra("taskName", tb.getTaskName());
						intent.putExtra("taskBean", tb);
						setResult(2, intent);
						finish();
						pdialog.dismiss();*/
						pdialog.dismiss();
						Intent intent = new Intent();
						intent.putExtra("taskName", tb.getTaskName());
						intent.putExtra("taskBean", tb);
						setResult(2, intent);
						finish();
						
						
					}
					else{
				
						pdialog.dismiss();
						Toast.makeText(getApplicationContext(),"Task name already exists", Toast.LENGTH_SHORT).show();
					}
					super.onPostExecute(result);
				}
			}
		}
}
			
		
					
						
		


