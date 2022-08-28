package com.example.woowa_recamping;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.woowa_recamping.db.UserDAO;

public class LoginActivity extends AppCompatActivity {
    private EditText editName, editPwd;
    private Button signBtn, logBtn;
    private ImageButton infoBtn;

    private UserDAO userDAO;
    private SQLiteDatabase db;
    private String name, pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 버튼 연결
        editName = findViewById(R.id.loginIdEdit);
        editPwd = findViewById(R.id.loginPwdEdit);
        signBtn = findViewById(R.id.loginSignButton);
        logBtn = findViewById(R.id.loginLoginButton);

        // 유저데이터베이스 생성
        userDAO = new UserDAO(this, "user.db", null, 1);
        db = userDAO.getWritableDatabase();
        userDAO.onCreate(db);

        // 클릭이벤트 설정
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = editName.getText().toString();
                pwd = editPwd.getText().toString();

                switch(view.getId()) {
                    case R.id.loginSignButton:                      // 회원가입 버튼
                        startActivity(new Intent(getApplicationContext(), com.example.woowa_recamping.SignupActivity.class));
                        break;
                    case R.id.loginLoginButton:                     // 로그인 버튼
                        if (startLogin(db, name, pwd)) {    // 로그인 함수 호출
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                        break;
                }
            }
        };
        signBtn.setOnClickListener(clickListener);
        logBtn.setOnClickListener(clickListener);
    }
    public boolean startLogin(SQLiteDatabase db, String id, String pwd) {
        String result;
        if((result = userDAO.getUserData(db, id)) != null) {
            if(result.equals(pwd)){
                ((CurUser)getApplication()).setUid(id);
                Toast.makeText(this, "Welcome~", Toast.LENGTH_SHORT).show();
                return true;
            }
            else {
                Toast.makeText(this, "Check your password.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        else {
            Toast.makeText(this, "User not found.", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
