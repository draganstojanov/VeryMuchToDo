package com.andraganoid.verymuchtodo.J.todo.list;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.andraganoid.verymuchtodo.J.model.TodoList;
import com.andraganoid.verymuchtodo.J.todo.ToDoViewModel;
import com.andraganoid.verymuchtodo.R;
import com.andraganoid.verymuchtodo.databinding.ListRowBinding;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ListFragmentAdapter extends RecyclerView.Adapter<ListFragmentAdapter.ListHolder> {

    private ArrayList<TodoList> tlList = new ArrayList<>();
    private ListFragment clicker;
    private ToDoViewModel lastEdit;

    ListFragmentAdapter(ArrayList<TodoList> tlList, ToDoViewModel toDoViewModel, ListFragment clicker) {
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
