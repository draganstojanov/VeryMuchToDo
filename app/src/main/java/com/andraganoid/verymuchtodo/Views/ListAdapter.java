package com.andraganoid.verymuchtodo.Views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.andraganoid.verymuchtodo.Model.Message;
import com.andraganoid.verymuchtodo.Model.TodoList;
import com.andraganoid.verymuchtodo.R;
import com.andraganoid.verymuchtodo.VeryOnItemClickListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class ListAdapter extends RecyclerView.Adapter <ListAdapter.ListsViewHolder> {

    private List <TodoList> lists;
    private VeryOnItemClickListener click;

    public ListAdapter(List <TodoList> lists, VeryOnItemClickListener click) {
        this.lists = lists;
        this.click = click;
    }


    @NonNull
    @Override
    public ListAdapter.ListsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        FrameLayout itemView = (FrameLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new ListsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.ListsViewHolder holder, final int position) {
        holder.bind(lists.get(position), click);
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }


    public static class ListsViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView shortDesc;
        TextView edited;
        ImageView completed;

        public ListsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.lists_title);
            shortDesc = itemView.findViewById(R.id.lists_short_desc);
            edited = itemView.findViewById(R.id.lists_edited);
            completed = itemView.findViewById(R.id.list_completed);
        }


        public void bind(final TodoList tl, final VeryOnItemClickListener click) {

            if (tl.isEmergency()) {
                title.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.colorAccent));
            }

            completed.setVisibility(tl.isCompleted()?View.VISIBLE:View.GONE);

            title.setText(tl.getTitle());
            shortDesc.setText(tl.getShortDescription());
            edited.setText(tl.getLastEdit());

            System.out.println("ADAPTER: "+tl.getLastEdit());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    click.listChoosed(tl);
                }
            });

        }
    }

}

