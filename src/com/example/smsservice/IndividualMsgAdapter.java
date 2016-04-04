package com.example.smsservice;


import com.example.smsservice.R;
import com.example.smsservice.IndividualMsgsWrapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class IndividualMsgAdapter extends BaseAdapter {
	private final static String TAG = IndividualMsgAdapter.class.getSimpleName();
	Context context;
	ArrayList<IndividualMsgsWrapper>  IndividualMsgList;
	LayoutInflater inflater;
	ViewHoder holder;



	public IndividualMsgAdapter(Context context, ArrayList<IndividualMsgsWrapper>  IndividualMsgList) {
		this.context = context;
		this. IndividualMsgList =  IndividualMsgList;

		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	
	}
	
	
	

	
	

	@Override
	public int getCount() {
		return  IndividualMsgList.size();
	}

	@Override
	public Object getItem(int position) {
		return  IndividualMsgList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
			
		
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.activity_individual_msg_adapter, null);

			holder = new ViewHoder(convertView);

			convertView.setTag(holder);
		} else {

			holder = (ViewHoder) convertView.getTag();
		}

		IndividualMsgsWrapper obj =  IndividualMsgList.get(position);

		
		  Boolean mySelf=obj.get_type().equals("sent");
			  
		  setAlignment(holder,mySelf);
		 
		holder.messageTv.setText(obj.get_content());
		
		 long date = System.currentTimeMillis(); 

		  SimpleDateFormat sdf = new SimpleDateFormat("dd MMM");
		  String dateString = sdf.format(date); 
		  
		 // Toast.makeText(context,dateString, Toast.LENGTH_LONG).show();
		  
		  String dateOfMsg=obj.get_date();
		  String timeofMsg=obj.get_time();
		  
		  if(obj.get_date().equals(dateString)){
			  dateOfMsg="Today"; }
		      
		  String msgInfo=dateOfMsg+" "+timeofMsg;
		  
		      holder.dateTv.setText(msgInfo); 
		

		return convertView;
		
	}

	
	
	 private  void   setAlignment(ViewHoder holder,Boolean mySelf){
		 
		 if(!mySelf){
			
			holder.textBackground.setBackgroundResource(R.drawable.background2);
			 
			 LinearLayout.LayoutParams layoutparameter=(LinearLayout.LayoutParams)holder.textBackground.getLayoutParams();
			 layoutparameter.gravity=Gravity.LEFT;
			 holder.textBackground.setLayoutParams(layoutparameter);
			 
			   RelativeLayout.LayoutParams laypara = (RelativeLayout.LayoutParams) holder.content.getLayoutParams();
			   laypara.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			   laypara.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,0);
		        holder.content.setLayoutParams(laypara);
			 
		        layoutparameter = (LinearLayout.LayoutParams) holder.messageTv.getLayoutParams();
		        layoutparameter.gravity = Gravity.LEFT;
	            holder.messageTv.setLayoutParams(layoutparameter);

	            layoutparameter = (LinearLayout.LayoutParams) holder.dateTv .getLayoutParams();
	            layoutparameter.gravity = Gravity.LEFT;
	            holder.dateTv .setLayoutParams(layoutparameter);
		        
		        
		 }
		 else{
			 
			 holder.textBackground.setBackgroundResource(R.drawable.background1);
			 
			 LinearLayout.LayoutParams layoutparameter=(LinearLayout.LayoutParams)holder.textBackground.getLayoutParams();
			 layoutparameter.gravity=Gravity.RIGHT;
			 holder.textBackground.setLayoutParams(layoutparameter);
			 
			 RelativeLayout.LayoutParams laypara = (RelativeLayout.LayoutParams) holder.content.getLayoutParams();
			   laypara.addRule(RelativeLayout.ALIGN_PARENT_LEFT,0);
			   laypara.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		        holder.content.setLayoutParams(laypara);
			 
		        layoutparameter = (LinearLayout.LayoutParams) holder.messageTv.getLayoutParams();
		        layoutparameter.gravity = Gravity.RIGHT;
	            holder.messageTv.setLayoutParams(layoutparameter);

	            layoutparameter = (LinearLayout.LayoutParams) holder.dateTv .getLayoutParams();
	            layoutparameter.gravity = Gravity.RIGHT;
	            holder.dateTv .setLayoutParams(layoutparameter);
		        
		        
			 
			
		 }
		  
	    }
	
	
	public static class ViewHoder {

		TextView messageTv,dateTv;
		LinearLayout content,textBackground;

		public ViewHoder(View view) {
		
            textBackground=(LinearLayout)view.findViewById(R.id.textBackground);
			content=(LinearLayout)view.findViewById(R.id.content);
			messageTv = (TextView)view.findViewById(R.id.message);
			dateTv = (TextView) view.findViewById(R.id.dateTime);
			
		}
	}

}
