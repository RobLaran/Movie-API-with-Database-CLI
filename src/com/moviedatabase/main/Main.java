package com.moviedatabase.main;

import com.moviedatabase.api.MovieAPI;
import com.moviedatabase.jdbc.DatabaseConnectivity;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    Scanner scan = new Scanner(System.in).useDelimiter("\n");
    static UserAuthentication authentication = new UserAuthentication();
    private User user;
    private List<Movie> movies = new ArrayList<>();


    public static void main(String[] args) {
        Main cli = new Main();

        cli.runInterface();
    }

    public void runInterface() {
        DatabaseConnectivity.connectDatabase();
        boolean running = true;

        while(running) {
            System.out.println("Welcome to Movie Database with API");
            System.out.println("(1) Login");
            System.out.println("(2) Register");
            System.out.println("(3) Exit");
            System.out.print("Enter number: ");
            try {
                int input = scan.nextInt();

                switch(input) {
                    case 1:
                        if(authentication.loginUser()) {
                            user = authentication.getUser();
                            mainInterface();
                        }
                        break;
                    case 2:
                        if(authentication.registerUser()) {
                            running = false;
                            runInterface();
                        }
                        break;
                    case 3:
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Select a correct number!");
                        break;
                }
            } catch(InputMismatchException inputExc) {
                System.out.println("Enter a number\n");
                break;
            }
        }
    }

    public void mainInterface() {
        while(true) {
            System.out.println("Hello " + user.getName());
            System.out.println("(1) Search Movies by title");
            System.out.println("(2) Popular Movies");
            System.out.println("(3) Movie Detail");
            System.out.println("(4) Add to Favorite Movies");
            System.out.println("(5) Favorite Movies");
            System.out.println("(0) Log out");
            System.out.print("Enter a number (1-4): ");
            int input = scan.nextInt();

            switch (input) {
                case 0:
                    return;
                case 1:
                    System.out.print("\nSearch Movie: ");
                    String title = scan.next();
                    searchMovie(title);
                    break;
                case 2:
                    System.out.println("Popular Movies: ");
                    showPopularMovies();
                    break;

                case 3:
                    System.out.println("\n(You can just copy from the results)");
                    System.out.print("Enter Movie: ");
                    String movieTitle = scan.next();

                    for(Movie movie : movies) {
                        if(movie.getTitle().equals(movieTitle)) {
                            movieDetail(movie.getId());
                            break;
                        }
                    }

                    System.out.println("The movie must be in the recent list");
                    System.out.println();
                    break;
                case 4:
                    System.out.println("\n(You can just copy from the results)");
                    System.out.print("Enter Movie: ");
                    String movieToAdd = scan.next();

                    for(Movie movie : movies) {
                        if(movie.getTitle().equals(movieToAdd)) {
                            DatabaseConnectivity.insertMovie(user.getID(), movie.getId(), movie.getTitle());
                            System.out.println("Added to Favorite Movies\n");
                            break;
                        }
                    }
                    break;
                case 5:
                    System.out.print("\nYour Favorite Movies: \n");
                    user.fetchFavoriteMovies(DatabaseConnectivity.fetchFavoriteMovies(user.getID()));
                    user.showFavoriteMovies();
                    break;

                default:
                    System.out.println("Select a correct number!");
                    break;
            }

        }
    }

     public void searchMovie(String title) {
         movies = MovieAPI.fetchSearchedMovies(title);
         movies.forEach(movie -> {
             System.out.println("Movie Title: " + movie.getTitle());
         });

         System.out.println();
     }

     public void  showPopularMovies() {
         movies = MovieAPI.fetchPopularMovies();
         movies.forEach(movie -> {
            System.out.println("Movie Title: " + movie.getTitle());
        });

         System.out.println();
     }

     public void movieDetail(int movieID) {
        MovieAPI.loadMovieDetail(movieID);
     }


}
