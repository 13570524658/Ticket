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
import com.future.zhh.ticket.domain.model.CheckLogInfo;
import com.future.zhh.ticket.presentation.listener.ItemClickCallBack;
import com.future.zhh.ticket.presentation.view.activities.CheckLogMsgActivity;
import com.future.zhh.ticket.presentation.view.activities.CustomerMsgActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/12/12.
 */

public class CheckLogAdapter extends RecyclerView.Adapter<CheckLogAdapter. CheckLogHolder> {
    private Context mContext;


    public void setClickCallBack(ItemClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    public List<CheckLogInfo> datas = null;
    public       ItemClickCallBack clickCallBack;

    public CheckLogAdapter(List<CheckLogInfo> datas, Context mContext) {
        this.datas = datas;
        this.mContext=mContext;
    }
    //创建新View，被LayoutManager所调用
    @Override
    public CheckLogHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_check_log_item,viewGroup,false);
        return new CheckLogHolder(view);
    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(CheckLogHolder viewHolder,final int position) {

        viewHolder.customerName.setText(datas.get(position).getCustomerName());
        viewHolder.customerID.setText(datas.get(position).getCustomerID());
        viewHolder.address.setText(datas.get(position).getAddress());
        viewHolder.checkTime.setText(datas.get(position).getCheckTime());

        if (datas.get(position).getIsPass().equals("0")){
            viewHolder.state.setText("合格");
            viewHolder.state.setTextColor(Color.GREEN);
        }else if (datas.get(position).getIsPass().equals("1")){
            viewHolder.state.setText("不合格");
            viewHolder.state.setTextColor(Color.RED);
        }
        viewHolder.customerID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, CustomerMsgActivity.class);
                intent.putExtra("customerID",datas.get(position).getCustomerID());
                mContext.startActivity(intent);
            }
        });
        viewHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,CheckLogMsgActivity.class);
                intent.putExtra("customerName",datas.get(position).getCustomerName());
                intent.putExtra("customerID",datas.get(position).getCustomerID());
                mContext.startActivity(intent);
            }
        });


    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas==null?0:datas.size();
    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class CheckLogHolder extends RecyclerView.ViewHolder {
        public TextView customerName;
        public TextView customerID;
        public TextView state;
        public TextView address;
        public TextView checkTime;
        public LinearLayout item;



        public CheckLogHolder(View view){
            super(view);
            customerName =  view.findViewById(R.id.tv_customerName);
            customerID=view.findViewById(R.id.tv_customerID);
            state=view.findViewById(R.id.tv_state);
            address=view.findViewById(R.id.tv_address);
            checkTime=view.findViewById(R.id.tv_checkTime);
            item=view.findViewById(R.id.ll_item);
        }
    }
}
