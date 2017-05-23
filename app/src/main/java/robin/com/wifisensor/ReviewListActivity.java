package robin.com.wifisensor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import robin.com.wifisensor.Doc.CGlobal;
import robin.com.wifisensor.model.tbl.TblClient;

/**
 * Created by Bhadresh Chavada on 11-02-2017.
 */

public class ReviewListActivity extends BaseTopActivity implements View.OnClickListener {


    @BindView(R.id.layRoot)
    LinearLayout layRoot;

    @BindView(R.id.btnAction)
    View btnAction;

    @BindView(R.id.btnBack)
    View btnBack;

    @BindView(R.id.viewAction)
    View viewAction;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_review);
        ButterKnife.bind(this);
        initHeaderBar(0);
        init();
    }

    private List<Object> data = new ArrayList<>();
    void init() {

        btnAction.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        data = CGlobal.dbManager.getClientList(null);

        for (int i=0; i<data.size(); i++){
            TblClient tblClient = (TblClient) data.get(i);
            View view =  getLayoutInflater().inflate(R.layout.item_review,null);
            ViewHolder viewHolder = new ViewHolder(view);
            viewHolder.setData(tblClient);
            this.layRoot.addView(viewHolder.mView);
        }

        viewAction.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnBack:{
                finish();
                break;
            }
            case R.id.btnAction:{
                finish();
                break;
            }
        }

    }

    public class ViewHolder {

        public View mView;
        public TextView txtName,txtLocation,txtJob,txtJobNumber,txtDate,txtInterval;

        public Object item;
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = (int) view.getTag();
                switch (id){
                    default:{
                        if (item != null && item instanceof Map) {
                            Map<String, Object> list = (Map<String, Object>) item;
                            String name = (String) list.get("name");
                            String location = (String) list.get("location");
                            String job = (String) list.get("job");
                            String num = (String) list.get("num");
                            String date = (String) list.get("date");
                            String interval = (String) list.get("interval");
                        }else if (item instanceof TblClient) {
                            TblClient client = (TblClient) item;
                            Intent intent = new Intent(ReviewListActivity.this,TrackListActivity.class);
                            intent.putExtra("client",client);
                            intent.putExtra("review_mode",true);
                            startActivity(intent);
                        }
                        break;
                    }
                }

            }
        };

        public ViewHolder(View localView) {
            mView = localView;
            txtName = (TextView) localView.findViewById(R.id.txtName);
            txtLocation = (TextView) localView.findViewById(R.id.txtLocation);
            txtJob = (TextView) localView.findViewById(R.id.txtJob);
            txtJobNumber = (TextView) localView.findViewById(R.id.txtJobNumber);
            txtDate = (TextView) localView.findViewById(R.id.txtDate);
            txtInterval = (TextView) localView.findViewById(R.id.txtInterval);

            mView.setOnClickListener(onClickListener);
            mView.setTag(1);
        }

        public void setData(final Object data) {
            item = data;
            if (data instanceof Map) {
                Map<String, Object> list = (Map<String, Object>) data;
                String name = (String) list.get("name");
                String location = (String) list.get("location");
                String job = (String) list.get("job");
                String num = (String) list.get("num");
                String date = (String) list.get("date");
                String interval = (String) list.get("interval");

                txtName.setText(name);
                txtLocation.setText(location);
                txtJob.setText(job);
                txtJobNumber.setText(num);
                txtDate.setText(date);
                txtInterval.setText(interval);
            }else if (data instanceof TblClient){
                TblClient client = (TblClient) data;
                txtName.setText(client.tc_name);
                txtLocation.setText(client.tc_location);
                txtJob.setText(client.tc_job);
                txtJobNumber.setText(client.tc_jobnum);
                txtDate.setText(client.tc_date);
                txtInterval.setText(client.tc_interval);
            }
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
