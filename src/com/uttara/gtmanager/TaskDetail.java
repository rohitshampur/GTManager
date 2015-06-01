package com.uttara.gtmanager;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TaskDetail extends Activity {
	private TextView taskName;
	private TextView taskDate;
	private TextView taskDes;
	private TextView projName;
	private TextView status;
	private TaskBean tb;
	private Button updateButton;
	private String projectName = null; 
	ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_detail);
		taskName = (TextView) findViewById(R.id.taskNameTxtvw);
		taskDes = (TextView) findViewById(R.id.taskDesctxtvw);
		projName = (TextView) findViewById(R.id.project_name_txtvw);
		taskDate = (TextView) findViewById(R.id.project_date_txtvw);
		status = (TextView) findViewById(R.id.statusTxtvw);
		updateButton = (Button) findViewById(R.id.btnEditTask);
		Intent intent = getIntent();
		projectName = intent.getStringExtra("projectName");
		if(projectName.contains("")){
		projectName = projectName.replaceAll("%40", " ");
		}
		boolean b = intent.getBooleanExtra("fromtTaskList", false);
		if(b){
			updateButton.setVisibility(View.GONE);
		}
		tb = new TaskBean();
		tb = (TaskBean) intent.getSerializableExtra("currentTask");
		taskName.setText("Task name : "+tb.getTaskName());
		taskDes.setText("Description : "+tb.getTaskDesc());
		projName.setText("Project name : "+projectName);
		taskDate.setText("Task Date : "+tb.getTaskCreatedDate());
		status.setText("Status : "+tb.getStatus());
		pd = new ProgressDialog(this);
	}

	public void updateStatus(View v) {
		Dialog d = onCreateDialogSingleChoice();
		d.show();
	}

	public Dialog onCreateDialogSingleChoice() {

		// Initialize the Alert Dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// Source of the data in the DIalog
		final String[] arr = new String[] { "Completed", "Pending",
				"In progress", "Cancelled" };

		final List<String> str1 = new ArrayList<String>();

		// Set the dialog title
		builder.setTitle("UpdateStatus")
				// Specify the list array, the items to be selected by default
				// (null for none),
				// and the listener through which to receive callbacks when
				// items are selected
				.setSingleChoiceItems(arr, 1,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Log.d(Config.TAG,
										"insdie onclick dialog interface which pos"
												+ which);
								for (String str : arr) {
									if(which>=0){
									str = arr[which];
									str1.add(str);
									}else{
										str = arr[2];
										str1.add(str);
									}
								}

							}
						})

				// Set the action buttons
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						Log.d(Config.TAG,
								"insdie onclick dialog interface which pos okay button"
										+ id);
						if(id==-1){
						String str = str1.get(0);
						Log.d(Config.TAG, "@@@@@@@@@@@@@@"+str);
						new UpdateStatus(str).execute();
						}else{
							String str = "Pending";
							new UpdateStatus(str).execute();
						}
					}
				})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								Log.d(Config.TAG,
										"insdie onclick dialog interface which pos cancel button"
												+ id);
								
								dialog.cancel();
							}
						});

		return builder.create();
	}

	private class UpdateStatus extends AsyncTask<Void, Void, String> {
		String status;
		
		public UpdateStatus(String str) {
			this.status = str;
		}
		@Override
		protected void onPreExecute() {
			/*pd.setTitle("Updating status");
			pd.setMessage("Please wait....");
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pd.setCancelable(false);
			pd.show();*/
			super.onPreExecute();
		}
		@Override
		protected String doInBackground(Void... params) {
			Log.d(Config.TAG, "asynctask of update status " + status);

			tb.setStatus(status);
			Log.d(Config.TAG,
					"asynctask of update status task bean "
							+ tb.getJsonString());
			String urlStr = new String(Config.CONFIG + "/editTask");
			List<NameValuePair> params1 = new ArrayList<NameValuePair>();
			params1.add(new BasicNameValuePair("data", tb.getJsonString()));
			String response = ServiceHandler.httpPost(urlStr, params1);
			Log.d(Config.TAG, "Response from server" + response);

			return response;

		}
		@Override
		protected void onPostExecute(String result) {
			
			super.onPostExecute(result);
			if(result.equals(Config.SUCCESS)){
				Toast.makeText(getApplicationContext(), "Status Updated successfully", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(getApplicationContext(), ManagerMenuActivity.class);
				startActivity(intent);
			}
			else{
				Toast.makeText(getApplicationContext(), "Something went wrong, please try again", Toast.LENGTH_LONG).show();
				//pd.dismiss();
			}
		}
	}
}
