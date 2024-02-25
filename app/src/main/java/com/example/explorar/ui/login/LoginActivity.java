package com.example.explorar.ui.login;

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

import com.example.explorar.MainActivity;
import com.example.explorar.R;
import com.example.explorar.ui.register.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private TextView textView;
    private Button button;
    private ProgressBar progressbar;

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Init instance of FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        textView = findViewById(R.id.register_text_view);
        button = findViewById(R.id.login_button);
        progressbar = findViewById(R.id.progressbar);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                loginUserAccount();
            }
        });
    }

    private void loginUserAccount()
    {
        progressbar.setVisibility(View.VISIBLE);

        String email, password;
        email = emailEditText.getText().toString();
        password = passwordEditText.getText().toString();

        // validations for input email and password
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

        // login existing user
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(
                            @NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                            "Login successful!!",
                                            Toast.LENGTH_LONG)
                                    .show();

                            Intent intent
                                    = new Intent(LoginActivity.this,
                                    MainActivity.class);
                            startActivity(intent);
                        }

                        else {
                            // sign-in failed
                            Toast.makeText(getApplicationContext(),
                                            "Login failed!!",
                                            Toast.LENGTH_LONG)
                                    .show();
                        }
                        // hide the progress bar
                        progressbar.setVisibility(View.GONE);
                    }
                });
}
}