package com.example.skybound;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.example.skybound.Adapters.Prod_adapter;
import com.example.skybound.Models.Prod_Model;
import com.example.skybound.Ordersummary.Order_Summary;
import com.example.skybound.Utility.AppUtilits;
import com.example.skybound.Utility.NetworkUtility;
import com.example.skybound.WebServices.ServiceWrapper;
import com.example.skybound.beanResponse.ProductRes;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private String  TAG = "homeactivity";



    private RecyclerView recycler_prod;
    private FloatingActionButton fab;
    private Prod_adapter Prod_adapter;

    private ArrayList<Prod_Model> ProdModelList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homelayout);

        fab= findViewById(R.id.fab);

        recycler_prod=(RecyclerView) findViewById(R.id.recycler_prod);

        LinearLayoutManager mLayoutManger3 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recycler_prod.setLayoutManager(mLayoutManger3);
        recycler_prod.setItemAnimator(new

                DefaultItemAnimator());

        Prod_adapter=new Prod_adapter(this,ProdModelList, GetScreenWidth());
        recycler_prod.setAdapter(Prod_adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, Order_Summary.class);
                startActivity(intent);                finish();
            }
        });

        getProdRes();




    }

    public void getProdRes() {

        if (!NetworkUtility.isNetworkConnected(HomeActivity.this)) {
            AppUtilits.displayMessage(HomeActivity.this, getString(R.string.network_not_connected));

        } else {
            ServiceWrapper service = new ServiceWrapper(null);
            Call<ProductRes> call = service.getProductRes("1234");
            call.enqueue(new Callback<ProductRes>() {
                @Override
                public void onResponse(Call<ProductRes> call, Response<ProductRes> response) {
                    Log.e(TAG, " response is " + response.body().getInformation().toString());
                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {
                            if (response.body().getInformation().size() > 0) {

                               ProdModelList.clear();
                                for (int i = 0; i < response.body().getInformation().size(); i++) {

                                    ProdModelList.add(new Prod_Model(response.body().getInformation().get(i).getId(), response.body().getInformation().get(i).getName(),
                                            response.body().getInformation().get(i).getImgUrl(),response.body().getInformation().get(i).getPrice(),response.body().getInformation().get(i).getStock()));

                                }

                                Prod_adapter.notifyDataSetChanged();
                            }

                        } else {
                            Log.e(TAG, "failed to get rnew prod " + response.body().getMsg());
                            // AppUtilits.displayMessage(HomeActivity.this,  response.body().getMsg());
                        }
                    } else {
                        // AppUtilits.displayMessage(HomeActivity.this,  getString(R.string.failed_request));

                        //  getNewProdRes();
                    }
                }

                @Override
                public void onFailure(Call<ProductRes> call, Throwable t) {
                    Log.e(TAG, "fail new prod " + t.toString());

                }
            });

        }

    }

    public int GetScreenWidth(){
        int  width =100;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager =  (WindowManager) getApplicationContext().getSystemService(WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;

        return width;

    }


}
