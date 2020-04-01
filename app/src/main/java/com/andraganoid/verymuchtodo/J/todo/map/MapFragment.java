package com.andraganoid.verymuchtodo.J.todo.map;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andraganoid.verymuchtodo.J.model.ToDoLocation;
import com.andraganoid.verymuchtodo.J.todo.TodoBaseFragment;
import com.andraganoid.verymuchtodo.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class MapFragment extends TodoBaseFragment implements OnMapReadyCallback {

    private GoogleMap map;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toDoViewModel.setTodoBars(getString(R.string.map), false);
    }

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
    }


    private void setMarkers() {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
                toDoViewModel.mLocation.getLatitude(),
                toDoViewModel.mLocation.getLongitude()), 12f));
        toDoViewModel.getLocationList().observe(getViewLifecycleOwner(), toDoLocations -> {
            for (ToDoLocation todoLocation : toDoLocations) {
                LatLng latLng = new LatLng(todoLocation.getLatitude(), todoLocation.getLongitude());
                map.addMarker(
                        new MarkerOptions()
                                .position(latLng)
                                .title(todoLocation.getUser().getName())
                                .snippet(toDoViewModel.getFormattedDate(todoLocation.getTimestamp())));
            }
        });
    }
}
