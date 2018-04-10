package com.future.zhh.ticket.presentation.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.domain.model.RecyclingGasListInfo;
import com.future.zhh.ticket.presentation.view.activities.GasMsgActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/12/6.
 */

public class SinceLogMsgTwoAdapter extends RecyclerView.Adapter<SinceLogMsgTwoAdapter. SinceLogMsgTwoHolder> {
    private Context mContext;
    public List<RecyclingGasListInfo> datas = null;

    public SinceLogMsgTwoAdapter(List<RecyclingGasListInfo> datas,Context mContext) {
        this.datas = datas;
        this.mContext=mContext;
    }
    @Override
    public SinceLogMsgTwoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_since_log_msg_hs,parent,false);
        return new SinceLogMsgTwoHolder(view);
    }

    @Override
    public void onBindViewHolder(final SinceLogMsgTwoHolder holder, final int position ) {
        holder.hsGasLabel.setText(datas.get(position).getGasLabel());
        holder.hsGasModule.setText(datas.get(position).getGasType());
        if (position>0){
            holder.topLine.setVisibility(View.GONE);
        }
        holder.hsGasLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, GasMsgActivity.class);
                intent.putExtra("label",datas.get(position).getGasLabel());
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return datas==null?0:datas.size();
    }
    public static class SinceLogMsgTwoHolder extends RecyclerView.ViewHolder {
        private TextView hsGasLabel;
        private TextView hsGasModule;
        private TextView topLine;

        public SinceLogMsgTwoHolder(View itemView) {
            super(itemView);
            hsGasLabel=itemView.findViewById(R.id.tv_label);
            hsGasModule=itemView.findViewById(R.id.tv_module);
            topLine=itemView.findViewById(R.id.tv_topLine);

        }
    }
}
