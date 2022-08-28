package com.example.woowa_recamping;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class RentFragment extends AppCompatActivity {

    private final int GET_GALLERY_IMAGE = 200;
    private ImageView imageview;
    private InputMethodManager imm;
    private EditText title;
    private EditText rentalFee;
    private TextView m, t1, w, t2, f, s1, s2; // 요일 선택 버튼
    private EditText content;
    private TextView ResultRentalFee;

    private double AfterLentalFee;

    private Button complete;
    Uri uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rentpage);

        title = (EditText)findViewById(R.id.title);
        rentalFee = (EditText)findViewById(R.id.rentalFee);
        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        content = (EditText)findViewById(R.id.content);
        ResultRentalFee = (TextView) findViewById(R.id.resultRentalFee);
        complete = findViewById(R.id.button8);

        RadioGroup rg = (RadioGroup)findViewById(R.id.radioGroup);
        LinearLayout rentPage = (LinearLayout)findViewById(R.id.rentPage);


        m = (TextView) findViewById(R.id.m);
        t1 = (TextView)findViewById(R.id.t1);
        w = (TextView)findViewById(R.id.w);
        t2 = (TextView)findViewById(R.id.t2);
        f = (TextView)findViewById(R.id.f);
        s1 = (TextView)findViewById(R.id.s1);
        s2 = (TextView)findViewById(R.id.s2);

        complete.setOnClickListener(new View.OnClickListener() {
            // @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                int a;
                ((CurUser) getApplication()).list.add(new Item(title.getText().toString(), content.getText().toString(), Integer.parseInt(rentalFee.getText().toString()), R.drawable.i_light, 3.1));
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
        imageview = (ImageView)findViewById(R.id.photo);
        imageview.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityResult.launch(intent);

                /* 기존 코드
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent. setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);

                 */
            }
        });

        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String WhatTitle = title.getText().toString();
            }
        });

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String result;
                if(checkedId == R.id.radio1) {
                    rentPage.setVisibility(View.VISIBLE);
                }
                else {
                    rentPage.setVisibility(View.INVISIBLE);
                }
            }
        });

        rentalFee.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int BeforeLentalFee = Integer.parseInt(rentalFee.getText().toString());
                AfterLentalFee = BeforeLentalFee + BeforeLentalFee*0.15;
                ResultRentalFee.setText("15% of Price = " + AfterLentalFee + "$");
            }
        });

        /* 기존 요일 버튼 코드
        m.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                clickBtn(m, motionEvent);
                return false;
            }
        });

         */

        m.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                clickBtn(m, motionEvent);
                return false;
            }
        });
        t1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                clickBtn(t1, motionEvent);
                return false;
            }
        });
        w.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                clickBtn(w, motionEvent);
                return false;
            }
        });
        t2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                clickBtn(t2, motionEvent);
                return false;
            }
        });
        f.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                clickBtn(f, motionEvent);
                return false;
            }
        });
        s1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                clickBtn(s1, motionEvent);
                return false;
            }
        });
        s2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                clickBtn(s2, motionEvent);
                return false;
            }
        });


        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String WhatContent = content.getText().toString();
            }
        });

    }

    ActivityResultLauncher<Intent> startActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK && result.getData() != null) {
                        uri = result.getData().getData();

                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            imageview.setImageBitmap(bitmap);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    );

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri selectedImageUri = data.getData();
            imageview.setImageURI(selectedImageUri);

        }

    }

    // manifast에 android:windowSoftInputMode="stateAlwaysHidden 추가
    public void linearOnClick(View v) {
        imm.hideSoftInputFromWindow(title.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(rentalFee.getWindowToken(), 0);
    }

    public void clickBtn(TextView t, MotionEvent m) {
        if(m.getAction() == MotionEvent.ACTION_DOWN) {
            t.setBackgroundColor(Color.parseColor("#9FBFA7"));
        } else if(m.getAction() == MotionEvent.ACTION_UP) {
            t.setBackgroundColor(Color.BLUE);
        }
    }
}
