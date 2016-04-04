package com.example.smsservice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NothingToDisplay extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nothing_to_display);
		
		
		
		 Button newsm=(Button)findViewById(R.id.newsm);
	
				
				newsm.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						Intent intent=new Intent(NothingToDisplay.this,NewSms.class);
						startActivity(intent);
						finish();
						
					}
				});
				
							
		
		
	}

	
	}
