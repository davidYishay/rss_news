package com.example.side2;




import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	
	static SQLiteDatabase UserDatabase;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pic_strat);
		
		 UserDatabase = this.openOrCreateDatabase("DatabaseNews", MODE_PRIVATE, null); /// level 1 create database 
		 UserDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + "userNews" + " (title TEXT, web TEXT);"); /// level 2 create table in databse 
		
	;
         
		    Handler handler = new Handler(); 
		    handler.postDelayed(new Runnable() {
		         @Override 
		         public void run()
		         { 
		        		Intent ii=new Intent(MainActivity.this, Side2Activity.class);
		                startActivity(ii);
		         } 
		    }, 2000); 
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
