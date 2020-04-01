package com.andraganoid.verymuchtodo.J.auth;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.andraganoid.verymuchtodo.MainActivity;
import com.andraganoid.verymuchtodo.MainViewModel;
import com.andraganoid.verymuchtodo.R;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class AuthBaseFragment extends Fragment {

    protected MainViewModel mainViewModel;
    protected MainActivity main;
    private InputMethodManager imm;
    private View mainView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main = (MainActivity) getActivity();
        mainView = main.findViewById(R.id.mainView);
        imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
        mainViewModel = ViewModelProviders.of(main).get(MainViewModel.class);
    }

    protected void showKeyboard(View view) {
        view.requestFocus();
        imm.showSoftInput(mainView, InputMethodManager.SHOW_FORCED);
    }

   protected void closeKeyboard() {
        imm.hideSoftInputFromWindow(mainView.getWindowToken(), 0);
    }

    protected void showSnackbar(String txt) {
        closeKeyboard();
        Snackbar.make(mainView, txt, Snackbar.LENGTH_LONG).show();
    }
}
