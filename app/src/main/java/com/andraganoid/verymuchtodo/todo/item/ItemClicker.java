package com.andraganoid.verymuchtodo.todo.item;

import android.view.View;

import com.andraganoid.verymuchtodo.model.TodoItem;

public interface ItemClicker {
    void onFabClicked();

    void onItemLongClicked(View view, TodoItem todoItem);

}
