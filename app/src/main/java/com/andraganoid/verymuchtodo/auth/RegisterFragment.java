package com.andraganoid.verymuchtodo.auth;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.andraganoid.verymuchtodo.R;
import com.andraganoid.verymuchtodo.databinding.RegisterFragmentBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterFragment extends AuthBaseFragment {

    private RegisterFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.register_fragment,
                container,
                false);
        binding.setClicker(this);
        binding.setViewModel(mainViewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        main.leave = false;
        showKeyboard(binding.registerUsername);
    }

    public void onRegisterConfirm() {
        if (isNameValid(mainViewModel.registrationName)
                && isEmailValid(mainViewModel.registrationMail)
                && isPasswordValid(mainViewModel.registrationPass)) {
            closeKeyboard();
            main.showLoader();
            mainViewModel.mAuth.createUserWithEmailAndPassword(
                    mainViewModel.registrationMail,
                    mainViewModel.registrationPass)
                    .addOnCompleteListener(main, task -> {
                        if (task.isSuccessful()) {
                            final FirebaseUser user = mainViewModel.mAuth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(mainViewModel.registrationName).build();
                            if (user != null) {
                                user.updateProfile(profileUpdates).addOnSuccessListener(aVoid -> {
                                    main.setUser(user);
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        main.closeLoader();
                                        showSnackbar(getString(R.string.error) + " " + e.getMessage());
                                    }
                                });
                            } else {
                                main.closeLoader();
                                showSnackbar(getString(R.string.auth_failed) + "\n" + task.getException().getMessage());
                            }
                        }
                    });
        }
    }


    private boolean isEmailValid(String email) {
        boolean e = !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        if (!e) {
            showSnackbar(getString(R.string.invalid_mail));
        }
        return e;
    }

    private boolean isNameValid(String name) {
        boolean n = !TextUtils.isEmpty(name) && name.length() > 2;
        if (!n) {
            showSnackbar(getString(R.string.invalid_name));
        }
        return n;
    }

    private boolean isPasswordValid(String pass) {
        boolean p = !TextUtils.isEmpty(pass) && pass.length() > 5;
        if (!p) {
            showSnackbar(getString(R.string.invalid_pass));
        }
        return p;
    }


}