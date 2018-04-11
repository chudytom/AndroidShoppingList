package com.example.tomasz123456.shoppinglist;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {
    private static String sortCriteraString = "sortCriteria";

    public static void SaveSortCriteria(Context context, MainActivity.SortCriteria sortCriteria)
    {
        SavePreference(context, sortCriteraString, sortCriteria.ordinal());
    }

    public static MainActivity.SortCriteria GetSortCriteria(Context context)
    {
        int sortInt =  GetPreferences(context, sortCriteraString, MainActivity.SortCriteria.Count.ordinal());
        System.out.println("Sort int: " + sortInt);
        return MainActivity.SortCriteria.values()[sortInt];
    }

    private static void SavePreference(Context context, String key, int value) {
        SharedPreferences prefs = context.getSharedPreferences(sortCriteraString, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    private static int GetPreferences(Context context, String key, int defaultValue) {
        SharedPreferences prefs = context.getSharedPreferences(sortCriteraString, Context.MODE_PRIVATE);
        return prefs.getInt(key, defaultValue);
    }

}
