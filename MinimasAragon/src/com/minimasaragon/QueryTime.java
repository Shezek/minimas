package com.minimasaragon;


import io.backbeam.Backbeam;
import io.backbeam.BackbeamObject;
import io.backbeam.FetchCallback;
import io.backbeam.Query;

import java.util.List;

import dataBase.SQLiteHelper;
import utils.Util;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class QueryTime extends ActionBarActivity {

Util util= new Util();
Context context;
TextView tf1;
TextView tf2;
Spinner edadcb;
Spinner pruebacb;
Spinner sexocb;
Spinner piscinacb;

	  @Override
	  protected void onCreate(Bundle savedInstanceState) 
	  {
	    super.onCreate(savedInstanceState);
	    supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
	    setContentView(R.layout.activity_query);
	    context=this;
	   
	    initUI();
	    
        
	  }
	  
	  public void initUI(){
		  
		  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		  getSupportActionBar().setTitle("Consulta de mínimas");
		  Button doQuery=(Button)findViewById(R.id.btnQuery);
		 tf1=(TextView)findViewById(R.id.tiempo1);
		  tf2=(TextView)findViewById(R.id.tiempo2);
		  edadcb=(Spinner)findViewById(R.id.spnEdad);
		 pruebacb=(Spinner)findViewById(R.id.spnPrueba);
		  sexocb=(Spinner)findViewById(R.id.spnSexo);
		  piscinacb=(Spinner)findViewById(R.id.spnPiscina);
		 
			
			
	 doQuery.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(isNetworkAvailable()==true){
					new readData().execute();
				}else{util.showInfoDialog(context, "Lo sentimos", "Es necesaria conexión a internet");}
				
			}

		});
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
	  
	  private class readData extends AsyncTask<Void, Void, Void> {

		  @Override
		  protected void onPreExecute() {
		   super.onPreExecute();
		   setSupportProgressBarIndeterminateVisibility(true);
		   Backbeam.setProject("minimasaragon");
			Backbeam.setEnvironment("dev");
			Backbeam.setContext(context);
			// Create the API keys in the control panel of your project
			Backbeam.setSharedKey("dev_f66422a74ed05c72c61c3ed98d22998f077ea27e");
			Backbeam.setSecretKey("dev_af601bd0f2e687dc3157d4dc59ecc171275e903777f2a186f2b9f56feba9fe52a27968409d36949e");
		  }

		  @Override
		  protected Void doInBackground(Void... params) {
			  
		   return null;
		  }

		  @Override
		  protected void onPostExecute(Void result) {
		   super.onPostExecute(result);
		   leer();
		  
		  }
		 }
	  
	  public void leer(){
		  
		  
		  final String piscina = piscinacb.getSelectedItem().toString();
			String prueba = pruebacb.getSelectedItem().toString();
			final String sexo= sexocb.getSelectedItem().toString();
			
			
			Query queryCommerce = new Query("absolutosminimasverano");
			//queryCommerce.setQuery("where piscina=? and prueba=? and cronometro=?", piscina,prueba,"manual")
			queryCommerce.setQuery("where prueba = ?",prueba)
					.fetch(4, 0, new FetchCallback() {
						@Override
						public void success(List<BackbeamObject> objects,
								int totalCount, boolean fromCache) {
							if(totalCount==0){tf1.setText("Prueba no existente en los campeonatos."); tf2.setText("");
							setSupportProgressBarIndeterminateVisibility(false);}
							for (BackbeamObject datos : objects) {
								if(sexo.equals("masculino")&datos.getString("piscina").toString().equals(piscina)){
									/*util.showToast(context, "chicos: "+datos.getString("piscina")+"m. "+datos.getString("tiempochicos")+
											" cronometro:"+datos.getString("cronometro"));*/
									if(datos.getString("cronometro").equals("electronico")){
									tf2.setText("chicos: "+datos.getString("piscina")+"m. "+datos.getString("tiempochicos")+
											" cronometro:"+datos.getString("cronometro"));}else{tf1.setText("chicos: "+datos.getString("piscina")+"m. "+datos.getString("tiempochicos")+
													" cronometro:"+datos.getString("cronometro"));}
									/*Log.i("consulta","chicos: "+commerce.getString("piscina")+"m. "+commerce.getString("tiempochicos")+
											" cronometro:"+commerce.getString("cronometro"));*/
								}else if(sexo.equals("femenino")&datos.getString("piscina").toString().equals(piscina)){
									//Log.i("consulta","chicas: "+commerce.getString("tiempochicas"));
									/*util.showToast(context, "chicas: "+datos.getString("piscina")+"m. "+datos.getString("tiempochicas")+
											" cronometro:"+datos.getString("cronometro"));*/
									if(datos.getString("cronometro").equals("electronico")){
									tf1.setText("chicas: "+datos.getString("piscina")+"m. "+datos.getString("tiempochicas")+
											" cronometro:"+datos.getString("cronometro"));}else{tf2.setText("chicas: "+datos.getString("piscina")+"m. "+datos.getString("tiempochicas")+
													" cronometro:"+datos.getString("cronometro"));}
								}
								setSupportProgressBarIndeterminateVisibility(false);
									
									util.log("fff ha entrado en el success busqueda(2)");
								
							}
						}

					});
	  }
	  
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu items for use in the action bar
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.main, menu);
			return super.onCreateOptionsMenu(menu);
		}
		private boolean isNetworkAvailable() {
		    ConnectivityManager connectivityManager 
		          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
		}
	  
	}