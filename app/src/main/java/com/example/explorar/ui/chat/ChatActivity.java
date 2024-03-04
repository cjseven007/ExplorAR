package com.example.explorar.ui.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.explorar.R;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page);

        EditText queryEditText = findViewById(R.id.edit_query);
        Button sendQueryButton = findViewById(R.id.send_query);
        TextView responseTextView = findViewById(R.id.response_text);
        ProgressBar progressBar = findViewById(R.id.chat_progress_bar);

        sendQueryButton.setOnClickListener(v->{
            Gemini model = new Gemini();
            String query = queryEditText.getText().toString();
            responseTextView.setText("");
            queryEditText.setText("");
            progressBar.setVisibility(View.VISIBLE);

        model.getResponse(query, new ResponseCallback() {
            @Override
            public void onResponse(String response) {

                responseTextView.setText(response);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(Throwable throwable) {
                Toast.makeText(ChatActivity.this, "Error" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
        } );

    }


}