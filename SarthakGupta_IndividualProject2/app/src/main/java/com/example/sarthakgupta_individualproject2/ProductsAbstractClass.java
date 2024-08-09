package com.example.sarthakgupta_individualproject2;

import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = {ProductsDB.class}, version = 1, exportSchema = false)
public abstract class ProductsAbstractClass extends RoomDatabase
{
    public abstract ProductsDAO productsDao();
}
