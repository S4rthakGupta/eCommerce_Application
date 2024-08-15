package com.example.sarthakgupta_individualproject2;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//Defining a table named Cart.
@Entity(tableName = "Cart")
public class CartDB {

    // Declaring columns
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "userName")
    String userName;

    @ColumnInfo(name = "productId")
    int productId;

    @ColumnInfo(name = "quantity")
    int quantity;

    // Setting constructor for getter and setter for userName.
    public CartDB(String userName, int productId, int quantity) {
        this.userName = userName;
        this.productId = productId;
        this.quantity = quantity;
    }

    // Setting getters and setters.
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}