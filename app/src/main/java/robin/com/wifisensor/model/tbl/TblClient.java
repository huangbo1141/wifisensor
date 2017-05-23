package robin.com.wifisensor.model.tbl;

import android.graphics.Bitmap;

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
}