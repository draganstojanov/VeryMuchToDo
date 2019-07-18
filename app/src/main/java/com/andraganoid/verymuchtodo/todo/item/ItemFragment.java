package com.andraganoid.verymuchtodo.todo.item;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andraganoid.verymuchtodo.R;
import com.andraganoid.verymuchtodo.databinding.FragmentItemBinding;
import com.andraganoid.verymuchtodo.model.Document;
import com.andraganoid.verymuchtodo.model.TodoItem;
import com.andraganoid.verymuchtodo.todo.TodoBaseFragment;


public class ItemFragment extends TodoBaseFragment implements ItemClicker {

    private FragmentItemBinding binding;
    private ItemFragmentAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toDoViewModel.setTodoBars(toDoViewModel.currentToDoList.getTitle(),toDoViewModel.currentToDoList.isEmergency());
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
    public void onResume() {
        super.onResume();
        toDo.backTo = toDo.LIST_FRAGMENT;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.itemRecView.setLayoutManager(new LinearLayoutManager(toDo));
        adapter = new ItemFragmentAdapter(toDoViewModel.currentToDoList.getTodoItemList(), toDoViewModel, this);
        binding.itemRecView.setAdapter(adapter);
        toDoViewModel.getTodoList().observe(getViewLifecycleOwner(), todoLists -> {
            boolean isInList = false;
            for (int i = 0; i < todoLists.size(); i++) {
                if (todoLists.get(i).getTitle().equals(toDoViewModel.currentToDoList.getTitle())) {
                    isInList = true;
                    break;
                }
            }
            if (isInList) {
                adapter.notifyDataSetChanged();
            } else {
                showSnackbar(getString(R.string.item_list_deleted));
                toDo.navigateToFragment(toDo.LIST_FRAGMENT);
            }
        });

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
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
                                    showSnackbar(getString(R.string.task_completed));
                                }
                                break;
                            case ItemTouchHelper.RIGHT:
                                if (todoItem.isCompleted()) {
                                    toDoViewModel.currentToDoList.getTodoItemList().remove(viewHolder.getAdapterPosition());
                                    toDoViewModel.fbRepo.deleteDocument(new Document(toDoViewModel.currentToDoList));
                                } else {
                                    adapter.notifyDataSetChanged();
                                    showSnackbar(getString(R.string.task_not_completed));
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
        return true;
    }
}

