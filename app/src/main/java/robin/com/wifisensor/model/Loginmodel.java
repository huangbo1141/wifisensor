
package robin.com.wifisensor.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Loginmodel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("cookie")
    @Expose
    private String cookie;
    @SerializedName("cookie_name")
    @Expose
    private String cookieName;
    @SerializedName("user")
    @Expose
    private User user;

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

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
