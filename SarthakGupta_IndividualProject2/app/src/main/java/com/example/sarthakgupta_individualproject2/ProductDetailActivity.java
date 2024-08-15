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

import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {

    // Declare UI elements and database references.
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

        // Set the content view to the associated layout
        setContentView(R.layout.activity_product_detail);

        // Initializing UI elements by linking them with their respective layout IDs.
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

        // Initializing the Room database instances for products and cart.
        productDB = Room.databaseBuilder(getApplicationContext(), ProductsAbstractClass.class,
                "Products").allowMainThreadQueries().build();
        CartDB = Room.databaseBuilder(getApplicationContext(), CartsAbstractClass.class,
                "Cart").allowMainThreadQueries().build();

        // Retrieving the passed data (user and product details) from the intent.
        Bundle bundle = getIntent().getExtras();
        String user = getIntent().getStringExtra("user");
        ProductsDB product = (ProductsDB) bundle.getSerializable("product");

        // Setting the product details on the UI elements.
        title.setText(product.getProductName());
        Double Price = product.getProductPrice();
        longDescription.setText(product.getProductLongDesc());

        // Setting the main image resource based on the product image name.
        mainImage.setImageResource(getResources().getIdentifier(product.getProductImg(), "drawable", getPackageName()));
        price.setText("Price: $" + Price);

        // Checking if the product already exists in the cart.
        boolean itemExists = CartDB.cartDao().checkIfItemExists(user, product.getId());
        if (itemExists) {
            // If the item exists, retrieve the existing quantity and display it.
            int existingQuantity = CartDB.cartDao().getExistingQuantity(user, product.getId());
            txtQuantity.setText(String.valueOf(existingQuantity + 1));
            btnRemove.setVisibility(View.VISIBLE);
        }
        else {
            // If the item does not exist, setting the quantity to 1 and hide the remove button
            txtQuantity.setText("1");
            btnRemove.setVisibility(View.GONE);
        }

        // Decreasing the quantity when the minus button is clicked.
        btnDecreaseQuantity.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(txtQuantity.getText().toString());
            if (currentQuantity > 1) {
                currentQuantity--;
                txtQuantity.setText(String.valueOf(currentQuantity));
            }
        });

        // Increasing the quantity when the plus button is clicked.
        btnIncreaseQuantity.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(txtQuantity.getText().toString());
            currentQuantity++;
            txtQuantity.setText(String.valueOf(currentQuantity));

        });

        // Adding the product to the cart or update the quantity when the add button is clicked.
        btnAddToCart.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(txtQuantity.getText().toString());


            if (itemExists) {
                // If the item exists, quantity in the cart will be updated.
                CartDB.cartDao().updateQuantity(currentQuantity, user, product.getId());
                Toast.makeText(v.getContext(), "Cart Updated!", Toast.LENGTH_SHORT).show();
            } else {
                // If the item does not exist, add it to the cart.
                CartDB cartItem = new CartDB(user, product.getId(), currentQuantity);
                CartDB.cartDao().addProduct(cartItem);
                Toast.makeText(v.getContext(), "Added to cart!", Toast.LENGTH_SHORT).show();
            }
            finish();
            startActivity(getIntent());
        });

        // Removing the product from the cart when the remove button is clicked.
        btnRemove.setOnClickListener(v -> {
            CartDB.cartDao().removeProduct(user, product.getId());
            Toast.makeText(v.getContext(), "Removed from cart!", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(getIntent());
        });

        List<CartDB> cartItemsList = (List<CartDB>) CartDB.cartDao().getProductByUser(user);

        // It will go to the cart activity when the go-to-cart button is clicked.
        goToCartBtn.setOnClickListener(v -> {
            if (cartItemsList.isEmpty()) {
                Toast.makeText(v.getContext(), "Your cart is empty!", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(this, CartActivity.class);
            intent.putExtra("username", user);
            startActivity(intent);

        } );

    }
}
