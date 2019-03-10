package com.andraganoid.verymuchtodo.Views;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.andraganoid.verymuchtodo.Model.TodoItem;
import com.andraganoid.verymuchtodo.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemAdapter extends RecyclerView.Adapter <ItemAdapter.ItemViewHolder> {

    private List <TodoItem> items;

    public ItemAdapter(List <TodoItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //  LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        FrameLayout itemView = (FrameLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);

     //   ItemViewHolder vh = new ItemViewHolder(itemView);
       // return vh;
        return new ItemViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position) {
        TodoItem it = items.get(position);

        holder.content.setText(it.getContent());
        holder.edited.setText((it.getLastEdit()));
        if (it.isCompleted()) {
            holder.completed.setVisibility(View.VISIBLE);
        } else {
            holder.completed.setVisibility(View.GONE);
        }



//        TodoList tl = lists.get(position);
//        if (tl.isEmergency()) {
//            holder.title.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.colorAccent));
//        }
//        if (tl.isCompleted()) {
//            holder.completed.setVisibility(View.VISIBLE);
//        } else {
//            holder.completed.setVisibility(View.GONE);
//        }
//        holder.title.setText(tl.getTitle());
//        holder.shortDesc.setText(tl.getShortDescription());
//        //  holder.created.setText(tl.getCreatedLine());
//        holder.edited.setText(tl.getLastEdit());
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // open items fragment
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView content;
        //  TextView shortDesc;
        //  TextView created;
        TextView edited;
        ImageView completed;
        //    CheckBox cBox;
        //  Button delBtn;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            content = itemView.findViewById(R.id.item_content);
            //      shortDesc = itemView.findViewById(R.id.lists_short_desc);
            // created = itemView.findViewById(R.id.lists_created);
            edited = itemView.findViewById(R.id.item_edited);
            completed = itemView.findViewById(R.id.item_completed);
            //  cBox = itemView.findViewById(R.id.lists_completed);
            // delBtn = itemView.findViewById(R.id.lists_del_btn);


        }
    }

}

