package com.andraganoid.verymuchtodo.J.todo.users;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andraganoid.verymuchtodo.J.todo.TodoBaseFragment;
import com.andraganoid.verymuchtodo.R;
import com.andraganoid.verymuchtodo.databinding.FragmentUserBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;


public class UserFragment extends TodoBaseFragment {

    private FragmentUserBinding binding;
    private UserFragmentAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toDoViewModel.setTodoBars(getString(R.string.users), false);
    }

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
        binding.userRecView.setLayoutManager(new LinearLayoutManager(toDo));
        adapter = new UserFragmentAdapter(toDoViewModel.getUserList().getValue(), this);
        binding.userRecView.setAdapter(adapter);
        toDoViewModel.getUserList().observe(this, users -> {
            adapter.setList(users);
        });
    }

    public void onUserClicked() {
    }
}