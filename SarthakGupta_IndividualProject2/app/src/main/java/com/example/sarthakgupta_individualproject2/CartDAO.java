package com.example.sarthakgupta_individualproject2;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface CartDAO {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void addProduct(CartDB cart);
    @Query("UPDATE Cart SET quantity=:quantity WHERE userName=:user AND productId=:productId")
    public void updateQuantity(int quantity, String user, int productId);
    @Query("SELECT * FROM Cart")
    public List<CartDB> listProducts();
    @Query("SELECT * FROM Cart WHERE userName=:user")
    public List<CartDB> getProductByUser(String user);
    @Query("DELETE FROM Cart WHERE userName=:user AND productId=:productId")
    public void removeProduct(String user, int productId);
    @Query("SELECT EXISTS(SELECT 1 FROM Cart WHERE userName = :user AND productId = :productId)")
    boolean checkIfItemExists(String user, int productId);

    @Query("SELECT quantity FROM Cart WHERE userName = :user AND productId = :productId")
    int getExistingQuantity(String user, int productId);
}

