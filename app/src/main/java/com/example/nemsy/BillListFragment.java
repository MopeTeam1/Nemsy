package com.example.nemsy;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
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
    int maxPage = 20000;
    int maxPageRange = 20000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_bill_list, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.billRecyclerView);
        Spinner spinner = rootView.findViewById(R.id.spinner);

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

        setPagingNum();
        if (maxPageRange == 0)
            pagingNextButton.setVisibility(View.INVISIBLE);
        if (nowPageRange == 0)
            pagingBackButton.setVisibility(View.INVISIBLE);
        else
            pagingBackButton.setVisibility(View.VISIBLE);

//        new Thread(() -> {
//            getData();
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//
//                }
//            });
//        }).start();

        if(AppHelper.requestQueue == null) {
            AppHelper.requestQueue = Volley.newRequestQueue(getContext());
        }

        // spinner
        for(int i=21; i>=1; i--) {
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
                nowPageRange = 0;
                nowPageNum = 1;
                makeRequest();
                setPagingNum();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
                nowPageNum = nowPageRange * 8 + 1;
                makeRequest();
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
                nowPageNum = nowPageRange * 8 + 1;
                makeRequest();
            }
        });

        for (AppCompatButton pagingButton : pagingButtonList) {
            pagingButton.setOnClickListener(pagingListener);
        }

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
        String url = String.format("https://open.assembly.go.kr/portal/openapi/nzmimeepazxkubdpn?KEY=a9d0d8e5551d4a738e4f0e22fa5a0c4d&Type=json&pIndex=%s&pSize=8&AGE=%s", nowPageNum, requestAge);

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
        adapter.clear();
        for(int i=0; i<billResponse.nzmimeepazxkubdpn.get(1).row.size(); i++) {
            Bill bill = billResponse.nzmimeepazxkubdpn.get(1).row.get(i);
            adapter.addItem(bill);
        }
        adapter.notifyDataSetChanged();
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
            makeRequest();
        }
    };

    private void setPagingNum() {


        for (int i=0; i<5; i++) {
            int nowPage = nowPageRange*5+i+1;
            pagingButtonList[i].setText(String.valueOf(nowPage));
            if (nowPageRange == 0)
                pagingBackButton.setVisibility(View.INVISIBLE);
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
