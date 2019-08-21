package storage;

import android.content.Context;
import android.content.SharedPreferences;

import constants.ApplicationConstants;

/**
 * Created by prasadsawant on 11/4/16.
 */

public class MySharedPreferences {

    public static void setPreferenceString(Context context, String key, String value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(ApplicationConstants.CHARGE_BOT_SHARED_PREFERENCES,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();

    }


    public static String getPreferenceString(Context context, String key) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(ApplicationConstants.CHARGE_BOT_SHARED_PREFERENCES,
                Context.MODE_PRIVATE);

        return sharedPreferences.getString(key, null);

    }


    public static void setPreferenceBoolean(Context context, String key, boolean value) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(ApplicationConstants.CHARGE_BOT_SHARED_PREFERENCES,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();

    }

    public static boolean getPreferenceBoolean(Context context, String key) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(ApplicationConstants.CHARGE_BOT_SHARED_PREFERENCES,
                Context.MODE_PRIVATE);

        return sharedPreferences.getBoolean(key, false);

    }
}
