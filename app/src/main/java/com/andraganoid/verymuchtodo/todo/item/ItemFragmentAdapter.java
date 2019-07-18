package com.andraganoid.verymuchtodo.todo.item;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.andraganoid.verymuchtodo.R;
import com.andraganoid.verymuchtodo.databinding.ItemRowBinding;
import com.andraganoid.verymuchtodo.model.TodoItem;
import com.andraganoid.verymuchtodo.todo.ToDoViewModel;

import java.util.ArrayList;

public class ItemFragmentAdapter extends RecyclerView.Adapter<ItemFragmentAdapter.ItemHolder> {
    private ArrayList<TodoItem> iList = new ArrayList<>();
    private ItemClicker clicker;
    private ToDoViewModel lastEdit;

    public ItemFragmentAdapter(ArrayList<TodoItem> iList, ToDoViewModel toDoViewModel, ItemClicker clicker) {
        this.lastEdit = toDoViewModel;
        this.clicker = clicker;
        setList(iList);
    }

    public void setList(ArrayList<TodoItem> iList) {
        this.iList = iList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRowBinding bindng = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_row,
                parent,
                false);
        return new ItemHolder(bindng);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemFragmentAdapter.ItemHolder holder, int position) {
        holder.bind(iList.get(position));
    }

    @Override
    public int getItemCount() {
        return iList.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        ItemRowBinding binding;

        ItemHolder(@NonNull ItemRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.setClicker(clicker);
        }

        void bind(TodoItem todoItem) {
            binding.setItemItem(todoItem);
            binding.setLastEdit(lastEdit);
            binding.executePendingBindings();
        }
    }
}
