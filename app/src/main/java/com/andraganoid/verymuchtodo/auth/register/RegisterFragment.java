package com.andraganoid.verymuchtodo.auth.register;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.andraganoid.verymuchtodo.R;
import com.andraganoid.verymuchtodo.auth.AuthBaseFragment;
import com.andraganoid.verymuchtodo.databinding.RegisterFragmentBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterFragment extends AuthBaseFragment implements RegisterClicker {


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        RegisterFragmentBinding binding = DataBindingUtil.inflate(
                inflater,
                R.layout.register_fragment,
                container,
                false);
        binding.setClicker(this);
        binding.setViewModel(mainViewModel);

        return binding.getRoot();
    }

    @Override
    public void onRegisterConfirm() {

        if (isNameValid(mainViewModel.registrationName)
                && isEmailValid(mainViewModel.registrationMail)
                && isPasswordValid(mainViewModel.registrationPass)) {
            mainViewModel.mAuth.createUserWithEmailAndPassword(
                    mainViewModel.registrationMail,
                    mainViewModel.registrationPass)
                    .addOnCompleteListener(main, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                final FirebaseUser user = mainViewModel.mAuth.getCurrentUser();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(mainViewModel.registrationName).build();
                                if (user != null) {
                                    user.updateProfile(profileUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            main.setUser(user);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(main, getString(R.string.error) + " " + e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            } else {
                                Toast.makeText(main, getString(R.string.auth_failed)+"\n"+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }


    private boolean isEmailValid(String email) {
        boolean e = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        if (!e) {
            Toast.makeText(main, getString(R.string.invalid_mail), Toast.LENGTH_SHORT).show();
        }
        return e;

    }

    private boolean isNameValid(String name) {
        boolean n = !TextUtils.isEmpty(name) && name.length() > 2;
        if (!n) {
            Toast.makeText(main, getString(R.string.invalid_name), Toast.LENGTH_SHORT).show();
        }
        return n;
    }

    private boolean isPasswordValid(String pass) {
        boolean p = !TextUtils.isEmpty(pass) && pass.length() > 5;
        if (!p) {
            Toast.makeText(main, getString(R.string.invalid_pass), Toast.LENGTH_SHORT).show();
        }
        return p;
    }


}