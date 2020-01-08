package com.example.skybound.Ordersummary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.skybound.Adapters.OrderSummary_Adapter;
import com.example.skybound.HomeActivity;
import com.example.skybound.Models.Cartitem_Model;
import com.example.skybound.R;
import com.example.skybound.Utility.AppUtilits;
import com.example.skybound.Utility.Constant;
import com.example.skybound.Utility.NetworkUtility;
import com.example.skybound.Utility.SharedPreferenceActivity;
import com.example.skybound.WebServices.ServiceWrapper;
import com.example.skybound.beanResponse.OrderSummary;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Order_Summary extends AppCompatActivity {


    private String TAG = "ordersummery";
    private TextView place_order, subtotalvalue, shippingvalue, ordertotalvalue;


    private RecyclerView item_recyclerview;
    private ArrayList<Cartitem_Model> cartitemModels = new ArrayList<>();
    private OrderSummary_Adapter orderSummeryAdapter;
    private float totalamount =0;
    Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);


        context = this;



        subtotalvalue = (TextView) findViewById(R.id.subtotal_value);

        item_recyclerview = (RecyclerView) findViewById(R.id.item_recyclerview);

        LinearLayoutManager mLayoutManger3 = new LinearLayoutManager( this, RecyclerView.VERTICAL, false);
        item_recyclerview.setLayoutManager(mLayoutManger3);
        item_recyclerview.setItemAnimator(new DefaultItemAnimator());

        orderSummeryAdapter = new OrderSummary_Adapter(this, cartitemModels);
        item_recyclerview.setAdapter(orderSummeryAdapter);

        getUserCartDetails();



    }

    public void getUserCartDetails(){

        if (!NetworkUtility.isNetworkConnected(Order_Summary.this)){
            AppUtilits.displayMessage(Order_Summary.this,  getString(R.string.network_not_connected));

        }else {
            //  Log.e(TAG, "  user value "+ SharePreferenceUtils.getInstance().getString(Constant.USER_DATA));
            ServiceWrapper service = new ServiceWrapper(null);
            Call<OrderSummary> call = service.getOrderSummarycall("1234" );

            call.enqueue(new Callback<OrderSummary>() {
                @Override
                public void onResponse(Call<OrderSummary> call, Response<OrderSummary> response) {
                    Log.e(TAG, "response is " + response.body() + "  ---- " + new Gson().toJson(response.body()));
                    //  Log.e(TAG, "  ss sixe 1 ");
                    if (response.body() != null && response.isSuccessful()) {
                        //    Log.e(TAG, "  ss sixe 2 ");
                        if (response.body().getStatus() == 1) {

                            if (response.body().getInformation().size() > 0) {


                                subtotalvalue.setText("Ksh " + response.body().getMsg());


                                cartitemModels.clear();
                                for (int i = 0; i < response.body().getInformation().size(); i++) {


                                    cartitemModels.add(new Cartitem_Model(response.body().getInformation().get(i).getId(),
                                            response.body().getInformation().get(i).getName(), response.body().getInformation().get(i).getDate(),
                                            response.body().getInformation().get(i).getPrice(), response.body().getInformation().get(i).getQty()));


                                }

                                orderSummeryAdapter.notifyDataSetChanged();


                            } else {
                                AppUtilits.displayMessage(Order_Summary.this, response.body().getMsg());
                            }
                        } else {
                            AppUtilits.displayMessage(Order_Summary.this, getString(R.string.network_error));
                        }

                    }
                }

                @Override
                public void onFailure(Call<OrderSummary> call, Throwable t) {

                    Log.e(TAG, "  fail- order symmer item "+ t.toString());
                    AppUtilits.displayMessage(Order_Summary.this, getString(R.string.fail_toordersummery));


                }
            });



        }






    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Order_Summary.this, HomeActivity.class);
        startActivity(intent);                finish();
        super.onBackPressed();

    }
}


