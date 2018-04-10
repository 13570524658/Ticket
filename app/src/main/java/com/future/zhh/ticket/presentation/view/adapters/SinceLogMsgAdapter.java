package com.future.zhh.ticket.presentation.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.domain.model.PayGasListInfo;
import com.future.zhh.ticket.domain.model.SinceTaskMsgInfo;
import com.future.zhh.ticket.presentation.view.activities.GasMsgActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/12/6.
 */

public class SinceLogMsgAdapter extends RecyclerView.Adapter<SinceLogMsgAdapter. SinceLogMsgHolder>{
    private Context mContext;
    public List<PayGasListInfo> datas = null;

    public SinceLogMsgAdapter(List<PayGasListInfo> datas,Context mContext) {
        this.datas = datas;
        this.mContext=mContext;
    }
    @Override
    public SinceLogMsgHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_since_log_msg_jf,parent,false);
        return new SinceLogMsgHolder(view);
    }

    @Override
    public void onBindViewHolder(final SinceLogMsgHolder holder, final int position ) {
            holder.jfGasLabel.setText(datas.get(position).getGasLabel());
            holder.jfGasModule.setText(datas.get(position).getGasType());
            holder.jfGasLabel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(mContext, GasMsgActivity.class);
                    intent.putExtra("label",datas.get(position).getGasLabel());
                    mContext.startActivity(intent);
                }
            });
            if (position>0){
                holder.TopLine.setVisibility(View.GONE);
            }
    }


    @Override
    public int getItemCount() {
        return datas==null?0:datas.size();
    }
    public static class SinceLogMsgHolder extends RecyclerView.ViewHolder {
        private TextView jfGasLabel;
        private TextView jfGasModule;
        private TextView TopLine;

        public SinceLogMsgHolder(View itemView) {
            super(itemView);
            jfGasLabel=itemView.findViewById(R.id.tv_label);
            jfGasModule=itemView.findViewById(R.id.tv_module);
            TopLine=itemView.findViewById(R.id.tv_topLine);

        }
    }
}
