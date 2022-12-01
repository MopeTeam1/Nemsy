package com.example.nemsy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostCommentAdapter extends RecyclerView.Adapter<PostCommentAdapter.ViewHolder> {
    ArrayList<PostComment> items = new ArrayList<PostComment>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.comment_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        PostComment item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(PostComment item) {
        items.add(item);
    }

    public void setItems(ArrayList<PostComment> items) {
        this.items = items;
    }

    public PostComment getItem(int position) {
        return items.get(position);
    }

    public PostComment setItem(int position, PostComment item) {
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

        public void setItem(PostComment item) {
            nickname.setText(item.getUserNickname());
            datetime.setText(item.getModifiedAt());
            content.setText(item.getContent());
        }
    }
}