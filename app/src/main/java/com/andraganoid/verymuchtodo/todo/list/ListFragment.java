package com.andraganoid.verymuchtodo.todo.list;

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
import com.andraganoid.verymuchtodo.databinding.ListFragmentBinding;
import com.andraganoid.verymuchtodo.model.TodoList;
import com.andraganoid.verymuchtodo.todo.TodoBaseFragment;

import java.util.ArrayList;

import static com.andraganoid.verymuchtodo.todo.Todo.COLLECTION_TODOS;

public class ListFragment extends TodoBaseFragment implements ListClicker {

    ListFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.list_fragment,
                container,
                false);
        binding.setClicker(this);

        //  binding.setViewModel(todoViewModel);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        closeKeyboard();

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
                        TodoList tl = todoActivity.todoList.get(viewHolder.getAdapterPosition());
                        if (tl.isCompleted()) {
                            todoActivity.deleteDocument(COLLECTION_TODOS, tl.getTitle());
                        } else {
                            listsAdapter.notifyDataSetChanged();
                            Toast.makeText(todoActivity, "List is not completed!.", Toast.LENGTH_LONG).show();
                        }
                    }
                };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);

        itemTouchHelper.attachToRecyclerView(binding.listRecView);


        binding.listRecView.setLayoutManager(new LinearLayoutManager(toDo));
        final ListFragmentAdapter adapter = new ListFragmentAdapter(todoViewModel.todoList.getValue(), this);
        binding.listRecView.setAdapter(adapter);

        todoViewModel.todoList.observe(toDo, new Observer<ArrayList<TodoList>>() {
            @Override
            public void onChanged(ArrayList<TodoList> todoLists) {
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onFabClicked() {
        // toDo.navigateToFragment(new ListEditFragment());
        Toast.makeText(toDo, "FAB CLICKED", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClicked(TodoList todoList) {
        Toast.makeText(toDo, "ITEM CLICKED", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemLongClicked(TodoList todoList) {
        Toast.makeText(toDo, "ITEM LONG CLICKED", Toast.LENGTH_SHORT).show();
    }
}
