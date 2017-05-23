package robin.com.wifisensor.util.http;


import robin.com.wifisensor.Doc.Constants;
import robin.com.wifisensor.model.BaseModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by hgc on 5/28/2016.
 */
public interface FileUploadService {
    @Multipart
    @POST(Constants.ACTION_UPLOAD)
    Call<BaseModel> upload(@Part("description") RequestBody description,
                           @Part MultipartBody.Part file);
}
