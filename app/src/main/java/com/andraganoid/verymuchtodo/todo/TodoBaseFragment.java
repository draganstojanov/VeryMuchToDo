package com.andraganoid.verymuchtodo.todo;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class TodoBaseFragment extends Fragment {

    protected ToDoViewModel todoViewModel;
    protected Todo toDo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toDo = (Todo) getActivity();
        todoViewModel = ViewModelProviders.of(toDo).get(ToDoViewModel.class);
    }

    public void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(flView.getWindowToken(), 0);
    }

}
