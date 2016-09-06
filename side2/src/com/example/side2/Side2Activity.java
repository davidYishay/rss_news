package com.example.side2;

import android.app.Activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;


import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Side2Activity extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

	private NavigationDrawerFragment mNavigationDrawerFragment;
	private CharSequence mTitle;
    boolean fragmentIsAlive = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_side2);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
	}

	@Override
	public void onNavigationDrawerItemSelected(int position)
	{
		if(!fragmentIsAlive)
		{
			fragmentIsAlive = true;
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
			.commit();
		}
	}

	public void onSectionAttached(int number) 
	{
//		 ***do action in side menu***
//		switch (number) 
//		{
//		case 1:
//			mTitle = getString(R.string.title_section1);
//			break;
//		case 2:
//			mTitle = getString(R.string.title_section2);
//			break;
//		case 3:
//			mTitle = getString(R.string.title_section3);
//			Toast.makeText(getApplicationContext(), "333",Toast.LENGTH_SHORT).show();
//			break;
//		}
	}

	public void restoreActionBar()
	{
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.side2, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) 
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	public static class PlaceholderFragment extends Fragment {

		
		private static final String ARG_SECTION_NUMBER = "section_number";

		Context context;
		ListAdapter adapter;
		ListView list;
	    ArrayList <String>  web;
	    ArrayList <String>  title;
	    ArrayList <String>  subSite;
	    ArrayList <Bitmap>  bitmapImage;
	    SwipeRefreshLayout mSwipeRefreshLayout;
	    
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_side2, container, false);
			
			 context =getContext();
			 web = new ArrayList<String>();
			 title = new ArrayList<String>();
			 subSite = new ArrayList<String>(); 
		     bitmapImage = new ArrayList<Bitmap>();
		     mSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_of_list_refresh_layout);
		     
		     adapter =new ListAdapter(context, web,title,subSite,bitmapImage);
			 list=(ListView)rootView.findViewById(R.id.fragmentListView);
		     list.setAdapter(adapter);
		     
		     
		     startDownloadArticles();
		     
		     mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() 
			    {
			        @Override
			        public void onRefresh() 
			         {
			           startDownloadArticles();
			           mSwipeRefreshLayout.setRefreshing(false);
			         }
			  
			      });
			
			return rootView;
		}
		public void startDownloadArticles()
		{
			    new  BackgroundParser().execute("http://www.ynet.co.il/Integration/StoryRss544.xml");	
				Log.v("level", "**1**");
				Toast.makeText(context, "start download articles...", Toast.LENGTH_SHORT).show();
		}
		class BackgroundParser extends AsyncTask<String,String, Integer>
		{
		    String titles="";
			String webSite="";
			String imgSite="";
			String subSite="";
			int articles =0;
			boolean getArticleReady=false;
			@Override
	     	protected Integer doInBackground(String... params)
	     	{
			
	    		Log.v("level", "**2**");
				String urlString= params[0];
				
				try 
				{
					Log.v("level", "**3**");	
								
					URL url=new URL(urlString);
					XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
					XmlPullParser parser = factory.newPullParser();
					InputStream is=url.openStream();
					parser.setInput(is, null);
					
					int eventType=parser.getEventType();
					String tagName="";
				
					
					boolean item=false;
					boolean title=false;
					while(eventType!=XmlPullParser.END_DOCUMENT)
					{	
						if(eventType==XmlPullParser.START_TAG)
						{
							tagName=parser.getName();
							if(tagName.equals("title"))
								title=true;
							if(tagName.equals("description"))
								item =true;
						}
						if(eventType==XmlPullParser.END_TAG)
						{
							tagName=parser.getName();
							if(tagName.equals("title"))
								title=false;
							if(tagName.equals("description"))
								item=false;
						}
						if(eventType==XmlPullParser.TEXT&&title)
						{		
							   titles=parser.getText();
						}
						if(eventType==XmlPullParser.TEXT&&item)
						{
						      String text =parser.getText();
						      if(text.contains("<div>"))
						      {
						    	  int start=text.indexOf("http");
						    	  int end =text.indexOf("'>");
						    	  webSite=text.substring(start,end);
//						    	  Log.i("textItem",webSite);
						    	  
						    	  int startImg=text.indexOf("http://images1");
						    	  int endImg=text.indexOf("' ");
						    	  imgSite=text.substring(startImg,endImg);
//						    	  Log.i("textItemImg",imgSite);
						    	
						    	  int startSub=text.indexOf("</div>")+6;
						    	  int endSub=text.length();
						    	  subSite=text.substring(startSub,endSub);
//						    	  Log.i("textSub",subSite);
						    	  
						    	    PlaceholderFragment.this.title.add(titles);
								    web.add(webSite);
								    PlaceholderFragment.this.subSite.add(subSite);
								    articles++;
								    publishProgress("");
						    	  
						      }
						      if(articles>0)
						      {
						    	  try
						    	  {
						    		 
	                                 if(imgSite.length()>0)
	                                 {
						    		    Log.d("hare","start bitmap from url");
							    	    URL urlPng = new URL(imgSite);
							            HttpURLConnection connection = (HttpURLConnection) urlPng.openConnection();
							            connection.setDoInput(true);
							            connection.connect();
							            InputStream input = connection.getInputStream();
							            Bitmap bmp = BitmapFactory.decodeStream(input);
							            Log.i("bitmapARRAYlist","h: "+bmp.getHeight()+"w: " +bmp.getWidth()) ;
							            bitmapImage.add(bmp); 
	                                 }
						    		  
						    	  }
						    	  catch (IOException e)
						    	  {
						    		  Log.d("hare","problem ");
						    	  }
						      }
						}
						Log.i("BackgroundParse",articles+") "+isCancelled());
						eventType=parser.next();
					}
				}
				catch (Exception e) 
				{
					//do something 
				}
				return articles;		
			}
			@Override
			protected void onPostExecute(Integer result)
			{
				super.onPostExecute(result);
				Toast.makeText(context, "amount news: "+result, Toast.LENGTH_LONG).show();
				Log.i("size bitmap","size of image: "+ bitmapImage.size()+" **********************");
			}
			@Override
			protected void onPreExecute()
			{
				super.onPreExecute();
				    adapter.clear();
			}
			@Override
			protected void onProgressUpdate(String...strings)
			{		
				super.onProgressUpdate();
				adapter.notifyDataSetChanged();
		    }
			
		}

		@Override
		public void onAttach(Activity activity) 
		{
			super.onAttach(activity);
			((Side2Activity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
		}
	}

}
