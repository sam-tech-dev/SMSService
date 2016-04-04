package com.example.smsservice;

import java.text.SimpleDateFormat;

import com.example.smsservice.IndividualMsgDisp;
import com.example.smsservice.MainActivity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.PhoneLookup;
import android.support.v7.app.NotificationCompat;
import android.support.v7.app.NotificationCompat.Builder;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;


public class NewSms extends Activity {

    Context context;	
	EditText mono=null;
	EditText sms=null;
	String mobileno,msg,timeString,dateString,name,type;
	ImageButton sendsms,backBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_sms);
		
		 mono=(EditText)findViewById(R.id.mobileno);
	     sms=(EditText)findViewById(R.id.sms);
		 sendsms=(ImageButton)findViewById(R.id.send);
		 backBtn=(ImageButton)findViewById(R.id.backbutton);
		
	   context=this;
		
	   
	   
	   
	   backBtn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			Intent intent=new Intent(context,MainActivity.class);
			startActivity(intent);
			finish();
			
		}
	});
		
		
		sendsms.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				

				 mobileno=mono.getText().toString();
				 msg=sms.getText().toString();
				
				 sendSms(context, mobileno, msg);
				 
				/* Intent intent=new Intent(NewSms.this,MainActivity.class);
					startActivity(intent);
					finish();*/
					
					
					Intent intent = new Intent(getApplicationContext() , IndividualMsgDisp.class);
					
					   intent.putExtra("monumber", mobileno );
					   intent.putExtra("name",name);
					
						startActivity(intent);
						finish();
					
					
					
			}
		});
				
	
	
	}


	
	
	  public void sendSms(Context context,String mobileno,String msg){
		  
		 
			try{
			 
				
				SmsManager smsmanager=SmsManager.getDefault();
				
				smsmanager.sendTextMessage(mobileno, null, msg, null, null);
				
				
				 
				  long date = System.currentTimeMillis(); 

				  SimpleDateFormat sdf = new SimpleDateFormat("dd MMM");
				   dateString = sdf.format(date); 
				  
				  SimpleDateFormat stime = new SimpleDateFormat("h:mm a");
				  timeString = stime.format(date);
				  
				  
				name=new DataBase(context).getContactName(context, mobileno); 
				 
				 if(name==null){
					name="Unknown Person";
				 }
				
				 type="sent";
				 
			
				 
				 new DataBase(context).insertionrow(new DataBase(context), name,mobileno, dateString,timeString, msg,type);				
				
				Toast.makeText(context, "sent SMS",Toast.LENGTH_SHORT).show();
				
			   IndividualMsgDisp.update();
			 //  MainActivity.updateFrontList();
			   
			  // Toast.makeText(context, "after update method",Toast.LENGTH_SHORT).show();
				
			}
			
			catch(Exception e){
				
				  //  Toast.makeText(getApplicationContext(), "try again", Toast.LENGTH_SHORT).show();
				
				     e.printStackTrace();
					
			         }	  
		  
	  }
	
	
	
	
	
}
