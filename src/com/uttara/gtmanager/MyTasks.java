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
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
	private ArrayAdapter listTaskAdapter;
	ListView lv;
	private ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_tasks);
		pd = new ProgressDialog(getApplicationContext());

		Intent intent = getIntent();
		email = intent.getStringExtra("email");
		new FetchMyTasks().execute();
		/*lv = (ListView) findViewById(android.R.id.list);

		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				TaskBean currTask = (TaskBean) listTaskAdapter
						.getItem(position);

				Dialog d = showDailog(currTask);
				d.show();
				
				 * ProjectBean currProj = (ProjectBean)
				 * listProjAdapter.getItem(position); Log.d("gtmanager",
				 * "current project = "+currProj); Intent intent = new
				 * Intent(getApplicationContext(), ProjectsList.class);
				 * intent.putExtra("currentMem", currProj);
				 * startActivity(intent);
				 
				Toast.makeText(getApplicationContext(), "Long clicked",
						Toast.LENGTH_SHORT).show();
				;
				return true;
			}
		});*/
	}

	public Dialog showDailog(TaskBean tb) {

		final int task_slno = tb.getTask_sl_no();
		AlertDialog.Builder builder = new AlertDialog.Builder(MyTasks.this);
		builder.setTitle("Delete task");
		builder.setMessage("Do you want to delete task?");

		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Toast.makeText(getApplicationContext(), "clicked yes",
				// Toast.LENGTH_LONG).show();
				new DeleteTask(task_slno).execute();
			}
		});

		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});

		builder.setCancelable(false);
		return builder.create();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		TaskBean currTask = (TaskBean) listTaskAdapter.getItem(position);
		Log.d("gtmanager", "current project = " + currTask);
		Intent intent = new Intent(this, TaskDetail.class);
		intent.putExtra("currentTask", currTask);
		intent.putExtra("projectName", currTask.getProjectName());
		startActivity(intent);
		super.onListItemClick(l, v, position, id);

	}

	private class DeleteTask extends AsyncTask<Void, Void, String> {
		int taskslno;

		public DeleteTask(int task_slno) {

			this.taskslno = task_slno;
		}

		@Override
		protected String doInBackground(Void... params) {
			HttpURLConnection con = null;
			BufferedReader br = null;
			try {
				String urlStr = new String(Config.CONFIG
						+ "/deleteTask?task_sl_no=" + taskslno);

				URL url = new URL(urlStr);
				con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("POST");
				br = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				String response = br.readLine();
				Log.d(Config.TAG, "Response string@@@@@@@@@@@@@@@ " + response);
				return response;
				/*
				 * JSONObject obj; JSONParser parser = new JSONParser(); obj =
				 * (JSONObject) parser.parse(response); String res = (String)
				 * obj.get("status"); Log.d(Config.TAG,
				 * "Response string after parsing@@@@@@@@@@@@@@@ " + res);
				 * return res;
				 */
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (con != null) {
						con.disconnect();
					}
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result.equals(Config.SUCCESS)) {
				Toast.makeText(getApplicationContext(),
						"Deleted task successfully", Toast.LENGTH_LONG).show();
				/*Intent intent = new Intent(getApplicationContext(),
						ManagerMenuActivity.class);
				startActivity(intent)*/;
			} else {
				Toast.makeText(getApplicationContext(),
						"Something went wrong, please try again",
						Toast.LENGTH_LONG).show();
				pd.dismiss();
			}
		}

	}

	private class FetchMyTasks extends AsyncTask<Void, Void, List<TaskBean>> {

		@Override
		protected List<TaskBean> doInBackground(Void... params) {
			HttpURLConnection con = null;
			BufferedReader br = null;
			try {
				String urlStr = new String(Config.CONFIG
						+ "/taskForEmployee?email=" + email);
				URL url = new URL(urlStr);
				con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("POST");
				br = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				String response = br.readLine().toString();
				Log.d("gtmanager",
						"inside do in background after getting response. response = "
								+ response.toString());
				JSONParser parser = new JSONParser();
				/*
				 * JSONObject jsonObject=(JSONObject) parser.parse(response);
				 * String data=(String) jsonObject.get("data");
				 */
				JSONArray jArray = (JSONArray) parser.parse(response);
				List<TaskBean> taskBeans = new ArrayList<TaskBean>();
				TaskBean tb;
				for (int i = 0; i < jArray.size(); i++) {
					JSONObject object = (JSONObject) jArray.get(i);

					tb = new TaskBean();
					tb.setTask_sl_no(Integer.parseInt(""
							+ (Long) object.get("task_sl_no")));
					tb.setTaskName((String) object.get("taskName"));
					tb.setTaskDesc((String) object.get("taskDesc"));
					tb.setTaskCreatedDate((String) object
							.get("taskCreatedDate"));
					tb.setPriority("" + (Long) object.get("priority"));
					tb.setTaskCompletionDate((String) object
							.get("taskCompletionDate"));
					tb.setStatus((String) object.get("taskStatus"));
					tb.setProjectName((String) object.get("projectName"));
					taskBeans.add(tb);

				}
				Log.d(Config.TAG, "taskBeans  " + taskBeans);
				return taskBeans;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<TaskBean> result) {
			Log.d(Config.TAG, "result = " + result);

			Activity activity = MyTasks.this;
			if (result.size() > 0) {
				listTaskAdapter = new TaskListAdapter(activity, result);
				setListAdapter(listTaskAdapter);
				Toast.makeText(getApplicationContext(), "Loaded Task list ",
						Toast.LENGTH_LONG).show();
				
				ListView lv = getListView();
				SwipeDismissListViewTouchListener touchListner = new SwipeDismissListViewTouchListener(lv, new SwipeDismissListViewTouchListener.DismissCallbacks() {
					
					@Override
					public void onDismiss(ListView listView, int[] reverseSortedPositions) {
						if(reverseSortedPositions.length>0){
						for (int position : reverseSortedPositions) {
							TaskBean tb = (TaskBean) listTaskAdapter.getItem(position);
							listTaskAdapter.remove(listTaskAdapter.getItem(position));
                            Log.d(Config.TAG, "Member bean in swipe "+tb);
                            new DeleteTask(tb.getTask_sl_no()).execute();
                            
                        }
						listTaskAdapter.notifyDataSetChanged();
						}else{
							Toast.makeText(getApplicationContext(), "List empty",
									Toast.LENGTH_SHORT).show();
						}
						
					}
					
					@Override
					public boolean canDismiss(int position) {
						// TODO Auto-generated method stub
						return true;
					}
				});
				lv.setOnTouchListener(touchListner);
				

			} else {
				Toast.makeText(getApplicationContext(), "List empty",
						Toast.LENGTH_SHORT).show();
			}
			super.onPostExecute(result);
		}

	}
}
