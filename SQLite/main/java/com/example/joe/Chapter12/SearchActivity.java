package com.example.joe.test2;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SearchActivity extends Activity {

    private EditText date_search_edit;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        processViews();

    }

    private void processViews() {
        date_search_edit = findViewById(R.id.date_search_edit);

        Date date = new Date();
        date_search_edit.setText(dateFormat.format(date));
    }

    public void clickDateSearch(View view) {
        String dateValue = date_search_edit.getText().toString();
        Date date = new Date();

        try {
            date = dateFormat.parse(dateValue);

        } catch (ParseException e) {
            Log.d("SearchActivity", e.toString());
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, monthOfYear, dayOfMonth);

                date_search_edit.setText(dateFormat.format(calendar.getTime()));
            }
        };

        DatePickerDialog dialog =
                new DatePickerDialog(this, listener, year, month, day);

        dialog.show();
    }

    public void clickOk(View view) {
        String dateValue = date_search_edit.getText().toString();

        Intent intent = getIntent();

        intent.putExtra("dateValue", dateValue);

        setResult(Activity.RESULT_OK, intent);

        finish();
    }

    public void clickCancel(View view) {
        finish();
    }

}