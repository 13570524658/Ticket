package com.future.zhh.ticket.presentation.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.domain.model.GasSetUpLogInfo;
import com.future.zhh.ticket.domain.model.GasSetUpLogListInfo;
import com.future.zhh.ticket.presentation.listener.ItemClickCallBack;
import com.future.zhh.ticket.presentation.view.activities.ByInputtingEditActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/12/1.
 */

public class ByInputtingLogAdapter extends RecyclerView.Adapter<ByInputtingLogAdapter. ByInputtingLogHolder> {
    private Context mContext;
    public List<GasSetUpLogListInfo> datas ;
    public       ItemClickCallBack clickCallBack;
    public void setClickCallBack(ItemClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }
    public ByInputtingLogAdapter(List<GasSetUpLogListInfo> datas, Context mContext) {
        this.datas = datas;
        this.mContext=mContext;
    }
    @Override
    public ByInputtingLogHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_by_in_putting_log,parent,false);
        return new ByInputtingLogHolder(view);
    }

    @Override
    public void onBindViewHolder(final ByInputtingLogHolder holder, final int position ) {
        holder.gasLabel.setText(datas.get(position).getGasLabe());
        holder.steelCode.setText(datas.get(position).getGasSteelcode());
        holder.factory.setText(datas.get(position).getProductFactoryID());
        holder.setUpTime.setText(datas.get(position).getSetUpTime());
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, ByInputtingEditActivity.class);
                intent.putExtra("label",datas.get(position).getGasLabe());
                intent.putExtra("enterpriseID",datas.get(position).getEnterpriseID());
                mContext.startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() {
        return datas==null?0:datas.size();
    }
    public static class ByInputtingLogHolder extends RecyclerView.ViewHolder {
        private TextView gasLabel;
        private TextView  steelCode;
        private TextView factory;
        private TextView setUpTime;
        private LinearLayout item;
        public ByInputtingLogHolder(View itemView) {
            super(itemView);
            gasLabel=itemView.findViewById(R.id.tv_gasLabel);
            steelCode=itemView.findViewById(R.id.tv_steelCode);
            factory=itemView.findViewById(R.id.tv_factoryID);
            setUpTime=itemView.findViewById(R.id.tv_setUptime);
            item=itemView.findViewById(R.id.ll_item);
        }
    }
}
