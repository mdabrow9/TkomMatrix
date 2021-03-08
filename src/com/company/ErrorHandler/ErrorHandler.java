package com.company.ErrorHandler;

public class ErrorHandler {

    public static void stop(String message)
    {
        System.out.println("Błąd: "+ message);
        System.exit(1);
    }


}
