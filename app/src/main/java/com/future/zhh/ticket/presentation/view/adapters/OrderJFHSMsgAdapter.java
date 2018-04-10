package com.future.zhh.ticket.presentation.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.domain.model.OrderTaskListInfo;
import com.future.zhh.ticket.presentation.ApplicationDatas;
import com.future.zhh.ticket.presentation.view.activities.OrderMsgActivity;
import com.future.zhh.ticket.presentation.view.activities.ScanGasInfoActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/27.
 */

public class OrderJFHSMsgAdapter extends RecyclerView.Adapter<OrderJFHSMsgAdapter.OrderJFHSMsgHolder> {
    private Context mContext;
    public List<OrderTaskListInfo> datas = null;

    public OrderJFHSMsgAdapter(List<OrderTaskListInfo> datas, Context mContext) {
        this.datas = datas;
        this.mContext = mContext;
    }

    @Override
    public OrderJFHSMsgHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_order_jf_hs_msg, parent, false);
        return new OrderJFHSMsgHolder(view);
    }

    @Override
    public void onBindViewHolder(final OrderJFHSMsgHolder holder, final int position) {
        holder.transportTaskID.setText(datas.get(position).getTransportTaskID());
        holder.transportTaskID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, OrderMsgActivity.class);
                intent.putExtra("orderID",holder.transportTaskID.getText().toString());
                mContext.startActivity(intent);
            }
        });
        holder.jfData.setText(datas.get(position).getDeliveryMsg());
        holder.hsData.setText(datas.get(position).getReclaimMsg());

        if (datas.get(position).getTransportStatus().equalsIgnoreCase("1")) {
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
        } else if (datas.get(position).getTransportStatus().equalsIgnoreCase("0")) {
            holder.stutas.setText("已完成");
            holder.jf.setVisibility(View.GONE);
            holder.hs.setVisibility(View.GONE);

        }


    }


    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public static class OrderJFHSMsgHolder extends RecyclerView.ViewHolder {
        private TextView transportTaskID;
        private TextView jf;
        private TextView hs;
        private TextView jfData;
        private TextView hsData;
        private TextView stutas;

        public OrderJFHSMsgHolder(View itemView) {
            super(itemView);
            transportTaskID = itemView.findViewById(R.id.tv_tarnsportTaskID);
            jf = itemView.findViewById(R.id.tv_jf);
            hs = itemView.findViewById(R.id.tv_hs);
            jfData = itemView.findViewById(R.id.tv_jf_data);
            hsData = itemView.findViewById(R.id.tv_hs_data);
            stutas = itemView.findViewById(R.id.status);
        }
    }
}
