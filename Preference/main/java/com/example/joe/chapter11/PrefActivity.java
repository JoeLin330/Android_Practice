package com.example.joe.chapter11;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.MultiSelectListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by USER on 2018/1/25.
 */

public class PrefActivity extends PreferenceActivity
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    // 設定畫面元件
    private EditTextPreference name;
    private ListPreference amount;
    private MultiSelectListPreference weeks;
    private RingtonePreference ringtone;
    private CheckBoxPreference alarm;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 指定使用的設定畫面配置資源
        addPreferencesFromResource(R.xml.mypref);

        processViews();

        // 建立讀取資料用的SharedPreferences物件
        sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
    }

    private void processViews() {
        // 取得設定畫面元件
        name = (EditTextPreference) findPreference(Pref02Activity.KEY_NAME);
        amount = (ListPreference) findPreference(Pref02Activity.KEY_AMOUNT);
        weeks = (MultiSelectListPreference) findPreference(Pref02Activity.KEY_WEEKS);
        ringtone = (RingtonePreference) findPreference(Pref02Activity.KEY_RINGTONE);
        alarm = (CheckBoxPreference) findPreference(Pref02Activity.KEY_ALARM);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 設定畫面元件的說明
        setSummary(Pref02Activity.KEY_NAME);
        setSummary(Pref02Activity.KEY_AMOUNT);
        setSummary(Pref02Activity.KEY_WEEKS);
        setSummary(Pref02Activity.KEY_RINGTONE);
        setSummary(Pref02Activity.KEY_ALARM);

        // 註冊實作設定值改變監聽事件
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    // 設定畫面元件的說明
    private void setSummary(String key) {
        if (key.equals(Pref02Activity.KEY_NAME)) {
            name.setSummary("Your name is " + name.getText());
        }
        else if (key.equals(Pref02Activity.KEY_AMOUNT)) {
            amount.setSummary("Amount is " + amount.getEntry());
        }
        else if (key.equals(Pref02Activity.KEY_WEEKS)) {
            Set<String> weekSet =
                    sharedPreferences.getStringSet(
                            Pref02Activity.KEY_WEEKS, new HashSet<String>());
            weeks.setSummary(weekSet.toString());
        }
        else if (key.equals(Pref02Activity.KEY_RINGTONE)) {
            ringtone.setSummary(
                    sharedPreferences.getString(
                            Pref02Activity.KEY_RINGTONE, "Select system ringtone"));
        }
        else if (key.equals(Pref02Activity.KEY_ALARM)) {
            alarm.setSummary("Set alarm " +
                    (alarm.isChecked() ? "ON" : "OFF"));
        }
    }

    @Override
    protected void onPause() {
        // 移除實作設定值改變監聽事件
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    // 設定值改變監聽介面實作方法
    // 第一個參數是使用中的SharedPreferences物件
    // 第二個參數是改變設定的資料名稱
    @Override
    public void onSharedPreferenceChanged(
            SharedPreferences sharedPreferences, String key) {

        setSummary(key);
    }

}
