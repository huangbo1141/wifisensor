package robin.com.wifisensor;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import robin.com.wifisensor.Doc.CGlobal;
import robin.com.wifisensor.Doc.Constants;
import robin.com.wifisensor.model.tbl.TblClient;

/**
 * Created by Administrator on 5/13/2017.
 */

public class ClientInfoSelectActivity extends BaseTopActivity implements View.OnClickListener {

    @BindView(R.id.spinnerClientName)
    Spinner spinnerClientName;

    @BindView(R.id.spinnerLocation)
    Spinner spinnerLocation;

    @BindView(R.id.spinnerJob)
    Spinner spinnerJob;

    @BindView(R.id.spinnerJobNumber)
    Spinner spinnerJobNumber;

    @BindView(R.id.edtDate)
    EditText edtDate;

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
        setContentView(R.layout.activity_clientinfo_select);
        ButterKnife.bind(this);
        initHeaderBar(0);
        init();
    }

    String trackname = "";
    TblClient mClient = null;
    void init(){
        edtDate.setOnClickListener(this);

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
        spinner.setEnabled(false);
        edtDate.setEnabled(false);

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
    }
    List<String> clientNames = new ArrayList<>();
    List<String> clientLocations = new ArrayList<>();
    List<String> clientJobs = new ArrayList<>();
    List<String> clientJobNums = new ArrayList<>();
    void setupAdapterName(List<Object> clist){
        spinnerClientName.setOnItemSelectedListener(null);
        clientNames = new ArrayList<>();
        if (clist!=null){
            for (int i=0; i<clist.size(); i++){
                TblClient tblClient = (TblClient) clist.get(i);
                clientNames.add(tblClient.tc_name);
            }
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, clientNames);
        spinnerClientName.setAdapter(dataAdapter);
        spinnerClientName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                Map<String,Object> map = new HashMap<>();
                map.put("name",name);
                map.put("mode","locations");
                List<Object> list = CGlobal.dbManager.getClientList(map);
                setupAdapterLocation(list);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    void setupAdapterLocation(List<Object> clist){
        clientLocations = new ArrayList<>();
        if (clist!=null){
            for (int i=0; i<clist.size(); i++){
                TblClient tblClient = (TblClient) clist.get(i);
                clientLocations.add(tblClient.tc_location);
            }
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, clientLocations);
        spinnerLocation.setAdapter(dataAdapter);
        spinnerLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String name = spinnerClientName.getSelectedItem().toString();
                String location = adapterView.getItemAtPosition(i).toString();
                Map<String,Object> map = new HashMap<>();
                map.put("location",location);
                map.put("name",name);
                map.put("mode","job");
                List<Object> list = CGlobal.dbManager.getClientList(map);
                setupAdapterJob(list);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    void setupAdapterJob(List<Object> clist){
        clientJobs = new ArrayList<>();
        if (clist!=null){
            for (int i=0; i<clist.size(); i++){
                TblClient tblClient = (TblClient) clist.get(i);
                clientJobs.add(tblClient.tc_job);
            }
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, clientJobs);
        spinnerJob.setAdapter(dataAdapter);
        spinnerJob.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String name = spinnerClientName.getSelectedItem().toString();
                String location = spinnerLocation.getSelectedItem().toString();
                String job = adapterView.getItemAtPosition(i).toString();
                Map<String,Object> map = new HashMap<>();
                map.put("location",location);
                map.put("name",name);
                map.put("job",job);
                map.put("mode","jobnumber");
                List<Object> list = CGlobal.dbManager.getClientList(map);
                setupAdapterJobNumber(list);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    void setupAdapterJobNumber(List<Object> clist){
        clientJobNums = new ArrayList<>();
        if (clist!=null){
            for (int i=0; i<clist.size(); i++){
                TblClient tblClient = (TblClient) clist.get(i);
                clientJobNums.add(tblClient.tc_jobnum);
            }
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, clientJobNums);
        spinnerJobNumber.setAdapter(dataAdapter);
        spinnerJobNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String name = spinnerClientName.getSelectedItem().toString();
                String location = spinnerLocation.getSelectedItem().toString();
                String job = spinnerJob.getSelectedItem().toString();
                String jobnum = adapterView.getItemAtPosition(i).toString();
                TblClient tblClient = new TblClient();
                tblClient.tc_name = name;
                tblClient.tc_location = location;
                tblClient.tc_job = job;
                tblClient.tc_jobnum = jobnum;
                TblClient dup = CGlobal.dbManager.checkDuplicate(tblClient);
                if (dup!=null){
                    edtDate.setText(dup.tc_date);
                    int cnt = spinner.getAdapter().getCount();
                    for (int k=0; k<cnt; k++){
                        String val = spinner.getItemAtPosition(k).toString();
                        if (val.equals(dup.tc_interval)){
                            spinner.setSelection(k,true);
                            break;
                        }
                    }
                    mClient = dup;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    boolean checkValidate(){
        if (mClient == null){
            return false;
        }
        return true;
    }
    DatePickerDialog datePickerDialog;

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.edtDate:{
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
                    Intent intent = new Intent(this,TrackListActivity.class);
                    intent.putExtra("client",mClient);
                    startActivity(intent);
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
            edtDate.setText(new StringBuilder().append(selectedMonth + 1)
                    .append("-").append(selectedDay).append("-").append(selectedYear)
                    .append(" ").toString());

            datePickerDialog.dismiss();
        }
    };

}
