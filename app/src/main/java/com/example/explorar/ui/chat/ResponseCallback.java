package com.example.explorar.ui.chat;

public interface ResponseCallback {

    void onResponse(String response);
    void onError(Throwable throwable);
}
