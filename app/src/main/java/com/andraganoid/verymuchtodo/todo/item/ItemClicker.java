package com.andraganoid.verymuchtodo.todo.item;

import android.view.View;

import com.andraganoid.verymuchtodo.model.TodoItem;
import com.andraganoid.verymuchtodo.todo.Todo;

public interface ItemClicker {
   void onFabClicked();
   void onItemLongClicked(View view,TodoItem todoItem);
   void onItemClicked(TodoItem todoItem);
}
