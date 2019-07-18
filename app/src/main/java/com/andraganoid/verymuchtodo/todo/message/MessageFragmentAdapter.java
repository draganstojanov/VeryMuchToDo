package com.andraganoid.verymuchtodo.todo.message;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.andraganoid.verymuchtodo.R;
import com.andraganoid.verymuchtodo.databinding.MsgRowBinding;
import com.andraganoid.verymuchtodo.model.Message;
import com.andraganoid.verymuchtodo.todo.ToDoViewModel;

import java.util.ArrayList;

public class MessageFragmentAdapter extends RecyclerView.Adapter<MessageFragmentAdapter.MessageHolder> {
    private ArrayList<Message> mList = new ArrayList<>();
    private MessageClicker clicker;
    private ToDoViewModel lastEdit;

    MessageFragmentAdapter(ArrayList<Message> mList, ToDoViewModel toDoViewModel, MessageClicker clicker) {
        this.lastEdit = toDoViewModel;
        this.clicker = clicker;
        setList(mList);
    }

    public void setList(ArrayList<Message> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessageFragmentAdapter.MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MsgRowBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.msg_row,
                parent,
                false);
        return new MessageHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
        holder.bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MessageHolder extends RecyclerView.ViewHolder {
        MsgRowBinding binding;

        MessageHolder(@NonNull MsgRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.setClicker(clicker);
        }

        void bind(Message msg) {
            binding.setMessage(msg);
            binding.setLastEdit(lastEdit);
            binding.executePendingBindings();
        }
    }
}


