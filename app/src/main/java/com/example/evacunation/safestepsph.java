package com.example.evacunation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

public class safestepsph extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safestepsph);

        Toast.makeText(this, "Loading Please Wait...", Toast.LENGTH_SHORT).show();

        PDFView pdfView = findViewById(R.id.pdfViewsafestepseng);
        pdfView.fromAsset("safestepspheng.pdf")
                .scrollHandle(new DefaultScrollHandle(this))
                .nightMode(false)
                .enableAnnotationRendering(true)
                .defaultPage(0)
                .fitEachPage(true)
                .load();
    }
}