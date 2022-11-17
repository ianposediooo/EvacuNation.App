package com.example.evacunation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

public class lists extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);

        Toast.makeText(this, "Loading the List Please Wait...", Toast.LENGTH_SHORT).show();

        PDFView pdfView = findViewById(R.id.pdfView);
        pdfView.fromAsset("fulllistevaccompressed.pdf")
                .onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {
//                        pdfView.useBestQuality(true);
                            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
                                pdfView.setNightMode(true);
                                Toast.makeText(lists.this, "Dark Mode On", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            pdfView.setNightMode(false);
                        }
                })
                .enableAntialiasing(true)
                .scrollHandle(new DefaultScrollHandle(this))
                .nightMode(false)
                .fitEachPage(true)
                .enableAnnotationRendering(true)
                .defaultPage(0)
                .load();



    }
}