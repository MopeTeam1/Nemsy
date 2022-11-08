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

    public void onFragmentChanged() {
        Intent intent = new Intent(getApplicationContext(), BillDetailActivity.class);
        startActivity(intent);
    }
}
