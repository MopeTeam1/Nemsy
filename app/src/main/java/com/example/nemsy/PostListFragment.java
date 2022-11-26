package com.example.nemsy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nemsy.model.Post;

public class PostListFragment extends Fragment {

    PostAdapter adapter;
    AppCompatButton postButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_post_list, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.postRecyclerView);
        postButton = rootView.findViewById(R.id.postButton);

        LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PostAdapter();
        recyclerView.setAdapter(adapter);
        getData();

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

        return rootView;
    }

    private void getData(){
        Post data = new Post("국민대학교","국민대학교는 대한민국의 사립 종합대학이다. 1900년대부터 시작되었다.","닉네임","11/23 11:08");
        adapter.addItem(data);
        data = new Post("국민대학교","국민대학교는 대한민국의 사립 종합대학이다. 1900년대부터 시작되었다.","닉네임","11/23 11:08");
        adapter.addItem(data);
        data = new Post("국민대학교","국민대학교는 대한민국의 사립 종합대학이다. 1900년대부터 시작되었다.","닉네임","11/23 11:08");
        adapter.addItem(data);
        data = new Post("국민대학교","국민대학교는 대한민국의 사립 종합대학이다. 1900년대부터 시작되었다.","닉네임","11/23 11:08");
        adapter.addItem(data);
        data = new Post("국민대학교","국민대학교는 대한민국의 사립 종합대학이다. 1900년대부터 시작되었다.","닉네임","11/23 11:08");
        adapter.addItem(data);
        data = new Post("국민대학교","국민대학교는 대한민국의 사립 종합대학이다. 1900년대부터 시작되었다.","닉네임","11/23 11:08");
        adapter.addItem(data);
        data = new Post("국민대학교","국민대학교는 대한민국의 사립 종합대학이다. 1900년대부터 시작되었다.","닉네임","11/23 11:08");
        adapter.addItem(data);
        data = new Post("국민대학교","국민대학교는 대한민국의 사립 종합대학이다. 1900년대부터 시작되었다.","닉네임","11/23 11:08");
        adapter.addItem(data);
        data = new Post("국민대학교","국민대학교는 대한민국의 사립 종합대학이다. 1900년대부터 시작되었다.","닉네임","11/23 11:08");
        adapter.addItem(data);
        Log.d("test", "test: "+data);
    }
}
