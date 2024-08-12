package com.example.sarthakgupta_individualproject2;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {CartDB.class}, version = 1, exportSchema = false)
public abstract class CartsAbstractClass extends RoomDatabase {

    public abstract CartDAO cartDao();


}
