package com.company.ErrorHandler;

public class ErrorHandler {

    public static void stop(String message)
    {
        System.out.println("Error: "+ message);
        System.exit(1);
    }


}
