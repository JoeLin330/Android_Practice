package com.example.joe.test2;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private static final int[] IDS = {
            R.id.id_listview, R.id.datetime_listview, R.id.note_listview};

    private static final int INSERT_REQUEST_CODE = 0;
    private static final int UPDATE_REQUEST_CODE = 1;
    private static final int SEARCH_REQUEST_CODE = 2;

    private PlaceDAO placeDAO;
    private ListView list_view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data01);

        list_view = findViewById(R.id.list_view);

        registerForContextMenu(list_view);

        placeDAO = new PlaceDAO(this);

        placeDAO.sampleData(this);

        refresh();

        processControllers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.menu_data01, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.refresh_menu:
                refresh();
                break;

            case R.id.insert_menu:
                Intent intentInsert =
                        new Intent(MainActivity.this, InsertActivity.class);

                startActivityForResult(intentInsert, INSERT_REQUEST_CODE);
                break;

            case R.id.search_menu:
                Intent intentSearch =
                        new Intent(MainActivity.this, SearchActivity.class);

                startActivityForResult(intentSearch, SEARCH_REQUEST_CODE);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) menuInfo;

        Place selected = placeDAO.get(info.id);

        menu.setHeaderTitle(selected.getNote());

        if (view == list_view) {
            MenuInflater menuInflater = getMenuInflater();

            menuInflater.inflate(R.menu.menu_data01_context, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Place selected = placeDAO.get(info.id);

        int id = item.getItemId();

        switch(id) {
            case R.id.update_menu:
                Intent intentUpdate = new Intent(this, UpdateActivity.class);

                intentUpdate.putExtra("id", selected.getId());
                startActivityForResult(intentUpdate, UPDATE_REQUEST_CODE);
                break;
            case R.id.delete_menu:
                AlertDialog.Builder d =
                        new AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);

                d.setTitle("DELETE?").setMessage("Delete " + selected.getNote() + "?");

                d.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        placeDAO.delete(selected.getId());
                        refresh();
                    }
                });

                d.setNegativeButton(android.R.string.cancel, null);

                d.show();
                break;
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SEARCH_REQUEST_CODE) {
                String dateValue = data.getStringExtra("dateValue");

                Cursor cursor = placeDAO.getSearchCursor(dateValue);

                SimpleCursorAdapter sca = new SimpleCursorAdapter(
                        this, R.layout.listview_place, cursor,
                        PlaceDAO.SHOW_COLUMNS, IDS,
                        CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

                list_view.setAdapter(sca);
            } else {
                refresh();
            }
        }
    }

    private void refresh() {

        Cursor cursor = placeDAO.getAllCursor();

        SimpleCursorAdapter sca = new SimpleCursorAdapter(
                this, R.layout.listview_place,
                cursor, PlaceDAO.SHOW_COLUMNS, IDS,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        list_view.setAdapter(sca);

    }

    private void processControllers() {

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Place place = placeDAO.get(id);

                Toast.makeText(MainActivity.this, place.getNote(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        placeDAO.close();
        super.onDestroy();
    }
}