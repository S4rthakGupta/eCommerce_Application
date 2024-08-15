package com.example.sarthakgupta_individualproject2;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;


// Annotate the interface as a Data Access Object (DAO) for Room database operations
@Dao
public interface ProductsDAO
{
    @Insert
    public void addProduct(ProductsDB product);
    @Update
    public void updateProduct(ProductsDB product);
    @Query("SELECT * FROM ProductDetails")
    public List<ProductsDB> listProducts();
    @Query("SELECT * FROM ProductDetails WHERE id=:id")
    public ProductsDB getProductById(Integer id);
    @Query("DELETE FROM ProductDetails WHERE id=:id")
    public void removeProduct(Integer id);

    // Method to retrieve a list of product images by product ID
    // Note: This method returns a list of products with only the `id` and `productImg` fields
    @Query("SELECT id, productImg FROM ProductDetails WHERE id=:id")
    public List<ProductsDB> getImagesByProductId(Integer id);


}

