package robin.com.wifisensor.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Bhadresh Chavada on 12-02-2017.
 */

public class Utils {
    public static String UserName = "USERNAME";
    public static String Password = "PASSWORD";
    public static String USERID = "USERID";
    public static String PREFS_NAME = "prefrence";

    static public boolean setPreference(Context c, String value, String key) {
        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, 0);
        settings = c.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    static public String getPreference(Context c, String key) {
        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, 0);
        settings = c.getSharedPreferences(PREFS_NAME, 0);
        String value = settings.getString(key, "");
        return value;
    }

    static public void clearSharedPrefrence(Context c) {
        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, 0);
        settings = c.getSharedPreferences(PREFS_NAME, 0);
        settings.edit().clear().commit();
    }
}
