package robin.com.wifisensor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import robin.com.wifisensor.model.tbl.TblClient;


/**
 * Created by Administrator on 5/13/2017.
 */

public class TrackDetailActivity extends BaseTopActivity implements View.OnClickListener {

    private Socket mSocket;
//    {
//        try {
//            // http://mhub.inventech.co.za:3000/
//            mSocket = IO.socket("http://mhub.inventech.co.za:3000");
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//    }

    @BindView(R.id.txtTrack)
    TextView txtTrack;

    @BindView(R.id.txtOrigin)
    TextView txtOrigin;

    @BindView(R.id.txtDistance)
    TextView txtDistance;

    @BindView(R.id.txtHeight)
    TextView txtHeight;

    @BindView(R.id.txtSpeed)
    TextView txtSpeed;

    @BindView(R.id.btnAction)
    AppCompatButton btnAction;

    @BindView(R.id.btnCancel)
    AppCompatButton btnCancel;

    String track;
    String origin;
    TblClient tblClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trackdetail);
        ButterKnife.bind(this);
        initHeaderBar(0);
        init();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            mSocket.disconnect();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private ArrayList<String> received_data = new ArrayList<>();
    void init() {
        btnAction.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        btnAction.setText("START");

        Intent intent = getIntent();
        try {
            track = intent.getStringExtra("track");
            origin = intent.getStringExtra("origin");
            tblClient = (TblClient) intent.getSerializableExtra("client");
            txtTrack.setText(track);
            txtOrigin.setText(origin);
        } catch (Exception ex) {

        }



    }
    private void stopTrack(){
        try{
            if (mSocket!=null){
                mSocket.disconnect();
            }
        }catch (Exception ex){

        }

    }
    private void startTrack(){
        Socket socket;
        try {
            stopTrack();
            socket = IO.socket("http://mhub.inventech.co.za:3000/");
            mSocket = socket;
            received_data = new ArrayList<>();
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    Log.e("EVENT_CONNECT","eee");

                }

            }).on("chat room data", new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    Log.e("chat room data","eee");
                    try{
                        JSONArray arrays = (JSONArray) args[0];
                        String x=null,y=null;
                        for (int i=0; i<arrays.length(); i++){
                            JSONArray iobj = (JSONArray) arrays.get(i);
                            x = (String) iobj.get(0);
                            y = (String) iobj.get(1);
                            Log.e("chat room data",x + " " + y);
                            received_data.add(x+"_"+y);
                        }
                        final String finalX = x;
                        final String finalY = y;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    txtDistance.setText(finalX);
                                    txtHeight.setText(finalY);
                                }catch (Exception ex){

                                }

                            }
                        });
                    }catch (Exception ex){

                    }
                }

            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

                @Override
                public void call(Object... args) {
                    Log.e("EVENT_DISCONNECT","eee");
                }

            });
            socket.connect();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

//    private Emitter.Listener onNewMessage = new Emitter.Listener() {
//        @Override
//        public void call(final Object... args) {
//            try{
//                JSONObject data = (JSONObject) args[0];
//                String p = data.toString();
//                Log.e("chat room data",p);
//            }catch (Exception ex){
//                ex.printStackTrace();
//            }
//
////            TrackDetailActivity.this.runOnUiThread(new Runnable() {
////                @Override
////                public void run() {
////                    JSONObject data = (JSONObject) args[0];
////                    String p = data.toString();
////                    Log.e("chat room data",p);
////                }
////            });
//        }
//    };

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnAction: {
                String action = btnAction.getText().toString().toLowerCase();
                if (action.equals("start")) {
                    btnAction.setText("DONE");
//                    fakeData();
                    startTrack();
                } else {
                    stopTrack();
                    Intent intent = new Intent(this, TrackGraphActivity.class);
                    intent.putStringArrayListExtra("received_data",received_data);
                    intent.putExtra("track", track);
                    intent.putExtra("origin", origin);
                    intent.putExtra("client", tblClient);
                    startActivityForResult(intent, 100);
                }

                break;
            }
            case R.id.btnCancel: {
                finish();
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            btnAction.setText("START");
        }
    }

    void fakeData() {

    }
}
