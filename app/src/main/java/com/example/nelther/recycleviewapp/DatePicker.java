package com.example.nelther.recycleviewapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by nelther on 10/11/2015.
 */
public class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    EditText edt;

    public DatePicker(){

    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(),this,year,month,day);
    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        ((EditText)getActivity().findViewById(R.id.edtFechaEntrega)).setText(year+"/"+(monthOfYear+1)+"/"+dayOfMonth);
    }
}
