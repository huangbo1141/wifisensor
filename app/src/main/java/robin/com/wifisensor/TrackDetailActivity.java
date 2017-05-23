package robin.com.wifisensor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import robin.com.wifisensor.model.tbl.TblClient;

/**
 * Created by Administrator on 5/13/2017.
 */

public class TrackDetailActivity extends BaseTopActivity implements View.OnClickListener {

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

    String track ;
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

    void init(){
        btnAction.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        btnAction.setText("START");

        Intent intent = getIntent();
        try{
            track = intent.getStringExtra("track");
            origin = intent.getStringExtra("origin");
            tblClient = (TblClient) intent.getSerializableExtra("client");
            txtTrack.setText(track);
            txtOrigin.setText(origin);
        }catch (Exception ex){

        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btnAction:{
                String action  = btnAction.getText().toString().toLowerCase();
                if (action.equals("start")){
                    btnAction.setText("DONE");
                    fakeData();
                }else{
                    Intent intent = new Intent(this,TrackGraphActivity.class);
                    intent.putExtra("track",track);
                    intent.putExtra("origin",origin);
                    intent.putExtra("client",tblClient);
                    startActivityForResult(intent,100);
                }

                break;
            }
            case R.id.btnCancel:{
                finish();
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100){
            btnAction.setText("START");
        }
    }

    void fakeData(){

    }
}
