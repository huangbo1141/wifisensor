package robin.com.wifisensor;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import robin.com.wifisensor.Doc.CGlobal;
import robin.com.wifisensor.model.tbl.TblClient;
import robin.com.wifisensor.model.tbl.TblTrack;

/**
 * Created by Bhadresh Chavada on 11-02-2017.
 */

public class TrackListActivity extends BaseTopActivity implements View.OnClickListener {


    @BindView(R.id.layRoot)
    LinearLayout layRoot;

    @BindView(R.id.txtTrack)
    TextView txtTrack;

    @BindView(R.id.spinnerOrigin)
    Spinner spinnerOrigin;

    @BindView(R.id.btnAction)
    View btnAction;

    @BindView(R.id.viewAction)
    View viewAction;


    private boolean review_mode = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_track);
        ButterKnife.bind(this);
        initHeaderBar(0);
        init();
    }

    private List<Object> data = new ArrayList<>();
    TblClient tblClient;
    void init() {
        Intent intent = getIntent();
        tblClient = (TblClient) intent.getSerializableExtra("client");
        try{
            review_mode = intent.getBooleanExtra("review_mode",false);
        }catch (Exception ex){

        }
        if (review_mode){
            viewAction.setVisibility(View.GONE);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("client",tblClient);
        data =  CGlobal.dbManager.getTrackList(map);
        if (data.size()>0){
            for (int i=0; i<data.size(); i++){
                TblTrack track = (TblTrack) data.get(i);
                View view =  getLayoutInflater().inflate(R.layout.item_track,null);
                ViewHolder viewHolder = new ViewHolder(view);
                viewHolder.setData(track);
                this.layRoot.addView(viewHolder.mView);
            }
            TblTrack tblTrack = (TblTrack) data.get(data.size()-1);
            int track = Integer.valueOf(tblTrack.tt_track);
            txtTrack.setText(String.valueOf(track+1));


        }else{
            txtTrack.setText("1");
        }

        List<String> list = new ArrayList<>();
        for (int i=0; i<100; i++){
            list.add(String.valueOf(i+1));
        }
        setSpinner(list);

        btnAction.setOnClickListener(this);
    }
    void setSpinner(List<String> list ){
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOrigin.setAdapter(dataAdapter);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnAction:{
                // go with this info
                String origin = spinnerOrigin.getSelectedItem().toString();
                Intent intent = new Intent(this,TrackDetailActivity.class);
                intent.putExtra("origin",origin);
                intent.putExtra("track",txtTrack.getText().toString());
                intent.putExtra("client",tblClient);
                startActivity(intent);
                break;
            }
            case R.id.btnAdd:{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Title");

                View view = getLayoutInflater().inflate(R.layout.dialog_track,null);
                builder.setView(view);

                final EditText track = (EditText) view.findViewById(R.id.edtTrack);
                final EditText origin = (EditText) view.findViewById(R.id.edtOrigin);

// Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strack = track.getText().toString();
                        String sorigin = origin.getText().toString();
                        if (strack.isEmpty() || sorigin.isEmpty()){
                            return;
                        }
                        Map<String,Object> list = new HashMap<>();
                        list.put("track",strack);
                        list.put("origin",sorigin);
                        data.add(list);

                        View view =  getLayoutInflater().inflate(R.layout.item_track,null);
                        ViewHolder viewHolder = new ViewHolder(view);
                        viewHolder.setData(list);
                        layRoot.addView(viewHolder.mView);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
                break;
            }
        }

    }

    public class ViewHolder {

        public View mView;
        public TextView txtTrack,txtOrigin;
        public View btnAction,btnRemove;

        public Object item;
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = view.getId();
                switch (id){
                    case  R.id.btnAction:{
                        if (item != null ) {
                            if (item instanceof Map){
                                Map<String, Object> list = (Map<String, Object>) item;
                                String track = (String) list.get("track");
                                String origin = (String) list.get("origin");
                                Intent intent = new Intent(TrackListActivity.this, TrackDetailActivity.class);
                                intent.putExtra("track", track);
                                intent.putExtra("origin", origin);
                                intent.putExtra("client", tblClient);
                                startActivity(intent);
                            }else if (item instanceof TblTrack){
                                TblTrack track = (TblTrack) item;
                                Intent intent = new Intent(TrackListActivity.this, TrackGraphActivity.class);
                                intent.putExtra("track_data", track);
                                intent.putExtra("client", tblClient);
                                startActivity(intent);

                            }

                        }
                        break;
                    }
                    case R.id.btnRemove:{
                        int index = data.indexOf(item);
                        layRoot.removeView(mView);
                        break;
                    }
                }

            }
        };

        public ViewHolder(View localView) {
            mView = localView;
            txtTrack = (TextView) localView.findViewById(R.id.txtTrack);
            txtOrigin = (TextView) localView.findViewById(R.id.txtOrigin);
            btnAction = localView.findViewById(R.id.btnAction);
            btnRemove = localView.findViewById(R.id.btnRemove);

            btnAction.setOnClickListener(onClickListener);
            btnRemove.setOnClickListener(onClickListener);
        }

        public void setData(final Object data) {
            item = data;
            if (data instanceof Map) {
                Map<String, Object> list = (Map<String, Object>) data;
                String track = (String) list.get("track");
                String origin = (String) list.get("origin");
                txtTrack.setText(track);
                txtOrigin.setText(origin);
            }else if (data instanceof TblTrack){
                TblTrack track = (TblTrack) data;
                txtTrack.setText(track.tt_track);
                txtOrigin.setText(track.tt_origin);
            }
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
