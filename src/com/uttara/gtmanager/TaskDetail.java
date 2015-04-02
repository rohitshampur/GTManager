package com.uttara.gtmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TaskDetail extends Activity {
	private TextView taskName;
	private TextView taskDate;
	private TextView taskDes;
	private TextView projName;
	private TextView status;
	private TaskBean tb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_detail);
		taskName = (TextView) findViewById(R.id.tv6);
		taskDes = (TextView) findViewById(R.id.tv7);
		projName = (TextView) findViewById(R.id.tv8);
		taskDate = (TextView) findViewById(R.id.tv9);
		status = (TextView) findViewById(R.id.tv10);
		Intent intent = getIntent();
		tb = new TaskBean();
		tb = (TaskBean) intent.getSerializableExtra("currentTask");
		taskName.setText(tb.getTaskName());
		taskDes.setText(tb.getTaskDesc());
		projName.setText(tb.getProjectName());
		taskDate.setText(tb.getTaskCreatedDate());
		status.setText(tb.getStatus());
	}

	
}
