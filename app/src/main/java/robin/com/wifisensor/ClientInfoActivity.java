package robin.com.wifisensor;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import robin.com.wifisensor.Doc.CGlobal;
import robin.com.wifisensor.Doc.Constants;
import robin.com.wifisensor.model.tbl.TblClient;

/**
 * Created by Administrator on 5/13/2017.
 */

public class ClientInfoActivity extends BaseTopActivity implements View.OnClickListener {

    @BindView(R.id.autoClientName)
    AutoCompleteTextView autoClientName;

    @BindView(R.id.autoLocation)
    AutoCompleteTextView autoLocation;

    @BindView(R.id.autoJob)
    AutoCompleteTextView autoJob;

    @BindView(R.id.autoJobNumber)
    AutoCompleteTextView autoJobNumber;

    @BindView(R.id.txtDate)
    TextView txtDate;

    @BindView(R.id.spinnerInterval)
    Spinner spinner;

    @BindView(R.id.btnAction)
    View btnAction;

    @BindView(R.id.btnBack)
    View btnBack;

    int year, month, day, hour, minute;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientinfo);
        ButterKnife.bind(this);
        initHeaderBar(0);
        init();
    }

    String trackname = "";
    void init(){
        txtDate.setOnClickListener(this);
        // month day year
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");
        String strdate = simpleDateFormat.format(date);
        txtDate.setText(strdate);

        Calendar calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        year = calendar.get(Calendar.YEAR);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        btnAction.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        List<String> list = new ArrayList<String>();
        list.add("100");
        list.add("200");
        list.add("300");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        spinner.setAdapter(dataAdapter);

        Intent intent = getIntent();
        try{
            trackname = intent.getStringExtra("name");
            Constants.curDevice = trackname;
        }catch (Exception ex){
            ex.printStackTrace();
        }

        if (trackname.isEmpty()){
            Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();
        }
        Map<String,Object> map = new HashMap<>();
        map.put("mode","name");
        List<Object> clist = CGlobal.dbManager.getClientList(map);
        setupAdapterName(clist);

        map = new HashMap<>();
        map.put("mode","locations");
        List<Object> clocation = CGlobal.dbManager.getClientList(map);
        setupAdapterLocation(clocation);

        autoLocation.setText(Constants.mAddressOutput);
        // get location from location

    }
    List<String> clientNames = new ArrayList<>();
    List<String> clientLocations = new ArrayList<>();
    void setupAdapterName(List<Object> clist){
        clientNames = new ArrayList<>();
        if (clist!=null){
            for (int i=0; i<clist.size(); i++){
                TblClient tblClient = (TblClient) clist.get(i);
                clientNames.add(tblClient.tc_name);
            }
        }
        ArrayAdapter adapter1 = new
                ArrayAdapter(this, android.R.layout.simple_list_item_1, clientNames);
        autoClientName.setOnItemClickListener(null);
        autoClientName.setAdapter(adapter1);

    }
    void setupAdapterLocation(List<Object> clist){
        clientLocations = new ArrayList<>();
        if (clist!=null){
            for (int i=0; i<clist.size(); i++){
                TblClient tblClient = (TblClient) clist.get(i);
                clientLocations.add(tblClient.tc_location);
            }
        }
        ArrayAdapter adapter1 = new
                ArrayAdapter(this, android.R.layout.simple_list_item_1, clientLocations);
        autoLocation.setOnItemClickListener(null);
        autoLocation.setAdapter(adapter1);

    }



    boolean checkValidate(){
        String cinfo_name = autoClientName.getText().toString();
        String cinfo_location = autoLocation.getText().toString();
        String cinfo_job = autoJob.getText().toString();
        String cinfo_jobnumber = autoJobNumber.getText().toString();
        String cinfo_date = txtDate.getText().toString();
        if (cinfo_name.isEmpty()){
            return false;
        }
        if (cinfo_location.isEmpty()){
            return false;
        }
        if (cinfo_job.isEmpty()){
            return false;
        }
        if (cinfo_jobnumber.isEmpty()){
            return false;
        }
        if (cinfo_date.isEmpty() || cinfo_date.toLowerCase().equals("date")){
            return false;
        }
        return true;
    }
    DatePickerDialog datePickerDialog;

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.txtDate:{
                if (datePickerDialog == null) {
                    datePickerDialog = new DatePickerDialog(this, pickerListener1, year, month, day);
                }
                datePickerDialog.show();
                break;
            }
            case R.id.btnBack:{
                finish();
                break;
            }
            case R.id.btnAction:{
                if (checkValidate()){
                    String cinfo_name = autoClientName.getText().toString();
                    String cinfo_location = autoLocation.getText().toString();
                    String cinfo_job = autoJob.getText().toString();
                    String cinfo_jobnumber = autoJobNumber.getText().toString();
                    String cinfo_date = txtDate.getText().toString();
                    String cinfo_interval = spinner.getSelectedItem().toString();

                    TblClient tblClient = new TblClient();
                    tblClient.tc_name = cinfo_name;
                    tblClient.tc_location = cinfo_location;
                    tblClient.tc_job = cinfo_job;
                    tblClient.tc_jobnum = cinfo_jobnumber;
                    tblClient.tc_date = cinfo_date;
                    tblClient.tc_interval = cinfo_interval;
                    TblClient dup = CGlobal.dbManager.checkDuplicateClient(tblClient);
                    if (dup == null){
                        CGlobal.dbManager.insertClient(tblClient);
                        TblClient data = CGlobal.dbManager.checkDuplicateClient(tblClient);

                        Intent intent = new Intent(this,TrackListActivity.class);
                        intent.putExtra("client",data);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(this,"Duplicate Entry found",Toast.LENGTH_SHORT);
                    }
                }
                break;
            }
        }
    }

    private DatePickerDialog.OnDateSetListener pickerListener1 = new DatePickerDialog.OnDateSetListener() {
        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            String date = String.format("%02d-%02d-%02d",selectedMonth+1,selectedDay,selectedYear);
            txtDate.setText(date);
            datePickerDialog.dismiss();
        }
    };

}
