package com.example.woowa_recamping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String title = getIntent().getStringExtra("title");
        String contents = getIntent().getStringExtra("contents");
        String price = getIntent().getStringExtra("price");
        int id = getIntent().getIntExtra("id", -1);

        TextView titleTextView = findViewById(R.id.detail_titleTextView);
        TextView contentsTextView = findViewById(R.id.detail_contentsTextView);
        ImageView imageView = findViewById(R.id.detail_imageView);

        button = findViewById(R.id.detail_Request);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), TransactionActivity.class);
                startActivity(i);
            }
        });

        titleTextView.setText(title);
        contentsTextView.setText(contents);
        if(id != -1) imageView.setImageResource(id);

    }
}