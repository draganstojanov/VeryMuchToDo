package com.andraganoid.verymuchtodo.todo.users;

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
import com.andraganoid.verymuchtodo.databinding.FragmentUserBinding;
import com.andraganoid.verymuchtodo.model.User;
import com.andraganoid.verymuchtodo.todo.TodoBaseFragment;

import java.util.ArrayList;


public class UserFragment extends TodoBaseFragment implements UserClicker {

    FragmentUserBinding binding;
    UserFragmentAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_user,
                container,
                false);
        binding.setClicker(this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // closeKeyboard(binding.getRoot());

        binding.userRecView.setLayoutManager(new LinearLayoutManager(toDo));
        adapter = new UserFragmentAdapter(toDoViewModel.getUserList().getValue(), this);
        binding.userRecView.setAdapter(adapter);

        toDoViewModel.getUserList().observe(this, new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(ArrayList<User> users) {
                adapter.setList(users);
            }
        });
    }

    @Override
    public void onUserClicked() {

    }
}