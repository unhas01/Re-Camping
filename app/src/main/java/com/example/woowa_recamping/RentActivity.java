package com.example.woowa_recamping;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
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
import java.util.Calendar;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class RentActivity extends AppCompatActivity {

    private final int GET_GALLERY_IMAGE = 200;
    private ImageView imageview;
    private InputMethodManager imm;
    Uri uri;
    View dialog;

    private TextView result, start, end, info;
    private EditText title, fee, content, time, location, price;
    private CheckBox ftof, pickup, delivery, shipping;
    private LinearLayout ftofLayout;
    private RadioGroup agree;
    private Button complete, startButton, endButton, suggestion;

    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);

        result = findViewById(R.id.rentalfeeResult);
        start = findViewById(R.id.startDate);
        end = findViewById(R.id.endDate);
        info = findViewById(R.id.info);

        title = findViewById(R.id.titleText);
        fee = findViewById(R.id.rentalFeeText);
        content = findViewById(R.id.contentText);
        time = findViewById(R.id.pickupTimeText);
        location = findViewById(R.id.pickupLocationText);
        price = findViewById(R.id.deliveryText);

        ftof = findViewById(R.id.facetofaceCheck);
        pickup = findViewById(R.id.pickupCheck);
        delivery = findViewById(R.id.deliveryCheck);
        shipping = findViewById(R.id.shippingserviceCheck);

        ftofLayout = findViewById(R.id.FaceToFaceView);

        agree = findViewById(R.id.radioGroup2);
        complete = findViewById(R.id.button8);
        endButton = findViewById(R.id.endButton);
        startButton = findViewById(R.id.startButton);
        suggestion = findViewById(R.id.suggestion);

        // suggestion 버튼 클릭시 팝업 창 띄우기
        suggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    fee.setText("25.5", TextView.BufferType.EDITABLE);
                }catch (NumberFormatException e) {

                }
//
                info.setHeight(100);
                info.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                dialog = View.inflate(RentActivity.this, R.layout.popup, null);

                AlertDialog.Builder dlg = new AlertDialog.Builder(RentActivity.this);

                dlg.setView(dialog);

                dlg.show();
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int pYear = calendar.get(Calendar.YEAR);
                int pMonth = calendar.get(Calendar.MONTH);
                int pDay = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(RentActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String dates = year + "/" + month + "/" + dayOfMonth + " ~ ";

                        start.setText(dates);
                    }
                }, pYear, pMonth, pDay);
                datePickerDialog.show();
            }
        });
        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int pYear = calendar.get(Calendar.YEAR);
                int pMonth = calendar.get(Calendar.MONTH);
                int pDay = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(RentActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String dates = year + "/" + month + "/" + dayOfMonth;

                        end.setText(dates);
                    }
                }, pYear, pMonth, pDay);
                datePickerDialog.show();
            }
        });
        complete.setOnClickListener(new View.OnClickListener() {
            // @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                int a;
                ((CurUser) getApplication()).list.add(new Item(title.getText().toString(), content.getText().toString(), Integer.parseInt(fee.getText().toString()), R.drawable.teent, 3.1));
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        imageview = (ImageView)findViewById(R.id.photoView);
        imageview.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityResult.launch(intent);
            }
        });
        //Face-to-Face 선택 시 나타날 대면 배송 방법 레이아웃 설정
        ftof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ftof.isChecked()) {
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    lp.leftMargin = 100;
                    ftofLayout.setLayoutParams(lp);
                }
                else ftofLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
            }
        });

        fee.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                int bfee = Integer.parseInt(fee.getText().toString());

                result.setText("15% of Price = " + String.format("*%.2f", bfee * 0.15) + "$");
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
        imm.hideSoftInputFromWindow(fee.getWindowToken(), 0);
    }
}
