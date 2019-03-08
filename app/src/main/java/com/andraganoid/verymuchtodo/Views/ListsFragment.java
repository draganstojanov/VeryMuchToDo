package com.andraganoid.verymuchtodo.Views;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.andraganoid.verymuchtodo.R;
import com.andraganoid.verymuchtodo.Todo;


public class ListsFragment extends Fragment {

    Todo todoActivity;
    RecyclerView listsRecView;
    ListsAdapter listsAdapter;
    RecyclerView.LayoutManager listsLayMan;

    public ListsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View flView = inflater.inflate(R.layout.fragment_lists, container, false);

        todoActivity = (Todo) getActivity();
        todoActivity.setTitle("Todo lists","");
        listsRecView = flView.findViewById(R.id.lists_rec_view);
        listsAdapter = new ListsAdapter(todoActivity.todoList);
        listsLayMan = new LinearLayoutManager(getContext());
        listsRecView.setLayoutManager(listsLayMan);
        listsRecView.setAdapter(listsAdapter);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {


            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                todoActivity.deleteDocument(Todo.COLLECTION_TODOS, todoActivity.todoList.get(viewHolder.getAdapterPosition()).getName());
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);

        itemTouchHelper.attachToRecyclerView(listsRecView);
        return flView;
    }

    public void refreshLists() {
        listsAdapter.notifyDataSetChanged();
    }



}
