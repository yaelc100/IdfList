package com.example.idflist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class TimerActivity extends AppCompatActivity {
   //AlertDialog.Builder adb;
   CalendarConstraints.Builder caBuilder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        //adb = new AlertDialog.Builder(this);

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Jerusalem"));
        calendar.clear();

        long today = MaterialDatePicker.todayInUtcMilliseconds();

        calendar.setTimeInMillis(today);

        calendar.roll(Calendar.MONTH, Calendar.JANUARY);
        long january = calendar.getTimeInMillis();

        calendar.roll(Calendar.MONTH, Calendar.DECEMBER);
        long december = calendar.getTimeInMillis();

        caBuilder = new CalendarConstraints.Builder();
        caBuilder.setStart(january);
        caBuilder.setEnd(december);


    }

    private void timePicking(){

    }

    public void pickDate(View view) {
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("yael");
        MaterialDatePicker materialDatePicker = builder.build();

        materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
    }
}