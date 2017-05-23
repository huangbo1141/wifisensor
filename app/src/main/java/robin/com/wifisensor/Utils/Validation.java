package robin.com.wifisensor.Utils;

/**
 * Created by Bhadresh Chavada on 15-02-2017.
 */

public class Validation {

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public final static boolean isValidPassword(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return true;
        }
    }

    public final static boolean isValidText(CharSequence target){
        if (target == null) {
            return false;
        } else {
            String string = target.toString();
            if(string.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
