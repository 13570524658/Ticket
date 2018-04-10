package com.future.zhh.ticket.libswiperefreshandloadmore.common;

import android.support.v7.widget.RecyclerView;
import android.view.View;


public class UnRecyclableViewHolder extends RecyclerView.ViewHolder{
    public UnRecyclableViewHolder(View itemView) {
        super(itemView);
        setIsRecyclable(false);
    }
}
