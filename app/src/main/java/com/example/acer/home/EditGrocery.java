package com.example.acer.home;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.acer.home.Model.DBRepository;

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
            final Button btnSaveChanges = editGroceryView.findViewById(R.id.btnSaveChanges);
            final Bundle bundle = getArguments();
           txtEditedGroceryName.setText(String.valueOf(bundle.getString("GroceryName")));
           txtEditedExpiryDate.setText(String.valueOf(bundle.getString("expiryDate")));
           txtEditedQuantity.setText(String.valueOf(bundle.getString("quantity")));
           final int selectedGroceryRowID = Integer.parseInt(bundle.getString("ID"));
           CalendarControl.SetCalendarControl(editGroceryView,txtEditedExpiryDate);
           //Save Changes button click event
            btnSaveChanges.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DBRepository groceryRepository =new DBRepository(getContext());
                    groceryRepository.UpdateGrocery(selectedGroceryRowID,Integer.parseInt(txtEditedQuantity.getText().toString()),txtEditedExpiryDate.getText().toString());
                    getFragmentManager().beginTransaction().replace(R.id.frame_container,new GroceriesFragment()).commit();
                }
            });

        // Inflate the layout for this fragment
       return editGroceryView;
    }


}
