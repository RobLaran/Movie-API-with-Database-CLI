package com.moviedatabase.main;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String name;
    private List<Movie> favoriteMovies;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
        favoriteMovies = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public int getID() {
        return this.id;
    }

    public void addMovie(Movie movie) {
        this.favoriteMovies.add(movie);
    }

    public void showFavoriteMovies() {
        for(int i = 0; i < favoriteMovies.size(); i++) {
            Movie movie = favoriteMovies.get(i);
            System.out.println("Movie " + (i + 1));
            System.out.println("Title: ");
            System.out.println("Overview: ");
            System.out.println("Popularity: ");
            System.out.println("Release Date: ");
            System.out.println("Vote Average: ");
            System.out.println("Vote Counts: ");
            System.out.println();
        }
    }

}
