package robin.com.wifisensor;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import robin.com.wifisensor.Doc.CGlobal;
import robin.com.wifisensor.Doc.Constants;
import robin.com.wifisensor.Utils.SharedPreference;
import robin.com.wifisensor.Utils.Utils;

/**
 * Created by Administrator on 5/13/2017.
 */

public class MainActivity extends BaseTopActivity implements View.OnClickListener,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,LocationListener {

    @BindView(R.id.menu1)
    View menu1;

    @BindView(R.id.menu2)
    View menu2;

    @BindView(R.id.menu3)
    View menu3;

    @BindView(R.id.menu4)
    View menu4;

    public static GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initHeaderBar(0);
        CGlobal.initGlobal(this);
        init();
    }

    protected Location mLastLocation;
    private AddressResultReceiver mResultReceiver;

    @Override
    public void onLocationChanged(Location location) {
        if (mLastLocation == null) {
            mLastLocation = location;
            stopLocationUpdates();
//            startLocationUpdates(-1);
        } else {
            // stay as this
            Log.d("onLocationChanged", "xx");
        }
        startIntentService();
    }

    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            // Display the address string
            // or an error message sent from the intent service.
            String mAddressOutput = resultData.getString(Constants.RESULT_DATA_KEY);


            // Show a toast message if an address was found.
            if (resultCode == Constants.SUCCESS_RESULT) {
                Constants.mAddressOutput = mAddressOutput;
            }

        }
    }
    void init(){
        menu1.setOnClickListener(this);
        menu2.setOnClickListener(this);
        menu3.setOnClickListener(this);
        menu4.setOnClickListener(this);

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

    }
    private boolean requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
            return false;
        }
        return true;
    }
    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }
    protected void startIntentService() {
        mResultReceiver =new AddressResultReceiver(new Handler());
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, mResultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, mLastLocation);
        startService(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }
    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

//        mLastLocation = new Location("gps");
        if (mLastLocation != null) {
            // Determine whether a Geocoder is available.
            if (!Geocoder.isPresent()) {
                Toast.makeText(this, R.string.no_geocoder_available,
                        Toast.LENGTH_LONG).show();
                return;
            }

            startIntentService();
        }else{
            if (requestLocationPermission()) {
                startLocationUpdates(0);
            }
        }
    }
    private LocationRequest mLocationRequest;
    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */
    protected void startLocationUpdates(int mode) {
        switch (mode) {
            case 0: {
                // high acc
                // Create the location request
                mLocationRequest = LocationRequest.create()
                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                        .setInterval(UPDATE_INTERVAL)
                        .setFastestInterval(FASTEST_INTERVAL);
                // Request location updates
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                break;
            }
            default: {
                mLocationRequest = LocationRequest.create()
                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)  //  PRIORITY_HIGH_ACCURACY      PRIORITY_BALANCED_POWER_ACCURACY
                        .setInterval(UPDATE_INTERVAL)
                        .setFastestInterval(FASTEST_INTERVAL);
                // Request location updates
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                break;
            }
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(MainActivity.class.getName(), "onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        mGoogleApiClient = null;
        Log.d(MainActivity.class.getName(), "onConnectionFailed");
    }


    int length = 1;
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.menu1:{
                Intent intent = new Intent(this,ConnectActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.menu2:{
                SharedPreference sharedPreference = new SharedPreference(this);
                String max = sharedPreference.RetriveValue("track_max");
                if (!max.isEmpty()){
                    length = Integer.valueOf(max);
                }
                List<Object> list = CGlobal.dbManager.getClientList(null);
                if(list.size()>0){
                    Intent intent = new Intent(this,ClientInfoSelectActivity.class);
                    intent.putExtra("continue",true);
                    intent.putExtra("name", Constants.PREFIX_TRACK+String.valueOf(length));
                    startActivity(intent);
                }

                break;
            }
            case R.id.menu3:{
                Intent intent = new Intent(this,ReviewListActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.menu4:{
                CGlobal.logout(MainActivity.this);
                break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    startLocationUpdates(0);
                } else {
                    // Permission Denied
                    Toast.makeText(MainActivity.this, "Location Service Permission Denied, Please enabled it and restart app.", Toast.LENGTH_SHORT)
                            .show();
                    finish();
                }

                if (grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("permission graneted", "read write");
                } else {
                    Toast.makeText(MainActivity.this, "External Storage Permission Denied, Please enabled it and restart app.", Toast.LENGTH_SHORT)
                            .show();
                    finish();
                }
                break;
            }

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
