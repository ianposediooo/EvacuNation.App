package com.example.evacunation;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.location.Location;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import android.telephony.SmsManager;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;

import static android.content.ContentValues.TAG;


public class tools extends Fragment implements SurfaceHolder.Callback {
    private static final int REQUEST_CALL = 1;
    private static final int REQUEST_CAMERA = 2;
    private static final int REQUEST_SEND_SMS = 3;
    private static final int REQUEST_LOCATION = 4;


    ImageButton btnSwitch;
    Button btnPlay;
    Button btnPause;
    Button emerbtn;
    Button btncall;
    View view;
    TextView tv;
    private boolean isLighOn = false;
    private boolean hasFlash;
    private Context context;
    private String m_Text = "";

    private Camera camera;
    Camera.Parameters params;

    @Override
    public void onStart() {
        super.onStart();
        SurfaceView preview = getView().findViewById(R.id.PREVIEW);
        SurfaceHolder mHolder = preview.getHolder();
        mHolder.addCallback(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        turnOffFlash();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (camera != null){
            camera.release();
            camera = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CALL:
                if (grantResults.length > 0 && grantResults [0] == PackageManager.PERMISSION_GRANTED) {
                    makecall();
                } else {
                    Toast.makeText(getActivity(),"Permission DENIED",Toast.LENGTH_SHORT).show();
                }
                break;
        }
        switch (requestCode){
            case REQUEST_CAMERA:
                if (grantResults.length > 0 && grantResults [0] == PackageManager.PERMISSION_GRANTED) {
                    turnOnFlash();
                } else {
                    Toast.makeText(getActivity(),"Permission DENIED",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
        switch (requestCode){
            case REQUEST_SEND_SMS:
                if (grantResults.length > 0 && grantResults [0] == PackageManager.PERMISSION_GRANTED) {
                    sendSms();
                } else {
                    Toast.makeText(getActivity(),"Permission DENIED",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
        switch (requestCode){
            case REQUEST_LOCATION:
                if (grantResults.length > 0 && grantResults [0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    Toast.makeText(getActivity(),"Permission DENIED",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
    private void makecall() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CALL);

        }
        else{
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + "911"));
            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(callIntent);
        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        /*Toast.makeText(getActivity(),"Tools",Toast.LENGTH_SHORT).show();*/

        view = inflater.inflate(R.layout.fragment_tools,
                container, false);

        hasFlash = getContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if(!hasFlash){
            AlertDialog alert = new AlertDialog.Builder(getActivity()).create();
            alert.setTitle("ERROR");
            alert.setMessage("Sorry your device doesn't have a flashlight");
            alert.setButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alert.show();
        }


        // flash switch button
        btnSwitch = view.findViewById(R.id.toggleButton);
        btncall = view.findViewById(R.id.button);
        //Play Music Button
        /*btnPlay=(Button) view.findViewById(R.id.button2);*/



        //call 911 button
        btncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                // Setting Dialog snippet
                alertDialog.setTitle("Do you want to call 911?");
                alertDialog.setIcon(R.drawable.phoneblack);
                alertDialog.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // Write your code here to execute after dialog
                                dialog.cancel();
                            }
                        });
                alertDialog.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                makecall();
                            }
                        });
                alertDialog.show();
            }
        });

