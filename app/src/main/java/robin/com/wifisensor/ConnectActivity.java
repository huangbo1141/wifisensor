package robin.com.wifisensor;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import robin.com.wifisensor.Doc.Constants;
import robin.com.wifisensor.Utils.SharedPreference;

/**
 * Created by Administrator on 5/13/2017.
 */

public class ConnectActivity extends BaseTopActivity implements View.OnClickListener {

    @BindView(R.id.btnAction)
    View btnAction;

    @BindView(R.id.btnConnect)
    View btnConnect;


    @BindView(R.id.edtPoint)
    EditText editText;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_device);
        ButterKnife.bind(this);
        initHeaderBar(0);
        init();
    }
    int length = 1;
    void init(){
        btnAction.setOnClickListener(this);
        btnConnect.setOnClickListener(this);
        SharedPreference sharedPreference = new SharedPreference(this);
        String max = sharedPreference.RetriveValue("track_max");

        if (!max.isEmpty()){
            length = Integer.valueOf(max);
        }
        if (length>0){
            editText.setText(Constants.PREFIX_TRACK+String.valueOf(length));
        }
        editText.setEnabled(false);
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btnAction:{
                Intent intent = new Intent(this, SurveyListActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.btnConnect:{
                String name = editText.getText().toString();
                Intent intent = new Intent(this, ClientInfoActivity.class);
                intent.putExtra("name", name);
                startActivity(intent);
                finish();
                break;
            }
        }
    }
}
