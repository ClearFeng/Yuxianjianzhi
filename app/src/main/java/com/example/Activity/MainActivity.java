package com.example.Activity;

import java.io.IOException;

import Common.Common;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	ImageButton imageButton1,imageButton2,imageButton3,imageButton4;
	ImageView settingButton;
	private Animation operatingAnim;
	private boolean onstart=true;
	AssetFileDescriptor fileDescriptor;
	MediaPlayer mp=new MediaPlayer();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams. 
	        		FLAG_FULLSCREEN ,WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		//设置音乐旋转动画
    	operatingAnim= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);  
        LinearInterpolator lin = new LinearInterpolator();  
        operatingAnim.setInterpolator(lin); 
        initMusic();

        mp.start();
		findView();
		
	}
	private void findView() {

		imageButton1=(ImageButton) findViewById(R.id.describle);
		imageButton2=(ImageButton) findViewById(R.id.learnCut);
		imageButton3=(ImageButton) findViewById(R.id.myWorks);
		imageButton4=(ImageButton) findViewById(R.id.nowCut);
		settingButton= (ImageView) findViewById(R.id.setting);
		ButtonListener btListener=new ButtonListener();
		imageButton1.setOnTouchListener(btListener);
		imageButton2.setOnTouchListener(btListener);
		imageButton3.setOnTouchListener(btListener);
		imageButton4.setOnTouchListener(btListener);
		settingButton.startAnimation(operatingAnim);
		settingButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(operatingAnim != null&&onstart){
					settingButton.clearAnimation();
                    mp.pause();
					onstart=false;
				} else {
					settingButton.startAnimation(operatingAnim);
					mp.start();
					onstart = true;

				}
			}
		});
		
		
	}
	class ButtonListener implements OnTouchListener{
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			int id=v.getId();
			switch(id){
			case R.id.describle:
				 if(event.getAction() == MotionEvent.ACTION_UP){ 
					 Common.changeSize(imageButton1,false);
					 Intent intent=new Intent(MainActivity.this,DescribleActivity.class);
					 startActivity(intent);
	             }else if(event.getAction() == MotionEvent.ACTION_DOWN){ 
	            	 Common.changeSize(imageButton1,true);
	             } 
				break;
			case R.id.learnCut:
				Toast.makeText(getApplicationContext(),"暂无教程",Toast.LENGTH_SHORT).show();
				if(event.getAction() == MotionEvent.ACTION_UP){ 
					 Common.changeSize(imageButton2,false);
	             }else if(event.getAction() == MotionEvent.ACTION_DOWN){ 
	            	 Common.changeSize(imageButton2,true);
	             } 
				break;
			case R.id.myWorks:
				if(event.getAction() == MotionEvent.ACTION_UP){ 
					 Common.changeSize(imageButton3,false);
					 startActivity(new Intent(MainActivity.this,WorksActivity.class));
	             }else if(event.getAction() == MotionEvent.ACTION_DOWN){ 
	            	 Common.changeSize(imageButton3,true);
	             } 
				break;
			case R.id.nowCut:
				if(event.getAction() == MotionEvent.ACTION_UP){ 
					 Common.changeSize(imageButton4,false);
					 startActivity(new Intent(MainActivity.this,CameraActivity.class));
	             }else if(event.getAction() == MotionEvent.ACTION_DOWN){ 
	            	 Common.changeSize(imageButton4,true);
	             } 
				break;
			}
			return false;
		}
	}
	private void initMusic(){		
		try {
			fileDescriptor = getAssets().openFd("music.mp3");
			mp.reset();
			mp.setDataSource(fileDescriptor.getFileDescriptor(),
					fileDescriptor.getStartOffset(),
					fileDescriptor.getLength());
			mp.prepare();


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mp.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer arg0) {
				try {
					mp.reset();
					mp.setDataSource(fileDescriptor.getFileDescriptor(),
							fileDescriptor.getStartOffset(),
							fileDescriptor.getLength());
					mp.prepare();
					mp.start();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(mp.isPlaying()){
			mp.reset();
		}
	}
}
	
	
	

