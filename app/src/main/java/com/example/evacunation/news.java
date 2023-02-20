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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/* loaded from: classes.dex */
public class news extends Fragment {
    private WebView mWebView;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView textView;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_news, viewGroup, false);
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


        ((Button) inflate.findViewById(R.id.goback)).setOnClickListener(new View.OnClickListener() { // from class: com.example.evacunation.news.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (news.this.mWebView.canGoBack()) {
                    news.this.mWebView.goBack();
                }
            }
        });
        ((Button) inflate.findViewById(R.id.gofwd)).setOnClickListener(new View.OnClickListener() { // from class: com.example.evacunation.news.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (news.this.mWebView.canGoForward()) {
                    news.this.mWebView.goForward();
                }
            }
        });
        this.mWebView.setWebViewClient(new WebViewClient() { // from class: com.example.evacunation.news.3
            @Override // android.webkit.WebViewClient
            public void onPageStarted(WebView webView2, String str, Bitmap bitmap) {
                super.onPageStarted(webView2, str, bitmap);
            }

            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView webView2, String str) {
                super.onPageFinished(news.this.mWebView, str);
                news.this.progressBar.setVisibility(View.INVISIBLE);
                news.this.swipeRefreshLayout.setRefreshing(false);
            }

            @Override // android.webkit.WebViewClient
            public void onReceivedError(WebView webView2, int i, String str, String str2) {
                super.onReceivedError(news.this.mWebView, i, str, str2);
            }
        });
        this.mWebView.loadUrl("https://news.google.com/topics/CAAqJggKIiBDQkFTRWdvSkwyMHZNRGwzWW5CeUVnVmxiaTFIUWlnQVAB?hl=en-PH&gl=PH&ceid=PH%3Aen");
        this.mWebView.setWebChromeClient(new WebChromeClient() { // from class: com.example.evacunation.news.4
            @Override // android.webkit.WebChromeClient
            public void onProgressChanged(WebView webView2, int i) {
                news.this.progressBar.setProgress(i);
                if (i == 100) {
                    news.this.progressBar.setVisibility(View.INVISIBLE);
                } else {
                    news.this.progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override // android.webkit.WebChromeClient
            public void onGeolocationPermissionsShowPrompt(String str, GeolocationPermissions.Callback callback) {
                super.onGeolocationPermissionsShowPrompt(str, callback);
                callback.invoke(str, true, false);
            }
        });
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) inflate.findViewById(R.id.swiper);
        this.swipeRefreshLayout = swipeRefreshLayout;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() { // from class: com.example.evacunation.news.5
            @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
            public void onRefresh() {
                news.this.mWebView.reload();
            }
        });
        TextView textView = (TextView) inflate.findViewById(R.id.sitename);
        this.textView = textView;
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.example.evacunation.news.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                news.this.mWebView.loadUrl("https://news.google.com/topics/CAAqJggKIiBDQkFTRWdvSkwyMHZNRGwzWW5CeUVnVmxiaTFIUWlnQVAB?hl=en-PH&gl=PH&ceid=PH%3Aen");
            }
        });
        return inflate;
    }
}
