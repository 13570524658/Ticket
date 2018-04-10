package com.future.zhh.ticket.presentation.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/11/19.
 */
public class BaseDialogFragment extends DialogFragment implements View.OnTouchListener{

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
        backHandledInterface.setSelectDialogFragment(this);
    }

    protected void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }




}
