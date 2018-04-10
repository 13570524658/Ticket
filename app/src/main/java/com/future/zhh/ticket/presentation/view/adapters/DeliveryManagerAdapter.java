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
import android.widget.Toast;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.domain.model.TransportTaskInfo;
import com.future.zhh.ticket.presentation.ApplicationDatas;
import com.future.zhh.ticket.presentation.listener.ItemClickCallBack;
import com.future.zhh.ticket.presentation.navigation.Navigator;
import com.future.zhh.ticket.presentation.view.activities.DeliveryManagerMsgActivity;
import com.future.zhh.ticket.presentation.view.activities.LoginActivity;
import com.future.zhh.ticket.presentation.view.activities.ScanGasInfoActivity;
import com.future.zhh.ticket.presentation.view.activities.ScanInfoActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/11/24.
 */

public class DeliveryManagerAdapter extends RecyclerView.Adapter<DeliveryManagerAdapter. DeliveryManagerHolder>{
    private Context mContext;


    public void setClickCallBack(ItemClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    public List<TransportTaskInfo> datas ;
   public       ItemClickCallBack clickCallBack;

    public DeliveryManagerAdapter(List<TransportTaskInfo> datas, Context mContext) {
        this.datas = datas;
        this.mContext=mContext;
    }
    //创建新View，被LayoutManager所调用
    @Override
    public DeliveryManagerHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_deliver_manager_item,viewGroup,false);
        return new DeliveryManagerHolder(view);
    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(DeliveryManagerHolder viewHolder, final int position) {

        if (datas.get(position).getStatus().equalsIgnoreCase("0")){
            viewHolder.status.setText("已完成");
            viewHolder.status.setTextColor(Color.GREEN);
            viewHolder.jf.setVisibility(View.GONE);
            viewHolder.hs.setVisibility(View.GONE);
        }else if (datas.get(position).getStatus().equals("1")){
            viewHolder.status.setText("已发货");
            viewHolder.status.setTextColor(Color.RED);
            viewHolder.jf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent = new Intent(mContext, ScanGasInfoActivity.class);
                    intent.putExtra(ApplicationDatas.TAP_FROM, ApplicationDatas.TAP_FROM_JF);
                    intent.putExtra("transportTaskID",datas.get(position).getTransportID());
                    mContext.startActivity(intent);
                }
            });
            viewHolder.hs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ScanGasInfoActivity.class);
                    intent.putExtra(ApplicationDatas.TAP_FROM, ApplicationDatas.TAP_FROM_HS);
                    intent.putExtra("transportTaskID",datas.get(position).getTransportID());
                    mContext.startActivity(intent);
                }
            });
        }
        viewHolder.item.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(clickCallBack != null){
                            clickCallBack.onItemClick(position);
                           Intent intent=new Intent(mContext, DeliveryManagerMsgActivity.class);
                           intent.putExtra("transportID",datas.get(position).getTransportID());
                           intent.putExtra("transportTaskID",datas.get(position).getTransportTaskID());
                                mContext.startActivity(intent);

                        }
                    }
                }
        );

        viewHolder.transportID.setText(datas.get(position).getTransportID());
        viewHolder.customerName.setText(datas.get(position).getCustomerName());
        viewHolder.customerAddress.setText(datas.get(position).getAddress());
        viewHolder.setUpTime.setText(datas.get(position).getTransportTime());



    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas==null?0:datas.size();
    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class DeliveryManagerHolder extends RecyclerView.ViewHolder {
        public TextView transportID;
        public TextView customerName;
        public TextView customerAddress;
        public TextView setUpTime;
        public LinearLayout item;

        public TextView status;
        public TextView jf;
        public TextView hs;

        public DeliveryManagerHolder(View view){
            super(view);
            transportID =  view.findViewById(R.id.tv_transportID);
            customerName =  view.findViewById(R.id.tv_customerName);
            customerAddress =  view.findViewById(R.id.tv_customerAddress);
            setUpTime =  view.findViewById(R.id.tv_setUpTime);
            item=view.findViewById(R.id.ll_item);

            status=view.findViewById(R.id.tv_status);
            jf=view.findViewById(R.id.ibtn_jf);
            hs=view.findViewById(R.id.ibtn_hs);
        }
    }
}
