package com.andraganoid.verymuchtodo.todo;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.andraganoid.verymuchtodo.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class TodoBaseFragment extends Fragment {


    public ToDoViewModel toDoViewModel;
    public Todo toDo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toDo = (Todo) getActivity();
        //toDoViewModel = ViewModelProviders.of(toDo).get(ToDoViewModel.class);
        toDoViewModel = toDo.toDoViewModel;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       BottomNavigationView bottomMain = toDo.findViewById(R.id.main_bottom_bar);
        bottomMain.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.main_lists:
                        toDo.navigateToFragment(toDo.LIST_FRAGMENT);
                        break;
                    case R.id.main_msg:
                        Toast.makeText(toDo, "MESSAGES", Toast.LENGTH_SHORT).show();
                       // toDo.navigateToFragment(toDo.MESSAGE_FRAGMENT);
                        break;
                    case R.id.main_users:
                        Toast.makeText(toDo, "USERS", Toast.LENGTH_SHORT).show();
                        //toDo.navigateToFragment(USER_FRAGMENT);
                        break;
                    case R.id.main_map:
                        Toast.makeText(toDo, "MAP", Toast.LENGTH_SHORT).show();
                        // toDo.navigateToFragment(toDo.MESSAGE_FRAGMENT);
                        break;
                    case R.id.main_logout:
                        Toast.makeText(toDo, "LOGOUT", Toast.LENGTH_SHORT).show();
                       toDo.logout();
                        break;

                }
                return false;
            }
        });
    }

    public void closeKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
