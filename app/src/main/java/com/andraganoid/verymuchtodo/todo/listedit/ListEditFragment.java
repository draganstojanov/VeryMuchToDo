package com.andraganoid.verymuchtodo.todo.listedit;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.andraganoid.verymuchtodo.R;
import com.andraganoid.verymuchtodo.databinding.FragmentListEditBinding;
import com.andraganoid.verymuchtodo.model.Document;
import com.andraganoid.verymuchtodo.model.TodoList;
import com.andraganoid.verymuchtodo.todo.TodoBaseFragment;
import com.andraganoid.verymuchtodo.todo.list.ListFragment;

import java.util.ArrayList;


public class ListEditFragment extends TodoBaseFragment implements ListEditClicker {

    private TodoList todoListItemNew;
    private FragmentListEditBinding binding;

    public ListEditFragment(TodoList todoListItem) {
        this.todoListItemNew = new TodoList();
        this.todoListItemNew.setTitle(todoListItem.getTitle());
        this.todoListItemNew.setDescription(todoListItem.getDescription());
        this.todoListItemNew.setUser(todoListItem.getUser());
        this.todoListItemNew.setCompleted(todoListItem.isCompleted());
        this.todoListItemNew.setEmergency(todoListItem.isEmergency());
        this.todoListItemNew.setTimestamp(todoListItem.getTimestamp());
        this.todoListItemNew.setTodoItemList(todoListItem.getTodoItemList());
    }

    public ListEditFragment() {
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
    public void onCreateClicked(TodoList todoListItem) {
        String title = todoListItem.getTitle();
        String desc = todoListItem.getDescription();


        if (!title.isEmpty()) {
            if (title.length() < 32) {
                if (desc.length() < 100) {
                    toDoViewModel.addDocument.setValue(new Document(todoListItemNew));


                    //TEST
                    ArrayList<TodoList> tl = new ArrayList<>();


                    tl = toDoViewModel.todoList.getValue();

                    tl.add(todoListItem);
                    toDoViewModel.todoList.setValue(tl);
                    //TEST

                    toDo.navigateToFragment(new ListFragment());

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
        toDo.navigateToFragment(new ListFragment());
    }
}
