package com.example.explorar;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.explorar.databinding.ActivityMainBinding;
import com.example.explorar.ui.chat.ChatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        FloatingActionButton ar_fab = findViewById(R.id.chat_button);
        ar_fab.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, ChatActivity.class)));

        BottomNavigationView navView = findViewById(R.id.nav_view);

        binding.navView.post(() -> {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
            NavigationUI.setupWithNavController(binding.navView, navController);
        });
    }
}
