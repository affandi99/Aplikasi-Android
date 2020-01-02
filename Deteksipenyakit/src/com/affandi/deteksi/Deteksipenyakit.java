package com.affandi.deteksi;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.webkit.WebSettings;
import android.app.ProgressDialog;
import com.google.android.gms.ads.*;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

public class Deteksipenyakit extends Activity {


  /** The view to show the ad. */
   private AdView adView;
   private WebView webView;
   private RelativeLayout _layout;
   public boolean needads=true;
   public boolean iresumed=true;
     /** The interstitial ad. */

  
   private ProgressDialog progressDialog;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    { 
    	
        
        super.onCreate(savedInstanceState);
    
        setContentView(R.layout.main);
        _layout = (RelativeLayout) findViewById(R.id.main_layout);
        webView = (WebView) findViewById(R.id.webView);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setPluginsEnabled(true);
		webView.getSettings().setSupportZoom(true);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		webView.getSettings().setDomStorageEnabled(true);
		webView.getSettings().setAppCacheMaxSize(1024*1024*16); //16Mb for cache		
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);
		webView.setWebChromeClient(new MyWebChromeClient());
		webView.setWebViewClient(new MyWebViewClient());
		String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
		webView.getSettings().setAppCachePath(appCachePath);
		progressDialog = ProgressDialog.show(webView.getContext(), "Loading...", "Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER    );
		
		webView.loadUrl(getString(R.string.my_url));
	 
		AdView adView = (AdView) this.findViewById(R.id.ad);
	    AdRequest adRequest = new AdRequest.Builder()
	        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
	        //.addTestDevice("E627597603CAB2E23B0F1830001AF158")
	        .build();
	    adView.loadAd(adRequest);
	  
	    
	    
	    if (needads) {
	   //  Intent ii = new Intent(LameXAdmob.this, com.andaired.admob.AdsActivity.class);
		// startActivity(ii);
		// needads=false;
	   // iresumed=false;
	    }
	
    }
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.layout.menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        
        switch (item.getItemId())
        {
        case R.id.menu_back:
        	// Single menu item is selected do something
        	// Ex: launching new activity/screen or show alert message
        	if (webView.canGoBack()) {
     			webView.goBack();    			
     		}
            return true;
        case R.id.menu_forward:
        	if (webView.canGoForward()) {
     			webView.goForward();    			
     		}
        	else
        	Toast.makeText(webView.getContext(), "Can't go forward now!", Toast.LENGTH_SHORT).show();
            
            return true;
        case R.id.menu_reload:
        	webView.reload();
            return true;
        case R.id.menu_home:
        	webView.loadUrl(getString(R.string.my_url));
            return true;
        case R.id.menu_stop:
        	webView.stopLoading();
            return true;
        
        case R.id.menu_quit:
        	moveTaskToBack (true);
            return true;    
        default:
            return super.onOptionsItemSelected(item);
        }
    }
  

    
    private class MyWebViewClient extends WebViewClient {
        @Override
         public boolean shouldOverrideUrlLoading(WebView webView, String url)      
        {
            webView.loadUrl(url);
            return true;
        }
    }

    private class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if(progressDialog!=null){
                if ( newProgress >=99 ) {
                    progressDialog.dismiss();
                    
                } else {
                    progressDialog.setMessage(newProgress + " % loaded");
                }
            }
            super.onProgressChanged(view, newProgress);
        }
    } 
  
    // To handle "Back" key press event for WebView to go back to previous screen.
 	@Override
 	public boolean onKeyDown(int keyCode, KeyEvent event) 
 	{
 		if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
 			webView.goBack();
 			return true;
 		}
		else if ((keyCode == KeyEvent.KEYCODE_BACK) && !webView.canGoBack())
		{
		new AlertDialog.Builder(this)
        .setIcon(android.R.drawable.ic_dialog_info)
        .setTitle("Quit application")
        .setMessage("Are you really sure?")
        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Stop the activity
                Deteksipenyakit.this.finish(); 
            
}
        })
        .setNegativeButton("Nope", null)
        .show();
 		
 	}
	return super.onKeyDown(keyCode, event);
	
 }
    
	/** Called before the activity is destroyed. */
  @Override
  public void onDestroy() {
	  	  
    // Destroy the AdView.
    if (adView != null) {
      adView.destroy();
    }
    _layout.removeView(webView);
    webView.removeAllViews();
    webView.clearHistory();
    webView.clearCache(false);
    webView.destroy();
    super.onDestroy();
  }
  



	
	
	
	
	 @Override
	    protected void onRestart() {
	        super.onRestart();
	        // The activity has become visible (it is now "resumed").
	    if (iresumed)
	    {
Intent i = new Intent(Deteksipenyakit.this,com.affandi.deteksi.AdsActivity.class);
startActivity(i);
	   iresumed=false;
	    }
	    }

}
