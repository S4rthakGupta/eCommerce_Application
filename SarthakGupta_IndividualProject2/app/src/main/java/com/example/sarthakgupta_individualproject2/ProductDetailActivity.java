package com.example.sarthakgupta_individualproject2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class ProductDetailActivity extends AppCompatActivity {

    TextView title, price, longDescription;
    EditText txtQuantity;
    ImageView mainImage;
    ProductsAbstractClass productDB;
    CartsAbstractClass CartDB;
    Button btnAddToCart, btnDecreaseQuantity, btnIncreaseQuantity, btnRemove, goToCartBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_detail);

        title = findViewById(R.id.textView);
        longDescription = findViewById(R.id.txtDescription);
        mainImage = findViewById(R.id.mainImage);
        price = findViewById(R.id.txtPrice1);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        btnDecreaseQuantity = findViewById(R.id.quantityMinusCart);
        btnIncreaseQuantity = findViewById(R.id.quantityPlusCart);
        goToCartBtn = findViewById(R.id.goToCartBtn);
        txtQuantity = findViewById(R.id.txtQuantity);
        btnRemove = findViewById(R.id.btnRemove);

        productDB = Room.databaseBuilder(getApplicationContext(), ProductsAbstractClass.class,
                "Products").allowMainThreadQueries().build();
        CartDB = Room.databaseBuilder(getApplicationContext(), CartsAbstractClass.class,
                "Cart").allowMainThreadQueries().build();

        Bundle bundle = getIntent().getExtras();
        String user = getIntent().getStringExtra("user");
        ProductsDB product = (ProductsDB) bundle.getSerializable("product");

        title.setText(product.getProductName());
        Double Price = product.getProductPrice();
        longDescription.setText(product.getProductLongDesc());

        mainImage.setImageResource(getResources().getIdentifier(product.getProductImg(), "drawable", getPackageName()));
        price.setText("Price: $" + Price);

        boolean itemExists = CartDB.cartDao().checkIfItemExists(user, product.getId());
        if (itemExists) {
            int existingQuantity = CartDB.cartDao().getExistingQuantity(user, product.getId());
            txtQuantity.setText(String.valueOf(existingQuantity + 1));
            btnRemove.setVisibility(View.VISIBLE);
        }
        else {
            txtQuantity.setText("1");
            btnRemove.setVisibility(View.GONE);
        }


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

        btnAddToCart.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(txtQuantity.getText().toString());


            if (itemExists) {
                // Item exists, update quantity
                CartDB.cartDao().updateQuantity(currentQuantity, user, product.getId());
                Toast.makeText(v.getContext(), "Cart Updated!", Toast.LENGTH_SHORT).show();
            } else {
                // Item doesn't exist, add to cart
                CartDB cartItem = new CartDB(user, product.getId(), currentQuantity);
                CartDB.cartDao().addProduct(cartItem);
                Toast.makeText(v.getContext(), "Added to cart!", Toast.LENGTH_SHORT).show();
            }
            finish();
            startActivity(getIntent());

        });

        btnRemove.setOnClickListener(v -> {
            CartDB.cartDao().removeProduct(user, product.getId());
            Toast.makeText(v.getContext(), "Removed from cart!", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(getIntent());
        });

        goToCartBtn.setOnClickListener(v -> {

            Intent intent = new Intent(this, CartActivity.class);
            intent.putExtra("username", user);
            startActivity(intent);
        } );

    }
}
