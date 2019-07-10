package com.andraganoid.verymuchtodo.todo.itemedit;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.andraganoid.verymuchtodo.R;
import com.andraganoid.verymuchtodo.databinding.FragmentItemEditBinding;
import com.andraganoid.verymuchtodo.model.Document;
import com.andraganoid.verymuchtodo.model.TodoItem;
import com.andraganoid.verymuchtodo.todo.TodoBaseFragment;

import java.util.ArrayList;


public class ItemEditFragment extends TodoBaseFragment implements ItemEditClicker {

    private TodoItem todoItemNew;
    private FragmentItemEditBinding binding;

    public ItemEditFragment() {
    }


    void init(){
        this.todoItemNew = new TodoItem();
        this.todoItemNew.setContent(toDoViewModel.currentToDoItem.getContent());
        this.todoItemNew.setUser(toDoViewModel.currentToDoItem.getUser());
        this.todoItemNew.setTimestamp(toDoViewModel.currentToDoItem.getTimestamp());
        this.todoItemNew.setCompleted(toDoViewModel.currentToDoItem.isCompleted());
        Toast.makeText(toDo, "START", Toast.LENGTH_SHORT).show();
      }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      init();
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
        binding.setItemItem(todoItemNew);
        binding.setClicker(this);
        return binding.getRoot();
    }


    private boolean editCheck( ) {
        String content = todoItemNew.getContent();
        Toast.makeText(toDo, content, Toast.LENGTH_SHORT).show();
        if (content.isEmpty()) {
            return false;
        } else {
            ArrayList<TodoItem> til = toDoViewModel.currentToDoList.getTodoItemList();
            for (int i = 0; i < til.size(); i++) {
                if (til.get(i).getContent().equals(todoItemNew.getContent())) {
                    til.remove(i);
                    break;
                }
            }
            til.add(todoItemNew);
            toDoViewModel.currentToDoList.setTodoItemList(til);
            toDoViewModel.addDocument.setValue(new Document(toDoViewModel.currentToDoList));

           return true;
        }


    }


    @Override
    public void onSave( ) {
        if (editCheck()) {
            toDo.navigateToFragment(toDo.ITEM_FRAGMENT);
        }
    }

    @Override
    public void onSaveAndNew( ) {
        if (editCheck()) { Toast.makeText(toDo, "OK1", Toast.LENGTH_SHORT).show();
            toDoViewModel.currentToDoItem = new TodoItem(toDoViewModel.user.get()); Toast.makeText(toDo, "OK2", Toast.LENGTH_SHORT).show();
           // toDo.navigateToFragment(toDo.ITEM_EDIT_FRAGMENT);
   init();

        }
    }

    @Override
    public void onCancel() {
        toDo.navigateToFragment(toDo.ITEM_FRAGMENT);
    }
}
