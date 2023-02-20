package com.example.evacunation;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

/* loaded from: classes.dex */
public class earthquakesweb extends Fragment {
    private WebView mWebView;
    private ProgressBar progressBar;
    private TextView textView;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_earthquakesweb, viewGroup, false);
        WebView webView = (WebView) inflate.findViewById(R.id.webview);
        this.mWebView = webView;
        webView.requestFocus();
        ProgressBar progressBar = (ProgressBar) inflate.findViewById(R.id.pbar);
        this.progressBar = progressBar;
        progressBar.setMax(100);
        WebSettings settings = this.mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLightTouchEnabled(true);
        settings.setGeolocationEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);

        ((Button) inflate.findViewById(R.id.goback)).setOnClickListener(new View.OnClickListener() { // from class: com.example.evacunation.earthquakesweb.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (earthquakesweb.this.mWebView.canGoBack()) {
                    earthquakesweb.this.mWebView.goBack();
                }
            }
        });
        ((Button) inflate.findViewById(R.id.gofwd)).setOnClickListener(new View.OnClickListener() { // from class: com.example.evacunation.earthquakesweb.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (earthquakesweb.this.mWebView.canGoForward()) {
                    earthquakesweb.this.mWebView.goForward();
                }
            }
        });
        this.mWebView.setWebViewClient(new WebViewClient() { // from class: com.example.evacunation.earthquakesweb.3
            @Override // android.webkit.WebViewClient
            public void onPageStarted(WebView webView2, String str, Bitmap bitmap) {
                super.onPageStarted(webView2, str, bitmap);
            }

            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView webView2, String str) {
                super.onPageFinished(earthquakesweb.this.mWebView, str);
                earthquakesweb.this.progressBar.setVisibility(View.INVISIBLE);
            }

            @Override // android.webkit.WebViewClient
            public void onReceivedError(WebView webView2, int i, String str, String str2) {
                super.onReceivedError(earthquakesweb.this.mWebView, i, str, str2);
//                Toast.makeText(earthquakesweb.this.getActivity(), str, Toast.LENGTH_SHORT).show();
            }
        });
        this.mWebView.loadUrl("https://earthquake.usgs.gov/earthquakes/map/?extent=4.87268,116.78467&extent=19.43611,127.771&baseLayer=satellite&list=false");
        this.mWebView.setWebChromeClient(new WebChromeClient() { // from class: com.example.evacunation.earthquakesweb.4
            @Override // android.webkit.WebChromeClient
            public void onProgressChanged(WebView webView2, int i) {
                earthquakesweb.this.progressBar.setProgress(i);
                if (i == 100) {
                    earthquakesweb.this.progressBar.setVisibility(View.INVISIBLE);
                } else {
                    earthquakesweb.this.progressBar.setVisibility(View.VISIBLE);
                }
            }

            @Override // android.webkit.WebChromeClient
            public void onGeolocationPermissionsShowPrompt(String str, GeolocationPermissions.Callback callback) {
                super.onGeolocationPermissionsShowPrompt(str, callback);
                callback.invoke(str, true, false);
            }
        });
        TextView textView = (TextView) inflate.findViewById(R.id.textView5);
        this.textView = textView;
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.example.evacunation.earthquakesweb.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                earthquakesweb.this.mWebView.loadUrl("https://earthquake.usgs.gov/earthquakes/map/?extent=4.87268,116.78467&extent=19.43611,127.771&baseLayer=satellite&list=false");
            }
        });
        return inflate;
    }
}
