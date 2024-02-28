package com.example.explorar.reading;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.explorar.R;

public class ReadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);

        String content = getIntent().getStringExtra("content");

        TextView textView = findViewById(R.id.title_text_view);
        textView.setText(content);
    }
}