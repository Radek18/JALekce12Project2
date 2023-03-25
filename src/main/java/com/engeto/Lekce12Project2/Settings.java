package com.engeto.Lekce12Project2;

public class Settings {

    private static final String URL = "jdbc:mysql://localhost:3306/eshop";
    private static final String USER = "radek";
    private static final String PASSWORD = "shop123";

    public static String url() {
        return URL;
    }

    public static String user() {
        return USER;
    }

    public static String password() {
        return PASSWORD;
    }

}