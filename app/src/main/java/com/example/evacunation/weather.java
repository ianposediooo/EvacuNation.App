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
import androidx.fragment.app.Fragment;

/* loaded from: classes.dex */
public class weather extends Fragment {
    public WebView mWebView;
    private ProgressBar progressBar;


    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_weather, viewGroup, false);
        WebView webView = inflate.findViewById(R.id.webview);
        this.mWebView = webView;
        webView.requestFocus();

        ProgressBar progressBar = inflate.findViewById(R.id.pbar);
        this.progressBar = progressBar;
        progressBar.setMax(100);

        WebSettings settings = this.mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLightTouchEnabled(true);
        settings.setGeolocationEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);

        this.mWebView.setWebViewClient(new WebViewClient() { // from class: com.example.evacunation.weather.3
            @Override // android.webkit.WebViewClient
            public void onPageStarted(WebView webView2, String str, Bitmap bitmap) {
                super.onPageStarted(webView2, str, bitmap);
//                Toast.makeText(weather.this.getActivity(), "Weather is loading...", Toast.LENGTH_SHORT).show();
            }

            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView webView2, String str) {
                super.onPageFinished(weather.this.mWebView, str);
//                Toast.makeText(weather.this.getActivity(), "Weather loaded", Toast.LENGTH_SHORT).show();
                weather.this.progressBar.setVisibility(View.INVISIBLE);
            }

            @Override // android.webkit.WebViewClient
            public void onReceivedError(WebView webView2, int i, String str, String str2) {
                super.onReceivedError(weather.this.mWebView, i, str, str2);
//                Toast.makeText(weather.this.getActivity(), str, Toast.LENGTH_LONG).show();
            }
        });
        String htmlContent = "<!doctype html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "  <meta charset=\"utf-8\" />\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\n" +
                "  <title>Windy Map — Forecast Timeline</title>\n" +
                "  <link rel=\"stylesheet\" href=\"https://unpkg.com/leaflet@1.4.0/dist/leaflet.css\" />\n" +
                "  <script src=\"https://unpkg.com/leaflet@1.4.0/dist/leaflet.js\"></script>\n" +
                "  <script src=\"https://api.windy.com/assets/map-forecast/libBoot.js\"></script>\n" +
                "  <link href=\"https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0\" rel=\"stylesheet\" />\n" +
                "  <style>\n" +
                "    html,body{height:100%;margin:0;padding:0;font-family:system-ui,-apple-system,Segoe UI,Roboto,Arial;background:#0b1b2b;}\n" +
                "    #windy{width:100vw;height:100vh;}\n" +
                "\n" +
                "    :root{\n" +
                "      --button-bg:#1e293b;\n" +
                "      --button-fg:#e6edf7;\n" +
                "      --stroke:rgba(255,255,255,0.12);\n" +
                "      --timeline-bg:rgba(17,24,39,0.9);\n" +
                "      --timeline-fg:#e6edf7;\n" +
                "      --timeline-active:#93c5fd;\n" +
                "      --toast-bg:rgba(17,24,39,0.95);\n" +
                "      --toast-fg:#fff;\n" +
                "    }\n" +
                "\n" +
                "    .toolbar{position:absolute;left:16px;bottom:100px;display:flex;flex-direction:column;gap:10px;z-index:9999;transition:opacity .3s ease;opacity:1;}\n" +
                "    .toolbar.hidden{opacity:0;pointer-events:none;}\n" +
                "    .btn{width:44px;height:44px;border-radius:12px;border:1px solid var(--stroke);background-color:var(--button-bg);color:var(--button-fg);display:flex;align-items:center;justify-content:center;box-shadow:0 4px 15px rgba(0,0,0,.15);cursor:pointer;transition:transform .15s;}\n" +
                "    .btn:hover{transform:translateY(-2px);}\n" +
                "    .btn .material-symbols-outlined{font-size:24px;}\n" +
                "\n" +
                "    .timeline{position:absolute;left:50%;bottom:20px;transform:translateX(-50%);display:flex;align-items:center;gap:8px;background:var(--timeline-bg);color:var(--timeline-fg);padding:10px 14px;border-radius:12px;box-shadow:0 4px 12px rgba(0,0,0,.3);overflow-x:auto;white-space:nowrap;scrollbar-width:thin;scrollbar-color:var(--timeline-active) transparent;z-index:9999;max-width:90vw;scroll-behavior:smooth;}\n" +
                "    .timeline::-webkit-scrollbar{height:6px;}\n" +
                "    .timeline::-webkit-scrollbar-thumb{background:var(--timeline-active);border-radius:99px;}\n" +
                "    .time-step{display:flex;flex-direction:column;align-items:center;justify-content:center;padding:6px 12px;border-radius:8px;cursor:pointer;transition:background .2s;min-width:70px;}\n" +
                "    .time-step .time{font-size:13px;font-weight:600;}\n" +
                "    .time-step .date{font-size:11px;opacity:.8;}\n" +
                "    .time-step:hover{background:rgba(0,0,0,.08);}\n" +
                "    .time-step.active{background:var(--timeline-active);color:#fff;font-weight:600;}\n" +
                "\n" +
                "    .toast{position:fixed;left:50%;bottom:120px;transform:translateX(-50%) scale(.9);background:var(--toast-bg);color:var(--toast-fg);padding:10px 20px;border-radius:20px;font-size:14px;opacity:0;transition:all .3s ease;pointer-events:none;z-index:10000;}\n" +
                "    .toast.show{opacity:1;transform:translateX(-50%) scale(1);}\n" +
                "  </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "  <div id=\"windy\"></div>\n" +
                "\n" +
                "  <div class=\"toolbar\" id=\"toolbar\">\n" +
                "    <button class=\"btn\" id=\"zoomIn\" title=\"Zoom In\"><span class=\"material-symbols-outlined\">zoom_in</span></button>\n" +
                "    <button class=\"btn\" id=\"zoomOut\" title=\"Zoom Out\"><span class=\"material-symbols-outlined\">zoom_out</span></button>\n" +
                "    <button class=\"btn\" id=\"locateBtn\" title=\"My Location\"><span class=\"material-symbols-outlined\">my_location</span></button>\n" +
                "    <button class=\"btn\" id=\"layerBtn\" title=\"Switch Layer\"><span class=\"material-symbols-outlined\">layers</span></button>\n" +
                "  </div>\n" +
                "\n" +
                "  <div class=\"timeline\" id=\"timeline\"></div>\n" +
                "  <div class=\"toast\" id=\"toast\">Loading...</div>\n" +
                "\n" +
                "  <script>\n" +
                "    const W_API_KEY='VRnS4f2oB9ZhDhKplnq7ly7mNvmCVoNG';\n" +
                "    const PH_BOUNDS=[[4.5,116.0],[21.5,127.5]];\n" +
                "    const fmtTime=new Intl.DateTimeFormat(undefined,{hour:'numeric',minute:'2-digit',hour12:true});\n" +
                "    const fmtDate=new Intl.DateTimeFormat(undefined,{weekday:'short',month:'short',day:'numeric'});\n" +
                "\n" +
                "    function showToast(message,duration=2000){\n" +
                "      const toast=document.getElementById('toast');\n" +
                "      toast.textContent=message;\n" +
                "      toast.classList.add('show');\n" +
                "      clearTimeout(toast._timeout);\n" +
                "      toast._timeout=setTimeout(()=>toast.classList.remove('show'),duration);\n" +
                "    }\n" +
                "\n" +
                "    window.onload=function(){\n" +
                "      showToast('Loading Windy forecast...');\n" +
                "      windyInit({key:W_API_KEY,lat:12.8797,lon:121.7740,zoom:5,overlay:'wind',isEmbedded:false},function(windyAPI){\n" +
                "        const {map,store}=windyAPI;\n" +
                "        const zoomIn=document.getElementById('zoomIn');\n" +
                "        const zoomOut=document.getElementById('zoomOut');\n" +
                "        const locateBtn=document.getElementById('locateBtn');\n" +
                "        const layerBtn=document.getElementById('layerBtn');\n" +
                "        const toolbar=document.getElementById('toolbar');\n" +
                "        const timeline=document.getElementById('timeline');\n" +
                "\n" +
                "        map.fitBounds(PH_BOUNDS);\n" +
                "\n" +
                "        zoomIn.onclick=()=>map.setZoom(map.getZoom()+1);\n" +
                "        zoomOut.onclick=()=>map.setZoom(map.getZoom()-1);\n" +
                "\n" +
                "        locateBtn.onclick=()=>{\n" +
                "          if(!navigator.geolocation)return showToast('Geolocation not supported');\n" +
                "          showToast('Locating...');\n" +
                "          navigator.geolocation.getCurrentPosition(pos=>{\n" +
                "            const {latitude,longitude}=pos.coords;\n" +
                "            map.setView([latitude,longitude],10);\n" +
                "            L.marker([latitude,longitude]).addTo(map).bindPopup('You are here').openPopup();\n" +
                "            showToast('Location found');\n" +
                "          },err=>showToast('Location error: '+err.message));\n" +
                "        };\n" +
                "\n" +
                "        layerBtn.onclick=()=>{\n" +
                "          showToast('Loading new layer...');\n" +
                "          const current=store.get('overlay');\n" +
                "          const next=current==='wind'?'rain':'wind';\n" +
                "          store.set('overlay',next);\n" +
                "          setTimeout(()=>showToast('Layer switched to '+next),1500);\n" +
                "        };\n" +
                "\n" +
                "        // timeline\n" +
                "        const now=new Date();\n" +
                "        let currentIndex=0;\n" +
                "        for(let i=0;i<48;i++){\n" +
                "          const t=new Date(now.getTime()+i*3600*1000);\n" +
                "          const el=document.createElement('div');\n" +
                "          el.className='time-step';\n" +
                "          el.innerHTML=`<div class=\"time\">${fmtTime.format(t)}</div><div class=\"date\">${fmtDate.format(t)}</div>`;\n" +
                "          el.dataset.ts=t.getTime();\n" +
                "          el.onclick=()=>{\n" +
                "            document.querySelectorAll('.time-step').forEach(x=>x.classList.remove('active'));\n" +
                "            el.classList.add('active');\n" +
                "            showToast('Loading forecast for '+fmtTime.format(t));\n" +
                "            store.set('timestamp',t.getTime());\n" +
                "          };\n" +
                "          timeline.appendChild(el);\n" +
                "          if(Math.abs(now-t)<30*60*1000)currentIndex=i;\n" +
                "        }\n" +
                "\n" +
                "        const steps=timeline.querySelectorAll('.time-step');\n" +
                "        steps[currentIndex].classList.add('active');\n" +
                "\n" +
                "        setTimeout(()=>{\n" +
                "          const active=timeline.querySelector('.time-step.active');\n" +
                "          if(active){\n" +
                "            const offset=active.offsetLeft-timeline.offsetWidth/2+active.offsetWidth/2;\n" +
                "            timeline.scrollTo({left:offset,behavior:'smooth'});\n" +
                "          }\n" +
                "        },800);\n" +
                "\n" +
                "        setTimeout(()=>showToast('Forecast successfully loaded'),2500);\n" +
                "\n" +
                "        let hideTimeout;\n" +
                "        function showToolbar(){\n" +
                "          toolbar.classList.remove('hidden');\n" +
                "          clearTimeout(hideTimeout);\n" +
                "          hideTimeout=setTimeout(()=>toolbar.classList.add('hidden'),3000);\n" +
                "        }\n" +
                "        map.on('movestart',showToolbar);\n" +
                "        map.on('zoomstart',showToolbar);\n" +
                "        map.on('mousemove',showToolbar);\n" +
                "        showToolbar();\n" +
                "      });\n" +
                "    };\n" +
                "  </script>\n" +
                "</body>\n" +
                "</html>\n";
        /*this.mWebView.loadUrl("https://www.windy.com/");*/
        this.mWebView.loadDataWithBaseURL("https://api.windy.com/", htmlContent, "text/html", "UTF-8", null);
        this.mWebView.setWebChromeClient(new WebChromeClient() { // from class: com.example.evacunation.weather.4
            @Override // android.webkit.WebChromeClient
            public void onProgressChanged(WebView webView2, int i) {
                weather.this.progressBar.setProgress(i);
                if (i == 100) {
                    weather.this.progressBar.setVisibility(View.INVISIBLE);
                } else {
                    weather.this.progressBar.setVisibility(View.VISIBLE);
                }
            }

            @Override // android.webkit.WebChromeClient
            public void onGeolocationPermissionsShowPrompt(String str, GeolocationPermissions.Callback callback) {
                super.onGeolocationPermissionsShowPrompt(str, callback);
                callback.invoke(str, true, false);
            }
        });
        return inflate;
    }
}
