package com.uttara.gtmanager;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MemberDetailActivity extends Activity {
	
	private TextView nameView;
	private TextView lnameView;
	private TextView emailView;
	private TextView phnoView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_member_detail);
		nameView = (TextView) findViewById(R.id.blnknametxtvw);
		lnameView = (TextView) findViewById(R.id.blnklnametxtvw);
		emailView = (TextView) findViewById(R.id.blnkemailtxtvw);
		phnoView = (TextView) findViewById(R.id.blnkphntxtvw);
		MemberBean mb = (MemberBean) getIntent().getSerializableExtra("currentMem");
		Log.d(Config.TAG, "member Bean"+mb);
		nameView.setText(mb.getFname());
		lnameView.setText(mb.getLname());
		emailView.setText(mb.getEmail());
		phnoView.setText(mb.getPhno());
	}


}
