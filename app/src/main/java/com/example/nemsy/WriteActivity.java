package com.example.nemsy;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class WriteActivity extends AppCompatActivity {

    EditText getTitle, getContent;
    Button btnConfirm;
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        // 타이틀바 제거
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        getTitle = (EditText) findViewById(R.id.et_title);
        getContent = (EditText) findViewById(R.id.et_content);
        btnConfirm = (Button) findViewById(R.id.btn_confirm);

        String title = getTitle.getText().toString();
        String content = getContent.getText().toString();

        // Back(<) 버튼 클릭 시 게시물 상세 액티비티로 전환
        btnBack = (ImageButton) findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 확인 버튼 클릭 시 글 등록
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Post(title, content);
                new Thread(() -> {
                    PostListFragment pf = (PostListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_post_list);
                    pf.getData();
                }).start();
                finish();
            }
        });
    }

    public void Post(String title, String content) {
        String responseString = null;
        try {
            OkHttpClient client = new OkHttpClient();

            SharedPreferences prefs = getSharedPreferences("person_info", 0);
            String authorId = prefs.getString("currUID", "");

            String strURL = String.format("http://10.0.2.2:8080/api/board/%s/post", authorId);
            String strBody = String.format("{\"content\" : \"%s\"}","{\"title\" : \"%s\"}", content, title);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), strBody);
            Request.Builder builder = new Request.Builder().url(strURL).post(requestBody);
            builder.addHeader("Content-type", "application/json");
            Request request = builder.build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                ResponseBody body = response.body();
                responseString = body.string();
                body.close();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

}