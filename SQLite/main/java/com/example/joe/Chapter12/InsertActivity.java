package com.example.joe.test2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class InsertActivity extends Activity {

    private EditText latitude_edit;
    private EditText longitude_edit;
    private EditText accuracy_edit;
    private EditText note_edit;

    private PlaceDAO placeDAO;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        processViews();

        placeDAO = new PlaceDAO(this);
    }

    private void processViews() {
        latitude_edit = findViewById(R.id.latitude_edit);
        longitude_edit = findViewById(R.id.longitude_edit);
        accuracy_edit = findViewById(R.id.accuracy_edit);
        note_edit = findViewById(R.id.note_edit);
    }

    public void clickOk(View view) {

        double latitudeValue = Double.parseDouble(latitude_edit.getText().toString());
        double longitudeValue = Double.parseDouble(longitude_edit.getText().toString());
        double accuracyValue = Double.parseDouble(accuracy_edit.getText().toString());
        String note = note_edit.getText().toString();

        Place place = new Place();

        place.setLatitude(latitudeValue);
        place.setLongitude(longitudeValue);
        place.setAccuracy(accuracyValue);
        place.setDatetime(System.currentTimeMillis());
        place.setNote(note);

        placeDAO.insert(place);

        Toast.makeText(this, "Insert success!", Toast.LENGTH_SHORT).show();

        Intent intent = getIntent();

        setResult(Activity.RESULT_OK, intent);

        finish();
    }

    public void clickCancel(View view) {
        finish();
    }
}