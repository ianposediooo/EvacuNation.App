package com.example.evacunation;

import android.os.Bundle;
import android.view.Window;
import androidx.appcompat.app.AppCompatActivity;

/* loaded from: classes.dex */
public class earthquake extends AppCompatActivity {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_earthquake);
        Window window = getWindow();
        window.addFlags(Integer.MIN_VALUE);
        window.clearFlags(67108864);
    }
}
