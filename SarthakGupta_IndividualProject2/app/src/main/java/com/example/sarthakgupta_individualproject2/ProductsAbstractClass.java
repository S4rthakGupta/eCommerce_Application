package com.example.sarthakgupta_individualproject2;

import androidx.room.Database;
import androidx.room.RoomDatabase;

// Annotating the class to be a Room Database with the specified entities and version.
@Database(entities = {ProductsDB.class}, version = 1, exportSchema = false)
public abstract class ProductsAbstractClass extends RoomDatabase
{

    // Abstract method to get the DAO for accessing the ProductsDB.
    public abstract ProductsDAO productsDao();
}
