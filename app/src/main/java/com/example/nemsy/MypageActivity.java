package com.example.nemsy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

    //로그아웃 다이얼로그 관련
    Button btn_logoutConfirm;
    Button btn_logoutCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

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
}