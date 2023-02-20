package com.example.evacunation;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

/* loaded from: classes.dex */
public class toolsnew extends Fragment {
    private static final int REQUEST_CALL = 1;
    private static final int REQUEST_CAMERA = 2;
    private static final int REQUEST_LOCATION = 4;
    private static final int REQUEST_SEND_SMS = 3;
    private Camera camera;
    private CameraManager cameraManager;
    public CardView card1;
    public CardView card2;
    public CardView card3;
    public CardView card4;
    public CardView card5;
    public CardView card6;
    public CardView card7;
    ImageButton flash;
    ImageView playstopbutton;
    private boolean hasFlash;
    private boolean isLighOn = false;
    private String m_Text = "";
    Camera.Parameters params;
    TextView tv;
    TextView sostv;
    TextView mode;
    View view;
    private boolean isServiceRunning = false;
    private Intent soundServiceIntent;

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        Camera camera = this.camera;
        if (camera != null) {
            camera.release();
            this.camera = null;
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

    /* JADX INFO: Access modifiers changed from: private */
    public void makecall() {
        if (ContextCompat.checkSelfPermission(getActivity(), "android.permission.CALL_PHONE") != 0) {
            requestPermissions(new String[]{"android.permission.CALL_PHONE"}, 1);
            return;
        }
        Intent intent = new Intent("android.intent.action.CALL");
        intent.setData(Uri.parse("tel:911"));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_toolsnew, viewGroup, false);
        this.view = inflate;
        this.card1 = (CardView) inflate.findViewById(R.id.card1);
        this.card2 = (CardView) this.view.findViewById(R.id.card2);
        this.card3 = (CardView) this.view.findViewById(R.id.card3);
        this.card4 = (CardView) this.view.findViewById(R.id.card4);
        this.card5 = (CardView) this.view.findViewById(R.id.card5);
        this.card6 = (CardView) this.view.findViewById(R.id.card6);



        //dark mode
//        this.card7 = (CardView) this.view.findViewById(R.id.card7);
//        this.mode = (TextView) this.view.findViewById(R.id.darkmodeoff);
//        this.imagemode = (ImageView) this.view.findViewById(R.id.night);
//        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
//            this.mode.setText("Turn ON\nDark Mode");
//            this.imagemode.setImageResource(R.drawable.darkmode);
//        }
//
//        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
//            this.mode.setText("Turn ON\nLight Mode");
//            this.imagemode.setImageResource(R.drawable.lightmode);
//        }

        boolean hasSystemFeature = getContext().getPackageManager().hasSystemFeature("android.hardware.camera.flash");
        this.hasFlash = hasSystemFeature;
        if (!hasSystemFeature) {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(toolsnew.this.getActivity(),R.style.CustomDialogTheme);
            builder.setTitle("ERROR");
            builder.setMessage("Sorry your device doesn't have a flashlight");
            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() { // from class: com.example.evacunation.toolsnew.1
                @Override // android.content.DialogInterface.OnClickListener
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.show();
        }
        this.card1.setOnClickListener(new View.OnClickListener() { // from class: com.example.evacunation.toolsnew.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(toolsnew.this.getActivity(),R.style.CustomDialogTheme);
                builder.setTitle("Call Philippines Emergency Hotline 911?");
                builder.setIcon(R.drawable.callemergency);
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() { // from class: com.example.evacunation.toolsnew.2.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() { // from class: com.example.evacunation.toolsnew.2.2
                    @Override // android.content.DialogInterface.OnClickListener
                    public void onClick(DialogInterface dialogInterface, int i) {
                        toolsnew.this.makecall();
                    }
                });
                builder.show();
            }
        });


