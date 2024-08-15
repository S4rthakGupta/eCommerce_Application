package com.example.sarthakgupta_individualproject2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import android.widget.Toast;

public class CheckoutActivity extends AppCompatActivity {

    // Declare TextView and Button for interacting with the UI
    TextView amount;
    Button checkoutBtn;

    // Declare a variable for the Room database instance

    CartsAbstractClass cartDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Set the content view to the layout defined in activity_checkout_form.xml.
        setContentView(R.layout.activity_checkout_form);

        // Retrieve 'TotalPrice' and 'Name' extras from the intent that started this activity
        String totalPrice = getIntent().getStringExtra("TotalPrice");
        String checkoutName = getIntent().getStringExtra("Name");
        amount = findViewById(R.id.amountCheckout);
        amount.setText(totalPrice);

        // Find the UI elements by ID.
        TextView txtChckName = findViewById(R.id.nameCheckout);
        TextView txtChckEmail = findViewById(R.id.emailCheckout);
        TextView txtChckPhone = findViewById(R.id.phoneCheckout);
        TextView txtChckAddress = findViewById(R.id.addressCheckout);
        TextView txtChckCreditCard = findViewById(R.id.creditCardCheckout);
        TextView txtChckCvv = findViewById(R.id.CVVCheckout);

        checkoutBtn = findViewById(R.id.submitBtn);

        // Create an instance of the Room database and allow queries on the main thread (not recommended for production)
        cartDataBase = Room.databaseBuilder(this, CartsAbstractClass.class,
                "Cart").allowMainThreadQueries().build();

        // Set up a click listener for the 'checkoutBtn'.
        checkoutBtn.setOnClickListener(view -> {

            // Validating input fields.
            if (TextUtils.isEmpty(txtChckName.getText().toString().trim())) {
                Toast.makeText(CheckoutActivity.this, "Name is required", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(txtChckEmail.getText().toString().trim()).matches()) {
                Toast.makeText(CheckoutActivity.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(txtChckPhone.getText().toString().trim())) {
                Toast.makeText(CheckoutActivity.this, "Phone is required", Toast.LENGTH_SHORT).show();
                return;

            }if (TextUtils.isEmpty(txtChckAddress.getText().toString().trim())) {
                Toast.makeText(CheckoutActivity.this, "Address is required", Toast.LENGTH_SHORT).show();
                return;

            }if (TextUtils.isEmpty(txtChckCreditCard.getText().toString().trim())) {
                Toast.makeText(CheckoutActivity.this, "Card details are required", Toast.LENGTH_SHORT).show();
                return;

            }if (TextUtils.isEmpty(txtChckCvv.getText().toString().trim())) {
                Toast.makeText(CheckoutActivity.this, "CVV is required", Toast.LENGTH_SHORT).show();
                return;
            }

            // Remove items from the cart for the given username using the DAO
            cartDataBase.cartDao().removeItemsByUsername(checkoutName);
            Toast.makeText(this, "Order Placed", Toast.LENGTH_SHORT).show();

            // Create an intent to start the ThankyouActivity
            Intent intent = new Intent(CheckoutActivity.this, ThankyouActivity.class);

            // Pass the 'checkoutName' extra to the ThankyouActivity
            intent.putExtra("name", checkoutName);

            // Starting the ThankyouActivity
            startActivity(intent);
        });
    }
}