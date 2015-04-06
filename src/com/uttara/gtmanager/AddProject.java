package com.uttara.gtmanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Semaphore;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddProject extends Activity {
	private TextView dateView;
	public static final int CHOOSE_MEMBER_RESULT = 1;
	private TaskBean tb;
	private String taskName;
	ArrayList<String> str = new ArrayList<String>();
	Calendar c = Calendar.getInstance();
	int year = c.get(Calendar.YEAR);
	int month = c.get(Calendar.MONTH);
	int day = c.get(Calendar.DAY_OF_MONTH);
	String selectedDate = null;
	List<MemberBeanParcable> MemberList = new ArrayList<MemberBeanParcable>();
	MemberBeanParcable mb;
	private EditText projName;
	private EditText projDesc;
	private EditText projType;
	private Spinner taskNameSpinner;
	private Spinner memberNamespinner;
	private ArrayAdapter<String> dataAdapter;
	private Bundle b;
	private List<String> str1 = new ArrayList<String>();
	private ProjectBean pb;
	List<TaskBean> tblist;
	private Button button1;

	private String projectName;
	private ProgressDialog pdialog;
	private ProgressDialog pd;
	public static final Semaphore LOCK = new Semaphore(0);

	static final int DATE_DIALOG_ID = 999;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_project);
		dateView = (TextView) findViewById(R.id.dateView);
		projName = (EditText) findViewById(R.id.projectName_fld);
		projDesc = (EditText) findViewById(R.id.description_fld);
		projType = (EditText) findViewById(R.id.typeOfPro_fld);
		taskNameSpinner = (Spinner) findViewById(R.id.spinner2);
		taskNameSpinner.setVisibility(View.GONE);
		memberNamespinner = (Spinner) findViewById(R.id.spinner1);
		memberNamespinner.setVisibility(View.GONE);
		button1 = (Button) findViewById(R.id.btnAddTask);
		/*
		 * button2 = (Button) findViewById(R.id.btnAddAnotherTask);
		 * button2.setVisibility(View.GONE);
		 */
		tblist = new ArrayList<TaskBean>();
		dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item);
		tb = new TaskBean();
		pb = new ProjectBean();
		pdialog = new ProgressDialog(AddProject.this);
		pd = new ProgressDialog(AddProject.this);

		/*
		 * button1.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) {
		 * 
		 * if(projectName == null ||projectName.trim().equals("")){
		 * Toast.makeText(getApplicationContext(),
		 * "Please enter project details first", Toast.LENGTH_LONG).show();
		 * button1.setVisibility(View.VISIBLE); }else{
		 * button1.setVisibility(View.INVISIBLE);
		 * button2.setVisibility(View.VISIBLE); addTask(); }
		 * 
		 * 
		 * 
		 * } });
		 */
		projName.requestFocus();

	}

	@SuppressWarnings("deprecation")
	public void showDate(View v) {
		showDialog(DATE_DIALOG_ID);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			// set date picker as current date

			return new DatePickerDialog(this, datePickerListener, year, month,
					day);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;
			month++;
			int currYear = c.get(Calendar.YEAR);
			int currMonth = c.get(Calendar.MONTH);
			int currDay = c.get(Calendar.DATE);
			/*
			 * if(year < currYear){ Toast.makeText(getApplicationContext(),
			 * "Please select the future date", Toast.LENGTH_LONG).show();
			 * }else{ if(currMonth<month){
			 * Toast.makeText(getApplicationContext(),
			 * "Please select the future date", Toast.LENGTH_LONG).show();
			 * }else{ Log.d(Config.TAG , "date "+month+" "+day+" "+year);
			 * dateView.setText(day+"-"+month+"-"+year); selectedDate =
			 * ""+day+"-"+month+"-"+year; // set selected date into textview } }
			 */
			dateView.setText(day + "-" + month + "-" + year);
			selectedDate = "" + day + "-" + month + "-" + year;

		}
	};

	public void chooseMember(View v) {
		Intent intent = new Intent(this, ChooseMemberList.class);
		startActivityForResult(intent, 1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		System.out.println(requestCode);
		switch (requestCode) {
		case 1:
			String name = null;

			if (data != null) {
				name = (String) data.getSerializableExtra("item");
				mb = new MemberBeanParcable();
				b = data.getBundleExtra("item1");
				mb = (MemberBeanParcable) b.get("item1");
				System.out.println("Member bean " + mb);
				System.out.println("before for loop" + name + "size"
						+ str.size());
				if (str.size() == 0) {
					str.add(name);
					MemberList.add(mb);
					dataAdapter
							.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					dataAdapter.add(name);
					memberNamespinner.setVisibility(View.VISIBLE);
					memberNamespinner.setAdapter(dataAdapter);
					dataAdapter.notifyDataSetChanged();
				} else {
					one: for (int i = 0; i <= str.size();) {
						System.out.println(str.get(i));

						if (str.get(i).equals(name)) {
							Log.d(Config.TAG, "name " + name);
							Toast.makeText(this, "Employee already selected",
									Toast.LENGTH_LONG).show();
							break one;
						} else {
							Log.d(Config.TAG, "name " + name);
							str.add(name);
							MemberList.add(mb);
							dataAdapter
									.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
							dataAdapter.add(name);
							memberNamespinner.setVisibility(View.VISIBLE);
							memberNamespinner.setAdapter(dataAdapter);
							dataAdapter.notifyDataSetChanged();

							break one;

						}
					}
				}
				System.out.println("after for loop" + name);
				Log.d(Config.TAG, "String data" + str);
			} else {
				Log.d(Config.TAG, "do nothing");
				memberNamespinner.setVisibility(View.INVISIBLE);
			}
			/*
			 * if(str.equals(name)){ Toast.makeText(this,
			 * "Employee already selected", Toast.LENGTH_LONG).show();
			 * dataAdapter.remove(name); }else{ Log.d(Config.TAG,
			 * "inside else block "+name);
			 * dataAdapter.setDropDownViewResource(android
			 * .R.layout.simple_spinner_dropdown_item); dataAdapter.add(name);
			 * memberNamespinner.setAdapter(dataAdapter);
			 * dataAdapter.notifyDataSetChanged();
			 */
			break;

		case 2:
			if (data != null) {
				taskName = data.getStringExtra("taskName");

				str1.add(taskName);
				Log.d(Config.TAG, "Str list" + str);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
						android.R.layout.simple_spinner_item);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				adapter.addAll(str1);
				taskNameSpinner.setVisibility(View.VISIBLE);
				taskNameSpinner.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				tb = new TaskBean();
				tb = (TaskBean) data.getSerializableExtra("taskBean");
				tblist.add(tb);
				tb.setTaskName(taskName);

				Log.d(Config.TAG, "taskBean" + tb);
				Log.d(Config.TAG, "TAskbean list>>>>>>>>>" + tblist);

			}
			break;
		}

	}

	public void addTask(View v) {
		Intent intent = new Intent(getApplicationContext(), AddTask.class);
		Log.d(Config.TAG, "member bean add task method " + MemberList);
		intent.putParcelableArrayListExtra("MemberBeanList",
				(ArrayList<? extends Parcelable>) MemberList);
		projectName = projName.getText().toString();
		intent.putExtra("projName", projName.getText().toString());
		if (projectName == null || projectName.trim().equals("")) {
			button1.setVisibility(View.VISIBLE);
			Toast.makeText(getApplicationContext(),
					"Please enter project details first", Toast.LENGTH_LONG)
					.show();

		} else {

			new CheckForProjectName(intent).execute();

		}

	}

	/*
	 * public void addAnotherTask(View v){
	 * 
	 * Intent intent = new Intent(getApplicationContext(), AddTask.class);
	 * Log.d(Config.TAG, "member bean add task method "+MemberList);
	 * intent.putParcelableArrayListExtra("MemberBeanList", (ArrayList<? extends
	 * Parcelable>) MemberList); projectName = projName.getText().toString();
	 * intent.putExtra("projName", projName.getText().toString());
	 * 
	 * startActivityForResult(intent, 2);
	 * 
	 * }
	 */
	private class CheckForProjectName extends AsyncTask<Void, Void, String> {
		Intent i;

		public CheckForProjectName(Intent intent) {
			// TODO Auto-generated constructor stub
			this.i = intent;
		}

		@Override
		protected void onPreExecute() {
			pdialog.setMessage("Checking project name ");
			pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pdialog.setIndeterminate(true);
			pdialog.show();
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(Void... params) {
			HttpURLConnection con = null;
			BufferedReader br = null;
			try {
				String urlStr = new String(Config.CONFIG
						+ "/checkProjectName?projectName=" + projectName);

				URL url = new URL(urlStr);
				con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("POST");
				br = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				String response = br.readLine();
				Log.d(Config.TAG, "Response string@@@@@@@@@@@@@@@ " + response);
				JSONObject obj;
				JSONParser parser = new JSONParser();
				obj = (JSONObject) parser.parse(response);
				String res = (String) obj.get("status");
				Log.d(Config.TAG,
						"Response string after parsing@@@@@@@@@@@@@@@ " + res);
				return res;
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
			if(result!=null){
			if (result.equals(Config.SUCCESS)) {
				startActivityForResult(i, 2);
				/*
				 * button1.setVisibility(View.INVISIBLE);
				 * button2.setVisibility(View.VISIBLE);
				 */
				// projName.setFocusable(false);
				pdialog.dismiss();
			} else {
				Toast.makeText(getApplicationContext(), result,
						Toast.LENGTH_LONG).show();
				projName.setError("Project name already exists ");
				pdialog.dismiss();
			}
			}else{
				Toast.makeText(getApplicationContext(), "Something went wrong ,please try again", Toast.LENGTH_LONG).show();
				pdialog.dismiss();
			}
			super.onPostExecute(result);
		}

	}

	public void addProject(View v) {
		String email;
		pb.setProjectName(projName.getText().toString());
		pb.setDescription(projDesc.getText().toString());
		pb.setTypeOfProject(projType.getText().toString());
		pb.setProjCompletionDate(selectedDate);
		SharedPreferences pref = getSharedPreferences("gtmanager", MODE_PRIVATE);
		email = pref.getString("email", "");

		if (pb.validate() == "") {

			Log.d(Config.TAG, "add project task bean list  @@@" + tblist);
			new OnlineAddProject(tblist, MemberList).execute(pb);
		} else {
			Toast.makeText(getApplicationContext(), pb.validate(),
					Toast.LENGTH_LONG).show();
		}
	}

	/*
	 * public void postData(String url,JSONObject obj) { // Create a new
	 * HttpClient and Post Header
	 * 
	 * HttpParams myParams = new BasicHttpParams();
	 * HttpConnectionParams.setConnectionTimeout(myParams, 10000);
	 * HttpConnectionParams.setSoTimeout(myParams, 10000); HttpClient httpclient
	 * = new DefaultHttpClient(myParams ); String json=obj.toString();
	 * 
	 * try {
	 * 
	 * HttpPost httppost = new HttpPost(url.toString());
	 * httppost.setHeader("Content-type", "application/json");
	 * 
	 * StringEntity se = new StringEntity(obj.toString());
	 * se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
	 * "application/json")); httppost.setEntity(se);
	 * 
	 * HttpResponse response = httpclient.execute(httppost); String temp =
	 * EntityUtils.toString(response.getEntity()); Log.i("tag", temp);
	 * 
	 * 
	 * } catch (ClientProtocolException e) {
	 * 
	 * } catch (IOException e) { } }
	 */

	private class OnlineAddProject extends
			AsyncTask<ProjectBean, Void, JSONObject> {
		List<TaskBean> tbl;
		List<MemberBeanParcable> mbl;

		public OnlineAddProject(List<TaskBean> tb,
				List<MemberBeanParcable> memberList) {
			// TODO Auto-generated constructor stub
			this.tbl = tb;
			this.mbl = memberList;
		}

		@Override
		protected void onPreExecute() {

			pd.setTitle("Adding Project");
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pd.setMessage("Please wait");
			pd.setCancelable(false);
			pd.show();
			super.onPreExecute();
		}

		@Override
		protected JSONObject doInBackground(ProjectBean... params) {
			HttpParams myParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(myParams, 10000);
			HttpConnectionParams.setSoTimeout(myParams, 10000);
			// HttpClient httpclient = new DefaultHttpClient(myParams );
			Log.d(Config.TAG, "Do in background task bean name-------------->>"
					+ tb.getTaskName());
			List<String> emailList = new ArrayList<String>();

			for (MemberBeanParcable mb : MemberList) {
				emailList.add(mb.getEmail().toString());
			}
			JSONObject obj1 = new JSONObject();
			pb.setTasklist(tbl);
			pb.setEmailList(emailList);

			Log.d(Config.TAG, "get json string" + pb.getJsonString());

			try {
				String url = new String(Config.CONFIG + "/jsonAddProject");
				List<NameValuePair> params1 = new ArrayList<NameValuePair>();
				params1.add(new BasicNameValuePair("data", pb.getJsonString()));
				String response = ServiceHandler.httpPost(url, params1);
				Log.d(Config.TAG, "Response is " + response);
				JSONObject responseJson = new JSONObject();
				JSONParser parser = new JSONParser();
				responseJson = (JSONObject) parser.parse(response);
				Log.d(Config.TAG, "response Json" + responseJson);
				return responseJson;
			} catch (Exception e) {
				Log.d(Config.TAG, "message" + e.getMessage());
			}
			return null;
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			if (result != null) {
				Log.d(Config.TAG, "on postexecute response" + result);
				String resultTaskAdd = (String) result.get("addTask");
				String resultProjectAdd = (String) result.get("addProject");
				System.out.println("--------------->>>>>>" + resultTaskAdd);
				System.out.println("----------@@@@@@@@@@" + resultProjectAdd);
				JSONParser parser = new JSONParser();
				try {
					// JSONObject ob = (JSONObject) parser.parse(resultTaskAdd);
					JSONObject ob1 = (JSONObject) parser
							.parse(resultProjectAdd);
					// String resultTaskAdd1 = (String)
					// ob.get("taskNameExistsKey");
					// Log.d(Config.TAG,
					// "@@@@@@@@@@@@@@__________>>>"+resultTaskAdd);
					String resultProjectAdd1 = (String) ob1.get("status");
					Log.d(Config.TAG, "@@@@@@@@@@@@@@" + resultProjectAdd1);
					if (resultTaskAdd.equals(Config.SUCCESS)) {
						Toast.makeText(getApplicationContext(),
								"Successfuly added the task", Toast.LENGTH_LONG)
								.show();
						if (resultProjectAdd1.equals(Config.SUCCESS)) {
							Toast.makeText(getApplicationContext(),
									"Successfuly add the project",
									Toast.LENGTH_LONG).show();
							Intent intent = new Intent(getApplicationContext(),
									ManagerMenuActivity.class);
							startActivity(intent);
						} else {
							Toast.makeText(getApplicationContext(),
									resultProjectAdd1, Toast.LENGTH_LONG)
									.show();
							pd.dismiss();
						}
					} else {
						Toast.makeText(getApplicationContext(), resultTaskAdd,
								Toast.LENGTH_LONG).show();
						pd.dismiss();

					}

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*
				 * JSONObject res1=(JSONObject) result.get("addProject"); String
				 * statusString=(String) res1.get("status"); JSONObject
				 * res2=(JSONObject) result.get("addTask"); String
				 * taskNameExistsKey=(String) res2.get("taskNameExistsKey");
				 * String insertMembers=(String) result.get("insertMembers");
				 * Log.d("test",
				 * "Data is \n Status is "+statusString+" \n taskNameExistsKey is "
				 * +taskNameExistsKey+" \n insertMembers is "+insertMembers);
				 */
			}
			super.onPostExecute(result);
		}
	}

}
