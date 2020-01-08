package com.example.skybound.WebServices;

import com.example.skybound.beanResponse.OrderSummary;
import com.example.skybound.beanResponse.PlaceOrder;
import com.example.skybound.beanResponse.ProductRes;
import com.example.skybound.beanResponse.StaffSignInRes;
import com.example.skybound.beanResponse.UserSignInRes;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ServiceInterface {


    ///  user signin request
    @Multipart
    @POST("skybound/user_signin.php")
    Call<UserSignInRes> UserSigninCall(
            @Part("id") RequestBody id,
            @Part("password") RequestBody password
    );

    ///  user staffrequest
    @Multipart
    @POST("skybound/user_signin.php")
    Call<StaffSignInRes> StaffSigninCall(
            @Part("id") RequestBody id,
            @Part("password") RequestBody password
    );
    // get products
    @Multipart
    @POST("skybound/product.php")
    Call<ProductRes> getPorduct(
            @Part("securecode") RequestBody securecode
    );


    //place order
    @Multipart
    @POST("skybound/placeorderapi.php")
    Call<PlaceOrder> PlaceOrderCall(
            @Part("securecode") RequestBody securecode,
            @Part("user_id") RequestBody user_id,
            @Part("prod_id") RequestBody prod_id,
            @Part("total_price") RequestBody total_price,
            @Part("qty") RequestBody qty
    );

    // get order summery
    @Multipart
    @POST("skybound/getordersummary.php")
    Call<OrderSummary> getOrderSummarycall(
            @Part("securecode") RequestBody securecode

    );

}
