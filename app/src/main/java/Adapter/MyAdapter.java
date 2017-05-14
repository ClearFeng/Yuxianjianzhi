package Adapter;

import com.example.Activity.R;



import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter{


	private String[] stringList;
	private Context context;
	public MyAdapter(String[] stringList, Context context) {
		super();
		this.stringList = stringList;
		this.context = context;
	}
	@Override
	public int getCount() {
		if(stringList==null)
		return 0;
		return stringList.length;
	}

	@Override
	public Object getItem(int position) {
		
		return stringList[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view=View.inflate(context, R.layout.describle_list_item, null);			
		TextView describleView=(TextView) view.findViewById(R.id.des_item);
		//添加数据
		describleView.setText(position+1+". "+stringList[position]);
		return view;
	}

}
