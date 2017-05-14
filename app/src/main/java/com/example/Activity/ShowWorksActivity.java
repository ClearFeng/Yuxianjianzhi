package com.example.Activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by Administrator on 2017/5/2.
 */

public class ShowWorksActivity extends Activity implements View.OnClickListener {
    ImageView imageView;
    ImageButton preButton,nextButton;
    int currentIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_show_works);
        imageView= (ImageView) findViewById(R.id.show_my_works);
        preButton= (ImageButton) findViewById(R.id.pre);
        nextButton= (ImageButton) findViewById(R.id.next);
        preButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        currentIndex=getIntent().getIntExtra("imgindex",0);
        showImg(currentIndex);

    }

    private void showImg(int imgindex) {
        Picasso.with(getApplicationContext())
                .load(new File(WorksActivity.list.get(imgindex).getSrcPath()))
                .placeholder(R.drawable.jgl)
                .error(R.drawable.ic_launcher)
                .into(imageView);
    }
    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.pre:
                if(currentIndex!=0){
                    currentIndex--;
                    showImg(currentIndex);
                }else{
                    Toast.makeText(getApplicationContext(),"这已经是第一张图片了",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.next:
                if(currentIndex<WorksActivity.list.size()-1){
                    currentIndex++;
                    showImg(currentIndex);
                }else{
                    Toast.makeText(getApplicationContext(),"这已经是最后一张图片了",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
