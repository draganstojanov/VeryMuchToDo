package com.andraganoid.verymuchtodo.Views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.andraganoid.verymuchtodo.BR;
import com.andraganoid.verymuchtodo.Model.TodoList;
import com.andraganoid.verymuchtodo.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;


//public static class HSTViewHolder extends RecyclerView.ViewHolder {
//    private TextView pos;
//    private TextView result;
//    private TextView date;
//
//    public HSTViewHolder(View itemView) {
//        super(itemView);
//        pos = itemView.findViewById(R.id.hs_pos);
//        result = itemView.findViewById(R.id.hs_result);
//        date = itemView.findViewById(R.id.hs_date);
//
//    }
//}
//
//    public HighScoresTableAdapter(List <Score> sList, long lastScoreId) {
//        this.sList = sList;
//        this.lastScoreId = lastScoreId;
//    }
//
//
//    @Override
//    public HighScoresTableAdapter.HSTViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//        ConstraintLayout itemView = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.highscore_row, parent, false);
//
//        HSTViewHolder vh = new HSTViewHolder(itemView);
//        return vh;
//    }
//
//
//    @Override
//    public void onBindViewHolder(HSTViewHolder holder, int position) {
//
//        holder.pos.setText(String.valueOf(position + 1) + ".");
//
//        score = sList.get(position);
//        switch (score.getScoreType()) {
//
//            case Score.SCORE_TYPE_POINTS:
//                res = String.valueOf((score.getScorePoints()));
//                break;
//
//            case Score.SCORE_TYPE_TIME:
//                res = score.getScoreTimeString();
//                break;
//        }
//
//        holder.result.setText(res);
//        holder.date.setText(score.getScoreDate());
//        System.out.println("LAST SCORE: " + lastScoreId + "***" + score.getId() + "***" + score.getScorePoints());
//        if ((int) lastScoreId == score.getId()) {
//            int c = holder.itemView.getContext().getResources().getColor(R.color.text1);
//            holder.pos.setTextColor(c);
//            holder.result.setTextColor(c);
//            holder.date.setTextColor(c);
//        } else {
//            int cc = holder.itemView.getContext().getResources().getColor(R.color.base);
//            holder.pos.setTextColor(cc);
//            holder.result.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.info));
//            holder.date.setTextColor(cc);
//        }
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return sList.size();
//    }
//}


public class ListsAdapter extends RecyclerView.Adapter <ListsAdapter.ListsViewHolder> {

    private List <TodoList> lists;

    public ListsAdapter(List <TodoList> lists) {
        this.lists = lists;
    }

    @NonNull
    @Override
    public ListsAdapter.ListsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        FrameLayout itemView = (FrameLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.lists_row, parent, false);

        ListsViewHolder vh = new ListsViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ListsAdapter.ListsViewHolder holder, int position) {
        TodoList tl = lists.get(position);
        holder.title.setText(tl.getName());
        holder.shortDesc.setText(tl.getShortDescription());
        holder.created.setText(tl.getCreatedLine());
        holder.edited.setText(tl.getEditedLine());

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }


    public static class ListsViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView shortDesc;
        TextView created;
        TextView edited;
    //    CheckBox cBox;
      //  Button delBtn;

        public ListsViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.lists_title);
            shortDesc = itemView.findViewById(R.id.lists_short_desc);
            created = itemView.findViewById(R.id.lists_created);
            edited = itemView.findViewById(R.id.lists_edited);
          //  cBox = itemView.findViewById(R.id.lists_completed);
           // delBtn = itemView.findViewById(R.id.lists_del_btn);


        }
    }

}
