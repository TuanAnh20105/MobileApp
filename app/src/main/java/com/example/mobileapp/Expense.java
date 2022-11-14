package com.example.mobileapp;

public class Expense {
        protected  int id;
        protected String nameOfExpense;

        public int getId_trip() {
            return id_trip;
        }

        public void setId_trip(int id_trip) {
            this.id_trip = id_trip;
        }

        protected int id_trip;

        public int getId() {
            return id;
        }

        public String getNameOfExpense() {
            return nameOfExpense;
        }

        public String getAmountOfExpense() {
            return amountOfExpense;
        }

        public String getDateOfExpense() {
            return dateOfExpense;
        }

        protected String amountOfExpense;

        public void setId(int id) {
            this.id = id;
        }

        public void setNameOfExpense(String nameOfExpense) {
            this.nameOfExpense = nameOfExpense;
        }

        public void setAmountOfExpense(String amountOfExpense) {
            this.amountOfExpense = amountOfExpense;
        }

        public void setDateOfExpense(String dateOfExpense) {
            this.dateOfExpense = dateOfExpense;
        }

        protected String dateOfExpense;
        public String toString() {
            return "Expense: "+    nameOfExpense+ "-"+ "amount: "+ amountOfExpense +"-" + "date: " + dateOfExpense ;
        }
    }


