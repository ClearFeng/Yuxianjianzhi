package MyUtil;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/5/13.
 */

public class MyUtil {
    /**
     * 设置Activity全屏
     * @param context 上下文
     */
    public static void setFullScreen(Context context){
        ((Activity)context).requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 全屏显示
        ((Activity)context).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 获取屏幕宽高的数组，index[0]为宽，index[1]为高
     * @param context 上下文
     */
    private int[] getScreenMetrix(Context context) {

        WindowManager WM = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        WM.getDefaultDisplay().getMetrics(outMetrics);
        int[] screenSize=new int[]{outMetrics.widthPixels,outMetrics.heightPixels};
        return screenSize;
    }

    /**
     * 将Bitmap保存到本地
     * @param bitmap 要保存的图片
     * @param context 上下文
     */
    public void saveImage(Bitmap bitmap,Context context){
        //注意此为意义上的外存卡，即如果你设置默认存储路径为sd卡，那么这个路径就是你实际的手机内存反之为sd卡路径
        String sdCardDirectory= Environment.getExternalStorageDirectory().toString();
        //创建一个文件
        File file=new File(sdCardDirectory+"/Pictures/");
        //创建一个文件夹
        file.mkdir();
        String name=(new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date());
        String filename=file+"/"+name+".jpg";

        try {
            FileOutputStream fous=new FileOutputStream(filename);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fous);// 把数据写入文件
            fous.flush();
            fous.close();
            bitmap.recycle();
            Toast.makeText(context,"图片已保存到我的作品",Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 改变控件大小
     * @param imageButton 要改变大小的按钮（以按钮为例）
     * @param add 变大还是变小true为变大，false为变小
     */
    public static void changeSize(ImageButton imageButton, boolean add){
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

}
