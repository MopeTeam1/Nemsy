package com.example.nemsy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CommunityDetailFragment extends Fragment {
    private ImageButton backBtn, billBtn, mypageBtn, communityBtn, likeBtn, dislikeBtn;
    private TextView title, writer, writtenDate, content, likeNum, dislikeNum;
    private boolean isLiked, isDisliked;

    // 댓글 RecyclerView, Adapter
    private RecyclerView recyclerView;
    private CommentAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_community_detail,
                container,
                false);
        backBtn = (ImageButton) view.findViewById(R.id.back_button);
        likeBtn = (ImageButton) view.findViewById(R.id.like_button);
        dislikeBtn = (ImageButton) view.findViewById(R.id.dislike_button);
        billBtn = (ImageButton) view.findViewById(R.id.bill_icon);
        mypageBtn = (ImageButton) view.findViewById(R.id.mypage_icon);
        // communityBtn = (ImageButton) findViewById(R.id.community_icon);

        title = (TextView) view.findViewById(R.id.title);
        writer = (TextView) view.findViewById(R.id.writer);
        writtenDate = (TextView) view.findViewById(R.id.writing_date);
        content = (TextView) view.findViewById(R.id.writing_content);
        likeNum = (TextView) view.findViewById(R.id.like_num);
        dislikeNum = (TextView) view.findViewById(R.id.dislike_num);

        // 댓글 RecyclerView
        recyclerView = (RecyclerView) view.findViewById(R.id.comments_recyclerView);

        // Adapter, LayoutManager 연결
        adapter = new CommentAdapter();
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        /*
        // 뒤로가기
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {finish();}
        });

        // navigation
        billBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(getApplicationContext(), BottomNavActivity.class);
                startActivity(intent);
            }
        });
        mypageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(getApplicationContext(), MypageActivity.class);
                startActivity(intent);
            }
        });

        communityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(getApplicationContext(), BottomNavActivity.class);
                startActivity(intent);
            }
        });

         */

        return view;
    }
}
