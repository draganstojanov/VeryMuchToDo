package com.andraganoid.verymuchtodo.Views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.andraganoid.verymuchtodo.Model.User;
import com.andraganoid.verymuchtodo.R;
import com.andraganoid.verymuchtodo.VeryOnItemClickListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserAdapter  extends RecyclerView.Adapter <UserAdapter.UserViewHolder> {

    private List<User> users;
    private VeryOnItemClickListener click;

    public UserAdapter(List <User> users, VeryOnItemClickListener click) {
        this.users = users;
        this.click = click;
    }


    @NonNull
    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        FrameLayout itemView = (FrameLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder, final int position) {
        holder.bind(users.get(position), click);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView name;


        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.user_name);
        }


        public void bind(final User us, final VeryOnItemClickListener click) {

            name.setText(us.getName());


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  //  click.something();
                }
            });

        }
    }

}
