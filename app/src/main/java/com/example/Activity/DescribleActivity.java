package com.example.Activity;

import Adapter.MyAdapter;
import Common.Common;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class DescribleActivity extends Activity{
	ListView listView;
	RelativeLayout relativeLayout;
	ImageButton backButton,homeButton;
	String[] list={"剪纸艺术的起源1","剪纸艺术的起源2","剪纸艺术的起源3","剪纸艺术的起源4",
			"剪纸艺术的起源5","剪纸艺术的起源6","剪纸艺术的起源7","剪纸艺术的起源8",
			"剪纸艺术的起源9","剪纸艺术的起源10","剪纸艺术的起源11","剪纸艺术的起源12",
			"剪纸艺术的起源13","剪纸艺术的起源14","剪纸艺术的起源15","剪纸艺术的起源16"}; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams. 
	        		FLAG_FULLSCREEN ,WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.activity_describle);
		relativeLayout= (RelativeLayout) findViewById(R.id.relayout);
	    listView=(ListView) findViewById(R.id.listview);
		listView.getBackground().setAlpha(150);
		relativeLayout.getBackground().setAlpha(150);
	    MyAdapter adapter=new MyAdapter(list, getApplicationContext());
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				if(position==0){
					Intent intent=new Intent(DescribleActivity.this,ArticleActivity.class);
					intent.putExtra("title", list[position]);
					intent.putExtra("position", position);
					startActivity(intent);
				}else{
					Toast.makeText(getApplicationContext(),"没有文章",Toast.LENGTH_SHORT).show();
				}
			}
		});
		backButton=(ImageButton) findViewById(R.id.back);
		homeButton=(ImageButton) findViewById(R.id.home1);
		backButton.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_UP){ 
					 Common.changeSize(backButton,false);
					 finish();
	             }else if(event.getAction() == MotionEvent.ACTION_DOWN){ 
	            	 Common.changeSize(backButton,true);
	             } 
				return false;
			}
		});
		homeButton.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction() == MotionEvent.ACTION_UP){ 
					 Common.changeSize(homeButton,false);
					 finish();
	             }else if(event.getAction() == MotionEvent.ACTION_DOWN){ 
	            	 Common.changeSize(homeButton,true);
	             } 
				return false;
			}
		});
	}
	
}
