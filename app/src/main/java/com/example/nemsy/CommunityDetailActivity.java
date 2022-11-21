package com.example.nemsy;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class CommunityDetailActivity extends AppCompatActivity {
    private ImageButton back_button, bill_icon, mypage_icon, community_icon, likeBtn, dislikeBtn;
    private TextView title, writer, writtenDate, content, likeNum, dislikeNum;
    private boolean isLiked, isDisliked;


    // 댓글 RecyclerView, Adapter
    private RecyclerView recyclerView;
    private CommentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_detail);
        // 타이틀바 제거
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        back_button = (ImageButton) findViewById(R.id.back_button);
        likeBtn = (ImageButton) findViewById(R.id.like_button);
        dislikeBtn = (ImageButton) findViewById(R.id.dislike_button);
        bill_icon = (ImageButton) findViewById(R.id.bill_icon);
        mypage_icon = (ImageButton) findViewById(R.id.mypage_icon);
        // communityBtn = (ImageButton) findViewById(R.id.community_icon);

        title = (TextView) findViewById(R.id.title);
        writer = (TextView) findViewById(R.id.writer);
        writtenDate = (TextView) findViewById(R.id.writing_date);
        content = (TextView) findViewById(R.id.writing_content);
        likeNum = (TextView) findViewById(R.id.like_num);
        dislikeNum = (TextView) findViewById(R.id.dislike_num);

        // 댓글 RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.comments_recyclerView);

        // Adapter, LayoutManager 연결
        adapter = new CommentAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        // 뒤로가기
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {finish();}
        });

        // navigation
        bill_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(getApplicationContext(), BottomNavActivity.class);
                startActivity(intent);
            }
        });
        mypage_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(getApplicationContext(), MypageActivity.class);
                startActivity(intent);
            }
        });

        /*
        communityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(getApplicationContext(), BottomNavActivity.class);
                startActivity(intent);
            }
        });

         */

    }
}