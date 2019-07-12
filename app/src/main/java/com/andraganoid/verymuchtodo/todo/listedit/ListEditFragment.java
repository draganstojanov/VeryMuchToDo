package com.andraganoid.verymuchtodo.todo.listedit;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.andraganoid.verymuchtodo.R;
import com.andraganoid.verymuchtodo.databinding.FragmentListEditBinding;
import com.andraganoid.verymuchtodo.model.Document;
import com.andraganoid.verymuchtodo.model.TodoList;
import com.andraganoid.verymuchtodo.todo.TodoBaseFragment;
import com.andraganoid.verymuchtodo.todo.listedit.ListEditClicker;

import java.util.ArrayList;


public class ListEditFragment extends TodoBaseFragment implements ListEditClicker {

    private TodoList todoListItemNew;
    private FragmentListEditBinding binding;

    public ListEditFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.todoListItemNew = new TodoList();
        this.todoListItemNew = (TodoList) toDoViewModel.clone(
                toDoViewModel.currentToDoList.get(),
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
    public void onCreateClicked() {
        //  String title = todoListItemNew.getTitle();
        //  String desc = todoListItemNew.getDescription();

        if (!todoListItemNew.getTitle().isEmpty()) {
            if (todoListItemNew.getTitle().length() < 32) {
                if (todoListItemNew.getDescription().length() < 100) {
                    todoListItemNew.setTimestampAndCompleted();
                    toDoViewModel.addDocument.setValue(new Document(todoListItemNew));

                    //TEST
                    ArrayList<TodoList> tl = new ArrayList<>();
                    if (toDoViewModel.todoList.getValue() != null) {
                        tl.addAll(toDoViewModel.todoList.getValue());
                    }
                    tl.add(todoListItemNew);
                    toDoViewModel.todoList.setValue(tl);
                    //TEST

                    toDo.navigateToFragment(toDo.LIST_FRAGMENT);
                } else {
                    Toast.makeText(toDo, R.string.desc_too_long, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(toDo, R.string.title_to_long, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onCancelClicked() {
        toDo.navigateToFragment(toDo.LIST_FRAGMENT);
    }
}
