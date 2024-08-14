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

    // Declare UI elements and Firebase authentication object.
    Button loginButton;
    EditText editEmail_id, editPassword;
    private FirebaseAuth mAuth;

    // Request code for registering a new user.
    private static final int REGISTER_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth instance for user authentication.
        mAuth = FirebaseAuth.getInstance();

        // Link UI elements to their corresponding views in the XML layout.
        loginButton = findViewById(R.id.loginBtn);
        editEmail_id = findViewById(R.id.editLoginUsername);
        editPassword = findViewById(R.id.editLoginPassword);

        // Set an OnClickListener on the loginButton to handle user login attempts.
        loginButton.setOnClickListener(v -> {
            String email = editEmail_id.getText().toString().trim();
            String password = editPassword.getText().toString().trim();

            // Validating email and password fields.
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

            // If validation passes, attempt to sign in the user.
            signInWithEmailAndPassword(email, password);
        });
    }

    // Method to handle user sign-in using Firebase Authentication.
    private void signInWithEmailAndPassword(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // If sign-in is successful, start ProductActivity and pass the user's email.
                        Intent intent = new Intent(LoginActivity.this, ProductActivity.class);

                        // Passing the username to use it on the product page.
                        intent.putExtra("username", email);
                        startActivity(intent);

                        // Finish the LoginActivity to prevent going back to it.
                        finish();
                    } else {
                        // If sign-in fails, redirect the user to the RegisterActivity.
                        Toast.makeText(LoginActivity.this, "User not found. Redirecting to Registration...", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                        intent.putExtra("email", email);
                        intent.putExtra("password", password);
                        startActivityForResult(intent, REGISTER_REQUEST_CODE);
                    }
                });
    }


    // Handle the result from the RegisterActivity after a user attempts to register.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REGISTER_REQUEST_CODE && resultCode == RESULT_OK) {
            // If registration was successful, retrieve the email and password from the result data.
            String email = data.getStringExtra("email");
            String password = data.getStringExtra("password");

            // Pre-fill the email and password fields with the registered user's credentials.
            editEmail_id.setText(email);
            editPassword.setText(password);

            // Show a message to the user indicating that the account was created.
            Toast.makeText(LoginActivity.this, "Account created. Please log in.", Toast.LENGTH_SHORT).show();
        }
    }
}