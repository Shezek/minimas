package com.minimasaragon;



import io.backbeam.Backbeam;
import io.backbeam.BackbeamObject;
import io.backbeam.ObjectCallback;
import utils.Util;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Denuncias extends ActionBarActivity {

	Util util = new Util();
	Context context;
	EditText denunciaet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_denuncia);

		context = this;

		Backbeam.setProject("minimasaragon");
		Backbeam.setEnvironment("dev");
		Backbeam.setContext(context);
		// Create the API keys in the control panel of your project
		Backbeam.setSharedKey("dev_f66422a74ed05c72c61c3ed98d22998f077ea27e");
		Backbeam.setSecretKey("dev_af601bd0f2e687dc3157d4dc59ecc171275e903777f2a186f2b9f56feba9fe52a27968409d36949e");

		initUI();
	}

	public void initUI() {

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle("Inserte su denuncia");
		Button denuncia = (Button) findViewById(R.id.BDenunciar);
		denunciaet = (EditText) findViewById(R.id.etDenuncia);
		
		denuncia.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(isNetworkAvailable()==true){
			denunciar();}else{util.showInfoDialog(context, "Lo sentimos", "Es necesaria conexión a internet");}
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
	
	private void denunciar(){
		if (denunciaet.getText().toString().length() == 0) {
			util.showInfoDialog(context, "Datos incompletos",
					"Inserte motivo de denuncia, por favor.");
		} else {
			setSupportProgressBarIndeterminateVisibility(true);
			final BackbeamObject denuncia = new BackbeamObject(
					"denuncias");

			denuncia.setString("denuncia", denunciaet.getText()
					.toString());
			denuncia.save(new ObjectCallback() {
				@Override
				public void success(BackbeamObject offer) {
					setSupportProgressBarIndeterminateVisibility(false);
					util.showToast(context, "Denuncia realizada");
					finish();

				}

			});
		}
	}
	
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

}