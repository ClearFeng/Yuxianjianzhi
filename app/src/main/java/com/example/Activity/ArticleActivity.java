package com.example.Activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


import Common.Common;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ArticleActivity extends Activity {
	TextView articleTitle, articlecontent;
	ImageButton backButton;
	RelativeLayout relativeLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.
				FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_article);
		Intent intent = getIntent();
		int position = intent.getIntExtra("position", 1);
		articleTitle = (TextView) findViewById(R.id.title);
		articleTitle.setText(intent.getStringExtra("title"));
		articlecontent = (TextView) findViewById(R.id.articlecontent);
		articlecontent.setText(readAssertResource(getApplicationContext(), position + ".txt"));
		backButton = (ImageButton) findViewById(R.id.back2);
		relativeLayout= (RelativeLayout) findViewById(R.id.relayout2);
		relativeLayout.getBackground().setAlpha(150);
		articlecontent.getBackground().setAlpha(150);

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

	}

	/**
	 * 从assert文件中读入txt
	 * @param context
	 * @param strAssertFileName
     * @return
     */
	public static String readAssertResource(Context context, String strAssertFileName) {
		try {
			//Return an AssetManager instance for your application's package
			InputStream is = context.getAssets().open(strAssertFileName);
			int size = is.available();

			// Read the entire asset into a local byte buffer.
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();

			// Convert the buffer into a string.
			String text = new String(buffer, "utf-8");
			return text;

		} catch (IOException e) {
			// Should never happen!
			throw new RuntimeException(e);
		}
	}



}



