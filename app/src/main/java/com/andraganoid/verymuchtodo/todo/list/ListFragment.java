package com.andraganoid.verymuchtodo.todo.list;

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
import com.andraganoid.verymuchtodo.databinding.FragmentListBinding;
import com.andraganoid.verymuchtodo.model.Document;
import com.andraganoid.verymuchtodo.model.TodoList;
import com.andraganoid.verymuchtodo.todo.TodoBaseFragment;


public class ListFragment extends TodoBaseFragment implements ListClicker {

    private FragmentListBinding binding;
    private ListFragmentAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toDoViewModel.setTodoBars(getString(R.string.todo_lists), "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_list,
                container,
                false);
        binding.setClicker(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.listRecView.setLayoutManager(new LinearLayoutManager(toDo));
        adapter = new ListFragmentAdapter(toDoViewModel.getTodoList().getValue(), toDoViewModel, this);
        binding.listRecView.setAdapter(adapter);


        toDoViewModel.getTodoList().observe(getViewLifecycleOwner(), todoLists -> {
            adapter.setList(todoLists);
            toDoViewModel.setAlerts("list", false);
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
                        TodoList todoList = toDoViewModel.getTodoList().getValue().get(viewHolder.getAdapterPosition());
                        switch (swipeDir) {
                            case ItemTouchHelper.RIGHT:
                                if (todoList.isCompleted()) {
                                    toDoViewModel.deleteDocument(new Document(todoList));
                                } else {
                                    showSnackbar(getString(R.string.list_not_completed));
                                    adapter.notifyDataSetChanged();
                                }
                                break;
                            case ItemTouchHelper.LEFT:
                                toDoViewModel.currentToDoList = todoList;
                                toDo.navigateToFragment(toDo.LIST_EDIT_FRAGMENT);
                                break;
                        }
                    }
                };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(binding.listRecView);
    }

    @Override
    public void onFabClicked() {
        toDoViewModel.currentToDoList = new TodoList(toDoViewModel.mUser);
        toDo.navigateToFragment(toDo.LIST_EDIT_FRAGMENT);
    }

    @Override
    public void onItemClicked(TodoList todoList) {
        toDoViewModel.currentToDoList = todoList;
        toDo.navigateToFragment(toDo.ITEM_FRAGMENT);
    }
}
