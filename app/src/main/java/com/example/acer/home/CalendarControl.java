package com.example.acer.home;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public  class CalendarControl {
    static DatePickerDialog.OnDateSetListener setDateListener;
    public static void SetCalendarControl (final View viewName, final EditText calendarEditText)
    {

        calendarEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year  = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day   = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dateDialog = new DatePickerDialog(viewName.getContext()
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
