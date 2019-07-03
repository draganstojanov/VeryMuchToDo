package com.andraganoid.verymuchtodo.todo;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.andraganoid.verymuchtodo.model.Message;
import com.andraganoid.verymuchtodo.model.TodoList;
import com.andraganoid.verymuchtodo.model.User;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ToDoViewModel extends ViewModel {

    MutableLiveData<ArrayList<User>> userList = new MutableLiveData<>();
    MutableLiveData<ArrayList<TodoList>> todoList = new MutableLiveData<>();
    MutableLiveData<ArrayList<Message>> messageList = new MutableLiveData<>();

    public FirebaseFirestore todo;
    public ObservableField<User> user = new ObservableField<>();

}