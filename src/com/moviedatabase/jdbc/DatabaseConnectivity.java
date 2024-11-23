package com.moviedatabase.jdbc;

import com.moviedatabase.api.MovieAPI;
import com.moviedatabase.main.Movie;

import java.net.URI;
import java.net.http.HttpRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        query = "select username from user where username = '"+name+"';";

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

    public static int getUserID(String name, String password) {
        query = "select user_id from user where username = '"+name+"' and password = '"+password+"';";

        try {
            ResultSet result = statement.executeQuery(query);

            if(result.next()) {
                return result.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return - 1;
    }

    public static List<Movie> fetchFavoriteMovies(int userID) {
        List<Movie> favoriteMovies = new ArrayList<>();

        query = "select * from favorite_movies where user_id = "+userID+";";

        try {
            ResultSet results = statement.executeQuery(query);

            while(results.next()) {
                favoriteMovies.add(MovieAPI.getMovie(results.getString("movie_title")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return favoriteMovies;
    }

    public static void insertMovie(int userID, int movieID, String movieTitle) {
        query = "insert into favorite_movies (movie_id, movie_title, user_id) values ("+movieID+", '"+movieTitle+"', "+userID+");";

        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
