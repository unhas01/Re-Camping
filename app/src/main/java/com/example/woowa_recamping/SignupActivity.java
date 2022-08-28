package com.example.woowa_recamping;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.woowa_recamping.data.User;
import com.example.woowa_recamping.db.UserDAO;

public class SignupActivity extends AppCompatActivity {
    private EditText editId, editName, editPwd, editPwd2, editAge, editPhone, editAddr, editEmail;
    private Button idButton, cancleButton, signupButton;

    private UserDAO userDAO;
    private SQLiteDatabase db;

    private boolean chkId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        chkId = false;

        editId = findViewById(R.id.signupIdEdit);
        editName = findViewById(R.id.signupNameEdit);
        editPwd = findViewById(R.id.signupPwdEdit);
        editPwd2 = findViewById(R.id.signupPwdEdit2);
        editAge = findViewById(R.id.signupAgeEdit);
        editPhone = findViewById(R.id.signupPhoneEdit);
        editAddr = findViewById(R.id.signupAddrEdit);
        editEmail = findViewById(R.id.signupEmailEdit);

        idButton = findViewById(R.id.signupIdButton);
        cancleButton = findViewById(R.id.singupCancleButton);
        signupButton = findViewById(R.id.signupButton);

        idButton.setOnClickListener(this::onClick);
        cancleButton.setOnClickListener(this::onClick);
        signupButton.setOnClickListener(this::onClick);
    }
    void onClick(View view) {
        userDAO = new UserDAO(this, "user.db", null, 1);
        db = userDAO.getWritableDatabase();
        userDAO.onCreate(db);

        switch(view.getId()) {
            case R.id.signupIdButton:   //db 아이디 중복 확인
                if(!userDAO.hasUserData(db, editId.getText().toString())) {
                    Toast.makeText(this, "Usable.", Toast.LENGTH_SHORT).show();
                    editId.setBackgroundColor(ContextCompat.getColor(this,R.color.gray));//배경색 설정
                    editId.setFocusable(false); //포커싱과
                    editId.setClickable(false); //터치가 안되도록
                    chkId = true;
                }
                else {
                    Toast.makeText(this, "Already exist.", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.singupCancleButton:
                finish();
                break;
            case R.id.signupButton:     //db 회원 데이터 추가 + 입력값 확인
                User u;
                if((u = CheckEdit()) != null && userDAO.insertUserDate(db, u)) {
                    Toast.makeText(this, u.getName() + ", Congratulation.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;

        }
    }

    User CheckEdit() {
        String name, id, pwd, pwd2, phone, addr, email;
        int age;

        name = editName.getText().toString();
        id = editId.getText().toString();
        pwd = editPwd.getText().toString();
        pwd2 = editPwd2.getText().toString();
        age = isNumeric(editAge.getText().toString()) ? Integer.parseInt(editAge.getText().toString()) : -1;
        phone = editPhone.getText().toString();
        addr = editAddr.getText().toString();
        email = editEmail.getText().toString();

        if (id.equals("") || name.equals("") || pwd.equals("") || pwd2.equals("") || phone.equals("") || addr.equals("") || email.equals("")) {
            Toast.makeText(this, "모든 항목을 채워주세요.", Toast.LENGTH_SHORT).show();
            return null;
        } else if (age == -1) {
            Toast.makeText(this, "입력 폼에 맞게 채워주세요.", Toast.LENGTH_SHORT).show();
            return null;
        }else if (!chkId) {
            Toast.makeText(this, "아이디 중복을 체크해주세요.", Toast.LENGTH_SHORT).show();
            return null;
        } else if (!editPwd.getText().toString().equals(editPwd2.getText().toString())) {
            Toast.makeText(this, "비밀번호를 맞게 작성했는지 확인해주세요.", Toast.LENGTH_SHORT).show();
            return null;
        }
        return new User(name, id, pwd, age, email, phone, addr);
    }
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}