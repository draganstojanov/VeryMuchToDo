package com.andraganoid.verymuchtodo.todo.repository;

import com.andraganoid.verymuchtodo.model.Message;
import com.andraganoid.verymuchtodo.model.ToDoLocation;
import com.andraganoid.verymuchtodo.model.TodoList;
import com.andraganoid.verymuchtodo.model.User;

import java.util.ArrayList;

public interface FirebaseCallback {

    public void listsUpdated(ArrayList<TodoList> tList);

    void messagesUpdated(ArrayList<Message> mList);

    void usersUpdated(ArrayList<User> uList);

    void locationsUpdated(ArrayList<ToDoLocation> lList);
}
