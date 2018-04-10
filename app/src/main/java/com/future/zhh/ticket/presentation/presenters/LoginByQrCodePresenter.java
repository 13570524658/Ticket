package com.future.zhh.ticket.presentation.presenters;

import android.content.Context;

import com.future.zhh.ticket.domain.interactor.LoginByQrCode;
import com.future.zhh.ticket.domain.interactor.base.Case;
import com.future.zhh.ticket.domain.interactor.base.DefaultSubscriber;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.domain.model.UserInfo;
import com.future.zhh.ticket.presentation.view.LoginByQrCodeView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Administrator on 2017/11/21.
 */

public class LoginByQrCodePresenter implements Presenter {
    Case loginByQrCode;
    LoginByQrCodeView loginByQrCodeView;
    Context mContext;


    @Inject
    public LoginByQrCodePresenter(Context mContext, @Named("loginByQrCode") Case loginByQrCode){
        this.mContext = mContext;
        this.loginByQrCode = loginByQrCode;
    }

    public void setView(LoginByQrCodeView loginView){
        this.loginByQrCodeView = loginView;
    }

    public void  loginByQrCode(String userQrCode){
        ((LoginByQrCode)loginByQrCode).setData(userQrCode);
        loginByQrCode.execute(new LoginByQrCodeSubscriber(mContext));
        loginByQrCodeView.showLoading();
    }

    private final class LoginByQrCodeSubscriber extends DefaultSubscriber<Result<UserInfo>> {

        protected LoginByQrCodeSubscriber(Context mContext) {
            super(mContext);
        }
        @Override
        public void onCompleted() {
            super.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            loginByQrCodeView.hideLoading();
        }

        @Override
        public void onNext(Result<UserInfo> result) {
            super.onNext(result);
            if (result.isSuccess()&&result.getData()!=null){
                loginByQrCodeView.retLoginByQrCodeView(true,result.getData(),result.getMsg());
            }else {
                loginByQrCodeView.hideLoading();
                loginByQrCodeView.retLoginByQrCodeView(false,result.getData(),result.getMsg());
            }
        }
    }
    @Override
    public void start() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }
}
