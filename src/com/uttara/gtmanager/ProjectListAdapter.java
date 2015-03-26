package com.uttara.gtmanager;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ProjectListAdapter extends ArrayAdapter{
	private Activity activity;
	private List<ProjectBean> pbl;
	@SuppressWarnings("unchecked")
	public ProjectListAdapter(Activity activity , List<ProjectBean> pb){
		super(activity,android.R.id.list,pb);
		this.activity = activity;
		this.pbl = pb;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		ProjectListView pv = null;
		if(rowView == null){
			LayoutInflater inflater = activity.getLayoutInflater();
			rowView = inflater.inflate(R.layout.row_project_list, null);
			pv = new ProjectListView();
			pv.projectName = (TextView) rowView.findViewById(R.id.textView4);
			pv.projectDate = (TextView) rowView.findViewById(R.id.textView5);
			pv.projectType = (TextView) rowView.findViewById(R.id.textView6);
			rowView.setTag(pv);
		}else{
			pv = (ProjectListView) rowView.getTag();
		}
		ProjectBean pb =pbl.get(position);
		pv.projectName.setText(pb.getProjectName().toString());
		pv.projectDate.setText(pb.getProj_CreatedDate().toString());
		pv.projectType.setText(pb.getTypeOfProject().toString());
		
		return rowView;
	}
	protected static class ProjectListView{
		protected TextView projectName;
		protected TextView projectDate;
		protected TextView projectType;
	}
}
