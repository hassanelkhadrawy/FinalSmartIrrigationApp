package com.example.finalsmartirrigationapp;

public class Model {
    public String User_ID;
    public int Heat_degree;
    public int Punmp_Status;
    public int Air_Status;
    public int Semad_Status;

    public Model() {

    }

    public Model(String user_ID, int heat_degree, int punmp_Status, int air_Status, int semad_Status) {
        User_ID = user_ID;
        Heat_degree = heat_degree;
        Punmp_Status = punmp_Status;
        Air_Status = air_Status;
        Semad_Status = semad_Status;
    }
}