        this.card2.setOnClickListener(new View.OnClickListener() { // from class: com.example.evacunation.toolsnew.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                toolsnew.this.getLocation();
            }
        });


        this.tv = (TextView) this.view.findViewById(R.id.textViewoff);
        this.flash = (ImageButton) this.view.findViewById(R.id.flash);

        this.card3.setOnClickListener(new View.OnClickListener() { // from class: com.example.evacunation.toolsnew.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (toolsnew.this.isLighOn) {
                    toolsnew.this.tv.setText("Flashlight\nis OFF");
                    toolsnew.this.flash.setImageResource(R.drawable.flashofff);
                    toolsnew.this.card3.setCardBackgroundColor(ActivityCompat.getColor(toolsnew.this.getActivity(), R.color.grey));
                    toolsnew.this.turnOffFlash();
                    /*Toast.makeText(toolsnew.this.getActivity(), "Flash turned OFF", Toast.LENGTH_SHORT).show();*/
                    return;
                }
                toolsnew.this.tv.setText("Flashlight\nis ON");
                toolsnew.this.flash.setImageResource(R.drawable.flash_on);
                toolsnew.this.card3.setCardBackgroundColor(ActivityCompat.getColor(toolsnew.this.getActivity(), R.color.flashyellow));
                toolsnew.this.turnOnFlash();
                /*Toast.makeText(toolsnew.this.getActivity(), "Flash turned ON", Toast.LENGTH_SHORT).show();*/
            }
        });
        this.flash.setOnClickListener(new View.OnClickListener() { // from class: com.example.evacunation.toolsnew.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (toolsnew.this.isLighOn) {
                    toolsnew.this.tv.setText("Flashlight\nis OFF");
                    toolsnew.this.flash.setImageResource(R.drawable.flashofff);
                    toolsnew.this.card3.setCardBackgroundColor(ActivityCompat.getColor(toolsnew.this.getActivity(), R.color.grey));
                    toolsnew.this.turnOffFlash();
                    /*Toast.makeText(toolsnew.this.getActivity(), "Flash turned OFF", Toast.LENGTH_SHORT).show();*/
                    return;
                }
                toolsnew.this.tv.setText("Flashlight\nis ON");
                toolsnew.this.flash.setImageResource(R.drawable.flash_on);
                toolsnew.this.card3.setCardBackgroundColor(ActivityCompat.getColor(toolsnew.this.getActivity(), R.color.flashyellow));
                toolsnew.this.turnOnFlash();
                /*Toast.makeText(toolsnew.this.getActivity(), "Flash turned ON", Toast.LENGTH_SHORT).show();*/
            }
        });

        this.sostv = (TextView) this.view.findViewById(R.id.sostext);
        this.playstopbutton = (ImageView) this.view.findViewById(R.id.playbutton);
        this.soundServiceIntent = new Intent(toolsnew.this.getActivity(), SoundService.class);
        this.card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isServiceRunning) {
                    toolsnew.this.getActivity().startService(soundServiceIntent);
                    toolsnew.this.sostv.setText("Stop SOS\nSound");
                    toolsnew.this.playstopbutton.setImageResource(R.drawable.stopcircle);
                    toolsnew.this.card4.setCardBackgroundColor(ActivityCompat.getColor(toolsnew.this.getActivity(), R.color.black));
                } else {
                    toolsnew.this.getActivity().stopService(soundServiceIntent);
                    toolsnew.this.sostv.setText("Play SOS\nSound");
                    toolsnew.this.playstopbutton.setImageResource(R.drawable.play);
                    toolsnew.this.card4.setCardBackgroundColor(ActivityCompat.getColor(toolsnew.this.getActivity(), R.color.sosgreen));
                }
                isServiceRunning = !isServiceRunning;

            }
        });


        this.card5.setOnClickListener(new View.OnClickListener() { // from class: com.example.evacunation.toolsnew.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                toolsnew.this.getActivity().startActivity(new Intent(toolsnew.this.getActivity(), hotlines.class));
            }
        });
        this.card6.setOnClickListener(new View.OnClickListener() { // from class: com.example.evacunation.disasterfragment.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                toolsnew.this.getActivity().startActivity(new Intent(toolsnew.this.getActivity(), lists.class));
            }
        });

        //dark mode codes
