package com.example.explorar.item;

import android.content.Intent;

public interface ItemCategory {
    Intent getIntent(Item item);
    Intent generateActivity(String title, String content, float lowerBound);
    Intent generateActivity(String title, String content, boolean status);
    Intent generateActivity(String title, String content);
}
