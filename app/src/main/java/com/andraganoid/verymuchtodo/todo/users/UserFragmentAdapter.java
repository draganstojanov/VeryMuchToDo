package com.andraganoid.verymuchtodo.todo.users;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.andraganoid.verymuchtodo.R;
import com.andraganoid.verymuchtodo.databinding.UserRowBinding;
import com.andraganoid.verymuchtodo.model.User;

import java.util.ArrayList;


public class UserFragmentAdapter extends RecyclerView.Adapter<UserFragmentAdapter.UserHolder> {
    private ArrayList<User> uList = new ArrayList<>();
    private UserClicker clicker;

    UserFragmentAdapter(ArrayList<User> uList, UserClicker clicker) {
        this.clicker = clicker;
        setList(uList);
    }

    public void setList(ArrayList<User> mList) {
        this.uList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserFragmentAdapter.UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UserRowBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.user_row,
                parent,
                false);
        return new UserHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserFragmentAdapter.UserHolder holder, int position) {
        holder.bind(uList.get(position));
    }

    @Override
    public int getItemCount() {
        return uList.size();
    }

    class UserHolder extends RecyclerView.ViewHolder {
        UserRowBinding binding;

        UserHolder(@NonNull UserRowBinding binding) {
            super((binding.getRoot()));
            this.binding = binding;
            binding.setClicker(clicker);
        }

        void bind(User user) {
            binding.setUser(user);
            binding.executePendingBindings();
        }
    }
}



