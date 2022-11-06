package com.example.nemsy;

import androidx.annotation.NonNull;
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
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    TextView loginError = (TextView) findViewById(R.id.tv_error);
    EditText et_password = (EditText) findViewById(R.id.et_password);
    EditText et_email = (EditText) findViewById(R.id.et_email);
    Button btn_login = (Button) findViewById(R.id.btn_login);
    Button btn_register = (Button) findViewById(R.id.btn_login);
    ToggleButton showPassword = (ToggleButton) findViewById(R.id.tbtn_eye);

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        editTextWatcher();
        clickEyeButton();
        initSignUp();
        initLogin();
    }

    private void editTextWatcher(){
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
    }

    private void clickEyeButton(){
        // 눈모양 토글 버튼 클릭 시 눈 이미지 변경 및 비밀번호 보이기/숨기기
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
    }

    private void initSignUp(){
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initLogin(){
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_email.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class); // 일단 Main으로 intent
//                            firebaseAuth.addAuthStateListener(firebaseAuthListener);
                            startActivity(intent);
                        }else{
                            Toast.makeText(LoginActivity.this,"로그인 실패",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}