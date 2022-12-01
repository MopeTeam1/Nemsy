package com.example.nemsy;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class CommunityDetailActivity extends AppCompatActivity {
    private ImageButton back_button,likeBtn, dislikeBtn, sendBtn;
    private TextView title, writer, writtenDate, content, likeNum, dislikeNum;
    private boolean isLiked, isDisliked;
    private int likeCount;
    private Long postId;
    private EditText comment;
    private NestedScrollView nestedScrollView;

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
        sendBtn = (ImageButton) findViewById(R.id.send_button);

        title = (TextView) findViewById(R.id.title);
        writer = (TextView) findViewById(R.id.writer);
        writtenDate = (TextView) findViewById(R.id.writing_date);
        content = (TextView) findViewById(R.id.writing_content);
        likeNum = (TextView) findViewById(R.id.like_num);
        dislikeNum = (TextView) findViewById(R.id.dislike_num);

        comment = (EditText) findViewById(R.id.comment);

        Intent inIntent = getIntent();


        // 이거 쓰시면 됩니다.
        postId = inIntent.getLongExtra("postId", -1);

        //


        title.setText(inIntent.getStringExtra("title"));
        content.setText(inIntent.getStringExtra("content"));
        writer.setText(inIntent.getStringExtra("author"));
        writtenDate.setText(inIntent.getStringExtra("createdAt"));



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




        // 댓글 전송버튼
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(() -> {
                    // postRequest(comment.getText().toString());
                }).start();
                comment.getText().clear();
            }
        });
    }


    // 댓글 가져오기
//    public void getRequest(){
//        String url = "http://54.250.154.173:8080/api/community/"+title+"/comments";
//        Log.d("게시글 title", title);
//        Log.d("게시글 url", url);
//
//        StringRequest request = new StringRequest(
//                Request.Method.GET,
//                url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.d("응답", response);
//                        // processResponse(response);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("에러", error.getMessage());
//                    }
//                }
//           )
//    }

}