package com.example.nemsy;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class BillListFragment extends Fragment {

    BillAdapter adapter;
    ArrayList ages = new ArrayList();

    static RequestQueue requestQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_bill_list, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        Spinner spinner = rootView.findViewById(R.id.spinner);

        if(requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getContext());
        }

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

        adapter.addItem(new Bill("집합건물의 소유 및 관리에 관한 법률 일부개정법률안", "2020-05-22", "21", "http://likms.assembly.go.kr/bill/billDetail.do?billId=PRC_K2F0C0Y5B2D2C1Z5B2F6T4S2K9N8V8&ageFrom=20&ageTo=20", "김병관의원 등 13인"));
        adapter.addItem(new Bill("집합건물의 소유 및 관리에 관한 법률 일부개정법률안", "2020-05-22", "21", "http://likms.assembly.go.kr/bill/billDetail.do?billId=PRC_K2F0C0Y5B2D2C1Z5B2F6T4S2K9N8V8&ageFrom=20&ageTo=20", "김병관의원 등 13인"));

        recyclerView.setAdapter(adapter);
        makeRequest();

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

    public void makeRequest() {
        String url = "https://open.assembly.go.kr/portal/openapi/nzmimeepazxkubdpn?KEY=a9d0d8e5551d4a738e4f0e22fa5a0c4d&Type=json&pIndex=1&pSize=2&AGE=20";

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("응답", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("에러", error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);
    }
}
