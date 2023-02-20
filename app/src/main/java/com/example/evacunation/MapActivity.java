package com.example.evacunation;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String COARSE_LOCATION = "android.permission.ACCESS_COARSE_LOCATION";
    private static final float DEFAULT_ZOOM = 14.0f;
    private static final String FINE_LOCATION = "android.permission.ACCESS_FINE_LOCATION";
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final String TAG = "MapActivity";
    private ImageView mGps;
    private CardView mHome;
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private EditText mSearchText;

    @Override // com.google.android.gms.maps.OnMapReadyCallback
    public void onMapReady(@NonNull GoogleMap googleMap) {
//        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");
        this.mMap = googleMap;
        new location().onMapReady(googleMap);
        if (this.mLocationPermissionsGranted) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this, FINE_LOCATION) == 0 || ActivityCompat.checkSelfPermission(this, COARSE_LOCATION) == 0) {
                this.mMap.setMyLocationEnabled(true);
                this.mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                try {
                    GeoJsonLayer layer = new GeoJsonLayer(mMap, R.raw.phfaultslines, this);
                    GeoJsonPolygonStyle polygonStyle = layer.getDefaultPolygonStyle();
                    polygonStyle.setStrokeColor(Color.RED);
                    polygonStyle.setStrokeWidth(1);
                    layer.addLayerToMap();
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(this, "Black Solid Lines are Fault Lines", Toast.LENGTH_LONG).show();
                /*try {
                    GeoJsonLayer layer = new GeoJsonLayer(mMap, R.raw.boundaries, this);
                    GeoJsonPolygonStyle polygonStyle = layer.getDefaultPolygonStyle();
                    polygonStyle.setStrokeColor(Color.GRAY);
                    polygonStyle.setStrokeWidth(3);
                    layer.addLayerToMap();
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }*/
                this.mMap.getUiSettings().setMyLocationButtonEnabled(true);
                this.mMap.getUiSettings().setCompassEnabled(true);
                this.mMap.getUiSettings().setZoomControlsEnabled(true);
                this.mMap.getUiSettings().setMapToolbarEnabled(true);
                this.mMap.getUiSettings().setRotateGesturesEnabled(true);
                this.mMap.getUiSettings().setTiltGesturesEnabled(true);
                init();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_map);
        this.mSearchText = findViewById(R.id.input_search);
        this.mGps = findViewById(R.id.ic_gps);
        this.mHome = findViewById(R.id.cardviewhome);
        Window window = getWindow();
        window.addFlags(Integer.MIN_VALUE);
        window.clearFlags(67108864);
        getLocationPermission();


    }

    private void init() {
        Log.d(TAG, "init: initializing");
        // from class: com.example.evacunation.MapActivity.1
// android.widget.TextView.OnEditorActionListener
        this.mSearchText.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i != 3 && i != 6 && keyEvent.getAction() != 0 && keyEvent.getAction() != 66) {
                return false;
            }
            MapActivity.this.geoLocate();
            return false;
        });
        // from class: com.example.evacunation.MapActivity.2
// android.view.View.OnClickListener
        this.mHome.setOnClickListener(view -> MapActivity.this.startActivity(new Intent(MapActivity.this, MainActivity.class)));
        // from class: com.example.evacunation.MapActivity.3
// android.view.View.OnClickListener
        this.mGps.setOnClickListener(view -> {
            Log.d(MapActivity.TAG, "onClick: clicked gps icon");
            MapActivity.this.getDeviceLocation();
        });
        hideSoftKeyboard();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void geoLocate() {
        Log.d(TAG, "geoLocate: geolocating");
        String obj = this.mSearchText.getText().toString();
        Geocoder geocoder = new Geocoder(this);
        List<Address> arrayList = new ArrayList<>();
        try {
            arrayList = geocoder.getFromLocationName(obj, 3);
        } catch (IOException e) {
            Log.d(TAG, "geoLocate: IOException: " + e.getMessage());
        }
        if (arrayList.size() > 0) {
            Address address = arrayList.get(0);
            Log.d(TAG, "geoLocate: found a location: " + address.toString());
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM, address.getAddressLine(0));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting the devices current location");
        FusedLocationProviderClient mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (this.mLocationPermissionsGranted) {
                mFusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener() { // from class: com.example.evacunation.MapActivity.4
                    @Override // com.google.android.gms.tasks.OnCompleteListener
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(MapActivity.TAG, "onComplete: found location!");
                            Location location = (Location) task.getResult();
                            MapActivity.this.moveCamera(new LatLng(location.getLatitude(), location.getLongitude()), MapActivity.DEFAULT_ZOOM, "My Location");
                            return;
                        }
                        Log.d(MapActivity.TAG, "onComplete: current location is null");
                        Toast.makeText(MapActivity.this, "Unable to get Current Location", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (SecurityException e) {
            Log.d(TAG, "getDeviceLocation: SecurityException" + e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void moveCamera(LatLng latLng, float f, String str) {
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng " + latLng.longitude);
        this.mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, f));
        if (!str.equals("My Location")) {
            this.mMap.addMarker(new MarkerOptions().position(latLng).snippet(str));
        }
        hideSoftKeyboard();
    }

    private void initMap() {
        Log.d(TAG, "initMap: initializing map");
        ((SupportMapFragment) Objects.requireNonNull(getSupportFragmentManager().findFragmentById(R.id.map))).getMapAsync(this);
    }

    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] strArr = {FINE_LOCATION, COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(getApplicationContext(), FINE_LOCATION) != 0) {
            ActivityCompat.requestPermissions(this, strArr, LOCATION_PERMISSION_REQUEST_CODE);
        } else if (ContextCompat.checkSelfPermission(getApplicationContext(), COARSE_LOCATION) == 0) {
            this.mLocationPermissionsGranted = true;
            initMap();
        } else {
            ActivityCompat.requestPermissions(this, strArr, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        Log.d(TAG, "onRequestPermissionsResult: called.");
        int i2 = 0;
        this.mLocationPermissionsGranted = false;
        if (i == LOCATION_PERMISSION_REQUEST_CODE && iArr.length > 0) {
            while (true) {
                if (i2 >= iArr.length) {
                    break;
                } else if (iArr[i2] != 0) {
                    this.mLocationPermissionsGranted = false;
                    Log.d(TAG, "onRequestPermissionsResult: permission denied");
                    break;
                } else {
                    i2++;
                }
            }
            Log.d(TAG, "onRequestPermissionsResult: permission granted");
            this.mLocationPermissionsGranted = true;
            initMap();
        }
    }

    private void hideSoftKeyboard() {
        getWindow().setSoftInputMode(3);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        super.onBackPressed();
    }
}
