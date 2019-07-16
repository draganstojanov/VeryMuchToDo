package com.andraganoid.verymuchtodo.todo;

public class LocationHandler  {

//    private FusedLocationProviderClient fusedLocationClient;
//
//    public LocationHandler(@NonNull Application application) {
//        super(application);
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(application);
//    }
//
//    @SuppressLint("MissingPermission")
//    public void getCurrentLocation() {
//        Toast.makeText(getApplication(), "GET LOCATION", Toast.LENGTH_SHORT).show();
//        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> saveCurrentLocation(location));
//
//    }
//
//    void saveCurrentLocation(Location location) {
//        // Toast.makeText(getApplication(), String.valueOf(location.getTime()), Toast.LENGTH_SHORT).show();
//        LatLng lastSavedLocation = new LatLng(prefs.getFloat("PREFS_LATITUDE",
//                0), prefs.getFloat("PREFS_LONGITUDE", 0));
//        LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
//        mLocation = location;
//
//
////        Toast.makeText(getApplication(), String.valueOf(mLocation.getLatitude()), Toast.LENGTH_SHORT).show();
//        if (lastSavedLocation.latitude == 0 && lastSavedLocation.longitude == 0) {
//            addDocument(new Document(new ToDoLocation(mUser, location)));
//            prefs.edit()
//                    .putFloat("PREFS_LATITUDE", (float) currentLocation.latitude)
//                    .putFloat("PREFS_LONGITUDE", (float) currentLocation.longitude)
//                    .apply();
//        } else {
//            if (SphericalUtil.computeDistanceBetween(lastSavedLocation, currentLocation) > 100) {
//                updateDocument(new Document(new ToDoLocation(mUser, location)));
//                prefs.edit()
//                        .putFloat("PREFS_LATITUDE", (float) currentLocation.latitude)
//                        .putFloat("PREFS_LONGITUDE", (float) currentLocation.longitude)
//                        .apply();
//                // mLocation = location;
//
//            }
//        }
//        // Toast.makeText(getApplication(), String.valueOf(mLocation.getLatitude()), Toast.LENGTH_SHORT).show();
//    }
}
