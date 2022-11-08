package com.example.nemsy;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class BottomNavActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
    }

    public void onFragmentChanged(Bill item) {
        Intent intent = new Intent(getApplicationContext(), BillDetailActivity.class);
        intent.putExtra("DETAIL_LINK", item.DETAIL_LINK);
        intent.putExtra("RST_PROPOSER", item.RST_PROPOSER);
        intent.putExtra("PUBL_PROPOSER", item.PUBL_PROPOSER);
        intent.putExtra("AGE", item.AGE);
        intent.putExtra("PROPOSE_DT", item.PROPOSE_DT);
        intent.putExtra("PROC_RESULT", item.PROC_RESULT);
        startActivity(intent);
    }
}
