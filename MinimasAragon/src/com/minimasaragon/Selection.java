package com.minimasaragon;

import java.util.ArrayList;

import model.DrawerModel;
import utils.Util;
import adapter.DrawerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class Selection extends ActionBarActivity {
Button swimReferences,chrono,tableInfo;
DrawerLayout drawer;
ActionBarDrawerToggle drawerToggle;
ListView list;
Util util= new Util();
Context context;
	  @Override
	  protected void onCreate(Bundle savedInstanceState) 
	  {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_selection);
	    context=this;
	    initUI();
	  }
	  
	  public void initUI(){
		  
		  drawer=(DrawerLayout)findViewById(R.id.drawer_layout);
		  list=(ListView)findViewById(R.id.left_drawer);
		  swimReferences=(Button)findViewById(R.id.swimButton);
		  chrono=(Button)findViewById(R.id.chronoButton);
		  tableInfo=(Button)findViewById(R.id.TableInfoB);
			// load slide menu items
			
		  String[] navMenuTitles = getResources().getStringArray(R.array.listArray);
		  ArrayList<DrawerModel> navDrawerItems = new ArrayList<DrawerModel>();
		  LayoutInflater inflater = LayoutInflater.from(this);
		  View search = inflater.inflate(R.layout.header, null);
		  list.addHeaderView(search);
			
		  navDrawerItems.add(new DrawerModel(navMenuTitles[0],R.drawable.web));
		  navDrawerItems.add(new DrawerModel(navMenuTitles[1],R.drawable.info));
		  navDrawerItems.add(new DrawerModel(navMenuTitles[2],R.drawable.logout));
		 

		  list.setAdapter(new DrawerAdapter(context, navDrawerItems));
		  
		  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		 
		  drawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
					drawer, /* DrawerLayout object */
					R.drawable.drawer_icon, /* nav drawer image to replace 'Up'caret*/
					R.string.description, /* "open drawer" description for accessibility */
					R.string.action_settings /* "close drawer" description for accessibility */
					);

					drawer.setDrawerListener(drawerToggle);
	
					list.setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, int pos,long arg3) {
							drawerOpener();
							switch (pos) {
							case 0: 
								break;
							case 1: Intent web=new Intent(context,Webs.class);
								startActivity(web);
								break;
							case 2:
								util.showInfoDialog(context, "Mínimas",
										"Aplicacion desarrollada por Arturo Tajahuerce. 2014");
								break;

							case 3:
								android.os.Process.killProcess(android.os.Process.myPid());
								break;
							
							}

						}
					});		
					
	 swimReferences.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent query=new Intent(context,QueryTime.class);
				startActivity(query);
				
			}

		});
	 chrono.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent query=new Intent(context,ChronoTime.class);
				startActivity(query);
				
			}

		});
	 tableInfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent query=new Intent(context,TableInfo.class);
				startActivity(query);
				
			}

		});

	  }
	  
	  @Override
		public boolean onOptionsItemSelected(MenuItem item) {
			switch (item.getItemId()) {
			case android.R.id.home:
				drawerOpener();
				return true;
			case R.id.action_flag:
				Intent denuncia= new Intent(context,Denuncias.class);
				startActivity(denuncia);
				return true;
			default:
				return super.onOptionsItemSelected(item);
			}
		}
	  
	  public void drawerOpener() {
			if (drawer.isDrawerOpen(list)) {
				drawer.closeDrawer(list);
			} else {
				drawer.openDrawer(list);
			}
		}
	  
	  
	  
	  @Override
		public boolean onKeyDown(int keycode, KeyEvent e) {
			switch (keycode) {
			case KeyEvent.KEYCODE_MENU:
				drawerOpener();
				return true;
			}
			return super.onKeyDown(keycode, e);
		}
	  
	  
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu items for use in the action bar
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.main, menu);
			return super.onCreateOptionsMenu(menu);
		}
		
		 @Override
		    protected void onPostCreate(Bundle savedInstanceState) {
		        super.onPostCreate(savedInstanceState);
		        // Sync the toggle state after onRestoreInstanceState has occurred.
		        drawerToggle.syncState();
		    }
	}