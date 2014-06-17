package com.minimasaragon;


import java.util.ArrayList;

import utils.Util;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import dataBase.SQLController;
import dataBase.SQLiteHelper;

public class TableInfo extends ActionBarActivity {

Util util= new Util();
Context context;
TableLayout table_layout;
SQLController sqlcon;
Button borrar;

	  @Override
	  protected void onCreate(Bundle savedInstanceState) 
	  {
	    super.onCreate(savedInstanceState);
	    supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
	    setContentView(R.layout.activity_table);
	    context=this;
	    sqlcon = new SQLController(this);
	    initUI();
	    
        
	  }
	  
	  public void initUI(){

		  
		  table_layout = (TableLayout) findViewById(R.id.tablelayout1);
		  borrar=(Button)findViewById(R.id.borrarB);
		  BuildTable();
		  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		  borrar.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					new RemoveTable().execute();
				}
			});
	  }
	 
	  private void BuildTable() {

		  sqlcon.open();
		  Cursor c = sqlcon.readEntry();

		  int rows = c.getCount();
		  int cols = c.getColumnCount();

		  c.moveToFirst();
		  ArrayList<Integer> columnas=new ArrayList<Integer>();
		  // outer for loop
		  for (int i = 0; i < rows; i++) {
			  
		   TableRow row = new TableRow(this);
		   row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
		     LayoutParams.WRAP_CONTENT));
		   columnas.add(i);
		   // inner for loop
		   for (int j = 0; j < cols; j++) {

		    TextView tv = new TextView(this);
		    tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
		      LayoutParams.WRAP_CONTENT));
		    tv.setGravity(Gravity.LEFT);
		    tv.setTextSize(18);
		    tv.setPadding(5, 5, 5, 5);
		    
		    if(j==0){
		    	if(c.getString(j).length()>10){
		    		tv.setText(columnas.get(i)+"  "+c.getString(j).substring(0, 11)+"...");
		    		}else{ 
		    		tv.setText(columnas.get(i)+"  "+c.getString(j));}
		    }else{if(j==4){tv.setText(c.getString(j)+"                                      "
		    		+ "                         ");}else{ tv.setText(c.getString(j));}}
		    if(i%2==0){tv.setBackgroundColor(Color.LTGRAY);}
		    row.addView(tv);

		   }

		   c.moveToNext();

		   table_layout.addView(row);

		  }
		  sqlcon.close();
		 }

	  private class RemoveTable extends AsyncTask<Void, Void, Void> {

		  @Override
		  protected void onPreExecute() {

		   super.onPreExecute();

		   table_layout.removeAllViews();

		  
		  }

		  @Override
		  protected Void doInBackground(Void... params) {


		   // inserting data
			  SQLiteHelper usdbh =
			            new SQLiteHelper(context, "TableNadadores", null, 1);
			 
			        SQLiteDatabase db = usdbh.getWritableDatabase();
			        db.execSQL("DROP TABLE IF EXISTS Nadadores");
			        db.execSQL("CREATE TABLE Nadadores (nombre TEXT, edad TEXT, piscina TEXT, prueba TEXT, tiempo TEXT)");
			        
			        
		   sqlcon.open();
		  
		   // BuildTable();
		   return null;
		  }

		  @Override
		  protected void onPostExecute(Void result) {
		   super.onPostExecute(result);
		   BuildTable();
		  
		  }
		 }
		 
	  @Override
		public boolean onOptionsItemSelected(MenuItem item) {
			switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;
			case R.id.action_flag:
				Intent denuncia= new Intent(context,Denuncias.class);
				startActivity(denuncia);
				return true;
			default:
				return super.onOptionsItemSelected(item);
			}
		}
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu items for use in the action bar
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.main, menu);
			return super.onCreateOptionsMenu(menu);
		}
	  
	}