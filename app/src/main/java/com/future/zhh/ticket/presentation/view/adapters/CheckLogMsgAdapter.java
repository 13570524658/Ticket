package com.future.zhh.ticket.presentation.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.domain.model.CheckGasLabelAndType;
import com.future.zhh.ticket.presentation.view.activities.GasMsgActivity;

import java.util.List;

/**
 * Created by Administrator on 2017/12/13.
 */

public class CheckLogMsgAdapter extends RecyclerView.Adapter<CheckLogMsgAdapter.CheckLogMsgHolder> {
    private Context mContext;
    public List<CheckGasLabelAndType> datas = null;

    public CheckLogMsgAdapter(List<CheckGasLabelAndType> datas, Context mContext) {
        this.datas = datas;
        this.mContext=mContext;
    }
    @Override
    public CheckLogMsgHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_check_log_msg,parent,false);
        return new CheckLogMsgHolder(view);
    }

    @Override
    public void onBindViewHolder(final CheckLogMsgHolder holder, final int position ) {
        holder.gasLabel.setText(datas.get(position).getGasLabel());
        holder.gasModule.setText(datas.get(position).getGasType());
        holder.gasLabel.setOnClickListener(new View.OnClickListener() {
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
    public static class CheckLogMsgHolder extends RecyclerView.ViewHolder {
        private TextView gasLabel;
        private TextView gasModule;

        public CheckLogMsgHolder(View itemView) {
            super(itemView);
            gasLabel=itemView.findViewById(R.id.tv_gasLabel);
            gasModule=itemView.findViewById(R.id.tv_gasType);

        }
    }
}
