package com.example.joe.test2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends Activity {

    private EditText id_update_edit;
    private EditText latitude_update_edit;
    private EditText longitude_update_edit;
    private EditText accuracy_update_edit;
    private EditText datetime_update_edit;
    private EditText note_update_edit;

    private Place place;

    private PlaceDAO placeDAO;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        placeDAO = new PlaceDAO(this);

        Intent intent = getIntent();

        long id = intent.getLongExtra("id", -1);
        place = placeDAO.get(id);

        processViews();
    }

    private void processViews() {
        id_update_edit = findViewById(R.id.id_update_edit);
        latitude_update_edit = findViewById(R.id.latitude_update_edit);
        longitude_update_edit = findViewById(R.id.longitude_update_edit);
        accuracy_update_edit = findViewById(R.id.accuracy_update_edit);
        datetime_update_edit = findViewById(R.id.datetime_update_edit);
        note_update_edit = findViewById(R.id.note_update_edit);

        id_update_edit.setText(Long.toString(place.getId()));
        latitude_update_edit.setText(Double.toString(place.getLatitude()));
        longitude_update_edit.setText(Double.toString(place.getLongitude()));
        accuracy_update_edit.setText(Double.toString(place.getAccuracy()));
        datetime_update_edit.setText(place.getDatetime());
        note_update_edit.setText(place.getNote());
    }

    public void clickOk(View view) {

        double latitudeValue = Double.parseDouble(latitude_update_edit.getText().toString());
        double longitudeValue = Double.parseDouble(longitude_update_edit.getText().toString());
        double accuracyValue = Double.parseDouble(accuracy_update_edit.getText().toString());
        String datetimeValue = datetime_update_edit.getText().toString();
        String noteValue = note_update_edit.getText().toString();

        place.setLatitude(latitudeValue);
        place.setLongitude(longitudeValue);
        place.setAccuracy(accuracyValue);
        place.setDatetime(datetimeValue);
        place.setNote(noteValue);

        placeDAO.update(place);
        Toast.makeText(this, "Update success!", Toast.LENGTH_SHORT).show();

        Intent intent = getIntent();

        setResult(Activity.RESULT_OK, intent);

        finish();
    }

    public void clickCancel(View view) {
        finish();
    }

}