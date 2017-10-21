package robin.com.wifisensor.model.tbl;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 5/23/2017.
 */

public class TblTrack extends Object implements Serializable {
    public String tt_id;
    public String tt_device;
    public String tc_id;
    public String tt_track;
    public String tt_origin;

    @Override
    public String toString() {
        return tt_origin;
    }

    public JSONObject getJsonObject(JSONObject jsonObject){
        if (jsonObject ==null)
            jsonObject = new JSONObject();
        try{
            jsonObject.put("device",tt_device);
        }catch (Exception ex){

        }
        try{
            jsonObject.put("track",tt_track);
        }catch (Exception ex){

        }
        try{
            jsonObject.put("origin",tt_origin);
        }catch (Exception ex){

        }

        return jsonObject;
    }
}
