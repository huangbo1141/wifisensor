package robin.com.wifisensor;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends BaseTopActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initHeaderBar(0);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                Intent in = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(in);

//                Intent openstartingpoint = new Intent(SplashActivity.this, DeviceListActivity.class);
//                startActivity(openstartingpoint);

                SplashActivity.this.finish();

            }
        }, 3000);

    }


}
