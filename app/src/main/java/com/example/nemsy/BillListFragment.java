package com.example.nemsy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class BillListFragment extends Fragment {

    BillAdapter adapter;
    ArrayList ages = new ArrayList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_bill_list, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        Spinner spinner = rootView.findViewById(R.id.spinner);

        // spinner
        for(int i=21; i>=10; i--) {
            ages.add(i+"대");
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_spinner_item, ages);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        // recyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new BillAdapter();

        adapter.addItem(new Bill("민주유공자 예우에 관한 법률안", "21", "전재수의원 등 11인", "2022-11-03"));
        adapter.addItem(new Bill("공직선거법 일부개정법률안", "21", "박성민의원 등 10인", "2022-11-03"));

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnBillItemClickListener() {
            @Override
            public void onItemClick(BillAdapter.ViewHolder holder, View view, int position) {
                Bill item = adapter.getItem(position);
                BottomNavActivity activity = (BottomNavActivity) getActivity();
                activity.onFragmentChanged(1);
            }
        });

        return rootView;
    }
}
