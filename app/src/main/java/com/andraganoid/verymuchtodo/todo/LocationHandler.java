package com.andraganoid.verymuchtodo.todo;

import android.annotation.SuppressLint;
import android.app.Application;
import android.location.Location;

import androidx.annotation.NonNull;

import com.andraganoid.verymuchtodo.model.Document;
import com.andraganoid.verymuchtodo.model.ToDoLocation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;


public class LocationHandler extends ToDoViewModel {

    private FusedLocationProviderClient fusedLocationClient;

    public LocationHandler(@NonNull Application application) {
        super(application);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(application);
    }

    @SuppressLint("MissingPermission")
    public void getCurrentLocation() {

        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> saveCurrentLocation(location));

        //  saveCurrentLocation(fusedLocationClient.getLastLocation().getResult());

    }

    void saveCurrentLocation(Location location) {

        LatLng lastSavedLocation = new LatLng(prefs.getFloat("PREFS_LATITUDE",
                0), prefs.getFloat("PREFS_LONGITUDE", 0));

        if (lastSavedLocation.latitude == 0 && lastSavedLocation.longitude == 0) {
            addDocument(new Document(new ToDoLocation(mUser.get(), location)));
        } else {

            LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());

            if (SphericalUtil.computeDistanceBetween(lastSavedLocation, currentLocation) > 100) {
                updateDocument(new Document(new ToDoLocation(mUser.get(), location)));
                prefs.edit()
                        .putFloat("PREFS_LATITUDE", (float) currentLocation.latitude)
                        .putFloat("PREFS_LONGITUDE", (float) currentLocation.longitude)
                        .apply();
            }

        }
    }
}
