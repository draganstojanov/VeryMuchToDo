package com.andraganoid.verymuchtodo.Model;

import java.util.List;

public class TodoList {

    private final String ITEM_CAT_SHOP_LIST="Shopping list";
    private final String ITEM_CAT_REMINDER="Reminder";

    private String name;
    private String shortDescription;
    private long createdTimestamp;
    private long lastChangedTimestamp;
    private String createdByUser;
    private String lastChangedByUser;
    private int category;
    private boolean allDone;
    private boolean setForDeletion;

    private List<TodoItem> todoItemList;
}
