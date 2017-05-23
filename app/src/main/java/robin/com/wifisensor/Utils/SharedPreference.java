package robin.com.wifisensor.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Bhadresh Chavada on 12-02-2017.
 */

public class SharedPreference {

    Context context;
    SharedPreferences sharedPref;
    String PREFERENCE = "PREFERENCE";


    public SharedPreference(Context context) {
        this.context = context;
        sharedPref = context.getSharedPreferences(PREFERENCE
                , Context.MODE_PRIVATE);
    }

    public void SaveValue(String key, String Value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, Value);
        editor.commit();
    }

    public String RetriveValue(String Key) {
        return sharedPref.getString(Key, "");
    }

    public void clearSharedPrefrence() {
        sharedPref.edit().clear().commit();
    }

}
