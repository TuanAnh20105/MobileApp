package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.app.Dialog;
import android.content.Intent;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.DatePicker;
import androidx.fragment.app.DialogFragment;
import android.app.DatePickerDialog;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText name,date,description,destination,personQuantity,transport;
    RadioButton yes,no;
    boolean check = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        yes = findViewById(R.id.radioYes);
        no = findViewById(R.id.radioNo);


        name = findViewById(R.id.txtName);
        date = findViewById(R.id.DateOftheTrip);
        description = findViewById(R.id.txtDescription);
        destination = findViewById(R.id.txtDestination);
        personQuantity = findViewById(R.id.txtPersonQuantity);
        transport = findViewById(R.id.txtTransport);


        date.setOnFocusChangeListener((view,b) ->
        {
            if(b)
            {
                DatePickerFragment dpf = new DatePickerFragment();
                dpf.setDateInput(date);
                dpf.show(getSupportFragmentManager(),"datePicker");
            }
        });
        Button btnSave = findViewById(R.id.btnSaveDetail);
        btnSave.setOnClickListener(view -> {

            if(checkValidation()==false)
            {
                DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
                try{
                    if(yes.isChecked()){
                        dbHelper.insertTrips(name.getText().toString(),date.getText().toString(),description.getText().toString(),
                                destination.getText().toString(),personQuantity.getText().toString(),
                                transport.getText().toString(),"Yes"
                        );
                        Toast.makeText(this, "Add Trip Successful", Toast.LENGTH_SHORT).show();


                    } else {
                        dbHelper.insertTrips(name.getText().toString(),date.getText().toString(),description.getText().toString(),
                                destination.getText().toString(),personQuantity.getText().toString(),
                                transport.getText().toString(),"No"
                        );
                        Toast.makeText(this, "Add Trip Successful", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    Toast.makeText(this, "Adding fail!", Toast.LENGTH_SHORT).show();
                }

                setEmpty();
            }
            else
            {
                Toast.makeText(this, "Adding fail!", Toast.LENGTH_SHORT).show();
                check = false;
            }
        });

        Button btnViewAllTrip = findViewById(R.id.btnView);
        btnViewAllTrip.setOnClickListener(view ->{
            Intent intent = new Intent(MainActivity.this, ViewAllTrips.class);
            startActivity(intent);
        });
    }
    public void setEmpty()
    {
        name.setText("");
        date.setText("");
        destination.setText("");
        description.setText("");
        personQuantity.setText("");
        transport.setText("");

    }
    public  boolean checkValidation()
    {

        if(TextUtils.isEmpty(name.getText().toString()) && name.getText().length() < 2)
        {
            name.setError("Name not null");
            check = true;
        }
        if(TextUtils.isEmpty(date.getText().toString()))
        {
            date.setError("Date not null");
            check = true;
        }
        if(TextUtils.isEmpty(description.getText().toString()) && name.getText().length() < 2)
        {
            description.setError("description not null");
            check = true;
        }
        if(TextUtils.isEmpty(destination.getText().toString()) && destination.getText().length() < 2)
        {
            destination.setError("destination not null");
            check = true;
        }
        if(TextUtils.isEmpty(personQuantity.getText().toString())&& CheckNumber(personQuantity.getText().toString()) == false)
        {
            personQuantity.setError("person Quantity not null");
            check = true;
        }
        if(TextUtils.isEmpty(transport.getText().toString()))
        {
            transport.setError("transport not null");
            check = true;
        }
        if(check == true)
        {
            return true;
        }

        return false;
    }
    public boolean CheckNumber(String a)
    {

        boolean numeric = true;

        try {
            Double num = Double.parseDouble(String.valueOf(a));
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