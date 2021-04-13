package com.example.e_learningapp.utils;

import java.util.Random;

public class Helper {


    //fireBase does not accept . character as a reference name so, it should be hidden like his *
    public static String removeDotForFireBase(String email) {
        return email.replace(".", "*");
    }

    public static String putDotBackForUi(String email) {

        return email.replace("*", ".");
    }



    public static int generateCode (){

        Random random = new Random();
        int upperBound = 9999 - 1000;
       int  code = random.nextInt(upperBound) + 1000;

       return code ;
    }
}
