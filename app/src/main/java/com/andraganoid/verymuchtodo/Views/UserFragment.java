package com.andraganoid.verymuchtodo.Views;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andraganoid.verymuchtodo.R;
import com.andraganoid.verymuchtodo.todo.Todo;


public class UserFragment extends Fragment implements View.OnClickListener {

    private View uView;
    private Todo todoActivity;
    private RecyclerView userRecView;
    private UserAdapter userAdapter;
    private RecyclerView.LayoutManager userLayMan;

    public UserFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        uView = inflater.inflate(R.layout.fragment_user, container, false);

        todoActivity = (Todo) getActivity();
        todoActivity.setTitle("Users", "");
        userRecView = uView.findViewById(R.id.user_rec_view);
        userAdapter = new UserAdapter(todoActivity.userList, todoActivity);
        userLayMan = new LinearLayoutManager(getContext());
        userRecView.setLayoutManager(userLayMan);
        userRecView.setAdapter(userAdapter);

        return uView;
    }

    public void refreshUsers() {
        userAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
    }

}