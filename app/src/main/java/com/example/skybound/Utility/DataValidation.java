package com.example.skybound.Utility;

import android.text.TextUtils;
import android.util.Log;

import java.util.regex.Pattern;

public class DataValidation  {

// full name  - must letters -- no- number, no special symbol
    // address - can contains letters, number, few symbol
    // phone number - length must > 9 not char,
    // passsword  - length >6, no space

    public static String PERSON_FULLNAME= "[a-zA-Z ]*";
    public static String ADDRESS= "[a-zA-Z.+-,0-9 ]*";
    public static String ID_NUMBER= "[0-9]*";
    public static String CODE= "[A-Z.+-,0-9 ]*";

    public static String EMAIL = "[a-z0-5._-]+@[a-z]+\\.+[a-z]+";

    public static Boolean isNotValidPassword( String password){
        Boolean valid = true;
        if (!TextUtils.isEmpty(password.trim())){
            if (password.trim().length()>5){
                valid =false;
            }
        }

        return valid;
    }

    public static Boolean isNotValidemail( String email){
        Boolean valid =true;
        if (!TextUtils.isEmpty(email.trim())){
            Log.e("data valid ", " full "+ email.trim());
            if (Pattern.compile( EMAIL).matcher(email).matches()){
                Log.e("databb", "match success");
                valid = false;
            }
        }

        return valid;
    }

    public static Boolean isNotValidFullName(String fullname){
        Boolean valid =true;
        if (!TextUtils.isEmpty(fullname.trim())){
            Log.e("data valid ", " full "+ fullname.trim());
            if (Pattern.compile( PERSON_FULLNAME).matcher(fullname).matches()){
                Log.e("databb", "match success");
                valid = false;
            }
        }

        return valid;
    }


    public static Boolean isNotValidAddress(String address){
        Boolean valid =true;
        if (!TextUtils.isEmpty(address.trim())){
            if (Pattern.compile( ADDRESS).matcher(address).matches()){
                valid = false;
            }
        }
        return valid;
    }
    public static Boolean isNotValidcode(String code){
        Boolean valid =true;
        if (!TextUtils.isEmpty(code.trim())){
            if (code.trim().length() == 10){
                valid = false;
            }
        }
        return valid;
    }


    public static Boolean isValidIdNumber(String idnumber){
        Boolean valid =true;
        if (!TextUtils.isEmpty(idnumber.trim()) && idnumber.length()==8){
            if (Pattern.compile( ID_NUMBER).matcher(idnumber).matches()){
                valid = false;
            }
        }
        return valid;
    }


}

