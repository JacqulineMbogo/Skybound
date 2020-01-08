package com.example.skybound.Adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.skybound.Models.Cartitem_Model;
import com.example.skybound.R;

import java.util.List;


public class OrderSummary_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Cartitem_Model> cartitem_models;
    private Context mContext;
    private String TAG = "order_adapter";

    public OrderSummary_Adapter(Context context, List<Cartitem_Model> cartitemModels) {
        this.cartitem_models = cartitemModels;
        this.mContext = context;

    }
    private class ordersummaryItemView extends RecyclerView.ViewHolder {
        TextView prod_name,  prod_qty, prod_price, prod_days;


        public  ordersummaryItemView(View itemView) {
            super(itemView);
            prod_name = (TextView) itemView.findViewById(R.id.prod_name);
            prod_price = (TextView) itemView.findViewById(R.id.prod_price);
            prod_qty = (TextView) itemView.findViewById(R.id.prod_qty);
            prod_days= (TextView) itemView.findViewById(R.id.prod_days);


        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_ordersum_item, parent,false);
        Log.e(TAG, "  view created ");
        return new ordersummaryItemView(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Log.e(TAG, "bind view "+ position);
        final Cartitem_Model model =  cartitem_models.get(position);

        ((ordersummaryItemView) holder).prod_name.setText(model.getProd_name());
        ((ordersummaryItemView) holder).prod_days.setText(model.getPrice());
        ((ordersummaryItemView) holder).prod_qty.setText(model.getQty());
        ((ordersummaryItemView) holder).prod_price.setText(model.getOld_price());


    }

    @Override
    public int getItemCount() {
        return cartitem_models.size();
    }
}