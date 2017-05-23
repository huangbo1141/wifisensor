package robin.com.wifisensor.model;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import robin.com.wifisensor.Doc.Constants;


/**
 * Created by hgc on 8/1/2016.
 */
public class PhotoManager {

    Object concurrentHot;
    Object concurrentFresh;
    private static final String concurrentInstance = "concurrentInstance";

    private static PhotoManager photoManager;

    Context context = null;


    public static PhotoManager getInstance(Context context){
        synchronized (concurrentInstance){
            if (photoManager == null){
                photoManager = new PhotoManager(context);
            }
            return photoManager;
        }

    }
    PhotoManager(Context context){
        this.context = context;
    }

    public static Integer getLastSyncStatus(Context context) {
        Integer data = 0;
        try {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
            data = prefs.getInt("lastSyncStatus", 0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return data;
    }
    public static boolean setLastSyncStatus(Integer data, Context context){
        try{
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).edit();
            editor.putInt("lastSyncStatus",data);
            editor.commit();

            // broadcast
            Intent intent = new Intent();
            intent.putExtra("status",data);
            intent.setAction(Constants.BROADCAST_SYNC_CHANGESTATUS);
            Context appcontext = context.getApplicationContext();
            appcontext.sendBroadcast(intent);
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }


    public static boolean logout(Context context){
        try{
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(Constants.KEY_HASLOGIN, -1);
            editor.commit();

//            setFirstname("",context);
//            setLastname("",context);
//            setScreen("",context);
//            setLanguage("",context);
//            setUsername("",context);
//            setPassword("",context);
//            setUsertimezone("",context);
//            setPublicShare("",context);
//            setLastMySyncDay("",context);
//            setMyInviteePending(0,context);
//            setFriendInviteePending(0,context);
//            setTotalInviteePending(0,context);
//            setMyUsers("",context);
//            setLastSyncDate("",context);

        }catch (Exception ex){
            ex.printStackTrace();
        }

        return true;
    }


    public static boolean setUserDefaultString(String keyword, String value, Context context){
        try{
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).edit();
            editor.putString(keyword,value);
            editor.commit();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }
    public static String getUserDefaultString(String keyword, Context context){
        try{
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
            String value = prefs.getString(keyword,"");
            return value;
        }catch (Exception ex){
            ex.printStackTrace();
            return "";
        }
    }

    public static boolean setUserDefaultInt(String keyword, Integer value, Context context){
        try{
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).edit();
            editor.putInt(keyword,value);
            editor.commit();
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }
    public static int getUserDefaultInt(String keyword, Context context){
        try{
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
            int value = prefs.getInt(keyword,0);
            return value;
        }catch (Exception ex){
            ex.printStackTrace();
            return 0;
        }
    }
}
