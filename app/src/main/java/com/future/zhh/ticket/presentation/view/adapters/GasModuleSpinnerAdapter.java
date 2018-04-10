package com.future.zhh.ticket.presentation.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.domain.model.GasmoduleList;

import java.util.List;

/**
 * Created by Administrator on 2017/11/30.
 */

public class GasModuleSpinnerAdapter extends BaseAdapter {
    private List<GasmoduleList> mList;
    private Context mContext;
    public GasModuleSpinnerAdapter(Context pContext, List<GasmoduleList> pList) {
        this.mContext = pContext;
        this.mList = pList;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        LayoutInflater _LayoutInflater=LayoutInflater.from(mContext);
        ViewHolder holder ;
        if (convertView == null) {
            convertView=_LayoutInflater.inflate(R.layout.spinner_gas_module_item, null);
            holder = new ViewHolder();
            holder.tv = (TextView) convertView.findViewById(R.id.tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv.setText(mList.get(position).getModuleType());
        holder.tv.setTextSize(15);
        return convertView;
    }
    static    class ViewHolder {
        private TextView tv;
    }
}
