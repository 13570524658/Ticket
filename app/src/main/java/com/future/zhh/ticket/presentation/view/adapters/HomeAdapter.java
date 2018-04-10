package com.future.zhh.ticket.presentation.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.domain.model.HomeIconModel;
import com.future.zhh.ticket.domain.model.enums.HomeCode;
import com.future.zhh.ticket.presentation.listener.OnItemClickListener;

import java.util.List;

/**
 * Created by Administrator on 2017/11/21.
 */

public class HomeAdapter extends BaseAdapter {

    private Context mContext;
    private List<HomeIconModel> homeIconModelList;
    private OnItemClickListener<HomeIconModel> onItemClickListener;

    public HomeAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<HomeIconModel> homeIconModelList){
        this.homeIconModelList = homeIconModelList;
    }

    public void setListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getCount() {
        return homeIconModelList==null?0:homeIconModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return homeIconModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return homeIconModelList.get(position).getHomeId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        HomeAdapterHolder viewHolder;
        if (convertView == null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_home_item, null);
            viewHolder = new HomeAdapterHolder();
            viewHolder.llHomeBtn = (LinearLayout) convertView.findViewById(R.id.llHomeBtn);
            viewHolder.tvDescription = (TextView) convertView.findViewById(R.id.tvDescription);
            viewHolder.ivIcon = (ImageView) convertView.findViewById(R.id.ivIcon);
            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (HomeAdapterHolder) convertView.getTag();
        }
        if(homeIconModelList.get(position).getHomeId() != HomeCode.BLANK.getId()) {
            viewHolder.ivIcon.setVisibility(View.VISIBLE);
            viewHolder.tvDescription.setVisibility(View.VISIBLE);
            viewHolder.llHomeBtn.setEnabled(true);
            viewHolder.ivIcon.setImageResource(homeIconModelList.get(position).getIcon());
            viewHolder.tvDescription.setText(homeIconModelList.get(position).getDescription());
            viewHolder.llHomeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(homeIconModelList.get(position));
                }
            });
        }else{
            viewHolder.ivIcon.setVisibility(View.INVISIBLE);
            viewHolder.tvDescription.setVisibility(View.INVISIBLE);
            viewHolder.llHomeBtn.setEnabled(false);
        }
        return convertView;
    }

    private class HomeAdapterHolder {

        LinearLayout llHomeBtn;
        TextView tvDescription;
        ImageView ivIcon;

    }
}

