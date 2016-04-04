package com.example.smsservice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.example.smsservice.MainActivity;
import com.example.smsservice.IndividualMsgDisp;
import com.example.smsservice.NotificationView;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.PhoneLookup;
import android.support.v7.app.NotificationCompat;
import android.support.v7.app.NotificationCompat.Builder;
import android.telephony.TelephonyManager;
import android.telephony.gsm.SmsMessage;
import android.util.Log;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class SmsReceiver extends BroadcastReceiver {

	
	Context context ;
  
	String msg="";
	String number="";
	String name="";
  
  
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		
		
		
		Bundle bundle =intent.getExtras();
		
		SmsMessage[] messages=null;
		String msg="";
		String number="";
		String name="";
		String dateString="";
	    String timeString="";
	    String type="";
		
		  if(bundle!=null){
			  Object[] pduobj = (Object[])bundle.get("pdus");
			  
			  messages = new SmsMessage[pduobj.length];
			  
			  for(int i=0;i<messages.length;i++){
				
				  
		try{		  
				  messages[i]=SmsMessage.createFromPdu((byte[])pduobj[i]);
				  
				  number = messages[i].getOriginatingAddress();
				  
				  
				  msg = messages[i].getMessageBody().toString();
			
				  
				
				  
				  
				  
				  long date = System.currentTimeMillis(); 

				  SimpleDateFormat sdf = new SimpleDateFormat("dd MMM");
				   dateString = sdf.format(date); 
				  
				  SimpleDateFormat stime = new SimpleDateFormat("h:mm a");
				  timeString = stime.format(date);
				  
				//  Toast.makeText(context, timeString, Toast.LENGTH_LONG).show();
				  
				  
				  
				 name= new DataBase(context).getContactName(context, number);
				  
				 if(name==null){
					 name="Unknown Person";
				 }
				  
	             type="received";
				
	            
	             
	             
			    new DataBase(context).insertionrow(new DataBase(context), name,number, dateString,timeString, msg,type );				 
				
			    
			    IndividualMsgDisp.update();
			    MainActivity.updateFrontList();
			 
			    
				 
			    IndividualMsgDisp.notification(name,number,msg);
			   
					  
					}catch(Exception e){
								
								
					Toast.makeText(context, "msg not received", Toast.LENGTH_LONG).show();
								
				   e.printStackTrace();
				  
				  
								
		     }
				  
				  
				  
				  
			  }
			  
			  
			  
			  
		  }
		
			  
		
		
	}
	
	
	
	
	
	
	
	
	
}


