package com.andraganoid.verymuchtodo.J.model;

import java.util.HashMap;

public class Document {

    public static final String COLLECTION_USERS = "collectionUsers";
    public static final String COLLECTION_TODO_LISTS = "collectionToDoList";
    public static final String COLLECTION_MESSAGES = "collectionMessages";
    public static final String COLLECTION_LOCATION = "collectionLocation";

    private String collection;
    private String documentName;
    private HashMap<String, Object> map;

    public Document(User user) {
        map = new HashMap<>();
        map.put("id", user.getId());
        map.put("name", user.getName());
        map.put("email", user.getEmail());
        collection = COLLECTION_USERS;
        documentName = user.getId();
    }

    public Document(TodoList todoList) {
        map = new HashMap<>();
        map.put("id", todoList.getId());
        map.put("title", todoList.getTitle());
        map.put("description", todoList.getDescription());
        map.put("emergency", todoList.isEmergency());
        map.put("completed", todoList.isCompleted());
        map.put("todoItemList", todoList.getTodoItemList());
        map.put("timestamp", todoList.getTimestamp());
        map.put("user", todoList.getUser());
        collection = COLLECTION_TODO_LISTS;
        documentName = todoList.getId();
    }

    public Document(Message message) {
        map = new HashMap<>();
        map.put("text", message.getText());
        map.put("timestamp", message.getTimestamp());
        map.put("user", message.getUser());
        map.put("id", message.getId());
        documentName = message.getId();
        collection = COLLECTION_MESSAGES;
    }

    public Document(ToDoLocation location) {
        map = new HashMap<>();
        map.put("latitude", location.getLatitude());
        map.put("longitude", location.getLongitude());
        map.put("timestamp", location.getTimestamp());
        map.put("user", location.getUser());
        collection = COLLECTION_LOCATION;
        documentName = location.getUser().getId();
    }

    public String getCollection() {
        return collection;
    }

    public String getDocumentName() {
        return documentName;
    }

    public HashMap<String, Object> getMap() {
        return map;
    }

}
