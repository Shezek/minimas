package com.minimasaragon;

import utils.Util;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class Webs extends ActionBarActivity {

Util util= new Util();
Context context;
	  @Override
	  protected void onCreate(Bundle savedInstanceState) 
	  {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_webs);
	    context=this;
	    initUI();
	  }
	  
	  public void initUI(){
		  
		  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		  getSupportActionBar().setTitle("Webs relacionadas");
		  TextView fan=(TextView)findViewById(R.id.fan);
		 
		  
		  fan.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.fanaragon.com/"));
					startActivity(browserIntent);
					
					
				}

			});
	  }
	  
	  @Override
		public boolean onOptionsItemSelected(MenuItem item) {
			switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;
			
			default:
				return super.onOptionsItemSelected(item);
			}
		}
	  
	  
	  
		
	}