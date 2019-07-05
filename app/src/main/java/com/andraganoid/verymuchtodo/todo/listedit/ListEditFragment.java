package com.andraganoid.verymuchtodo.todo.listedit;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.andraganoid.verymuchtodo.R;
import com.andraganoid.verymuchtodo.databinding.FragmentListEditBinding;
import com.andraganoid.verymuchtodo.model.TodoList;
import com.andraganoid.verymuchtodo.todo.TodoBaseFragment;


public class ListEditFragment extends TodoBaseFragment implements ListEditClicker {

    private TodoList todoList;
    private FragmentListEditBinding binding;

    public ListEditFragment(TodoList todoList) {
        this.todoList = todoList;
    }

    public ListEditFragment() {
        this.todoList = new TodoList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_list_edit,
                container,
                false
        );
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onCreateClicked(TodoList todoList) {
        //check is emptyand send
    }
}
