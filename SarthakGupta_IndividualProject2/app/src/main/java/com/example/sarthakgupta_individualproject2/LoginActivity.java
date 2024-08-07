package com.example.sarthakgupta_individualproject2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    Button loginButton, registerButton;
    EditText editEmail_id, editPassword;
    private FirebaseAuth mAuth;

    private static final int REGISTER_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        loginButton = findViewById(R.id.loginBtn);
        editEmail_id = findViewById(R.id.editLoginUsername);
        editPassword = findViewById(R.id.editLoginPassword);

        loginButton.setOnClickListener(v -> {
            String email = editEmail_id.getText().toString().trim();
            String password = editPassword.getText().toString().trim();

            // Validate email and password
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(LoginActivity.this, "Email is required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(LoginActivity.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "Password is required", Toast.LENGTH_SHORT).show();
                return;
            }

            // Sign in the user
            signInWithEmailAndPassword(email, password);
        });
    }

    private void signInWithEmailAndPassword(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in successful, proceed to ProductActivity
                        Intent intent = new Intent(LoginActivity.this, ProductActivity.class);
                        intent.putExtra("username", email);
                        startActivity(intent);
                        finish();
                    } else {
                        // Sign in failed, redirect to RegisterActivity
                        Toast.makeText(LoginActivity.this, "User not found. Redirecting to Registration...", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                        intent.putExtra("email", email);
                        intent.putExtra("password", password);
                        startActivityForResult(intent, REGISTER_REQUEST_CODE);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REGISTER_REQUEST_CODE && resultCode == RESULT_OK) {
            // Registration was successful, pre-fill the email and password
            String email = data.getStringExtra("email");
            String password = data.getStringExtra("password");
            editEmail_id.setText(email);
            editPassword.setText(password);
            // Optionally, you can auto-fill the login fields and show a message to the user
            Toast.makeText(LoginActivity.this, "Account created. Please log in.", Toast.LENGTH_SHORT).show();
        }
    }
}