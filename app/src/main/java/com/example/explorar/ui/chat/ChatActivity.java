package com.example.explorar.ui.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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

        sendQueryButton.setOnClickListener(v->{
            Gemini model = new Gemini();
            String query = queryEditText.getText().toString();
            responseTextView.setText("");
            queryEditText.setText("");

        model.getResponse(query, new ResponseCallback() {
            @Override
            public void onResponse(String response) {
                responseTextView.setText(response);
            }

            @Override
            public void onError(Throwable throwable) {
                Toast.makeText(ChatActivity.this, "Error" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        } );

    }


}