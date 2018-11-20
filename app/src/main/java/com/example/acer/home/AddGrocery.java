package com.example.acer.home;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.support.v4.app.FragmentManager;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * This class contains the code to implement searchable text box and saving the user details to Database
 * @author  Sarmad Noor
 * @version 1.0
 * @since   20 November, 2018
 * @References : https://stackoverflow.com/questions/30093111/findviewbyid-not-working-in-fragment
 *
 */
public class AddGrocery extends Fragment {
     final String [] groceriesList = new String[]{"Banana", "Apple"};
     DatePickerDialog.OnDateSetListener setDateListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Reference URL : https://stackoverflow.com/questions/30093111/findviewbyid-not-working-in-fragment
        final View addGroceryView = inflater.inflate(R.layout.fragment_add_grocery,container,false);
        final EditText txtExpiryDate = addGroceryView.findViewById(R.id.txtExpiryDate);
        final Button btnSaveGroceryDetail = addGroceryView.findViewById(R.id.btnSave);
        AutoCompleteTextView txtSearchBox = addGroceryView.findViewById(R.id.autoTxtSearchGrocery);
        // URL Reference : https://stackoverflow.com/questions/2696846/from-autocomplete-textbox-to-database-search-and-display
        ArrayAdapter <String> adapter = new ArrayAdapter<String>(addGroceryView.getContext(), R.layout.searchable_list_view,groceriesList);
        txtSearchBox.setAdapter(adapter);
        // Event handler to open a calender dialog
        txtExpiryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year  = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day   = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dateDialog = new DatePickerDialog(addGroceryView.getContext(), AlertDialog.THEME_HOLO_LIGHT
                ,setDateListener , year, month, day);
                dateDialog.show();
            }
        });
        //Event to set the selected date to the edit Text control
        setDateListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public  void onDateSet (DatePicker view, int year, int month, int dayOfMonth)
            {
                month = month +1;
                String date = month + "/" + dayOfMonth + "/" + year;
                txtExpiryDate.setText(date);
            }
        };
        btnSaveGroceryDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            getFragmentManager().beginTransaction().replace(R.id.frame_container,new GroceriesFragment()).commit();
            }
        });
        return addGroceryView;
    }

}
