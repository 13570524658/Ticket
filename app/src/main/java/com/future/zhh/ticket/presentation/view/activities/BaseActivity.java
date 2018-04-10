package com.future.zhh.ticket.presentation.view.activities;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.presentation.AppApplication;
import com.future.zhh.ticket.presentation.internal.dl.components.ApplicationComponent;
import com.future.zhh.ticket.presentation.internal.dl.modules.ActivityModule;
import com.future.zhh.ticket.presentation.navigation.Navigator;
import com.future.zhh.ticket.presentation.utils.CommonLog;
import com.future.zhh.ticket.presentation.utils.LogFactory;
import com.future.zhh.ticket.presentation.utils.SoundPlayUtil;
import com.future.zhh.ticket.presentation.view.fragments.BackHandledInterface;
import com.future.zhh.ticket.presentation.view.fragments.BaseDialogFragment;
import com.future.zhh.ticket.presentation.view.fragments.BaseFragment;

import javax.inject.Inject;

/**
 * Created by Administrator on 2017/11/19.
 */
public abstract class BaseActivity extends AppCompatActivity implements BackHandledInterface {
    public final static String TAG = BaseActivity.class.getSimpleName();
    private CommonLog logger = LogFactory.createLog(TAG);
    private Context mContext;


    @Inject
    Navigator navigator;

    protected SoundPlayUtil soundPlayUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(this.getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE){
            // 隐藏标题栏
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            // 隐藏状态栏
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        AppApplication.get(this).addActivity(this);
        ApplicationComponent applicationComponent = AppApplication.get(this).getApplicationComponent();
        applicationComponent.inject(this);
        this.setupActivityComponent(applicationComponent);
        soundPlayUtil = new SoundPlayUtil(this);
    }
    protected ApplicationComponent getApplicationComponent(){
        return ((AppApplication)getApplication()).getApplicationComponent();
    }

    protected abstract void setupActivityComponent(ApplicationComponent applicationComponent);

    protected ActivityModule getActivityModule(){
        return new ActivityModule(this);
    }

    @Override
    public void setSelectedFragment(BaseFragment selectedFragment) {

    }

    @Override
    public void setSelectDialogFragment(BaseDialogFragment baseDialogFragment) {

    }

    public void showToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    public void showToast(int msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    protected void addFragment(int containerViewId, Fragment fragment, String fragmentTag, boolean isShowAnim) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(isShowAnim){
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_bottom, R.anim.exit_to_bottom);
        }
        if(fragmentManager.findFragmentByTag(fragmentTag) != null){
            fragmentTransaction.show(fragment).commit();
        }else{
            fragmentTransaction
                    .add(containerViewId,fragment,fragmentTag)
                    .commit();
        }

    }

    protected void hideFragment(Fragment fragment, boolean isShowAnim){
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(isShowAnim) {
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_bottom, R.anim.exit_to_bottom);
        }
        fragmentTransaction.hide(fragment)
                .commit();
    }


    protected void addDialogFragment(BaseDialogFragment baseDialogFragment, String fragmentTag){
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        baseDialogFragment.show(fragmentManager,fragmentTag);
    }

    protected void hideDialogFragment(BaseDialogFragment baseDialogFragment){
        baseDialogFragment.dismiss();
    }

}

