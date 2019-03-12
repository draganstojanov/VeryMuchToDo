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
import android.widget.EditText;
import android.widget.Toast;

import com.andraganoid.verymuchtodo.Model.Message;
import com.andraganoid.verymuchtodo.Model.TodoList;
import com.andraganoid.verymuchtodo.R;
import com.andraganoid.verymuchtodo.Todo;

import static com.andraganoid.verymuchtodo.Todo.COLLECTION_MESSAGES;
import static com.andraganoid.verymuchtodo.Todo.COLLECTION_TODOS;
import static com.andraganoid.verymuchtodo.Todo.myself;

public class MsgFragment extends Fragment implements View.OnClickListener {

    private View mView;
    private Todo todoActivity;
    private RecyclerView msgRecView;
    private MsgAdapter msgAdapter;
    private RecyclerView.LayoutManager msgLayMan;

    public MsgFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_list, container, false);
        todoActivity = (Todo) getActivity();
        todoActivity.setTitle("Messages", "");
        msgRecView = mView.findViewById(R.id.msg_rec_view);
        msgAdapter = new MsgAdapter(todoActivity.messagesList);
        msgLayMan = new LinearLayoutManager(getContext());
        msgRecView.setLayoutManager(msgLayMan);
        msgRecView.setAdapter(msgAdapter);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                Message message = todoActivity.messagesList.get(viewHolder.getAdapterPosition());
                if (message.getFrom().equals(myself.getId())) {
                    todoActivity.deleteDocument(COLLECTION_MESSAGES, message.getTitle());
                } else {
                    msgAdapter.notifyDataSetChanged();
                    Toast.makeText(todoActivity, "You are not author of this message!", Toast.LENGTH_LONG).show();
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);

        itemTouchHelper.attachToRecyclerView(msgRecView);

        mView.findViewById(R.id.lists_fab).setOnClickListener(this);


        return mView;
    }

    @Override
    public void onClick(View v) {

        String sendMsg=((EditText)mView.findViewById(R.id.new_msg)).getText().toString();

        if(!sendMsg.isEmpty()){
            Message m=new Message(sendMsg);

        }

    }
}
