package com.andraganoid.verymuchtodo.Views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andraganoid.verymuchtodo.Model.TodoList;
import com.andraganoid.verymuchtodo.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class ListsAdapter extends RecyclerView.Adapter <ListsAdapter.ListsViewHolder> {

    private List <TodoList> lists;

    public ListsAdapter(List <TodoList> lists) {
        this.lists = lists;
    }

    @NonNull
    @Override
    public ListsAdapter.ListsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.lists_row, parent, false);
        return new ListsViewHolder(viewDataBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull ListsAdapter.ListsViewHolder holder, int position) {
     //   String name = lists.get(position);
      //  holder.bind(name);
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }


    public class ListsViewHolder extends RecyclerView.ViewHolder {
        public ListsViewHolder(@NonNull ViewDataBinding binding) {
            super(binding.getRoot());
        }
    }
}
