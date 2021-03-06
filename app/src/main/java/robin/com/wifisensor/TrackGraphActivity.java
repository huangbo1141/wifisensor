package robin.com.wifisensor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;

import butterknife.BindView;
import butterknife.ButterKnife;
import robin.com.wifisensor.Doc.CGlobal;
import robin.com.wifisensor.Doc.Constants;
import robin.com.wifisensor.model.tbl.TblClient;
import robin.com.wifisensor.model.tbl.TblTrack;

/**
 * Created by Bhadresh Chavada on 11-02-2017.
 */

public class TrackGraphActivity extends BaseTopActivity implements View.OnClickListener {


    @BindView(R.id.chart1)
    LineChart mChart;

    @BindView(R.id.btnPrev)
    View btnRedo;

    @BindView(R.id.btnNext)
    View btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_graph);
        ButterKnife.bind(this);
        initHeaderBar(0);
        String filename = null;
        try {
            Intent intent = getIntent();
            filename = intent.getStringExtra("filename");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        init(filename);
    }
    String track ;
    String origin;
    TblClient tblClient;

    String tblTrack;
    void init(String filename) {
        if (filename == null) {
            CGlobal.drawGraphSample(mChart, this);
        }
        Intent intent = getIntent();
        try{
            track = intent.getStringExtra("track");
            origin = intent.getStringExtra("origin");
            tblClient = (TblClient) intent.getSerializableExtra("client");
        }catch (Exception ex){

        }
        try{
            tblTrack = intent.getStringExtra("track_data");
        }catch (Exception ex){

        }
        if (tblTrack!=null){
            // review mode

        }
        btnRedo.setOnClickListener(this);
        btnNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnPrev:{
                finish();
                break;
            }
            case R.id.btnNext:{
                if (tblClient !=null){
                    TblTrack tblTrack = new TblTrack();
                    tblTrack.tc_id = tblClient.tc_id;
                    tblTrack.tt_track = track;
                    tblTrack.tt_origin = origin;
                    tblTrack.tt_device = Constants.curDevice;
                    CGlobal.dbManager.insertTrack(tblTrack);

                    Intent intent = new Intent(TrackGraphActivity.this,TrackListActivity.class);
                    intent.putExtra("client",tblClient);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }

                break;
            }
        }
    }


}
