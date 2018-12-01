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
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.acer.home.Model.DBImplementer;
import com.example.acer.home.Model.DBRepository;
import com.example.acer.home.Model.GroceryModel;

import org.json.JSONArray;
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

     DatePickerDialog.OnDateSetListener setDateListener;
     private String TAG ="AddGrcoery";
    String[] groceriesList = null ;
    AutoCompleteTextView searchableTextView;
    View instanceGroceryView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Reference URL : https://stackoverflow.com/questions/30093111/findviewbyid-not-working-in-fragment
        final View addGroceryView = inflater.inflate(R.layout.fragment_add_grocery,container,false);
        final EditText txtExpiryDate = addGroceryView.findViewById(R.id.txtExpiryDate);
        final Button btnSaveGroceryDetail = addGroceryView.findViewById(R.id.btnSave);
        final AutoCompleteTextView txtSearchBox = addGroceryView.findViewById(R.id.autoTxtSearchGrocery);
        final EditText txtAddQuantity = addGroceryView.findViewById(R.id.txtQuantity);
        final Spinner unitSpinner = (Spinner) addGroceryView.findViewById(R.id.units_Spinner);
        String [] arrUnit = getContext().getResources().getStringArray(R.array.arrUnit);
        ArrayAdapter <String> spinnerAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item,arrUnit);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        unitSpinner.setAdapter(spinnerAdapter);
        searchableTextView = txtSearchBox;
        instanceGroceryView = addGroceryView;
        TextWatcher fieldTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence inputText, int start, int before, int count) {
                if (StartFilter()) {
                    PopulateGroceryName(inputText);
                     //groceriesList = new String[]{"Banana", "Apple", "Bread", "Butter", "Milk"};
                   // ArrayAdapter <String> adapter = new ArrayAdapter<String>(addGroceryView.getContext(), R.layout.searchable_list_view,groceriesList);
                    //txtSearchBox.setAdapter(adapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
            private boolean StartFilter() {
                return txtSearchBox.getText().toString().trim().length() > 0;
            }
        };
        txtSearchBox.addTextChangedListener(fieldTextWatcher);
        // URL Reference : https://stackoverflow.com/questions/2696846/from-autocomplete-textbox-to-database-search-and-display


        //Calling Calendar class to display calendar
        CalendarControl.SetCalendarControl(addGroceryView,txtExpiryDate);
        btnSaveGroceryDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBRepository groceryRepository =new DBRepository(getContext());
                List<GroceryModel> groceryCount= groceryRepository.ViewGroceries();
                GroceryModel addGroceryModel = new GroceryModel(groceryCount.size() + 1,txtSearchBox.getText().toString(),Integer.parseInt(txtAddQuantity.getText().toString()), unitSpinner.getSelectedItem().toString(),txtExpiryDate.getText().toString());
                groceryRepository.InsertGrocery(addGroceryModel);
            getFragmentManager().beginTransaction().replace(R.id.frame_container,new GroceriesFragment()).commit();
            }
        });
        return addGroceryView;
    }
    public void PopulateGroceryName (CharSequence inputText)
    {
        String apiURL =getString(R.string.azureApiUrl).concat("Grocery?grocerykeyword=" + inputText)  ;
        try {

        JsonArrayRequest azureApiRequest = new JsonArrayRequest(Request.Method.GET, apiURL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                 groceriesList =new String[response.length()];
                for (int i =0; i < groceriesList.length; i++ )
                {
                    groceriesList[i] = response.optString(i);
                }
                ArrayAdapter <String> adapter = new ArrayAdapter<String>(instanceGroceryView.getContext(), R.layout.searchable_list_view,groceriesList);
                searchableTextView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                }
            });
            SingletonRequestQueue.getInstance(getContext()).addToRequestQueue(azureApiRequest);
        }
        catch (Exception ex)
        {

        }
    }

}
