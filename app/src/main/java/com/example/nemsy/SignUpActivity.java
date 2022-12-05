package com.example.nemsy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignUpActivity extends AppCompatActivity {
    ImageButton buttonBack;
    AppCompatButton signButton;
    TextView warningEmailOverlap, warningEmailForm, warningNicknameForm, warningPassword, warningRePassword;
    EditText getEmail, getPassword, getNickname, getRePassword;
    String email, nickname, password, rePassword;
    boolean isValidEmailForm = false;
    boolean isValidNicknameForm = false;
    boolean isValidPassword = false;
    boolean isValidRePassword = false;
    private FirebaseAuth firebaseAuth;
    boolean TF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getEmail = (EditText) findViewById(R.id.et_email);
        getNickname = (EditText) findViewById(R.id.et_nickname);
        getPassword = (EditText) findViewById(R.id.et_password);
        getRePassword = (EditText) findViewById(R.id.et_repassword);

        warningEmailOverlap = (TextView) findViewById(R.id.tv_error_email_overlap);
        warningEmailForm = (TextView) findViewById(R.id.tv_error_email_form);
        warningNicknameForm = (TextView) findViewById(R.id.tv_error_nickname);
        warningPassword = (TextView) findViewById(R.id.tv_error_password);
        warningRePassword = (TextView) findViewById(R.id.tv_error_repassword);
        signButton = (AppCompatButton) findViewById(R.id.btn_signup);

        firebaseAuth = FirebaseAuth.getInstance();

        // 타이틀바 제거
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        signButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                    String email = getEmail.getText().toString().trim();
                    String password = getPassword.getText().toString().trim();
                    String nickname = getNickname.getText().toString().trim();

                    checkAllCondition(isValidEmailForm, isValidPassword, isValidRePassword, isValidNicknameForm);
                    if (TF==true){
                        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){

                                    FirebaseUser user = firebaseAuth.getCurrentUser();

                                    String email = user.getEmail();
                                    String uid = user.getUid();

                                    //해쉬맵 테이블을 파이어베이스 데이터베이스에 저장
                                    HashMap<Object,String> hashmap = new HashMap<>();

                                    hashmap.put("uid",uid);
                                    hashmap.put("email",email);
                                    hashmap.put("nickname", nickname);
                                    hashmap.put("password", password);

                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference reference = database.getReference("Users");
                                    reference.child(uid).setValue(hashmap);

                                    // 자체 서버에 유저 등록
                                    new Thread(() -> {
                                        registerUser(uid, nickname);
                                    }).start();

                                    Toast.makeText(SignUpActivity.this,"회원가입에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();

                                }else{
                                    Toast.makeText(SignUpActivity.this, "이미 존재하는 아이디 입니다.", Toast.LENGTH_SHORT).show();
//                                return; //해당 메소드 진행을 멈추고 빠져나감
                                }
                            }
                        });
                    } else{
                        Toast.makeText(SignUpActivity.this, "입력 형식을 확인해주세요.", Toast.LENGTH_SHORT).show();
                    }




            }
        });

        // Back(<) 버튼 클릭 시 로그인 액티비티로 전환 (Login Activity)
        buttonBack = (ImageButton) findViewById(R.id.btn_back);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 이메일 형식 검사
        getEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    warningEmailForm.setVisibility(View.INVISIBLE);
                } else {
                    email = getEmail.getText().toString();
                    isValidEmailForm = Pattern.matches("\\w+@\\w+\\.\\w+(\\.\\w+)?",email);

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
                    isValidPassword= Pattern.matches("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&]).{7,15}.$",password);

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

    // 회원 가입 가능 여부 판단
    private boolean checkAllCondition(boolean isValidEmailForm, boolean isValidPassword, boolean isValidRePassword, boolean isValidNicknameForm){
        if (!(isValidEmailForm && isValidPassword && isValidRePassword && isValidNicknameForm)){
            TF = false;
        }else{
            TF= true;
        }
        return TF;
    }

    // 자체 서버에 User 등록
    private void registerUser(String uid, String nickname) {
        try {
            String content = "123123123123dgadsogahroeg";
            OkHttpClient client = new OkHttpClient();

            String strURL = String.format("http://54.250.154.173:8080/api/user/register/%s", uid);
            String strBody = String.format("{\"nickname\" : \"%s\"}", nickname);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), strBody);
            Request.Builder builder = new Request.Builder().url(strURL).post(requestBody);
            builder.addHeader("Content-type", "application/json");
            Request request = builder.build();
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()) {
                Log.d("http :", "success");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}