package com.uttara.gtmanager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddProject extends Activity {
	private TextView dateView;
	public static final int CHOOSE_MEMBER_RESULT = 1;
	ArrayList<String >str =new ArrayList<String>();
	
 
	private int year;
	private int month;
	private int day;
 
	static final int DATE_DIALOG_ID = 999;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_project);
		dateView = (TextView) findViewById(R.id.dateView);
		
	}
	

	public void addTask(View v){
		Intent intent = new Intent(getApplicationContext(), AddTask.class);
		startActivity(intent);
	}
	@SuppressWarnings("deprecation")
	public void showDate(View v){
		showDialog(DATE_DIALOG_ID);
	}
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
		   // set date picker as current date
		   return new DatePickerDialog(this, datePickerListener, 
                         year, month,day);
		}
		return null;
	}
	private DatePickerDialog.OnDateSetListener datePickerListener 
    = new DatePickerDialog.OnDateSetListener() {

// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
			int selectedMonth, int selectedDay) {
		year = selectedYear;
		month = selectedMonth;
		day = selectedDay;
		Log.d(Config.TAG , "date "+month+" "+day+" "+year);	
		dateView.setText(day+"-"+month+"-"+year);
		// set selected date into textview
		
			
		
		
		}
		};
		public void chooseMember(View v){
			Intent intent = new Intent(this, ChooseMemberList.class);
			startActivityForResult(intent,1);
		}
	
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			
			super.onActivityResult(requestCode, resultCode, data);
			System.out.println(requestCode);
			
			String name;
			
				if(data != null){
				name = (String) data.getSerializableExtra("item");
				System.out.println(name);
				one:for(int i=0;i<str.size();i++){
					if(str.get(i).equals(name)){
						Toast.makeText(this, "Employee already selected", Toast.LENGTH_SHORT).show();;
					}else
					{
						str.add(name);
						break one;
					}
				}
				
				Log.d(Config.TAG, "String data"+str);
				}else
				{
					Log.d(Config.TAG, "do nothing");
				}
				ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
						android.R.layout.simple_spinner_item, str);
				Spinner spinner = (Spinner)findViewById(R.id.spinner1);
				/*ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
				spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);*/
				dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				spinner.setAdapter(dataAdapter);
				//dataAdapter.addAll(str);
				dataAdapter.notifyDataSetChanged();
				
			
		}
	
}
