package com.example.mobileapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class AddExpense extends AppCompatActivity {

    EditText name, amount, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        Intent intent = getIntent();
        int id_trip ;
        name = findViewById(R.id.txtNameOfExpense);
        amount = findViewById(R.id.txtAmountOfExpense);
        date = findViewById(R.id.txtDateOfExpense);
        id_trip = intent.getIntExtra("trip_id",-1);
        Button btnSave = findViewById(R.id.btnSaveExpense);
        btnSave.setOnClickListener(view ->{
            if(checkValidation() == false)
            {
                DatabaseHelper dbHelper = new DatabaseHelper(this);
                dbHelper.InsertExpense(id_trip,name.getText().toString(),amount.getText().toString(),date.getText().toString());
                Toast.makeText(this, "Adding successful!", Toast.LENGTH_SHORT).show();
                Intent i =new Intent(AddExpense.this,UpdateTrip.class);
                startActivity(i);
            }
            else
            {
                Toast.makeText(this, "Adding fail!", Toast.LENGTH_SHORT).show();
            }
        });


        Button btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(view->{
            Intent intent1 = new Intent(AddExpense.this,UpdateTrip.class);
            startActivity(intent1);
        });
        date.setOnFocusChangeListener((view,b) ->
        {
            if(b)
            {
                MainActivity.DatePickerFragment dpf = new MainActivity.DatePickerFragment();
                dpf.setDateInput(date);
                dpf.show(getSupportFragmentManager(),"datePicker");
            }
        });


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
            int y = c.get((Calendar.YEAR));
            int m = c.get(Calendar.MONTH);
            int d = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(requireContext(),this,y,m,d);

        }
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            dateInput.setText((String.valueOf(i2 + "/" + i1 +"/" +i)));
        }
    }
    public  boolean checkValidation()
    {
        if(TextUtils.isEmpty(name.getText().toString())&& name.getText().length() < 2)
        {
            name.setError("Name not null");
            return true;
        }
        if(TextUtils.isEmpty(date.getText().toString()))
        {
            date.setError("Date not null");
            return true;
        }
        if(TextUtils.isEmpty(amount.getText().toString()) || CheckNumber(amount.getText().toString())==false)
        {
            amount.setError("amount not null or isn't numeric");
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
}