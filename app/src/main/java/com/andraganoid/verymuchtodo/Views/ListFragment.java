//package com.andraganoid.verymuchtodo.Views;
//
//
//import android.content.Context;
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.constraintlayout.widget.ConstraintLayout;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.ItemTouchHelper;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.andraganoid.verymuchtodo.model.TodoList;
//import com.andraganoid.verymuchtodo.R;
//import com.andraganoid.verymuchtodo.todo.Todo;
//
//
//import static com.andraganoid.verymuchtodo.todo.Todo.COLLECTION_TODOS;
//
//
//public class ListFragment extends Fragment implements View.OnClickListener {
//
//    private View flView;
//    private Todo todoActivity;
//    private RecyclerView listsRecView;
//    private ListAdapter listsAdapter;
//    private RecyclerView.LayoutManager listsLayMan;
//    private ConstraintLayout createView;
//    private ConstraintLayout listsView;
//
//    public ListFragment() {
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        flView = inflater.inflate(R.layout.fragment_list, container, false);
//
//
//        todoActivity = (Todo) getActivity();
//        listsView = flView.findViewById(R.id.todo_lists_view);
//        createView = flView.findViewById(R.id.new_todo_list_form);
//        todoActivity.setTitle("Todo lists", "");
//        listsRecView = flView.findViewById(R.id.lists_rec_view);
//        listsAdapter = new ListAdapter(todoActivity.todoList, todoActivity);
//        listsLayMan = new LinearLayoutManager(getContext());
//        listsRecView.setLayoutManager(listsLayMan);
//        listsRecView.setAdapter(listsAdapter);
//        closeKeyboard();
//        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
//
//
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
//                TodoList tl = todoActivity.todoList.get(viewHolder.getAdapterPosition());
//                if (tl.isCompleted()) {
//                    todoActivity.deleteDocument(COLLECTION_TODOS, tl.getTitle());
//                } else {
//                    listsAdapter.notifyDataSetChanged();
//                    Toast.makeText(todoActivity, "List is not completed!.", Toast.LENGTH_LONG).show();
//                }
//            }
//        };
//
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
//
//        itemTouchHelper.attachToRecyclerView(listsRecView);
//
//        flView.findViewById(R.id.lists_fab).setOnClickListener(this);
//        flView.findViewById(R.id.new_todo_list_create).setOnClickListener(this);
//
//        return flView;
//    }
//
//    public void refreshLists() {
//        listsAdapter.notifyDataSetChanged();
//        closeKeyboard();
//    }
//
//
//    @Override
//    public void onClick(View v) {
//
//        switch (v.getId()) {
//            case R.id.lists_fab:
//                listsView.setVisibility(View.GONE);
//                ((EditText) flView.findViewById(R.id.new_todo_list_title)).getText().clear();
//                ((EditText) flView.findViewById(R.id.new_todo_list_desc)).getText().clear();
//                createView.setVisibility(View.VISIBLE);
//                break;
//
//            case R.id.new_todo_list_create:
//
//                String title = ((EditText) flView.findViewById(R.id.new_todo_list_title)).getText().toString();
//
//                if (!title.isEmpty()) {
//
//                    if (title.length() < 24) {
//                        String sDesc = ((EditText) flView.findViewById(R.id.new_todo_list_desc)).getText().toString();
//
//                        if (sDesc.length() < 100) {
//
//                            CheckBox cBox = flView.findViewById(R.id.new_todo_list_emergency);
//
//                            listsView.setVisibility(View.VISIBLE);
//                            createView.setVisibility(View.GONE);
//
//                            todoActivity.saveList(new TodoList(title, sDesc, cBox.isChecked()));
//
//                        } else {
//                            Toast.makeText(todoActivity, "Description is too long", Toast.LENGTH_SHORT).show();
//                        }
//
//                    } else {
//                        Toast.makeText(todoActivity, "Title is too long", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                break;
//        }
//
//    }
//
//    private void closeKeyboard() {
//        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(flView.getWindowToken(), 0);
//    }
//}
