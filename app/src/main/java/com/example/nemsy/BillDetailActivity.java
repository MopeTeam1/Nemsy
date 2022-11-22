package com.example.nemsy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import okhttp3.*;

public class BillDetailActivity extends AppCompatActivity {
    private ImageButton back_button, bill_icon, mypage_icon, likeBtn, dislikeBtn;
    private TextView bill_name, propose, all_propose, age, propose_date, status, bill_content, likeNum, dislikeNum;
    private String billId, userId;
    private boolean isLiked, isDisliked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);

        back_button = (ImageButton) findViewById(R.id.back_button);
        bill_icon = (ImageButton) findViewById(R.id.bill_icon);
        mypage_icon = (ImageButton) findViewById(R.id.mypage_icon);
        likeBtn = (ImageButton) findViewById(R.id.like_button);
        dislikeBtn = (ImageButton) findViewById(R.id.dislike_button);
        bill_name = (TextView) findViewById(R.id.bill_name);
        propose = (TextView) findViewById(R.id.propose);
        all_propose = (TextView) findViewById(R.id.all_propose);
        age = (TextView) findViewById(R.id.age);
        propose_date = (TextView) findViewById(R.id.propose_date);
        status = (TextView) findViewById(R.id.status);
        bill_content = (TextView) findViewById(R.id.bill_content);
        likeNum = (TextView) findViewById(R.id.like_num);
        dislikeNum = (TextView) findViewById(R.id.dislike_num);
        RecyclerView recyclerView = findViewById(R.id.comments_recyclerView);

        Intent inIntent = getIntent();
        billId = inIntent.getStringExtra("BILL_ID");
        propose.setText(inIntent.getStringExtra("RST_PROPOSER"));
        all_propose.setText(inIntent.getStringExtra("PUBL_PROPOSER"));
        age.setText(inIntent.getStringExtra("AGE"));
        propose_date.setText(inIntent.getStringExtra("PROPOSE_DT"));
        status.setText(inIntent.getStringExtra("PROC_RESULT"));
        if(inIntent.getStringExtra("PROC_RESULT") == null) {
            status.setText("접수");
        }

        userId = "dfinwqeofnwoi1111";
        isLiked = false;
        isDisliked = false;

        // 웹 크롤링
        String detailLink = inIntent.getStringExtra("DETAIL_LINK");
        final Bundle bundle = new Bundle();
        new Thread() {
            @Override
            public void run() {
                Document doc = null;
                try {
                    doc = Jsoup.connect(detailLink).get();
                    Elements title = doc.select(".titCont");
                    int idxNo = title.text().indexOf("]");
                    int idxproposer = title.text().indexOf("(");
                    String billName = title.text().substring(idxNo+1, idxproposer);
                    bundle.putString("title", billName);

                    Elements content = doc.select("#summaryContentDiv");
                    String contents = content.html().replace("<br> ", "");
                    bundle.putString("content", contents);

                    Message msg = handler.obtainMessage();
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        // recyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        CommentAdapter adapter = new CommentAdapter();
        recyclerView.setAdapter(adapter);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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
        new Thread(() -> {
            isLiked = getIsLiked("likes");
            isDisliked = getIsLiked("likes");
        }).start();;
        System.out.println("-----------------------mid---=-=-=-=-==--=-=-=-=-=");

//        likeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int likeCnt;
//                System.out.println("-----------------------clickedLike---=-=-=-=-==--=-=-=-=-=");
//                if (isLiked)
//                    likeCnt = deleteLike("likes");
//                else {
//                    if (isDisliked) {
//                        int dislikeCnt = deleteLike("dislikes");
//                        dislikeNum.setText(dislikeCnt);
//                    }
//                    likeCnt = postLike("likes");
//                    System.out.println(likeCnt);
//                    System.out.println("-----------------------postLikeEnd---=-=-=-=-==--=-=-=-=-=");
//                }
//                likeNum.setText(likeCnt);
//            }
//        });
//
//        dislikeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int dislikeCnt;
//                if (isDisliked)
//                    dislikeCnt = deleteLike("dislikes");
//                else {
//                    if (isLiked) {
//                        int likeCnt = deleteLike("likes");
//                        dislikeNum.setText(likeCnt);
//                    }
//                    dislikeCnt = postLike("dislikes");
//
//                }
//                likeNum.setText(dislikeCnt);
//            }
//        });

    }

    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            Bundle bundle = msg.getData();
            bill_name.setText(bundle.getString("title"));
            bill_content.setText(bundle.getString("content"));
        }
    };

    private boolean getIsLiked(String type) {
        try {
            String url = String.format("http://10.0.2.2:8080/api/bill/%s/%s/%s", billId, userId, type);
            System.out.println(url);

            // OkHttp 클라이언트 객체 생성
            OkHttpClient client = new OkHttpClient();

            // GET 요청 객체 생성
            Request.Builder builder = new Request.Builder().url(url).get();

            builder.addHeader("Content-type", "application/json");

            Request request = builder.build();
            System.out.println("Error pos 4");

            // OkHttp 클라이언트로 GET 요청 객체 전송
            Response response = client.newCall(request).execute();
            System.out.println("Error pos 5");

            if (response.isSuccessful()) {
                // 응답 받아서 처리
                System.out.println("Error pos 6");

                ResponseBody body = response.body();
                if (body != null) {
                    System.out.println("Response:" + body.string());
                    if (body.toString().equals("true"))
                        return true;
                }
                else
                    System.out.println("Error Occurred:" + body.string());
            }
            else
                System.err.println("Error Occurred");

            return false;
        } catch(Exception e) {
            System.out.println("Error pos 7");

            e.printStackTrace();
        }

        return false;
    }

    private int postLike(String type) {
        System.out.println("-----------------------postLike---=-=-=-=-==--=-=-=-=-=");
        try{
            //인스턴스를 생성합니다.
            System.out.println("error pos 1");
            OkHttpClient client = new OkHttpClient();
            System.out.println("error pos 2");

            //URL
            String strURL = String.format("http://10.0.2.2:8080/api/bill/%s/%s/%s", billId, userId, type);
            System.out.println("error pos 3");

            //parameter를 JSON object로 전달합니다
            String strBody = "";
            System.out.println("error pos 4");

            //POST요청을 위한 request body를 구성합니다.
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), strBody);
            System.out.println("error pos 5");

            //POST요청을 위한 build작업
            Request.Builder builder = new Request.Builder().url(strURL).post(requestBody);
            System.out.println("error pos 6");


            //json을 주고받는 경우, 헤더에 추가
            builder.addHeader("Content-type", "application/json");

            //request 객체를 생성
            Request request = builder.build();

            //request를 요청하고 그 결과를 response 객체로 응답을 받음.
            Response response = client.newCall(request).execute();
            //응답처리
            if(response.isSuccessful()){
                ResponseBody body = response.body();
                System.out.println("[responseBody]:"+ body.string());
                int cnt = Integer.parseInt(body.string());
                body.close();
                return cnt;
            }
        }catch(Exception e){
            System.out.println("==================start=====================");
            e.printStackTrace();
            System.out.println("==================end=====================");

        }
        return -1;
    }

    private int deleteLike(String type) {
        System.out.println("-----------------------DeleteLike---=-=-=-=-==--=-=-=-=-=");
        try{
            //인스턴스를 생성합니다.
            OkHttpClient client = new OkHttpClient();
            //URL
            String strURL = String.format("http://10.0.2.2:8080/api/bill/%s/%s/%s", billId, userId, type);

            //POST요청을 위한 build작업
            Request.Builder builder = new Request.Builder().url(strURL).delete();

            //json을 주고받는 경우, 헤더에 추가
            builder.addHeader("Content-type", "application/json");

            //request 객체를 생성
            Request request = builder.build();

            //request를 요청하고 그 결과를 response 객체로 응답을 받음.
            Response response = client.newCall(request).execute();
            //응답처리
            if(response.isSuccessful()){
                ResponseBody body = response.body();
                System.out.println("[responseBody]:"+ body.string());
                int cnt = Integer.parseInt(body.string());
                body.close();
                return cnt;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return -1;
    }
}