//        this.card7.setOnClickListener(new View.OnClickListener() {
//            @Override // android.view.View.OnClickListener
//            public void onClick(View view) {
//                if (view.isPressed() && AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
////                    toolsnew.this.mode.setText("Turn ON\nDark Mode");
////                    toolsnew.this.imagemode.setImageResource(R.drawable.lightmode);
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                    Toast.makeText(toolsnew.this.getActivity(), "Dark mode is turned ON", Toast.LENGTH_SHORT).show();
//                    return;
//                }
////                toolsnew.this.mode.setText("Turn ON\nLight Mode");
////                toolsnew.this.imagemode.setImageResource(R.drawable.darkmode);
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                Toast.makeText(toolsnew.this.getActivity(), "Light mode turned ON", Toast.LENGTH_SHORT).show();
//            }
//        });
        return this.view;
    }

    private void sendSms() {
        if (ContextCompat.checkSelfPermission(getActivity(), "android.permission.SEND_SMS") != 0) {
            requestPermissions(new String[]{"android.permission.SEND_SMS"}, 3);
        } else {
            getLocation();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getLocation() {
        if (ContextCompat.checkSelfPermission(getActivity(), "android.permission.ACCESS_FINE_LOCATION") != 0) {
            requestPermissions(new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 4);
        } else {
            LocationServices.getFusedLocationProviderClient((Activity) getActivity()).getLastLocation().addOnCompleteListener(new OnCompleteListener() { // from class: com.example.evacunation.toolsnew.8
                @Override // com.google.android.gms.tasks.OnCompleteListener
                public void onComplete(Task task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(toolsnew.this.getActivity(), "Current Location Found", Toast.LENGTH_SHORT).show();
                        Log.d("ContentValues", "onComplete: found location!");
                        Location location = (Location) task.getResult();
                        if (location != null) {
                            final double latitude = location.getLatitude();
                            final double longitude = location.getLongitude();

                            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(toolsnew.this.getActivity(), R.style.CustomDialogTheme);
                            builder.setIcon(R.drawable.messageemergency);
                            builder.setTitle("Input your Emergency Message below or leave it blank.");
                            builder.setMessage("NOTE: This will also send your PRECISE CURRENT LOCATION to the Receiver.");
                            final EditText editText = new EditText(toolsnew.this.getActivity());
                            editText.setInputType(1);
                            editText.requestFocus();
                            builder.setView(editText);
                            builder.setPositiveButton("SEND", new DialogInterface.OnClickListener() { // from class: com.example.evacunation.toolsnew.8.2
                                @Override // android.content.DialogInterface.OnClickListener
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    toolsnew.this.m_Text = editText.getText().toString();
                                    Intent intent = new Intent (Intent.ACTION_SEND);
                                    intent.setType ("text/plain");
                                    String Body ="Emergency Message";
                                    String Sub ="'" + toolsnew.this.m_Text + "'\n-Emergency Message of the sender\n\nThe sender of this message is in grave danger or in need of immediate help. Attached below is the link of the map where the person in emergency is currently located. Contact the appropriate authorities now to send help. \n\nAng nagpadala ng mensaheng ito ay nasa matinding panganib o nangangailangan ng agarang tulong. Nakalakip sa ibaba ang link ng mapa kung saan kasalukuyang matatagpuan ang taong nasa emergency. Makipag-ugnayan sa naaangkop na awtoridad ngayon upang magpadala ng tulong.\n\n" + ("https://www.google.com/maps/search/?api=1&query=" + latitude + "%2C" + longitude) + "\n\n (This emergency message is sent using EvacuNation App)";
                                    intent.putExtra (Intent.EXTRA_TEXT, Body) ;
                                    intent.putExtra (Intent.EXTRA_TEXT, Sub);
                                    startActivity (Intent.createChooser (intent, "Share Using"));
                                }
                            });
                            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() { // from class: com.example.evacunation.toolsnew.8.3
                                @Override // android.content.DialogInterface.OnClickListener
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });
                            builder.show();
                            return;
                        }
                        return;
                    }
                    Log.d("ContentValues", "onComplete: current location is null");
                    Toast.makeText(toolsnew.this.getActivity(), "Unable to get Current Location", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void turnOnFlash() {
        if (ContextCompat.checkSelfPermission(getActivity(), "android.permission.CAMERA") != 0) {
            requestPermissions(new String[]{"android.permission.CAMERA"}, 2);
        } else if (Build.VERSION.SDK_INT >= 23) {
            try {
                CameraManager cameraManager = (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);
                this.cameraManager = cameraManager;
                if (cameraManager != null && !this.isLighOn) {
                    this.cameraManager.setTorchMode(cameraManager.getCameraIdList()[0], true);
                    this.isLighOn = true;
                }
            } catch (CameraAccessException e) {
                Log.e("ContentValues", e.toString());
            }
        } else if (!this.isLighOn) {
            Camera open = Camera.open();
            this.camera = open;
            Camera.Parameters parameters = open.getParameters();
            this.params = parameters;
            parameters.setFlashMode("torch");
            this.camera.setParameters(this.params);
            this.camera.startPreview();
            this.isLighOn = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void turnOffFlash() {
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                CameraManager cameraManager = (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);
                this.cameraManager = cameraManager;
                if (cameraManager != null && this.isLighOn) {
                    this.cameraManager.setTorchMode(cameraManager.getCameraIdList()[0], false);
                    this.isLighOn = false;
                }
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        } else if (this.isLighOn) {
            Camera.Parameters parameters = this.camera.getParameters();
            this.params = parameters;
            parameters.setFlashMode("off");
            this.camera.setParameters(this.params);
            this.camera.stopPreview();
            this.isLighOn = false;
        }
    }
}
