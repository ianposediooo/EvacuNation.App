package com.example.evacunation;



import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;

/* loaded from: classes.dex */
public class about extends Fragment {
    private static final String KEY_MY_VALUE = "myValue";
    private int mMyValue;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    public MaterialButton btn;
    public Chip lightmode;
    public Chip darkmode, followsystem;

    public static about newInstance(String str, String str2) {
        about about = new about();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM1, str);
        bundle.putString(ARG_PARAM2, str2);
        about.setArguments(bundle);
        return about;
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mMyValue = savedInstanceState.getInt(KEY_MY_VALUE);
        }
        if (getArguments() != null) {
            this.mParam1 = getArguments().getString(ARG_PARAM1);
            this.mParam2 = getArguments().getString(ARG_PARAM2);


        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_MY_VALUE, mMyValue);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {

        //version name code
        View v = layoutInflater.inflate(R.layout.fragment_about, viewGroup, false);

        Context context = getContext().getApplicationContext(); // or activity.getApplicationContext()
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();

        String myVersionName = "not available"; // initialize String

        try {
            myVersionName = packageManager.getPackageInfo(packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        lightmode = v.findViewById(R.id.lightmode);
        darkmode = v.findViewById(R.id.darkmode);
        followsystem = v.findViewById(R.id.followsystem);


        TextView tvVersionName = (TextView) v.findViewById(R.id.version);
        tvVersionName.setText(myVersionName);

        lightmode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.isPressed()){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                   /* Toast.makeText(about.this.getActivity(), "Light Mode On", Toast.LENGTH_SHORT).show();*/
                }
            }
        });

        darkmode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.isPressed()){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                   /* Toast.makeText(about.this.getActivity(), "Dark Mode On", Toast.LENGTH_SHORT).show();*/
                }
            }
        });

        followsystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.isPressed()){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                    /*Toast.makeText(about.this.getActivity(), "Following System Theme", Toast.LENGTH_SHORT).show();*/
                }
            }
        });




        //share sheet button code
        btn = v.findViewById(R.id.shareapp);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myVersionName = "not available"; // initialize String

                try {
                    myVersionName = packageManager.getPackageInfo(packageName, 0).versionName;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent (Intent.ACTION_SEND);
                intent.setType ("text/plain");
                String Sub ="Download the Latest Version of EvacuNation\n\nEvacuNation - An Evacuation Center Locator and Disaster Preparedness Mobile App\n" + "\nhttps://github.com/ianposediooo/EvacuNation.App/releases/tag/v2.1.5\n\nShared via EvacuNation\n- Version " + myVersionName;
                intent.putExtra (Intent.EXTRA_TEXT, Sub);
                startActivity (Intent.createChooser (intent, "Share this App using"));
            }


        });

        btn = v.findViewById(R.id.github);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://github.com/ianposediooo/EvacuNation.App";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });


        return v;


    }
}
