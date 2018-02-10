package com.example.joe.test2;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.Date;

// 資料功能類別
public class PlaceDAO {

    public static final String TABLE_NAME = "place";
    public static final String KEY_ID = "_id";

    public static final String LATITUDE_COLUMN = "latitude";
    public static final String LONGITUDE_COLUMN = "longitude";
    public static final String ACCURACY_COLUMN = "accuracy";
    public static final String DATETIME_COLUMN = "datetime";
    public static final String NOTE_COLUMN = "note";

    public static final String[] COLUMNS = {
            KEY_ID, LATITUDE_COLUMN, LONGITUDE_COLUMN, ACCURACY_COLUMN,
            DATETIME_COLUMN, NOTE_COLUMN};

    public static final String[] SHOW_COLUMNS = {KEY_ID, DATETIME_COLUMN, NOTE_COLUMN};

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    LATITUDE_COLUMN + " REAL NOT NULL, " +
                    LONGITUDE_COLUMN + " REAL NOT NULL, " +
                    ACCURACY_COLUMN + " REAL NOT NULL, " +
                    DATETIME_COLUMN + " TEXT NOT NULL, " +
                    NOTE_COLUMN + " TEXT NOT NULL)";

    private SQLiteDatabase db;

    public PlaceDAO(Context context) {
        db = MyDBHelper.getDatabase(context);
    }

    public void close() {
        db.close();
    }

    public Place insert(Place place) {
        ContentValues cv = new ContentValues();

        cv.put(LATITUDE_COLUMN, place.getLatitude());
        cv.put(LONGITUDE_COLUMN, place.getLongitude());
        cv.put(ACCURACY_COLUMN, place.getAccuracy());
        cv.put(DATETIME_COLUMN, place.getDatetime());
        cv.put(NOTE_COLUMN, place.getNote());

        long id = db.insert(TABLE_NAME, null, cv);
        place.setId(id);

        return place;
    }

    public boolean update(Place place) {
        ContentValues cv = new ContentValues();

        cv.put(LATITUDE_COLUMN, place.getLatitude());
        cv.put(LONGITUDE_COLUMN, place.getLongitude());
        cv.put(ACCURACY_COLUMN, place.getAccuracy());
        cv.put(DATETIME_COLUMN, place.getDatetime());
        cv.put(NOTE_COLUMN, place.getNote());

        String where = KEY_ID + "=" + place.getId();

        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    public boolean delete(long id) {
        String where = KEY_ID + "=" + id;

        return db.delete(TABLE_NAME, where, null) > 0;
    }

    public Cursor getAllCursor() {
        return db.query(TABLE_NAME, SHOW_COLUMNS,
                null, null, null, null, null);
    }

    public Cursor getSearchCursor(String date) {
        String where = "substr(datetime, 1, 10)='" + date + "'";

        return db.query(TABLE_NAME, SHOW_COLUMNS, where,
                null, null, null, null, null);
    }

    public Place get(long id) {

        Place place = null;

        String where = KEY_ID + "=" + id;

        Cursor result = db.query(TABLE_NAME, COLUMNS, where,
                null, null, null, null, null);

        if (result.moveToFirst()) {
            place = getRecord(result);
        }

        result.close();

        return place;
    }

    public Place getRecord(Cursor cursor) {

        Place result = new Place();

        result.setId(cursor.getLong(0));
        result.setLatitude(cursor.getDouble(1));
        result.setLongitude(cursor.getDouble(2));
        result.setAccuracy(cursor.getDouble(3));
        result.setDatetime(cursor.getString(4));
        result.setNote(cursor.getString(5));

        return result;
    }

    public void sampleData(Context context) {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

        boolean firstTime = sp.getBoolean("FIRST_TIME", true);

        if (firstTime) {
            Place p01 = new Place(0, 25.04719, 121.516981, 10.0,
                    "2011-12-31 08:30", "Hello!");
            Place p02 = new Place(0, 24.143033, 121.271982, 25.0,
                    "2012-01-01 06:12", "Hi!");
            Place p03 = new Place(0, 25.200854, 121.646714, 55.0,
                    "2012-02-12 16:50", "Awesome!");

            insert(p01);
            insert(p02);
            insert(p03);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            for (int i = 0; i < 10; i++) {
                Date date = new Date(System.currentTimeMillis() + (i * 1000 * 60 * 60 * 24));

                Place place = new Place(0, 25.04719 + i, 121.516981 + i,
                        i * 10, sdf.format(date), "Place: " + i);

                insert(place);
            }

            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("FIRST_TIME", false);
            editor.apply();
        }
    }
}
