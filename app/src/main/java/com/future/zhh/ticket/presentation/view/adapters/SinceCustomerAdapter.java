package com.future.zhh.ticket.presentation.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.domain.model.SinceByCustomerListInfo;
import com.future.zhh.ticket.presentation.ApplicationDatas;
import com.future.zhh.ticket.presentation.view.activities.OrderMsgActivity;
import com.future.zhh.ticket.presentation.view.activities.ScanGasInfoActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/12/5.
 */

public class SinceCustomerAdapter  extends RecyclerView.Adapter<SinceCustomerAdapter. SinceCustomerHolder> {
    private Context mContext;
    public List<SinceByCustomerListInfo> datas = null;

    public SinceCustomerAdapter(List<SinceByCustomerListInfo> datas,Context mContext) {
        this.datas = datas;
        this.mContext=mContext;
    }
    @Override
    public SinceCustomerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_since_customer,parent,false);
        return new SinceCustomerHolder(view);
    }

    @Override
    public void onBindViewHolder(final SinceCustomerHolder holder, final int position ) {
                holder.orderID.setText(datas.get(position).getOrderID());
                holder.orderID.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(mContext, OrderMsgActivity.class);
                        intent.putExtra("orderID",holder.orderID.getText().toString().trim());
                        mContext.startActivity(intent);
                    }
                });
                holder.transportType.setText(datas.get(position).getTransportType());
                holder.orderTime.setText(datas.get(position).getOrderTime());
                holder.address.setText(datas.get(position).getAddress());
                holder.phone.setText(datas.get(position).getPhone());

                holder.jf.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                Intent intent=new Intent(mContext, ScanGasInfoActivity.class);
                intent.putExtra(ApplicationDatas.TAP_FROM,ApplicationDatas.TAP_FROM_SINCE_JF);
                intent.putExtra("odrerID",datas.get(position).getOrderID());
                mContext.startActivity(intent);
                    }
                });
                holder.hs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(mContext, ScanGasInfoActivity.class);
                        intent.putExtra(ApplicationDatas.TAP_FROM,ApplicationDatas.TAP_FROM_SINCE_HS);
                        intent.putExtra("odrerID",datas.get(position).getOrderID());
                        mContext.startActivity(intent);
                    }
                });
    }



    @Override
    public int getItemCount() {
        return datas==null?0:datas.size();
    }
    public static class SinceCustomerHolder extends RecyclerView.ViewHolder {
        private TextView orderID;
        private TextView transportType;
        private TextView orderTime;
        private TextView address;
        private TextView phone;
        private TextView jf;
        private TextView hs;
        public SinceCustomerHolder(View itemView) {
            super(itemView);
            orderID=itemView.findViewById(R.id.tv_orderID);
            transportType=itemView.findViewById(R.id.tv_transportType);
            orderTime=itemView.findViewById(R.id.tv_orderTime);
            address=itemView.findViewById(R.id.tv_address);
            phone=itemView.findViewById(R.id.tv_phone);
            jf=itemView.findViewById(R.id.tv_jf);
            hs=itemView.findViewById(R.id.tv_hs);
        }
    }
}
