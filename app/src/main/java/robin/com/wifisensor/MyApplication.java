package robin.com.wifisensor;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.multidex.MultiDexApplication;
import android.util.Base64;
import android.util.Log;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@ReportsCrashes(
        mailTo = "bohuang29@hotmail.com", // my email here
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.crash_toast_text)
public class MyApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        printHashKey();

        ACRA.init(this);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();

    }


    public void printHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "robin.com.wifisensor",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d(" KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Log.e("error", e.toString());
        } catch (NoSuchAlgorithmException e) {

        }
    }
}
