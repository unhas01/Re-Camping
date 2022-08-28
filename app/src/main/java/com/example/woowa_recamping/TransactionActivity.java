package com.example.woowa_recamping;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TransactionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.transaction);

        // Accept 버튼 클릭시 메인페이지로 전환
        Button btn_accept = (Button) findViewById(R.id.btn_Accept);
        btn_accept.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

                Toast.makeText(getApplicationContext(), "Transaction complete!!", Toast.LENGTH_LONG).show();
            }
        });

        TextView fee_result = (TextView) findViewById(R.id.text_total_fee);
        EditText edit_day = (EditText) findViewById(R.id.text_days);
        edit_day.addTextChangedListener(new TextWatcher() {

            // 변경 전
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            // 변경 될때마다
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int day = Integer.parseInt(edit_day.getText().toString());
                int final_fee = day * 30 + 6;
                //if(edit_day.getText().toString() != null || edit_day.getText().toString() != "")
                    fee_result.setText(final_fee + "$");
            }

            // 변경 후
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
