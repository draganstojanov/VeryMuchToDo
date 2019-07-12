package com.andraganoid.verymuchtodo.todo.item;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andraganoid.verymuchtodo.R;
import com.andraganoid.verymuchtodo.databinding.FragmentItemBinding;
import com.andraganoid.verymuchtodo.model.Document;
import com.andraganoid.verymuchtodo.model.TodoItem;
import com.andraganoid.verymuchtodo.model.TodoList;
import com.andraganoid.verymuchtodo.todo.TodoBaseFragment;
import com.andraganoid.verymuchtodo.todo.itemedit.ItemEditFragment;

import java.util.ArrayList;


public class ItemFragment extends TodoBaseFragment implements ItemClicker {

    private FragmentItemBinding binding;
    private ItemFragmentAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_item,
                container,
                false);
        binding.setClicker(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // closeKeyboard(binding.getRoot());

        // Toast.makeText(toDo, String.valueOf(toDoViewModel.currentToDoList.getTodoItemList().size()), Toast.LENGTH_SHORT).show();
        binding.itemRecView.setLayoutManager(new LinearLayoutManager(toDo));
        adapter = new ItemFragmentAdapter(toDoViewModel.currentToDoList.getTodoItemList(), this);
        binding.itemRecView.setAdapter(adapter);

        toDoViewModel.getTodoList().observe(toDo, new Observer<ArrayList<TodoList>>() {
            @Override
            public void onChanged(ArrayList<TodoList> todoLists) {

                // DA LI JE NULL

              //  toDoViewModel.currentToDoList.getTitle();


                adapter.notifyDataSetChanged();
            }
        });


        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                TodoItem todoItem = toDoViewModel.currentToDoList.getTodoItemList().get(viewHolder.getAdapterPosition());
                switch (swipeDir) {

                    case ItemTouchHelper.LEFT:
                        if (!todoItem.isCompleted()) {
                            toDoViewModel.currentToDoItem = todoItem;
                            toDo.navigateToFragment(new ItemEditFragment());

                        } else {
                            adapter.notifyDataSetChanged();
                            Toast.makeText(toDo, getString(R.string.task_completed), Toast.LENGTH_LONG).show();
                        }

                        break;

                    case ItemTouchHelper.RIGHT:

                        if (todoItem.isCompleted()) {
                            toDoViewModel.currentToDoList.getTodoItemList().remove(viewHolder.getAdapterPosition());
                            //  todoActivity.saveList(todoActivity.currengtList);
                            toDoViewModel.addDocument.setValue(new Document(toDoViewModel.currentToDoList));

                        } else {
                            adapter.notifyDataSetChanged();
                            Toast.makeText(toDo, getString(R.string.task_not_completed), Toast.LENGTH_LONG).show();
                        }
                        break;
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(binding.itemRecView);


    }


    @Override
    public void onFabClicked() {
        toDoViewModel.currentToDoItem = new TodoItem(toDoViewModel.user.get());
        toDo.navigateToFragment(toDo.ITEM_EDIT_FRAGMENT);
    }


    public void onItemLongClicked(View view, TodoItem todoItem) {
        //  toDo.navigateToFragment(toDo.ITEM_EDIT_FRAGMENT);
    }


}