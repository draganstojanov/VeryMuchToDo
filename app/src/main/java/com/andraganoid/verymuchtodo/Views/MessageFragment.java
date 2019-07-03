package com.andraganoid.verymuchtodo.Views;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.andraganoid.verymuchtodo.model.Message;
import com.andraganoid.verymuchtodo.R;
import com.andraganoid.verymuchtodo.todo.Todo;

import static com.andraganoid.verymuchtodo.todo.Todo.COLLECTION_MESSAGES;
import static com.andraganoid.verymuchtodo.todo.Todo.myself;

public class MessageFragment extends Fragment implements View.OnClickListener {

    private View mView;
    private Todo todoActivity;
    private RecyclerView msgRecView;
    private MsgAdapter msgAdapter;
    private RecyclerView.LayoutManager msgLayMan;
    private EditText msgText;

    public MessageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_msg, container, false);
        msgText = mView.findViewById(R.id.new_msg);

        todoActivity = (Todo) getActivity();
        todoActivity.setTitle("Messages", "");
        msgRecView = mView.findViewById(R.id.msg_rec_view);
        msgAdapter = new MsgAdapter(todoActivity.messagesList);
        msgLayMan = new LinearLayoutManager(getContext());
        msgRecView.setLayoutManager(msgLayMan);
        msgRecView.setAdapter(msgAdapter);
        closeKeyboard();
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                Message message = todoActivity.messagesList.get(viewHolder.getAdapterPosition());
                if (message.getId().equals(myself.getId())) {
                    todoActivity.deleteDocument(COLLECTION_MESSAGES, message.getTitle());
                } else {
                    msgAdapter.notifyDataSetChanged();
                    Toast.makeText(todoActivity, "You are not author of this message!", Toast.LENGTH_LONG).show();
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(msgRecView);

        ((ImageView) mView.findViewById(R.id.msg_send)).setOnClickListener(this);


        return mView;
    }

    public void refreshMsg() {
        msgAdapter.notifyDataSetChanged();
        closeKeyboard();
    }

    @Override
    public void onClick(View v) {

        String sendMsg = msgText.getText().toString();

        if (!sendMsg.isEmpty()) {
            Message m = new Message(sendMsg);
            todoActivity.sendMessage(m);
        }

    }

    private void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mView.getWindowToken(), 0);
        msgRecView.smoothScrollToPosition(10000);
        msgText.setText("");
    }

}
