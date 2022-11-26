package com.example.nemsy;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

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

        // Back(<) 버튼 클릭 시  게시물 상세 액티비티로 전환
        btnBack = (ImageButton) findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}