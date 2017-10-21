package robin.com.wifisensor;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import robin.com.wifisensor.Doc.CGlobal;
import robin.com.wifisensor.Doc.Constants;
import robin.com.wifisensor.model.tbl.TblClient;
import robin.com.wifisensor.model.tbl.TblTrack;
import robin.com.wifisensor.util.http.ApiClient;

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

        init();
    }
    String track ;
    String origin;
    TblClient tblClient;

    TblTrack tblTrack;
    ArrayList<String> received_data = new ArrayList<>();
    void init() {

        Intent intent = getIntent();
        try{
            received_data = intent.getStringArrayListExtra("received_data");
        }catch (Exception ex){

        }
        try{
            track = intent.getStringExtra("track");
            origin = intent.getStringExtra("origin");
            tblClient = (TblClient) intent.getSerializableExtra("client");
        }catch (Exception ex){

        }
        try{
            tblTrack = (TblTrack) intent.getSerializableExtra("track_data");
        }catch (Exception ex){

        }
        if (tblTrack!=null){
            // review mode
            btnRedo.setVisibility(View.GONE);

        }
        btnRedo.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        if (tblTrack != null){
            File wifisensor = CGlobal.dirChecker("wifisensor");
            File file = new File(wifisensor.getAbsolutePath() + File.separator + tblTrack.tt_id+".txt");
            received_data = CGlobal.readFromDisk(file);
        }
        updateGraph();
    }
    public void updateGraph(){
//        CGlobal.drawGraphSample(mChart, this);
        CGlobal.drawGraph(received_data,mChart,TrackGraphActivity.this);
    }


    public boolean saveDataToFile(TblTrack tblTrack){
        if (tblTrack == null){
            return false;
        }

        File wifisensor = CGlobal.dirChecker("wifisensor");
        File file = new File(wifisensor.getAbsolutePath() + File.separator + tblTrack.tt_id+".txt");
        boolean writtenToDisk = CGlobal.writeToDisk(received_data, file);
        return writtenToDisk;
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
                if (tblTrack ==null){
                    TblTrack tblTrack = new TblTrack();
                    tblTrack.tc_id = tblClient.tc_id;
                    tblTrack.tt_track = track;
                    tblTrack.tt_origin = origin;
                    tblTrack.tt_device = Constants.curDevice;

                    TblTrack insertData = CGlobal.dbManager.insertTrack(tblTrack);

                    if (saveDataToFile(insertData)){
                        String postData = CGlobal.getPostData(insertData);
                        // upload to webstore
                        ApiClient.ApiInterface apiClient = ApiClient.getApiClient();
                        Call<Object> call = apiClient.onPostData(Constants.key,postData);
                        call.enqueue(new Callback<Object>() {
                            @Override
                            public void onResponse(Call<Object> call, Response<Object> response) {
                                try{
                                    String string = response.body().toString();
                                    Intent intent = new Intent(TrackGraphActivity.this,TrackListActivity.class);
                                    intent.putExtra("client",tblClient);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }catch (Exception ex){
                                    Log.e("postData","error");
                                }
                                Log.e("postData","error");
                            }

                            @Override
                            public void onFailure(Call<Object> call, Throwable t) {
                                Log.e("postData","error");

                            }
                        });


                    }else{
                        Toast.makeText(TrackGraphActivity.this,"Error while saving",Toast.LENGTH_LONG).show();
                    }


                }else{
                    // next track
                    TblTrack track =  CGlobal.dbManager.checkNextTrack(tblTrack);
                    if (track !=null){
                        tblTrack = track;
                        updateGraph();
                    }else{
                        Toast.makeText(this,"No More Tracks",Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            }
        }
    }


}
