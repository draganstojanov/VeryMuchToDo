package com.andraganoid.verymuchtodo.Views;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andraganoid.verymuchtodo.R;

public class ListsFragment extends Fragment {


    public ListsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View flView = inflater.inflate(R.layout.fragment_lists, container, false);


        return flView;
    }

}
