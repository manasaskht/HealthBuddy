package com.example.acer.home;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
/**
 * This class contains the code to implement the List view which displays grocery name, quantity, expiry date and intake calories
 * @author  Sarmad Noor
 * @version 1.0
 * @since   25 November, 2018
 */
public class EditGrocery extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View editGroceryView = inflater.inflate(R.layout.fragment_edit_grocery, container, false);

            final EditText txtEditedExpiryDate =(EditText) editGroceryView.findViewById(R.id.txtSavedExpiryDate);
            final EditText txtEditedGroceryName = (EditText) editGroceryView.findViewById(R.id.txtSavedGroceryName);
            final  EditText txtEditedQuantity = editGroceryView.findViewById(R.id.txtSavedQuantity);
           Bundle bundle = getArguments();
           txtEditedGroceryName.setText(String.valueOf(bundle.getString("GroceryName")));
           txtEditedExpiryDate.setText(String.valueOf(bundle.getString("expiryDate")));
           txtEditedQuantity.setText(String.valueOf(bundle.getString("quantity")));
        // Inflate the layout for this fragment
       return editGroceryView;
    }


}
