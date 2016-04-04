package com.example.smsservice;

import java.util.ArrayList;

import com.example.smsservice.NewSms;
import com.example.smsservice.SmsReceiver;

import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.app.NotificationCompat.Builder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class IndividualMsgDisp extends Activity {

	 static  IndividualMsgAdapter  mAdapter;
	static ArrayList<IndividualMsgsWrapper> IndMsgList,IndividualMsgList;
     TextView individual;
     static ListView indlistv;
     static Context context;
     ImageButton back,sendBtn;
     EditText message;
   static  String	 number;
     String	 name;
     SmsReceiver myReceiver;
     
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_individual_msg_disp);
		
		
		context=this;
		
		Bundle bundle = getIntent().getExtras();
	    	 number = bundle.getString("monumber");
	    	 name = bundle.getString("name");
	    
	    if(name.equals("Unknown Person")){
	    	name=number;
	    }
	    
	    
	    
	    back=(ImageButton)findViewById(R.id.backbutton);
	    sendBtn=(ImageButton)findViewById(R.id.sendIt);
	    indlistv=(ListView)findViewById(R.id.listView1);
	    message=(EditText)findViewById(R.id.msg);
	    individual=(TextView)findViewById(R.id.indname);
	    individual.setText(name);
	    
	    
	    
	    back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent=new Intent(getApplicationContext(),MainActivity.class);
				 startActivity(intent);
				  finish();
				
			}
		});
		
	    
	    sendBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//Toast.makeText(getApplicationContext(),"sattar :"+number, Toast.LENGTH_LONG).show();
				
				String msg=message.getText().toString();
				
				
			if(number!=null){	
				new NewSms().sendSms(context, number, msg); }
				
			}
		});
	    
	
	    
	    
	    
	    indlistv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				AlertDialog.Builder alert= new AlertDialog.Builder(context);
				
				alert.setTitle("Alert!!");
				alert.setMessage("Are you sure to delete SMS?");
				
						final String idofs= IndividualMsgList.get(position).get_id();
						final String num=IndividualMsgList.get(position).get_number();
				
				 alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

		                @Override
		                public void onClick(DialogInterface dialog, int which) {
		                                        
		                	
		             Integer noSMS=new DataBase(context).deleteSingleSms(new DataBase(context), idofs,num);
		                if(noSMS==1){
		                	
		                	Intent intent=new Intent(IndividualMsgDisp.this,MainActivity.class);
		    				startActivity(intent);
		    				finish();	
		                }else{
		                	update();
		                }
		                	
		                    dialog.dismiss();
		                    
		                   

		                }
		            });
		            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

		                @Override
		                public void onClick(DialogInterface dialog, int which) {

		                    dialog.dismiss();
		                }
		            });

		            alert.show();
				            
				
				return true;
			}
	    	
	    	
	    	
		});
	  
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    IndMsgList=individualComMsgList();
		
	    setIndividualMessageAdapter(IndMsgList);
	    
	}
	 
	 
 static  ArrayList<IndividualMsgsWrapper> individualComMsgList(){
	    
	    
      DataBase object=  new DataBase(context);	 
      
      
      Cursor cursor = object.getIndividualMsgs(object, number);
      
       IndividualMsgList=new ArrayList<IndividualMsgsWrapper>();   
      
    if(cursor!=null){
      
      cursor.moveToFirst();
      
      
      do{
	   
      IndividualMsgsWrapper indMsginstance=new IndividualMsgsWrapper();     
		
      
      
      
      indMsginstance.set_id(cursor.getString(0));
        
      indMsginstance.set_name(cursor.getString(1));
		 
      indMsginstance.set_number(cursor.getString(2));
		
		 
		
      indMsginstance.set_date(cursor.getString(3));
		
      indMsginstance.set_time(cursor.getString(4));
		
      indMsginstance.set_content(cursor.getString(5));
		
      indMsginstance.set_type(cursor.getString(6));
		
      IndividualMsgList.add(indMsginstance);
						
			//Toast.makeText(getApplicationContext(), csr.getString(0), Toast.LENGTH_LONG).show();
		    //Toast.makeText(getApplicationContext(),csr.getString(2), Toast.LENGTH_LONG).show(); 
	        //Toast.makeText(getApplicationContext(),String.valueOf(noofSms), Toast.LENGTH_LONG).show(); 
	  
	  
  }while(cursor.moveToNext());
	  
	 	  cursor.close();
	 
	 	     
    } 
    
        return  IndividualMsgList;
    
	}

	  
	
	
static	private void setIndividualMessageAdapter(ArrayList<IndividualMsgsWrapper> IndividualMsgList) {
		// TODO Auto-generated method stub
		mAdapter = new IndividualMsgAdapter(context, IndividualMsgList);
		indlistv.setAdapter(mAdapter);
		 indlistv.setSelection(indlistv.getAdapter().getCount()-1);
	}
	
		
	
	
	
	
	  static void update(){
		  
		 	try{  
			
			 IndividualMsgList.clear();
		  ArrayList<IndividualMsgsWrapper> newList=individualComMsgList();
		     
			// Toast.makeText(context, "size of original list"+IndividualMsgList.size(), Toast.LENGTH_LONG).show();
		  
			 setIndividualMessageAdapter(IndividualMsgList);
			 
	 
			// mAdapter. notifyDataSetChanged();
			 		
		}catch(NullPointerException e){
			
			//Toast.makeText(context, "error in update", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	  }
	
	
	
	  
	
	  
public static  void notification(String name,String number,String msg){
		  
		  if(name.equals("Unknown Person")){
		    	name=number;
		    }
		  
		  
		  //Log.d("number", number);
		//  Log.d("message", msg);
		    
		 // Toast.makeText(context, name+number+" sattar "+msg, Toast.LENGTH_LONG).show();
		  
		 try{ 
		  Intent intent=new Intent(context, NotificationView.class);
			//Intent intent = new Intent(context, NotificationView.class);
		
			intent.putExtra("title", name);
			intent.putExtra("text", msg);
		  
		   
			
	 PendingIntent contentintent=PendingIntent.getActivity(context, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);
		  
		NotificationCompat.Builder mBuilder=(Builder) new NotificationCompat.Builder(context).setSmallIcon(R.drawable.smalllogo)
				.setContentTitle(name).setContentText(msg);
			
		Log.d("after number", number);
		
     mBuilder.setContentIntent(contentintent);
     
     mBuilder.setDefaults(Notification.DEFAULT_SOUND);
     mBuilder.setAutoCancel(true);
     
     NotificationManager mNotificationManager=
  		   (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
     
       mNotificationManager.notify(0, mBuilder.build());
	
	
		 }catch(NullPointerException e){
			
			// Toast.makeText(context, "null pointer in individual", Toast.LENGTH_SHORT).show();
			 e.printStackTrace();
		 }
	
}
	  
	  
	  
	  
	
	
}
