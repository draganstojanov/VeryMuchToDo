package com.andraganoid.verymuchtodo.Views;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.andraganoid.verymuchtodo.Model.TodoItem;
import com.andraganoid.verymuchtodo.R;
import com.andraganoid.verymuchtodo.VeryOnItemClickListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemAdapter extends RecyclerView.Adapter <ItemAdapter.ItemViewHolder> {

    private List <TodoItem> items;
    private VeryOnItemClickListener click;

    public ItemAdapter(List <TodoItem> items, VeryOnItemClickListener click) {
        this.items = items;
        this.click = click;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        FrameLayout itemView = (FrameLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position) {
        holder.bind(items.get(position), click, position);
    }


//    @Override
//    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position) {
//        TodoItem it = items.get(position);
//
//        holder.content.setText(it.getContent());
//        holder.edited.setText((it.getLastEdit()));
//
//        holder.completed.setVisibility(it.isCompleted()?View.VISIBLE:View.GONE);
////        if (it.isCompleted()) {
////            holder.completed.setVisibility(View.VISIBLE);
////        } else {
////            holder.completed.setVisibility(View.GONE);
////        }
//
//
//    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView content;
        TextView edited;
        ImageView completed;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.item_content);
            edited = itemView.findViewById(R.id.item_edited);
            completed = itemView.findViewById(R.id.item_completed);

        }


        public void bind(final TodoItem ti, final VeryOnItemClickListener click, final int position) {
            content.setText(ti.getContent());
            edited.setText((ti.getLastEdit()));
            completed.setVisibility(ti.isCompleted() ? View.VISIBLE : View.GONE);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    click.changedCompletion(position);

                    return false;
                }
            });

        }

    }
}


//public class ItemAdapter extends RecyclerView.Adapter <ItemAdapter.ItemViewHolder> {
//
//    private List <TodoItem> items;
//
//    public ItemAdapter(List <TodoItem> items) {
//        this.items = items;
//    }
//
//    @NonNull
//    @Override
//    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        //  LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//
//        FrameLayout itemView = (FrameLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
//
//        //   ItemViewHolder vh = new ItemViewHolder(itemView);
//        // return vh;
//        return new ItemViewHolder(itemView);
//    }
//
//
//    @Override
//    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position) {
//        TodoItem it = items.get(position);
//
//        holder.content.setText(it.getContent());
//        holder.edited.setText((it.getLastEdit()));
//
//        holder.completed.setVisibility(it.isCompleted()?View.VISIBLE:View.GONE);
////        if (it.isCompleted()) {
////            holder.completed.setVisibility(View.VISIBLE);
////        } else {
////            holder.completed.setVisibility(View.GONE);
////        }
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return items.size();
//    }
//
//
//    public static class ItemViewHolder extends RecyclerView.ViewHolder {
//        TextView content;
//        //  TextView shortDesc;
//        //  TextView created;
//        TextView edited;
//        ImageView completed;
//        //    CheckBox cBox;
//        //  Button delBtn;
//
//        public ItemViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            content = itemView.findViewById(R.id.item_content);
//            //      shortDesc = itemView.findViewById(R.id.lists_short_desc);
//            // created = itemView.findViewById(R.id.lists_created);
//            edited = itemView.findViewById(R.id.item_edited);
//            completed = itemView.findViewById(R.id.item_completed);
//            //  cBox = itemView.findViewById(R.id.lists_completed);
//            // delBtn = itemView.findViewById(R.id.lists_del_btn);
//
//
//        }
//    }
//
//}


