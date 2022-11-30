package com.example.nemsy;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nemsy.model.Post;

public class ViewHolderPost extends RecyclerView.ViewHolder{

    TextView title;
    TextView content;
    TextView author;
    TextView createdAt;

    public ViewHolderPost(@NonNull View itemView, final OnPostItemClickListener listener){
        super(itemView);

        title = itemView.findViewById(R.id.postTitle);
        content = itemView.findViewById(R.id.postContent);
        author = itemView.findViewById(R.id.postAuthor);
        createdAt = itemView.findViewById(R.id.postCreatedAt);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                if (listener != null) {
                    listener.onItemClick(ViewHolderPost.this, view, position);
                }
            }
        });
    }

    public void onBind(Post data){
        title.setText(data.getTitle());
        content.setText(data.getContent());
        author.setText(data.getAuthor());
        createdAt.setText(data.getCreatedAt());
    }
}
