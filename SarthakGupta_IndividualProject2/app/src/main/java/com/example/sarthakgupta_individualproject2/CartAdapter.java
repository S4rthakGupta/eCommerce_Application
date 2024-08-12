package com.example.sarthakgupta_individualproject2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private static List<CartDB> cartItems;
    private static String user = "";
    private static CartsAbstractClass cartdatabase;
    private ProductsAbstractClass productsDatabase;

    public CartAdapter(List<CartDB> cartItems, String user) {

        this.cartItems = cartItems;
        this.user = user;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_cart_details, parent, false);
        return new CartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        CartDB cart = cartItems.get(position);

        productsDatabase = Room.databaseBuilder(holder.itemView.getContext(), ProductsAbstractClass.class, "Products").allowMainThreadQueries().build();

        ProductsDB product = productsDatabase.productsDao().getProductById(cart.getProductId());

        holder.imgProductSmall.setImageResource(holder.itemView.getContext().getResources().getIdentifier(product.getProductImg(), "drawable", holder.itemView.getContext().getPackageName()));
        holder.txtItemName.setText(product.getProductName());
        holder.txtUnitPrice.setText("$" + product.getProductPrice());
        holder.txtQuantity1.setText(Integer.toString(cart.getQuantity()));

        Log.d("CartAdapter", "Product: " + product);
        Log.d("CartAdapter", "TextView: " + holder.txtItemName);

        // Check if item already exists in Cart for this user and product ID
//        boolean itemExists = cartdatabase.cartDao().checkIfItemExists(user, product.getId());
//        holder.btnAddToCart.setEnabled(!itemExists);
//
//        if (itemExists) {
//            holder.btnAddToCart.setText("Added to cart!");
//            holder.btnAddToCart.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.PrimaryColorDeselected));
//        } else {
//            holder.btnAddToCart.setText("Add to Cart");
//        }
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProductSmall;

        EditText txtQuantity1;
        TextView txtItemName, txtUnitPrice;

        CardView cardView;
        Button btnRemove1, btnDecreaseQuantity, btnIncreaseQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProductSmall = itemView.findViewById(R.id.cartProductImg);
            txtItemName = itemView.findViewById(R.id.productNameCart);
            txtUnitPrice = itemView.findViewById(R.id.productAmount);
            cardView = itemView.findViewById(R.id.cardView);
            txtQuantity1 = itemView.findViewById(R.id.cartQuantity);
            btnDecreaseQuantity = itemView.findViewById(R.id.quantityMinusCart);
            btnIncreaseQuantity = itemView.findViewById(R.id.quantityPlusCart);
            btnRemove1 = itemView.findViewById(R.id.removeBtnCart);

            btnDecreaseQuantity.setOnClickListener(v -> {
                int currentQuantity = Integer.parseInt(txtQuantity1.getText().toString());
                if (currentQuantity > 1) {
                    currentQuantity--;
                    txtQuantity1.setText(String.valueOf(currentQuantity));
                }
            });

            btnIncreaseQuantity.setOnClickListener(v -> {
                int currentQuantity = Integer.parseInt(txtQuantity1.getText().toString());
                currentQuantity++;
                txtQuantity1.setText(String.valueOf(currentQuantity));

            });

            cartdatabase = Room.databaseBuilder(itemView.getContext(), CartsAbstractClass.class,
                    "Cart").allowMainThreadQueries().build();

            btnRemove1.setOnClickListener(v -> {
                int position = getAdapterPosition();
                CartDB cart = cartItems.get(position);
                cartdatabase.cartDao().removeProduct(user, cart.getProductId());
                Toast.makeText(v.getContext(), "Removed from cart!", Toast.LENGTH_SHORT).show();
                cartItems.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, cartItems.size());
            });
        }
    }
}