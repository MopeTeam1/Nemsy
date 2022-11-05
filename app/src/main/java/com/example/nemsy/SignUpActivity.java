package com.example.nemsy;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.pm.SigningInfo;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    ImageButton buttonBack;
    AppCompatButton buttonRegister;
    TextView warningEmailOverlap, warningEmailForm, warningNicknameOverlap, warningNicknameForm, warningPassword, warningRePassword;
    EditText getEmail, getPassword, getNickname, getRePassword;
    String email, nickname, password, rePassword;
    boolean isValidEmailForm = false;
    boolean isValidNicknameForm = false;
    boolean isValidPassword = false;
    boolean isValidRePassword = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // 타이틀바 제거
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // 회원가입 버튼 클릭 : 모든 가입 조건 충족 시 로그인 액티비티로 전환 (Main Activity)
        buttonRegister = (AppCompatButton) findViewById(R.id.btn_signup);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidEmailForm && isValidNicknameForm && isValidPassword && isValidRePassword){
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);

                    // 이메일 중복 검사 기능 추가시 isValidEmailOverlap 추가
                } else{
                }
            }
        });

        // Back(<) 버튼 클릭 시 로그인 액티비티로 전환 (Main Activity)
        buttonBack = (ImageButton) findViewById(R.id.btn_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        getEmail = (EditText) findViewById(R.id.et_email);
        getNickname = (EditText) findViewById(R.id.et_nickname);
        getPassword = (EditText) findViewById(R.id.et_password);
        getRePassword = (EditText) findViewById(R.id.et_repassword);

        warningEmailOverlap = (TextView) findViewById(R.id.tv_error_email_overlap);
        warningEmailForm = (TextView) findViewById(R.id.tv_error_email_form);
        warningNicknameForm = (TextView) findViewById(R.id.tv_error_nickname);
        warningPassword = (TextView) findViewById(R.id.tv_error_password);
        warningRePassword = (TextView) findViewById(R.id.tv_error_repassword);

        // 이메일 중복 검사

        // 이메일 형식 검사
        getEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    warningEmailForm.setVisibility(View.INVISIBLE);
                } else {
                    email = getEmail.getText().toString();
                    isValidEmailForm= Pattern.matches("\\w+@\\w+\\.\\w+(\\.\\w+)?",email);

                    // 이메일 형식이 일치하지 않을 시 경고 메세지 보이기
                    if(isValidEmailForm){
                        warningEmailForm.setVisibility(View.INVISIBLE);

                    } else{
                        warningEmailForm.setVisibility(View.VISIBLE);

                    }
                }

            }
        });

        // 닉네임 조건 검사 : 4자 이상 10자 이내
        getNickname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus){
                    warningNicknameForm.setVisibility(View.INVISIBLE);
                } else {
                    nickname = getNickname.getText().toString();
                    isValidNicknameForm = (nickname.length() > 3) && (nickname.length() < 11);

                    // 4자 이상 10자 이하가 아닐 시 경고 메세지 보이기
                    if (isValidNicknameForm){
                        warningNicknameForm.setVisibility(View.INVISIBLE);
                    } else{
                        warningNicknameForm.setVisibility(View.VISIBLE);
                    }
                }

            }
        });

        // 비밀번호 조건 검사
        getPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    warningPassword.setVisibility(View.INVISIBLE);
                } else {
                    password = getPassword.getText().toString();
                    isValidPassword= Pattern.matches("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[~!@#$%^&*()+=]).{8,16}$",password);

                    // 비밀번호 형식이 맞지 않을 시 경고 메세지 보이기
                    if(isValidPassword){
                        warningPassword.setVisibility(View.INVISIBLE);
                    } else {
                        warningPassword.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        // 비밀번호 재입력 확인 검사
        getRePassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus){
                    warningRePassword.setVisibility(View.INVISIBLE);
                } else {
                    rePassword = getRePassword.getText().toString();
                    isValidRePassword = rePassword.equals(password);

                    // 비밀번호 입력이 일치하지 않을 시 경고 메세지 보이기
                    if (isValidRePassword){
                        warningRePassword.setVisibility(View.INVISIBLE);
                    } else{
                        warningRePassword.setVisibility(View.VISIBLE);
                    }
                }
            }
        });



     }
}
