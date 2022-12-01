package com.example.nemsy;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class CommunityDetailActivity extends AppCompatActivity {
    private ImageButton back_button,like_button, sendBtn;
    private TextView title, writer, writtenDate, content, likeNum, dislikeNum;
    private boolean isLiked, isDisliked;
    private Long postId;
    private String isLikeClicked;
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
        like_button = (ImageButton) findViewById(R.id.like_button);
        // communityBtn = (ImageButton) findViewById(R.id.community_icon);
        sendBtn = (ImageButton) findViewById(R.id.send_button);

        title = (TextView) findViewById(R.id.title);
        writer = (TextView) findViewById(R.id.writer);
        writtenDate = (TextView) findViewById(R.id.writing_date);
        content = (TextView) findViewById(R.id.writing_content);
        likeNum = (TextView) findViewById(R.id.like_num);
        dislikeNum = (TextView) findViewById(R.id.dislike_num);

        Intent inIntent = getIntent();
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

    }
    private void getLike(){
        SharedPreferences pref = getSharedPreferences("person_info", 0);
        String userId = pref.getString("currUID", "");
        postId = inIntent.getLongExtra("postId", -1);
        try{
            OkHttpClient client = new OkHttpClient();
            String strURL = String.format("http://54.250.154.173:8080/api/board/%s/%s/likes", postId, userId);
            okhttp3.Request.Builder builder = new okhttp3.Request.Builder().url(strURL).get();
            Log.d("getLike","postId: " + postId);
            Log.d("getLike","strURL" + strURL);
            builder.addHeader("Content-type", "application/json");
            okhttp3.Request request = builder.build();
            Log.d("getLike","request: " +request);
            okhttp3.Response response = client.newCall(request).execute();
            Log.d("getLike","response: " +response);
            if(response.isSuccessful()) {
                ResponseBody body = response.body();
                isLikeClicked = body.string();
                Log.d("getLike","responseString" + isLikeClicked);
                body.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void postLike(){
        SharedPreferences pref = getSharedPreferences("person_info", 0);
        String userId = pref.getString("currUID", "");
        try{
            OkHttpClient client = new OkHttpClient();
            String strURL = String.format("http://54.250.154.173:8080/api/bill/%s/%s/likes", billId,userId);
            String strBody = "{}";
            Log.d("postLike","strURL" + strURL);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), strBody);
            okhttp3.Request.Builder builder = new okhttp3.Request.Builder().url(strURL).post(requestBody);
            builder.addHeader("Content-type", "application/json");
            okhttp3.Request request = builder.build();
            Log.d("postLike","request: " +request);
            okhttp3.Response response = client.newCall(request).execute();
            Log.d("postLike","response: " +response);
            if(response.isSuccessful()) {
                Log.d("postLike", " response: success");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteLike(){
        SharedPreferences pref = getSharedPreferences("person_info", 0);
        String userId = pref.getString("currUID", "");
        try{
            OkHttpClient client = new OkHttpClient();
            String strURL = String.format("http://54.250.154.173:8080/api/bill/%s/%s/likes", billId,userId);
            String strBody = "{}";
            Log.d("deleteLike","strURL" + strURL);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), strBody);
            okhttp3.Request.Builder builder = new okhttp3.Request.Builder().url(strURL).delete();
            builder.addHeader("Content-type", "application/json");
            okhttp3.Request request = builder.build();
            Log.d("deleteLike","request: " +request);
            okhttp3.Response response = client.newCall(request).execute();
            Log.d("deleteLike","response: " +response);
            if(response.isSuccessful()) {
                Log.d("deleteLike", " response: success");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}