package com.example.nemsy;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class BillListFragment extends Fragment {

    BillAdapter adapter;
    ArrayList ages = new ArrayList();
    String requestAge = "21";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_bill_list, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        Spinner spinner = rootView.findViewById(R.id.spinner);

        if(AppHelper.requestQueue == null) {
            AppHelper.requestQueue = Volley.newRequestQueue(getContext());
        }

        // spinner
        for(int i=21; i>=10; i--) {
            ages.add(i+"대");
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_spinner_item, ages);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                requestAge = ages.get(i).toString().substring(0, 2);
                adapter.clearItem();
                makeRequest();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // recyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new BillAdapter();

        recyclerView.setAdapter(adapter);

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

    public void makeRequest() {
        String url = "https://open.assembly.go.kr/portal/openapi/nzmimeepazxkubdpn?KEY=a9d0d8e5551d4a738e4f0e22fa5a0c4d&Type=json&pIndex=1&pSize=100&AGE=" + requestAge;

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("응답", response);
                        processResponse(response);
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
        AppHelper.requestQueue.add(request);
    }

    public void processResponse(String response) {
        Gson gson = new Gson();
        BillResponse billResponse = gson.fromJson(response, BillResponse.class);

        for(int i=0; i<billResponse.nzmimeepazxkubdpn.get(1).row.size(); i++) {
            Bill bill = billResponse.nzmimeepazxkubdpn.get(1).row.get(i);
            adapter.addItem(bill);
        }
        adapter.notifyDataSetChanged();
    }
}
