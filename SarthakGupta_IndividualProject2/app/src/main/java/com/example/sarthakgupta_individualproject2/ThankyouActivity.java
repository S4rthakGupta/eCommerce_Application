package com.example.sarthakgupta_individualproject2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class ThankyouActivity extends AppCompatActivity {

    Button products, signOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thankyou);

        products = findViewById(R.id.moreProducts);
        signOut = findViewById(R.id.signoutBtn);
        String name = getIntent().getStringExtra("name");
        products.setOnClickListener(view -> {
            Intent intent = new Intent(ThankyouActivity.this, ProductActivity.class);

            intent.putExtra("username", name);
            startActivity(intent);
        });

            signOut.setOnClickListener(view -> {
            Intent intent = new Intent(ThankyouActivity.this, MainActivity.class);
            startActivity(intent);
        });

    }
}