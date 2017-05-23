// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 5/18/2015 5:57:30 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 

package robin.com.wifisensor.util.http;


import robin.com.wifisensor.Doc.Constants;
import robin.com.wifisensor.model.BaseModel;
import robin.com.wifisensor.model.Country;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;


public class ApiClient {

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

//    private static Retrofit.Builder builder =
//            new Retrofit.Builder()
//                    .baseUrl(Constants.url)
//                    //.addConverterFactory(RxJavaCallAdapterFactory.create());
//                    //.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
//                    //.addConverterFactory(GsonConverterFactory.create());
    private static int delaytimeout = 120;
    private static int connecttimeout = 120;

//    public static String url = "http://54.251.62.201";
//    public static String url = "http://cwcadmin.cwcnetwork.com.my";
//      public static String url = "http://cwcnetwork.com.my";
    private static ApiInterface apiService;
    private static ApiInterface apiService_shorttime;
    private static Map<String, ApiIpInterface> apiServices = new HashMap<>();

//    public static <S> S createService(Class<S> serviceClass) {
//        Retrofit retrofit = builder.client(httpClient.build()).build();
//        return retrofit.create(serviceClass);
//    }

    public ApiClient() {
    }

    public static ApiInterface getApiClient() {
        if (apiService == null) {

            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(connecttimeout, TimeUnit.SECONDS)
                    .connectTimeout(connecttimeout, TimeUnit.SECONDS)
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.url)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())

                    .build();

            apiService = retrofit.create(ApiInterface.class);
        }
        return apiService;
    }
    public static ApiInterface getApiClientShortTime() {
        if (apiService_shorttime == null) {
            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(5, TimeUnit.SECONDS)
                    .connectTimeout(6, TimeUnit.SECONDS)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.url)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiService_shorttime = retrofit.create(ApiInterface.class);
        }
        return apiService_shorttime;
    }

    public static ApiIpInterface getIpClient(String url) {
        if (url == null)
            return null;
        ApiIpInterface apiIpInterface = apiServices.get(url);
        if (apiIpInterface == null) {


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())

                    .build();
            apiIpInterface = retrofit.create(ApiIpInterface.class);


            apiServices.put(url, apiIpInterface);
        }
        return apiIpInterface;
    }
    public static interface ApiInterface {


        @GET(Constants.ACTION_GETANALYSISSUMMARY)
        Call<BaseModel> onUserInfo(@QueryMap Map<String, String> query1);


        @FormUrlEncoded
        @POST(Constants.ACTION_AUTHENTICATION)
        Call<BaseModel> onGetDiveDetail(@Field("id") String id);


        @Multipart
        @POST(Constants.ACTION_UPLOAD)
        Call<BaseModel> uploadFile(@Part("user") RequestBody user, @Part("pass") RequestBody pass,
                                   @Part MultipartBody.Part file);

        @POST(Constants.ACTION_ADDPOINT)
        Call<BaseModel> onAddPoint(@Body Country data);


        @GET("assets/rest/apns/{phpfile}")
        Call<BaseModel> onTemplateRequestForHotResponse(@Path("phpfile") String phpfile, @QueryMap Map<String, String> query1);

        @GET("assets/rest/apns/{phpfile}")
        Call<BaseModel> onTemplateRequest(@Path("phpfile") String phpfile, @QueryMap Map<String, String> query1);

        @GET("assets/rest/apns/{phpfile}")
        Call<BaseModel> onTemplateRequestForActionResponse(@Path("phpfile") String phpfile, @QueryMap Map<String, String> query1);

        @GET("assets/rest/apns/{phpfile}")
        Call<BaseModel> onTemplateRequestForCommentResponse(@Path("phpfile") String phpfile, @QueryMap Map<String, String> query1);
    }

    public static interface ApiIpInterface {

    }
}