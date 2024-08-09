package com.example.sarthakgupta_individualproject2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {

    TextView title, price;
    EditText txtQuantity;
    ImageView mainImage, secondImage, thirdImage;
    ProductsAbstractClass productDB;
//    CartDatabase cartdatabase;
    Button btnAddToCart, btnDecreaseQuantity, btnIncreaseQuantity, btnRemove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_detail);

        title = findViewById(R.id.productName);
        mainImage = findViewById(R.id.mainImage);
        price = findViewById(R.id.txtPrice1);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        btnDecreaseQuantity = findViewById(R.id.btnDecreaseQuantity);
        btnIncreaseQuantity = findViewById(R.id.btnIncreaseQuantity);
        txtQuantity = findViewById(R.id.txtQuantity);

        List<ProductsDB> arrImages;
        productDB = Room.databaseBuilder(getApplicationContext(), ProductsAbstractClass.class,
                "Products").allowMainThreadQueries().build();
//        cartdatabase = Room.databaseBuilder(getApplicationContext(), CartDatabase.class,
//                "Cart").allowMainThreadQueries().build();

        Bundle bundle = getIntent().getExtras();
        String user = getIntent().getStringExtra("user");
        ProductsDB product = (ProductsDB) bundle.getSerializable("product");

        title.setText(product.getProductName());
//        arrImages = productDB.productsDao().getImagesByProductId(product.getId());
        Double Textprice = product.getProductPrice();


        mainImage.setImageResource(getResources().getIdentifier(product.getProductImg(), "drawable", getPackageName()));
        price.setText("Price: $" + Textprice);

//        boolean itemExists = cartdatabase.cartDao().checkIfItemExists(user, product.getId());
//        if (itemExists) {
//            int existingQuantity = cartdatabase.cartDao().getExistingQuantity(user, product.getId());
//            txtQuantity.setText(String.valueOf(existingQuantity + 1));
//        }


        btnDecreaseQuantity.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(txtQuantity.getText().toString());
            if (currentQuantity > 1) {
                currentQuantity--;
                txtQuantity.setText(String.valueOf(currentQuantity));
            }
        });

        btnIncreaseQuantity.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(txtQuantity.getText().toString());
            currentQuantity++;
            txtQuantity.setText(String.valueOf(currentQuantity));

        });

//        btnAddToCart.setOnClickListener(v -> {
//            int currentQuantity = Integer.parseInt(txtQuantity.getText().toString());
//
//
//            if (itemExists) {
//                // Item exists, update quantity
//                cartdatabase.cartDao().updateQuantity(currentQuantity, user, product.getId());
//                Toast.makeText(v.getContext(), "Cart Updated!", Toast.LENGTH_SHORT).show();
//            } else {
//                // Item doesn't exist, add to cart
//                CartDB cartItem = new CartDB(user, product.getId(), currentQuantity);
//                cartdatabase.cartDao().addProduct(cartItem);
//                Toast.makeText(v.getContext(), "Added to cart!", Toast.LENGTH_SHORT).show();
//            }
//
//        });


    }
}
