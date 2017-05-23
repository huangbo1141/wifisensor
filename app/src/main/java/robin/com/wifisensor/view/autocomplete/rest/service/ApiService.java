package robin.com.wifisensor.view.autocomplete.rest.service;



import java.util.Map;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.QueryMap;
import robin.com.wifisensor.view.autocomplete.rest.responses.PredictionResponse;


/**
 * Created by DAVID-WORK on 17/06/2015.
 */
public interface ApiService
{
    @GET("/place/autocomplete/json")
    void getPredictions(@QueryMap Map<String, String> options, Callback<PredictionResponse> callback);

    @GET("/place/autocomplete/json")
    PredictionResponse getPredictions(@QueryMap Map<String, String> options);
}
