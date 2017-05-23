package robin.com.wifisensor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import robin.com.wifisensor.Doc.Constants;
import robin.com.wifisensor.Utils.SharedPreference;

/**
 * Created by Bhadresh Chavada on 11-02-2017.
 */

public class SurveyListActivity extends BaseTopActivity implements View.OnClickListener {


    @BindView(R.id.layRoot)
    LinearLayout layRoot;

    @BindView(R.id.btnAdd)
    View btnAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_survey);
        ButterKnife.bind(this);
        initHeaderBar(0);
        init();
    }

    private List<Object> data = new ArrayList<>();
    int length = 1;
    void init() {
        SharedPreference sharedPreference = new SharedPreference(this);
        String max = sharedPreference.RetriveValue("track_max");
        if (!max.isEmpty()){
            length = Integer.valueOf(max);
        }
        if (length>0){
            for (int i=0; i<length; i++){
                Map<String,Object> list = new HashMap<>();
                list.put("name","GRIDTRACK_"+String.valueOf(i+1));
                data.add(list);
            }

            for (int i=0; i<data.size(); i++){
                Map<String,Object> list = (Map<String, Object>) data.get(i);
                View view =  getLayoutInflater().inflate(R.layout.item_wifidevice,null);
                ViewHolder viewHolder = new ViewHolder(view);
                viewHolder.setData(list);
                this.layRoot.addView(viewHolder.mView);
            }
        }


        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnAdd:{
//                MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
//                        .title("Input Information")
//                        .content("Input Wifi Detail")
//                        .input("Name", "", false, new MaterialDialog.InputCallback() {
//                            @Override
//                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
//                                Map<String,Object> list = new HashMap<>();
//                                list.put("name",input.toString());
//                                data.add(list);
//
//                                View view =  getLayoutInflater().inflate(R.layout.item_wifidevice,null);
//                                ViewHolder viewHolder = new ViewHolder(view);
//                                viewHolder.setData(list);
//                                layRoot.addView(viewHolder.mView);
//                            }
//                        });
//                builder.show();

                length++;
                Map<String,Object> list = new HashMap<>();
                String max = String.valueOf(length);
                list.put("name", Constants.PREFIX_TRACK+max);
                data.add(list);

                View view =  getLayoutInflater().inflate(R.layout.item_wifidevice,null);
                ViewHolder viewHolder = new ViewHolder(view);
                viewHolder.setData(list);
                layRoot.addView(viewHolder.mView);

                SharedPreference sharedPreference = new SharedPreference(this);
                sharedPreference.SaveValue("track_max",max);
                break;
            }
        }

    }

    public class ViewHolder {

        public View mView;
        public TextView txtName;
        public View btnConnect,btnRemove;

        public Object item;
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = view.getId();
                switch (id){
                    case  R.id.btnConnect:{
                        if (item != null && item instanceof Map) {
                            Map<String, Object> list = (Map<String, Object>) item;
                            String name = (String) list.get("name");
                            Intent intent = new Intent(SurveyListActivity.this, ClientInfoActivity.class);
                            intent.putExtra("name", name);
                            startActivity(intent);
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
            txtName = (TextView) localView.findViewById(R.id.txtName);
            btnConnect = localView.findViewById(R.id.btnConnect);
            btnRemove = localView.findViewById(R.id.btnRemove);

            btnConnect.setOnClickListener(onClickListener);
            btnRemove.setOnClickListener(onClickListener);
        }

        public void setData(final Object data) {
            item = data;
            if (data instanceof Map) {
                Map<String, Object> list = (Map<String, Object>) data;
                String name = (String) list.get("name");
                txtName.setText(name);
            }
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
