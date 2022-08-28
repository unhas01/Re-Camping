package com.example.woowa_recamping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.woowa_recamping.data.Data;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface {
    ArrayList<Item> items = new ArrayList<>();
    BottomNavigationView bottomNavigationView;

    Button requestButton, rentButton;

    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        requestButton = findViewById(R.id.button5);
        rentButton = findViewById(R.id.button4);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        ItemAdapter adapter = new ItemAdapter(this, items, this);

        if(((CurUser) getApplication()).list.size()==0) {
            ((CurUser) getApplication()).list.add(new Item("I'm sharing the camping tent", "Camping rentals are available in Sadang-dong, seoul. Please call me.", 30, R.drawable.i_tent, 0.3));
            ((CurUser) getApplication()).list.add(new Item("Good for fireplace", "I'd like to make a good deal in Nowon-gu.", 120, R.drawable.i_fire, 0.9));
            ((CurUser) getApplication()).list.add(new Item("Who needs a hammock chair?", "You can rent it in Dong-gu, Gwangju.", 70, R.drawable.i_hammok,1.2));
            ((CurUser) getApplication()).list.add(new Item("I have a sleeping bag for Army.", "I brought it as a supply from the army.", 70, R.drawable.i_bed, 2.5));
        }

        items=((CurUser) getApplication()).list;
        adapter.setItems(items);

        recyclerView.setAdapter(adapter);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        bottomNavigationView.getMenu().getItem(2).setChecked(true);

        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), RequestActivity.class);
                startActivity(i);
            }
        });
        rentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), RentFragment.class));
                startActivity(new Intent(getApplicationContext(), RentActivity.class));
            }
        });
    }

    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);

        intent.putExtra("title", items.get(position).getTitle());
        intent.putExtra("contents", items.get(position).getContents());
        intent.putExtra("price", items.get(position).getPrice());
        intent.putExtra("id", items.get(position).getId());
        intent.putExtra("distance", items.get(position).getDistance());

        startActivity(intent);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
            Intent intent;
            switch (item.getItemId()) {
                case R.id.home:
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    return true;
                case R.id.cart:
                    intent = new Intent(getApplicationContext(), CartActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    return true;
                case R.id.menu:
                    intent = new Intent(getApplicationContext(), CategoryActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    return true;
                case R.id.chat:
                    intent = new Intent(getApplicationContext(), ChatActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    return true;
                case R.id.myinfo:
                    intent = new Intent(getApplicationContext(), UserActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    return true;
            }
            return false;
        }
    };

}