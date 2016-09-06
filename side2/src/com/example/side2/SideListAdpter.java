package com.example.side2;

import java.util.ArrayList;


import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SideListAdpter  extends ArrayAdapter<String> implements OnClickListener, OnLongClickListener
{

    Context context;
    ArrayList<String>  articles;
    ArrayList<String>  web;
    
    
	public SideListAdpter(Context context, ArrayList<String> articles,ArrayList<String>web) 
	{
		super(context,R.layout.side_test,articles);	
		Log.i("sideAdpter", "cons");
		this.context = context;
		this.articles = articles;
		this.web = web;
	}
	
	   @Override
		public View getView(int position, View convertView, ViewGroup parent) 
	   {
			View rowView = convertView;
			TextView title = null;
			ImageView imageView = null;
			if (rowView == null) 
			{
				    LayoutInflater vi;
		            vi = LayoutInflater.from(getContext());
		            rowView = vi.inflate(R.layout.side_test, null,true);
			}
			imageView = (ImageView) rowView.findViewById(R.id.imgSideTest);
			title = (TextView) rowView.findViewById(R.id.textViewTitleSide);
			
			title.setText(articles.get(position));
			
		    rowView.setTag(position);
			rowView.setOnClickListener(this);
			rowView.setOnLongClickListener(this);
			
			return rowView;
		}

	@Override
	public int getCount() {
//		Log.i("sideAdpter", "count");
		return articles.size();
	}

	@Override
	public String getItem(int position) {
//		Log.i("sideAdpter", "getItem");
		return getItem(position);
	}

	@Override
	public void onClick(View v)
	{
		   int pos= (Integer)v.getTag();  
		   String url =web.get(pos);
//    	   Log.i("url string",url);
		   Intent intent=new Intent(context, Weblink.class);
	       intent.putExtra("url", url);
	       context.startActivity(intent);	
	}

	@Override
	public boolean onLongClick(View v) 
	{
		int pos= (Integer)v.getTag();
		MainActivity.UserDatabase.delete("userNews", "title = ?", new String[] {articles.get(pos)});
		Toast.makeText(context, "This article has been deleted from the reading list", Toast.LENGTH_SHORT).show();
		return true;
	}

	
   
}
