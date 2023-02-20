package com.example.evacunation;

import androidx.annotation.RawRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnLongPressListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

public class safestepsph extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safestepsph);

        PDFView pdfView = findViewById(R.id.pdfViewsafestepseng);
        pdfView.fromAsset("safestepspheng.pdf")
                .onLoad(new OnLoadCompleteListener() {
                    @Override
                    public void loadComplete(int nbPages) {
                        pdfView.useBestQuality(true);
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
                    }
                })
                .spacing(10)
                .scrollHandle(new DefaultScrollHandle(this))
                .enableAnnotationRendering(true)
                .defaultPage(0)
                .fitEachPage(true)
                .load();
    }
}