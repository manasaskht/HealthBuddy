package com.example.acer.home;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.acer.home.Model.DBRepository;
import com.example.acer.home.Model.GroceryModel;

import org.json.JSONArray;

import java.util.List;
/**
 * This class represents add Grocery functionality
 * @author  Sarmad Noor
 * @version 1.0
 * @since   2 December, 2018
 */
public class AddGroceryActivity extends AppCompatActivity {
    DatePickerDialog.OnDateSetListener setDateListener;
    private String TAG ="AddGrcoery";
    String[] groceriesList = null ;
    AutoCompleteTextView searchableTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grocery);

        setTitle(getResources().getString(R.string.addGrocery));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        final EditText txtExpiryDate =  findViewById(R.id.txtExpiryDate);
        final Button btnSaveGroceryDetail = findViewById(R.id.btnSave);
        final AutoCompleteTextView txtSearchBox = findViewById(R.id.autoTxtSearchGrocery);
        final EditText txtAddQuantity = findViewById(R.id.txtQuantity);
        final Spinner unitSpinner = (Spinner)findViewById(R.id.units_Spinner);
        String [] arrUnit = getApplicationContext().getResources().getStringArray(R.array.arrUnit);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arrUnit);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        unitSpinner.setAdapter(spinnerAdapter);
        searchableTextView = txtSearchBox;
        TextWatcher fieldTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence inputText, int start, int before, int count) {
                if (StartFilter()) {
                    PopulateGroceryName(inputText);
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
        CalendarControl.SetCalendarControl(this,txtExpiryDate);
        btnSaveGroceryDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validation.isGroceryNameEmpty(txtSearchBox) && Validation.IsFieldEmpty(txtAddQuantity,txtExpiryDate)) {
                    Fragment groceriesFragment = new GroceriesFragment();
                    DBRepository groceryRepository = new DBRepository(getApplicationContext());
                    List<GroceryModel> groceryCount = groceryRepository.ViewGroceries();
                    GroceryModel addGroceryModel = new GroceryModel(groceryCount.size() + 1, txtSearchBox.getText().toString(), Integer.parseInt(txtAddQuantity.getText().toString()), unitSpinner.getSelectedItem().toString(), txtExpiryDate.getText().toString());
                    groceryRepository.InsertGrocery(addGroceryModel);
                    //getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, groceriesFragment).commit();
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext() , "Please enter the required fields", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    public void PopulateGroceryName (CharSequence inputText )
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
                    ArrayAdapter <String> adapter = new ArrayAdapter<String>(getBaseContext(), R.layout.searchable_list_view, groceriesList);
                    searchableTextView.setAdapter(adapter);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            SingletonRequestQueue.getInstance(getApplicationContext()).addToRequestQueue(azureApiRequest);
        }
        catch (Exception ex)
        {

        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        finish();
        return true;
    }

}
