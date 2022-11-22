package com.example.nemsy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class BottomNavActivity extends AppCompatActivity {
    private ImageButton bill_icon, mypage_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        bill_icon = (ImageButton) findViewById(R.id.bill_icon);
        mypage_icon = (ImageButton) findViewById(R.id.mypage_icon);

        // 액션바 제거
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        bill_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0, 0);
                Intent intent = getIntent();
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

//        mypage_icon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), MypageActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
    }

    public void onFragmentChanged(Bill item) {
        Intent intent = new Intent(getApplicationContext(), BillDetailActivity.class);
        intent.putExtra("BILL_ID", item.BILL_ID);
        intent.putExtra("DETAIL_LINK", item.DETAIL_LINK);
        intent.putExtra("RST_PROPOSER", item.RST_PROPOSER);
        intent.putExtra("PUBL_PROPOSER", item.PUBL_PROPOSER);
        intent.putExtra("AGE", item.AGE);
        intent.putExtra("PROPOSE_DT", item.PROPOSE_DT);
        intent.putExtra("PROC_RESULT", item.PROC_RESULT);
        startActivity(intent);
    }
}
