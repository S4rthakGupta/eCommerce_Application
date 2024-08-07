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

public class RegisterActivity extends AppCompatActivity {

    Button registerButton;
    EditText editEmail_id, editPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        registerButton = findViewById(R.id.registerBtn);
        editEmail_id = findViewById(R.id.editRegisterUsername);
        editPassword = findViewById(R.id.editRegisterPassword);

        // Pre-fill the email and password if they were passed from LoginActivity
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        String password = intent.getStringExtra("password");

        if (email != null) {
            editEmail_id.setText(email);
        }

        if (password != null) {
            editPassword.setText(password);
        }

        registerButton.setOnClickListener(v -> {
            String inputEmail = editEmail_id.getText().toString().trim();
            String inputPassword = editPassword.getText().toString().trim();

            // Validate email and password
            if (TextUtils.isEmpty(inputEmail)) {
                Toast.makeText(RegisterActivity.this, "Email is required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches()) {
                Toast.makeText(RegisterActivity.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(inputPassword)) {
                Toast.makeText(RegisterActivity.this, "Password is required", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create user with Firebase Auth
            mAuth.createUserWithEmailAndPassword(inputEmail, inputPassword)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // User created successfully, show success message and pass data back to LoginActivity
                            Toast.makeText(RegisterActivity.this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("email", inputEmail);
                            resultIntent.putExtra("password", inputPassword);
                            setResult(RESULT_OK, resultIntent);
                            finish();
                        } else {
                            // Registration failed, show error message
                            Toast.makeText(RegisterActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}