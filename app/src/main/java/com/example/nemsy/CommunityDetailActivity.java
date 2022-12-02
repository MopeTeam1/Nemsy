package com.example.nemsy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.nemsy.model.Post;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class CommunityDetailActivity extends AppCompatActivity {
    private ImageButton back_button,likeBtn, sendBtn;
    private TextView title, writer, writtenDate, content, likeNum;
    private boolean isLiked;
    private String isLikeClicked;
    private int likeCount;
    private Long postId;
    private EditText comment;
    private NestedScrollView nestedScrollView;

    // 댓글 RecyclerView, Adapter
    private RecyclerView recyclerView;
    private PostCommentAdapter adapter;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_detail);
        // 타이틀바 제거
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        back_button = (ImageButton) findViewById(R.id.back_button);
        likeBtn = (ImageButton) findViewById(R.id.like_button);
        // communityBtn = (ImageButton) findViewById(R.id.community_icon);
        sendBtn = (ImageButton) findViewById(R.id.send_button);

        title = (TextView) findViewById(R.id.title);
        writer = (TextView) findViewById(R.id.writer);
        writtenDate = (TextView) findViewById(R.id.writing_date);
        content = (TextView) findViewById(R.id.writing_content);
        likeNum = (TextView) findViewById(R.id.like_num);

        comment = (EditText) findViewById(R.id.comment);

        Intent inIntent = getIntent();
        postId = inIntent.getLongExtra("postId", -1);

        title.setText(inIntent.getStringExtra("title"));
        content.setText(inIntent.getStringExtra("content"));
        writer.setText(inIntent.getStringExtra("author"));
        writtenDate.setText(inIntent.getStringExtra("createdAt"));

        // 댓글 RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.comments_recyclerView);

        // Adapter, LayoutManager 연결
        adapter = new PostCommentAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        new Thread(() -> {
            getLike();
            getRequest();
        }).start();

        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                like_button.setVisibility(View.INVISIBLE);
//                like_button2.setVisibility(View.VISIBLE);
                Log.d("postLike:", "isLikeClicked" + isLikeClicked);
                if (isLikeClicked.equals("false")) {
                    new Thread(() -> {
                        postLike();
                        isLikeClicked="true";
                    }).start();
                } else{
                    new Thread(() -> {
                        deleteLike();
                        isLikeClicked="false";
                    }).start();
                }
            }
        });

        // 뒤로가기
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {finish();}
        });

        // 댓글 전송버튼
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cmt = comment.getText().toString().trim();
                if (cmt.equals(""))
                    return;
                new Thread(() -> {
                    postRequest(cmt);
                }).start();
                comment.getText().clear();
            }
        });

        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                like_button.setVisibility(View.INVISIBLE);
//                like_button2.setVisibility(View.VISIBLE);
                Log.d("postLike:", "isLikeClicked" + isLikeClicked);
                if (isLikeClicked.equals("false")) {
                    likeBtn.setSelected(true);
                    new Thread(() -> {
                        postLike();
                        isLikeClicked="true";
                    }).start();
                } else{
                    likeBtn.setSelected(false);
                    new Thread(() -> {
                        deleteLike();
                        isLikeClicked="false";
                    }).start();
                }
            }
        });

    }
    private void getLike(){
        SharedPreferences pref = getSharedPreferences("person_info", 0);
        String userId = pref.getString("currUID", "");
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
                Log.d("getLike","isLikeClicked" + isLikeClicked);
                body.close();
                setLike();
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
            String strURL = String.format("http://54.250.154.173:8080/api/board/%s/%s/likes", postId, userId);
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
            String strURL = String.format("http://54.250.154.173:8080/api/board/%s/%s/likes", postId, userId);
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

    // 댓글 작성하기
    public void postRequest(String content){
        try{
            OkHttpClient client = new OkHttpClient();

            SharedPreferences preferences = getSharedPreferences("person_info", 0);
            String userId = preferences.getString("currUID", "");

            String strUrl = "http://54.250.154.173:8080/api/board/"+postId+"/"+userId+"/comment";
            String strBody = String.format("{\"content\" : \"%s\"}", content);

            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), strBody);

            okhttp3.Request.Builder builder = new okhttp3.Request.Builder().url(strUrl).post(requestBody);
            builder.addHeader("Content-type","application/json");
            okhttp3.Request request = builder.build();
            okhttp3.Response response = client.newCall(request).execute();

            if(response.isSuccessful()){
                getRequest();
                nestedScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        nestedScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    // 댓글 가져오기
    public void getRequest(){
        String responseString = null;
        try{
            OkHttpClient client = new OkHttpClient();
            String strUrl = "http://54.250.154.173:8080/api/board/"+postId+"/comments";
             Log.d("게시글 id", String.valueOf(postId));
             Log.d("게시글 url", strUrl);
            Request.Builder builder = new Request.Builder().url(strUrl).get();
            builder.addHeader("Content-type", "application/json");
            Request request = builder.build();
            Response response = client.newCall(request).execute();
            Log.d("response ", response.toString());
            if (response.isSuccessful()) {
                Log.d("http ", "success 11");

                ResponseBody body = response.body();
                Log.d("http ", "success 12");

                responseString = body.string();
                body.close();
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        try {
            JSONArray jsonArray = new JSONArray(responseString);
            PostComment data;
            adapter.clear();
            Log.d("http ", "success 13");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Long id = jsonObject.getLong("id");
                String content = jsonObject.getString("content");
                String userId = jsonObject.getString("userId");
                String userNickname = jsonObject.getString("userNickname");
                Long postId = jsonObject.getLong("postId");
                String createdAt = jsonObject.getString("createdAt");
                String modifiedAt = jsonObject.getString("modifiedAt");
                Log.d("http ", "success 14");

                data = new PostComment(id,content, userId, userNickname, postId, createdAt, modifiedAt );
                adapter.addItem(data);
                Log.d("http ", "success 15");

                setData();

                // Log.d("jsonObject :", jsonObject.toString());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setData() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
                Log.d("http ", "success 16");

            }
        });
    }

    public void setLike(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        Log.d("debug","isLikeClicked"+isLikeClicked);
                        if (isLikeClicked.equals("false")) {
                            likeBtn.setSelected(false);
                        }else {
                            likeBtn.setSelected(true);
                        }
                    }
                });
            }
        }).start();
    }

}