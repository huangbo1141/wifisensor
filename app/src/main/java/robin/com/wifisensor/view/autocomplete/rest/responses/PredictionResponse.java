package robin.com.wifisensor.view.autocomplete.rest.responses;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import robin.com.wifisensor.view.autocomplete.rest.model.Prediction;

/**
 * Created by DAVID-WORK on 19/07/2015.
 */

public class PredictionResponse extends BaseResponse
{
    @SerializedName("predictions")
    private ArrayList<Prediction> mPredictionList;

    public ArrayList<Prediction> getPredictionList()
    {
        return mPredictionList;
    }

    @Override
    public String toString()
    {
        return "PredictionResponse{" +
                "mPredictionList=" + mPredictionList +
                "} " + super.toString();
    }
}
