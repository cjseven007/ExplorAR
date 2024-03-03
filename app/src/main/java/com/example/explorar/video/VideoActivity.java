package com.example.explorar.video;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.explorar.R;

public class VideoActivity extends AppCompatActivity {

    private String title;
    private String content;
    private boolean status;
    private float lowerBound;
    private WebView webView;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        status = getIntent().getBooleanExtra("status", false);
        lowerBound = getIntent().getFloatExtra("lowerBound", 0.5f);

        textView = findViewById(R.id.title_text_view);
        textView.setText(title);

        webView = findViewById(R.id.web_view);
        webView.loadData(content, "text/html", "utf-8");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
    }
}