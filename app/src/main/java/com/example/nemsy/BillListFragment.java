package com.example.nemsy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class BillListFragment extends Fragment {

    BillAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_bill_list, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);

        // recyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new BillAdapter();

        adapter.addItem(new Bill("민주유공자 예우에 관한 법률안", "21", "전재수의원 등 11인", "2022-11-03"));
        adapter.addItem(new Bill("공직선거법 일부개정법률안", "21", "박성민의원 등 10인", "2022-11-03"));

        recyclerView.setAdapter(adapter);

        return rootView;
    }
}
