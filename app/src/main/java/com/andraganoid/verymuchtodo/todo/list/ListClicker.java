package com.andraganoid.verymuchtodo.todo.list;

import com.andraganoid.verymuchtodo.model.TodoList;

public interface ListClicker {
    void onFabClicked();

    void onItemClicked(TodoList todoList);

   // void onItemLongClicked(TodoList todoList);
}
