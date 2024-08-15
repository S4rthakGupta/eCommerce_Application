package com.example.sarthakgupta_individualproject2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;

public class CartActivity extends AppCompatActivity implements ItemUpdateListener
{

//    Initialzing Layout elements and Adapter
    ProductsAbstractClass productDB;
    TextView amount;
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    double TotalPrice = 0.0;

    Button checkoutBtn;

    CartsAbstractClass cartDB;

    List<CartDB> cartItemsList;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);

        // Get the username from the intent
        user = getIntent().getStringExtra("username");

        // Initialize RecyclerView and its components
        recyclerView = findViewById(R.id.rView_Cart);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Initialize databases
        cartDB = Room.databaseBuilder(this, CartsAbstractClass.class,
                "Cart").allowMainThreadQueries().build();
        productDB = Room.databaseBuilder(this, ProductsAbstractClass.class,
                "Products").allowMainThreadQueries().build();

        // Get cart items for the user and set up the adapter
        cartItemsList = (List<CartDB>) cartDB.cartDao().getProductByUser(user);
        mAdapter = new CartAdapter(cartItemsList, user, this);
        recyclerView.setAdapter(mAdapter);

        // Initialize UI components
        amount = findViewById(R.id.txtFinalAmount);
        checkoutBtn = findViewById(R.id.checkoutBtn);

        // Calculate and display the total price
        getTotalPrice();

        // Hide the checkout button if the cart is empty
        if (cartItemsList.isEmpty()) {
            checkoutBtn.setVisibility(View.GONE);
        }

        // Set up the checkout button click listener
        checkoutBtn.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
            intent.putExtra("TotalPrice", amount.getText().toString());
            intent.putExtra("Name", user);
            startActivity(intent);
        });
    }

    @Override
    public void onItemUpdate() {
        // Update cart items and total price when an item is updated
        cartItemsList = (List<CartDB>) cartDB.cartDao().getProductByUser(user);
        mAdapter.notifyDataSetChanged();
        getTotalPrice();
    }

    private void getTotalPrice() {
        TotalPrice = 0.0;
        // Iterate through cart items and calculate total price
        for (int i = 0; i < cartItemsList.size(); i++) {
            CartDB cartItem = cartItemsList.get(i);
            ProductsDB product = productDB.productsDao().getProductById(cartItem.getProductId());

//            If the product is there or not.
            if (product != null) {
                double unitPrice = product.getProductPrice();
                int quantity = cartItem.getQuantity();
                TotalPrice += unitPrice * quantity;
            } else {

                // Log error if product is not found
                Log.e("CartActivity", "Product not found for ID: " + cartItem.getProductId());
            }
        }
        // Applying tax and update the total price display.
        TotalPrice += TotalPrice * 0.13;
        amount.setText("Total Amount: $" + String.format("%.2f", TotalPrice));
    }
}