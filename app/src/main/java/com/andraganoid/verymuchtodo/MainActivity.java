package com.andraganoid.verymuchtodo;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.andraganoid.verymuchtodo.auth.login.LoginFragment;
import com.andraganoid.verymuchtodo.todo.Todo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class MainActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    private final int LOCATION = 111;
    public MainViewModel mainViewModel;
    private SharedPreferences prefs;
    public boolean leave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        leave = true;
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.mAuth = FirebaseAuth.getInstance();
        getPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(LOCATION)
    private void getPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            checkLoginStatus();
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.get_permission), LOCATION, perms);
        }
    }

    private void checkLoginStatus() {
        if (prefs.getString("PREFS_ID", "").isEmpty()) {
            navigateToFragment(new LoginFragment());
        } else {
            startActivity(new Intent(this, Todo.class));
        }
    }


    public void navigateToFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_fragment, fragment)
                    .commit();
        }
    }

    public void setUser(FirebaseUser user) {
        prefs.edit().putString("PREFS_ID", user.getUid())
                .putString("PREFS_NAME", user.getDisplayName())
                .putString("PREFS_EMAIL", user.getEmail())
                .apply();
        startActivity(new Intent(this, Todo.class));
    }


    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        checkLoginStatus();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        getPermission();
    }

    @Override
    public void onBackPressed() {
        if (leave) {
            super.onBackPressed();
        } else {
            navigateToFragment(new LoginFragment());
        }
    }

}