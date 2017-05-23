package robin.com.wifisensor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.util.HashMap;
import java.util.Map;

import robin.com.wifisensor.Utils.APIServices;
import robin.com.wifisensor.Utils.AppConstant;
import robin.com.wifisensor.Utils.SharedPreference;
import robin.com.wifisensor.Utils.Utils;
import robin.com.wifisensor.Utils.Validation;
import robin.com.wifisensor.model.Loginmodel;


/**
 * Created by Bhadresh Chavada on 11-02-2017.
 */

public class LoginActivity extends BaseTopActivity implements View.OnClickListener {

    EditText emailedt, passwordedt;
    TextView registertxt;
    TextView submitbtn;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String login = new SharedPreference(LoginActivity.this).RetriveValue(Utils.USERID);
        if (login != null && !login.isEmpty()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            this.finish();
        } else {
            setContentView(R.layout.activity_login);
            init();
            initHeaderBar(0);
        }
    }

    void init() {

        emailedt = (EditText) findViewById(R.id.login_email_edt);
        passwordedt = (EditText) findViewById(R.id.login_password_edt);
        registertxt = (TextView) findViewById(R.id.login_register_txt);
        submitbtn = (TextView) findViewById(R.id.login_submit_btn);


//        emailedt.getBackground().mutate().setColorFilter(ContextCompat.getColor(this, android.R.color.white), PorterDuff.Mode.SRC_ATOP);

        submitbtn.setOnClickListener(this);
        registertxt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


        if (v.getId() == R.id.login_register_txt) {
            startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            LoginActivity.this.finish();
        } else if (v.getId() == R.id.login_submit_btn) {

            if (checkValidate()) {
                String username = new SharedPreference(LoginActivity.this).RetriveValue(Utils.UserName);
                String password = new SharedPreference(LoginActivity.this).RetriveValue(Utils.Password);

                new SharedPreference(LoginActivity.this).SaveValue(Utils.USERID, "1");
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                LoginActivity.this.finish();
            }

//            if (AppConstant.isNetworkAvailable(LoginActivity.this)) {
//                if (checkValidate()) {
//                    String username = new SharedPreference(LoginActivity.this).RetriveValue(Utils.UserName);
//                    String password = new SharedPreference(LoginActivity.this).RetriveValue(Utils.Password);
//                    if (emailedt.getText().toString().equals(username) && passwordedt.getText().toString().equals(password)) {
//                        new SharedPreference(LoginActivity.this).SaveValue(Utils.USERID, "1");
//                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                        LoginActivity.this.finish();
//                    } else {
//                        Toast.makeText(this, "Username or Password Incorrect", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//            } else {
//                Toast.makeText(this, "Please connect to Internet", Toast.LENGTH_SHORT).show();
//            }
        }
    }

    boolean checkValidate() {
        if (!Validation.isValidText(emailedt.getText())) {
            Toast.makeText(this, "Input Username", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Validation.isValidText(passwordedt.getText())) {
            Toast.makeText(this, "Input Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


}
