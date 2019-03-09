package com.andraganoid.verymuchtodo.Views;


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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.andraganoid.verymuchtodo.Model.TodoItem;
import com.andraganoid.verymuchtodo.Model.TodoList;
import com.andraganoid.verymuchtodo.R;
import com.andraganoid.verymuchtodo.Todo;

import java.util.HashMap;
import java.util.Map;

import static com.andraganoid.verymuchtodo.Todo.COLLECTION_TODOS;

public class ItemFragment extends Fragment implements View.OnClickListener {


    private View fiView;
    private Todo todoActivity;
    private RecyclerView itemRecView;
    private ItemtAdapter itemAdapter;
    private RecyclerView.LayoutManager itemLayMan;
    private ConstraintLayout editView;
    private ConstraintLayout itemView;

    public ItemFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fiView = inflater.inflate(R.layout.fragment_item, container, false);

        itemView = fiView.findViewById(R.id.todo_items_view);
        editView = fiView.findViewById(R.id.new_todo_item_form);

        todoActivity = (Todo) getActivity();
        xxx todoActivity.setTitle("Todo lists", "");
        itemRecView = fiView.findViewById(R.id.lists_rec_view);
        itemAdapter = new ListAdapter(todoActivity.todoList);
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

                switch (swipeDir) {
                    xxx case ItemTouchHelper.LEFT:
                        break;//edit
                    case ItemTouchHelper.RIGHT:
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

    public void refreshLists() {
        itemAdapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.items_fab:

                itemView.setVisibility(View.GONE);
                ((EditText) fiView.findViewById(R.id.new_todo_item_content)).getText().clear();
                editView.setVisibility(View.VISIBLE);
                break;

            case R.id.new_todo_item_save:

                String content = ((EditText) fiView.findViewById(R.id.new_todo_item_content)).getText().toString();

                if (!content.isEmpty()) {

                    TodoItem newTodoItem = new TodoItem(content);
                    itemView.setVisibility(View.VISIBLE);
                    editView.setVisibility(View.GONE);
xxx
                    Map <String, Object> documentData = new HashMap <>();
                    documentData.put("content", content);
                    documentData.put("lastEdit", newTodoItem.getLastEdit());
                    documentData.put("completed", newTodoItem.isCompleted());

                    todoActivity.addDocument(COLLECTION_TODOS, title, documentData);


                }

                break;
        }


    }

}
