package com.andraganoid.verymuchtodo;

import com.andraganoid.verymuchtodo.model.TodoList;

public interface VeryOnItemClickListener {
    void listChoosed(TodoList tl);

    void changedCompletion(int position);
}
