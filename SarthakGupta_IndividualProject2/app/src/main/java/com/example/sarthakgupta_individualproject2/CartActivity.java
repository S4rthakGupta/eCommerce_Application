package com.example.sarthakgupta_individualproject2;

import android.os.Bundle;
import android.util.Log;
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
        user = getIntent().getStringExtra("username");
        recyclerView = findViewById(R.id.rView_Cart);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        cartDB = Room.databaseBuilder(this, CartsAbstractClass.class,
                "Cart").allowMainThreadQueries().build();
        cartItemsList = (List<CartDB>) cartDB.cartDao().getProductByUser(user);
        productDB = Room.databaseBuilder(this, ProductsAbstractClass.class,
                "Products").allowMainThreadQueries().build();

        mAdapter = new CartAdapter(cartItemsList, user, this);
        recyclerView.setAdapter(mAdapter);

        amount = findViewById(R.id.txtFinalAmount);
        checkoutBtn = findViewById(R.id.checkoutBtn);
        getTotalPrice();
    }


    @Override
    public void onItemUpdate() {
        cartItemsList = (List<CartDB>) cartDB.cartDao().getProductByUser(user);
        mAdapter.notifyDataSetChanged();
        getTotalPrice();
    }

    private void getTotalPrice() {
        TotalPrice = 0.0;
        for (int i = 0; i < cartItemsList.size(); i++) {
            CartDB cartItem = cartItemsList.get(i);
            ProductsDB product = productDB.productsDao().getProductById(cartItem.getProductId());
            if (product != null) {
                double unitPrice = product.getProductPrice();
                int quantity = cartItem.getQuantity();
                TotalPrice += unitPrice * quantity;
            } else {
                // Log the missing product ID
                Log.e("CartActivity", "Product not found for ID: " + cartItem.getProductId());
            }
        }
        TotalPrice += TotalPrice * 0.13; // Assuming this is for tax
        amount.setText("Total Amount: $" + String.format("%.2f", TotalPrice));
    }
}