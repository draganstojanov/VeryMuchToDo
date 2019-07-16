package com.andraganoid.verymuchtodo.todo.message;


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
import com.andraganoid.verymuchtodo.databinding.FragmentMessageBinding;
import com.andraganoid.verymuchtodo.model.Document;
import com.andraganoid.verymuchtodo.model.Message;
import com.andraganoid.verymuchtodo.todo.TodoBaseFragment;

import java.util.ArrayList;


public class MessageFragment extends TodoBaseFragment implements MessageClicker {

    FragmentMessageBinding binding;
    public String newMsgText;
    MessageFragmentAdapter adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toDoViewModel.setTodoBars(getString(R.string.messages), "");
        toDoViewModel.setAlerts("msg", false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_message,
                container,
                false);
        binding.setClicker(this);
        binding.setMessage(this);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // closeKeyboard(binding.getRoot());

        binding.msgRecView.setLayoutManager(new LinearLayoutManager(toDo));
        adapter = new MessageFragmentAdapter(toDoViewModel.getMessageList().getValue(), toDoViewModel, this);
        binding.msgRecView.setAdapter(adapter);

        toDoViewModel.getMessageList().observe(this, new Observer<ArrayList<Message>>() {
            @Override
            public void onChanged(ArrayList<Message> messages) {
                adapter.setList(messages);
            }
        });

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                Message message = toDoViewModel.getMessageList().getValue().get(viewHolder.getAdapterPosition());
                if (message.getUser().getId().equals(toDoViewModel.mUser.getId())) {
                    toDoViewModel.deleteDocument(new Document(message));
                } else {
                    Toast.makeText(toDo, R.string.not_msg_creator, Toast.LENGTH_LONG).show();
                    adapter.notifyDataSetChanged();
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(binding.msgRecView);

    }


    @Override
    public void sendMessage() {

        if (!newMsgText.isEmpty()) {
            if (newMsgText.length() < 100) {
                toDoViewModel.addDocument(new Document(new Message(toDoViewModel.mUser, newMsgText)));
                closeKeyboard();
                newMsgText = "";
                binding.invalidateAll();
            } else {
                Toast.makeText(toDo, R.string.msg_too_long, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
