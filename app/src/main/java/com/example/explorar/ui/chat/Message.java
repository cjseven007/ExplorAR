package com.example.explorar.ui.chat;

public class Message {

    public boolean isLoading;
    String username;
    String message;

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }
}
