package com.example.nemsy;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MypageFragment extends Fragment {
    Dialog nicknameDialog;
    Dialog logoutDialog;

    //닉네임 다이얼로그 관련
    Button btn_nicknameConfirm;
    Button btn_nicknameCancel;
    EditText changeNickname;
    TextView errorMessage;
    TextView tv_nickname;
    TextView tv_email;

    //로그아웃 다이얼로그 관련
    Button btn_logoutConfirm;
    Button btn_logoutCancel;

    private FirebaseAuth firebaseAuth;

    String currUID;
    String currNick;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_mypage, container, false);

        Button btn_changeNickname = rootView.findViewById(R.id.btn_change_nickname);
        Button btn_Logout = rootView.findViewById(R.id.btn_logout);
        tv_nickname = rootView.findViewById(R.id.tv_nickname);
        tv_email = rootView.findViewById(R.id.tv_email);

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Users");

        SharedPreferences pref = this.getActivity().getSharedPreferences("person_info", 0); // 프레퍼런스
        SharedPreferences.Editor editor = pref.edit();
        currUID = pref.getString("currUID","");
        Log.d("Database", "prefCurrUID: " + currUID);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue().toString();
                Log.d("Database", "AllUserInfo: " + value);
                for (DataSnapshot userSnapshot: snapshot.getChildren()) {
                    String key = userSnapshot.getKey();
                    if (key.toString().trim().equals(currUID.toString().trim())){
                        HashMap<String, HashMap<String, Object>> userInfo = (HashMap<String, HashMap<String, Object>>) userSnapshot.getValue();
                        Log.d("DataBase", "userInfo: " +userInfo);
                        Log.d("Database", "currNick: " + userInfo.get("nickname"));
                        tv_nickname.setText(userInfo.get("nickname")+"");
                        tv_email.setText(userInfo.get("email")+"");
                        editor.putString("currNick", userInfo.get("nickname")+"");
                        editor.apply();
                        currNick = pref.getString("currNick","");
                        Log.d("Database", "currNick: " + currNick );
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        nicknameDialog = new Dialog(getContext());
        nicknameDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        nicknameDialog.setContentView(R.layout.dialog_nickname);

        btn_changeNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showNicknameDialog();
            }
        });

        changeNickname = nicknameDialog.findViewById(R.id.et_changenickname);

        changeNickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                errorMessage.setVisibility(View.INVISIBLE);

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        logoutDialog = new Dialog(getContext());
        logoutDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        logoutDialog.setContentView(R.layout.dialog_logout);

        btn_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogoutDialog();
            }
        });

        return rootView;
    }

    private void showLogoutDialog() {
        logoutDialog.show();

        btn_logoutConfirm = logoutDialog.findViewById(R.id.btn_confirm);
        btn_logoutCancel = logoutDialog.findViewById(R.id.btn_cancel);

        btn_logoutConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //로그인 창으로 돌아가기
                firebaseAuth.signOut();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
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
                    errorMessage.setVisibility(View.VISIBLE);
                }
                else {
                    //회원정보의 닉네임 바꿔주기
                    new Thread(() -> {
                        changeNickName(nickname);
                    }).start();
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

    private void changeNickName(String newNickname) {
        SharedPreferences pref = this.getActivity().getSharedPreferences("person_info", 0); // 프레퍼런스
        SharedPreferences.Editor editor = pref.edit();
        try {
            Log.i("newNickname ", newNickname);
            Log.i("UID ", currUID);
            OkHttpClient client = new OkHttpClient();
            String strURL = String.format("http://54.250.154.173:8080/api/user/%s/nickname", currUID);
            String strBody = String.format("{\"nickname\" : \"%s\"}", newNickname);
            Log.i("strBody ", strBody);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), strBody);
            Request.Builder builder = new Request.Builder().url(strURL).put(requestBody);
            builder.addHeader("Content-type", "application/json");
            Request request = builder.build();
            Log.d("http ", "log 6");
            Response response = client.newCall(request).execute();
            Log.d("http ", "log 7");
            if(response.isSuccessful()) {
                Log.d("http :", "success");

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_nickname.setText(newNickname);
                        Toast.makeText(getContext(),"닉네임이 성공적으로 변경되었습니다",Toast.LENGTH_SHORT).show();
                        editor.putString("currNick", newNickname);
                        editor.apply();
                        currNick = pref.getString("currNick","");
                        Log.d("Database", "currNick: " + currNick );
                    }
                });
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
