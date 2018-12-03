package com.example.acer.home;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
/**
 * This class represents generic Calendar control functionality
 * @author  Sarmad Noor
 * @version 1.0
 * @since   2 December, 2018
 */
public  class CalendarControl {
    static DatePickerDialog.OnDateSetListener setDateListener;
    public static void SetCalendarControl (final Context context, final EditText calendarEditText)
    {

        calendarEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year  = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day   = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dateDialog = new DatePickerDialog(context
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
                String date = dayOfMonth + "-" + month + "-" + year;
                calendarEditText.setText(date);
            }
        };

    }

}
