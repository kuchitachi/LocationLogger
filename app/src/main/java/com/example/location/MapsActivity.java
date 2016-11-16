package com.example.location;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.BaseColumns;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.database.sqlite.*;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    EditText titleEdit;
    EditText contentEdit;

    private static final int go =3;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        titleEdit = (EditText)findViewById(R.id.edit_message);
        contentEdit = (EditText)findViewById(R.id.editText2);

        ((Button)findViewById(R.id.button)).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        DBHandler dbhandler = DBHandler.open(this);

        if(v.getId() == R.id.button) {
            String carName = titleEdit.getText().toString();

            long cnt = dbhandler.insert(carName);

            if(cnt == -1) {
                Toast.makeText(this,
                        carName + "가 테이블에 추가되지 않았습니다", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,
                        carName + "가 테이블에 추가되었습니다.", Toast.LENGTH_LONG).show();
            }
        }
        dbhandler.close();
    }

    //Restart, Pause할때마다 위치를 얻게하고싶어서 이렇게 했습니다. 더 좋은 방법이 있을거같지만 잘 모르겠습니다.
    protected void onRestart() {
        super.onRestart();
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    protected void onPuase() {
        super.onPause();
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        // Permisson 얻는 과정 (자세한 내용은 정확히 모르겠습니다)
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // 이 권한을 필요한 이유를 설명해야하는가?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // 다이어로그같은것을 띄워서 사용자에게 해당 권한이 필요한 이유에 대해 설명합니다
                // 해당 설명이 끝난뒤 requestPermissions()함수를 호출하여 권한허가를 요청해야 합니다

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        go);
                // 필요한 권한과 요청 코드를 넣어서 권한허가요청에 대한 결과를 받아야 합니다

            }
        }


        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER); //내 위치 받기

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude()); // 내 위치를 받아서 생성
        mMap.addMarker(new MarkerOptions().position(sydney).title("You are here")); // 마커 생성
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


}