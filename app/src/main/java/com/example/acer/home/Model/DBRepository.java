package com.example.acer.home.Model;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

public class DBRepository {

    private String DB_NAME = "db_grocery";
    private DBImplementer dbImplementer;
    public DBRepository (Context context)
    {
        dbImplementer = Room.databaseBuilder(context, DBImplementer.class,DB_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();

    }
    public void InsertGrocery (final GroceryModel addGrocery)
    {
        //new Thread(new Runnable() {
            final GroceryModel groceryModel = addGrocery;
          //  @Override
          //  public void run() {
                dbImplementer.groceryDao().insertSingleGrocery(groceryModel);
            //}
        //}).start();
    }
    public List<GroceryModel> ViewGroceries ()
    {
        return dbImplementer.groceryDao().fetchGroceries();
    }
    public void UpdateGrocery (int RowId, int quantity, String unit ,String expiryDate)
    {
        dbImplementer.groceryDao().updateSingleGrocery( RowId, quantity,unit,expiryDate);
    }
    public void DeleteGrocery (int rowID)
    {
        dbImplementer.groceryDao().DeleteSingleGrocery(rowID);
    }
}
