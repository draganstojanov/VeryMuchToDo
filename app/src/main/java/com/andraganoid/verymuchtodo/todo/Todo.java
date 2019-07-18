package com.andraganoid.verymuchtodo.todo;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.andraganoid.verymuchtodo.MainActivity;
import com.andraganoid.verymuchtodo.R;
import com.andraganoid.verymuchtodo.databinding.ActivityTodoBinding;
import com.andraganoid.verymuchtodo.todo.item.ItemFragment;
import com.andraganoid.verymuchtodo.todo.itemedit.ItemEditFragment;
import com.andraganoid.verymuchtodo.todo.list.ListFragment;
import com.andraganoid.verymuchtodo.todo.listedit.ListEditFragment;
import com.andraganoid.verymuchtodo.todo.map.MapFragment;
import com.andraganoid.verymuchtodo.todo.menu.MenuClicker;
import com.andraganoid.verymuchtodo.todo.message.MessageFragment;
import com.andraganoid.verymuchtodo.todo.users.UserFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.rw.keyboardlistener.KeyboardUtils;


public class Todo extends AppCompatActivity implements MenuClicker {

    public ToDoViewModel toDoViewModel;
    private SharedPreferences prefs;
    public ActivityTodoBinding binding;
    public Fragment backTo;
    private View bottomBar;

    public final Fragment LIST_FRAGMENT = new ListFragment();
    public final Fragment LIST_EDIT_FRAGMENT = new ListEditFragment();
    public final Fragment ITEM_FRAGMENT = new ItemFragment();
    public final Fragment ITEM_EDIT_FRAGMENT = new ItemEditFragment();
    public final Fragment MESSAGE_FRAGMENT = new MessageFragment();
    public final Fragment USER_FRAGMENT = new UserFragment();
    public final Fragment MAP_FRAGMENT = new MapFragment();

    @Override
    protected void onResume() {
        super.onResume();
        navigateToFragment(LIST_FRAGMENT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_todo);
        bottomBar = findViewById(R.id.main_bottom_bar);
        toDoViewModel = ViewModelProviders.of(this).get(ToDoViewModel.class);
        toDoViewModel.getCurrentLocation();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        registerObservers();
        binding.setMenuAlert(toDoViewModel.menuAlert.getValue());
        binding.setClicker(this);
        setKeyboardListener();
    }

    private void registerObservers() {

        toDoViewModel.todoBars.observe(this, todoBars -> {
            binding.todoToolbar.setTitle(todoBars.getTitle());
            binding.todoToolbar.setSubtitle(todoBars.getSubtitle());
        });

        toDoViewModel.menuAlert.observe(this, menuAlert -> {
            binding.invalidateAll();
        });
    }

    private void setKeyboardListener() {
        KeyboardUtils.addKeyboardToggleListener(this, isVisible -> {
            if (isVisible) {
                bottomBar.setVisibility(View.GONE);
            } else {
                bottomBar.setVisibility(View.VISIBLE);
            }
        });
    }


    public void navigateToFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.todo_fragment, fragment)
                    .commit();
        }
    }

    @Override
    public void onListItemClicked() {
        navigateToFragment(LIST_FRAGMENT);
    }

    @Override
    public void onMessageItemClicked() {
        navigateToFragment(MESSAGE_FRAGMENT);
    }

    @Override
    public void onUserItemClicked() {
        navigateToFragment(USER_FRAGMENT);
    }

    @Override
    public void onMapItemClicked() {
        navigateToFragment(MAP_FRAGMENT);
    }

    @Override
    public void onLogoutItemClicked() {
        prefs.edit()
                .putString("PREFS_ID", "")
                .putString("PREFS_NAME", "")
                .putString("PREFS_EMAIL", "")
                .putBoolean("PREFS_IS_USER_REGISTRED", false)
                .apply();
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(this, MainActivity.class));
        finishAffinity();
    }

    @Override
    public void onBackPressed() {
        if (backTo == null) {
            finishAffinity();
        } else {
            navigateToFragment(backTo);
        }
    }
}
