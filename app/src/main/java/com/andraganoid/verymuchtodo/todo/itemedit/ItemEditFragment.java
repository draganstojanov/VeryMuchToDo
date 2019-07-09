package com.andraganoid.verymuchtodo.todo.itemedit;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andraganoid.verymuchtodo.R;
import com.andraganoid.verymuchtodo.model.TodoItem;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemEditFragment extends Fragment {


    public ItemEditFragment() {
        // Required empty public constructor
    }

    public ItemEditFragment(TodoItem todoItem) {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_edit, container, false);
    }

}
