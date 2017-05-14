package Common;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Environment;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class Common {
	/**
	 * 改变按钮大小
	 * @param imageButton
	 * @param add 变大还是变小true为变大，false为变小
	 */
	public static void changeSize(ImageButton imageButton,boolean add){
		//获取当前控件的布局对象
	    RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) 
	    		imageButton.getLayoutParams();
		if(add){//按下
			params.leftMargin=params.leftMargin-5;
			params.topMargin=params.topMargin-5;
        	params.height=params.height+10;//设置当前控件布局的高度
        	params.width=params.width+10;
			imageButton.setLayoutParams(params);//将设置好的布局参数应用到控件中
		}else{//松开
			params.leftMargin=params.leftMargin+5;
			params.topMargin=params.topMargin+5;
			params.height=params.height-10;//设置当前控件布局的高度
			params.width=params.width-10;
			imageButton.setLayoutParams(params);//将设置好的布局参数应用到控件中
		}
	}
	

    private static double getScaling(int src, int des) {  
        /** 
         * 48 目标尺寸÷原尺寸 sqrt开方，得出宽高百分比 49 
         */  
        double scale = Math.sqrt((double) des / (double) src);  
        return scale;  
    }  
  
    public static int computeSampleSize(BitmapFactory.Options options,  
            int minSideLength, int maxNumOfPixels) {  
        int initialSize = computeInitialSampleSize(options, minSideLength,  
                maxNumOfPixels);  
  
        int roundedSize;  
        if (initialSize <= 8) {  
            roundedSize = 1;  
            while (roundedSize < initialSize) {  
                roundedSize <<= 1;  
            }  
        } else {  
            roundedSize = (initialSize + 7) / 8 * 8;  
        }  
  
        return roundedSize;  
    }  
  
    private static int computeInitialSampleSize(BitmapFactory.Options options,  
            int minSideLength, int maxNumOfPixels) {  
        double w = options.outWidth;  
        double h = options.outHeight;  
  
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math  
                .sqrt(w * h / maxNumOfPixels));  
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(  
                Math.floor(w / minSideLength), Math.floor(h / minSideLength));  
  
        if (upperBound < lowerBound) {  
            return lowerBound;  
        }  
  
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {  
            return 1;  
        } else if (minSideLength == -1) {  
            return lowerBound;  
        } else {  
            return upperBound;  
        }  
    }

    /**
     * 旋转图片
     */
    public static Bitmap adjustPhotoRotation(Bitmap bitmap, int orientationDegree) {


        Matrix matrix = new Matrix();
        matrix.setRotate(orientationDegree, (float) bitmap.getWidth() / 2,
                (float) bitmap.getHeight() / 2);
        float targetX, targetY;
        if (orientationDegree == 90) {
            targetX = bitmap.getHeight();
            targetY = 0;
        } else {
            targetX = bitmap.getHeight();
            targetY = bitmap.getWidth();
        }


        final float[] values = new float[9];
        matrix.getValues(values);


        float x1 = values[Matrix.MTRANS_X];
        float y1 = values[Matrix.MTRANS_Y];


        matrix.postTranslate(targetX - x1, targetY - y1);


        Bitmap canvasBitmap = Bitmap.createBitmap(bitmap.getHeight(), bitmap.getWidth(),
                Bitmap.Config.ARGB_8888);


        Paint paint = new Paint();
        Canvas canvas = new Canvas(canvasBitmap);
        canvas.drawBitmap(bitmap, matrix, paint);


        return canvasBitmap;

    }
}
