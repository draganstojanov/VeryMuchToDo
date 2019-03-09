package com.andraganoid.verymuchtodo.Views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.andraganoid.verymuchtodo.Model.TodoList;
import com.andraganoid.verymuchtodo.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class ListAdapter extends RecyclerView.Adapter <ListAdapter.ListsViewHolder> {

    private List <TodoList> lists;

    public ListAdapter(List <TodoList> lists) {
        this.lists = lists;
    }

    @NonNull
    @Override
    public ListAdapter.ListsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //  LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        FrameLayout itemView = (FrameLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);

        ListsViewHolder vh = new ListsViewHolder(itemView);
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ListsViewHolder holder, final int position) {
        TodoList tl = lists.get(position);
        if (tl.isEmergency()) {
            holder.title.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.colorAccent));
        }
        if (tl.isCompleted()) {
            holder.completed.setVisibility(View.VISIBLE);
        } else {
            holder.completed.setVisibility(View.GONE);
        }
        holder.title.setText(tl.getTitle());
        holder.shortDesc.setText(tl.getShortDescription());
      //  holder.created.setText(tl.getCreatedLine());
        holder.edited.setText(tl.getLastEdit());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             // open items fragment

            }
        });

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }


    public static class ListsViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView shortDesc;
      //  TextView created;
        TextView edited;
        ImageView completed;
        //    CheckBox cBox;
        //  Button delBtn;

        public ListsViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.lists_title);
            shortDesc = itemView.findViewById(R.id.lists_short_desc);
           // created = itemView.findViewById(R.id.lists_created);
            edited = itemView.findViewById(R.id.lists_edited);
            completed = itemView.findViewById(R.id.list_completed);
            //  cBox = itemView.findViewById(R.id.lists_completed);
            // delBtn = itemView.findViewById(R.id.lists_del_btn);



        }
    }

}
