package com.example.mokytojas.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setTitle(R.string.searchTitle);

        Button insertBtn = findViewById(R.id.insertBtn);

        insertBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent goToNewEntryActivity = new Intent(SearchActivity.this, NewEntryActivity.class);
                startActivity(goToNewEntryActivity);
            }
        });
    }
}
