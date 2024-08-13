package com.example.sarthakgupta_individualproject2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ProductsDB> productList;
    ProductsAbstractClass productDB;

    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_products);
        user = getIntent().getStringExtra("username");

        recyclerView = findViewById(R.id.rView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        RoomDatabase.Callback myCallBack = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
            }
        };

        productDB = Room.databaseBuilder(getApplicationContext(), ProductsAbstractClass.class,
                "Products").allowMainThreadQueries().build();


//        ProductsDB p1 = new ProductsDB(
//                "keyboard1",
//                "Keychron K1 Mechanical Keyboard",
//                139.99,
//                "Slim mechanical keyboard with RGB backlighting.",
//                "The Keychron K1 is a slim mechanical keyboard featuring RGB backlighting and a low-profile design. It offers a tactile typing experience with its Gateron mechanical switches and supports both Windows and macOS. The ultra-thin design makes it perfect for both office and home use."
//        );
//
//        productDB.productsDao().addProduct(p1);
//
//        ProductsDB p2 = new ProductsDB(
//                "keyboard2",
//                "Keychron K6 Wireless Mechanical Keyboard",
//                99.99,
//                "Compact 65% wireless mechanical keyboard.",
//                "The Keychron K6 is a compact 65% mechanical keyboard with wireless connectivity. It features hot-swappable switches, allowing you to customize your typing experience, and supports multiple devices with its Bluetooth 5.1 capability. Ideal for a minimalist setup and versatile usage."
//        );
//
//        productDB.productsDao().addProduct(p2);
//
//        ProductsDB p3 = new ProductsDB(
//                "keyboard3",
//                "Keychron K8 Tenkeyless Mechanical Keyboard",
//                119.99,
//                "Compact tenkeyless design with RGB backlighting.",
//                "The Keychron K8 is a tenkeyless mechanical keyboard with customizable RGB backlighting. It features hot-swappable switches and supports both wired and wireless connections. Its compact design makes it perfect for users who prefer more desk space without sacrificing functionality."
//        );
//
//        productDB.productsDao().addProduct(p3);
//
//        ProductsDB p4 = new ProductsDB(
//                "keyboard4",
//                "Keychron K3 Ultra-Slim Mechanical Keyboard",
//                129.99,
//                "Ultra-slim design with low-profile switches.",
//                "The Keychron K3 is an ultra-slim mechanical keyboard featuring low-profile Gateron switches and RGB backlighting. It's designed for those who want a sleek, portable keyboard without compromising on typing quality. Perfect for both office work and on-the-go use."
//        );
//
//        productDB.productsDao().addProduct(p4);
//
//        ProductsDB p5 = new ProductsDB(
//                "keyboard5",
//                "Keychron K7 Hot-Swappable Mechanical Keyboard",
//                149.99,
//                "Compact 65% layout with hot-swappable switches.",
//                "The Keychron K7 offers a 65% compact layout with hot-swappable switches, allowing for easy customization of your typing experience. It features RGB backlighting and supports both Bluetooth and wired connections, making it a versatile choice for any setup."
//        );
//
//        productDB.productsDao().addProduct(p5);
//
//        ProductsDB p6 = new ProductsDB(
//                "keyboard6",
//                "Keychron K1 Backlit Mechanical Keyboard",
//                159.99,
//                "Sleek and slim with adjustable backlighting.",
//                "The Keychron K1 is a sleek and slim mechanical keyboard with adjustable RGB backlighting. Its low-profile switches provide a comfortable typing experience, and it supports both macOS and Windows. Ideal for users who appreciate a minimalist design with high functionality."
//        );
//
//        productDB.productsDao().addProduct(p6);
//
//        ProductsDB p7 = new ProductsDB(
//                "keyboard7",
//                "Keychron K6 Hot-Swappable Mechanical Keyboard",
//                109.99,
//                "65% layout with customizable switches.",
//                "The Keychron K6 is a 65% compact mechanical keyboard with hot-swappable switches and customizable RGB backlighting. It supports wireless Bluetooth connections and is perfect for users who need a compact, versatile keyboard for various tasks."
//        );
//
//        productDB.productsDao().addProduct(p7);
//
//        ProductsDB p8 = new ProductsDB(
//                "keyboard8",
//                "Keychron K8 Wireless Mechanical Keyboard",
//                139.99,
//                "Wireless connectivity with a tenkeyless design.",
//                "The Keychron K8 combines wireless connectivity with a tenkeyless mechanical design. It features RGB backlighting, hot-swappable switches, and both Bluetooth and wired modes. Ideal for users who want a compact and flexible keyboard for any setup."
//        );
//
//        productDB.productsDao().addProduct(p8);


        productList = (ArrayList<ProductsDB>) productDB.productsDao().listProducts();
        mAdapter = new ProductActivityAdapter(productList, user);
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        productList = (ArrayList<ProductsDB>) productDB.productsDao().listProducts();
        mAdapter = new ProductActivityAdapter(productList, user);
        recyclerView.setAdapter(mAdapter);
    }
}