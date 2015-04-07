package com.uttara.gtmanager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomSpinnerAdapter1 extends ArrayAdapter {
	private Activity activity;
	private List<RegBean> sList = new ArrayList<RegBean>();
	public CustomSpinnerAdapter1(Activity activity, int textViewResourceId, List<RegBean> objects) {
		super(activity, textViewResourceId, objects);
		this.activity = activity;
		this.sList = objects;
		// TODO Auto-generated constructor stub
		
	}

	
	
	public View getCustomView(int position, View convertView,
			ViewGroup parent) {
			 
			// Inflating the layout for the custom Spinner
			LayoutInflater inflater = activity.getLayoutInflater() ;
			View layout = inflater.inflate(R.layout.row_member_list, parent, false);
			 
			// Declaring and Typecasting the textview in the inflated layout
			TextView tvName = (TextView) layout.findViewById(R.id.memberNameTxtvw);
			TextView tvEmail = (TextView) layout.findViewById(R.id.memberEmailTxtvw); 
			Log.d(Config.TAG, "inside custom spinner "+sList);
			// Setting the text using the array
			/*for(MemberBeanParcable mb :sList){
				
				tvName.setText(mb.getFname().toString());
				tvEmail.setText(mb.getEmail().toString());
			}*/
			RegBean rb = new RegBean();
			/*for(int i = 0;i<sList.size();i++){
				
			}*/
			rb = sList.get(position);
			tvName.setText(rb.getFname().toString());
			tvEmail.setText(rb.getEmail().toString());
			// Setting the color of the text
			tvName.setTextColor(Color.rgb(75, 180, 225));
			 
			// Declaring and Typecasting the imageView in the inflated layout
			
			 
			// Setting an image using the id's in the array
			
			 
			// Setting Special atrributes for 1st element
			
			
			// Setting the size of the text
		
			 
			
			 
			return layout;
			}
	// It gets a View that displays in the drop down popup the data at the specified position
	@Override
	public View getDropDownView(int position, View convertView,
	ViewGroup parent) {
	return getCustomView(position, convertView, parent);
	}
	 
	// It gets a View that displays the data at the specified position
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	return getCustomView(position, convertView, parent);
	}
	}
