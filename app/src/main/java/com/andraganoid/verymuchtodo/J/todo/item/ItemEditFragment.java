package com.andraganoid.verymuchtodo.J.todo.item;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andraganoid.verymuchtodo.J.model.Document;
import com.andraganoid.verymuchtodo.J.model.TodoItem;
import com.andraganoid.verymuchtodo.J.model.TodoList;
import com.andraganoid.verymuchtodo.J.todo.TodoBaseFragment;
import com.andraganoid.verymuchtodo.R;
import com.andraganoid.verymuchtodo.databinding.FragmentItemEditBinding;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;


public class ItemEditFragment extends TodoBaseFragment {

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
        binding.setLifecycleOwner(this);
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
            toDoViewModel.currentToDoItem.setUser(toDoViewModel.mUser);
            til.add(toDoViewModel.currentToDoItem);
            TodoList tl = toDoViewModel.currentToDoList;
            tl.setTodoItemList(til);
            toDoViewModel.currentToDoList = tl;
            toDoViewModel.currentToDoList.updateList(toDoViewModel.mUser);
            toDoViewModel.addDocument(new Document(toDoViewModel.currentToDoList));
            return true;
        }
    }

    public void onSave() {
        if (editCheck()) {
            toDo.navigateToFragment(toDo.ITEM_FRAGMENT);
        }
    }

    public void onSaveAndNew() {
        if (editCheck()) {
            toDoViewModel.currentToDoItem = new TodoItem(toDoViewModel.mUser);
            binding.setItemItem(toDoViewModel.currentToDoItem);
        }
    }

    public void onCancel() {
        toDoViewModel.currentToDoItem.setContent(content);
        toDo.navigateToFragment(toDo.ITEM_FRAGMENT);
    }
}