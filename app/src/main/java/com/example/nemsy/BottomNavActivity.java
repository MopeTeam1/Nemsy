package com.example.nemsy;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class BottomNavActivity extends AppCompatActivity {
    BillListFragment billListFragment;
    BillDetailFragment billDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        billListFragment = (BillListFragment) getSupportFragmentManager().findFragmentById(R.id.bill_list);
        billDetailFragment = new BillDetailFragment();
    }

    public void onFragmentChanged(int index) {
        if (index == 0) {
            getSupportFragmentManager().beginTransaction().replace(R.id.bottom_navigation, billListFragment).commit();
        }
        else if (index == 1) {
            getSupportFragmentManager().beginTransaction().replace(R.id.bottom_navigation, billDetailFragment).commit();
        }
    }
}
