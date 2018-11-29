package com.example.acer.home.Model;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.acer.home.GroceryCard;

import java.util.List;


@Dao
public interface GroceryDao {
@Insert
    void insertSingleGrocery (GroceryModel groceryCard);
@Query("SELECT * FROM GroceryModel")
List<GroceryModel> fetchGroceries ();
}
