package com.andraganoid.verymuchtodo.J.todo.location;

import android.annotation.SuppressLint;
import android.app.Application;
import android.location.Location;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import androidx.annotation.NonNull;


public class LocationHandler {

    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private LocationHandlerCallback lCallback;
    private final int INTERVAL = 5 * 60 * 1000;
    private final int MAX_WAIT_TIME = 10 * 60 * 1000;

    public LocationHandler(@NonNull Application application, LocationHandlerCallback lCallback) {
        super();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(application);
        this.lCallback = lCallback;
        setLocationCallback();
        startLocationUpdates();
    }

    @SuppressLint("MissingPermission")
    public void getCurrentLocation() {
        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> lCallback.saveCurrentLocation(location));
    }

    private void setLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    lCallback.saveCurrentLocation(location);
                }
            }
        };
    }


    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(createLocationRequest(),
                locationCallback,
                null);
    }

    protected LocationRequest createLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(INTERVAL);
        // locationRequest.setFastestInterval(1 * 30 * 1000);
        locationRequest.setMaxWaitTime(MAX_WAIT_TIME);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        return locationRequest;
    }
}
