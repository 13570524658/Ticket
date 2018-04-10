package com.future.zhh.ticket.presentation.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.domain.model.RecyclingGasInfo;
import com.future.zhh.ticket.domain.model.SincePayGasInfo;
import com.future.zhh.ticket.presentation.view.activities.GasMsgActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/12/4.
 */

public class SinceRecyclingGasInfoListAdapter   extends RecyclerView.Adapter<SinceRecyclingGasInfoListAdapter.SinceRecyclingGasInfoListHolder>  {
    private Context mContext;
    public List<RecyclingGasInfo> datas = null;

    public SinceRecyclingGasInfoListAdapter(List<RecyclingGasInfo> datas, Context mContext) {
        this.datas = datas;
        this.mContext=mContext;
    }
    @Override
    public SinceRecyclingGasInfoListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_since_recycling_gas_info_list,parent,false);
        return new SinceRecyclingGasInfoListHolder(view);
    }

    @Override
    public void onBindViewHolder(final SinceRecyclingGasInfoListHolder holder, int position ) {
        holder.jfGasLabel.setText(datas.get(position).getLabel());
        holder.jfGasLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, GasMsgActivity.class);
                intent.putExtra("label",holder.jfGasLabel.getText().toString());
                mContext.startActivity(intent);
            }
        });
        holder.jfGasModule.setText(datas.get(position).getGasType());
        if (position!=0){
            holder.lineTop.setVisibility(View.GONE);
        }
    }



    @Override
    public int getItemCount() {
        return datas==null?0:datas.size();
    }
    public static class SinceRecyclingGasInfoListHolder extends RecyclerView.ViewHolder {
        private TextView jfGasLabel;
        private TextView jfGasModule;
        private TextView lineTop;
        public SinceRecyclingGasInfoListHolder(View itemView) {
            super(itemView);
            jfGasLabel=itemView.findViewById(R.id.tv_jfGasLabel);
            jfGasModule=itemView.findViewById(R.id.tv_jsGasModule);
            lineTop=itemView.findViewById(R.id.tv_line_top);
        }
    }
}
