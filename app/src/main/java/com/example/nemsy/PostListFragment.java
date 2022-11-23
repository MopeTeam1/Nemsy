package com.example.nemsy;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nemsy.model.Post;

public class PostListFragment extends Fragment {

    PostAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_post_list, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.postRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PostAdapter();
        recyclerView.setAdapter(adapter);
        getData();


        adapter.setOnItemClickListener(new OnBillItemClickListener() {
            @Override
            public void onItemClick(BillAdapter.ViewHolder holder, View view, int position) {
                Bill item = adapter.getItem(position);
                BottomNavActivity activity = (BottomNavActivity) getActivity();
                activity.putExtraToIntent(item);
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
