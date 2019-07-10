package com.andraganoid.verymuchtodo.todo;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.andraganoid.verymuchtodo.model.Document;
import com.andraganoid.verymuchtodo.model.Message;
import com.andraganoid.verymuchtodo.model.TodoItem;
import com.andraganoid.verymuchtodo.model.TodoList;
import com.andraganoid.verymuchtodo.model.User;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ToDoViewModel extends ViewModel {

    public MutableLiveData<ArrayList<User>> userList = new MutableLiveData<>();
    public MutableLiveData<ArrayList<TodoList>> todoList = new MutableLiveData<>();
    public MutableLiveData<ArrayList<Message>> messageList = new MutableLiveData<>();

//    public FirebaseFirestore todo;
    public ObservableField<User> user = new ObservableField<>();

    public MutableLiveData<Document> addDocument = new MutableLiveData<>();
    public MutableLiveData<Document> deleteDocument = new MutableLiveData<>();

    public LiveData<Document> getAddDocument() {
        return addDocument;
    }

    public LiveData<Document> getDeleteDocument() {
        return deleteDocument;
    }

    public LiveData<ArrayList<TodoList>> getTodoList() {
        return todoList;
    }

    public TodoList currentToDoList;
    public TodoItem currentToDoItem;
}