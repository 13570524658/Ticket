package com.future.zhh.ticket.domain.interactor.base;

import android.content.Context;

import com.future.zhh.ticket.domain.exception.CommonErrorHelper;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/11/19.
 */

public class DefaultSubscriber<T> extends Subscriber<T> {

    private Context mContext;

    protected DefaultSubscriber(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        CommonErrorHelper.handleCommonError(mContext,e);
    }

    @Override
    public void onNext(T t) {

    }


}
