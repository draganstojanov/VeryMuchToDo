package com.andraganoid.verymuchtodo.todo;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

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

    public void closeKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
