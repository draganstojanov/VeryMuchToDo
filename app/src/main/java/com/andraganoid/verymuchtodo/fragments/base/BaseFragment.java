package com.andraganoid.verymuchtodo.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.andraganoid.verymuchtodo.ToDoViewModel;

public class BaseFragment extends Fragment {

    ToDoViewModel toDoViewModel;

    //  public static BaseFragment newInstance() {
    //   return new BaseFragment();
    // }

//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//       // return inflater.inflate(R.layout.base_fragment, container, false);
//    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toDoViewModel = ViewModelProviders.of(getActivity()).get(ToDoViewModel.class);

    }

}
