package com.example.nemsy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MypageActivity extends AppCompatActivity {

    Dialog nicknameDialog;
    Dialog logoutDialog;
    ImageButton btn_changeNickname;
    Button btn_Logout;

    //닉네임 다이얼로그 관련
    Button btn_nicknameConfirm;
    Button btn_nicknameCancel;
    EditText changeNickname;
    TextView errorMessage;
    TextView tv_nickname;

    //로그아웃 다이얼로그 관련
    Button btn_logoutConfirm;
    Button btn_logoutCancel;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        firebaseAuth = FirebaseAuth.getInstance();

        init();

        btn_changeNickname = (ImageButton) findViewById(R.id.btn_change_nickname);
        btn_Logout = (Button) findViewById(R.id.btn_logout);

        nicknameDialog = new Dialog(MypageActivity.this);
        nicknameDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        nicknameDialog.setContentView(R.layout.dialog_nickname);

        btn_changeNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showNicknameDialog();
            }
        });

        logoutDialog = new Dialog(MypageActivity.this);
        logoutDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        logoutDialog.setContentView(R.layout.dialog_logout);

        btn_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showLogoutDialog();
            }
        });
    }

    private void showLogoutDialog() {
        logoutDialog.show();

        btn_logoutConfirm = logoutDialog.findViewById(R.id.btn_confirm);
        btn_logoutCancel = logoutDialog.findViewById(R.id.btn_cancel);

        btn_logoutConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //로그인 창으로 돌아가기
                logoutDialog.dismiss();
            }
        });

        btn_logoutCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutDialog.dismiss();
            }
        });
    }

    private void showNicknameDialog() {
        nicknameDialog.show();

        btn_nicknameConfirm = nicknameDialog.findViewById(R.id.btn_confirm);
        btn_nicknameCancel = nicknameDialog.findViewById(R.id.btn_cancel);
        changeNickname = nicknameDialog.findViewById(R.id.et_changenickname);
        errorMessage = nicknameDialog.findViewById(R.id.tv_error);

        btn_nicknameConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nickname = changeNickname.getText().toString().trim();
                if (nickname.length() < 4 || nickname.length() >10) {
                    errorMessage.setText("4자 이상 10자 이내로 입력해주세요");
                }
                else {
                    //회원정보의 닉네임 바꿔주기
                    Toast.makeText(getApplicationContext(),"닉네임이 성공적으로 변경되었습니다",Toast.LENGTH_SHORT).show();
                    nicknameDialog.dismiss();
                }
            }
        });

        btn_nicknameCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nicknameDialog.dismiss();
            }
        });
    }

    private void init(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Log.d("Database", "currUserId " + user.getUid());
        tv_nickname = (TextView) findViewById(R.id.tv_nickname);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue().toString();
                Log.d("Database", "AllUserInfo: " + value);
                for (DataSnapshot userSnapshot: snapshot.getChildren()) {
                    String key = userSnapshot.getKey();
                    Log.d("Database", "key: " + key.toString().trim());
                    Log.d("Database", "UserId " + user.getUid().toString().trim());
                    if (key.toString().trim().equals(user.getUid().toString().trim())){
                        HashMap<String, HashMap<String, Object>> userInfo = (HashMap<String, HashMap<String, Object>>) userSnapshot.getValue();
                        Log.d("Database", "currNick: " + userInfo.get("nickname"));
                        HashMap<String, Object> nickname = userInfo.get("nickname");
//                        String currNickname = userInfo.get("nickname").toString();
//                        Log.d("Database", "curr: " + currNickname);
//                        tv_nickname.setText(currNickname);
                        break;
                    }
//                    Log.d("Database", "value: " + userInfo);
//                    Log.d("Database", "value: " + userInfo.get("password"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
