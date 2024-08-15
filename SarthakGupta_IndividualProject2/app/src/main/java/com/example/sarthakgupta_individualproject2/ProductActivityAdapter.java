package com.example.sarthakgupta_individualproject2;

import android.content.Intent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;


import java.io.Serializable;
import java.util.ArrayList;

public class ProductActivityAdapter extends RecyclerView.Adapter<ProductActivityAdapter.ViewHolder> {

    // Static ArrayList to hold product data.
    private static ArrayList<ProductsDB> products;
    private static String user = "";

    // Static variable for the cart database
    static CartsAbstractClass cartDB;


    // Constructor to initialize products and user variables.
    public ProductActivityAdapter(ArrayList<ProductsDB> products, String user) {

        this.products = products;
        this.user = user;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflating the layout for each item in the RecyclerView.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_products_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Getting the product at the current position.
        ProductsDB product = products.get(position);

        // Set product image, name, short description, and price.
        holder.imgProduct.setImageResource(holder.itemView.getContext().getResources().getIdentifier(product.getProductImg(), "drawable", holder.itemView.getContext().getPackageName()));
        holder.txtName.setText(product.getProductName());
        holder.txtShortDesc.setText(product.getProductShortDesc());
        holder.txtPrice.setText("$" + Double.toString(product.getProductPrice()));

        // Check if the item is already in the cart.
        boolean itemExists = cartDB.cartDao().checkIfItemExists(user, product.getId());

        // Enable or disable the "Add to Cart" button based on whether the item exists in the cart.
        holder.btnAddToCart.setEnabled(!itemExists);

        // Update button text based on whether the item is in the cart.
        if (itemExists) {
            holder.btnAddToCart.setText("Added to cart!");
        } else {
            holder.btnAddToCart.setText("Add to Cart");
        }
    }

    @Override
    public int getItemCount() {
        // Returning the total number of products.
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView txtName, txtShortDesc, txtPrice;

        CardView cardView;
        Button btnAddToCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initializing the views.
            imgProduct = itemView.findViewById(R.id.productImg);
            txtName = itemView.findViewById(R.id.textVieww);
            txtShortDesc = itemView.findViewById(R.id.productDesc);
            txtPrice = itemView.findViewById(R.id.productPrice);
            cardView = itemView.findViewById(R.id.productsCardView);
            btnAddToCart = itemView.findViewById(R.id.cartBtn);

            // Set up the OnClickListener for the CardView.
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    // Get the item position
                    ProductsDB product = products.get(position);

                    // Creating a bundle to pass product data to the ProductDetailActivity
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("product", (Serializable) product);

                    // Create an intent to start the ProductDetailActivity.
                    Intent intent = new Intent(v.getContext(), ProductDetailActivity.class);
                    intent.putExtras(bundle);
                    intent.putExtra("user", user);
                    v.getContext().startActivity(intent);
                }
            });


            // Initialize the cart database.
            cartDB = Room.databaseBuilder(itemView.getContext(), CartsAbstractClass.class,
                    "Cart").allowMainThreadQueries().build();

            // Set up the OnClickListener for the "Add to Cart" button.
            btnAddToCart.setOnClickListener(v -> {

                int position = getAdapterPosition();

                ProductsDB product = products.get(position);
                CartDB cartItem = new CartDB(user, product.getId(), 1);

                // Adding the product to cart.
                cartDB.cartDao().addProduct(cartItem);

                // Disable the button and update its text.
                btnAddToCart.setEnabled(false);
                btnAddToCart.setText("Added to cart!");
            });

        }
    }
}
