package com.andraganoid.verymuchtodo.todo.list;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.andraganoid.verymuchtodo.R;
import com.andraganoid.verymuchtodo.databinding.FragmentListEditBinding;
import com.andraganoid.verymuchtodo.model.Document;
import com.andraganoid.verymuchtodo.model.TodoList;
import com.andraganoid.verymuchtodo.todo.TodoBaseFragment;


public class ListEditFragment extends TodoBaseFragment {

    private FragmentListEditBinding binding;
    private TodoList todoListItemNew;

    public ListEditFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.todoListItemNew = new TodoList();
        this.todoListItemNew = (TodoList) toDoViewModel.clone(
                toDoViewModel.currentToDoList,
                todoListItemNew);
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
        binding.setTodoListItem(todoListItemNew);
        binding.setClicker(this);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        toDo.backTo = toDo.LIST_FRAGMENT;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showKeyboard(binding.newTodoListTitle);
        toDoViewModel.setTodoBars(toDoViewModel.currentToDoList.getTitle(), toDoViewModel.currentToDoList.isEmergency());
    }

    public void onCreateClicked() {
        if (!todoListItemNew.getTitle().isEmpty()) {
            if (todoListItemNew.getTitle().length() < 32) {
                if (todoListItemNew.getDescription().length() < 100) {
                    todoListItemNew.updateList(toDoViewModel.mUser);
                    if (todoListItemNew.getId().equals("new")) {
                        todoListItemNew.setId();
                        toDoViewModel.addDocument(new Document(todoListItemNew));
                    } else {
                        toDoViewModel.updateDocument(new Document(todoListItemNew));
                    }
                    toDo.navigateToFragment(toDo.LIST_FRAGMENT);
                } else {
                    showSnackbar(getString(R.string.desc_too_long));
                }
            } else {
                showSnackbar(getString(R.string.title_to_long));
            }
        }
    }

    public void onCancelClicked() {
        toDo.navigateToFragment(toDo.LIST_FRAGMENT);
    }
}
