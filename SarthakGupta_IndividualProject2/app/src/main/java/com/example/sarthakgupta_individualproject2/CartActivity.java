package com.example.sarthakgupta_individualproject2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;

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

        mAdapter = new CartAdapter(cartItemsList, user);
        recyclerView.setAdapter(mAdapter);

    }
}