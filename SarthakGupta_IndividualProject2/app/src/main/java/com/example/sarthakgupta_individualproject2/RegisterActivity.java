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

    // Declare UI elements and Firebase authentication object.
    Button registerButton;
    EditText editEmail_id, editPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Set the content view to the XML layout file activity_register.xml.
        setContentView(R.layout.activity_register);

        // Initializing Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Link UI elements to their corresponding views in the XML layout.
        registerButton = findViewById(R.id.registerBtn);
        editEmail_id = findViewById(R.id.editRegisterUsername);
        editPassword = findViewById(R.id.editRegisterPassword);

        // Get the intent that started this activity and pre-fill email and password fields if passed from LoginActivity.
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        String password = intent.getStringExtra("password");

//        Pre-fill email and password fields
        if (email != null) {
            editEmail_id.setText(email);
        }

        if (password != null) {
            editPassword.setText(password);
        }

        // Set an OnClickListener on the registerButton to handle user registration attempts.
        registerButton.setOnClickListener(v -> {

            // Retrieve the email and password entered by the user.
            String inputEmail = editEmail_id.getText().toString().trim();
            String inputPassword = editPassword.getText().toString().trim();

            // Validating email and password
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

            // Create a new user with Firebase Authentication.
            mAuth.createUserWithEmailAndPassword(inputEmail, inputPassword)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // If registration is successful, show a success message and pass the user's credentials back to LoginActivity.
                            Toast.makeText(RegisterActivity.this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("email", inputEmail);
                            resultIntent.putExtra("password", inputPassword);
                            setResult(RESULT_OK, resultIntent);
                            // Close RegisterActivity and return to LoginActivity.
                            finish();
                        } else {
                            // If registration fails, show an error message with the reason.
                            Toast.makeText(RegisterActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}