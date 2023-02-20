package com.example.evacunation;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.internal.FallbackServiceBroker;

public class CovidActivity extends AppCompatActivity {
    public WebView mWebView;
    private ProgressBar progressBar;


    @SuppressLint({"SdCardPath", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid);

        Toast.makeText(this, "Loading Please Wait...", Toast.LENGTH_SHORT).show();


        ProgressBar progressBar = findViewById(R.id.pbar);
        this.progressBar = progressBar;
        progressBar.setMax(100);


        mWebView = findViewById(R.id.webview);
        WebSettings settings = this.mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLightTouchEnabled(true);
        settings.setGeolocationEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        String appCachePath = this.getCacheDir().getAbsolutePath();
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        if ( !isNetworkAvailable() )  //offline
            settings.setCacheMode( WebSettings.LOAD_CACHE_ONLY );

        mWebView.loadUrl("https://infogram.com/1py2j0qjp6kqglt3mz5j6xj6w5a1xjmrwp");

        this.mWebView.setWebChromeClient(new WebChromeClient() { // from class: com.example.evacunation.weather.4
            @Override // android.webkit.WebChromeClient
            public void onProgressChanged(WebView webView2, int i) {
                CovidActivity.this.progressBar.setProgress(i);
                if (i == 100) {
                    CovidActivity.this.progressBar.setVisibility(View.INVISIBLE);
                } else {
                    CovidActivity.this.progressBar.setVisibility(View.VISIBLE);
                }
            }

            @Override // android.webkit.WebChromeClient
            public void onGeolocationPermissionsShowPrompt(String str, GeolocationPermissions.Callback callback) {
                super.onGeolocationPermissionsShowPrompt(str, callback);
                callback.invoke(str, true, false);
            }
        });
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(
                CONNECTIVITY_SERVICE );
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();}

}
