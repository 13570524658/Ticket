package com.future.zhh.ticket.presentation.view.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.future.zhh.ticket.presentation.view.fragments.BaseFragment;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/24.
 */

public class SimplePageAdapter extends FragmentStatePagerAdapter {

    public final static String TITLE = "title";
    public final static String FRAGS = "frags";

    private List<Map<String,Object>> frags;
    private Context mContext;

    public SimplePageAdapter(FragmentManager fm, List<Map<String,Object>> frags, Context context) {
        super(fm);
        this.frags = frags;
        this.mContext = context;
    }

    public void setData(List<Map<String,Object>> frags){
        this.frags = frags;
    }

    @Override
    public Fragment getItem(int position) {
        return frags != null?(BaseFragment) frags.get(position).get(FRAGS):null;
    }

    @Override
    public int getCount() {
        return frags != null ? frags.size():0;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (CharSequence)frags.get(position).get(TITLE);
    }

}
