package Adapter;

import java.io.File;
import java.util.List;

import com.example.Activity.R;
import com.squareup.picasso.Picasso;

import Bean.Work;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class WorkItemAdapter extends BaseAdapter{
	List<Work> worklist;
	ImageView workImg;
	TextView workName;
	Context context;
	public WorkItemAdapter(List<Work> list, Context context){
		super();
		this.worklist = list;
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return worklist.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view=View.inflate(context, R.layout.work_list_item, null);
		workImg=(ImageView) view.findViewById(R.id.workimg);
		workName=(TextView) view.findViewById(R.id.workname);
		//添加数据
//		workImg.setImageBitmap(worklist.get(position).getImgBitmap());
        //最简单的加载方式，load里面为图片的url（当然也可以是本地资源啊什么的），into进image控件里面去。
		Picasso.with(context).load(new File(worklist.get(position).getSrcPath())).into(workImg);
		//将图片切成长宽都是100 加载到控件上，适合确定控件长宽
		Picasso.with(context).load(new File(worklist.get(position).getSrcPath())).resize(100, 100).centerCrop().into(workImg);
		//添加空白和占位图片。加载图片中有一个占位图片，加载失败错误显示另一张图片
		Picasso.with(context).load(new File(worklist.get(position).getSrcPath())).placeholder(R.drawable.ic_launcher).error(R.drawable.bg).into(workImg);
		//汇总常用的方法,直接拿这个用就行了
		Picasso.with(context).load(new File(worklist.get(position).getSrcPath())).placeholder(R.drawable.ic_launcher).error(R.drawable.bg).resize(100,100).centerCrop().into(workImg);
		workName.setText(worklist.get(position).getImgName());
		return view;
	}

}
