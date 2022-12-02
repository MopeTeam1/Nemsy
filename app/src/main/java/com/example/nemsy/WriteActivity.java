package com.example.nemsy;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
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

        // ** 앞에 ** 달린 주석은 읽고 지워주시면 될 것 같습니다.
        // ** 아래 코드가 onCreate 메소드에 있으면,
        // 그러면 초기에 activity를 실행하자마자 .getText().toString()을 해서
        // 가져온 빈 값이 title과 content에 저장되니까
        // 글쓰기 버튼을 클릭하였을 때 값을 가져오게 해야합니다.
        // String title = getTitle.getText().toString();
        // String content = getContent.getText().toString();

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
                // ** 이렇게 수정하면 글쓰기 버튼 클릭시에 값을 가져오겠죠?
                String title = getTitle.getText().toString();
                String content = getContent.getText().toString();

                Log.d("title :", title);
                Log.d("content :", content);

                // ** http 통신하는 코드는 새로운 스레드에서 작동해야돼서 아래처럼 수정하였습니다.
                new Thread(() -> {
                    Post(title, content);
                }).start();

                // ** 이 부분은 제가 드린 블로그 보고 다른 방식으로 try 해보시고
                // 1시간 넘게 투자하지는 마세요.. ㅋㅋ
//                new Thread(() -> {
//                    PostListFragment pf = (PostListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_post_list);
//                    pf.getData();
//                }).start();
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

            // ** ip 54.250.154.173 로 수정하였습니다.
            String strURL = String.format("http://54.250.154.173:8080/api/board/%s/post", authorId);
            // ** 기존 코드대로 하면 "{"content" : "%s"}","{"title" : "%s"}" 이렇게 나와서
            // 다음처럼 수정했습니다. "{"content" : "%s", "title" : "%s"}"
            // json form이 보통 하나의 중괄호 안에 key - value 형태로 있는거라 위처럼 나와야합니다
            // {
            //   "key" : "value",
            //   "key2" : value2"
            // }

            String strBody = String.format("{\"content\" : \"%s\", \"title\" : \"%s\"}", content, title);

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