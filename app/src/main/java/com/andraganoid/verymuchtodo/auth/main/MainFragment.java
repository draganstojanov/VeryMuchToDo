package com.andraganoid.verymuchtodo.auth.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.andraganoid.verymuchtodo.MainActivity;
import com.andraganoid.verymuchtodo.R;
import com.andraganoid.verymuchtodo.MainViewModel;
import com.andraganoid.verymuchtodo.databinding.MainFragmentBinding;

import static com.andraganoid.verymuchtodo.MainActivity.LOGIN_FRAGMENT;
import static com.andraganoid.verymuchtodo.MainActivity.REGISTER_FRAGMENT;

public class MainFragment extends Fragment implements MainClicker {

    protected MainViewModel mainViewModel;
    protected MainActivity main;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        main = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        MainFragmentBinding binding = DataBindingUtil.inflate(
                inflater,
                R.layout.main_fragment,
                container,
                false);
        binding.setClicker(this);
        binding.setViewModel(mainViewModel);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mainViewModel.mAuth == null) {
            main.loginSuccesfully(mainViewModel.mAuth.getCurrentUser());
        }
    }

    @Override
    public void onRegisterClick() {
        main.navigateToFragment(REGISTER_FRAGMENT);
    }

    @Override
    public void onLoginClick() {
        main.navigateToFragment(LOGIN_FRAGMENT);
    }
}
