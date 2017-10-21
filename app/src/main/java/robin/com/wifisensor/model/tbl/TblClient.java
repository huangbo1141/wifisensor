package robin.com.wifisensor.model.tbl;

import android.graphics.Bitmap;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 5/23/2017.
 */
public class TblClient extends Object implements Serializable {
    public String tc_id;
    public String tc_name;
    public String tc_location;
    public String tc_job;
    public String tc_jobnum;
    public String tc_date;
    public String tc_interval;

    @Override
    public String toString() {
        return tc_name;
    }

    public JSONObject getJsonObject(){
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("name",tc_name);
        }catch (Exception ex){

        }
        try{
            jsonObject.put("location",tc_location);
        }catch (Exception ex){

        }
        try{
            jsonObject.put("job",tc_job);
        }catch (Exception ex){

        }
        try{
            jsonObject.put("jobnumber",tc_jobnum);
        }catch (Exception ex){

        }
        try{
            jsonObject.put("name",tc_name);
        }catch (Exception ex){

        }

        return jsonObject;
    }
}