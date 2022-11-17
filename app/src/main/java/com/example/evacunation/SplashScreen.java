package com.example.evacunation;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        startActivity(new Intent(this, MainActivity.class));
        finish();
        Window window = getWindow();
        window.addFlags(Integer.MIN_VALUE);
        window.clearFlags(67108864);
    }
}
