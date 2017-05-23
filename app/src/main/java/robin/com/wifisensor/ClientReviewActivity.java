package robin.com.wifisensor;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 5/13/2017.
 */

public class ClientReviewActivity extends BaseTopActivity implements View.OnClickListener {

    @BindView(R.id.edtClientName)
    EditText edtClientName;

    @BindView(R.id.edtLocation)
    EditText edtLocation;

    @BindView(R.id.edtJob)
    EditText edtJob;

    @BindView(R.id.edtJobNumber)
    EditText edtJobNumber;

    @BindView(R.id.edtDate)
    TextView edtDate;

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
        setContentView(R.layout.activity_clientreview);
        ButterKnife.bind(this);
        initHeaderBar(0);
        init();
    }

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
                Intent intent = new Intent(this,ReviewListActivity.class);
                startActivity(intent);
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
