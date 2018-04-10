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
import com.future.zhh.ticket.domain.model.PayGasInfo;
import com.future.zhh.ticket.presentation.view.activities.GasMsgActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/25.
 */

public class DeliveryManagerMsgJFAdapter  extends RecyclerView.Adapter<DeliveryManagerMsgJFAdapter. DeliveryManagerJFMsgHolder> {

    private Context mContext;
    public List<PayGasInfo> datas = null;

    public DeliveryManagerMsgJFAdapter(List<PayGasInfo> datas,Context mContext) {
        this.datas = datas;
        this.mContext=mContext;
    }
    @Override
    public DeliveryManagerJFMsgHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_delivery_manager_msg_jf,parent,false);
        return new DeliveryManagerJFMsgHolder(view);
    }

    @Override
    public void onBindViewHolder(final DeliveryManagerJFMsgHolder holder, int position ) {
        holder.jfGasLabel.setText(datas.get(position).getGasLabel());
        holder.jfGasLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, GasMsgActivity.class);
                intent.putExtra("label",holder.jfGasLabel.getText().toString());
                mContext.startActivity(intent);
            }
        });
        holder.jfGasModule.setText(datas.get(position).getGasModule());
        if (position!=0){
            holder.lineTop.setVisibility(View.GONE);
        }
    }



    @Override
    public int getItemCount() {
        return datas==null?0:datas.size();
    }
    public static class DeliveryManagerJFMsgHolder extends RecyclerView.ViewHolder {
        private TextView jfGasLabel;
        private TextView jfGasModule;
        private TextView lineTop;

        public DeliveryManagerJFMsgHolder(View itemView) {
            super(itemView);
            jfGasLabel=itemView.findViewById(R.id.tv_jfGasLabel);
            jfGasModule=itemView.findViewById(R.id.tv_jsGasModule);
            lineTop=itemView.findViewById(R.id.tv_line_top);

        }
    }
}
