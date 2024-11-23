package com.moviedatabase.main;

import com.moviedatabase.jdbc.DatabaseConnectivity;

import java.util.Scanner;

public class UserAuthentication {
    private Scanner scan = new Scanner(System.in);

    public boolean loginUser() {
        boolean isLoggedIn = false;

        while(!isLoggedIn) {
            System.out.println("User Login");
            System.out.print("Enter Username: ");
            String name = scan.next();
            System.out.print("Enter Password: ");
            String password = scan.next();

            if(DatabaseConnectivity.isUserLoggedIn(name, password)) {
                isLoggedIn = true;
                System.out.println("Logged in");
            } else {
                System.out.println("User not found");
            }
        }

        return isLoggedIn;

    }

    public boolean registerUser() {
        boolean isRegistered = false;

        while(!isRegistered) {
            System.out.println("User Registration");
            System.out.print("Enter Username: ");
            String name = scan.next();
            System.out.print("Enter Password: ");
            String password = scan.next();
            System.out.print("Re-Type Password: ");
            String reTypePassword = scan.next();

            if(!DatabaseConnectivity.isUserRegistered(name)) {
                if(password.equals(reTypePassword)) {
                    isRegistered = true;
                    DatabaseConnectivity.insertUser(name, password);
                    System.out.println("User registered");
                } else {
                    System.out.println("Password mismatched");
                }
            } else {
                System.out.println("User already exist");
            }

        }

        return isRegistered;
    }
}
