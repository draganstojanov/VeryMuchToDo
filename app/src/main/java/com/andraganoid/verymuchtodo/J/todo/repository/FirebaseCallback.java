package com.andraganoid.verymuchtodo.J.todo.repository;

import com.andraganoid.verymuchtodo.J.model.Message;
import com.andraganoid.verymuchtodo.J.model.ToDoLocation;
import com.andraganoid.verymuchtodo.J.model.TodoList;
import com.andraganoid.verymuchtodo.J.model.User;

import java.util.ArrayList;

public interface FirebaseCallback {

    public void listsUpdated(ArrayList<TodoList> tList);

    void messagesUpdated(ArrayList<Message> mList);

    void usersUpdated(ArrayList<User> uList);

    void locationsUpdated(ArrayList<ToDoLocation> lList);
}
