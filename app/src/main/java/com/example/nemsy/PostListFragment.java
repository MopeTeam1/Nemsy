package com.example.nemsy;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nemsy.model.Post;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class PostListFragment extends Fragment {

    PostAdapter adapter;
    AppCompatButton postButton;

    ImageButton pagingBackButton;
    AppCompatButton pagingFirstButton;
    AppCompatButton pagingSecondButton;
    AppCompatButton pagingThirdButton;
    AppCompatButton pagingFourthButton;
    AppCompatButton pagingFifthButton;
    ImageButton pagingNextButton;

    AppCompatButton[] pagingButtonList;

    int nowPageRange = 0;
    int nowPageNum = 1;
    int maxPage = 30;
    int maxPageRange = 5;

    Handler handler = new Handler();

    public static final int REQUEST_CODE = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_post_list, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.postRecyclerView);
        postButton = rootView.findViewById(R.id.postButton);

        // paging button init
        pagingBackButton = rootView.findViewById(R.id.btn_paging_back);
        pagingFirstButton = rootView.findViewById(R.id.btn_paging_1);
        pagingSecondButton = rootView.findViewById(R.id.btn_paging_2);
        pagingThirdButton = rootView.findViewById(R.id.btn_paging_3);
        pagingFourthButton = rootView.findViewById(R.id.btn_paging_4);
        pagingFifthButton = rootView.findViewById(R.id.btn_paging_5);
        pagingNextButton = rootView.findViewById(R.id.btn_paging_next);

        pagingButtonList =  new AppCompatButton[5];
        pagingButtonList[0] = pagingFirstButton;
        pagingButtonList[1] = pagingSecondButton;
        pagingButtonList[2] = pagingThirdButton;
        pagingButtonList[3] = pagingFourthButton;
        pagingButtonList[4] = pagingFifthButton;

        LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PostAdapter();
        new Thread(() -> {
            getData();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    setPagingNum();
                    if (maxPageRange == 0 || nowPageRange == maxPageRange)
                        pagingNextButton.setVisibility(View.INVISIBLE);
                    else
                        pagingNextButton.setVisibility(View.VISIBLE);
                    if (nowPageRange == 0)
                        pagingBackButton.setVisibility(View.INVISIBLE);
                    else
                        pagingBackButton.setVisibility(View.VISIBLE);
                }
            });
        }).start();
        recyclerView.setAdapter(adapter);



        adapter.setOnItemClickListener(new OnPostItemClickListener() {
            @Override
            public void onItemClick(ViewHolderPost holder, View view, int position) {
                Post item = adapter.getItem(position);
                BottomNavActivity activity = (BottomNavActivity) getActivity();
                activity.putExtraPostIntent(item);
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WriteActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });

        pagingBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowPageRange--;
                if (nowPageRange <= 0)
                    v.setVisibility(View.INVISIBLE);
                pagingNextButton.setVisibility(View.VISIBLE);
                setPagingNum();
                nowPageNum = nowPageRange * 5 + 1;
                new Thread(() -> {
                    getData();
                }).start();
            }
        });

        pagingNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowPageRange++;
                pagingBackButton.setVisibility(View.VISIBLE);
                if ( nowPageRange == maxPageRange )
                    v.setVisibility(View.INVISIBLE);

                setPagingNum();
                nowPageNum = nowPageRange * 5 + 1;
                Log.d("nowPageNum :", String.valueOf(nowPageNum));
                new Thread(() -> {
                    getData();
                }).start();
            }
        });

        for (AppCompatButton pagingButton : pagingButtonList) {
            pagingButton.setOnClickListener(pagingListener);
        }

        return rootView;
    }

    // 커뮤니티 글 작성 결과
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            Log.d("requestCode :", String.valueOf(requestCode));

            if (resultCode == RESULT_OK) {
                Log.d("resultCode :", String.valueOf(resultCode));
                new Thread(() -> {
                    nowPageNum = 1;
                    nowPageRange = 0;
                    getData();
                    setPagingNum();
                }).start();
            }
        }
    }

    protected void getData(){
        String responseString = null;
        try {
            OkHttpClient client = new OkHttpClient();
//            String strURL = String.format("http://10.0.2.2:8080/api/board?page=%d", nowPageNum);
            String strURL = String.format("http://54.250.154.173:8080/api/board?page=%d", nowPageNum - 1);
            Request.Builder builder = new Request.Builder().url(strURL).get();
            builder.addHeader("Content-type", "application/json");
            Request request = builder.build();
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                ResponseBody body = response.body();
                responseString = body.string();
                body.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            JSONObject ob = new JSONObject(responseString);
            maxPage = (int) Math.ceil((double) ob.getLong("postCnt") / 6) ;
            maxPageRange = (maxPage - 1) / 5;

            JSONArray jsonArray = ob.getJSONArray("postList");
            Post data;
            adapter.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String title = jsonObject.getString("title");
                String content = jsonObject.getString("content");
                String author = jsonObject.getString("authorNickname");
                String createdAt = jsonObject.getString("createdAt");
                int likeCount = jsonObject.getInt("likeCount");
                Long postId = jsonObject.getLong("postId");
                data = new Post(title, content, author, createdAt, likeCount, postId);
                adapter.addItem(data);
                Log.d("jsonObject :", jsonObject.toString());
                setData();
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
            }
        });
    }

    private final View.OnClickListener pagingListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            nowPageNum = Integer.parseInt(((AppCompatButton)view).getText().toString());
            AppCompatButton selected = (AppCompatButton)view;
            pagingFirstButton.setTextColor(Color.parseColor("#8E8E8E"));
            pagingSecondButton.setTextColor(Color.parseColor("#8E8E8E"));
            pagingThirdButton.setTextColor(Color.parseColor("#8E8E8E"));
            pagingFourthButton.setTextColor(Color.parseColor("#8E8E8E"));
            pagingFifthButton.setTextColor(Color.parseColor("#8E8E8E"));
            selected.setTextColor(Color.parseColor("#000000"));

            // 게시글 가져오는 코드
            new Thread(() -> {
                getData();
            }).start();
        }
    };

    private void setPagingNum() {


        for (int i=0; i<5; i++) {
            int nowPage = nowPageRange*5+i+1;
            pagingButtonList[i].setText(String.valueOf(nowPage));
            if (nowPage > maxPage) {
                pagingButtonList[i].setVisibility(View.GONE);
            } else {
                pagingButtonList[i].setVisibility(View.VISIBLE);
            }
        }
        pagingFirstButton.setTextColor(Color.parseColor("#000000"));
        pagingSecondButton.setTextColor(Color.parseColor("#8E8E8E"));
        pagingThirdButton.setTextColor(Color.parseColor("#8E8E8E"));
        pagingFourthButton.setTextColor(Color.parseColor("#8E8E8E"));
        pagingFifthButton.setTextColor(Color.parseColor("#8E8E8E"));
    }
}
