package com.future.zhh.ticket.presentation.presenters;

import android.content.Context;

import com.future.zhh.ticket.domain.interactor.LoginByPassword;
import com.future.zhh.ticket.domain.interactor.base.Case;
import com.future.zhh.ticket.domain.interactor.base.DefaultSubscriber;
import com.future.zhh.ticket.domain.model.Result;
import com.future.zhh.ticket.domain.model.UserInfo;
import com.future.zhh.ticket.presentation.view.LoginByPasswordView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by Administrator on 2017/11/21.
 */

public class LoginByPasswordPresenter implements Presenter {
    Case loginByPassword;
    LoginByPasswordView loginByPasswordView;
    Context mContext;


    @Inject
    public LoginByPasswordPresenter(Context mContext,@Named("loginByPassword") Case loginByPassword){
        this.mContext = mContext;
        this.loginByPassword = loginByPassword;
    }

    public void setView(LoginByPasswordView loginView){
        this.loginByPasswordView = loginView;
    }

    public void  loginByPassword(String userID,String password){
        ((LoginByPassword)loginByPassword).setData(userID,password);
        loginByPassword.execute(new LoginByPasswordSubscriber(mContext));
        loginByPasswordView.showLoading();
    }

    private final class LoginByPasswordSubscriber extends DefaultSubscriber<Result<UserInfo>> {

        protected LoginByPasswordSubscriber(Context mContext) {
            super(mContext);
        }
        @Override
        public void onCompleted() {
            super.onCompleted();
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            loginByPasswordView.hideLoading();
        }

        @Override
        public void onNext(Result<UserInfo> result) {
            super.onNext(result);
            if (result.isSuccess()&&result.getData()!=null){
                    loginByPasswordView.retLoginByPasswordView(true,result.getData(),result.getMsg());
            }else {
                loginByPasswordView.hideLoading();
                loginByPasswordView.retLoginByPasswordView(false,result.getData(),result.getMsg());
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
