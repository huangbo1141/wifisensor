package robin.com.wifisensor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Bhadresh Chavada on 22-02-2017.
 */

public class UsercheckModel {
    @SerializedName("available")
    @Expose
    private String available;

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }
}
