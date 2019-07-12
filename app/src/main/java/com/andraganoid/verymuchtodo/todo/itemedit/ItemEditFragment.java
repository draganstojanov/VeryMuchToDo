package com.andraganoid.verymuchtodo.todo.itemedit;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

    public ItemEditFragment() {
    }


    private void init() {

//        TodoItem todoItem = new TodoItem();
//        toDoViewModel.todoItemNew.set((TodoItem) toDoViewModel.clone(
//                toDoViewModel.currentToDoItem.get(),
//                todoItem));
//       Toast.makeText(toDo, toDoViewModel.todoItemNew.get().getContent(), Toast.LENGTH_SHORT).show();
//        if (binding != null) {
//            binding.setItemItem(toDoViewModel.todoItemNew.get());
//        }
//        this.todoItemNew = new TodoItem();
//        this.todoItemNew.setContent(toDoViewModel.currentToDoItem.get().getContent());
//        this.todoItemNew.setUser(toDoViewModel.currentToDoItem.get().getUser());
//        this.todoItemNew.setTimestamp(toDoViewModel.currentToDoItem.get().getTimestamp());
//        this.todoItemNew.setCompleted(toDoViewModel.currentToDoItem.get().isCompleted());
//        Toast.makeText(toDo, "START", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // toDoViewModel = ViewModelProviders.of(this).get(MapViewModel.class);
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_item_edit,
                container,
                false
        );

        binding.setClicker(this);
      //  binding.executePendingBindings();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setItemItem(toDoViewModel.currentToDoItem.get());
    }

    private boolean editCheck() {


        String content = toDoViewModel.currentToDoItem.get().getContent();
        Toast.makeText(toDo, content, Toast.LENGTH_SHORT).show();
        if (content.isEmpty()) {
            return false;
        } else {

            ArrayList<TodoItem> til = toDoViewModel.currentToDoList.get().getTodoItemList();
            for (int i = 0; i < til.size(); i++) {

                if (til.get(i).getContent().equals(content)) {
                    til.remove(i);
                    break;
                }
            }
            til.add(toDoViewModel.currentToDoItem.get());
            TodoList tl = toDoViewModel.currentToDoList.get();
            tl.setTodoItemList(til);
            toDoViewModel.currentToDoList.set(tl);
            toDoViewModel.addDocument.setValue(new Document(toDoViewModel.currentToDoList.get()));
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
            toDoViewModel.currentToDoItem.set(new TodoItem(toDoViewModel.user.get()));
            binding.setItemItem(toDoViewModel.currentToDoItem.get());
            //  toDoViewModel.currentToDoItem.notifyChange();
           // init();
          //  binding.invalidateAll();

        }
    }

    @Override
    public void onCancel() {
        //  TodoItem ti=new TodoItem(toDoViewModel.user.get()) ;
        // ti.setContent(String.valueOf(System.currentTimeMillis()));
        // toDoViewModel.todoItemNew.set(ti);
//binding.invalidateAll();


        toDo.navigateToFragment(toDo.ITEM_FRAGMENT);
    }
}
