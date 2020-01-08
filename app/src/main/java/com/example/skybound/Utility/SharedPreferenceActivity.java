package com.example.skybound.Utility;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.skybound.MyApp;

public class SharedPreferenceActivity {


    private static String PREFERENCE_NAME = "skybound";
    private static SharedPreferenceActivity sharedPreferenceActivity;

    private SharedPreferences sharedPreferences;

    private SharedPreferenceActivity(Context context) {
        PREFERENCE_NAME = PREFERENCE_NAME + context.getPackageName();
        this.sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferenceActivity getInstance(){
        if (sharedPreferenceActivity == null){
            sharedPreferenceActivity = new SharedPreferenceActivity(MyApp.getContext());
        }
        return sharedPreferenceActivity;
    }

    public void saveString(String key,  String Val ){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, Val);
        editor.commit();
    }

    public String getString(String key, String defVal){
        return sharedPreferences.getString(key, defVal);
    }


    public String getString(String key){
        return sharedPreferences.getString(key, "");
    }

    public void saveInt(String key,  int Val ){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, Val);
        editor.commit();
    }

    public int getInteger(String key){ return sharedPreferences.getInt(key, 0 ); }


}
