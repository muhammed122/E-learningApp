package com.example.e_learningapp.utils;

public class Helper {


    //fireBase does not accept . character as a reference name so, it should be hidden like his *
    public static String removeDotForFireBase(String email) {
        return email.replace(".", "*");
    }

    public static String putDotBackForUi(String email) {

        return email.replace("*", ".");
    }
}
