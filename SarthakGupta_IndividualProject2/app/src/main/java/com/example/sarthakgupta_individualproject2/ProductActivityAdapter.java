package com.example.sarthakgupta_individualproject2;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;


import java.io.Serializable;
import java.util.ArrayList;

public class ProductActivityAdapter extends RecyclerView.Adapter<ProductActivityAdapter.ViewHolder> {

    private static ArrayList<ProductsDB> products;
    private static String user = "";
    static CartsAbstractClass cartDB;

    public ProductActivityAdapter(ArrayList<ProductsDB> products, String user) {

        this.products = products;
        this.user = user;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_products_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductsDB product = products.get(position);
        holder.imgProduct.setImageResource(holder.itemView.getContext().getResources().getIdentifier(product.getProductImg(), "drawable", holder.itemView.getContext().getPackageName()));
        holder.txtName.setText(product.getProductName());
        holder.txtShortDesc.setText(product.getProductShortDesc());
        holder.txtPrice.setText("$" + Double.toString(product.getProductPrice()));

        // Check if item already exists in Cart for this user and product ID
        boolean itemExists = cartDB.cartDao().checkIfItemExists(user, product.getId());
        holder.btnAddToCart.setEnabled(!itemExists);

        if (itemExists) {
            holder.btnAddToCart.setText("Added to cart!");
//            holder.btnAddToCart.setBackgroundColor(holder.itemView.getContext().getResources().getColor(R.color.bluegreycolor));
        } else {
            holder.btnAddToCart.setText("Add to Cart");
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView txtName, txtShortDesc, txtPrice;

        CardView cardView;
        Button btnAddToCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.productImg);
            txtName = itemView.findViewById(R.id.productName);
            txtShortDesc = itemView.findViewById(R.id.productDesc);
            txtPrice = itemView.findViewById(R.id.productPrice);
            cardView = itemView.findViewById(R.id.productsCardView);
            btnAddToCart = itemView.findViewById(R.id.cartBtn);

//             Set onClickListener here
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition(); // Get the item position

                    ProductsDB product = products.get(position);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("product", (Serializable) product);

                    Intent intent = new Intent(v.getContext(), ProductDetailActivity.class);
                    intent.putExtras(bundle);
                    intent.putExtra("user", user);
                    v.getContext().startActivity(intent);


                }
            });

            cartDB = Room.databaseBuilder(itemView.getContext(), CartsAbstractClass.class,
                    "Cart").allowMainThreadQueries().build();

            btnAddToCart.setOnClickListener(v -> {

                int position = getAdapterPosition();

                ProductsDB product = products.get(position);
                CartDB cartItem = new CartDB(user, product.getId(), 1);
                cartDB.cartDao().addProduct(cartItem);
                btnAddToCart.setEnabled(false);
                // btnAddToCart.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.bluegreycolor));
                btnAddToCart.setText("Added to cart!");
            });

        }


    }


}
