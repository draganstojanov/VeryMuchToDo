package com.andraganoid.verymuchtodo.todo.item;

import android.view.View;

import com.andraganoid.verymuchtodo.model.TodoItem;

public interface ItemClicker {

    void onFabClicked();

    boolean onItemLongClicked(View view, TodoItem todoItem);

}
