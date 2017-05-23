package robin.com.wifisensor.model;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by hgc on 5/21/2016.
 */
public class ActionType {
    public int index=-1;
    public ActionT actiontype = ActionT.TimeLine;
    public Object param1;
    //  0   like
    //  1   report
    //  2   hyperlink navigate to other users view
    //  3   share
    //  4   comment
    //  5   click hyperlink

    public ImageView imageView;
    public TextView txtLikesCount;

    public enum ActionT{
        TimeLine,Me,Friend
    }
}
