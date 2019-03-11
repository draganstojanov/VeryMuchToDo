package com.andraganoid.verymuchtodo;

import com.andraganoid.verymuchtodo.Model.TodoList;

public interface VeryOnItemClickListener {
    void listChoosed(TodoList tl);

    void changedCompletition(int position);
}
