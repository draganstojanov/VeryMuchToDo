package com.andraganoid.verymuchtodo.todo.list;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.andraganoid.verymuchtodo.R;
import com.andraganoid.verymuchtodo.databinding.ListRowBinding;
import com.andraganoid.verymuchtodo.model.TodoList;
import com.andraganoid.verymuchtodo.todo.ToDoViewModel;

import java.util.ArrayList;

public class ListFragmentAdapter extends RecyclerView.Adapter<ListFragmentAdapter.ListHolder> {
    private ArrayList<TodoList> tlList = new ArrayList<>();
    private ListClicker clicker;
    private ToDoViewModel lastEdit;

    ListFragmentAdapter(ArrayList<TodoList> tlList, ToDoViewModel toDoViewModel, ListClicker clicker) {
        this.lastEdit = toDoViewModel;
        this.clicker = clicker;
        setList(tlList);
    }

    public void setList(ArrayList<TodoList> tlList) {
        this.tlList = tlList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListFragmentAdapter.ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListRowBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.list_row,
                parent,
                false);
        return new ListHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ListFragmentAdapter.ListHolder holder, int position) {
        holder.bind(tlList.get(position));
    }

    @Override
    public int getItemCount() {
        return tlList.size();
    }

    class ListHolder extends RecyclerView.ViewHolder {
        ListRowBinding binding;

        ListHolder(@NonNull ListRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.setClicker(clicker);
        }

        void bind(TodoList item) {
            binding.setTodoListItem(item);
            binding.setLastEdit(lastEdit);
            binding.executePendingBindings();
        }

    }

}
