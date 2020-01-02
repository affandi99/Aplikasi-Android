package com.affandi.deteksi;

import android.os.Bundle;
import android.util.Log;
import android.app.Activity;
import com.google.android.gms.ads.*; 

public class AdsActivity extends Activity {

private InterstitialAd interstitialAd;
private static final String LOG_TAG = "Andaired App";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ads);
		
		 // Create an ad.
	    interstitialAd = new InterstitialAd(this);
	    interstitialAd.setAdUnitId("ca-app-pub-9512477645283696/8336703830");
	    
	    AdRequest adRequest = new AdRequest.Builder()
        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
        //.addTestDevice("INSERT_YOUR_HASHED_DEVICE_ID_HERE")
        .build();

	    //Load the interstitial ad.
	    interstitialAd.loadAd(adRequest);
	    
	    interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                //add was shown
            	  if (interstitialAd.isLoaded()) {
            	      interstitialAd.show();
            	   } 
            	  else {
            	      Log.d(LOG_TAG, "Interstitial ad was not ready to be shown.");
            	      //finish();
            	  }
            }

            @Override
            public void onAdClosed() {
                // Proceed to the next level.
               finish();
            }
            
            @Override
            
            public void onAdFailedToLoad (int errorCode)
            {
            	
            //Ad was not loaded and lets close this window
            	finish();
            }
        });

	    
	}

	

}
