package com.andraganoid.verymuchtodo.todo.itemedit;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.andraganoid.verymuchtodo.R;
import com.andraganoid.verymuchtodo.databinding.FragmentItemEditBinding;
import com.andraganoid.verymuchtodo.model.Document;
import com.andraganoid.verymuchtodo.model.TodoItem;
import com.andraganoid.verymuchtodo.model.TodoList;
import com.andraganoid.verymuchtodo.todo.TodoBaseFragment;

import java.util.ArrayList;


public class ItemEditFragment extends TodoBaseFragment implements ItemEditClicker {

    private FragmentItemEditBinding binding;
    private String content;

    public ItemEditFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_item_edit,
                container,
                false
        );
        binding.setClicker(this);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        toDo.backTo = toDo.ITEM_FRAGMENT;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showKeyboard(binding.todoItemContent);
        content = toDoViewModel.currentToDoItem.getContent();
        toDoViewModel.setTodoBars(toDoViewModel.currentToDoList.getTitle(), toDoViewModel.currentToDoList.isEmergency());
        binding.setItemItem(toDoViewModel.currentToDoItem);
    }

    private boolean editCheck() {
        String content = toDoViewModel.currentToDoItem.getContent();
        if (content.isEmpty()) {
            return false;
        } else {
            ArrayList<TodoItem> til = toDoViewModel.currentToDoList.getTodoItemList();
            for (int i = 0; i < til.size(); i++) {
                if (til.get(i).getContent().equals(content)) {
                    til.remove(i);
                    break;
                }
            }
            toDoViewModel.currentToDoItem.setTimestamp(System.currentTimeMillis());
            til.add(toDoViewModel.currentToDoItem);
            TodoList tl = toDoViewModel.currentToDoList;
            tl.setTodoItemList(til);
            toDoViewModel.currentToDoList = tl;
            toDoViewModel.currentToDoList.setTimestampAndCompleted();
            toDoViewModel.addDocument(new Document(toDoViewModel.currentToDoList));
            return true;
        }
    }

    @Override
    public void onSave() {
        if (editCheck()) {
            toDo.navigateToFragment(toDo.ITEM_FRAGMENT);
        }
    }

    @Override
    public void onSaveAndNew() {
        if (editCheck()) {
            toDoViewModel.currentToDoItem = new TodoItem(toDoViewModel.mUser);
            binding.setItemItem(toDoViewModel.currentToDoItem);
        }
    }

    @Override
    public void onCancel() {
        toDoViewModel.currentToDoItem.setContent(content);
        toDo.navigateToFragment(toDo.ITEM_FRAGMENT);
    }
}