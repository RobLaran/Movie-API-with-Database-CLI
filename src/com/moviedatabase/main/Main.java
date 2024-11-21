package com.moviedatabase.main;

import com.moviedatabase.jdbc.DatabaseConnectivity;

import java.util.Scanner;

public class Main {
    Scanner scan = new Scanner(System.in);
    static UserAuthentication authentication = new UserAuthentication();
    private User user;


    public static void main(String[] args) {
        Main cli = new Main();

        cli.runInterface();
    }

    public void runInterface() {
        DatabaseConnectivity.connectDatabase();

        System.out.println("Welcome to Movie Database with API");
        System.out.println("(1) Login");
        System.out.println("(2) Register");
        System.out.println("(3) Exit");
        System.out.print("Enter number: ");
        int input = scan.nextInt();

        switch(input) {
            case 1:
                authentication.loginUser();
                break;
            case 2:
                authentication.registerUser();
                break;
            case 3:
                System.exit(0);
                break;

            default:
                System.out.println("Select a correct number!");
                break;
        }
    }

     public void searchMovie() {
         System.out.println("Search Movie");
     }


}
