package com.example.skybound.WebServices;

import com.example.skybound.BuildConfig;
import com.example.skybound.Utility.Constant;
import com.example.skybound.beanResponse.OrderSummary;
import com.example.skybound.beanResponse.PlaceOrder;
import com.example.skybound.beanResponse.ProductRes;
import com.example.skybound.beanResponse.StaffSignInRes;
import com.example.skybound.beanResponse.UserSignInRes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceWrapper {


    private ServiceInterface mServiceInterface;

    public ServiceWrapper(Interceptor mInterceptorheader) {
        mServiceInterface = getRetrofit(mInterceptorheader).create(ServiceInterface.class);
    }

    public Retrofit getRetrofit(Interceptor mInterceptorheader) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient mOkHttpClient = null;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(Constant.API_CONNECTION_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(Constant.API_READ_TIMEOUT, TimeUnit.SECONDS);

//        if (BuildConfig.DEBUG)
//            builder.addInterceptor(loggingInterceptor);

        if (BuildConfig.DEBUG) {
//            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }


        mOkHttpClient = builder.build();
        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(mOkHttpClient)
                .build();
        return retrofit;
    }


    ///  user signin
    public Call<UserSignInRes> UserSigninCall(String id, String password){
        return mServiceInterface.UserSigninCall(convertPlainString(id),  convertPlainString(password));
    }

    ///  user signin
    public Call<StaffSignInRes> StaffSigninCall(String id, String password){
        return mServiceInterface.StaffSigninCall(convertPlainString(id),  convertPlainString(password));
    }


    // convert aa param into plain text
    public RequestBody convertPlainString(String data){
        RequestBody plainString = RequestBody.create(MediaType.parse("text/plain"), data);
        return  plainString;
    }
    ///  product details
    public Call<ProductRes> getProductRes(String securcode){
        return mServiceInterface.getPorduct(convertPlainString(securcode));
    }

    ///  place order
    public Call<PlaceOrder> PlaceOrderCall(String securecode, String user_id, String prod_id, String total_price, String qty){
        return mServiceInterface.PlaceOrderCall(convertPlainString(securecode),  convertPlainString(user_id),  convertPlainString(prod_id),  convertPlainString(total_price),  convertPlainString(qty));
    }

    // get order summery
    public Call<OrderSummary> getOrderSummarycall(String securcode){
        return mServiceInterface.getOrderSummarycall(convertPlainString(securcode));
    }
}
