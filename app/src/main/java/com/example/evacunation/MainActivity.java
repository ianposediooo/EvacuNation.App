package com.example.evacunation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.evacunation.databinding.ActivityMainBinding;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    int NightMode;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private static final String TAG = "MainActivity";
    private int mCounter = 0;
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private BottomNavigationView navigationView;
//    private FrameLayout frameLayout;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("counter", mCounter);

        NightMode = AppCompatDelegate.getDefaultNightMode();

        sharedPreferences = getSharedPreferences("SharedPrefs", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editor.putInt("NightModeInt", NightMode);
        editor.apply();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mCounter = savedInstanceState.getInt("counter");
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new home());


        sharedPreferences = getSharedPreferences("SharedPrefs", MODE_PRIVATE);
        NightMode = sharedPreferences.getInt("NightModeInt", 1);
        Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show();
        AppCompatDelegate.setDefaultNightMode(NightMode);




        //bottom navigation view
        navigationView = findViewById(R.id.bottomNavigationview);
//        frameLayout = findViewById(R.id.frame_layout);
//        navigationView.setOnNavigationItemSelectedListener(listener);
//        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new home()).commit();

        statusCheck();

        binding.bottomNavigationview.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.homefragment:
                    replaceFragment(new home());
                    break;
                case R.id.mapActivity:
                    if (MainActivity.this.isServicesOK()) {
                        Intent intent = new Intent(MainActivity.this, MapActivity.class);
                        MainActivity.this.startActivity(intent);
                    }
                    return true;
                case R.id.weather:
                    replaceFragment(new weather());
                    break;
                case R.id.tools:
                    replaceFragment(new toolsnew());
                    break;
            }


            return true;
        });




    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }


    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }



    private void buildAlertMessageNoGps() {
        final MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this, R.style.CustomDialogTheme);
        builder.setIcon(R.drawable.locationoutlined);
        builder.setTitle("Location Services or GPS is turned OFF!");
        builder.setMessage("Location Services (GPS) is required to run the app. Please enable it in settings.")
                .setCancelable(false)
                .setPositiveButton("Enable Location Services", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                });
        builder.show();
    }

//    private BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//            Fragment selectedFragment = null;
//
//            switch (item.getItemId()) {
//                case R.id.homefragment:
//                    selectedFragment = new home();
//                    break;
//                case R.id.mapActivity:
//                    if (MainActivity.this.isServicesOK()) {
//                        Intent intent = new Intent(MainActivity.this, MapActivity.class);
//                        MainActivity.this.startActivity(intent);
//                    }
//                    return true;
//                case R.id.weather:
//                    selectedFragment = new weather();
//                    break;
//                case R.id.tools:
//                    selectedFragment = new toolsnew();
//                    break;
//            }
//            MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, selectedFragment).commit();
//            return true;
//        }
//    };

    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if (available == ConnectionResult.SUCCESS){
            Log.d(TAG, "isServicesOK: Google Play Services is Working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
    @Override
    public void onBackPressed() {

        super.onBackPressed();
        if (this.navigationView.getSelectedItemId() == R.id.homefragment) {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this, R.style.CustomDialogTheme);
            builder.setTitle("Are you sure you want to exit the app?");
            builder.setIcon(R.drawable.exitapp);
            builder.setNegativeButton("No", MainActivity$$ExternalSyntheticLambda1.INSTANCE);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() { // from class: com.example.evacunation.MainActivity$$ExternalSyntheticLambda0
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    MainActivity.this.m23lambda$onBackPressed$2$comexampleevacunationMainActivity(dialogInterface, i);
                }
            });
            builder.show();
            return;
        }
        this.navigationView.setSelectedItemId(R.id.homefragment);
    }

    /* renamed from: lambda$onBackPressed$2$com-example-evacunation-MainActivity */
    public /* synthetic */ void m23lambda$onBackPressed$2$comexampleevacunationMainActivity(DialogInterface dialogInterface, int i) {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

}

