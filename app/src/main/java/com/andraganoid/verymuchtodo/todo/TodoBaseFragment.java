package com.andraganoid.verymuchtodo.todo;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TodoBaseFragment extends Fragment {

    public ToDoViewModel toDoViewModel;
    public Todo toDo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toDo = (Todo) getActivity();
        //toDoViewModel = ViewModelProviders.of(toDo).get(ToDoViewModel.class);
        toDoViewModel = toDo.toDoViewModel;
    }

    @Override
    public void onResume() {
        super.onResume();
        toDoViewModel.getCurrentLocation();
    }

    public void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(toDo.binding.getRoot().getWindowToken(), 0);
    }

}
