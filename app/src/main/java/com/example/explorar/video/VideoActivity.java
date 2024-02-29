package com.example.explorar.video;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.example.explorar.R;

public class VideoActivity extends AppCompatActivity {

    private String title;
    private String content;
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");

        webView = findViewById(R.id.web_view);
        webView.loadData(content, "text/html", "utf-8");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
    }
}