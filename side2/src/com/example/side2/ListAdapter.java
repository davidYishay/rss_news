package com.example.side2;

import java.util.ArrayList;
import java.util.List;

import android.R.array;
import android.app.Dialog;
import android.content.ClipData.Item;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.AvoidXfermode.Mode;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ListAdapter extends ArrayAdapter<String> implements View.OnClickListener,View.OnLongClickListener
{
	private Context context;
	private final ArrayList<String>web;
	private final ArrayList<String> title;
	private final ArrayList<String> subsite;
	private final ArrayList<Bitmap> bitmapImage;
	Bitmap imageFirst;
	
	public ListAdapter(Context context, ArrayList<String> web,ArrayList<String>title,ArrayList<String> subSite, ArrayList<Bitmap>bitmapImage) 
	{
		super((Context)context, R.layout.list_side, web);
		this.context = context;
		this.web = web;
		this.title = title;
		this.subsite = subSite;
		this.bitmapImage = bitmapImage;
		
		imageFirst= BitmapFactory.decodeResource(context.getResources(),R.drawable.tower_tree);
		
	}

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
    	
        View v = convertView;
        TextView txtTitle = null;
		ImageView imageView = null;
		TextView   subTitle = null;
        if (v == null) 
        {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_side, null,true);
        }
	
		txtTitle = (TextView) v.findViewById(R.id.txt);
		imageView = (ImageView) v.findViewById(R.id.img);
	    subTitle = (TextView) v.findViewById(R.id.textViewSubcaption);
		txtTitle.setText(title.get(position));
		subTitle.setText(subsite.get(position));
	     
		try 
		{
		     if(bitmapImage.size()>=position)
		     {
			   imageView.setImageBitmap(bitmapImage.get(position));
		     }
    	} 
		catch (Exception e)
		{
			imageView.setImageBitmap(imageFirst);
		}
		
		v.setTag(position);
		v.setOnClickListener(this);
		v.setOnLongClickListener(this);
        return v;
    }
	@Override
	public void onClick(View v) 
	{
		int pos= (Integer)v.getTag();  
		String url =web.get(pos);
//    	Log.i("url string",url);
    	
        Intent intent=new Intent(context, Weblink.class);
        intent.putExtra("url", url);
        context.startActivity(intent);		
	}
	@Override
	public boolean onLongClick(View vClickLong) 
	{
		final int pos= (Integer)vClickLong.getTag();
	    Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.dialog_news);
		dialog.setTitle("Add the article to the your list?");

		Button dialogButton = (Button) dialog.findViewById(R.id.btnNews);
		dialogButton.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View vDialog) 
			{
			     String result = "('"+title.get(pos)+"','"+web.get(pos)+"');";
			     	MainActivity.UserDatabase.execSQL("INSERT INTO "
			    	     + "userNews" 
			    	     + " (title,web)"
			    	     + " VALUES " +result);
				Toast.makeText(context, "The article saved", Toast.LENGTH_SHORT).show();
			}
		});
		dialog.show();
		return true;
	}

}