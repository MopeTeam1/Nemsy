package com.example.nemsy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BillCommentAdapter extends RecyclerView.Adapter<BillCommentAdapter.ViewHolder> {
    ArrayList<BillComment> items = new ArrayList<BillComment>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.comment_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        BillComment item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(BillComment item) {
        items.add(item);
    }

    public void setItems(ArrayList<BillComment> items) {
        this.items = items;
    }

    public BillComment getItem(int position) {
        return items.get(position);
    }

    public BillComment setItem(int position, BillComment item) {
        return items.set(position, item);
    }

    public void clear() {
        items.clear();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nickname, datetime, content;

        public ViewHolder(View itemView) {
            super(itemView);

            nickname = itemView.findViewById(R.id.comment_name);
            datetime = itemView.findViewById(R.id.comment_date);
            content = itemView.findViewById(R.id.comment_content);
        }

        public void setItem(BillComment item) {
            nickname.setText(item.getUserNickname());
            String date = item.getModifiedAt().substring(0, 10);
            String time = item.getModifiedAt().substring(11, 16);
            datetime.setText(date + " " + time);
            content.setText(item.getContent());
        }
    }
}
