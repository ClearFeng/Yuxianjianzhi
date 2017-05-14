package com.example.Activity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import Adapter.WorkItemAdapter;
import Bean.Work;
import Common.Common;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;

public class WorksActivity extends Activity {
	ListView workListView;
	ImageButton backButton, homeButton;
	List<String> imgPathList;
	WorkItemAdapter adapter;
	RelativeLayout relativeLayout,relativeLayout2;
	public static List<Work> list = new ArrayList<Work>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_works);
		workListView = (ListView) findViewById(R.id.work_listview);
		relativeLayout= (RelativeLayout) findViewById(R.id.relayout3);
		relativeLayout2= (RelativeLayout) findViewById(R.id.relayout4);
		relativeLayout2.getBackground().setAlpha(150);
		relativeLayout.getBackground().setAlpha(150);
		new Thread(){
			public void run(){
				initData();
				adapter= new WorkItemAdapter(list,getApplicationContext());
				Message message=new Message();
				handler.sendMessage(message);
			}
		}.start();
		

		workListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent=new Intent(WorksActivity.this,ShowWorksActivity.class);
				intent.putExtra("imgindex",position);
				startActivity(intent);
			}
		});

		backButton = (ImageButton) findViewById(R.id.back3);
		homeButton = (ImageButton) findViewById(R.id.home3);
		backButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					Common.changeSize(backButton, false);
					finish();
				} else if (event.getAction() == MotionEvent.ACTION_DOWN) {
					Common.changeSize(backButton, true);
				}
				return false;
			}
		});
		homeButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					Common.changeSize(homeButton, false);
					finish();
				} else if (event.getAction() == MotionEvent.ACTION_DOWN) {
					Common.changeSize(homeButton, true);
				}
				return false;
			}
		});
	}


	protected void onDestroy() {
		super.onDestroy();
		list.clear();
		System.out.println("----------------onDestroy()---------------");
	}

	public Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			workListView.setAdapter(adapter);
		};
	};
	/**
	 * 从本地文件夹获取图片资源
	 */
	private void initData() {
		imgPathList = new ArrayList<String>();
		imgPathList = getImagePathFromSD();
		for (int i = 0; i < imgPathList.size(); i++) {
			String path = imgPathList.get(i);
			list.add(new Work(path, "102452552"));
		}
	}

	/**
	 * 从sd卡获取图片资源
	 * 
	 * @return
	 */
	
	private List<String> getImagePathFromSD() {
		// 图片列表
		List<String> imagePathList = new ArrayList<String>();
		// 得到sd卡内image文件夹的路径 File.separator(/)
		String filePath = Environment.getExternalStorageDirectory().toString()
				+ File.separator + "Pictures";
		// 得到该路径文件夹下所有的文件
		File fileAll = new File(filePath);
		File[] files = fileAll.listFiles();
		// 将所有的文件存入ArrayList中,并过滤所有图片格式的文件
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (checkIsImageFile(file.getPath())) {
				imagePathList.add(file.getPath());
			}
		}
		// 返回得到的图片列表
		return imagePathList;
	}

	/**
	 * 检查扩展名，得到图片格式的文件
	 *
	 * @param fName
	 *            文件名
	 * @return
	 */
	private boolean checkIsImageFile(String fName) {
		boolean isImageFile = false;
		// 获取扩展名
		String FileEnd = fName.substring(fName.lastIndexOf(".") + 1,
				fName.length()).toLowerCase();
		if (FileEnd.equals("jpg") || FileEnd.equals("png")
				|| FileEnd.equals("gif") || FileEnd.equals("jpeg")
				|| FileEnd.equals("bmp")) {
			isImageFile = true;
		} else {
			isImageFile = false;
		}
		return isImageFile;
	}
}
