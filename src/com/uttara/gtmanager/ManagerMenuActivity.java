package com.uttara.gtmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ManagerMenuActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manager_menu);
	}
	public void projectsMenu(View v){
		Intent intent = new Intent(this, ProjectsList.class);
		startActivity(intent);
	}
	public void membersList(View v){
		Intent intent = new Intent(this, MemberList.class);
		startActivity(intent);
		
	}
	public void reportsMenu(View v){
		
	}
	public void tasksMenu(View v){
		
	}
}
