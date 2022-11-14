package com.example.mobileapp;

public class Trip {


        public void setId(int id) {
            this.id = id;
        }

        protected  int id;

        public void setName(String name) {
            this.name = name;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setPersonQuantity(String personQuantity) {
            this.personQuantity = personQuantity;
        }

        public void setTransport(String transport) {
            this.transport = transport;
        }
        public String name;
        public String date;
        public String destination;
        public String description;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getDate() {
            return date;
        }

        public String getDestination() {
            return destination;
        }

        public String getDescription() {
            return description;
        }

        public String getPersonQuantity() {
            return personQuantity;
        }

        public String getTransport() {
            return transport;
        }

        public String personQuantity;
        public String transport;

    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }

    public String assessment;
        public String toString() {
            return " Trip: "+    name+ "-"+ " date: "+ date +"-"
                    + " destination: " + destination+" description: "+description
                    +" person_Quantity: "+ personQuantity+" Transport " + transport +
                    " RiskAssessment " + assessment ;
        }
    }

