package com.andraganoid.verymuchtodo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.andraganoid.verymuchtodo.auth.AuthBaseFragment;
import com.andraganoid.verymuchtodo.auth.login.LoginFragment;
import com.andraganoid.verymuchtodo.auth.register.RegisterFragment;
import com.andraganoid.verymuchtodo.todo.Todo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    MainViewModel mainViewModel;
    SharedPreferences prefs;

    public static final Fragment MAIN_FRAGMENT = new AuthBaseFragment();
    public static final Fragment REGISTER_FRAGMENT = new RegisterFragment();
    public static final Fragment LOGIN_FRAGMENT = new LoginFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.mAuth = FirebaseAuth.getInstance();
        // navigateToFragment(MAIN_FRAGMENT);
        checkLoginStatus();
    }

    private void checkLoginStatus() {

        if (prefs.getString("PREFS_ID", "").isEmpty()) {
            navigateToFragment(LOGIN_FRAGMENT);
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


}