package com.example.smsservice;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Intent;

public class NotificationView extends Activity {

	TextView titletv,texttv;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification_view);
	

	
  try{	
	
	 NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
     
     notificationmanager.cancel(0);

    
     Intent i = getIntent();

   String  title = i.getStringExtra("title");
    String text = i.getStringExtra("text");

    
    Log.d(" sattar number", "this"+title);
	  Log.d("sattar message","that"+ text);
  
     
   // Toast.makeText(getApplicationContext(), title+" sattar "+text, Toast.LENGTH_SHORT).show();
    
     titletv = (TextView) findViewById(R.id.title);
     texttv = (TextView) findViewById(R.id.text);

     
     titletv.setText(title);
     texttv.setText(text);
  }catch(NullPointerException e){
	  
	 // Toast.makeText(getApplicationContext(), "null pointer", Toast.LENGTH_SHORT).show();
	  e.printStackTrace();
	  
  }
	
	}
}
