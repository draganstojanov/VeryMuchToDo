package com.andraganoid.verymuchtodo.todo.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.andraganoid.verymuchtodo.R;
import com.andraganoid.verymuchtodo.databinding.ListFragmentBinding;
import com.andraganoid.verymuchtodo.todo.TodoBaseFragment;

public class ListFragment extends TodoBaseFragment implements ListClicker {


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ListFragmentBinding binding = DataBindingUtil.inflate(
                inflater,
                R.layout.list_fragment,
                container,
                false);
        binding.setClicker(this);
        //  binding.setViewModel(todoViewModel);

        return binding.getRoot();
    }

    @Override
    public void onFabClicked() {

    }
}
