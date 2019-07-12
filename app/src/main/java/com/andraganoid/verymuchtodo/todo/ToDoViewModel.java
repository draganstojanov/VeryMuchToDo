package com.andraganoid.verymuchtodo.todo;

import android.text.format.DateFormat;
import android.util.Log;

import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.andraganoid.verymuchtodo.model.Document;
import com.andraganoid.verymuchtodo.model.Message;
import com.andraganoid.verymuchtodo.model.TodoItem;
import com.andraganoid.verymuchtodo.model.TodoList;
import com.andraganoid.verymuchtodo.model.User;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class ToDoViewModel extends ViewModel {


    public MutableLiveData<ArrayList<User>> userList = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TodoList>> todoList = new MutableLiveData<>();
    public MutableLiveData<ArrayList<Message>> messageList = new MutableLiveData<>();

    public ObservableField<User> user = new ObservableField<>();

    public MutableLiveData<Document> addDocument = new MutableLiveData<>();
    public MutableLiveData<Document> deleteDocument = new MutableLiveData<>();

    public LiveData<Document> getAddDocument() {
        return addDocument;
    }

    public LiveData<Document> getDeleteDocument() {
        return deleteDocument;
    }

    public void setTodoList(ArrayList<TodoList> todoLists) {
        todoList.setValue(todoLists);
    }

    public LiveData<ArrayList<TodoList>> getTodoList() {
        return todoList;
    }


    public TodoList currentToDoList;
    public TodoItem currentToDoItem;

    public Object clone(Object original, Object cloned) {
        for (Field field : original.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                field.set(cloned, field.get(original));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return cloned;
    }

    private StringBuilder sb = new StringBuilder();
    private Calendar cal = Calendar.getInstance();

    public String getLastEdit(String name, Long timestamp) {
        sb.setLength(0);
        sb.append(name)
                .append("@")
                .append(getFormattedDate(timestamp));
        return sb.toString();
    }

    private String getFormattedDate(Long timestamp) {
        cal.setTimeInMillis(timestamp);
        return DateFormat.format("dd.MM.yyyy HH:mm", cal).toString();
    }


    public void parseToDoListCollection(QuerySnapshot queryDocumentSnapshots) {
        ArrayList<TodoList> tList = new ArrayList<>();

        for (QueryDocumentSnapshot qs : queryDocumentSnapshots) {


            TodoList t = qs.toObject(TodoList.class);
            Log.d("QUERY", t.getTitle());
            tList.add(qs.toObject(TodoList.class));
        }

        if (tList.size() > 0) {
            Collections.sort(tList, new Comparator<TodoList>() {
                @Override
                public int compare(final TodoList object1, final TodoList object2) {
                    return String.valueOf(object2.getTimestamp()).compareTo(String.valueOf(object1.getTimestamp()));
                }
            });
        }
        setTodoList(tList);
    }


}