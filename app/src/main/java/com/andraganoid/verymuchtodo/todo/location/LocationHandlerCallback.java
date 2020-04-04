package com.andraganoid.verymuchtodo.todo.location;

import android.location.Location;

public interface LocationHandlerCallback {
    void saveCurrentLocation(Location location);
}
