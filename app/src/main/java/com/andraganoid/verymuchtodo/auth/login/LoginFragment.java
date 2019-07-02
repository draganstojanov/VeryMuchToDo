package com.andraganoid.verymuchtodo.auth.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.andraganoid.verymuchtodo.R;
import com.andraganoid.verymuchtodo.databinding.LoginFragmentBinding;
import com.andraganoid.verymuchtodo.auth.main.MainFragment;

public class LoginFragment extends MainFragment implements LoginClicker {

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
        //  binding.setViewModel(toDoViewModel);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        if (toDoViewModel.mAuth == null) {
//            main.navigateToFragment(LIST_FRAGMENT);
//        }
    }

    @Override
    public void onLoginConfirm() {
        Toast.makeText(main, "LOGIN", Toast.LENGTH_SHORT).show();
    }
}
