package com.moviedatabase.jdbc;

import java.sql.*;

public class DatabaseConnectivity {
    private final static String username = "root";
    private final static String password = "darting1223";
    private final static String API_KEY = "693ce75cb812cc6552f7f0501eb2c4dc";
    private static String url = "jdbc:mysql://127.0.0.1:3306/movie_db";
    private static Connection connection;
    private static Statement statement;
    private static String query;
    private static ResultSet resultSet;

    public static void connectDatabase() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static  boolean isUserLoggedIn(String name, String password) {
        query = "select * from user where username = '"+name+"' and password = '"+password+"';";

        try {
            return statement.executeQuery(query).next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isUserRegistered(String name) {
        query = "select username from user where username = '"+name+"'";

        try {
            return statement.executeQuery(query).next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertUser(String name, String password) {
        query = "insert into user (username, password) values ('"+name+"', '"+password+"');";

        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void fetchMovies() {

    }

    public void fetchMovieDetail() {

    }

    public void fetchGenres() {

    }

}
