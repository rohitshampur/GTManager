package com.uttara.gtmanager;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyTaskListAdapter extends ArrayAdapter {
	private Activity activity;
	private List<TaskBean> tbl;
	@SuppressWarnings("unchecked")
	public MyTaskListAdapter(Activity activity, List<TaskBean> tbl) {
		super(activity,android.R.id.list, tbl);
		this.activity = activity;
		this.tbl = tbl;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		TaskListView mv = null;
		if(rowView == null){
			LayoutInflater inflater = activity.getLayoutInflater();
			rowView = inflater.inflate(R.layout.row_task_list, null);
			mv = new TaskListView();
			mv.taskName = (TextView) rowView.findViewById(R.id.taskNameInsert);
			mv.status = (TextView) rowView.findViewById(R.id.statusInsert);
			
			
			rowView.setTag(mv);
		}else{
			mv = (TaskListView) rowView.getTag();
		}
		TaskBean tb =tbl.get(position);
		mv.taskName.setText(tb.getTaskName().toString());
		mv.status.setText(tb.getStatus().toString());
		
		
		return rowView;
	}
	protected static class TaskListView{
		protected TextView taskName;
		protected TextView status;
		
	}
}
