package com.example.explorar.ui.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.explorar.GlobalVariables;
import com.example.explorar.MainActivity;
import com.example.explorar.R;
import com.example.explorar.ui.login.LoginActivity;
import com.example.explorar.user.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {
    private EditText studentIdEditText, nameEditText, emailEditText, passwordEditText;
    private TextView textView;
    private Button button;
    private ProgressBar progressbar;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Init FirebaseAuth instance
        firebaseAuth = FirebaseAuth.getInstance();

        nameEditText = findViewById(R.id.name_edit_text);
        studentIdEditText = findViewById(R.id.student_id_edit_text);
        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        textView = findViewById(R.id.login_text_view);
        button = findViewById(R.id.register_button);
        progressbar = findViewById(R.id.progressbar);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                registerNewUser();
            }
        });
    }

    private void registerNewUser()
    {
        progressbar.setVisibility(View.VISIBLE);

        String name, studentId, email, password;
        name = nameEditText.getText().toString();
        studentId = studentIdEditText.getText().toString();
        email = emailEditText.getText().toString();
        password = passwordEditText.getText().toString();

        // Validations for input email and password
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter name!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(studentId)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter student ID!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter email!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter password!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // create new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if (task.isSuccessful()) {
                        String uid = task.getResult().getUser().getUid();
                        UserData userData = new UserData(name, studentId, uid, email, new ArrayList<>(), new ArrayList<>());

                        GlobalVariables.setUserData(userData);

                        FirebaseFirestore.getInstance().collection("users").document(uid).set(userData);
                        Toast.makeText(getApplicationContext(),
                                        "Registration successful!",
                                        Toast.LENGTH_LONG)
                                .show();

                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        // Registration failed
                        Toast.makeText(getApplicationContext(),
                                "Registration failed!!"
                                        + " Please try again later",
                                Toast.LENGTH_LONG)
                        .show();
                    }
                    // hide the progress bar
                    progressbar.setVisibility(View.GONE);
                }
            });
    }
}