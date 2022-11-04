package com.example.nemsy;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

public class LoginActivity extends AppCompatActivity {
    TextView loginError;
    EditText et_email, et_password;
    Button btn_login, btn_register;
    ToggleButton showPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 타이틀바 제거
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // 회원가입 버튼 클릭 시 회원가입 액티비티로 전환
        btn_register = (Button) findViewById(R.id.btn_login);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);

        // 눈모양 토글 버튼 클릭 시 눈 이미지 변경 및 비밀번호 보이기/숨기기
        showPassword = (ToggleButton) findViewById(R.id.tbtn_eye);
        showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isClicked = ((ToggleButton) view).isChecked();

                if (isClicked) {
                    showPassword.setBackgroundDrawable(getResources().getDrawable(R.drawable.eye_visible));
                    et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    showPassword.setBackgroundDrawable(getResources().getDrawable(R.drawable.eye_invisible));
                    et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        loginError = (TextView) findViewById(R.id.tv_error);

        // EditText 입력 변화 이벤트 처리
        final TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (loginError.getVisibility() == View.VISIBLE) {
                    loginError.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };
        // 로그인 버튼 클릭 시
        btn_login = (Button) findViewById(R.id.btn_login);
        /**
         * btn_login.setOnClickListener(new View.OnClickListener() {
         *             @Override
         *             public void onClick(View view) {
         *                  로그인 성공 시 - 발의법률안 목록 페이지로 이동
         *                  로그인 실패 시 - error 메세지
         *
         *                  if ( 성공 ) {
         *                  Intent intent = new Intent(LoginActivity.this, MainpageActivity.class);
         *                  startActivity(intent);
         *
         *                  // 로그아웃 후 다시 로그인 페이지에서 초기화 될 수 있게 email, password 입력값을을 초기화
         *                  et_email.setText(null);
         *                  et_password.setText(null);
         *                  } else{
         *                      loginError.setVisibility(View.VISIBLE);
         *                  }
         *
         *                  // 로그인 버튼 클릭 후 email 혹은 password 입력 변화 시 error 감추기
         *                  et_email.addTextChangedListener(textWatcher);
         *                  et_password.addTextChangedListener(textWatcher);
         *             }
         *         });
         *
         *
         */
    }
}