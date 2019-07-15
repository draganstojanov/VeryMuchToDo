package com.andraganoid.verymuchtodo.todo.message;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.andraganoid.verymuchtodo.R;
import com.andraganoid.verymuchtodo.databinding.FragmentMessageBinding;
import com.andraganoid.verymuchtodo.model.Message;
import com.andraganoid.verymuchtodo.todo.TodoBaseFragment;

import java.util.ArrayList;


public class MessageFragment extends TodoBaseFragment implements MessageClicker {

    FragmentMessageBinding binding;
    String newMsgText;
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
        binding.setMessage(newMsgText);

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // closeKeyboard(binding.getRoot());

        binding.msgRecView.setLayoutManager(new LinearLayoutManager(toDo));
        adapter = new MessageFragmentAdapter(toDoViewModel.getMessageList().getValue(), toDoViewModel,this);
        binding.msgRecView.setAdapter(adapter);

        toDoViewModel.getMessageList().observe(this, new Observer<ArrayList<Message>>() {
            @Override
            public void onChanged(ArrayList<Message> messages) {
                  adapter.setList(messages);
            }
        });


//        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
//                new ItemTouchHelper.SimpleCallback(
//                        0,
//                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//
//                    @Override
//                    public boolean onMove(@NonNull RecyclerView recyclerView,
//                                          @NonNull RecyclerView.ViewHolder viewHolder,
//                                          @NonNull RecyclerView.ViewHolder target) {
//                        return false;
//                    }

//                    @Override
//                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
//                        TodoList todoList = toDoViewModel.getTodoList().getValue().get(viewHolder.getAdapterPosition());
//
//                        switch (swipeDir) {
//                            case ItemTouchHelper.RIGHT:
//                                if (todoList.isCompleted()) {
//                                    toDoViewModel.deleteDocument.setValue(new Document(todoList));
//                                } else {
//                                    Toast.makeText(toDo, getString(R.string.list_not_completed), Toast.LENGTH_LONG).show();
//                                }
//                                adapter.notifyDataSetChanged();
//                                break;
//                            case ItemTouchHelper.LEFT:
//                                toDoViewModel.currentToDoList = todoList;
//                                toDo.navigateToFragment(toDo.LIST_EDIT_FRAGMENT);
//                                break;
//                        }
//                    }
//                };
//
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
//        itemTouchHelper.attachToRecyclerView(binding.listRecView);
    }


    @Override
    public void sendMessage() {

    }
}
