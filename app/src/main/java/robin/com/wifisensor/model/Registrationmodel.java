package robin.com.wifisensor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AMD21 on 10/2/17.
 */

public class Registrationmodel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("cookie")
    @Expose
    private String cookie;
    @SerializedName("user_id")
    @Expose
    private Integer userId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
