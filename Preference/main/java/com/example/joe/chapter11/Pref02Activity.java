package com.example.joe.chapter11;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by USER on 2018/1/25.
 */

public class Pref02Activity extends AppCompatActivity {

    private EditText name_edit;
    private TextView amount_text;
    private EditText weeks_edit;
    private SeekBar amount_seekbar;
    private Switch vip_switch;
    private EditText ringtone_edit;
    private CheckBox alarm_check;

    public static final String KEY_NAME = "KEY_NAME";
    public static final String KEY_AMOUNT = "KEEY_AMOUNT";
    public static final String KEY_WEEKS = "KEY_WEEKS";
    public static final String KEY_VIP = "KEY_VIP";
    public static final String KEY_RINGTONE = "KEY_RINGTONE";
    public static final String KEY_ALARM = "KEY_ALARM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref02);

        processViews();
    }

    @Override
    protected  void onResume() {
        super.onResume();

        restorePref();
    }

    public void clickPref(View v) {
        Intent intent = new Intent(this, PrefActivity.class);

        startActivity(intent);
    }

    public void clickCancel(View v) {
        finish();
    }

    private void restorePref() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        String name = sp.getString(KEY_NAME, "");
        String amountStr = sp.getString(KEY_AMOUNT, "0");
        int amount = Integer.parseInt(amountStr);

        Set<String> weeks = sp.getStringSet(KEY_WEEKS, new HashSet<String>());
        boolean vip = sp.getBoolean(KEY_VIP, false);
        String ringtone = sp.getString(KEY_RINGTONE, "");
        boolean alarm = sp.getBoolean(KEY_ALARM, false);

        name_edit.setText(name);
        amount_seekbar.setProgress(amount);
        amount_text.setText(amountStr);
        weeks_edit.setText(weeks.toString());
        vip_switch.setChecked(vip);
        ringtone_edit.setText(ringtone);
        alarm_check.setChecked(alarm);
    }

    private void processViews() {
        name_edit = findViewById(R.id.name_edit);
        amount_text = findViewById(R.id.amount_text);
        weeks_edit = findViewById(R.id.weeks_edit);
        amount_seekbar = findViewById(R.id.amount_seekbar);
        vip_switch = findViewById(R.id.vip_switch);
        ringtone_edit = findViewById(R.id.ringtone_edit);
        alarm_check = findViewById(R.id.alarm_check);
    }
}


