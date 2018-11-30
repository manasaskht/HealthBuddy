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
import android.util.Log;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.acer.home.Model.DBImplementer;
import com.example.acer.home.Model.DBRepository;
import com.example.acer.home.Model.GroceryModel;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * This class contains the code to implement searchable text box and saving the user details to Database
 * @author  Sarmad Noor
 * @version 1.0
 * @since   20 November, 2018
 * @References : https://stackoverflow.com/questions/30093111/findviewbyid-not-working-in-fragment
 *
 */
public class AddGrocery extends Fragment {
     final String [] groceriesList = new String[]{"Banana", "Apple","Bread","Butter","Milk"};
     DatePickerDialog.OnDateSetListener setDateListener;
     private String TAG ="AddGrcoery";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Reference URL : https://stackoverflow.com/questions/30093111/findviewbyid-not-working-in-fragment
        final View addGroceryView = inflater.inflate(R.layout.fragment_add_grocery,container,false);
        final EditText txtExpiryDate = addGroceryView.findViewById(R.id.txtExpiryDate);
        final Button btnSaveGroceryDetail = addGroceryView.findViewById(R.id.btnSave);
        final AutoCompleteTextView txtSearchBox = addGroceryView.findViewById(R.id.autoTxtSearchGrocery);
        final EditText txtAddQuantity = addGroceryView.findViewById(R.id.txtQuantity);
        // URL Reference : https://stackoverflow.com/questions/2696846/from-autocomplete-textbox-to-database-search-and-display
        ArrayAdapter <String> adapter = new ArrayAdapter<String>(addGroceryView.getContext(), R.layout.searchable_list_view,groceriesList);
        txtSearchBox.setAdapter(adapter);
        //Calling Calendar class to display calendar
        CalendarControl.SetCalendarControl(addGroceryView,txtExpiryDate);
        btnSaveGroceryDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBRepository groceryRepository =new DBRepository(getContext());
                List<GroceryModel> groceryCount= groceryRepository.ViewGroceries();
                GroceryModel addGroceryModel = new GroceryModel(groceryCount.size() + 1,txtSearchBox.getText().toString(),Integer.parseInt(txtAddQuantity.getText().toString()),txtExpiryDate.getText().toString());
                groceryRepository.InsertGrocery(addGroceryModel);
            getFragmentManager().beginTransaction().replace(R.id.frame_container,new GroceriesFragment()).commit();
            }
        });
        return addGroceryView;
    }
    public void PopulateGroceryName ()
    {
        String apiURL =getString(R.string.azureApiUrl);

        JsonObjectRequest azureApiRequesr = new JsonObjectRequest(Request.Method.GET, apiURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

}
