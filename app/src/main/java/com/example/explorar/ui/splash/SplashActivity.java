package com.example.explorar.ui.splash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.explorar.GlobalVariables;
import com.example.explorar.MainActivity;
import com.example.explorar.R;
import com.example.explorar.ui.login.LoginActivity;
import com.example.explorar.ui.reconnect.ReconnectActivity;
import com.example.explorar.user.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // on below line we are configuring our window to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        GlobalVariables.setUserData(new UserData());

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            String uid = firebaseAuth.getCurrentUser().getUid();
            FirebaseFirestore.getInstance().collection("users").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        UserData userData = new UserData(
                                (String) task.getResult().get("name"),
                                (String) task.getResult().get("studentId"),
                                (String) task.getResult().get("userId"),
                                (String) task.getResult().get("email"),
                                (ArrayList<String>) task.getResult().get("courses"),
                                (List<Map<String, Object>>) task.getResult().get("completed")
                        );

                        GlobalVariables.setUserData(userData);

                        // on below line we are calling handler to run a task
                        // for specific time interval
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }, 1500);
                    } else {
                        Intent intent = new Intent(SplashActivity.this, ReconnectActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        } else {
            // on below line we are calling handler to run a task
            // for specific time interval
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 2500);
        }
    }
}