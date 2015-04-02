package com.uttara.gtmanager;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ChooseMemberListAdapter extends ArrayAdapter {
	private Activity activity;
	private List<MemberBeanParcable> mbl;
	@SuppressWarnings("unchecked")
	public ChooseMemberListAdapter(Activity activity, List<MemberBeanParcable> mbl2) {
		super(activity,android.R.id.list, mbl2);
		this.activity = activity;
		this.mbl = mbl2;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		MemberListView mv = null;
		if(rowView == null){
			LayoutInflater inflater = activity.getLayoutInflater();
			rowView = inflater.inflate(R.layout.row_member_list, null);
			mv = new MemberListView();
			mv.name = (TextView) rowView.findViewById(R.id.memberNameTxtvw);
			mv.email = (TextView) rowView.findViewById(R.id.memberEmailTxtvw);
			
			
			rowView.setTag(mv);
		}else{
			mv = (MemberListView) rowView.getTag();
		}
		MemberBeanParcable pb =mbl.get(position);
		mv.name.setText(pb.getName().toString());
		mv.email.setText(pb.getEmail().toString());
		
		
		return rowView;
	}
	protected static class MemberListView{
		protected TextView name;
		protected TextView email;
		
	}
}
