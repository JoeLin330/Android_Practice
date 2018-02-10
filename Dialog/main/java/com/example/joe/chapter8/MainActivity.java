package com.example.joe.chapter8;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Button button01;
    private Button button02;

    private int year, month, day;
    private int hour, minute;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog02);

        processViews();

        // 取得目前日期與時間
        Calendar cal = Calendar.getInstance();

        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);

        hour = cal.get(Calendar.HOUR);
        minute = cal.get(Calendar.MINUTE);
    }

    private void processViews() {
        button01 = findViewById(R.id.button01);
        button02 = findViewById(R.id.button02);
    }

    // 選擇日期
    public void clickButton01(View view) {
        // 日期設定監聽類別
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

            // 三個int值參數依序為年、月（一月為0，二月為1...）、日
            @Override
            public void onDateSet(DatePicker view,
                                  int year, int monthOfYear, int dayOfMonth) {
                MainActivity.this.year = year;
                MainActivity.this.month = monthOfYear;
                MainActivity.this.day = dayOfMonth;

                button01.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
            }

        };

        // 建立日期對話框物件
        // 第一個參數是使用對話框的Activity元件
        // 第二個參數是日期設定監聽物件
        // 第三到第五個參數是預設的年、月、日
        DatePickerDialog d = new DatePickerDialog(this, listener, year, month, day);
        // 顯示日期對話框
        d.show();
    }

    public void clickButton02(View view) {
        // 時間設定監聽類別
        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            // 兩個int值參數為時與分
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                MainActivity.this.hour = hourOfDay;
                MainActivity.this.minute = minute;

                button02.setText(hourOfDay + ":" + minute);
            }
        };

        // 建立對話框物件
        // 第一個參數是使用對話框的Activity元件
        // 第二個參數是時間設定監聽物件
        // 第三與第四個參數是預設的時與分
        // 第五個參數設定為true表示使用24小時制
        TimePickerDialog d = new TimePickerDialog(
                MainActivity.this, listener, hour, minute, true);
        // 顯示時間對話框
        d.show();
    }

    public void clickButton03(View view) {
        Intent intent = new Intent(MainActivity.this, Dialog03Activity.class);

        startActivity(intent);
    }

}
