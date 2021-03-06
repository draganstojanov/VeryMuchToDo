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
import com.andraganoid.verymuchtodo.databinding.LoginFragmentBinding;


public class LoginFragment extends AuthBaseFragment {

    private LoginFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.login_fragment,
                container,
                false);
        binding.setClicker(this);
        binding.setViewModel(mainViewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        main.leave = true;
        showKeyboard(binding.signInEmail);
    }

    public void onLoginConfirm() {
        if (!TextUtils.isEmpty(mainViewModel.userMail)
                && !TextUtils.isEmpty(mainViewModel.userPass)) {
            closeKeyboard();
            main.showLoader();
            mainViewModel.mAuth.signInWithEmailAndPassword(
                    mainViewModel.userMail,
                    mainViewModel.userPass)
                    .addOnCompleteListener(main, task -> {
                        if (task.isSuccessful()) {
                            main.setUser(mainViewModel.mAuth.getCurrentUser());
                        } else {
                            main.closeLoader();
                            showSnackbar(getString(R.string.auth_failed) + "\n" + task.getException().getMessage());
                        }
                    });
        }
    }

    public void onNotRegistered() {
        main.navigateToFragment(new RegisterFragment());
    }
}


