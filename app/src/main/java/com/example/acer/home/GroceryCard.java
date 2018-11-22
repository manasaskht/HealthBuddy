package com.example.acer.home;

import android.text.method.DateTimeKeyListener;

import java.util.Date;

/**
 * This class represents the grocery card template
 * @author  Sarmad Noor
 * @version 1.0
 * @since   20 November, 2018
 */
public class GroceryCard {
    protected String groceryName;
    protected int quantity;
    protected String expiryDate;

    GroceryCard (String groceryName, int quantity, String expiryDate)
    {
        this.groceryName = groceryName;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
    }
}
