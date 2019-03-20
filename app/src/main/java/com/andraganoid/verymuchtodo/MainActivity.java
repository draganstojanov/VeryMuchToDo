package com.andraganoid.verymuchtodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            loginSuccesfully(currentUser, false);
        } else {
            findViewById(R.id.register_form).setVisibility(View.GONE);
            findViewById(R.id.sign_in_form).setVisibility(View.GONE);
            findViewById(R.id.login_info).setVisibility(View.VISIBLE);
        }

    }


    public void register(View v) {
        findViewById(R.id.login_info).setVisibility(View.GONE);
        findViewById(R.id.register_form).setVisibility(View.VISIBLE);
    }

    public void signIn(View v) {
        findViewById(R.id.login_info).setVisibility(View.GONE);
        findViewById(R.id.sign_in_form).setVisibility(View.VISIBLE);
    }

    public void signInConfirm(View v) {

        String password = ((EditText) findViewById(R.id.sign_in_password)).getText().toString().trim();
        String email = ((EditText) findViewById(R.id.sign_in_email)).getText().toString().trim();


        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener <AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task <AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                loginSuccesfully(user, false);
                            } else {
                                Toast.makeText(MainActivity.this, "Authentification failed.", Toast.LENGTH_LONG).show();
                                findViewById(R.id.login_info).setVisibility(View.VISIBLE);
                                findViewById(R.id.sign_in_form).setVisibility(View.GONE);
                                findViewById(R.id.register_form).setVisibility(View.GONE);
                            }
                        }
                    });
        }
    }


    public void registerConfirm(View v) {


        final String displayName = ((EditText) findViewById(R.id.register_username)).getText().toString().trim();
        String email = ((EditText) findViewById(R.id.register_email)).getText().toString().trim();
        String password = ((EditText) findViewById(R.id.register_password)).getText().toString().trim();

        if (isNameValid(displayName) && isEmailValid(email) && isPasswordValid(password)) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener <AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task <AuthResult> task) {
                            if (task.isSuccessful()) {
                                final FirebaseUser user = mAuth.getCurrentUser();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(displayName).build();
                                user.updateProfile(profileUpdates).addOnSuccessListener(new OnSuccessListener <Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        loginSuccesfully(user, true);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                    }
                                });


                            } else {
                                Toast.makeText(MainActivity.this, "Authentification failed.", Toast.LENGTH_LONG).show();
                                findViewById(R.id.login_info).setVisibility(View.VISIBLE);
                                findViewById(R.id.sign_in_form).setVisibility(View.GONE);
                                findViewById(R.id.register_form).setVisibility(View.GONE);
                            }
                        }
                    });
        }
    }

    private boolean isEmailValid(String email) {
        System.out.println("EMAIL: " + email);
        boolean e = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        if (!e) {
            Toast.makeText(this, "Email address is not valid!", Toast.LENGTH_SHORT).show();
        }
        return e;

    }

    private boolean isNameValid(String name) {
        boolean n = !TextUtils.isEmpty(name) && name.length() > 3;
        if (!n) {
            Toast.makeText(this, "Name must be min 4 chars long!", Toast.LENGTH_SHORT).show();
        }
        return n;
    }

    private boolean isPasswordValid(String pass) {
        boolean p = !TextUtils.isEmpty(pass) && pass.length() > 3;
        if (!p) {
            Toast.makeText(this, "password must be min 4 chars long!", Toast.LENGTH_SHORT).show();
        }
        return p;
    }

    private void loginSuccesfully(FirebaseUser user, boolean register) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.edit().putString("id", user.getUid())
                .putString("name", user.getDisplayName())
                .putString("email", user.getEmail())
                .putBoolean("register", register)
                .apply();

        Intent intent = new Intent(this, Todo.class);
        startActivity(intent);
    }
}