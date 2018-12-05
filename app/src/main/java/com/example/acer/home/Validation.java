package com.example.acer.home;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
/**
 * This class represents add Grocery functionality
 * @author  Sarmad Noor
 * @version 1.0
 * @since   2 December, 2018
 * Reference URL : https://tausiq.wordpress.com/2013/01/19/android-input-field-validation/
 */
public class Validation {

    public static boolean IsFieldEmpty (EditText... editText)
    {
        for(EditText text:editText) {
            String enteredText = text.getText().toString().trim();
            text.setError(null);
            // Length zero  means no Text
            if (enteredText.length() == 0) {
                text.setError("Required");
                return false;
            }
        }
    return true;
    }
    public static boolean isGroceryNameEmpty (AutoCompleteTextView autoTxtView)
    {
        String enteredGroceryName = autoTxtView.getText().toString().trim();
        autoTxtView.setError(null);
        if(enteredGroceryName.length() == 0)
        {
            autoTxtView.setError("Required");
            return false;
        }
        return  true;
    }
    /**
     * Method which checks the network connectivity.
     * @return false if device is in airplane mode.
     * Reference URL :https://stackoverflow.com/questions/23753245/do-i-need-access-network-state-permission-if-i-have-internet-one
     */
    public static boolean IsNetworkConnected( ConnectivityManager connectivityManager)
    {
        boolean isOnline=false;
        NetworkInfo ntwInfo = connectivityManager.getActiveNetworkInfo();
        if (ntwInfo !=null)
        {
            isOnline = ntwInfo.isConnected();

        }
        return isOnline ;
    }
}
