package com.andraganoid.verymuchtodo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.andraganoid.verymuchtodo.Views.ItemFragment;
import com.andraganoid.verymuchtodo.Views.ListFragment;
import com.andraganoid.verymuchtodo.Views.MessageFragment;
import com.andraganoid.verymuchtodo.Views.UserFragment;
import com.andraganoid.verymuchtodo.auth.main.MainFragment;
import com.andraganoid.verymuchtodo.auth.login.LoginFragment;
import com.andraganoid.verymuchtodo.todo.map.MapFragment;
import com.andraganoid.verymuchtodo.auth.register.RegisterFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    ToDoViewModel toDoViewModel;
    SharedPreferences prefs;


    public static final Fragment BASE_FRAGMENT = new MainFragment();
    public static final Fragment LIST_FRAGMENT = new ListFragment();
    public static final Fragment REGISTER_FRAGMENT = new RegisterFragment();
    public static final Fragment LOGIN_FRAGMENT = new LoginFragment();
    public static final Fragment ITEM_FRAGMENT = new ItemFragment();
    public static final Fragment MESSAGE_FRAGMENT = new MessageFragment();
    public static final Fragment USER_FRAGMENT = new UserFragment();
    public static final Fragment MAP_FRAGMENT = new MapFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        toDoViewModel = ViewModelProviders.of(this).get(ToDoViewModel.class);
        toDoViewModel.mAuth = FirebaseAuth.getInstance();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        navigateToFragment(BASE_FRAGMENT);
    }


    public void navigateToFragment(Fragment fragment) {

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.todo_fragment, fragment)
                    .commit();
        }
    }

    public void loginSuccesfully(FirebaseUser user) {
        prefs.edit().putString("PREFS_ID", user.getUid())
                .putString("PREFS_NAME", user.getDisplayName())
                .putString("PREFS_EMAIL", user.getEmail())
                .apply();

        startActivity(new Intent(this, Todo.class));
    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if (currentUser != null) {
//            loginSuccesfully(currentUser, false);
//        } else {
//            findViewById(R.id.register_form).setVisibility(View.GONE);
//            findViewById(R.id.sign_in_form).setVisibility(View.GONE);
//            findViewById(R.id.login_info).setVisibility(View.VISIBLE);
//        }
//
//    }
//
//
//    public void register(View v) {
//        findViewById(R.id.login_info).setVisibility(View.GONE);
//        findViewById(R.id.register_form).setVisibility(View.VISIBLE);
//    }
//
//    public void signIn(View v) {
//        findViewById(R.id.login_info).setVisibility(View.GONE);
//        findViewById(R.id.sign_in_form).setVisibility(View.VISIBLE);
//    }
//
//    public void signInConfirm(View v) {
//
//        String password = ((EditText) findViewById(R.id.sign_in_password)).getText().toString().trim();
//        String email = ((EditText) findViewById(R.id.sign_in_email)).getText().toString().trim();
//
//
//        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
//            mAuth.signInWithEmailAndPassword(email, password)
//                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                FirebaseUser user = mAuth.getCurrentUser();
//                                loginSuccesfully(user, false);
//                            } else {
//                                Toast.makeText(MainActivity.this, "Authentification failed.", Toast.LENGTH_LONG).show();
//                                findViewById(R.id.login_info).setVisibility(View.VISIBLE);
//                                findViewById(R.id.sign_in_form).setVisibility(View.GONE);
//                                findViewById(R.id.register_form).setVisibility(View.GONE);
//                            }
//                        }
//                    });
//        }
//    }
//
//
//    public void registerConfirm(View v) {
//
//
//        final String displayName = ((EditText) findViewById(R.id.register_username)).getText().toString().trim();
//        String email = ((EditText) findViewById(R.id.register_email)).getText().toString().trim();
//        String password = ((EditText) findViewById(R.id.register_password)).getText().toString().trim();
//
//        if (isNameValid(displayName) && isEmailValid(email) && isPasswordValid(password)) {
//            mAuth.createUserWithEmailAndPassword(email, password)
//                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                final FirebaseUser user = mAuth.getCurrentUser();
//                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                                        .setDisplayName(displayName).build();
//                                user.updateProfile(profileUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void aVoid) {
//                                        loginSuccesfully(user, true);
//                                    }
//                                }).addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                    }
//                                });
//
//
//                            } else {
//                                Toast.makeText(MainActivity.this, "Authentification failed.", Toast.LENGTH_LONG).show();
//                                findViewById(R.id.login_info).setVisibility(View.VISIBLE);
//                                findViewById(R.id.sign_in_form).setVisibility(View.GONE);
//                                findViewById(R.id.register_form).setVisibility(View.GONE);
//                            }
//                        }
//                    });
//        }
//    }
//
//    private boolean isEmailValid(String email) {
//        System.out.println("EMAIL: " + email);
//        boolean e = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
//        if (!e) {
//            Toast.makeText(this, "Email address is not valid!", Toast.LENGTH_SHORT).show();
//        }
//        return e;
//
//    }
//
//    private boolean isNameValid(String name) {
//        boolean n = !TextUtils.isEmpty(name) && name.length() > 3;
//        if (!n) {
//            Toast.makeText(this, "Name must be min 4 chars long!", Toast.LENGTH_SHORT).show();
//        }
//        return n;
//    }
//
//    private boolean isPasswordValid(String pass) {
//        boolean p = !TextUtils.isEmpty(pass) && pass.length() > 3;
//        if (!p) {
//            Toast.makeText(this, "password must be min 4 chars long!", Toast.LENGTH_SHORT).show();
//        }
//        return p;
//    }
//
//    private void loginSuccesfully(FirebaseUser user, boolean register) {
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        prefs.edit().putString("id", user.getUid())
//                .putString("name", user.getDisplayName())
//                .putString("email", user.getEmail())
//                .putBoolean("register", register)
//                .apply();
//
//        Intent intent = new Intent(this, Todo.class);
//        startActivity(intent);
    //   }
}