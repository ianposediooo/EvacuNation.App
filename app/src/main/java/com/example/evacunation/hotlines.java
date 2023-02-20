package com.example.evacunation;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class hotlines extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_PHONE = 1;

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotlines);


        if (ContextCompat.checkSelfPermission(this, "android.permission.CALL_PHONE") != 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{"android.permission.CALL_PHONE"}, 1);
            }
            return;
        }
        startadapter();
    }

    public void startadapter(){
        List<String> titles = new ArrayList<>();
        titles.add("Public Complaint Hotline");
        titles.add("Bureau of Fire Protection");
        titles.add("NDRRMC");
        titles.add("Philippine Red Cross");
        titles.add("DOTR - Traffic Crisis Management Unit");

        titles.add("Poison Control Center");
        titles.add("National Center for Mental Health Crisis");
        titles.add("Department of Social Welfare and Development - Crisis Intervention Unit");
        titles.add("Department of Tourism - Tourist Assistance Hotline");
        titles.add("National Bureau of Investigation");

        titles.add("Philippine Coast Guard - Search and Rescue Hotline");
        titles.add("Department of Environment and Natural Resources - Environmental Management Bureau");
        titles.add("Philippine National Police - Women and Children Protection Center");
        titles.add("Department of Labor and Employment - 24/7 Hotline");
        titles.add("Department of Foreign Affairs - Overseas Workers Welfare Administration");

        titles.add("Department of Foreign Affairs - Consular Office Assistance");
        titles.add("National Center for Mental Health Crisis Hotline - Hopeline Project");
        titles.add("Philippine Drug Enforcement Agency - Drug Abuse Treatment and Rehabilitation Center");
        titles.add("Philippine National Police - Anti-Kidnapping Group");
        titles.add("Philippine National Police - Highway Patrol Group");

        titles.add("North Luzon Expressway (NLEX)");
        titles.add("Subic-Clark-Tarlac Expressway (SCTEX)");
        titles.add("South Luzon Expressway (SLEX)");
        titles.add("Manila-Cavite Expressway (CAVITEX)");
        titles.add("Cavite-Laguna Expressway (CALAX)");

        titles.add("Skyway System");
        titles.add("Tarlac-Pangasinan-La Union Expressway (TPLEX)");
        titles.add("Laguna Provincial Disaster Risk Reduction and Management Office");
        titles.add("Philippine General Hospital");
        titles.add("St. Luke's Medical Center");

        titles.add("Makati Medical Center");
        titles.add("The Medical City");
        titles.add("Asian Hospital and Medical Center");
        titles.add("Cardinal Santos Medical Center");
        titles.add("Manila Doctors Hospital");

        titles.add("University of Santo Tomas Hospital");
        titles.add("St. Luke's Medical Center-Global City");
        titles.add("Lung Center of the Philippines");
        titles.add("Department of Environment and Natural Resources - Environmental Law Enforcement 24/7 Hotline");
        titles.add("Land Transportation Franchising and Regulatory Board - Public Assistance and Complaints Desk");

        titles.add("Department of Public Works and Highways - Report a Road Problem Hotline");
        titles.add("National Grid Corporation of the Philippines - Power Interruption Hotline");
        titles.add("Maynilad Water Services - Water Supply and Services Hotline");
        titles.add("Manila Water Company - Water Supply and Services Hotline");
        titles.add("Manila Electric Company (Meralco)");

        //numbers
        List<String> data = new ArrayList<>();
        data.add("8888");
        data.add("160");
        data.add("(02) 8911-1406");
        data.add("143");
        data.add("8531-9999");

        data.add("(02) 8892-7301");
        data.add("09178998727");
        data.add("(02) 8734-8639");
        data.add("(02) 8801-0000");
        data.add("(02) 8523-8231");

        data.add("162");
        data.add("(02) 8925-2327");
        data.add("134");
        data.add("1349");
        data.add("(02) 8482-3454");

        data.add("(02) 8551-8344");
        data.add("09175584673");
        data.add("(02) 8824-0354");
        data.add("(02) 8470-8624");
        data.add("(02) 8933-4436");

        data.add("09178397539");
        data.add("09275027777");
        data.add("0917-687-7539");
        data.add("(02) 165-8888");
        data.add("(02) 7-5000");

        data.add("(02) 8888-7777");
        data.add("0917-839-7539");
        data.add("(049) 501-7877");
        data.add("(02) 8554-8400");
        data.add("(02) 8723-0101");

        data.add("(02) 8888-8999");
        data.add("(02) 8396-9899");
        data.add("(02) 8771-9000");
        data.add("(02) 8724-3001");
        data.add("(02) 8558-0888");

        data.add("(02) 7319-2731");
        data.add("(02) 8806-3100");
        data.add("(02) 8924-6101");
        data.add("8888-920-111");
        data.add("(02) 8929-5342");

        data.add("(02) 165-02");
        data.add("16211");
        data.add("1626");
        data.add("1627");
        data.add("(02) 16211");

        // create example titles


        // set up recycler view
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new MyAdapter(data, titles);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_PHONE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, do something
                startadapter();
                Toast.makeText(this, "Phone permission granted", Toast.LENGTH_SHORT).show();
            } else {
                // Permission denied, show an explanation or disable the functionality
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this,R.style.CustomDialogTheme);
                builder.setTitle("Please allow phone permission so that you can call emergency hotlines.");
                builder.setIcon(R.drawable.warning);
                builder.setCancelable(false);
                builder.setPositiveButton("Allow in Settings", new DialogInterface.OnClickListener() { // from class: com.example.evacunation.toolsnew.2.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        openAppSettings();
                    }
                });
                builder.show();
            }
        }
    }

    private void openAppSettings() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

}