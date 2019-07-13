package com.andraganoid.verymuchtodo.auth;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.andraganoid.verymuchtodo.MainActivity;
import com.andraganoid.verymuchtodo.MainViewModel;


public class AuthBaseFragment extends Fragment {

    protected MainViewModel mainViewModel;
    protected MainActivity main;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main = (MainActivity) getActivity();
        mainViewModel = ViewModelProviders.of(main).get(MainViewModel.class);
    }

}
