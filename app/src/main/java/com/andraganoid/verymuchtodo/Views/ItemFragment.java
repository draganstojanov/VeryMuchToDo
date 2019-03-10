package com.andraganoid.verymuchtodo.Views;


import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.andraganoid.verymuchtodo.Model.TodoItem;
import com.andraganoid.verymuchtodo.Model.TodoList;
import com.andraganoid.verymuchtodo.R;
import com.andraganoid.verymuchtodo.Todo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.andraganoid.verymuchtodo.Todo.COLLECTION_TODOS;


public class ItemFragment extends Fragment implements View.OnClickListener {


    private View fiView;
    private Todo todoActivity;
    private RecyclerView itemRecView;
    private ItemAdapter itemAdapter;
    private RecyclerView.LayoutManager itemLayMan;
    private ConstraintLayout editView;
    private ConstraintLayout itemView;
    private TodoItem currentItem;

    public ItemFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fiView = inflater.inflate(R.layout.fragment_item, container, false);

        itemView = fiView.findViewById(R.id.todo_items_view);
        editView = fiView.findViewById(R.id.new_todo_item_form);

        todoActivity = (Todo) getActivity();
        todoActivity.setTitle(todoActivity.currentList.getTitle(), "");
        itemRecView = fiView.findViewById(R.id.item_rec_view);
        itemAdapter = new ItemAdapter(todoActivity.currentList.getTodoItemList());
        itemLayMan = new LinearLayoutManager(getContext());
        itemRecView.setLayoutManager(itemLayMan);
        itemRecView.setAdapter(itemAdapter);




        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {


            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                TodoItem ti = todoActivity.currentList.getTodoItemList().get(viewHolder.getAdapterPosition());
                switch (swipeDir) {

                    case ItemTouchHelper.LEFT:
                        if (!ti.isCompleted()) {
                            showItemEdit(ti);

//                            itemView.setVisibility(View.GONE);
//
//                            ((EditText) fiView.findViewById(R.id.new_todo_item_content)).setText(ti.getContent());
//                            currentItem = ti;
//
//                            editView.setVisibility(View.VISIBLE);
                        } else {
                            itemAdapter.notifyDataSetChanged();
                            Toast.makeText(todoActivity, "Task is completed!.", Toast.LENGTH_LONG).show();
                        }

                        break;//edit

                    case ItemTouchHelper.RIGHT:

                        if (ti.isCompleted()) {
                            todoActivity.deleteDocument(COLLECTION_TODOS, todoActivity.currentList.getTitle());
                        } else {
                            itemAdapter.notifyDataSetChanged();
                            Toast.makeText(todoActivity, "Task is not completed!.", Toast.LENGTH_LONG).show();
                        }
                        break;//delete
                }


//                TodoList tl = todoActivity.todoList.get(viewHolder.getAdapterPosition());
//                if (tl.isCompleted()) {
//                    todoActivity.deleteDocument(COLLECTION_TODOS, tl.getTitle());
//                } else {
//                    listsAdapter.notifyDataSetChanged();
//                    Toast.makeText(todoActivity, "List is not completed!.", Toast.LENGTH_LONG).show();
//                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);

        itemTouchHelper.attachToRecyclerView(itemRecView);


        fiView.findViewById(R.id.items_fab).setOnClickListener(this);
        fiView.findViewById(R.id.new_todo_item_save).setOnClickListener(this);

        return fiView;
    }

    public void refreshItems() {
        itemAdapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.items_fab:
                showItemEdit(new TodoItem(""));
//                itemView.setVisibility(View.GONE);
//                ((EditText) fiView.findViewById(R.id.new_todo_item_content)).getText().clear();
//                currentItem = new TodoItem("");
//                editView.setVisibility(View.VISIBLE);
                break;

            case R.id.new_todo_item_save:

                String content = ((EditText) fiView.findViewById(R.id.new_todo_item_content)).getText().toString();

                if (!content.isEmpty()) {

                    //  TodoItem newTodoItem = new TodoItem(content);
                    currentItem.setContent(content);
                    itemView.setVisibility(View.VISIBLE);
                    editView.setVisibility(View.GONE);

                    List <TodoItem> temp = todoActivity.currentList.getTodoItemList();
                    temp.add(currentItem);
                    todoActivity.currentList.setTodoItemList(temp);
                    todoActivity.currentList.setLastEditTimestamp(currentItem.getLastEditTimestamp());

                    Map<String, List<TodoItem>> map=new HashMap();
                    map.put("todoItemList",temp);
                    todoActivity.updateDocument(COLLECTION_TODOS,todoActivity.currentList.getTitle(),"todoItemList",temp);

                }

                break;
        }


    }

    private void showItemEdit(TodoItem item) {

        itemView.setVisibility(View.GONE);

        currentItem = item;
        ((EditText) fiView.findViewById(R.id.new_todo_item_content)).setText(currentItem.getContent());

        editView.setVisibility(View.VISIBLE);

    }


}
