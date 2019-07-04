package com.andraganoid.verymuchtodo.todo;

import android.os.Bundle;

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

}
