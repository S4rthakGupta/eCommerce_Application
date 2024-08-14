package com.example.sarthakgupta_individualproject2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import android.widget.Toast;

public class CheckoutActivity extends AppCompatActivity {

    TextView amount;

    Button checkoutBtn;

    CartsAbstractClass cartDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_checkout_form);



        String totalPrice = getIntent().getStringExtra("TotalPrice");
        String checkoutName = getIntent().getStringExtra("Name");
        amount = findViewById(R.id.amountCheckout);
        amount.setText(totalPrice);



        checkoutBtn = findViewById(R.id.submitBtn);
        cartDataBase = Room.databaseBuilder(this, CartsAbstractClass.class,
                "Cart").allowMainThreadQueries().build();
        checkoutBtn.setOnClickListener(view -> {
            cartDataBase.cartDao().removeItemsByUsername(checkoutName);
            Toast.makeText(this, "Order Placed", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CheckoutActivity.this, ThankyouActivity.class);

            intent.putExtra("name", checkoutName);
            startActivity(intent);
        });
    }
}