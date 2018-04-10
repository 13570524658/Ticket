package com.future.zhh.ticket.presentation.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.domain.model.SinceLogListInfo;
import com.future.zhh.ticket.presentation.view.activities.SinceLogMsgActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/12/5.
 */

public class SinceLogAdapter extends RecyclerView.Adapter<SinceLogAdapter. SinceLogHolder> {
    private Context mContext;
    public List<SinceLogListInfo> datas = null;

    public SinceLogAdapter(List<SinceLogListInfo> datas, Context mContext) {
        this.datas = datas;
        this.mContext=mContext;
    }
    @Override
    public SinceLogHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_since_log,parent,false);
        return new SinceLogHolder(view);
    }

    @Override
    public void onBindViewHolder(SinceLogHolder holder, final int position) {
        holder.orderID.setText(datas.get(position).getOrderID());
        if (datas.get(position).getState().equals("0")){
            holder.state.setText("已完成");
            holder.state.setTextColor(Color.GREEN);
        }else if (datas.get(position).getState().equals("1")){
            holder.state.setText("发货中");
            holder.state.setTextColor(Color.RED);
        }
        holder.customerName.setText(datas.get(position).getCustomerName());
        holder.adress.setText(datas.get(position).getAddress());
        holder.endTime.setText(datas.get(position).getEndTime());
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( mContext,SinceLogMsgActivity.class);
                intent.putExtra("orderID",datas.get(position).getOrderID());
                mContext.startActivity(intent);
            }
        });
        }
    @Override
    public int getItemCount() {
        return datas==null?0:datas.size();
    }
    public static class SinceLogHolder extends RecyclerView.ViewHolder {
        private TextView orderID;
        private TextView state;
        private TextView customerName;
        private TextView adress;
        private TextView endTime;
        private LinearLayout item;
        public SinceLogHolder(View itemView) {
            super(itemView);
            orderID=itemView.findViewById(R.id.tvOrderID);
            state=itemView.findViewById(R.id.tvState);
            customerName=itemView.findViewById(R.id.tvCustomerName);
            adress=itemView.findViewById(R.id.tvAddress);
            endTime=itemView.findViewById(R.id.tvEndTime);
            item=itemView.findViewById(R.id.ll_item);
        }
    }
    }

