package com.andraganoid.verymuchtodo.todo.item;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.andraganoid.verymuchtodo.R;
import com.andraganoid.verymuchtodo.databinding.ItemRowBinding;
import com.andraganoid.verymuchtodo.model.TodoItem;

import java.util.List;

public class ItemFragmentAdapter extends RecyclerView.Adapter<ItemFragmentAdapter.ItemHolder> {
    private List<TodoItem> iList;
    private ItemClicker clicker;

    public ItemFragmentAdapter(List<TodoItem> iList, ItemClicker clicker) {
        this.iList = iList;
        this.clicker = clicker;
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

    public class ItemHolder extends RecyclerView.ViewHolder {
        ItemRowBinding binding;

        public ItemHolder(@NonNull ItemRowBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
            binding.setClicker(clicker);
        }

        void bind(TodoItem todoItem) {
            binding.setItemItem(todoItem);
        }
    }
}
