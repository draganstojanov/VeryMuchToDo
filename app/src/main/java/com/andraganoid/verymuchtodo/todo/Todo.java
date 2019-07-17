package com.andraganoid.verymuchtodo.todo;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.andraganoid.verymuchtodo.MainActivity;
import com.andraganoid.verymuchtodo.R;
import com.andraganoid.verymuchtodo.databinding.ActivityTodoBinding;
import com.andraganoid.verymuchtodo.todo.item.ItemFragment;
import com.andraganoid.verymuchtodo.todo.itemedit.ItemEditFragment;
import com.andraganoid.verymuchtodo.todo.list.ListFragment;
import com.andraganoid.verymuchtodo.todo.listedit.ListEditFragment;
import com.andraganoid.verymuchtodo.todo.map.MapFragment;
import com.andraganoid.verymuchtodo.todo.menu.MenuAlert;
import com.andraganoid.verymuchtodo.todo.menu.MenuClicker;
import com.andraganoid.verymuchtodo.todo.menu.TodoBars;
import com.andraganoid.verymuchtodo.todo.message.MessageFragment;
import com.andraganoid.verymuchtodo.todo.users.UserFragment;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.firebase.auth.FirebaseAuth;


public class Todo extends AppCompatActivity implements MenuClicker {

    public ToDoViewModel toDoViewModel;
    private SharedPreferences prefs;
    public ActivityTodoBinding binding;
    //public LocationHandler toDoLocation;
    private LocationCallback locationCallback;

    public final Fragment LIST_FRAGMENT = new ListFragment();
    public final Fragment LIST_EDIT_FRAGMENT = new ListEditFragment();
    public final Fragment ITEM_FRAGMENT = new ItemFragment();
    public final Fragment ITEM_EDIT_FRAGMENT = new ItemEditFragment();
    public final Fragment MESSAGE_FRAGMENT = new MessageFragment();
    public final Fragment USER_FRAGMENT = new UserFragment();
    public final Fragment MAP_FRAGMENT = new MapFragment();

    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
        navigateToFragment(LIST_FRAGMENT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_todo);
        toDoViewModel = ViewModelProviders.of(this).get(ToDoViewModel.class);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        registerObservers();
        binding.setMenuAlert(toDoViewModel.menuAlert.getValue());
        binding.setClicker(this);
        // toDoLocation = new LocationHandler(toDoViewModel.getApplication());
        toDoViewModel.getCurrentLocation();
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {


                    toDoViewModel.saveCurrentLocation(location);

                    Log.d("LOCATIONRESULT",
                            location.getLatitude() + "+"
                                    + location.getLongitude() + "+"
                                    + location.getTime() + "+"
                                    + toDoViewModel.getFormattedDate(location.getTime()));
                }
            }
        };
    }


    @Override
    protected void onStart() {
        super.onStart();
        toDoViewModel.setFirebaseListeners();
    }


    private void registerObservers() {

        toDoViewModel.todoBars.observe(this, new Observer<TodoBars>() {
            @Override
            public void onChanged(TodoBars todoBars) {
                binding.todoToolbar.setTitle(todoBars.getTitle());
                binding.todoToolbar.setSubtitle(todoBars.getSubtitle());
            }
        });

        toDoViewModel.menuAlert.observe(this, new Observer<MenuAlert>() {
            @Override
            public void onChanged(MenuAlert menuAlert) {
                binding.invalidateAll();
            }
        });
    }


    public void navigateToFragment(Fragment fragment) {

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.todo_fragment, fragment)
                    .commit();
        }
    }


    @Override
    public void onListItemClicked() {
        navigateToFragment(LIST_FRAGMENT);
    }

    @Override
    public void onMessageItemClicked() {
        navigateToFragment(MESSAGE_FRAGMENT);
    }

    @Override
    public void onUserItemClicked() {
        navigateToFragment(USER_FRAGMENT);
    }

    @Override
    public void onMapItemClicked() {
        navigateToFragment(MAP_FRAGMENT);
    }

    @Override
    public void onLogoutItemClicked() {
        prefs.edit().putString("PREFS_ID", "")
                .putString("PREFS_NAME", "")
                .putString("PREFS_EMAIL", "")
                .putBoolean("PREFS_IS_USER_REGISTRED", false)
                .apply();
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, MainActivity.class));
        finishAffinity();
    }


    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {

        toDoViewModel.fusedLocationClient.requestLocationUpdates(createLocationRequest(),
                locationCallback,
                null /* Looper */);
    }

    protected LocationRequest createLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(5 * 60 * 1000);
        // locationRequest.setFastestInterval(1 * 30 * 1000);
        locationRequest.setMaxWaitTime(10 * 60 * 1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        return locationRequest;
    }


}
