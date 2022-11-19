package com.example.mobileapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.List;

public class ViewAllTrips extends AppCompatActivity {
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_trips);
        builder = new AlertDialog.Builder(this);
        SearchView search = findViewById(R.id.searchView);
        Button deleteAll = findViewById(R.id.btnDeleteAll);
        deleteAll.setOnClickListener(view->{

            builder.setTitle("").
                    setMessage("Do you want to Delete this trip?")
                    .setCancelable(true)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteTrip();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .show();

        });



        DatabaseHelper dbHelper = new DatabaseHelper(this);
        List<Trip> trips = dbHelper.getTrips();
        ArrayAdapter<Trip> arrayAdapter = new ArrayAdapter<Trip>(this, android.R.layout.simple_list_item_1,trips);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                arrayAdapter.getFilter().filter(s);                return false;
            }
        });

        listView.setOnItemClickListener((adapterView, view1, i, l) -> {
            Trip selectTrips = trips.get(i);
            openDetails(selectTrips);
        });
    }
    public void deleteTrip()
    {
        DatabaseHelper db = new DatabaseHelper(this);
        db.DeleteAllTrip();
        Toast.makeText(this, "Delete All Successful", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(ViewAllTrips.this,MainActivity.class);
        startActivity(i);
    }
    private void openDetails(Trip selectTrips) {
        Intent intent2 = new Intent(ViewAllTrips.this, UpdateTrip.class);
        intent2.putExtra("trip_id",selectTrips.getId());
        intent2.putExtra("nameOfTrip",selectTrips.getName());
        intent2.putExtra("destination",selectTrips.getDestination());
        intent2.putExtra("description",selectTrips.getDescription());
        intent2.putExtra("personQuantity",selectTrips.getPersonQuantity());
        intent2.putExtra("transport",selectTrips.getTransport());
        intent2.putExtra("dateOfTheTrip",selectTrips.getDate());
        intent2.putExtra("requiresRiskAssessment",selectTrips.getAssessment());
        startActivity(intent2);

    }
}