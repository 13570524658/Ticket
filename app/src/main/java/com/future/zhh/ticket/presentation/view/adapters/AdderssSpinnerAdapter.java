package com.future.zhh.ticket.presentation.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.domain.model.AddressListInfo;
import com.future.zhh.ticket.domain.model.GasmoduleList;

import java.util.List;

/**
 * Created by Administrator on 2017/12/8.
 */

public class AdderssSpinnerAdapter  extends BaseAdapter {
    private List<AddressListInfo> mList;
    private Context mContext;
    public AdderssSpinnerAdapter(Context pContext, List<AddressListInfo> mList) {
        this.mContext = pContext;
        this.mList = mList;
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
        AdderssSpinnerAdapter.ViewHolder holder ;
        if (convertView == null) {
            convertView=_LayoutInflater.inflate(R.layout.spinner_address, null);
            holder = new AdderssSpinnerAdapter.ViewHolder();
            holder.tv =  convertView.findViewById(R.id.tv);
            convertView.setTag(holder);
        } else {
            holder = (AdderssSpinnerAdapter.ViewHolder) convertView.getTag();
        }
        holder.tv.setText(mList.get(position).getAddress());
        holder.tv.setTextSize(15);
        return convertView;
    }
    static    class ViewHolder {
        private TextView tv;
    }
}
