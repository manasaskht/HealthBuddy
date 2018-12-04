package com.example.acer.home;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.home.Model.DBRepository;
/**
 * This class represents edit Grocery functionality
 * @author  Sarmad Noor
 * @version 1.0
 * @since   2 December, 2018
 */
public class EditGroceryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_grocery);

        setTitle(getResources().getString(R.string.editGrocery));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getColor(R.color.colorAccent)));

        final EditText txtEditedExpiryDate = findViewById(R.id.txtSavedExpiryDate);
        final TextView txtEditedGroceryName = findViewById(R.id.txtSavedGroceryName);
        final  EditText txtEditedQuantity = findViewById(R.id.txtSavedQuantity);
        final Button btnSaveChanges = findViewById(R.id.btnSaveChanges);
        final Spinner spinnerEditedUnit = findViewById(R.id.unitedited_spinner);
        String [] arrUnits = getApplicationContext().getResources().getStringArray(R.array.arrUnit);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arrUnits);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerEditedUnit.setAdapter(spinnerAdapter);
        txtEditedGroceryName.setText(getIntent().getStringExtra("GroceryName"));
        txtEditedExpiryDate.setText(getIntent().getStringExtra("expiryDate"));
        txtEditedQuantity.setText(getIntent().getStringExtra("quantity"));
        String selectedUnit = getIntent().getStringExtra("unit");
        spinnerEditedUnit.setSelection(spinnerAdapter.getPosition(selectedUnit));
        final int selectedGroceryRowID = Integer.parseInt(getIntent().getStringExtra("ID"));
        CalendarControl.SetCalendarControl(this,txtEditedExpiryDate);
        //Save Changes button click event
        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validation.IsFieldEmpty(txtEditedQuantity,txtEditedExpiryDate)) {
                    DBRepository groceryRepository = new DBRepository(getApplicationContext());
                    groceryRepository.UpdateGrocery(selectedGroceryRowID, Integer.parseInt(txtEditedQuantity.getText().toString()), spinnerEditedUnit.getSelectedItem().toString(), txtEditedExpiryDate.getText().toString());
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext() , "Please enter the required fields", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        finish();
        return true;
    }
}
