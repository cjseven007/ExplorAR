package com.example.explorar.reading;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.explorar.R;

public class ReadingActivity extends AppCompatActivity {

    private String title;
    private String content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);

        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");

        TextView titleTextView = findViewById(R.id.title_text_view);
        TextView contentTextView = findViewById(R.id.content_text_view);

        titleTextView.setText(title);
        contentTextView.setText(content);
    }
}