package robin.com.wifisensor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import robin.com.wifisensor.Utils.APIServices;
import robin.com.wifisensor.Utils.AppConstant;
import robin.com.wifisensor.Utils.SharedPreference;
import robin.com.wifisensor.Utils.Utils;
import robin.com.wifisensor.Utils.Validation;
import robin.com.wifisensor.model.Nouncemodel;
import robin.com.wifisensor.model.Registrationmodel;
import robin.com.wifisensor.model.UsercheckModel;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Bhadresh Chavada on 11-02-2017.
 */

public class RegistrationActivity extends BaseTopActivity implements View.OnClickListener {

    public EditText usernameedt, conPwdEdt, emailedt, passedt;
    public Button sunbmitbtn;
    public String Nounce;
    boolean validuser = false;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initHeaderBar(0);
        init();
    }

    void init() {
        usernameedt = (EditText) findViewById(R.id.register_user_edt);
        conPwdEdt = (EditText) findViewById(R.id.register_cpwd_edt);
        emailedt = (EditText) findViewById(R.id.register_email_edt);
        passedt = (EditText) findViewById(R.id.register_password_edt);
        sunbmitbtn = (Button) findViewById(R.id.register_submit_btn);

//        sunbmitbtn.setEnabled(false);
        sunbmitbtn.setOnClickListener(this);

        if (AppConstant.isNetworkAvailable(RegistrationActivity.this)) {

        } else {
            Toast.makeText(this, getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }

    }

    boolean checkValidate() {
        if (!Validation.isValidText(usernameedt.getText())) {
            Toast.makeText(this, "Input Username", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Validation.isValidText(passedt.getText())) {
            Toast.makeText(this, "Input Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Validation.isValidEmail(emailedt.getText())) {
            Toast.makeText(this, "Input Valid Email", Toast.LENGTH_SHORT).show();
            return false;
        }
//        if (!Validation.isValidEmail(mobileNoEdt.getText())){
//            Toast.makeText(this, "Input Phone number", Toast.LENGTH_SHORT).show();
//            return false;
//        }
        if (!conPwdEdt.getText().toString().equals(passedt.getText().toString())) {
            Toast.makeText(this, getString(R.string.passwordmismatch), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.register_submit_btn) {
            if (AppConstant.isNetworkAvailable(RegistrationActivity.this)) {
                if (checkValidate()) {
                    new SharedPreference(RegistrationActivity.this).SaveValue(Utils.UserName, usernameedt.getText().toString());
                    new SharedPreference(RegistrationActivity.this).SaveValue(Utils.Password, passedt.getText().toString());
                    startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                    finish();
                }

            } else {
                Toast.makeText(this, getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
            }
        }
    }

    void Retrofit_Nounce() {
        Map<String, String> nonceMap = new HashMap<String, String>();
        nonceMap.put("controller", "user");
        nonceMap.put("method", "register");

        APIServices nonceservice = AppConstant.setupRetrofit(AppConstant.BASE_URL);
        Call<Nouncemodel> nonceCall = nonceservice.NounceService(nonceMap);
        Log.e("url", nonceCall.request().url().toString());
        nonceCall.enqueue(new Callback<Nouncemodel>() {
            @Override
            public void onResponse(Call<Nouncemodel> call, Response<Nouncemodel> response) {
                if (response.body() != null) {
                    Nounce = response.body().getNonce();
                }

            }

            @Override
            public void onFailure(Call<Nouncemodel> call, Throwable t) {

            }
        });
    }

    void CheckUserName() {
        Map<String, String> UserCheck = new HashMap<>();
        UserCheck.put("username", usernameedt.getText().toString());

        APIServices userCheckservice = AppConstant.setupRetrofit(AppConstant.BASE_URL_USERCHECK);
        Call<UsercheckModel> UserCheckCall = userCheckservice.UserCheckervice(UserCheck);

        UserCheckCall.enqueue(new Callback<UsercheckModel>() {
            @Override
            public void onResponse(Call<UsercheckModel> call, Response<UsercheckModel> response) {
                if (response.body() != null) {
                    if (response.body().getAvailable().equals("true")) {
                        sunbmitbtn.setEnabled(true);
                        validuser = true;
                        usernameedt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_user, 0, R.drawable.ic_check, 0);
                    } else {
                        usernameedt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_user, 0, 0, 0);
                        sunbmitbtn.setEnabled(true);
                    }
                }

            }

            @Override
            public void onFailure(Call<UsercheckModel> call, Throwable t) {

            }
        });
    }

    void Retrofir_Registartion() {

        pd = new ProgressDialog(RegistrationActivity.this);
        pd.setMessage("loading");
        pd.setCancelable(false);
        pd.show();


        Map<String, String> RegistrationMap = new HashMap<>();
        RegistrationMap.put("insecure", "cool");
        RegistrationMap.put("username", usernameedt.getText().toString());
        RegistrationMap.put("email", emailedt.getText().toString());
        RegistrationMap.put("nonce", Nounce);
        RegistrationMap.put("user_pass", passedt.getText().toString());
        RegistrationMap.put("display_name", usernameedt.getText().toString());
//        RegistrationMap.put("mobile", mobileNoEdt.getText().toString());
//        RegistrationMap.put("last_name", mobileNoEdt.getText().toString());


        APIServices registrationservice = AppConstant.setupRetrofit(AppConstant.BASE_URL);
        Call<Registrationmodel> registrationCall = registrationservice.RegistrationService(RegistrationMap);

        registrationCall.enqueue(new Callback<Registrationmodel>() {
            @Override
            public void onResponse(Call<Registrationmodel> call, Response<Registrationmodel> response) {

                pd.dismiss();

                if (response.body() != null)
                    if (response.body().getStatus() != null)
                        if (response.body().getStatus().equalsIgnoreCase("ok")) {
                            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                        } else {
                            Toast.makeText(RegistrationActivity.this, getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();
                        }
                    else
                        Toast.makeText(RegistrationActivity.this, getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(RegistrationActivity.this, getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Registrationmodel> call, Throwable t) {

            }

        });
    }
}
