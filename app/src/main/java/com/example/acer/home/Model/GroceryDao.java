package com.example.acer.home.Model;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.acer.home.GroceryCard;

import java.util.List;


@Dao
public interface GroceryDao {
@Insert
    void insertSingleGrocery (GroceryModel groceryModel);
@Query("SELECT * FROM GroceryModel order by expiryDate asc")
    List<GroceryModel> fetchGroceries ();
@Query("UPDATE GroceryModel SET quantity= :updatedQuantity,unit= :updatedUnit, expiryDate= :updatedExpiryDate WHERE baseID = :rowID")
    void updateSingleGrocery (int rowID,int updatedQuantity,String updatedUnit,String updatedExpiryDate);
@Query("Delete from GroceryModel where baseID = :rowID ")
    void DeleteSingleGrocery(int rowID);
@Query("Select  distinct groceryName from GroceryModel")
    List<String> fetchDistinctGroceryName();
@Query("Select groceryName from GroceryModel where expiryDate = :todayDate")
    List<String> getExpiredGroceries(String todayDate);

}
