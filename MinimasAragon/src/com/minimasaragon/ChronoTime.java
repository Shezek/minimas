package com.minimasaragon;


import dataBase.SQLiteHelper;
import utils.Util;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class ChronoTime extends ActionBarActivity {

Util util= new Util();
Context context;
Chronometer chrono;
Button startB,finishB,lapB,okB;
ImageButton saveB;
TextView tiempo;
EditText etName;
Spinner spnEstilo,spnSexo,spnNacimiento,spnPiscina;
LinearLayout opciones;
int i=0;
boolean started=false, opened=false;

	  @Override
	  protected void onCreate(Bundle savedInstanceState) 
	  {
	    super.onCreate(savedInstanceState);
	    supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
	    setContentView(R.layout.activity_chrono);
	    context=this;
	    
	    initUI();
	    
        
	  }
	  
	  public void initUI(){
		 getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		 getSupportActionBar().setTitle("Cronómetro");
		chrono=(Chronometer)findViewById(R.id.chronometer1);
		startB=(Button)findViewById(R.id.startButton);
		finishB=(Button)findViewById(R.id.finishButton);
		lapB=(Button)findViewById(R.id.lapButton);
		saveB=(ImageButton)findViewById(R.id.saveButton);
		spnEstilo=(Spinner)findViewById(R.id.Spinner03);
		spnSexo=(Spinner)findViewById(R.id.Spinner02);
		spnNacimiento=(Spinner)findViewById(R.id.Spinner01);
		spnPiscina=(Spinner)findViewById(R.id.spinner1);
		opciones=(LinearLayout)findViewById(R.id.opcionesGuardar);
		okB=(Button)findViewById(R.id.guardar);
		etName=(EditText)findViewById(R.id.etName);
		opciones.requestFocus();
		 startB.setOnClickListener(new OnClickListener() {
			
			 
			 
			 
				@Override
				public void onClick(View v) {
					
					
					if(started==true){
						
						chrono.stop();
						saveB.setVisibility(0);
						startB.setText("restart");
						started=false;
						
					}else
					{ 
						if(startB.getText().equals("start")){
							chrono.setBase(SystemClock.elapsedRealtime());
						}
						chrono.start();
						finishB.setEnabled(true);
						lapB.setEnabled(true);
						saveB.setVisibility(View.GONE);
						opciones.setVisibility(View.GONE);
						okB.setVisibility(View.GONE);
						opened=false;
						startB.setText("stop");
						started=true;
					}
					
					
				}

			});
		 
		 finishB.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					i=0;
					started=false;
					startB.setText("start");
					finishB.setEnabled(false);
					lapB.setEnabled(false);
					saveB.setVisibility(View.GONE);
					opciones.setVisibility(View.GONE);
					opened=false;
					okB.setVisibility(View.GONE);
					chrono.stop();
					chrono.setBase(SystemClock.elapsedRealtime());
					for(int j=0;j<5;j++){
						switch(j){
						case 0:tiempo=(TextView)findViewById(R.id.tv1);tiempo.setVisibility(View.GONE); break;
						case 1:tiempo=(TextView)findViewById(R.id.tv2);tiempo.setVisibility(View.GONE);break;
						case 2:tiempo=(TextView)findViewById(R.id.tv3);tiempo.setVisibility(View.GONE);break;
						case 3:tiempo=(TextView)findViewById(R.id.tv4);tiempo.setVisibility(View.GONE);break;
				
						}
					}
					
					util.showToast(context, String.valueOf(chrono.getText()));
					
				}

			});
		 saveB.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if(opened==false){
					opciones.setVisibility(0);
					okB.setVisibility(0);
					opened=true;
					}else{
						opciones.setVisibility(View.GONE);
						okB.setVisibility(View.GONE);
						opened=false;
					}
				}
			});
		 
		 okB.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					 if(etName.getText().length()>0){
					guardar();
					opciones.setVisibility(View.GONE);
					okB.setVisibility(View.GONE);
					util.showToast(context,"Nadador guardado");}else{    
						  util.showInfoDialog(context, "datos incompletos", "Por favor, Indique nombre del nadador");}
				}
			});
		 lapB.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					
					switch(i){
					case 0:tiempo=(TextView)findViewById(R.id.tv1);i++; tiempo.setText(String.valueOf(chrono.getText())); tiempo.setVisibility(0); break;
					case 1:tiempo=(TextView)findViewById(R.id.tv2);i++; tiempo.setText(String.valueOf(chrono.getText())); tiempo.setVisibility(0);break;
					case 2:tiempo=(TextView)findViewById(R.id.tv3);i++; tiempo.setText(String.valueOf(chrono.getText())); tiempo.setVisibility(0);break;
					case 3:tiempo=(TextView)findViewById(R.id.tv4);i=0; tiempo.setText(String.valueOf(chrono.getText())); tiempo.setVisibility(0);break;
					
					}
					
					
				}

			});
	  }
	  
	  public void guardar(){
		  
		 
		  SQLiteHelper usdbh =
		            new SQLiteHelper(this, "TableNadadores", null, 1);
		 
		        SQLiteDatabase db = usdbh.getWritableDatabase();
		 
		        //Si hemos abierto correctamente la base de datos
		        if(db != null)
		        {
  
		                //Insertamos los datos en la tabla Usuarios
		                db.execSQL("INSERT INTO Nadadores(Nombre,Edad,Piscina,Prueba,Tiempo) " +
		                           "VALUES ('" + etName.getText() +"','"+spnNacimiento.getSelectedItem().toString()+"','"+
		                		spnPiscina.getSelectedItem().toString()+"','"+spnEstilo.getSelectedItem().toString()+"','"+String.valueOf(chrono.getText())+"')");
		                
		                db.close();
		        }else{util.showToast(context, "error");}
		 
		        	
		           
		        
	  
		  
		    
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