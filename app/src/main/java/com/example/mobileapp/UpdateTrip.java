package com.example.mobileapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class UpdateTrip extends AppCompatActivity {


    EditText name,date,description,destination,personQuantity,transport;
    int TripId;
    RadioButton yes, no;
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_trip);


        builder = new AlertDialog.Builder(this);
        yes = findViewById(R.id.radioYesUpdate);
        no = findViewById(R.id.radioNoUpdate);

        name = findViewById(R.id.txtNameUpdate);
        date = findViewById(R.id.txtDateUpdpate);
        description = findViewById(R.id.txtDescriptionUpdate);
        destination = findViewById(R.id.txtDestinationUpdate);
        personQuantity = findViewById(R.id.txtPersonQuantityUpdate);
        transport = findViewById(R.id.txtTransportUpdate);



        Intent intent1 = getIntent();
        TripId = intent1.getIntExtra("trip_id",0);
        String nameOfTrip = intent1.getStringExtra("nameOfTrip");
        String dateOfTrip = intent1.getStringExtra("dateOfTheTrip");
        String destinationOfTrip = intent1.getStringExtra("destination");
        String descriptionOfTrip = intent1.getStringExtra("description");
        String personQuantityOfTrip = intent1.getStringExtra("personQuantity");
        String transportOfTrip = intent1.getStringExtra("transport");

        String checkRadio = intent1.getStringExtra("requiresRiskAssessment");
        if(checkRadio.equals(checkRadio.toString()))
        {
            yes.setChecked(true);
        }
        else
        {
            no.setChecked(true);
        }

        name.setText(String.valueOf(nameOfTrip));
        date.setText(String.valueOf(dateOfTrip));
        destination.setText((String.valueOf(destinationOfTrip)));
        description.setText((String.valueOf(descriptionOfTrip)));
        personQuantity.setText((String.valueOf(personQuantityOfTrip)));
        transport.setText((String.valueOf(transportOfTrip)));

        date.setOnFocusChangeListener((view,b) ->
        {
            if(b)
            {
                UpdateTrip.DatePickerFragment dlg = new UpdateTrip.DatePickerFragment();
                dlg.setDateInput(date);
                dlg.show(getSupportFragmentManager(),"datePicker");
            }
        });
        Button saveButton = findViewById(R.id.btnSaveUpdate);
        saveButton.setOnClickListener(view -> {
            if(checkValidation()==false)
            {
                DatabaseHelper dbHelper = new DatabaseHelper(this);

                try{
                    if(yes.isChecked()){
                        dbHelper.UpdateTrip(String.valueOf(TripId),name.getText().toString(),date.getText().toString(),destination.getText()
                                .toString(),description.getText().toString(),personQuantity.getText().toString(),transport.getText().toString(),"Yes"
                        );
                    } else {
                        dbHelper.UpdateTrip(String.valueOf(TripId),name.getText().toString(),date.getText().toString(),destination.getText()
                                .toString(),description.getText().toString(),personQuantity.getText().toString(),transport.getText().toString(),"No"
                        );
                    }
                }catch(Exception e){
                    Toast.makeText(this, "Adding fail!", Toast.LENGTH_SHORT).show();
                }
            }

        });
        Button addExpenseButton = findViewById(R.id.btnAddExpenseUpdate);
        addExpenseButton.setOnClickListener(view ->{
            Intent intent = new Intent(UpdateTrip.this, AddExpense.class);
            intent.putExtra("trip_id",TripId);
            intent.putExtra("name",nameOfTrip);
            startActivity(intent);
        });
        Button deleteButton = findViewById(R.id.btnDeleteUpdate);
        deleteButton.setOnClickListener(view ->{
            builder.setTitle("").
                    setMessage("Do you want to Delete this trip?")
                    .setCancelable(true)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            delete();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .show();
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            dbHelper.deleteTrip(TripId);
            Intent intent = new Intent(UpdateTrip.this,MainActivity.class);
            startActivity(intent);
        });

        DatabaseHelper db = new DatabaseHelper(this);
        List<Expense> expenses = db.getExpense(TripId);
        if(expenses.size() >0)
        {
            ArrayAdapter<Expense> arrayAdapter = new ArrayAdapter<Expense>(this, android.R.layout.simple_list_item_1,expenses);
            ListView listViewExpense = findViewById(R.id.listViewExpense);
            listViewExpense.setAdapter(arrayAdapter);
        }
    }
    public void delete()
    {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.deleteTrip(TripId);
    }
    public  boolean checkValidation()
    {
        if(TextUtils.isEmpty(name.getText().toString()) && name.getText().length() < 2)
        {
            name.setError("Name not null or less than 2");
            return true;
        }
        if(TextUtils.isEmpty(date.getText().toString()))
        {
            date.setError("Date not null");
            return true;
        }
        if(TextUtils.isEmpty(description.getText().toString()) && name.getText().length() < 2)
        {
            description.setError("Description not null or less than 2");
            return true;
        }
        if(TextUtils.isEmpty(destination.getText().toString())&& name.getText().length() < 2)
        {
            destination.setError("Destination not null or less than 2");
            return true;
        }
        if(TextUtils.isEmpty(personQuantity.getText().toString()) && CheckNumber(personQuantity.getText().toString()) == false)
        {
            personQuantity.setError("Person Quantity not null");
            return true;
        }
        if(TextUtils.isEmpty(transport.getText().toString()))
        {
            transport.setError("Transport not null");
            return true;
        }
        return false;
    }

    public boolean CheckNumber(String a)
    {

        boolean numeric = true;
        Double num ;

        try {
             num = Double.parseDouble(String.valueOf(a));
        } catch (NumberFormatException e) {
            numeric = false;
        }
        if(numeric== true)
        {
            return true;
        }
        else
        {
            return false;
        }

    }
    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
        public void setDateInput(EditText dateInput) {
            this.dateInput = dateInput;
        }

        public EditText getDateInput() {
            return dateInput;
        }

        EditText dateInput;
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
        {
            final Calendar c = Calendar.getInstance();
            int year = c.get((Calendar.YEAR));
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(requireContext(),this,year,month,day);

        }
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            dateInput.setText((String.valueOf(i2 + "/" + i1 +"/" +i)));
        }
    }

}