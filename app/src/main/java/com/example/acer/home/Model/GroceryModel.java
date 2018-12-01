package com.example.acer.home.Model;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity
public class GroceryModel {
    @NonNull
    public int groceryId;
    @NonNull
    @PrimaryKey (autoGenerate = true)
    public int baseID;
    public String groceryName;
    public int quantity;
    public String expiryDate;
    public GroceryModel (int groceryId, String groceryName, int quantity, String expiryDate)
    {
        this.groceryId= groceryId;
        this.groceryName = groceryName;
        this.expiryDate=expiryDate;
        this.quantity = quantity;
    }
    public int getGroceryId ()
    {
        return groceryId;
    }
    public void setGroceryId(int groceryId)
    {
        this.groceryId= groceryId;
    }
    public String getGroceryName ()
    {
        return groceryName;
    }
    public void setGroceryName(String groceryName)
    {
        this.groceryName= groceryName;
    }
    public int getQuantity ()
    {
        return quantity;
    }
    public void setQuantity(int quantity)
    {
        this.quantity= quantity;
    }
    public String getExpiryDate ()
    {
        return expiryDate;
    }
    public void setExpiryDate(String expiryDate)
    {
        this.expiryDate= expiryDate;
    }
    public int getBaseID() { return baseID;}

}
