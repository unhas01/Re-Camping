package com.example.woowa_recamping;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.woowa_recamping.data.Data;

import java.util.ArrayList;


public class RequestActivity extends AppCompatActivity {
    private final int GET_GALLERY_IMAGE = 200;
    private ImageView imageview;

    EditText title;
    EditText content;
    EditText price;
    Button button_complete, button_cancle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        title = (EditText) findViewById(R.id.title);
        content = (EditText) findViewById(R.id.content);
        price = findViewById(R.id.price);
        button_complete = (Button) findViewById(R.id.button_complete);
        button_cancle = findViewById(R.id.button_cancle);
        imageview = (ImageView)findViewById(R.id.imageView);

        imageview.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent. setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });

        button_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        button_complete.setOnClickListener(new View.OnClickListener() {
           // @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                int a;
                ((CurUser) getApplication()).list.add(new Item(title.getText().toString(), content.getText().toString(), Integer.parseInt(price.getText().toString()), R.drawable.i_light, 3.1));
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri selectedImageUri = data.getData();
            imageview.setImageURI(selectedImageUri);

        }

    }

    public class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(RequestActivity context) {
            super(context, "requestDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE requestTBL ( request_title varchar PRIMARY KEY,request_content varchar);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS requestTBL");
            onCreate(db);

        }
    }
}

