package com.andraganoid.verymuchtodo.todo.map;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.andraganoid.verymuchtodo.R;
import com.andraganoid.verymuchtodo.todo.TodoBaseFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;


public class MapFragment extends TodoBaseFragment implements OnMapReadyCallback {

    private GoogleMap map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_map, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setMapToolbarEnabled(false);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setCompassEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (toDo.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && toDo.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                map.setMyLocationEnabled(true);
            }
        }

        // Add a marker in Sydney, Australia, and move the camera.
//        LatLng sydney = new LatLng(-34, 151);
//        map.addMarker(new MarkerOptions().positiony(sydney).title("Marker in Sydney"));
//        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


  void  checkPermissions(){




  }
}
