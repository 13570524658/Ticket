package com.future.zhh.ticket.presentation.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.domain.model.SincePayGasInfo;
import com.future.zhh.ticket.presentation.view.activities.GasMsgActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/12/4.
 */

public class SincePayGasInfoListAdapter   extends RecyclerView.Adapter<SincePayGasInfoListAdapter. SincePayGasInfoListHolder> {
    private Context mContext;
    public List<SincePayGasInfo> datas = null;

    public SincePayGasInfoListAdapter(List<SincePayGasInfo> datas, Context mContext) {
        this.datas = datas;
        this.mContext=mContext;
    }
    @Override
    public SincePayGasInfoListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_since_pay_gas_info_list,parent,false);
        return new SincePayGasInfoListHolder(view);
    }

    @Override
    public void onBindViewHolder(final SincePayGasInfoListHolder holder, int position ) {
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
    public static class SincePayGasInfoListHolder extends RecyclerView.ViewHolder {
        private TextView jfGasLabel;
        private TextView jfGasModule;
        private TextView lineTop;
        public SincePayGasInfoListHolder(View itemView) {
            super(itemView);
            jfGasLabel=itemView.findViewById(R.id.tv_jfGasLabel);
            jfGasModule=itemView.findViewById(R.id.tv_jsGasModule);
            lineTop=itemView.findViewById(R.id.tv_line_top);
        }
    }
}
