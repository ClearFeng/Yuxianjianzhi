package com.example.Activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.IOException;
import java.io.InputStream;

public class Image {
 public Bitmap bitmap;  //图片资源对象

public Image(Context context, Bitmap bitmap){
        this.bitmap = bitmap;
        }
/**
 * 通过图片文件名来获得图片，主要用于图片在Asset目录下时
 * @param context
 * @param name    图片名
 * @param scaleW  图片宽的缩放比例
 * @param scaleH  图片高的缩放比例
 * @throws IOException
 */
public Image(Context context, String name, float scaleW, float scaleH) throws IOException {
        InputStream is = context.getAssets().open(name);
        bitmap = BitmapFactory.decodeStream(is);
        is.close();
        bitmap = setScaleSize(bitmap, scaleW,scaleH);
        }
public int getWidth(){
        return bitmap.getWidth();
        }
public int getHeight(){
        return bitmap.getHeight();
        }

/**
 * 将bitmap的像素值放入数组
 * @param array
 * @param x
 * @param y
 * @return
 */
public int[] getPixel(int[] array,int x,int y){
                bitmap.getPixels(array, 0, getWidth(), 0, 0, getWidth(), getHeight());
                return array;
        }
/**
 * 通过一个像素数组创建Bitmap
 * @param array
 * @param w
 * @param h
 * @return
 */
public Bitmap CreateImage(int[] array,int w,int h){
        Bitmap image = Bitmap.createBitmap(array, w, h, Bitmap.Config.RGB_565);
        return image;
        }
/**
 * 图片缩放
 * @param bitmap
 * @param arg0
 * @param arg1
 * @return
 */
private Bitmap setScaleSize(Bitmap bitmap,float arg0,float arg1){
        Matrix matrix = new Matrix();
        matrix.postScale(arg0,arg1);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0,getWidth(),getHeight(), matrix,true);
        return bitmap;
        }
/**
 * 图片内存回收
 *
 * @param image
 * @return
 */
public static void free(Image image) {
        try {
        if(image.bitmap != null){
        if(image.bitmap.isRecycled()==false) {//如果没有回收
        //回收图片所占的内存
        image.bitmap.recycle();
        if(image.bitmap.isRecycled()== true){
        image.bitmap = null;
        }
        }else{
        image.bitmap = null;
        }
        }else{
        image.bitmap = null;
        //return null;
        }

        } catch (Exception e) {
        e.printStackTrace();
        }
        //return image;
        }
}

