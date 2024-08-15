package com.example.sarthakgupta_individualproject2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    // Defining objects and variables.
    private static List<CartDB> cartItems;
    private static String user = "";
    private static CartsAbstractClass cartdatabase;
    private ProductsAbstractClass productsDatabase;

    private ItemUpdateListener objListener;


//     Initializing a constructor
    public CartAdapter(List<CartDB> cartItems, String user, ItemUpdateListener objListener) {
        this.cartItems = cartItems;
        this.user = user;
        this.objListener = objListener;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for cart item and return a ViewHolder.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_cart_details, parent, false);
        return new CartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        // Bind data to ViewHolder.
        CartDB cart = cartItems.get(position);

        // Initialize products database
        productsDatabase = Room.databaseBuilder(holder.itemView.getContext(), ProductsAbstractClass.class, "Products").allowMainThreadQueries().build();

        // Get product details and set to ViewHolder
        ProductsDB product = productsDatabase.productsDao().getProductById(cart.getProductId());

        // Log product and TextView details for debugging
        holder.imgProductSmall.setImageResource(holder.itemView.getContext().getResources().getIdentifier(product.getProductImg(), "drawable", holder.itemView.getContext().getPackageName()));
        holder.txtItemName.setText(product.getProductName());
        holder.txtUnitPrice.setText("$" + product.getProductPrice());
        holder.txtQuantity1.setText(Integer.toString(cart.getQuantity()));

//        Logging for debugging purposes.
        Log.d("CartAdapter", "Product: " + product);
        Log.d("CartAdapter", "TextView: " + holder.txtItemName);
    }

    @Override
    public int getItemCount() {
        // Return the number of items in the cart
        return cartItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

//        Initializing Layouts Elements
        ImageView imgProductSmall;

        EditText txtQuantity1;
        TextView txtItemName, txtUnitPrice;

        CardView cardView;
        Button btnRemove1, btnDecreaseQuantity, btnIncreaseQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize ViewHolder views
            imgProductSmall = itemView.findViewById(R.id.cartProductImg);
            txtItemName = itemView.findViewById(R.id.productNameCart);
            txtUnitPrice = itemView.findViewById(R.id.productAmount);
            cardView = itemView.findViewById(R.id.productsCardView);
            txtQuantity1 = itemView.findViewById(R.id.cartQuantity);
            btnDecreaseQuantity = itemView.findViewById(R.id.quantityMinusCart);
            btnIncreaseQuantity = itemView.findViewById(R.id.quantityPlusCart);
            btnRemove1 = itemView.findViewById(R.id.removeBtnCart);

            // Increase quantity button click listener
            btnIncreaseQuantity.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != -1) {  // Check for valid position
                    CartDB cart = cartItems.get(position);
                    int currentQuantity = cart.getQuantity();
                    currentQuantity++;

                    // Update quantity and notify changes
                    cart.setQuantity(currentQuantity);
                    cartItems.set(position, cart);
                    cartdatabase.cartDao().updateQuantity(currentQuantity, user, cart.getProductId());
                    notifyItemChanged(position);
                    notifyDataSetChanged();
                    objListener.onItemUpdate();
                }
            });

            // Decrease quantity button click listener
            btnDecreaseQuantity.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != -1) {  // Check for valid position
                    CartDB cart = cartItems.get(position);
                    int currentQuantity = cart.getQuantity();
                    if (currentQuantity > 1) {
                        currentQuantity--;
                        cart.setQuantity(currentQuantity);
                        cartItems.set(position, cart);
                        cartdatabase.cartDao().updateQuantity(currentQuantity, user, cart.getProductId());
                        notifyItemChanged(position);
                        notifyDataSetChanged();
                        objListener.onItemUpdate();
                    }
                }
            });
//             Initialzing cart database.
            cartdatabase = Room.databaseBuilder(itemView.getContext(), CartsAbstractClass.class,
                    "Cart").allowMainThreadQueries().build();

            // Remove from cart button click listener
            btnRemove1.setOnClickListener(v -> {
                int position = getAdapterPosition();
                CartDB cart = cartItems.get(position);
                cartdatabase.cartDao().removeProduct(user, cart.getProductId());
                Toast.makeText(v.getContext(), "Removed from cart!", Toast.LENGTH_SHORT).show();
                cartItems.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, cartItems.size());
                objListener.onItemUpdate();
            });
        }
    }
}