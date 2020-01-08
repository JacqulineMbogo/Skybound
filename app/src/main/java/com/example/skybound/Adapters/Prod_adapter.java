package com.example.skybound.Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.skybound.HomeActivity;
import com.example.skybound.Models.Prod_Model;
import com.example.skybound.Ordersummary.Order_Summary;
import com.example.skybound.Products.ProductDetails;
import com.example.skybound.R;
import com.example.skybound.Utility.AppUtilits;
import com.example.skybound.Utility.NetworkUtility;
import com.example.skybound.WebServices.ServiceWrapper;
import com.example.skybound.beanResponse.PlaceOrder;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Prod_adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Prod_Model> mProdModelList;
    private String TAG ="NewProd_adapter";
    private int mScrenwith;


    public Prod_adapter(Context context, List<Prod_Model> list, int screenwidth ){
        mContext = context;
        mProdModelList = list;
        mScrenwith =screenwidth;

    }

    private class ProductHolder extends RecyclerView.ViewHolder {
        ImageView prod_img;
        TextView prod_name,prod_price,prod_stock;
        LinearLayout cardView;

        public ProductHolder(View itemView) {
            super(itemView);
            prod_img = (ImageView) itemView.findViewById(R.id.prod_img);
            prod_name = (TextView) itemView.findViewById(R.id.prod_name);
            prod_price = (TextView) itemView.findViewById(R.id.prod_price);
            prod_stock = (TextView) itemView.findViewById(R.id.prod_stock);
            cardView =  itemView.findViewById(R.id.cardview);



            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( mScrenwith - (mScrenwith/100*70), LinearLayout.LayoutParams.MATCH_PARENT);
            params.setMargins(10,10,10,10);


        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_newproduct, parent,false);
        Log.e(TAG, "  view created ");
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Prod_Model model = mProdModelList.get(position);
        Log.e(TAG, " assign value ");
        ((ProductHolder) holder).prod_name.setText(model.getProd_name());
        ((ProductHolder) holder).prod_price.setText("Ksh: "+model.getPrice());
        ((ProductHolder) holder).prod_stock.setText("Stock: "+model.getStock());



        Glide.with(mContext)
                .load(model.getImg_ulr())
                .into(((ProductHolder) holder).prod_img);
        // imageview glider lib to get imagge from url


       ((ProductHolder) holder).prod_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, model.getStock());
                if (model.getStock().equals("0")){

                    Toast.makeText(mContext, "Item is sold out", Toast.LENGTH_LONG).show();

                } else {



                final Dialog dialog;

                view = LayoutInflater.from(mContext).inflate(R.layout.cart_popup, null, false);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

                alertDialog.setView(view);

                alertDialog.setCancelable(true);


                dialog = alertDialog.create();

                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

                dialog.show();


                final Window dialogWindow = dialog.getWindow();
                dialogWindow.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                dialogWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

                //  Log.e(TAG, "  prod_id "+String.valueOf(prod_id));

                final EditText qty = view.findViewById(R.id.qty);
                final EditText amount = view.findViewById(R.id.amount);
                final Button ok = view.findViewById(R.id.ok);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!(amount.getText().toString().equals("") || amount.getText().toString().equals("0"))) {

                            if (!(qty.getText().toString().equals("") || qty.getText().toString().equals("0"))) {
                                if(Integer.valueOf(amount.getText().toString() ) >= Integer.valueOf( model.getPrice())) {

                                    if(Integer.valueOf(qty.getText().toString()) <= Integer.valueOf(model.getStock())){

                                        dialog.cancel();
                                        placeOrder(model.getProd_id(), qty.getText().toString(), amount.getText().toString());

                                    }else{

                                        Toast.makeText(mContext, "Not enough stock remaining", Toast.LENGTH_LONG).show();

                                    }
                                }else{
                                    Toast.makeText(mContext, "Minimum price exceeded", Toast.LENGTH_LONG).show();

                                }
                            } else {
                                Toast.makeText(mContext, "Please input number of items sold", Toast.LENGTH_LONG).show();

                            }

                        } else {
                            Toast.makeText(mContext, "Please input cash amount sold for this item", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

            }
        });

    }

    public  void placeOrder(String prod_id, String qty, String amount){

        final AlertDialog progressbar = AppUtilits.createProgressBar(mContext);
        if (!NetworkUtility.isNetworkConnected(mContext)){
            AppUtilits.displayMessage(mContext,  mContext.getString(R.string.network_not_connected));

        }else {
            //  Log.e(TAG, "  user value "+ SharePreferenceUtils.getInstance().getString(Constant.USER_DATA));
            ServiceWrapper service = new ServiceWrapper(null);
            Call<PlaceOrder> call = service.PlaceOrderCall("12345", "sampleuser1", prod_id, amount, qty );
            call.enqueue(new Callback<PlaceOrder>() {
                @Override
                public void onResponse(Call<PlaceOrder> call, Response<PlaceOrder> response) {

                    Log.e(TAG, "reposne is " + response.body().getInformation());
                    if (response.body() != null && response.isSuccessful()) {
                        if (response.body().getStatus() == 1) {

                            AppUtilits.destroyDialog(progressbar);
                            AppUtilits.displayMessage(mContext, response.body().getMsg());

                   /*         Intent intent = new Intent(mContext, HomeActivity.class);
                            startActivity(intent);
                            finish();*/


                        }else {
                            AppUtilits.displayMessage(mContext,  response.body().getMsg());
                        }
                    }else {
                        AppUtilits.displayMessage(mContext, mContext.getString(R.string.network_error));
                    }
                }

                @Override
                public void onFailure(Call<PlaceOrder> call, Throwable t) {
                    Log.e(TAG, "  fail delete cart "+ t.toString());
                    AppUtilits.displayMessage(mContext, mContext.getString(R.string.fail_toplaceorder));

                }
            });


        }


    }
    @Override
    public int getItemCount() {
        return mProdModelList.size();
    }
}
