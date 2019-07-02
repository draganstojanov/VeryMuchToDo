package com.andraganoid.verymuchtodo.Views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.andraganoid.verymuchtodo.model.Message;
import com.andraganoid.verymuchtodo.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MsgAdapter extends RecyclerView.Adapter <MsgAdapter.MsgViewHolder> {

    private List <Message> msgs;

    public MsgAdapter(List <Message> msgs) {
        this.msgs = msgs;
    }

    @NonNull
    @Override
    public MsgViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        FrameLayout itemView = (FrameLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_row, parent, false);

        return new MsgViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MsgViewHolder holder, final int position) {
        Message m = msgs.get(position);

        holder.data.setText(m.getMsgData());
        holder.text.setText((m.getText()));

    }

    @Override
    public int getItemCount() {
        return msgs.size();
    }


    public static class MsgViewHolder extends RecyclerView.ViewHolder {

        TextView data;
        TextView text;


        public MsgViewHolder(@NonNull View itemView) {
            super(itemView);

            data = itemView.findViewById(R.id.msg_data);
            text = itemView.findViewById(R.id.msg_text);

        }
    }

}