package com.andraganoid.verymuchtodo.Views;


import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.andraganoid.verymuchtodo.model.TodoItem;
import com.andraganoid.verymuchtodo.R;
import com.andraganoid.verymuchtodo.todo.Todo;

import java.util.ArrayList;
import java.util.List;


public class ItemFragment extends Fragment implements View.OnClickListener {

    private boolean isNew;
    private View fiView;
    private Todo todoActivity;
    private RecyclerView itemRecView;
    private ItemAdapter itemAdapter;
    private RecyclerView.LayoutManager itemLayMan;
    private ConstraintLayout editView;
    private ConstraintLayout itemView;
    private TodoItem currentItem;
    private List <TodoItem> tl = new ArrayList <>();


    public ItemFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fiView = inflater.inflate(R.layout.fragment_item, container, false);

        itemView = fiView.findViewById(R.id.todo_items_view);
        editView = fiView.findViewById(R.id.new_todo_item_form);

        todoActivity = (Todo) getActivity();
        tl = todoActivity.currentList.getTodoItemList();
        todoActivity.findViewById(R.id.main_lists).setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        todoActivity.setTitle(todoActivity.currentList.getTitle(), "");
        itemRecView = fiView.findViewById(R.id.item_rec_view);
        itemAdapter = new ItemAdapter(tl, todoActivity);
        itemLayMan = new LinearLayoutManager(getContext());
        itemRecView.setLayoutManager(itemLayMan);
        itemRecView.setAdapter(itemAdapter);
        closeKeyboard();

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
                            isNew = false;
                            showItemEdit(ti);

                        } else {
                            itemAdapter.notifyDataSetChanged();
                            Toast.makeText(todoActivity, "Task is completed!.", Toast.LENGTH_LONG).show();
                        }

                        break;//edit

                    case ItemTouchHelper.RIGHT:

                        if (ti.isCompleted()) {
                            todoActivity.currentList.getTodoItemList().remove(viewHolder.getAdapterPosition());
                            todoActivity.saveList(todoActivity.currentList);

                        } else {
                            itemAdapter.notifyDataSetChanged();
                            Toast.makeText(todoActivity, "Task is not completed!.", Toast.LENGTH_LONG).show();
                        }
                        break;//delete
                }


            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);

        itemTouchHelper.attachToRecyclerView(itemRecView);

        fiView.findViewById(R.id.items_fab).setOnClickListener(this);
        fiView.findViewById(R.id.new_todo_item_save).setOnClickListener(this);

        return fiView;
    }

    public void refreshItems() {
        tl.clear();
        tl.addAll(todoActivity.currentList.getTodoItemList());
        itemAdapter.notifyDataSetChanged();
        closeKeyboard();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.items_fab:
                isNew = true;
                showItemEdit(new TodoItem(""));

                break;

            case R.id.new_todo_item_save:

                String content = ((EditText) fiView.findViewById(R.id.new_todo_item_content)).getText().toString();

                if (!content.isEmpty()) {

                    currentItem.setContent(content);
                    currentItem.setLastEdit();
                    itemView.setVisibility(View.VISIBLE);
                    editView.setVisibility(View.GONE);

                    if (isNew) {
                        todoActivity.currentList.getTodoItemList().add(currentItem);
                        todoActivity.currentList.setCompleted(false);
                    }
                    todoActivity.currentList.setLastEditTimestamp(currentItem.getLastEditTimestamp());
                    todoActivity.currentList.setLastEditId(todoActivity.myself.getId());

                    todoActivity.saveList(todoActivity.currentList);
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

    private void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(fiView.getWindowToken(), 0);
        itemRecView.smoothScrollToPosition(10000);
    }


}
