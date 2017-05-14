package com.example.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Common.Common;


public class ShowWorkActivity extends Activity implements View.OnClickListener {
    byte[] data;
    Bitmap srcBitmap,caoBitmap;
    Context context;
    ImageView imageView;
    int len;
    int red;
    int greed;
    int blue;
    int apla;
    int pix;
    private ImageButton buttonOk,buttonCancel;
    //OpenCV库加载并初始化成功后的回调函数
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            // TODO Auto-generated method stub
            switch (status){
                case BaseLoaderCallback.SUCCESS:
                    if (status == LoaderCallbackInterface.SUCCESS ) {
                        // now we can call opencv code !
                          initsrc();//获取图片然后做一系列变换
                        System.out.println("33333333333333333333333333333333");
                        imageView.setImageBitmap(caoBitmap);
                    } else {
                        super.onManagerConnected(status);
                    }
                    break;
                default:
                    super.onManagerConnected(status);
                    break;
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.acticity_showwork);
        context = getApplicationContext();
        imageView = (ImageView) findViewById(R.id.showImage);
        buttonOk= (ImageButton) findViewById(R.id.button_ok);
        buttonCancel= (ImageButton) findViewById(R.id.button_cancel);
        buttonOk.setOnClickListener(this);
        buttonCancel.setOnClickListener(this);



    }

    public void saveImage(Bitmap bitmap2){
                String sdCardDirectory=Environment.getExternalStorageDirectory().toString();
                System.out.println(sdCardDirectory);

                //创建一个文件
                File file=new File(sdCardDirectory+"/Pictures/");
                //创建一个文件夹
                file.mkdir();
                String name=(new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date());
                String filename=file+"/"+name+".jpg";

                try {
                    FileOutputStream fous=new FileOutputStream(filename);
                    caoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fous);// 把数据写入文件
                    fous.flush();
                    fous.close();
                    caoBitmap.recycle();
                    Toast.makeText(getApplicationContext(),"图片已保存到我的作品",Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

    public void initsrc(){
        Mat rgbMat = new Mat();
        byte [] bis=CameraSurfaceView.data1;
        srcBitmap=BitmapFactory.decodeByteArray(bis, 0, bis.length) ;
        srcBitmap=Common.adjustPhotoRotation(srcBitmap,90);

        Utils.bitmapToMat(srcBitmap, rgbMat);
        caoBitmap=MyHoughLine(rgbMat);
        changeBitmapColor();
        System.out.println("2222222222222222222222222222");
    }

    /*Hough直线检测*/
    public Bitmap MyHoughLine(Mat src){
        Bitmap resultHough;

        Mat grayMat = new Mat();
        Mat cannyEdges = new Mat();
        Mat lines = new Mat();
        Mat origination = new Mat(src.size(), CvType.CV_8UC1);
        src.copyTo(origination);//拷贝
        /*通过Canny得到边缘图*/
        Imgproc.cvtColor(origination,grayMat,Imgproc.COLOR_BGR2GRAY);
        Imgproc.Canny(grayMat,cannyEdges,50,300);
        /*获得直线图*/
        Imgproc.HoughLinesP(cannyEdges,lines,1,Math.PI/180,10,0,50);
        Mat houghLines = new Mat();
        houghLines.create(cannyEdges.rows(),cannyEdges.cols(),CvType.CV_8UC1);
        /*在图线的上绘制直线*/
        for(int i = 0;i < lines.rows();i++){
            double[] points = lines.get(i,0);
            if(null != points){
                double x1,y1,x2,y2;
                x1 = points[0];
                y1 = points[1];
                x2 = points[2];
                y2 = points[3];
                Point pt1 = new Point(x1,y1);
                Point pt2 = new Point(x2,y2);
            /*在一幅图像上绘制直线*/
                Imgproc.line(houghLines,pt1,pt2,new Scalar(55,100,195),5);
            }
        }
        resultHough = Bitmap.createBitmap(src.cols(),src.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(houghLines,resultHough);
        return resultHough;
    }

    public void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_2_0, this, mLoaderCallback);
        } else {
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    public void changeBitmapColor(){
        try {
            Image image = new Image(this,caoBitmap);
            len = image.getWidth() * image.getHeight();
            int[] imageARGB = new int[len];
            int[] newimage = new int[len];
            image.getPixel(imageARGB, 0, 0);
            for (int i = 0; i < image.getHeight(); i++)
                for (int j = 0; j < image.getWidth(); j++) {
                    if (imageARGB[i * image.getWidth() + j] != 0) {
                        //apla = ((imageARGB[i * image.getWidth() + j]&0xaa000000)>>24);
                        red = ((imageARGB[i * image.getWidth() + j] & 0x00ff0000) >> 16);
                        greed = ((imageARGB[i * image.getWidth() + j] & 0x0000ff00) >> 8);
                        blue = ((imageARGB[i * image.getWidth() + j] & 0x000000ff));
                        // int apla = ((5 * 255 / 10) << 24) | 0x00ffffff;
                        if (red == 0 && greed == 0 & blue == 0) {
                            red = 255;
                            greed = 0;
                            blue = 0;
                            // pix =((red<<16)|(greed<<8)|blue)&apla;
                        }else{
                            red = 255;
                            greed = 255;
                            blue = 255;
                        }
                        pix = (red << 16) | (greed << 8) | blue;
                        newimage[i * image.getWidth() + j] = pix;
                    } else
                        newimage[i * image.getWidth() + j] = imageARGB[i
                                * image.getWidth() + j];
                }

            caoBitmap=image.CreateImage(newimage,image.getWidth(),image.getHeight());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_ok:
                try {
                    saveImage(caoBitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finish();
                break;
            case R.id.button_cancel:
                finish();
                break;
            default:
                break;
        }
    }


}
