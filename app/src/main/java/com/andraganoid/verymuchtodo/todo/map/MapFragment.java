package com.andraganoid.verymuchtodo.todo.map;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.andraganoid.verymuchtodo.R;
import com.andraganoid.verymuchtodo.model.ToDoLocation;
import com.andraganoid.verymuchtodo.todo.TodoBaseFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


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
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setCompassEnabled(true);

        setMarkers();


        // Add a marker in Sydney, Australia, and move the camera.
//        LatLng sydney = new LatLng(-34, 151);
//        map.addMarker(new MarkerOptions().positiony(sydney).title("Marker in Sydney"));
        //  map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


    private void setMarkers() {
        // LatLng latLng;

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                toDoViewModel.mLocation.getLatitude(),
                toDoViewModel.mLocation.getLongitude()), 12f));

        toDoViewModel.getLocationList().observe(getViewLifecycleOwner(), new Observer<ArrayList<ToDoLocation>>() {
            @Override
            public void onChanged(ArrayList<ToDoLocation> toDoLocations) {
                for (ToDoLocation todoLocation : toDoLocations) {

                    LatLng latLng = new LatLng(todoLocation.getLatitude(), todoLocation.getLongitude());
                    // String title = toDoViewModel.getLastEdit(todoLocation.getUser().getName(), todoLocation.getTimestamp());
                    map.addMarker(
                            new MarkerOptions()
                                    .position(latLng)
                                    .title(todoLocation.getUser().getName())
                                    .snippet(toDoViewModel.getFormattedDate(todoLocation.getTimestamp())));
                           // .showInfoWindow();
                }
            }
        });


    }

}
