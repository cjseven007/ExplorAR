package com.example.explorar.ui.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.explorar.R;

import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageViewHolder> {
    private List<Message> messages;
    private Context context;

    public MessageListAdapter(Context context, List<Message> messages) {
        this.context = context;
        this.messages = messages;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_messages, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {

        Message message = messages.get(position);
        holder.textViewUsername.setText(message.username);
        holder.textViewMessage.setText(message.message);
        if (holder.textViewUsername.getText() == "You"){
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.purple_200));
            holder.textViewMessage.setTextColor(context.getResources().getColor(R.color.white));
        }

//        if (message.isLoading()) {
//            holder.progressBar.setVisibility(View.VISIBLE);
//        } else {
//            holder.progressBar.setVisibility(View.GONE);
//        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView textViewUsername;
        TextView textViewMessage;
        CardView cardView;

        ProgressBar progressBar;


        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUsername = itemView.findViewById(R.id.text_chat_user);
            textViewMessage = itemView.findViewById(R.id.text_chat_message);
            cardView = itemView.findViewById(R.id.card_chat_message);
//            progressBar = itemView.findViewById(R.id.chat_progress_bar);
        }
    }
}