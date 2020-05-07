package com.andraganoid.verymuchtodo.todo;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class TodoBaseFragment extends Fragment {

    public ToDoViewModel toDoViewModel;
    public Todo toDo;
    private InputMethodManager imm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toDo = (Todo) getActivity();
       // toDoViewModel = ViewModelProvider(toDo).get(ToDoViewModel.class);
        imm = (InputMethodManager) toDo.getSystemService(INPUT_METHOD_SERVICE);
    }

    @Override
    public void onResume() {
        super.onResume();
        toDo.backTo = null;
        toDoViewModel.getCurrentLocation();
    }

    protected void showKeyboard(View view) {
        view.requestFocus();
        imm.showSoftInput(toDo.binding.getRoot(), InputMethodManager.SHOW_FORCED);
    }

    protected void closeKeyboard() {
        imm.hideSoftInputFromWindow(toDo.binding.getRoot().getWindowToken(), 0);
    }

    protected void showSnackbar(String txt) {
        closeKeyboard();
        Snackbar.make(toDo.binding.getRoot(), txt, Snackbar.LENGTH_LONG).show();
    }
}
