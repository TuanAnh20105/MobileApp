package com.example.mobileapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME  = "TripTable.db";

    private static final String DROP = "DROP TABLE IF EXISTS";

    //    Property of trips table
    private static final String TABLE_TRIPS = "trips";
    private static final String ID_TRIP = "trip_id";
    private static final String NAME_OF_TRIP = "nameOfTrip";
    private static final String DESTINATION = "destination";
    private static final String DATE_OF_THE_TRIP = "dateOfTheTrip";
    private static final String REQUIRES_RISK_ASSESSMENT = "requiresRiskAssessment";
    private static final String PERSON_QUANTITY = "personQuantity";
    private static final String TRANSPORT = "transport";
    private static final String DESCRIPTION = "description";


    private SQLiteDatabase database;
    //    Create trips table
    private static final String DATABASE_CREATE = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT)"
            , TABLE_TRIPS, ID_TRIP, NAME_OF_TRIP, DATE_OF_THE_TRIP,DESTINATION,DESCRIPTION,PERSON_QUANTITY,TRANSPORT, REQUIRES_RISK_ASSESSMENT);

    //------------------Get Trip-------------------------
    public ArrayList<Trip> getTrips() {
        Cursor cursor = database.query(TABLE_TRIPS, new String[] {ID_TRIP, NAME_OF_TRIP, DATE_OF_THE_TRIP, DESTINATION,DESCRIPTION,PERSON_QUANTITY,TRANSPORT,REQUIRES_RISK_ASSESSMENT},
                null, null, null, null, ID_TRIP);

        ArrayList<Trip> results = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String date = cursor.getString(2);
            String destination = cursor.getString(3);
            String description = cursor.getString(4);
            String personQuantity = cursor.getString(5);
            String transport = cursor.getString(6);
            String assessment = cursor.getString(7);

            Trip trip = new Trip();
            trip.setDate(date);
            trip.setName(name);
            trip.setId(id);
            trip.setDescription(description);
            trip.setDestination(destination);
            trip.setPersonQuantity(personQuantity);
            trip.setTransport(transport);
            trip.setAssessment(assessment);
            results.add(trip);
            cursor.moveToNext();
        }

        return results;

    }
    //-------------------------Insert Trip------------------------------
    public long insertTrips(String name, String date, String destination, String description,String personQuantity, String transport,String assessment) {
        ContentValues rowValues = new ContentValues();

        rowValues.put(NAME_OF_TRIP, name);
        rowValues.put(DATE_OF_THE_TRIP, date);
        rowValues.put(DESTINATION, destination);
        rowValues.put(DESCRIPTION, description);
        rowValues.put(PERSON_QUANTITY, personQuantity);
        rowValues.put(TRANSPORT, transport);
        rowValues.put(REQUIRES_RISK_ASSESSMENT, assessment);
        return database.insertOrThrow(TABLE_TRIPS, null, rowValues);
    }
    //--------------------------Delete Trip---------------------------------
    public long deleteTrip(int tripId){
        return database.delete(TABLE_TRIPS,"trip_id=?", new String[]{tripId +""});
    }
    public long DeleteAllTrip()
    {
        return database.delete(TABLE_TRIPS,null,null);
    }
    //---------------------Update Trip---------------------------------
    public long UpdateTrip(String tripId, String name, String date, String destination, String description,String personQuantity, String transport,String assessment )
    {
        database = this.getWritableDatabase();
        ContentValues rowValues = new ContentValues();
        rowValues.put(NAME_OF_TRIP, name);
        rowValues.put(DATE_OF_THE_TRIP, date);
        rowValues.put(DESTINATION, destination);
        rowValues.put(DESCRIPTION, description);
        rowValues.put(PERSON_QUANTITY, personQuantity);
        rowValues.put(TRANSPORT, transport);
        rowValues.put(REQUIRES_RISK_ASSESSMENT, assessment);
        return database.update(TABLE_TRIPS,rowValues,"trip_id=?",new String[]{tripId});

    }

    //----------------Delte All Trips-----------------------------------------

    public void deleteTableF(String tablename) {

        String selectQuery = "DELETE FROM " + tablename;

        SQLiteDatabase db= this.getWritableDatabase();

        db.execSQL(selectQuery);

//        SQLiteDatabase db  = new DatabaseHelper1(this); // Dbhelper class object
//        db.deleteTableF("table_name");
    }
    //-----------------------Create Property Of Expense------------------------
    private static final String EXPENSE_TABLE = "expenses";
    private static final String ID_EXPENSE = "expense_id";
    private static final String NAME_OF_EXPENSE = "nameOfExpense";
    private static final String AMOUNT_OF_THE_EXPENSE = "amountOfTheExpense";
    private static final String DATE_OF_THE_EXPENSE = "timeOfTheExpense";

    //       Create expenses table
    private static final String EXPENSE_TABLE_CREATE = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s INTEGER, " +
                    "%s TEXT, " +
                    "%s TEXT, " +
                    "%s TEXT)"
            , EXPENSE_TABLE, ID_EXPENSE,ID_TRIP, NAME_OF_EXPENSE, AMOUNT_OF_THE_EXPENSE,DATE_OF_THE_EXPENSE);

    //--------------------------Insert Expense----------------------------

    public long InsertExpense(int trip_id,String nameOfExpense,String amountOfExpense,String dateOfExpense)
    {
        ContentValues rowValues = new ContentValues();
        rowValues.put(ID_TRIP, trip_id);
        rowValues.put(NAME_OF_EXPENSE, nameOfExpense);
        rowValues.put(AMOUNT_OF_THE_EXPENSE, amountOfExpense);
        rowValues.put(DATE_OF_THE_EXPENSE, dateOfExpense);
        return database.insertOrThrow(EXPENSE_TABLE, null, rowValues);
    }


    //--------------------------Get Expense--------------------------------
    public  ArrayList<Expense> getExpense(int id){
        String MY_QUERY = "   SELECT b.expense_id,b.trip_id, a.nameOfTrip, b.amountOfTheExpense, b.timeOfTheExpense FROM "+ TABLE_TRIPS+
                " a INNER JOIN " + EXPENSE_TABLE + " b ON a.trip_id = b.trip_id where a.trip_id=?";
        Cursor cursor = database.rawQuery(MY_QUERY,new String[]{String.valueOf(id)});
        ArrayList<Expense> results = new ArrayList<>();

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int expense_id = cursor.getInt(0);
            int trip_id = cursor.getInt(1);
            String nameOfExpense = cursor.getString(2);
            String amountOfExpense = cursor.getString(3);
            String dateOfExpense = cursor.getString(4);

            Expense expense = new Expense();
            expense.setId(expense_id);
            expense.setId_trip(trip_id);
            expense.setNameOfExpense(nameOfExpense);
            expense.setAmountOfExpense(amountOfExpense);
            expense.setDateOfExpense(dateOfExpense);
            results.add(expense);
            cursor.moveToNext();
        }

        return results;
    }


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 6);
        database = getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
        db.execSQL(EXPENSE_TABLE_CREATE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("DROP TABLE IF EXISTS " + TABLE_TRIPS);
        DB.execSQL("DROP TABLE IF EXISTS " + EXPENSE_TABLE);
        onCreate(DB);
        Log.w(this.getClass().getName(), DATABASE_NAME + "DATABASE UPGRADE TO VERSION " + newVersion + "- OLD DATA LOST");
    }
}

