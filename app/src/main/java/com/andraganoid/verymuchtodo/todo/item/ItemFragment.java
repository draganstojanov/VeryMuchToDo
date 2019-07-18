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

import java.util.ArrayList;


public class ItemFragment extends TodoBaseFragment implements ItemClicker {

    private FragmentItemBinding binding;
    private ItemFragmentAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toDoViewModel.setTodoBars(toDoViewModel.currentToDoList.getTitle(), "");
        toDoViewModel.setAlerts("list", false);


    }

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


        binding.itemRecView.setLayoutManager(new LinearLayoutManager(toDo));
        adapter = new ItemFragmentAdapter(toDoViewModel.currentToDoList.getTodoItemList(), toDoViewModel, this);
        binding.itemRecView.setAdapter(adapter);

        toDoViewModel.getTodoList().observe(getViewLifecycleOwner(), new Observer<ArrayList<TodoList>>() {
            @Override
            public void onChanged(ArrayList<TodoList> todoLists) {
                boolean isInList = false;
                int index = -1;
                for (int i = 0; i < todoLists.size(); i++) {
                    if (todoLists.get(i).getTitle().equals(toDoViewModel.currentToDoList.getTitle())) {
                        isInList = true;
                        index = i;
                        break;
                    }
                }
                if (isInList) {   adapter.notifyDataSetChanged();
                   // adapter.setList(todoLists.get(index).getTodoItemList());
                } else {
                    Toast.makeText(toDo, R.string.item_list_deleted, Toast.LENGTH_SHORT).show();
                    toDo.navigateToFragment(toDo.LIST_FRAGMENT);
                }
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
                            toDo.navigateToFragment(toDo.ITEM_EDIT_FRAGMENT);

                        } else {
                            adapter.notifyDataSetChanged();
                            Toast.makeText(toDo, getString(R.string.task_completed), Toast.LENGTH_LONG).show();
                        }

                        break;

                    case ItemTouchHelper.RIGHT:

                        if (todoItem.isCompleted()) {
                            toDoViewModel.currentToDoList.getTodoItemList().remove(viewHolder.getAdapterPosition());
                            //  todoActivity.saveList(todoActivity.currengtList);
//                            toDoViewModel.deleteDocument.setValue(new Document(toDoViewModel.currentToDoList));
                            toDoViewModel.fbRepo.deleteDocument(new Document(toDoViewModel.currentToDoList));

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
        toDoViewModel.currentToDoItem = new TodoItem(toDoViewModel.mUser);
        toDo.navigateToFragment(toDo.ITEM_EDIT_FRAGMENT);
    }

    @Override
    public boolean onItemLongClicked(View view, TodoItem todoItem) {
        todoItem.setCompleted(!todoItem.isCompleted());

        toDoViewModel.currentToDoList.setTimestampAndCompleted();
        toDoViewModel.addDocument(new Document(toDoViewModel.currentToDoList));


      // binding.invalidateAll();
        return true;
    }


}

