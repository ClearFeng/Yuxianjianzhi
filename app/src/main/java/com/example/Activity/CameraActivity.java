package com.example.Activity;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import jp.co.cyberagent.android.gpuimage.GPUImage;



public class CameraActivity extends Activity implements View.OnClickListener{

    private CameraSurfaceView mCameraSurfaceView;
    private RectOnCamera mRectOnCamera;
    private ImageButton takePicBtn;
    private RectOnCamera rectOnCamera;
    private GPUImage gpuImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera);
        mCameraSurfaceView = (CameraSurfaceView) findViewById(R.id.cameraSurfaceView);
        mRectOnCamera = (RectOnCamera) findViewById(R.id.rectOnCamera);
        takePicBtn= (ImageButton) findViewById(R.id.takePicBtn);
        rectOnCamera= (RectOnCamera) findViewById(R.id.rectOnCamera);
        takePicBtn.setOnClickListener(this);


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.takePicBtn:
                Toast.makeText(getApplicationContext(),"正在生成图片，请勿抖动...",Toast.LENGTH_LONG).show();
                rectOnCamera.setVisibility(View.VISIBLE);
                mCameraSurfaceView.takePicture();
                break;
            default:
                break;
        }
    }
}
