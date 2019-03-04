package com.andraganoid.verymuchtodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
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
            loginSuccesfully(currentUser);
        } else {
            findViewById(R.id.login_info).setVisibility(View.VISIBLE);
        }

    }


    public void register(View v) {
        findViewById(R.id.login_info).setVisibility(View.GONE);
        findViewById(R.id.register_form).setVisibility(View.VISIBLE);
    }

    public void signIn(View v) {
        findViewById(R.id.login_info).setVisibility(View.GONE);
        findViewById(R.id.sig_in_form).setVisibility(View.VISIBLE);
    }

    public void signInConfirm(View v) {

        String email = ((EditText) findViewById(R.id.sign_in_username)).getText().toString();
        String password = ((EditText) findViewById(R.id.sign_in_password)).getText().toString();


        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener <AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task <AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                // Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                //  updateUI(user);
                                loginSuccesfully(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("SIGN IN FAIL", "signInWithEmail:failure" + task.getException());
                                Toast.makeText(MainActivity.this, "Authentification failed.", Toast.LENGTH_LONG).show();
                                findViewById(R.id.login_info).setVisibility(View.VISIBLE);
                                findViewById(R.id.register_form).setVisibility(View.GONE);
                            }

                        }
                    });
        }
    }


    public void registerConfirm(View v) {


        final String displayName = ((EditText) findViewById(R.id.register_username)).getText().toString();
        String email = ((EditText) findViewById(R.id.register_email)).getText().toString();
        String password = ((EditText) findViewById(R.id.register_password)).getText().toString();

        if (isNameValid(displayName) && isEmailValid(email) && isPasswordValid(password)) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener <AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task <AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                //  Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(displayName).build();
                                user.updateProfile(profileUpdates);
                                // updateUI(user);
                                loginSuccesfully(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("REGISTER FAIL", "createUserWithEmail:failure" + task.getException());
                                Toast.makeText(MainActivity.this, "Authentification failed.", Toast.LENGTH_LONG).show();
                                findViewById(R.id.login_info).setVisibility(View.VISIBLE);
                                findViewById(R.id.register_form).setVisibility(View.GONE);
                            }
                        }
                    });
        }
    }

    private boolean isEmailValid(String email) {

        boolean e = !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
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

    private void loginSuccesfully(FirebaseUser user) {

        Intent intent = new Intent(this, Todo.class);
        intent.putExtra("userId", user.getUid());
        intent.putExtra("displayName", user.getDisplayName());
        intent.putExtra("email", user.getEmail());
        startActivity(intent);
    }

}