        //EMERGENCY BUTTON
        emerbtn = view.findViewById(R.id.emergency);
        emerbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                /*Toast.makeText(getActivity(),"Emergency",Toast.LENGTH_SHORT).show();*/
                getLocation();
            }
        });




        // Switch button click event to toggle flash on/off
        tv = view.findViewById(R.id.textViewoff);
        btnSwitch.setOnClickListener(view -> {
            if (isLighOn) {
                // turn off flash
                tv.setText("Flashlight is OFF");
                btnSwitch.setImageResource(R.drawable.flashoff);
                turnOffFlash();
                /*Toast.makeText(getActivity(),"Flash turned OFF",Toast.LENGTH_SHORT).show();*/
            } else {
                // turn on flash
                tv.setText("Flashlight is ON");
                btnSwitch.setImageResource(R.drawable.flashonblack);
                turnOnFlash();
                /*Toast.makeText(getActivity(),"Flash turned ON",Toast.LENGTH_SHORT).show();*/
            }
        });

        //Play Music Button
        btnPlay= view.findViewById(R.id.button2);
        /*mp2 = MediaPlayer.create(getActivity(),R.raw.sos);
        mp2.setLooping(true);*/
        btnPlay.setOnClickListener((View view) -> {
            Intent sound = new Intent(getActivity(), SoundService.class);
            getActivity().startService(sound);
        });
        btnPause= view.findViewById(R.id.button3);
        btnPause.setOnClickListener((View view) -> {
            /*mp2.pause();*/
            Intent sound = new Intent(getActivity(), SoundService.class);
            getActivity().stopService(sound);
        });
        return view;
    }




    //SENDING SMS
    private void sendSms(){
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.SEND_SMS}, REQUEST_SEND_SMS);
        }
        else{
            getLocation();
        }
    }


    private void getLocation(){
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else{
            Task location = LocationServices.getFusedLocationProviderClient(getActivity()).getLastLocation();
            location.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "onComplete: found location!");
                        Location currentLocation = (Location) task.getResult();

                        /*moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                DEFAULT_ZOOM,
                                "My Location");*/
                        if (currentLocation != null) {
                            //GET LOCATION
                            double lat = currentLocation.getLatitude();
                            double lon = currentLocation.getLongitude();
                            //ALERT DIALOG INPUT TEXT
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Input your EMERGENCY MESSAGE below.");
                            final EditText input = new EditText(getActivity());
                            input.setInputType(InputType.TYPE_CLASS_TEXT);
                            builder.setView(input);
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    m_Text = input.getText().toString();
                                    //SHARE SHEET
                                    String message = "https://www.google.com/maps/search/?api=1&query=" + lat + "%2C" + lon;
                                    Intent sendIntent = new Intent();
                                    sendIntent.setAction(Intent.ACTION_SEND);
                                    sendIntent.putExtra(Intent.EXTRA_TEXT,  "'" + m_Text + "'" + "\n" + "-Message of the sender" + "\n\n"  +
                                            "The sender of this message is in grave danger or in need of immediate help. "
                                            + "Attached below is the link of the map where the person in emergency is currently located. " +
                                            "Contact the appropriate authorities now to send help. " + "\n\n" + message + "\n\n"
                                            + " (This emergency message is sent using EvacuNation App)");
                                    sendIntent.setType("text/plain");
                                    startActivity(sendIntent);
                                }
                            });
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                            builder.show();
                        }

                    } else {
                        Log.d(TAG, "onComplete: current location is null");
                        Toast.makeText(getActivity(), "Unable to get Current Location", Toast.LENGTH_SHORT).show();
                    }

                }

            });
        }
    }


    // Turning On flash
    private void turnOnFlash() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.CAMERA}, REQUEST_CAMERA);
        }
        else{
            if (!isLighOn) {
                if (camera == null || params == null) {
                    return;
                }
                params = camera.getParameters();
                params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(params);
                camera.startPreview();
                isLighOn = true;
            }

        }

    }

    // Turning Off flash
    private void turnOffFlash() {

        if (isLighOn) {
            if (camera == null || params == null) {
                return;
            }
            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
            isLighOn = false;

        }
    }

    /*
     * Toggle switch button images
     * changing image states to on / off
     * */


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (camera != null) {
            camera.stopPreview();
            camera.setPreviewCallback(null);
            camera.release();
            camera = null;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (camera == null) {
            camera = Camera.open();
            params = camera.getParameters();
            try {
                camera.setPreviewDisplay(holder);
            } catch (IOException e) {
                camera.release();
                camera = null;
            }
        }
    }

}

