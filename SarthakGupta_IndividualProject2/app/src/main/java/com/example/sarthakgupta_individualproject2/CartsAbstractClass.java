package com.example.sarthakgupta_individualproject2;

import androidx.room.Database;
import androidx.room.RoomDatabase;

// Abstract class for the Room database that holds the database and serves as the main access point.
@Database(entities = {CartDB.class}, version = 1, exportSchema = false)
public abstract class CartsAbstractClass extends RoomDatabase {


//  Abstract method to get the DAO for CartDB..
    public abstract CartDAO cartDao();


}
