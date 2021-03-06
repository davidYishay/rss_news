package com.example.side2;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ListView;

public class Weblink extends Activity {

	
	WebView wv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weblink);
		
		wv =(WebView)findViewById(R.id.webviewLink);
		
		String url;
		Intent iin= getIntent();
		Bundle b = iin.getExtras();
		
		 if(b!=null)
	         url =(String) b.get("url");
		 else
		  url="http://www.ynet.co.il/Integration/StoryRss544.xml";
		  WebSettings webSettings = wv.getSettings();
		  webSettings.setJavaScriptEnabled(true);
		  wv.loadUrl(url);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.weblink, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
