package com.andraganoid.verymuchtodo.todo.list;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andraganoid.verymuchtodo.R;
import com.andraganoid.verymuchtodo.todo.TodoBaseFragment;


public class ListEditFragment extends TodoBaseFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_edit, container, false);
    }

}
