package com.example.nemsy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

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

    int nowPageRange;
    int nowPageNum;
    Long postCnt;

    Handler handler = new Handler();

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

        nowPageRange = 0;
        setPagingNum();

        LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PostAdapter();
        new Thread(() -> {
            getData();
        }).start();
        recyclerView.setAdapter(adapter);

        pagingBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

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
                startActivity(intent);
            }
        });

        pagingBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowPageRange--;
                if (nowPageRange <= 0)
                    v.setVisibility(View.INVISIBLE);
                setPagingNum();
            }
        });

        pagingNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowPageRange++;
                pagingBackButton.setVisibility(View.VISIBLE);
                if (nowPageRange > 0)
                    v.setVisibility(View.VISIBLE);
                setPagingNum();
            }
        });

        for (AppCompatButton pagingButton : pagingButtonList) {
            pagingButton.setOnClickListener(pagingListener);
        }

        return rootView;
    }

    private void getData(){
        String responseString = null;
        try {
            OkHttpClient client = new OkHttpClient();
            String strURL = String.format("http://10.0.2.2:8080/api/board?page=%d", nowPageNum);
//            String strURL = String.format("http://54.250.154.173:8080/api/board?page=%d", nowPageNum);
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
            postCnt = ob.getLong("postCnt");
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
                data = new Post(title, content, author, createdAt, likeCount);
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
            nowPageNum = Integer.parseInt(((AppCompatButton)view).getText().toString()) - 1;

            // 게시글 가져오는 코드
            new Thread(() -> {
                getData();
            }).start();
        }
    };

    private void setPagingNum() {
        for (int i=0; i<5; i++) {
            pagingButtonList[i].setText(String.valueOf(nowPageRange*5+i+1));
        }
    }
}
