package com.example.smsservice;


import com.example.smsservice.R;
import com.example.smsservice.messagewrapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MessageListAdapter extends BaseAdapter {
	private final static String TAG = MessageListAdapter.class.getSimpleName();
	Context context;
	ArrayList<messagewrapper> messageList;
	LayoutInflater inflater;
	ViewHoder holder;
  
 
   
	public MessageListAdapter(Context context, ArrayList<messagewrapper> messageList) {
		this.context = context;
		this.messageList = messageList;
		
       // mSelectedItemsIds=new SparseBooleanArray();
		  
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	
	}

	@Override
	public int getCount() {
		return messageList.size();
	}
	

	@Override
	public messagewrapper getItem(int position) {
		return messageList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	
	public void remove(messagewrapper object) {
	
		String mono=object.get_number();
		  messageList.remove(object);
		// Toast.makeText(context,mono, Toast.LENGTH_LONG).show();
		  
       new DataBase(context).deleteIndividualAllSms(new DataBase(context),mono);	
         
		
		
		//notifyDataSetChanged();
	}
 /*
	public void removeSelection() {
		
		 mSelectedItemsIds = new SparseBooleanArray();
			MainActivity.updateFrontList();
			//notifyDataSetChanged();
		}
	
	
	public void toggleSelection(int position) {
		
		
		selectView(position, !mSelectedItemsIds.get(position));
	
	}

	public void selectView(int position, boolean value) {
		
		Toast.makeText(context,"selected view", Toast.LENGTH_LONG).show();
		
		if (value){
			Toast.makeText(context,"false", Toast.LENGTH_LONG).show();
			mSelectedItemsIds.put(position, value);}
		else{
			Toast.makeText(context,"true", Toast.LENGTH_LONG).show();
			mSelectedItemsIds.delete(position);
		}
		
		//MainActivity.updateFrontList();
		
	}
 
	public int getSelectedCount() {
		return mSelectedItemsIds.size();
	}
 
	
	
	public SparseBooleanArray getSelectedIds() {
		return mSelectedItemsIds;
	}
	
	*/
	
	
	
	
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.activity_message_list_adapter, null);

			holder = new ViewHoder(convertView);

			convertView.setTag(holder);
		} else {

			holder = (ViewHoder) convertView.getTag();
		}

		messagewrapper obj = messageList.get(position);

		
		//  Toast.makeText(context,obj.get_name(), Toast.LENGTH_LONG).show();
       // Toast.makeText(context,obj.get_number(), Toast.LENGTH_LONG).show();
		
		if(obj.get_name().equals("Unknown Person")){
		holder.addresseTv.setText(obj.get_number()); }
		else{
		holder.addresseTv.setText(obj.get_name()); }
		
		
		holder.countTv.setText(obj.get_count());
		holder.contentTv.setText(obj.get_content());
		
		 long date = System.currentTimeMillis(); 

		  SimpleDateFormat sdf = new SimpleDateFormat("dd MMM");
		  String dateString = sdf.format(date); 
		  
		 // Toast.makeText(context,dateString, Toast.LENGTH_LONG).show();
		 
		  
		  if(obj.get_date().equals(dateString)){
			  holder.dateTv.setText(obj.get_time()); }
		  else{
		      holder.dateTv.setText(obj.get_date()); }
		

		return convertView;
	}

	public static class ViewHoder {

		TextView addresseTv,contentTv,dateTv,countTv;

		public ViewHoder(View view) {
		
            
			addresseTv = (TextView) view.findViewById(R.id.personaddress);
			countTv = (TextView) view.findViewById(R.id.noofSms);
			contentTv = (TextView) view.findViewById(R.id.messagecontent);
			dateTv = (TextView) view.findViewById(R.id.dateofmsg);
			
		}
	}

}
