package com.future.zhh.ticket.presentation.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.domain.model.GasJfLogInfo;
import com.future.zhh.ticket.presentation.view.activities.GasMsgActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/11/27.
 */

public class JfGasScanLogAdapter extends RecyclerView.Adapter<JfGasScanLogAdapter. JfGasScanLogHolder>{

    private Context mContext;
    public List<GasJfLogInfo> datas = null;

    public JfGasScanLogAdapter(List<GasJfLogInfo> datas, Context mContext) {
        this.datas = datas;
        this.mContext=mContext;
    }
    @Override
    public JfGasScanLogHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_hs_gas_scan_log,parent,false);
        return new JfGasScanLogHolder(view);
    }

    @Override
    public void onBindViewHolder(JfGasScanLogHolder holder, final int position) {
        holder.hsGasLabel.setText(datas.get(position).getLabel());
        holder.hsGasModule.setText(datas.get(position).getType());
        if (position!=0){
            holder.lineTop.setVisibility(View.GONE);
        }
        holder.hsGasLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(mContext,GasMsgActivity.class);
                intent.putExtra("label",datas.get(position).getLabel());
                mContext.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return datas==null?0:datas.size();
    }
    public static class JfGasScanLogHolder extends RecyclerView.ViewHolder {
        private TextView hsGasLabel;
        private TextView hsGasModule;
        private TextView lineTop;
        public JfGasScanLogHolder(View itemView) {
            super(itemView);
            hsGasLabel=itemView.findViewById(R.id.tv_hsGasLabel);
            hsGasModule=itemView.findViewById(R.id.tv_hsGasModule);
            lineTop=itemView.findViewById(R.id.tv_line_top);
        }
    }
}
