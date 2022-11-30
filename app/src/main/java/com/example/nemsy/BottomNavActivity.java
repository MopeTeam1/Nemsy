package com.example.nemsy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nemsy.model.Post;

public class BottomNavActivity extends AppCompatActivity {
    private ImageButton community_icon, bill_icon, mypage_icon;
    PostListFragment postListFragment;
    BillListFragment billListFragment;
    MypageFragment mypageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        community_icon = (ImageButton) findViewById(R.id.community_icon);
        bill_icon = (ImageButton) findViewById(R.id.bill_icon);
        mypage_icon = (ImageButton) findViewById(R.id.mypage_icon);
        billListFragment = (BillListFragment) getSupportFragmentManager().findFragmentById(R.id.bill_list);
        mypageFragment = new MypageFragment();
        postListFragment = new PostListFragment();

        // 액션바 제거
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // bottom navigation으로 프래그먼트 전환
        community_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.bottom_navigation, postListFragment).commit();
            }
        });

        bill_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.bottom_navigation, billListFragment).commit();
            }
        });

        mypage_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.bottom_navigation, mypageFragment).commit();
            }
        });
    }

    public void putExtraToIntent(Bill item) {
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

    public void putExtraPostIntent(Post post) {
        Intent intent = new Intent(getApplicationContext(), CommunityDetailActivity.class);
        intent.putExtra("title", post.getTitle());
        intent.putExtra("content",post.getContent());
        intent.putExtra("author", post.getAuthor());
        intent.putExtra("createdAt", post.getCreatedAt());
        startActivity(intent);
    }

    public void WriteIntent(){
        Intent intent= new Intent(getApplicationContext(), WriteActivity.class);
        startActivity(intent);
    }
}
