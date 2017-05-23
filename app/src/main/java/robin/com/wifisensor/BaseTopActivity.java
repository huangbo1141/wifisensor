package robin.com.wifisensor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import robin.com.wifisensor.Doc.CGlobal;


/**
 * Created by hgc on 6/26/2015.
 */
public class BaseTopActivity extends AppCompatActivity{

    protected ImageView imgHome;
    protected ImageView imgLogo;
    protected ImageView imgWifi;
    protected View logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected void initHeaderBar(int mode) {
        try {
            logout = findViewById(R.id.activity_main_logout);
            logout.setOnClickListener(onClickListener);
            logout.setEnabled(true);
            logout.setVisibility(View.GONE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            imgLogo = (ImageView) findViewById(R.id.imgLogo);
            imgLogo.setOnClickListener(onClickListener);
            imgLogo.setImageResource(R.drawable.home);
            imgLogo.setOnClickListener(onClickListener);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            imgWifi = (ImageView) findViewById(R.id.activity_main_connect);
            imgLogo.setOnClickListener(onClickListener);
            imgWifi.setVisibility(View.GONE);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id){
                case R.id.imgLogo:{
                    Intent intent = new Intent(BaseTopActivity.this,MainActivity.class);
                    startActivity( intent);
                    break;
                }
                case R.id.activity_main_logout: {
                    CGlobal.logout(BaseTopActivity.this);
                    break;
                }
            }
        }
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("restored", 1);
    }

    private void initFonts() {
//        Typeface openSans = Typeface.createFromAsset(getAssets(), AppFonts.OPENSANS_SEMIBOLD);
//        topTitle .setTypeface(openSans);
    }

    protected void setBackImage() {
        //imgHome.setImageResource(R.mipmap.ic_action_back);
    }

}
