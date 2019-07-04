package com.andraganoid.verymuchtodo.model;

import java.util.HashMap;

public class Document {
    private String collection;
    private String document;
    private HashMap<String, Object> map;

    public Document(String collection, String document, HashMap<String, Object> map) {
        this.collection = collection;
        this.document = document;
        this.map = map;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public HashMap<String, Object> getMap() {
        return map;
    }

    public void setMap(HashMap<String, Object> map) {
        this.map = map;
    }
}
