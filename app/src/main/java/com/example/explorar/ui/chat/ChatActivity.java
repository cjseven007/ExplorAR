package com.example.explorar.ui.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.explorar.R;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private EditText queryEditText;
    private Button sendQueryButton;
    private RecyclerView recyclerView;
    private MessageListAdapter adapter;
    private List<Message> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page);

        queryEditText = findViewById(R.id.edit_query);
        sendQueryButton = findViewById(R.id.send_query);
        recyclerView = findViewById(R.id.recycler_chat);

        messageList = new ArrayList<>();
        adapter = new MessageListAdapter(this, messageList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sendQueryButton.setOnClickListener(v -> {
            String query = queryEditText.getText().toString();
            if (!query.isEmpty()) {
                sendMessage(query);
                queryEditText.setText("");
            } else {
                Toast.makeText(this, "Please enter a message", Toast.LENGTH_SHORT).show();
            }
        });

        String initialMessage = "Hi, I am Study Bot!\nYour dedicated AI chat bot to answer your questions regarding your studies.";
        addMessage("Gemini Buddy", initialMessage);
    }

    private void sendMessage(String query) {
//        OpenAI model = new OpenAI(ChatActivity.this);
//
//        model.getResponse(query, new ResponseCallback() {
//            @Override
//            public void onResponse(String response) {
//                runOnUiThread(() -> {
//                    addMessage("You", query);
//                    addMessage("Gemini Buddy", response);
//                    scrollToBottom();
//                    showNoChatImage(); // Show Image when empty chat
//                });
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//                System.out.println(throwable.getMessage());
//                runOnUiThread(() -> Toast.makeText(ChatActivity.this, "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show());
//            }
//        });
        Gemini model = new Gemini();

        model.getResponse(query, new ResponseCallback() {
            @Override
            public void onResponse(String response) {
                runOnUiThread(() -> {
                    addMessage("You", query);
                    addMessage("Gemini Buddy", response);
                    scrollToBottom();
                    showNoChatImage(); // Show Image when empty chat
                });
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println(throwable.getMessage());
                runOnUiThread(() -> Toast.makeText(ChatActivity.this, "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show());
            }
        }, messageList);
    }

    private void showNoChatImage() {
        ImageView noChatImage = findViewById(R.id.no_chat_image);
        if (messageList.isEmpty()) {
            noChatImage.setVisibility(View.VISIBLE);
        } else {
            noChatImage.setVisibility(View.GONE);
        }
    }

    private void addMessage(String username, String message) {
        Message newMessage = new Message();
        newMessage.username = username;
        newMessage.message = message;
        messageList.add(newMessage);
        adapter.notifyItemInserted(messageList.size() - 1);
    }

    private void scrollToBottom() {
        recyclerView.scrollToPosition(messageList.size() - 1);
    }
}
