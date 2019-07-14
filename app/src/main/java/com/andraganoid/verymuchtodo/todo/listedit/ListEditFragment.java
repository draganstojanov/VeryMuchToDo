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


public class ListEditFragment extends TodoBaseFragment implements ListEditClicker {

    private TodoList todoListItemNew;

    public ListEditFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        toDoViewModel.setTodoBars(toDoViewModel.currentToDoList.getTitle(), "");
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
        FragmentListEditBinding binding = DataBindingUtil.inflate(
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
        if (!todoListItemNew.getTitle().isEmpty()) {
            if (todoListItemNew.getTitle().length() < 32) {
                if (todoListItemNew.getDescription().length() < 100) {
                    if (toDoViewModel.currentToDoList.getTimestamp() != 0) {
                        toDoViewModel.deleteDocument.setValue(new Document(toDoViewModel.currentToDoList));
                    }

                    //TEST
//                    ArrayList<TodoList> tl = new ArrayList<>();
//                    if (toDoViewModel.todoList.getValue() != null) {
//                        tl.addAll(toDoViewModel.todoList.getValue());
//
//                        for (int i = 0; i < tl.size(); i++) {
//
//                            if (tl.get(i).getTitle().equals(toDoViewModel.currentToDoList.getTitle())) {
//                                tl.remove(i);
//                                break;
//                            }
//                        }
//
//                    }
//                    tl.add(todoListItemNew);
//                    toDoViewModel.todoList.setValue(tl);
                    //TEST


                    todoListItemNew.setTimestampAndCompleted();
                    toDoViewModel.addDocument.setValue(new Document(todoListItemNew));

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
