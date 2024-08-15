package com.example.sarthakgupta_individualproject2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ThankyouActivity extends AppCompatActivity {

    // Declaring buttons for interacting with the UI.
    Button products, signOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Set the content view to the layout defined in activity_thankyou.xml.
        setContentView(R.layout.activity_thankyou);


        // Initialize buttons by finding them in the layout.
        products = findViewById(R.id.moreProducts);
        signOut = findViewById(R.id.signoutBtn);

        // Retrieve the 'name' extra from the intent that started this activity
        String name = getIntent().getStringExtra("name");

        // Set up a click listener for the 'products' button.
        products.setOnClickListener(view -> {

            // Create an intent to start the ProductActivity
            Intent intent = new Intent(ThankyouActivity.this, ProductActivity.class);
            intent.putExtra("username", name);
            startActivity(intent);
        });

        // Set up a click listener for the 'signOut' button
        signOut.setOnClickListener(view -> {

            // Create an intent to start the MainActivity (which could be the login screen)
            Intent intent = new Intent(ThankyouActivity.this, MainActivity.class);
            startActivity(intent);
        });

    }
}