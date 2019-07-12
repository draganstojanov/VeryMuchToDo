package com.andraganoid.verymuchtodo.model;

import java.util.HashMap;

public class Document {

    public static final String COLLECTION_USERS = "collectionUsers";
    public static final String COLLECTION_TODO_LISTS = "collectionToDoList";
    public static final String COLLECTION_MESSAGES = "collectionMessages";

    private String collection;
    private String documentName;
    private HashMap<String, Object> map;


    public Document(User user) {
        map = new HashMap<>();
        map.put("id", user.getId());
        map.put("name", user.getName());
        map.put("email", user.getEmail());
//        map.put("location", user.getLocation());
//        map.put("locationTimestamp", user.getLocationTimestamp());
        collection = COLLECTION_USERS;
        documentName = user.getId();
    }

    public Document(TodoList todoList) {
        map = new HashMap<>();
        map.put("title", todoList.getTitle());
        map.put("description", todoList.getDescription());
        map.put("emergency", todoList.isEmergency());
        map.put("completed", todoList.isCompleted());
        map.put("todoItemList", todoList.getTodoItemList());
        map.put("timestamp", todoList.getTimestamp());
        map.put("user", todoList.getUser());
        collection = COLLECTION_TODO_LISTS;
        documentName = todoList.getTitle();
    }


    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public HashMap<String, Object> getMap() {
        return map;
    }

    public void setMap(HashMap<String, Object> map) {
        this.map = map;
    }
}
