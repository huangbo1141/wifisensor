
package robin.com.wifisensor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Capabilities {

    @SerializedName("subscriber")
    @Expose
    private Boolean subscriber;

    public Boolean getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Boolean subscriber) {
        this.subscriber = subscriber;
    }

}
