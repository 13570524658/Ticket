package com.future.zhh.ticket.presentation.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.domain.model.CustomerCardNoTaskListInfo;
import com.future.zhh.ticket.presentation.ApplicationDatas;
import com.future.zhh.ticket.presentation.presenters.Presenter;
import com.future.zhh.ticket.presentation.view.activities.OrderMsgActivity;
import com.future.zhh.ticket.presentation.view.activities.ScanGasInfoActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/27.
 */

public class CustomerJfHsMsgAdapter extends RecyclerView.Adapter<CustomerJfHsMsgAdapter.CustomerJfHsMsgHolder> {
    private Context mContext;
    public List<CustomerCardNoTaskListInfo> datas = null;

    public CustomerJfHsMsgAdapter(List<CustomerCardNoTaskListInfo> datas, Context mContext) {
        this.datas = datas;
        this.mContext = mContext;
    }

    @Override
    public CustomerJfHsMsgHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_custmoer_jf_hs_msg, parent, false);
        return new CustomerJfHsMsgHolder(view);
    }

    @Override
    public void onBindViewHolder(final CustomerJfHsMsgHolder holder, final int position) {
        holder.transportTaskID.setText(datas.get(position).getTransportTaskID());
        holder.orderID.setText(datas.get(position).getTransportTaskID());
        holder.orderTime.setText(datas.get(position).getTransportTime());
        holder.customerAddress.setText(datas.get(position).getCustomerAddress());
        holder.phone.setText(datas.get(position).getPhone());
        holder.jfData.setText(datas.get(position) .getDeliveryMsg());
        holder.hsData.setText(datas.get(position) .getReclaimMsg());

        holder.transportTaskID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, OrderMsgActivity.class);
                intent.putExtra("orderID",holder.transportTaskID.getText().toString());
                mContext.startActivity(intent);
            }
        });

        holder.orderID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,OrderMsgActivity.class);
                intent.putExtra("orderID",holder.orderID.getText().toString());
                mContext.startActivity(intent);
            }
        });

        if (datas.get(position).getTransportStatus().equals("1")) {
            holder.stutas.setText("已发货");

            holder.jf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(mContext, "跳转到交付", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(mContext, ScanGasInfoActivity.class);
                    intent.putExtra(ApplicationDatas.TAP_FROM, ApplicationDatas.TAP_FROM_JF);
                    intent.putExtra("transportTaskID",datas.get(position).getTransportTaskID());
                    mContext.startActivity(intent);
                }
            });
            holder.hs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(mContext, "跳转到回收", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(mContext, ScanGasInfoActivity.class);
                    intent.putExtra(ApplicationDatas.TAP_FROM, ApplicationDatas.TAP_FROM_HS);
                    intent.putExtra("transportTaskID",datas.get(position).getTransportTaskID());
                    mContext.startActivity(intent);
                }
            });
        } else if (datas.get(position).getTransportStatus().equals("0")) {
            holder.stutas.setText("已完成");
            holder.jf.setVisibility(View.GONE);
            holder.hs.setVisibility(View.GONE);
            holder.adapter.setVisibility(View.GONE);

        }


    }


    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public static class CustomerJfHsMsgHolder extends RecyclerView.ViewHolder {
        private TextView transportTaskID;
        private TextView stutas;
        private TextView orderID;
        private TextView orderTime;
        private TextView customerAddress;
        private TextView phone;
        private TextView jf;
        private TextView hs;
        private TextView jfData;
        private TextView hsData;
        private LinearLayout adapter;


        public CustomerJfHsMsgHolder(View itemView) {
            super(itemView);
            transportTaskID = itemView.findViewById(R.id.tv_transoprtTaskID);
            stutas = itemView.findViewById(R.id.tv_status);
              orderID = itemView.findViewById(R.id.tv_orderID);
            orderTime = itemView.findViewById(R.id.tv_orderTime);
            customerAddress = itemView.findViewById(R.id.tv_customerAddress);
                    phone = itemView.findViewById(R.id.tv_phone);
            jf = itemView.findViewById(R.id.tv_jf);
            hs = itemView.findViewById(R.id.tv_hs);
            jfData = itemView.findViewById(R.id.tv_jfData);
            hsData = itemView.findViewById(R.id.tv_hsData);
            adapter=itemView.findViewById(R.id.ll_adapter);

        }
    }
}
