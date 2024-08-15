package com.example.sarthakgupta_individualproject2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

// MainActivity is the entry point of the application, extending AppCompatActivity to provide compatibility support.

public class MainActivity extends AppCompatActivity {
    Button startButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startBtn);

        // Set an OnClickListener on the startButton to handle click events.

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // When the button is clicked, create an Intent to start the LoginActivity.
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);

                // Start the LoginActivity.
                startActivity(intent);
            }
        });
    }
}
