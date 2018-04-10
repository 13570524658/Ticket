package com.future.zhh.ticket.presentation.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.future.zhh.ticket.R;
import com.future.zhh.ticket.presentation.internal.dl.HasComponent;
import com.future.zhh.ticket.presentation.utils.CommonLog;
import com.future.zhh.ticket.presentation.utils.LogFactory;

/**
 * Created by Administrator on 2017/11/19.
 */

public abstract class BaseFragment extends Fragment implements View.OnTouchListener{
    public final static String TAG = BaseFragment.class.getSimpleName();
    private CommonLog logger = LogFactory.createLog(TAG);
    public boolean isVisible = false;

    BackHandledInterface backHandledInterface;

    public  boolean OnBackPress(){
        return false;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getActivity() instanceof BackHandledInterface){
            this.backHandledInterface = (BackHandledInterface) getActivity();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        backHandledInterface.setSelectedFragment(this);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            isVisible = true;
            onVisiable();
        }else{
            isVisible = false;
            onInvisiable();
        }
    }

    private void onVisiable(){
        lazyLoad();
    }

    private void onInvisiable(){

    }

    protected abstract void lazyLoad();

    protected void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }

    protected void addFragment(int containerViewId, Fragment fragment, String fragmentTag, boolean isShowAnim) {
        android.support.v4.app.FragmentManager fragmentManager = this.getChildFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(isShowAnim) {
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_bottom, R.anim.exit_to_bottom);
        }
        if(fragmentManager.findFragmentByTag(fragmentTag) != null){
            logger.e("fragmentManager.findFragmentByTag(fragmentTag) != null");
            fragmentTransaction.show(fragment).commit();
        }else{
            logger.e("else");
            fragmentTransaction.add(containerViewId,fragment,fragmentTag)
                    .commit();
        }
    }

    public void showToast(String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }



}

