package com.example.evacunation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class lists extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);

        Toast.makeText(this, "Loading the Lists Please Wait...", Toast.LENGTH_SHORT).show();

        PDFView pdfView = findViewById(R.id.pdfView);
        pdfView.fromAsset("fulllistevaccompressed.pdf")
                .onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {
//                        pdfView.useBestQuality(true);
                        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(pdfView.getContext(),R.style.CustomDialogTheme);
                        builder.setTitle("Tip:");
                        builder.setMessage("Use the Page Scrollbar on the Top Right Corner for Easy Scrolling and Pinch to Zoom.");
                        builder.setIcon(R.drawable.tip);
                        builder.setCancelable(true);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        builder.show();
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