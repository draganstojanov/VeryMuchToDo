package com.andraganoid.verymuchtodo.J.todo.item;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.andraganoid.verymuchtodo.J.model.TodoItem;
import com.andraganoid.verymuchtodo.J.todo.ToDoViewModel;
import com.andraganoid.verymuchtodo.R;
import com.andraganoid.verymuchtodo.databinding.ItemRowBinding;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ItemFragmentAdapter extends RecyclerView.Adapter<ItemFragmentAdapter.ItemHolder> {
    private ArrayList<TodoItem> iList = new ArrayList<>();
    private ItemFragment clicker;
    private ToDoViewModel lastEdit;

    public ItemFragmentAdapter(ArrayList<TodoItem> iList, ToDoViewModel toDoViewModel, ItemFragment clicker) {
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
