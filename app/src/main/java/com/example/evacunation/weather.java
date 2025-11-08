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
                "<meta charset=\"utf-8\" />\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />\n" +
                "<title>Windy Map — LIVE Timeline (Final)</title>\n" +
                "<link rel=\"stylesheet\" href=\"https://unpkg.com/leaflet@1.4.0/dist/leaflet.css\" />\n" +
                "<script src=\"https://unpkg.com/leaflet@1.4.0/dist/leaflet.js\"></script>\n" +
                "<script src=\"https://api.windy.com/assets/map-forecast/libBoot.js\"></script>\n" +
                "<link href=\"https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined\" rel=\"stylesheet\" />\n" +
                "\n" +
                "<style>\n" +
                "html, body {\n" +
                "  height: 100%;\n" +
                "  margin: 0;\n" +
                "  padding: 0;\n" +
                "  font-family: system-ui, -apple-system, Segoe UI, Roboto, Arial;\n" +
                "  background: var(--bg-color);\n" +
                "  transition: background 0.4s ease, color 0.4s ease;\n" +
                "}\n" +
                "#windy { width: 100vw; height: 100vh; }\n" +
                "\n" +
                ":root {\n" +
                "  --green: #1DB954;\n" +
                "  --button-bg: var(--green);\n" +
                "  --button-fg: #ffffff;\n" +
                "  --timeline-bg: rgba(255,255,255,0.7);\n" +
                "  --timeline-fg: #000;\n" +
                "  --timeline-active: var(--green);\n" +
                "  --bg-color: #f4f4f4;\n" +
                "  --live-red: #ff5252;\n" +
                "}\n" +
                "\n" +
                "@media (prefers-color-scheme: dark) {\n" +
                "  :root {\n" +
                "    --timeline-bg: rgba(17,17,17,0.6);\n" +
                "    --timeline-fg: #e6edf7;\n" +
                "    --bg-color: #0b1b2b;\n" +
                "  }\n" +
                "}\n" +
                "\n" +
                ".toolbar {\n" +
                "  position: absolute;\n" +
                "  left: 16px;\n" +
                "  bottom: 100px;\n" +
                "  display: flex;\n" +
                "  flex-direction: column;\n" +
                "  gap: 10px;\n" +
                "  z-index: 9999;\n" +
                "  transition: opacity .3s ease;\n" +
                "}\n" +
                ".toolbar.hidden { opacity: 0; pointer-events: none; }\n" +
                "\n" +
                ".btn {\n" +
                "  width: 44px;\n" +
                "  height: 44px;\n" +
                "  border-radius: 12px;\n" +
                "  border: none;\n" +
                "  background-color: var(--button-bg);\n" +
                "  color: var(--button-fg);\n" +
                "  display: flex;\n" +
                "  align-items: center;\n" +
                "  justify-content: center;\n" +
                "  box-shadow: 0 4px 15px rgba(0,0,0,0.2);\n" +
                "  cursor: pointer;\n" +
                "  transition: transform .15s, background .3s;\n" +
                "}\n" +
                ".btn:hover { transform: translateY(-2px); background-color: #1ed760; }\n" +
                "\n" +
                ".timeline {\n" +
                "  position: absolute;\n" +
                "  left: 50%;\n" +
                "  bottom: 20px;\n" +
                "  transform: translateX(-50%);\n" +
                "  display: flex;\n" +
                "  align-items: center;\n" +
                "  gap: 8px;\n" +
                "  background: var(--timeline-bg);\n" +
                "  backdrop-filter: blur(10px);\n" +
                "  -webkit-backdrop-filter: blur(10px);\n" +
                "  color: var(--timeline-fg);\n" +
                "  padding: 10px 14px;\n" +
                "  border-radius: 16px;\n" +
                "  box-shadow: 0 4px 12px rgba(0,0,0,0.3);\n" +
                "  overflow-x: auto;\n" +
                "  white-space: nowrap;\n" +
                "  scrollbar-width: thin;\n" +
                "  scrollbar-color: var(--timeline-active) transparent;\n" +
                "  z-index: 9999;\n" +
                "  max-width: 90vw;\n" +
                "  scroll-behavior: smooth;\n" +
                "  transition: background 0.5s ease, color 0.5s ease;\n" +
                "}\n" +
                ".timeline::-webkit-scrollbar { height: 6px; }\n" +
                ".timeline::-webkit-scrollbar-thumb { background: var(--timeline-active); border-radius: 99px; }\n" +
                "\n" +
                ".time-step {\n" +
                "  display: flex;\n" +
                "  flex-direction: column;\n" +
                "  align-items: center;\n" +
                "  justify-content: center;\n" +
                "  padding: 6px 12px;\n" +
                "  border-radius: 8px;\n" +
                "  cursor: pointer;\n" +
                "  transition: background 0.2s;\n" +
                "  min-width: 70px;\n" +
                "}\n" +
                ".time-step .time {\n" +
                "  font-size: 13px;\n" +
                "  font-weight: 600;\n" +
                "  display: flex;\n" +
                "  align-items: center;\n" +
                "  gap: 4px;\n" +
                "  margin-bottom: 4px;\n" +
                "}\n" +
                ".time-step .date {\n" +
                "  font-size: 12px;\n" +
                "  font-weight: 600;\n" +
                "  opacity: 0.9;\n" +
                "}\n" +
                ".time-step:hover { background: rgba(29,185,84,0.25); }\n" +
                "\n" +
                "@keyframes pulseActive {\n" +
                "  0% { box-shadow: 0 0 0px rgba(29,185,84,0.6); }\n" +
                "  50% { box-shadow: 0 0 12px rgba(29,185,84,0.9); }\n" +
                "  100% { box-shadow: 0 0 0px rgba(29,185,84,0.6); }\n" +
                "}\n" +
                ".time-step.active {\n" +
                "  background: var(--timeline-active);\n" +
                "  color: #fff;\n" +
                "  font-weight: 600;\n" +
                "  animation: pulseActive 2s ease-in-out infinite;\n" +
                "  padding: 8px 26px; /* more space for LIVE label */\n" +
                "  border-radius: 10px;\n" +
                "}\n" +
                "\n" +
                ".live-label {\n" +
                "  color: #fff;\n" +
                "  font-weight: 700;\n" +
                "  font-size: 11px;\n" +
                "  margin-left: 4px;\n" +
                "  padding: 1px 6px;\n" +
                "  background: var(--live-red);\n" +
                "  border-radius: 4px;\n" +
                "  opacity: 0;\n" +
                "  transition: opacity 0.3s ease;\n" +
                "}\n" +
                ".live-label.visible {\n" +
                "  opacity: 1;\n" +
                "}\n" +
                "\n" +
                ".overlay-popup {\n" +
                "  position: fixed; top: 0; left: 0;\n" +
                "  width: 100%; height: 100%;\n" +
                "  background: rgba(0,0,0,0.4);\n" +
                "  display: none; align-items: center; justify-content: center;\n" +
                "  z-index: 10000;\n" +
                "  backdrop-filter: blur(4px);\n" +
                "}\n" +
                ".overlay-popup.active { display: flex; }\n" +
                "\n" +
                ".popup-content {\n" +
                "  background: var(--timeline-bg);\n" +
                "  backdrop-filter: blur(10px);\n" +
                "  color: var(--timeline-fg);\n" +
                "  border-radius: 12px;\n" +
                "  padding: 20px;\n" +
                "  box-shadow: 0 4px 20px rgba(0,0,0,0.4);\n" +
                "  width: 280px;\n" +
                "  text-align: center;\n" +
                "  border: 2px solid var(--timeline-active);\n" +
                "}\n" +
                ".popup-content h3 {\n" +
                "  margin: 0 0 10px;\n" +
                "  font-size: 18px;\n" +
                "  border-bottom: 1px solid rgba(255,255,255,0.2);\n" +
                "  padding-bottom: 8px;\n" +
                "}\n" +
                ".layer-option {\n" +
                "  margin: 6px 0;\n" +
                "  padding: 8px 0;\n" +
                "  border-radius: 8px;\n" +
                "  background: rgba(255,255,255,0.05);\n" +
                "  cursor: pointer;\n" +
                "  transition: background 0.2s;\n" +
                "}\n" +
                ".layer-option:hover { background: rgba(29,185,84,0.35); color: #fff; }\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div id=\"windy\"></div>\n" +
                "\n" +
                "<div class=\"toolbar\" id=\"toolbar\">\n" +
                "  <button class=\"btn\" id=\"zoomIn\" title=\"Zoom In\"><span class=\"material-symbols-outlined\">zoom_in</span></button>\n" +
                "  <button class=\"btn\" id=\"zoomOut\" title=\"Zoom Out\"><span class=\"material-symbols-outlined\">zoom_out</span></button>\n" +
                "  <button class=\"btn\" id=\"locateBtn\" title=\"My Location\"><span class=\"material-symbols-outlined\">my_location</span></button>\n" +
                "  <button class=\"btn\" id=\"resetBtn\" title=\"Reset View\"><span class=\"material-symbols-outlined\">center_focus_strong</span></button>\n" +
                "  <button class=\"btn\" id=\"layerBtn\" title=\"Switch Layer\"><span class=\"material-symbols-outlined\">layers</span></button>\n" +
                "</div>\n" +
                "\n" +
                "<div class=\"timeline\" id=\"timeline\"></div>\n" +
                "\n" +
                "<div class=\"overlay-popup\" id=\"layerPopup\">\n" +
                "  <div class=\"popup-content\">\n" +
                "    <h3>Select Layer</h3>\n" +
                "    <div class=\"layer-option\" data-layer=\"wind\">\uD83C\uDF2C\uFE0F Wind</div>\n" +
                "    <div class=\"layer-option\" data-layer=\"rain\">\uD83C\uDF27\uFE0F Rain</div>\n" +
                "    <div class=\"layer-option\" data-layer=\"clouds\">☁\uFE0F Clouds</div>\n" +
                "    <div class=\"layer-option\" data-layer=\"temp\">\uD83C\uDF21\uFE0F Temperature</div>\n" +
                "    <div class=\"layer-option\" data-layer=\"waves\">\uD83C\uDF0A Waves</div>\n" +
                "    <div class=\"layer-option\" data-layer=\"pressure\">\uD83C\uDF00 Pressure</div>\n" +
                "  </div>\n" +
                "</div>\n" +
                "\n" +
                "<script>\n" +
                "const W_API_KEY='VRnS4f2oB9ZhDhKplnq7ly7mNvmCVoNG';\n" +
                "const PH_BOUNDS=[[4.5,116.0],[21.5,127.5]];\n" +
                "const fmtTime=new Intl.DateTimeFormat(undefined,{hour:'numeric',minute:'2-digit',hour12:true});\n" +
                "const fmtDate=new Intl.DateTimeFormat(undefined,{weekday:'short',month:'short',day:'numeric'});\n" +
                "const getDayIcon=hour=>(hour>=6&&hour<18)?'☀\uFE0F':'\uD83C\uDF19';\n" +
                "\n" +
                "function centerTimelineItem(item, container){\n" +
                "  if(!item||!container)return;\n" +
                "  const containerRect=container.getBoundingClientRect();\n" +
                "  const itemRect=item.getBoundingClientRect();\n" +
                "  const offset=(itemRect.left-containerRect.left)-(containerRect.width/2-itemRect.width/2);\n" +
                "  container.scrollBy({left:offset,behavior:'smooth'});\n" +
                "}\n" +
                "\n" +
                "window.onload=function(){\n" +
                "  windyInit({key:W_API_KEY,lat:12.8797,lon:121.7740,zoom:5,overlay:'wind',isEmbedded:false},function(windyAPI){\n" +
                "    const {map,store}=windyAPI;\n" +
                "    const zoomIn=document.getElementById('zoomIn');\n" +
                "    const zoomOut=document.getElementById('zoomOut');\n" +
                "    const locateBtn=document.getElementById('locateBtn');\n" +
                "    const resetBtn=document.getElementById('resetBtn');\n" +
                "    const layerBtn=document.getElementById('layerBtn');\n" +
                "    const toolbar=document.getElementById('toolbar');\n" +
                "    const timeline=document.getElementById('timeline');\n" +
                "    const popup=document.getElementById('layerPopup');\n" +
                "\n" +
                "    map.fitBounds(PH_BOUNDS);\n" +
                "\n" +
                "    zoomIn.onclick=()=>map.setZoom(map.getZoom()+1);\n" +
                "    zoomOut.onclick=()=>map.setZoom(map.getZoom()-1);\n" +
                "    resetBtn.onclick=()=>map.fitBounds(PH_BOUNDS);\n" +
                "\n" +
                "    locateBtn.onclick=()=>{\n" +
                "      if(!navigator.geolocation)return;\n" +
                "      navigator.geolocation.getCurrentPosition(pos=>{\n" +
                "        const {latitude,longitude}=pos.coords;\n" +
                "        map.setView([latitude,longitude],10);\n" +
                "        L.marker([latitude,longitude]).addTo(map).bindPopup('You are here').openPopup();\n" +
                "      });\n" +
                "    };\n" +
                "\n" +
                "    layerBtn.onclick=()=>popup.classList.add('active');\n" +
                "    popup.onclick=(e)=>{if(e.target.classList.contains('overlay-popup'))popup.classList.remove('active');};\n" +
                "    popup.querySelectorAll('.layer-option').forEach(opt=>{\n" +
                "      opt.onclick=()=>{\n" +
                "        store.set('overlay',opt.dataset.layer);\n" +
                "        popup.classList.remove('active');\n" +
                "      };\n" +
                "    });\n" +
                "\n" +
                "    const now=new Date();\n" +
                "    now.setMinutes(0,0,0);\n" +
                "    let currentIndex=0;\n" +
                "    for(let i=0;i<48;i++){\n" +
                "      const t=new Date(now.getTime()+i*3600*1000);\n" +
                "      const el=document.createElement('div');\n" +
                "      el.className='time-step';\n" +
                "      el.innerHTML=`\n" +
                "        <div class=\"time\">${getDayIcon(t.getHours())} ${fmtTime.format(t)}</div>\n" +
                "        <div class=\"date\">${fmtDate.format(t)}</div>\n" +
                "      `;\n" +
                "      el.dataset.ts=t.getTime();\n" +
                "      el.onclick=()=>{\n" +
                "        document.querySelectorAll('.time-step').forEach(x=>x.classList.remove('active'));\n" +
                "        el.classList.add('active');\n" +
                "        store.set('timestamp',t.getTime());\n" +
                "        centerTimelineItem(el,timeline);\n" +
                "      };\n" +
                "      timeline.appendChild(el);\n" +
                "      if(Math.abs(now-t)<30*60*1000)currentIndex=i;\n" +
                "    }\n" +
                "\n" +
                "    const steps=timeline.querySelectorAll('.time-step');\n" +
                "    let activeItem=steps[currentIndex];\n" +
                "    activeItem.classList.add('active');\n" +
                "    addLiveLabel(activeItem);\n" +
                "    setTimeout(()=>centerTimelineItem(activeItem,timeline),500);\n" +
                "\n" +
                "    function addLiveLabel(item){\n" +
                "      document.querySelectorAll('.live-label').forEach(l=>l.remove());\n" +
                "      const label=document.createElement('span');\n" +
                "      label.className='live-label';\n" +
                "      label.textContent='LIVE';\n" +
                "      item.querySelector('.time').appendChild(label);\n" +
                "      requestAnimationFrame(()=>label.classList.add('visible'));\n" +
                "    }\n" +
                "\n" +
                "    setInterval(()=>{\n" +
                "      const now=new Date();\n" +
                "      let closestIndex=0,closestDiff=Infinity;\n" +
                "      steps.forEach((s,i)=>{\n" +
                "        const diff=Math.abs(now - new Date(parseInt(s.dataset.ts)));\n" +
                "        if(diff<closestDiff){closestDiff=diff;closestIndex=i;}\n" +
                "      });\n" +
                "      document.querySelectorAll('.time-step').forEach(x=>x.classList.remove('active'));\n" +
                "      activeItem=steps[closestIndex];\n" +
                "      activeItem.classList.add('active');\n" +
                "      addLiveLabel(activeItem);\n" +
                "      centerTimelineItem(activeItem,timeline);\n" +
                "      store.set('timestamp',parseInt(activeItem.dataset.ts));\n" +
                "    },60000);\n" +
                "\n" +
                "    let hideTimeout;\n" +
                "    function showToolbar(){\n" +
                "      toolbar.classList.remove('hidden');\n" +
                "      clearTimeout(hideTimeout);\n" +
                "      hideTimeout=setTimeout(()=>toolbar.classList.add('hidden'),3000);\n" +
                "    }\n" +
                "    map.on('movestart',showToolbar);\n" +
                "    map.on('zoomstart',showToolbar);\n" +
                "    map.on('mousemove',showToolbar);\n" +
                "    showToolbar();\n" +
                "  });\n" +
                "};\n" +
                "</script>\n" +
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
