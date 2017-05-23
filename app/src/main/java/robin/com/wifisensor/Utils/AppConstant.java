package robin.com.wifisensor.Utils;

import android.content.Context;
import android.net.ConnectivityManager;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by AMD21 on 10/2/17.
 */

public class AppConstant {

    public static String BASE_URL = "http://designyourworld.com.au/projects/quizweb/api/";

    public static String BASE_URL_USERCHECK = "http://designyourworld.com.au/projects/quizweb/";

    public static APIServices setupRetrofit(String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        APIServices apiServices = retrofit.create(APIServices.class);
        return apiServices;
    }

    public static boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }


}
