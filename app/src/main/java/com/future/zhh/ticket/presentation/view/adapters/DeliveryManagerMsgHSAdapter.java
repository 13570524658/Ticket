package com.future.zhh.ticket.presentation.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.domain.model.ReclaimGasInfo;
import com.future.zhh.ticket.presentation.view.activities.GasMsgActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/25.
 */

public class DeliveryManagerMsgHSAdapter extends RecyclerView.Adapter<DeliveryManagerMsgHSAdapter. DeliveryManagerHSMsgHolder> {

    private Context mContext;
    public List<ReclaimGasInfo> datas = null;

    public DeliveryManagerMsgHSAdapter(List<ReclaimGasInfo> datas,Context mContext) {
        this.datas = datas;
        this.mContext=mContext;
    }
    @Override
    public DeliveryManagerHSMsgHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_delivery_manager_msg_hs,parent,false);
        return new DeliveryManagerHSMsgHolder(view);
    }

    @Override
    public void onBindViewHolder(final DeliveryManagerHSMsgHolder holder, int position) {
        holder.hsGasLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, GasMsgActivity.class);
                intent.putExtra("label",holder.hsGasLabel.getText().toString());
                mContext.startActivity(intent);
            }
        });
        holder.hsGasLabel.setText(datas.get(position).getGasLabel());
        holder.hsGasModule.setText(datas.get(position).getGasModule());
        if (position!=0){
            holder.lineTop.setVisibility(View.GONE);
        }
    }



    @Override
    public int getItemCount() {
        return datas==null?0:datas.size();
    }
    public static class DeliveryManagerHSMsgHolder extends RecyclerView.ViewHolder {
        private TextView hsGasLabel;
        private TextView hsGasModule;
        private TextView lineTop;
        public DeliveryManagerHSMsgHolder(View itemView) {
            super(itemView);
            hsGasLabel=itemView.findViewById(R.id.tv_hsGasLabel);
            hsGasModule=itemView.findViewById(R.id.tv_hsGasModule);
            lineTop=itemView.findViewById(R.id.tv_line_top);
        }
    }

}
