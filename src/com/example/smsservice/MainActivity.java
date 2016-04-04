package com.example.smsservice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import com.example.smsservice.R.drawable;
import com.example.smsservice.messagewrapper;
import com.example.smsservice.MessageListAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification.Action;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.YuvImage;
import android.graphics.drawable.Drawable;

public class MainActivity extends Activity {

	
    static  ArrayList<messagewrapper> MsgList,messageList,dummyList;
	static  public   MessageListAdapter mAdapter;
	static  Context context;
	static  ListView lstv;
	 SmsReceiver myReceiver;
	static DataBase obj;
	static Cursor crr;
	static int checkedCount=0; 
	 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		
		 lstv=(ListView)findViewById(R.id.listView);
		ImageButton newsms= (ImageButton)findViewById(R.id.newm);
		
		dummyList=new ArrayList<messagewrapper>();
		
       newsms.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent=new Intent(MainActivity.this,NewSms.class);
				startActivity(intent);
				finish();
				
			}
		});
		
		
          
      
		 lstv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		 
		 
		 lstv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				lstv.setLongClickable(true);
				view.setSelected(true);
				view.setPressed(true);
				
				return true;
			}
		});
		 
		 
	/*	 
		 lstv.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				view.setSelected(true);
				view.setPressed(true);
			
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		*/
		lstv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			
		
				Intent intent = new Intent(getApplicationContext() , IndividualMsgDisp.class);
			
			   intent.putExtra("monumber", messageList.get(position).get_number());
			   intent.putExtra("name",messageList.get(position).get_name());
			
				startActivity(intent);
				//view.setSelected(true);
				
			}});
		
		
		lstv.setMultiChoiceModeListener(new MultiChoiceModeListener() {
			
			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void onDestroyActionMode(ActionMode mode) {
				// TODO Auto-generated method stub
				//mAdapter.removeSelection();
			}
			
			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				// TODO Auto-generated method stub
				
				mode.getMenuInflater().inflate(R.menu.main, menu);
				return true;
			}
			
			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				// TODO Auto-generated method stub
				
				// Toast.makeText(context,"wtf1", Toast.LENGTH_LONG).show();
		
				 
				switch (item.getItemId()) {
				case R.id.delete:
					
					for(messagewrapper obje:dummyList){
						
						mAdapter.remove(obje);
						
					}
					
					checkedCount=0;
					
					mode.finish();
					return true;
		
				default:
					return false;
				}
			
				
				
			}
			
			@Override
			public void onItemCheckedStateChanged(ActionMode mode, int position,
					long id, boolean checked) {
				// TODO Auto-generated method stub
				
				//checkedCount =lstv.getCheckedItemCount();
				
				if(dummyList.isEmpty()){
					
					
					
					checkedCount++;
					
					mode.setTitle(checkedCount + " Selected");
				
				    dummyList.add(messageList.get(position));
						
				}else{
				
							     int cn=0;
							for(messagewrapper obje:dummyList){
								
								messagewrapper objec=messageList.get(position);
								
								if(obje.get_number().equals(objec.get_number())){
									
									cn++;
									
								}
							
							   }
							
				
									if(cn==1){
									checkedCount--;
									
									mode.setTitle(checkedCount + " Selected");
								
									dummyList.remove(messageList.get(position));
									
									}else{
										
										checkedCount++;
										
										mode.setTitle(checkedCount + " Selected");
									
										
									  dummyList.add(messageList.get(position));
									
									}
				
				
				    }	
				
			}
		});
		
		
		
		
       context=this;
		
       obj=new DataBase(context);
	
	
	
	  crr=obj.getDatatodisplay(obj);
	 
	  if(crr!=null&& crr.moveToFirst()){
			
		  MsgList=makeMessageList();
			
		    setMessageAdapter(MsgList);
		    
		}
	  else{	
	
   // Toast.makeText(context,"cursor is null", Toast.LENGTH_LONG).show();
	Intent intent = new Intent(context, NothingToDisplay.class);
	startActivity(intent);
	 finish();
    
    
    }
	
	}
	
	
	
 static 	ArrayList<messagewrapper>  makeMessageList(){
		
		
		Cursor csr;
	    int noofSms;
		
     //  DataBase obj=new DataBase(context);
	  	
		
		  crr=obj.getDatatodisplay(obj);
		 
		  
    messageList = new ArrayList<messagewrapper>();
	
    
    if(crr!=null&&crr.moveToFirst()){
		
		  	 
			 
		  do{	 
			  int id= crr.getInt(0);
			 
			  csr= obj.getData(obj, id);
			  csr.moveToFirst();
			  
			  noofSms = obj.getCountSms(obj, csr.getString(2));
			 
		      messagewrapper messageinstance=new messagewrapper();     
				
		      
		      
		      
		        messageinstance.set_id(csr.getInt(0));
		        
				messageinstance.set_name(csr.getString(1));
				 
				messageinstance.set_number(csr.getString(2));
				
				
				
				messageinstance.set_date(csr.getString(3));
				
				messageinstance.set_time(csr.getString(4));
				
				messageinstance.set_content(csr.getString(5));
				
				messageinstance.set_count(String.valueOf(noofSms));
				
				messageList.add(messageinstance);
								
					//Toast.makeText(getApplicationContext(), csr.getString(0), Toast.LENGTH_LONG).show();
				    //Toast.makeText(getApplicationContext(),csr.getString(2), Toast.LENGTH_LONG).show(); 
			        //Toast.makeText(getApplicationContext(),String.valueOf(noofSms), Toast.LENGTH_LONG).show(); 
			  
			  
		  }while(crr.moveToNext());
			  
			 	  crr.close();
			 	  csr.close();
			 
	 }
		
	
	return messageList;
		
		
	}
	
	
	
	
	
static	private void setMessageAdapter(ArrayList<messagewrapper> messageList) {
		// TODO Auto-generated method stub
		mAdapter = new MessageListAdapter(context, messageList);
		lstv.setAdapter(mAdapter);
		
		checkedCount=0;
		
		
		
		Collections.sort(messageList, new Comparator<messagewrapper>()
				{

					@Override
					public int compare(messagewrapper lhs, messagewrapper rhs) {
						// TODO Auto-generated method stub
						return   rhs.get_id()-lhs.get_id();
					}
				   
				});
		
		
	}

	
	
	static void updateFrontList(){
		  
	 	try{  
				messageList.clear();
	  ArrayList<messagewrapper> newList=makeMessageList();
	  
	    setMessageAdapter(messageList);
		 
	   
	    
		 lstv.setSelection(lstv.getAdapter().getCount()-1);
		 
		// mAdapter.notifyDataSetChanged();
		 		
	}catch(Exception e){
		
		Toast.makeText(context, "error in update", Toast.LENGTH_LONG).show();
		e.printStackTrace();
	}
  }

	
	
	public  void goTonothingDisplay(){
		 Intent intent = new Intent(MainActivity.this, NothingToDisplay.class);
 		startActivity(intent);
 		 finish();
	}
	

	
}
