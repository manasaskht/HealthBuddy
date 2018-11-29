package com.example.acer.home.Model;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {GroceryModel.class}, version = 1, exportSchema = false)
public abstract class DBImplementer extends RoomDatabase {
    public abstract GroceryDao  groceryDao();
}
