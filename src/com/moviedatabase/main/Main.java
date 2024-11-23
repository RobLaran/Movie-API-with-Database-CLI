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

        cli.mainInterface();
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
                            running = false;
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
        System.out.println("(1) Search Movies");
        System.out.println("(2) Movie Detail");
        System.out.println("(3) Favorite Movies");
        System.out.println("(4) Log out");
        System.out.print("Enter a number (1-4): ");
        int input = scan.nextInt();

        switch (input) {
            case 1:
                System.out.print("\nSearch Movie: ");
                String title = scan.next();
                searchMovie(title);
                break;
            case 2:
                System.out.print("\nEnter Movie: ");
                String movieTitle = scan.next();

                for(Movie movie : movies) {
                    if(movie.getTitle().equals(movieTitle)) {
                        movieDetail(movie.getId());
                        break;
                    }
                }

                System.out.println();
                break;
            case 3:

                break;

            case 4:

                break;

            default:
                System.out.println("Select a correct number!");
                break;
        }
        System.out.println(movies);
        mainInterface();
    }

     public void searchMovie(String title) {
         movies = MovieAPI.fetchSearchedMovies(title);
         movies.forEach(movie -> {
             System.out.println("Movie Title: " + movie.getTitle());
         });

         System.out.println();
     }

     public void movieDetail(int movieID) {
        MovieAPI.loadMovieDetail(movieID);
     }


}
