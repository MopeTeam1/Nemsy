package com.example.nemsy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    TextView loginError ;
    EditText et_password ;
    EditText et_email ;
    Button btn_login ;
    Button btn_register ;
    ToggleButton showPassword ;

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
        loginError = (TextView) findViewById(R.id.tv_error);
        et_password = (EditText) findViewById(R.id.et_password);
        // EditText 입력 변화 이벤트 처리
        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loginError.setVisibility(View.INVISIBLE);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void clickEyeButton(){
        showPassword= (ToggleButton) findViewById(R.id.tbtn_eye);
        et_password = (EditText) findViewById(R.id.et_password);
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
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean checkPassword(EditText passwordEditText){
        et_password = (EditText) findViewById(R.id.et_password);
        String passwordValidation = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&]).{7,15}.$";
        String password = et_password.getText().toString().trim();
        if (password.matches(passwordValidation)){
            return true;
        } else {
            return false;
        }
    }

    private void initLogin(){
        et_password = (EditText) findViewById(R.id.et_password);
        et_email = (EditText) findViewById(R.id.et_email);
        btn_login = (Button) findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_email.getText().toString().trim();
                String password = et_password.getText().toString().trim();

                if(email.length()<=3){
                    Toast.makeText(LoginActivity.this, "이메일을 올바르게 입력하세요.", Toast.LENGTH_SHORT).show();
                    et_email.requestFocus();
                    return;
                }
                if((password.length()<=7)||(password.length()>=17)||(!checkPassword(et_password))){
                    Toast.makeText(LoginActivity.this, "영문, 숫자, 특수문자를 포함해서 비밀번호를 올바르게 입력하세요(8자-16자).", Toast.LENGTH_SHORT).show();
                    et_password.requestFocus();
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            SharedPreferences pref = getSharedPreferences("person_info", 0); // 프레퍼런스
                            SharedPreferences.Editor editor = pref.edit();
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            Log.d("Pref", "currUserId: " + user.getUid());
                            editor.putString("currUID", user.getUid());
                            editor.apply();
                            Intent intent = new Intent(LoginActivity.this, BottomNavActivity.class); // 일단 Main으로 intent
                            startActivity(intent);
                        }else{
                            loginError = (TextView) findViewById(R.id.tv_error);
                            loginError.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });
    }

}