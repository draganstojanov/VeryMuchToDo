package com.andraganoid.verymuchtodo.auth.login;

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
import com.andraganoid.verymuchtodo.databinding.LoginFragmentBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;


public class LoginFragment extends AuthBaseFragment implements LoginClicker {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        LoginFragmentBinding binding = DataBindingUtil.inflate(
                inflater,
                R.layout.login_fragment,
                container,
                false);
        binding.setClicker(this);
        binding.setViewModel(mainViewModel);

        return binding.getRoot();
    }


    @Override
    public void onLoginConfirm() {
        mainViewModel.userMail="dragan.stojanov@gmail.com";
        mainViewModel.userPass="dushek";
        if (!TextUtils.isEmpty(mainViewModel.userMail)
                && !TextUtils.isEmpty(mainViewModel.userPass)) {
            mainViewModel.mAuth.signInWithEmailAndPassword(
                    mainViewModel.userMail,
                    mainViewModel.userPass)
                    .addOnCompleteListener(main, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                main.setUser(mainViewModel.mAuth.getCurrentUser());
                            } else {
                                Toast.makeText(main, getString(R.string.auth_failed) + "\n" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    @Override
    public void onNotRegistred() {

    }
}


