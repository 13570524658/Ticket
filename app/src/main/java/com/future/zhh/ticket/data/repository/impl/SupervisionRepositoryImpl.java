package com.future.zhh.ticket.data.repository.impl;

import android.content.Context;

import com.future.zhh.ticket.data.repository.SupervisionRepository;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/11/19.
 */

public class SupervisionRepositoryImpl implements SupervisionRepository {

    public final static String TAG = SupervisionRepositoryImpl.class.getSimpleName();
    private Context mContext;

    @Inject
    public SupervisionRepositoryImpl(Context context) {
        this.mContext = context;
    }
}
