package com.andraganoid.verymuchtodo.todo.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andraganoid.verymuchtodo.R;
import com.andraganoid.verymuchtodo.databinding.ListFragmentBinding;
import com.andraganoid.verymuchtodo.model.Document;
import com.andraganoid.verymuchtodo.model.TodoList;
import com.andraganoid.verymuchtodo.todo.TodoBaseFragment;
import com.andraganoid.verymuchtodo.todo.listedit.ListEditFragment;

import java.util.ArrayList;


public class ListFragment extends TodoBaseFragment implements ListClicker {

    private ListFragmentBinding binding;
    private ListFragmentAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.list_fragment,
                container,
                false);
        binding.setClicker(this);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       // closeKeyboard(binding.getRoot());

        binding.listRecView.setLayoutManager(new LinearLayoutManager(toDo));
        adapter = new ListFragmentAdapter(toDoViewModel.getTodoList().getValue(), this);
        binding.listRecView.setAdapter(adapter);

        toDoViewModel.getTodoList().observe( toDo, new Observer<ArrayList<TodoList>>() {
            @Override
            public void onChanged(ArrayList<TodoList> todoLists) {
                adapter.notifyDataSetChanged();
            }
        });

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(
                        0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                        TodoList todoList = toDoViewModel.todoList.getValue().get(viewHolder.getAdapterPosition());

                        switch (swipeDir) {
                            case ItemTouchHelper.RIGHT:
                                if (todoList.isCompleted()) {
                                    toDoViewModel.deleteDocument.setValue(new Document(todoList));
                                } else {
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(toDo, getString(R.string.list_not_completed), Toast.LENGTH_LONG).show();
                                }
                            case ItemTouchHelper.LEFT:
                                toDo.navigateToFragment(new ListEditFragment(todoList));
                        }
                    }
                };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(binding.listRecView);
    }

    @Override
    public void onFabClicked() {
        toDo.navigateToFragment(new ListEditFragment(new TodoList(toDoViewModel.user.get())));
    }

    @Override
    public void onItemClicked(TodoList todoList) {
        Toast.makeText(toDo, "ITEM CLICKED", Toast.LENGTH_SHORT).show();
    }

//    @Override
//    public boolean onItemLongClicked(View view,TodoList todoList) {
//        Toast.makeText(toDo, "ITEM LONG CLICKED", Toast.LENGTH_SHORT).show();
//        return true;
//    }
}
